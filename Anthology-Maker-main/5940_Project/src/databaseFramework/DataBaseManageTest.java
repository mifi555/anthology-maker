package databaseFramework;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for DataBaseManage class.
 *
 */
public class DataBaseManageTest {

    // Create new database of poems
    DataBaseManage db = new DataBaseManage();

    @Before
    public void setUp() throws Exception {
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for DataBaseManage()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testDataBaseManage() {
        // Check that the initial poems are correct

        // Poem 1
        String expectedTitle = "I Do Not Love You Except Because I Love You";
        assertEquals(expectedTitle, db.getAllPoems().get(0).getTitle());

        // Poem 2
        expectedTitle = "Love Sonnet XVII";
        assertEquals(expectedTitle, db.getAllPoems().get(1).getTitle());
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for createAuthorMap()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testCreateAuthorMap() {
        List<Poem> poems = CSVFileReader.readCSVFile("poem_data.csv");
        HashMap<String, List<Poem>> map = DataBaseManage.createAuthorMap(poems);
        assertTrue(map.size() > 0);
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for determineThemes()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testDetermineThemes() {
        // Check that the themes of the initial poems are correct

        // Poem 1: "I Do Not Love You Except Because I Love You" (should contain word
        // love in it)
        Set<String> expectedThemes = new HashSet<>();
        expectedThemes.add("love");
        assertEquals(expectedThemes, db.getAllPoems().get(0).getThemes());

        // Poem 2: "Love Sonnet XVII" (should contain word love and soul (deathWords))
        expectedThemes.add("death");
        assertEquals(expectedThemes, db.getAllPoems().get(1).getThemes());
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for createThemeMap()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testCreateThemeMap() {
        DataBaseManage dbm = new DataBaseManage();
//        List<Poem> poems = CSVFileReader.readCSVFile("poem_data.csv");
   
        HashMap<String, List<Poem>> map= dbm.getThemeMap();
        assertTrue(map.size() > 0);
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for createFormMap()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testCreateFormMap() {
    	List<Poem> poems = CSVFileReader.readCSVFile("poem_data.csv");
        HashMap<String, List<Poem>> map = DataBaseManage.createFormMap(poems);
        assertTrue(map.size() > 0);
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for searchByTheme()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testSearchByTheme() {
    	Scanner sc =new Scanner("love");
    	List<Poem> results=DataBaseManage.searchByTheme(sc);
    	assertTrue(results.size()>0);

    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for searchByForm()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testSearchByForm() {
        DataBaseManage dbm = new DataBaseManage();

        Scanner sc = new Scanner("Sonnet");
        List<Poem> results = DataBaseManage.searchByForm(sc);

        assertTrue(results.size() > 0);
        assertEquals(16, results.size());
        assertEquals(899, dbm.getAllPoems().size());
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for searchByAuthor()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testSearchByAuthor() {
        Scanner sc = new Scanner("Pablo Neruda");
        List<Poem> results = DataBaseManage.searchByAuthor(sc);
        assertTrue(results.size() > 0);
    }

    @Test
    public void testSearchByTitle() {
        Scanner sc = new Scanner("I Do Not Love You Except Because I Love You");
        List<Poem> results = DataBaseManage.searchByTitle(sc);
        assertTrue(results.size() > 0);
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for searchByPoemContent()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testSearchByPoemContent() {
        Scanner sc = new Scanner("ove you except because I love you");
        List<Poem> results = DataBaseManage.searchByPoemContent(sc);
        assertTrue(results.size() > 0);
    }

}
