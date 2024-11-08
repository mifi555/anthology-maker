package databaseFramework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Poem object class. Encapsulates the attributes of a poem
 * 
 * @author
 *
 */

public class Poem extends IPoem {

    private String author;
    private String title;
    private String text;
    private Set<String> themes;

    private String form;
    private int lineCount;
    private int stanzaCount;
    private List<Integer> stanzaLineCounts;
    private List<List<Integer>> syllableCountsPerLine;

    public Poem(String author, String title, String textString) {
        super();
        this.author = author;
        this.title = title;
        this.text = textString;
        this.form = findPoemForm(textString);
    }

    /*
     * Methods
     */

    /**
     * Method finds the poem's form. In the process, sets lineCount, stanzaCount,
     * standaLineCounts, as well as syllableCountsPerLine
     * 
     * @param poem
     * @return poetic form as a String
     */

    public String findPoemForm(String poem) {
        List<String> lines = Arrays.asList(poem.split("\\r?\\n"));
        List<Integer> stanzaLineCounts = new ArrayList<>();
        List<List<Integer>> syllableCountsPerLine = new ArrayList<>();
        List<Integer> currentStanzaSyllableCounts = new ArrayList<>();

        int lineCount = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                stanzaLineCounts.add(lineCount);
                syllableCountsPerLine.add(currentStanzaSyllableCounts);
                currentStanzaSyllableCounts = new ArrayList<>();
                lineCount = 0;
            } else {
                currentStanzaSyllableCounts.add(countSyllables(line));
                lineCount++;
            }

        }
        stanzaLineCounts.add(lineCount);
        syllableCountsPerLine.add(currentStanzaSyllableCounts);

        for (int i : stanzaLineCounts) {
            this.lineCount += i;
        }
        this.stanzaCount = stanzaLineCounts.size();

        this.stanzaLineCounts = stanzaLineCounts;
        this.syllableCountsPerLine = syllableCountsPerLine;

        switch (this.getLineCount()) {
        case 1:
            return "Monostich";
        case 2:
            return "Couplet";
        case 3:
            return "Tercet";
        case 4:
            return "Quatrain";
        case 5:
            return "Cinquain";
        case 6:
            return "Sestet";
        case 8:
            return "Octave";
        case 14:
            return findSonnetType(this.stanzaLineCounts);
        case 19:
            return isVilanelle(this.stanzaLineCounts);
        default:
            return "Free Verse";
        }

    }

    /**
     * This method is called in findPoemForm for a poem that has 19 lines to find
     * out if the poem is a Vilanelle or not.
     * 
     * @param stanzaLineCounts
     * @return Vilanelle type as a String (or Free Verse if Vilanelle criteria isn't
     *         satisfied)
     */
    private String isVilanelle(List<Integer> stanzaLineCounts) {
        List<Integer> vilanellePattern = Arrays.asList(3, 3, 3, 3, 3, 4);

        if (stanzaLineCounts.size() == 6) {
            if (stanzaLineCounts.equals(vilanellePattern)) {
                return "Vilanelle";
            }
        }
        return "Free Verse";
    }

    /**
     * This method is called in findPoemForm for a poem that has 14 lines to find
     * out as there are different types of sonnets (Shakespearean, Petrarchan,
     * etc.).
     * 
     * @param stanzaLineCounts
     * @return Sonnet type as a String (or Free Verse if Sonnet criteria isn't
     *         satisfied)
     */
    private String findSonnetType(List<Integer> stanzaLineCounts) {
        List<Integer> petrarchanSonnetPattern1 = Arrays.asList(8, 6);
        List<Integer> petrarchanSonnetPattern2 = Arrays.asList(4, 4, 3, 3);
        List<Integer> shakeSpeareanSonnetPattern = Arrays.asList(4, 4, 4, 2);

        if (stanzaLineCounts.size() == 2) {
            if (stanzaLineCounts.equals(petrarchanSonnetPattern1)) {
                return "Petrarchan Sonnet";
            }
        } else if (stanzaLineCounts.size() == 4) {
            if (stanzaLineCounts.equals(petrarchanSonnetPattern2)) {
                return "Sonnet";
            } else if (stanzaLineCounts.equals(shakeSpeareanSonnetPattern)) {
                return "Shakespearean Sonnet";
            }
        }
        // if above isn't satisfied, even though the poem may have 14 lines, it may not
        // be a sonnet. Instead, the poem is categorized as a "Free Verse".
        return "Free Verse";
    }

    /**
     * Counts the number of syllables in a line by calling countWordSyllables for
     * each word in a line.
     * 
     * @param line
     * @return syllable count in a line as an integer
     */
    private static int countSyllables(String line) {
        String[] words = line.split(" ");
        int syllableCount = 0;

        for (String word : words) {
            syllableCount += countWordSyllables(word);
        }

        return syllableCount;
    }

    /**
     * Checks if character is a vowel.
     * 
     * @param c
     * @return boolean
     */

    private static boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y';
    }

    /**
     * Simple syllable counting method that counts the number of syllables in a word
     * 
     * @param word
     * @return syllable count as an integer
     */
    private static int countWordSyllables(String word) {

        int count = 0;
        word = word.toLowerCase();

        if (word.length() == 0) {
            return 0;
        }

        if (isVowel(word.charAt(0)) || isVowel(word.charAt(0)) || isVowel(word.charAt(0)) || isVowel(word.charAt(0))
                || isVowel(word.charAt(0))) {
            count++;
        }

        for (int i = 1; i < word.length(); i++) {
            if (isVowel(word.charAt(i)) || isVowel(word.charAt(i)) || isVowel(word.charAt(i)) || isVowel(word.charAt(i))
                    || isVowel(word.charAt(i))) {
                if (!(word.charAt(i - 1) == 'a' || word.charAt(i - 1) == 'e' || word.charAt(i - 1) == 'i'
                        || word.charAt(i - 1) == 'o' || word.charAt(i - 1) == 'u')) {
                    count++;
                }
            }
        }

        if (word.charAt(word.length() - 1) == 'e') {
            count--;
        }
        return count;
    }

    @Override
    public String toString() {
        return "Author: " + author + "\nTitle: " + title + "\n" + "\n" + text;
    }

    /*
     * Getters and Setters.
     */

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getTextString() {
        return text;
    }

    public Set<String> getThemes() {
        return this.themes;
    }

    public String getForm() {
        return form;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getStanzaCount() {
        return stanzaCount;
    }

    public List<Integer> getStanzaLineCounts() {
        return stanzaLineCounts;
    }

    public List<List<Integer>> getSyllableCountsPerLine() {
        return syllableCountsPerLine;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextString(String textString) {
        this.text = textString;
    }

    public void setThemes(Set<String> themes) {
        this.themes = themes;
    }

}