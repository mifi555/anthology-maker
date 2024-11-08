package databaseFramework;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for Poem class.
 *
 */
public class PoemTest {

    List<Poem> poems = CSVFileReader.readCSVFile("poem_data.csv");

    @Before
    public void setUp() throws Exception {
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for Poem()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testPoem() {
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for determineThemes()
     * ----------------------------------------------------------------------
     */

    @Test
    public void testdetermineThemes() {
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for findForm()
     * ----------------------------------------------------------------------
     */
    @Test
    public void testFindForm() {
        // I Do Not Love You Except Because I Love You
        Poem pabloNerudaPoem0 = poems.get(0);
        assertEquals("Pablo Neruda", pabloNerudaPoem0.getAuthor());
        assertEquals("I Do Not Love You Except Because I Love You", pabloNerudaPoem0.getTitle());
        assertEquals(4, pabloNerudaPoem0.getStanzaCount());
        assertEquals(Arrays.asList(4, 4, 3, 3), pabloNerudaPoem0.getStanzaLineCounts());
        assertEquals(14, pabloNerudaPoem0.getLineCount());
        assertEquals("Sonnet", pabloNerudaPoem0.getForm());

        // Love Sonnet XVII
        Poem pabloNerudaPoem1 = poems.get(1);
        assertEquals("Pablo Neruda", pabloNerudaPoem1.getAuthor());
        assertEquals("Love Sonnet XVII", pabloNerudaPoem1.getTitle());
        assertEquals(4, pabloNerudaPoem1.getStanzaCount());
        assertEquals(Arrays.asList(4, 4, 3, 3), pabloNerudaPoem1.getStanzaLineCounts());
        assertEquals(14, pabloNerudaPoem0.getLineCount());
        assertEquals("Sonnet", pabloNerudaPoem1.getForm());

        // If You Forget Me
        Poem pabloNerudaPoem2 = poems.get(2);
        assertEquals("Pablo Neruda", pabloNerudaPoem2.getAuthor());
        assertEquals("If You Forget Me", pabloNerudaPoem2.getTitle());
        assertEquals(6, pabloNerudaPoem2.getStanzaCount());
        assertEquals(Arrays.asList(2, 14, 3, 4, 12, 13), pabloNerudaPoem2.getStanzaLineCounts());
        assertEquals(48, pabloNerudaPoem2.getLineCount());
        assertEquals("Free Verse", pabloNerudaPoem2.getForm());

        // XVII (I do not love you...)
        Poem pabloNerudaPoem3 = poems.get(3);
        assertEquals("Pablo Neruda", pabloNerudaPoem3.getAuthor());
        assertEquals("XVII (I do not love you...)", pabloNerudaPoem3.getTitle());
        assertEquals(4, pabloNerudaPoem3.getStanzaCount());
        assertEquals(Arrays.asList(4, 4, 3, 3), pabloNerudaPoem3.getStanzaLineCounts());
        assertEquals(14, pabloNerudaPoem3.getLineCount());
        assertEquals("Sonnet", pabloNerudaPoem3.getForm());

        // Morning (Love Sonnet XXVII)
        Poem pabloNerudaPoem4 = poems.get(4);
        assertEquals("Pablo Neruda", pabloNerudaPoem4.getAuthor());
        assertEquals("Morning (Love Sonnet XXVII)", pabloNerudaPoem4.getTitle());
        assertEquals(4, pabloNerudaPoem4.getStanzaCount());
        assertEquals(Arrays.asList(4, 4, 3, 3), pabloNerudaPoem4.getStanzaLineCounts());
        assertEquals(14, pabloNerudaPoem4.getLineCount());
        assertEquals("Sonnet", pabloNerudaPoem4.getForm());

    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for isVilanelle()
     * ----------------------------------------------------------------------
     */
    public void isVilanelle() {
        String author = "Elizabeth Bishop";
        String title = "One Art";
        String text = "The art of losing isn't hard to master;" + "\nso many things seem filled with the intent"
                + "\nto be lost that their loss is no disaster." + "\n\nLose something every day. Accept the fluster"
                + "\nof lost door keys, the hour badly spent." + "\nThe art of losing isn't hard to master."
                + "\n\nThen practice losing farther, losing faster:" + "\nplaces, and names, and where it was you meant"
                + "\nto travel. None of these will bring disaster."
                + "\n\nI lost my mother's watch. And look! my last, or" + "\nnext-to-last, of three loved houses went."
                + "\nThe art of losing isn't hard to master." + "\n\nI lost two cities, lovely ones. And, vaster,"
                + "\nsome realms I owned, two rivers, a continent." + "\nI miss them, but it wasn't a disaster."
                + "\n\n--Even losing you (the joking voice, a gesture"
                + "\nI love) I shan't have lied. It's evident\nthe art of losing's not too hard to master"
                + "\nthough it may look like (Write it!) like disaster.";

        Poem vilanelle = new Poem(author, title, text);

        assertEquals("Vilanelle", vilanelle.getForm());
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for findSonnetType()
     * ----------------------------------------------------------------------
     */
    public void testFindSonnetType() {

        // Shakespearean Sonnet test
        String author = "Shakespeare";
        String title = "Made up Sonnet";
        String text = "\"Upon a starry night, I saw her face," + "\\nA beauty bright, that shone with endless grace,"
                + "\\nHer eyes like pools, reflecting heaven's light,"
                + "\\nHer smile, a sun, that banished all the night."
                + "\\n\\nWith every step, she drew me to her side," + "\\nAnd in her presence, all my fears did hide,"
                + "\\nShe spoke of love, with words so pure and true,"
                + "\\nThat in that moment, I knew what I must do." + "\\n\\nFor she had captured me, body and soul,"
                + "\\nAnd in her arms, I found my truest goal," + "\\nTo love and cherish her, until the end,"
                + "\\nAnd nevermore, to let my heart unbend." + "\\n\\nSo let the stars above, bear witness true,"
                + "\\nThat I, for her, would do all that I could do.\"";

        Poem shakespeareanSonnet = new Poem(author, title, text);

        assertEquals("Shakespearean Sonnet", shakespeareanSonnet.getForm());

        // Petrarchan Sonnet test
        String author2 = "Petrarch";
        String title2 = "Made up Petrarchan Sonnet";
        String text2 = "In silence, I do ponder and reflect," + "\nOn life and all its twists and turns unknown,"
                + "\nAnd yet, amidst the chaos and the wreck," + "\nMy heart finds peace, in thoughts of love alone."
                + "\n\nFor in your eyes, I see a shining light," + "\nThat guides me through the darkest of my days,"
                + "\nAnd in your arms, I find my sweet respite," + "\nFrom all life's trials and its weary ways."
                + "\n\nOh, how your touch can soothe my troubled mind,"
                + "\nAnd bring me back to joy and hope and grace," + "\nFor in your love, a solace I do find,"
                + "\nThat makes my heart sing out in sweet embrace." + "\n\nSo let this love, forever be enshrined,"
                + "\nA precious treasure, in our sacred space.";

        Poem petrarchanSonnet = new Poem(author2, title2, text2);

        assertEquals("Petrarchan Sonnet", petrarchanSonnet.getForm());
    }

    /*
     * ---------------------------------------------------------------------- 
     * Tests
     * for countSyllables()
     * ----------------------------------------------------------------------
     */
    @Test
    public void testCountSyllables() {

        String author = "Author";
        String title = "Title";
        String text = "Upon a starry night, I saw her face" + "\n Goodbye in death" + "\n\n My love";

        Poem poemExample = new Poem(author, title, text);

        assertEquals(Arrays.asList(Arrays.asList(10, 4), Arrays.asList(2)), poemExample.getSyllableCountsPerLine());
    }
}
