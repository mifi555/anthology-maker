package databaseFramework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * DataBaseManage class that stores Poem data.
 * 
 * @author
 *
 */
public class DataBaseManage extends IPoem {

    /*
     * Private instance variables
     */

    // We will build our HashMaps off of this list
    private List<Poem> allPoems;

    // HashSet of poems to be written out as part of finalized anthology
    private HashSet<Poem> writtenOutPoems = new HashSet<>();

    // Maps authors to poems
    static HashMap<String, List<Poem>> authorMap;

    // Maps themes to poems.
    private static HashMap<String, List<Poem>> themeMap = new HashMap<String, List<Poem>>();

    // Maps poetic forms to poems.
    private static HashMap<String, List<Poem>> formMap;

    // Set of poems that were already written
    private static Set<Poem> writtenPoems = new HashSet<>();

    // Initial HashMap mapping themes (e.g., love, death, etc.) to HashSet of words
    // (see IPoem)
    private HashMap<String, HashSet<String>> themesToWords = new HashMap<>();

    // Index used to write out "page" number for each poem in the poem_anthology.txt
    private static int index = 1;

    /*
     * Constructor
     */

    public DataBaseManage() {
        // Construct the initial map of themes to words
        setThemesToWords(themesToWords);
        // Construct the list of poems
        allPoems = CSVFileReader.readCSVFile("poem_data.csv");
        // Populate the poems' themes
        populateThemes();
        // Create the map of authors to poems
        DataBaseManage.authorMap = createAuthorMap(allPoems);
        createThemeMap(allPoems);
        DataBaseManage.formMap = createFormMap(allPoems);

    }

    /*
     * Methods
     */

    /**
     * Creates a HashMap from poem list that maps authors to list of poems of that
     * author.
     * 
     * @param poems : list of Poem Objects generated in CSV filereader.
     * @return HashMap that maps authors to list of poems of that form.
     */
    public static HashMap<String, List<Poem>> createAuthorMap(List<Poem> poems) {
        HashMap<String, List<Poem>> authorMap = new HashMap<String, List<Poem>>();

        for (Poem poem : poems) {
            List<Poem> poemList = new ArrayList<Poem>();
            if (authorMap.containsKey(poem.getAuthor())) {
                poemList = authorMap.get(poem.getAuthor());
            } else {
                poemList = new ArrayList<Poem>();
            }
            poemList.add(poem);
            authorMap.put(poem.getAuthor(), poemList);
        }
        return authorMap;
    }

    /**
     * Gets the themes of a poem by comparing each word of the poem with the
     * constant HashSet of words associated to a theme.
     * 
     * @param body of a given poem.
     * @return the list of a poem's theme(s).
     */
    public Set<String> determineThemes(String body) {
        // Initialize list of themes to be returned
        Set<String> themes = new HashSet<>();

        // Convert body into an array of words w/o non-alphanumeric characters
        String[] words = body.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
        // Iterate through every word of the poem
        for (String word : words) {
            // Convert this word into lower case
            word.toLowerCase();
            // Iterate through the keys in the THEMES map
            for (String theme : this.themesToWords.keySet()) {
                // If current word belongs to current theme
                if (this.themesToWords.get(theme).contains(word)) {
                    // Add this theme to the list of themes
                    themes.add(theme);
                }
            }
        }
        // If no words belonged to any theme, add the theme 'other' to the list
        if (themes.isEmpty()) {
            themes.add(getThemesArray()[getThemesArray().length - 1]);
        }
        return themes;
    }

    /**
     * For each poem, sets its theme(s) by calling determineThemes().
     */
    public void populateThemes() {
        // Iterate through the list of poems in this database
        for (Poem poem : allPoems) {
            // Determine this poem's themes and store in a HashSet
            Set<String> poemThemes = determineThemes(poem.getTextString());
            // Set this poem's themes accordingly
            poem.setThemes(poemThemes);
        }
    }

    /**
     * Populates the map of themes to poems (private instance variable) given a list
     * of poems.
     * 
     * @param poems whose themes are added to themeMap.
     */
    public static void createThemeMap(List<Poem> poems) {
        // Iterate through the list of poems
        for (Poem poem : poems) {
            // Iterate through this poem's theme(s)
            for (String theme : poem.getThemes()) {
                // If this theme already featured in themeMap, add this poem as value
                if (themeMap.containsKey(theme)) {
                    themeMap.get(theme).add(poem);
                } else {
                    // Otherwise add both the theme and poem to the themeMap
                    List<Poem> themePoemList = new ArrayList<>();
                    themePoemList.add(poem);
                    themeMap.put(theme, themePoemList);
                }
            }
        }
    }

