/**
 * Interface for Poem.
 */

package databaseFramework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class IPoem {

    // The data structure that maps themes to words, helping us to determine the
    // themes of poems
    private Map<String, HashSet<String>> themesToWords = new HashMap<>();

    // Constant array of themes. Note: 'other' should be last element
    private static String[] themesArray = { "love", "death", "joy", "sadness", "war", "other" };

    /*
     * Build HashSets for generic themes
     */

    // Love
    private static String[] loveW = { "adoration", "affection", "allure", "alluring", "amour", "ardor", "attachment",
            "attraction", "beloved", "caring", "cherish", "compassion", "courtship", "crush", "darling", "deeply",
            "desire", "desires", "devotion", "embrace", "enchantment", "eros", "fidelity", "flame", "fondness",
            "heartfelt", "hearts", "hugs", "infatuation", "intimacy", "kisses", "longing", "love", "lovers", "lovesick",
            "lovingly", "loyalty", "lust", "lustful", "partnership", "passion", "relationship", "romance", "sensuality",
            "sentiment", "soulmates", "sweetheart", "swoon", "tenderness", "togetherness", "true love", "union",
            "warmth", "yearning" };
    private static HashSet<String> loveWords = new HashSet<>(Arrays.asList(loveW));

    // Death
    private static String[] deathW = { "afterlife", "bereavement", "beyond", "burial", "cemetery", "crypt", "darkness",
            "deceased", "demise", "departed", "elegy", "entropy", "ephemeral", "eulogy", "eternal", "extinguish",
            "farewell", "finality", "funeral", "ghost", "goodbye", "grave", "grief", "grim reaper", "heaven", "hell",
            "hereafter", "immortality", "inevitable", "lament", "lifeless", "loss", "memory", "mortality", "mourn",
            "mourning", "obituary", "passing", "requiem", "rest", "reincarnation", "respite", "shroud", "silence",
            "sleep", "soul", "spirit", "tomb", "transient", "transition", "void" };
    private static HashSet<String> deathWords = new HashSet<>(Arrays.asList(deathW));

    // Joy
    private static String[] joyW = { "bliss", "celebration", "cheer", "contentment", "delight", "ecstasy", "elation",
            "euphoria", "exhilaration", "exuberance", "festivity", "fulfillment", "gaiety", "glee", "gratification",
            "happiness", "harmony", "jubilation", "laughter", "lightness", "merriment", "optimism", "overjoyed",
            "peace", "pleasure", "radiance", "rapture", "rejoicing", "satisfaction", "serenity", "smiles", "success",
            "thrill", "triumph", "uplifted", "victory", "warmth", "well-being", "wholeness", "wonder", "zest" };
    private static HashSet<String> joyWords = new HashSet<>(Arrays.asList(joyW));

    // Sadness
    private static String[] sadnessW = { "anguish", "bleak", "blue", "despair", "desolation", "disappointment",
            "discontent", "distress", "dolor", "forlorn", "gloom", "grief", "heartache", "heaviness", "hopelessness",
            "lament", "loneliness", "longing", "loss", "melancholy", "misery", "mournful", "pain", "regret", "remorse",
            "sigh", "sob", "sorrow", "suffering", "tears", "torment", "tragedy", "unhappiness", "wistful", "woe",
            "woeful" };
    private static HashSet<String> sadnessWords = new HashSet<>(Arrays.asList(sadnessW));

    // War
    private static String[] warW = { "aggression", "alliance", "ambush", "annihilation", "armistice", "armor",
            "arsenal", "artillery", "atrocity", "barrage", "battles", "bloodshed", "bombardment", "bravery",
            "casualties", "combat", "conflict", "conquest", "destruction", "fortress", "frontline", "heroism",
            "hostilities", "invasion", "martial", "military", "munition", "occupation", "peace", "plunder", "prisoners",
            "rebellion", "resistance", "siege", "skirmish", "soldiers", "strategy", "surrender", "tactics", "truce",
            "trenches", "troops", "valor", "victory", "violence", "warfare", "warriors", "weapons" };
    private static HashSet<String> warWords = new HashSet<>(Arrays.asList(warW));

    /*
     * Getters and setters
     */

    public Map<String, HashSet<String>> getThemesToWords() {
        return this.themesToWords;
    }

    public void setThemesToWords(Map<String, HashSet<String>> themesToWords) {
        // Construct the Map of main themes to words (we will only ever have 5)
        themesToWords.put(themesArray[0], loveWords);
        themesToWords.put(themesArray[1], deathWords);
        themesToWords.put(themesArray[2], joyWords);
        themesToWords.put(themesArray[3], sadnessWords);
        themesToWords.put(themesArray[4], warWords);
    }

    public String[] getThemesArray() {
        return themesArray;
    }

    public HashSet<String> getLoveWords() {
        return loveWords;
    }

    public HashSet<String> getDeathWords() {
        return deathWords;
    }

    public HashSet<String> getJoyWords() {
        return joyWords;
    }

    public HashSet<String> getSadnessWords() {
        return sadnessWords;
    }

    public HashSet<String> getWarWords() {
        return warWords;
    }
}
