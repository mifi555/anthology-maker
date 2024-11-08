package scrapingFramework;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Web crawling and scraping class that scrapes poem data.
 * 
 * @author milanfilo
 *
 */

public class WebCrawler {

    public static void main(String[] args) {

        System.out.println("Scraping started...");

        // k represents the number of pages that we wish to scrape (inclusive)
        // the website that we are using has 3448 pages
        int start = 1;
        int k = 100;

        String csvFilePath = "poem_data.csv";

        try {
            FileWriter csvWriter = new FileWriter(csvFilePath);
            // Write out header of CSV file

            csvWriter.append("Author");
            csvWriter.append(",");
            csvWriter.append("Title");
            csvWriter.append(",");
            csvWriter.append("Text");
            csvWriter.append("\n");

            // Set the path to the ChromeDriver executable
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

            // Chrome options
            ChromeOptions options = new ChromeOptions();
            // Chrome browser should run in headless mode, which means that it runs without
            // a visible user interface.
            options.addArguments("--headless");

            // disables the use of the GPU in Chrome, which can improve performance in
            // headless mode.
            options.addArguments("--disable-gpu");

            // disables the Chrome sandbox, which is a security feature that helps prevent
            // malicious code from executing on the system.
            options.addArguments("--no-sandbox");

            // load extensions: e.g. AdBlocker
//            options.addArguments(
//                    "load-extension=/Users/milanfilo/Library/Application Support/Google/Chrome/Profile 2/ghbmnnjooekpmoecnnnilnnbdlolhkhi");

            // Launch Chrome browser
            WebDriver driver = new ChromeDriver(options);

            // increase timeout
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));

            // Have Chrome browser navigate through each page to obtain individual links of
            // poems on that page
            for (int i = start; i <= k; i++) {

                // Navigate to the website
                String url = "http://www.famouspoetsandpoems.com/search/" + Integer.toString(i) + "/poems/";
                driver.get(url);

                // identify individual poem's link using a CSS Selector

                List<WebElement> poemLinks = driver.findElements(By.cssSelector("td td div a"));
                poemLinks.remove(0);

                ArrayList<String> targets = new ArrayList<String>();

                // collect targets which are href (HTML attributes) used to specify the URL of
                // the page that the link goes to
                // this is how we tell the driver to crawl to each poem's individual page
                for (WebElement link : poemLinks) {
                    targets.add(link.getAttribute("href"));
                }

                // do what is needed in the target (i.e. scrape the of an individual poem's
                // page)
                for (String target : targets) {
                    driver.get(target);

                    // get poem author
                    WebElement poemAuthor = driver.findElement(By.cssSelector("div[align='left'] span span"));
                    String poemAuthorString = poemAuthor.getText().replaceAll("by", "").trim();

                    // append poem's author to CSV file
                    csvWriter.append(poemAuthorString);

                    // new column
                    csvWriter.append(",");

                    // get poem title
                    WebElement poemTitle = driver.findElement(By.cssSelector("div > span"));
                    String[] poemTitleArray = poemTitle.getText().split(poemAuthor.getText());

                    // reformat poem title for csv file
                    String poemTitleString = poemTitleArray[0];
                    poemTitleString = "\"" + poemTitleString.replace("\"", "\"\"") + "\"";

                    // append poem title to CSV file
                    csvWriter.append(poemTitleString);

                    // new column
                    csvWriter.append(",");

//                  System.out.println(poemTitleString);

                    // get poem text
                    WebElement poemContent = driver.findElement(By.cssSelector(
                            "body > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(7) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(5)"));
                    String poemContentString = poemContent.getText();
                    poemContentString = "\"" + poemContentString.replace("\"", "\"\"") + "\"\n";

                    // append poem's content to CSV file
                    csvWriter.append(poemContentString);
                }

                System.out.println(i + " page(s) out of " + k + " scraped...");
            }

            // Close the browser
            driver.quit();

            // Close CSV writer
            csvWriter.close();

            System.out.println("Scraping complete!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