    /**
     * Creates a HashMap from poem list that maps forms to list of poems of that
     * form.
     * 
     * @param poems : list of Poem Objects generated in CSV filereader.
     * @return HashMap that maps forms to list of poems of that form.
     */
    public static HashMap<String, List<Poem>> createFormMap(List<Poem> poems) {
        // create a hashmap that maps theme to poem
        HashMap<String, List<Poem>> map = new HashMap<>();

        // create hashmap by iterating through poem list that was created in
        // CSVFileReader
        for (Poem poem : poems) {
            String form = poem.getForm();
            List<Poem> poemList = new ArrayList<Poem>();
            if (map.containsKey(form)) {
                poemList = map.get(form);
            } else {
                poemList = new ArrayList<Poem>();
            }
            poemList.add(poem);
            map.put(form, poemList);
        }
        return map;
    }

    /**
     * Returns a list of poems that contain a theme picked by the user. The list of
     * poems is then meant to be displayed in the console (titles are shown).
     * 
     * @param sc is user's input, represents the selection of a theme
     * @return a list of poems which contain the user's choice of theme.
     */
    public static List<Poem> searchByTheme(Scanner sc) {
        // Display message for user to pick a theme
        System.out.println("Please input the theme of the poems you would like to add to your anthology:");
        // Get the user's choice
        String selectedTheme = sc.nextLine();
        // Add all poems containing this theme to a list of poems to be returned
        List<Poem> poems = new ArrayList<>();
        for (String theme : themeMap.keySet()) {
            if (theme.equals(selectedTheme)) {
                poems.addAll(themeMap.get(theme));
            }
        }
        return poems;
    }

    /**
     * Searches the HashMap that maps poems to a specific poetic form and returns a
     * list of poems of a specific form.
     * 
     * @param sc
     * @return list of poems that match the form specified by the user.
     */
    public static List<Poem> searchByForm(Scanner sc) {
        System.out.println("Please input form:");
        String form = sc.nextLine();
        List<Poem> poems = new ArrayList<>();
        for (String key : formMap.keySet()) {
            if (key.toLowerCase().contains(form.toLowerCase())) {
                poems.addAll(formMap.get(key));
            }
        }
        return poems;
    }

    /**
     * Searches the HashMap that maps poems to a specific author and returns a list
     * of poems of that author
     * 
     * @param sc
     * @return list of poems that match the author specified by the user.
     */
    public static List<Poem> searchByAuthor(Scanner sc) {
        System.out.println("Please input author:");
        String author = sc.nextLine();
        List<Poem> poems = new ArrayList<>();
        for (String key : authorMap.keySet()) {
            if (key.toLowerCase().contains(author.toLowerCase())) {
                poems.addAll(authorMap.get(key));
            }
        }
        return poems;
    }

    public static List<Poem> searchByTitle(Scanner sc) {
        System.out.println("Please input poem title:");
        String title = sc.nextLine();
        List<Poem> poems = new ArrayList<>();
        for (String author : authorMap.keySet()) {
            for (Poem p : authorMap.get(author)) {
                if (p.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    poems.add(p);
                }
            }
        }
        return poems;
    }

    public static List<Poem> searchByPoemContent(Scanner sc) {
        System.out.println("Please input poem content:");
        String word = sc.nextLine();
        List<Poem> poems = new ArrayList<>();
        for (String author : authorMap.keySet()) {
            for (Poem p : authorMap.get(author)) {
                if (p.getTextString().toLowerCase().contains(word.toLowerCase())) {
                    poems.add(p);
                }
            }
        }

        return poems;
    }

    /**
     * Writes out passed in list of poems into the poem anthology text file.
     * 
     * @param poems
     */
    public static void write(List<Poem> poems) {
        // check if poem has already been written
        if (poems.isEmpty()) {
            System.out.println("No poems have been written to your anthology");
            System.out.println("-----------------------------------------------------------");
            System.out.println();
            // otherwise, write out poem
        } else {
            for (Poem poem : poems) {
                try {
                    FileWriter fw = new FileWriter(new File("poem_anthology.txt"), true);
                    fw.write("\n");
                    fw.write("-----------------" + index + "----------------\n");
                    fw.write(poem.toString() + "\n");
                    index++;
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /*
     * Getters and setters
     */

    public List<Poem> getAllPoems() {
        return this.allPoems;
    }

    // Abigail
    public HashMap<String, List<Poem>> getAuthorMap() {
        return authorMap;
    }

    public HashMap<String, List<Poem>> getThemeMap() {
        return themeMap;
    }

    public HashMap<String, List<Poem>> getFormMap() {
        return formMap;
    }

    public HashSet<Poem> getWrittenOutPoems() {
        return this.writtenOutPoems;
    }

}
