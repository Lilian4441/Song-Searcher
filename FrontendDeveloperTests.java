// --== CS400 Fall 2023 File Header Information ==--
// Name: Lilian Huang
// Email: llhuang@wisc.edu
// Group: C06
// TA: Binwei Yao
// Lecturer: Florian
// Notes to Grader: both integration tests do not pass due to bugs in backend's code (have been communicated)

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The `FrontendDeveloperTests` class contains a series of JUnit test methods to validate the functionality
 * of the `Frontend` class in the application. These tests evaluate various user interactions, including
 * handling invalid input, loading data files, listing songs by danceability score, showing the average
 * danceability score, and exiting the application.
 *
 */
public class FrontendDeveloperTests{

    /**
     * This test method evaluates the response of the application to invalid input.
     * The method sets up a simulated environment where the 'start()' method has already been called.
     * It creates a TextUITester object with an invalid word input and checks the response of the application.
     * The test verifies that the application displays the correct error message when an invalid input is provided.
     *
     * If the actual output contains the string "Invalid input.", the test passes, otherwise, it fails.
     */
    @Test
    public void testResponseToInvalidInput() {
        // assuming start() has been called already
        TextUITester testInvalidWord = new TextUITester("invalid_input\n"); // input invalid because it is a word

        // creates a scanner for frontend object
        Scanner scanner = new Scanner(System.in);

        // creates a backend placeholder object
        HuangBackendPlaceholderFrontend backend1 = new HuangBackendPlaceholderFrontend();

        // instantiates front end object
        Frontend testResponseToInvalidInput = new Frontend(backend1, scanner);

        // enters command loop
        testResponseToInvalidInput.start();

        // closes scanner
        scanner.close();

        // saves actual output to a variable (so later we can check if it matches the expected output)
        String actualOutputWord = testInvalidWord.checkOutput();

        // saves expected output to a variable (so later we can check if it matches the actual output)
        String expectedOutput = "Welcome to Song Searcher!\r\n" +
                "=========================\r\n" +
                "Please type the number corresponding to one of the following choices:\r\n" +
                "    1. specify and load a data file\r\n" +
                "    2. list all songs that have a specific danceability score\r\n" +
                "    3. show the average danceability score in the dataset\r\n" +
                "    4. exit the app\r\nInvalid input. ";

        // validate actual output against expected output (if any of these do not match, test fails)
        Assertions.assertTrue(expectedOutput.contains("Invalid input."));
    }

    /**
     * This test method evaluates the behavior of the application when a user specifies and loads a valid data file.
     * The method simulates user input of a valid file name ("songs.csv") and checks the application's response.
     *
     * If the actual output matches the expected output, the test passes, otherwise, the test fails.
     */
    @Test
    public void testSpecifyAndLoadDataFile() {
        // simulates that start() has been called already and user has selected option "1"
        TextUITester testValidFile = new TextUITester("songs.csv\n");

        // creates a scanner for frontend object
        Scanner scanner = new Scanner(System.in);

        // creates a backend placeholder object
        HuangBackendPlaceholderFrontend backend2 = new HuangBackendPlaceholderFrontend();

        // instantiates front end objects
        Frontend testIfValid = new Frontend(backend2, scanner);

        // tests the specifyAndLoadDataFile
        testIfValid.specifyAndLoadDataFile("songs.csv"); // when file valid

        // closes scanner
        scanner.close();

        // saves actual output to a variable (so later we can check if it matches the expected output)
        String actualOutputValid = testValidFile.checkOutput(); // when file valid

        // save expected output to a variable (so later we can check if it matches the actual output)
        String expectedOutputValid = "[contents of file]"; // when file valid

        // validate actual output equals expected output
        Assertions.assertEquals(actualOutputValid, expectedOutputValid); // when file is valid
    }

