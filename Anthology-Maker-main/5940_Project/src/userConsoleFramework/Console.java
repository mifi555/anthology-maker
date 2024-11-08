package userConsoleFramework;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import databaseFramework.DataBaseManage;
import databaseFramework.Poem;

/**
 * Console class allows user to choose what poems they would like to add to
 * their anthology by interacting with a console-based interface.
 * 
 * @author
 *
 */

public class Console {

    // stores poems that have been written out to the user's anthology
    private static HashSet<Poem> userAnthology = new HashSet<Poem>();

    public static void main(String[] args) {
        // takes in user input
        // consider invalid input
        DataBaseManage db = new DataBaseManage();

        // ensures that the previous poetry anthology is cleared when creating a new one
        try {
            FileWriter fw = new FileWriter(new File("poem_anthology.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            menu();
            List<Poem> poemListSelection = new ArrayList<Poem>();
            int choice = sc.nextInt();
            sc.nextLine();

            // search poems by author name
            if (choice == 1) {
                poemListSelection = DataBaseManage.searchByAuthor(sc);
                // if no poems match the criterion
                if (poemListSelection.isEmpty()) {
                    System.out.println("This author does not exist.");
                } else {
                    // if any poem matches criterion, it is displayed to the user via the
                    // displayPoemSelection method
                    // user then chooses which poems they would like to add to the anthology
                    List<Poem> poemListToWriteOut = displayPoemSelection(poemListSelection);
                    // write out any poems that user adds to the anthology if they're not in the
                    // anthology already
                    DataBaseManage.write(poemListToWriteOut);
                }
                // search poems by title
            } else if (choice == 2) {
                poemListSelection = DataBaseManage.searchByTitle(sc);

                if (poemListSelection.isEmpty()) {
                    System.out.println("This title does not exist.");
                } else {
                    List<Poem> poemListToWriteOut = displayPoemSelection(poemListSelection);
                    DataBaseManage.write(poemListToWriteOut);
                }
                // search poems by content
            } else if (choice == 3) {
                poemListSelection = DataBaseManage.searchByPoemContent(sc);

                if (poemListSelection.isEmpty()) {
                    System.out.println("This content does not exist.");
                } else {
                    List<Poem> poemListToWriteOut = displayPoemSelection(poemListSelection);
                    DataBaseManage.write(poemListToWriteOut);
                }
                // search poems by theme
            } else if (choice == 4) {

                poemListSelection = db.searchByTheme(sc);
                if (poemListSelection.isEmpty()) {
                    System.out.println("This theme does not exist.");
                } else {
                    List<Poem> poemListToWriteOut = displayPoemSelection(poemListSelection);
                    DataBaseManage.write(poemListToWriteOut);
                }
                // search poems by form
            } else if (choice == 5) {
                poemListSelection = db.searchByForm(sc);

                if (poemListSelection.isEmpty()) {
                    System.out.println("This form does not exist.");
                } else {
                    List<Poem> poemListToWriteOut = displayPoemSelection(poemListSelection);
                    DataBaseManage.write(poemListToWriteOut);
                }
            } else if (choice == 6) {
                System.out.println("Exiting Anthology Maker...");
                break;
            }
        }
        sc.close();
    }

    /**
     * Method that prints out menu options for the user to interact with.
     */

    public static void menu() {
        System.out.println("Please input 1-4:");
        System.out.println("1 - Search poems by author");
        System.out.println("2 - Search poems by title");
        System.out.println("3 - Search poems by content");
        System.out.println("4 - Search poems by theme");
        System.out.println("5 - Search poems by poetic form");
        System.out.println("6 - Exit");
    }

    /**
     * Display Poem Selection takes in a list of poems that is returned from
     * searching through one of the HashMaps. The poems are displayed to the user in
     * the console. The user can select which poems they would like to add to their
     * anthology. These poems are returned by the method.
     * 
     * @param poemListSelection
     * @return list of poems that will be written out to the poem anthology text
     *         file upon selection by the user.
     */

    public static List<Poem> displayPoemSelection(List<Poem> poemListSelection) {

        Scanner sc = new Scanner(System.in);

        List<Poem> poemListToWriteOut = new ArrayList<Poem>();

        while (true) {
            System.out.println("---------- RESULTS ----------");
            // print out list of poems that has been returned by one of the search functions
            for (Poem poem : poemListSelection) {
                System.out.println("[" + (poemListSelection.indexOf(poem) + 1) + "]" + "\"" + poem.getTitle()
                        + "\", by " + poem.getAuthor());
            }

            // prompt user to choose which poem they would like to view a particular poem
            System.out.println();
            System.out.println("Please input a number between 1-" + poemListSelection.size()
                    + " to view a particular poem. Press 0 to return to the main menu.");

            int choice = sc.nextInt();
            sc.nextLine();

            // if valid index, print out poem onto the console
            if (choice > 0 && choice <= poemListSelection.size()) {

                System.out.println("-----------------------------------------------------------");

                System.out.println("\"" + poemListSelection.get(choice - 1).getTitle() + "\", by "
                        + poemListSelection.get(choice - 1).getAuthor());
                System.out.println("\n" + poemListSelection.get(choice - 1).getTextString() + "\n");

                System.out.println("-----------------------------------------------------------");

                // prompt user to add poem to their anthology
                System.out.println("Would you like you to add this poem to your anthology?");

                String response = sc.nextLine().toLowerCase();

                // if user chooses to add poem to the anthology and that poem hasn't been added
                // to the anthology yet,
                // the poem is add to a list of poems to be written out to the anthology
                // back in the main method
                if ("yes".equals(response)) {
                    if (!userAnthology.contains(poemListSelection.get(choice - 1))) {
                        // add poem to poems to be written out
                        poemListToWriteOut.add(poemListSelection.get(choice - 1));
                        userAnthology.add(poemListSelection.get(choice - 1));
                        System.out.println("Poem added to the new list.");

                    } else {
                        System.out.println("This poem is already in your anthology");

                    }

                } else {
                    System.out.println("Poem was not added to your anthology.");
                    System.out.println();
                }
                // otherwise, return back to main menu
            } else {
                System.out.println("Returning back to main menu...");
                System.out.println();
                break;
            }
        }
        // return list of poems that user selected to add to their anthology
        return poemListToWriteOut;
    }
}
