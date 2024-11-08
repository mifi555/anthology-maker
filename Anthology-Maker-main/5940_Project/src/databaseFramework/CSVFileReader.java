package databaseFramework;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

/**
 * CSVFileReader class that reads a CSV File.
 *
 */
public class CSVFileReader {

    public CSVFileReader() {
    }

    /**
     * Method reads CVFile. For each row in the CSV we create a Poem Object using
     * the column data (author, title, content) and add it to a List of Poems.
     * 
     * @param fileName
     * @return poems : list of Poem Objects
     */
    public static List<Poem> readCSVFile(String fileName) {

        // create list of poem objects
        List<Poem> poems = new ArrayList<Poem>();

        try {

            CSVReader csvReader = new CSVReader(new FileReader(fileName));

            // read all data in CSV file at once
            List<String[]> rows = csvReader.readAll();

            // remove the header file (column titles)
            rows.remove(0);

            // iterate through each row in CSV file
            for (String[] arr : rows) {
                // obtain the author, title, and poem text for each row
                String author = arr[0].trim();
                String title = arr[1].trim();
                String content = arr[2];

                // create new Poem object and add it to list of poems associated with that
                // author
                poems.add(new Poem(author, title, content));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        // return list of Poem Object
        return poems;
    }

}