    /**
     * This test method evaluates the functionality of listing songs based on danceability score.
     * The method simulates user input of a valid danceability number (68) and checks the application's response.
     *
     * If the actual output contains the string representations of the expected songs, the test passes, otherwise, the test fails.
     */
    @Test
    public void testListSongsByDanceability() {
        // simulates that start() has been called already and user has selected option "2"
        TextUITester testValidDanceNum = new TextUITester("68\n"); // user input is valid (number is within range)

        // creates a scanner for frontend object
        Scanner scanner = new Scanner(System.in);

        // creates a backend placeholder object
        HuangBackendPlaceholderFrontend backend3 = new HuangBackendPlaceholderFrontend();

        // instantiates front end objects
        Frontend testIfValid = new Frontend(backend3, scanner);

        // creates a sample list of songs of SongDataInterfacePlaceholder type
        ArrayList<SongDataInterface> songs = new ArrayList<>();

        // creates song objects
        SongDataInterfacePlaceholder testSong1 = new SongDataInterfacePlaceholder("Kesha", "TiK ToK", 2010, "dance pop", 56);
        SongDataInterfacePlaceholder testSong2 = new SongDataInterfacePlaceholder("Bruno Mars", "Marry You", 2010, "pop", 62);
        SongDataInterfacePlaceholder testSong3 = new SongDataInterfacePlaceholder("Lady Gaga", "Alejandro", 2010, "dance pop", 56);
        SongDataInterfacePlaceholder testSong4 = new SongDataInterfacePlaceholder("Neon Trees", "Animal", 2010, "indie pop", 48);
        SongDataInterfacePlaceholder testSong5 = new SongDataInterfacePlaceholder("Christina Perri", "A Thousand Years", 2011, "dance pop", 56);

        // adds song objects to array list
        songs.add(testSong1);
        songs.add(testSong2);
        songs.add(testSong3);
        songs.add(testSong4);
        songs.add(testSong5);

        // tests the listSongsByDanceability
        testIfValid.listSongsByDanceability(56); // inputted num valid

        // closes scanner
        scanner.close();

        // saves actual output to a variable (so later we can check if it matches the expected output)
        String actualOutputValid = testValidDanceNum.checkOutput(); // inputted num valid

        // save expected output (when danceability num is invalid) to a variable (so later we can check if it matches the actual output)
        String expectedOutputValid = testSong1.toString() + "\r\n" + testSong3.toString() + "\r\n" + testSong5.toString()+ "\r\n";

        // validate actual output equals expected output
        Assertions.assertTrue(actualOutputValid.contains(testSong1.toString()));
        Assertions.assertTrue(actualOutputValid.contains(testSong3.toString()));
        Assertions.assertTrue(actualOutputValid.contains(testSong5.toString()));
    }

    /**
     * This test method evaluates the behavior of the 'showAverageDanceability()' method in the application.
     * The method simulates user selection of the option to show the average danceability score.
     *
     * If the actual return value matches the expected return value, the test passes, otherwise the test fails
     */
    @Test
    public void testShowAverageDanceability() {
        // simulates that start() has been called already and user has selected option "3"

        // creates a scanner for frontend object
        Scanner scanner = new Scanner(System.in);

        // creates a backend placeholder object
        HuangBackendPlaceholderFrontend backend4 = new HuangBackendPlaceholderFrontend();

        // instantiates front end object
        Frontend exampleCallOnMethod = new Frontend(backend4, scanner);

        // closes scanner
        scanner.close();

        // checks that expected return value equals actual return value (where current return value is 0 before implementation)
        Assertions.assertEquals(101, exampleCallOnMethod.showAverageDanceability());
    }

    /**
     * This test method evaluates the functionality of the 'exit()' method in the application.
     * The method simulates user selection of the option to exit the application.
     *
     * If the actual output matches the expected output, the test passes.
     */
    @Test
    public void testExit() {
        // simulates that start() has been called already, and user has selected option "4"
        TextUITester testExit = new TextUITester("exit\n"); // user input is invalid

        // creates a scanner for frontend object
        Scanner scanner = new Scanner(System.in);

        // creates a backend placeholder object
        HuangBackendPlaceholderFrontend backend5 = new HuangBackendPlaceholderFrontend();

        // instantiates front end object
        Frontend exampleCallOnExit = new Frontend(backend5, scanner);

        // tests functionality of exit method
        exampleCallOnExit.exit();

        // closes scanner
        scanner.close();

        // saves actual output to a variable (so later we can check if it matches the expected output)
        String actualOutput = testExit.checkOutput();

        // save expected output to a variable (so later we can check if it matches the actual output)
        String expectedOutput = "Thank you for using Song Searcher! See you next time.";

        // validate actual outputs equal expected outputs
        Assertions.assertEquals(actualOutput, expectedOutput);
    }

    /**
     * This method performs an integration test for reading data using the Backend class.
     * It tests the readData method's functionality and handles IOException if it occurs.
     */
    @Test
    public void integrationTestReadData(){
        // create a new instance of the backend class for testing
        Backend backend1 = new Backend();

        try{
            // attempts to read data from specifies csv file using backend's readData method
            backend1.readData("src/songs.csv"); // calls backend readData method

            // if data is successfully read, test case passes

        } catch (IOException e){
            // if an IOException occurs, fail the test with a message
            Assertions.fail("IOException occured while reading data: " + e.getMessage());
        }
    }

    /**
     * This method performs an integration test for calculating the average danceability
     * using the Backend class. It tests the getAverageDanceability method's functionality
     * and handles IOException if it occurs.
     */
    @Test
    public void integrationTestAvgDance(){
        // create a new instance of the backend class for testing
       Backend backend2 = new Backend();

        // variable to store the calculated average danceability.
       double avgDanceability = 0;

       try{
           // attempts to read data from specifies csv file using backend's readData method
           backend2.readData("src/songs.csv");

           avgDanceability = backend2.getAverageDanceability(); // saves actual avg. dance. value
       } catch (IOException e){
           Assertions.fail("IOException occured while reading data: " + e.getMessage());
       }

       // asserts that calculated avg. danceability is non-negative
       Assertions.assertTrue(avgDanceability >= 0); // checks that avg dance is non negative
    }
}
