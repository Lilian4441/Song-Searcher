// --== CS400 Fall 2023 File Header Information ==--
// Name: Lilian Huang
// Email: llhuang@wisc.edu
// Group: C06
// TA: Binwei Yao
// Lecturer: Florian
// Notes to Grader: main loop methods that involve backend (all methods except the exit method) are not functional due
//                  to bug in backend code (has been communicated already)

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Frontend class implements the FrontendInterface and provides the user interface
 * for interacting with the Song Searcher application. Users can specify and load a data
 * file, list songs based on danceability score, show the average danceability score, and exit the app.
 */
public class Frontend implements FrontendInterface {

    /**
     * The backend interface provides methods to interact with the application's data.
     */
    Backend backend;

    HuangBackendPlaceholderFrontend backendPlaceholder;
    /**
     * Scanner object to read user input from the console.
     */
    Scanner scanner;


    /**
     * Constructor to initialize Frontend with a given backend and scanner.
     *
     * @param backend The backend interface providing data interaction methods.
     * @param scanner Scanner object for reading user input.
     */
    public Frontend(Backend backend, Scanner scanner) {
        this.backend = backend;
        this.scanner = scanner;
    }

    /**
     * Constructor to initialize Frontend with a given backend placeholder and scanner
     *
     * @param backend The backend interface placeholder
     * @param scanner Scanner object for reading user input.
     */
    public Frontend(HuangBackendPlaceholderFrontend backend, Scanner scanner){
        this.backendPlaceholder = backend;
        this.scanner = scanner;
    }

    /**
     * Starts the application's user interface, allowing users to interact with the Song Searcher app.
     */
    @Override
    public void start() {
        // prints welcome statement
        System.out.println("Welcome to Song Searcher!");
        System.out.println("=========================");

        // prints out user's options with an instruction
        System.out.println("Please type the number corresponding to one of the following choices:");
        System.out.println("    1. specify and load a data file");
        System.out.println("    2. list all songs that have a specific danceability score");
        System.out.println("    3. show the average danceability score in the dataset");
        System.out.println("    4. exit the app");


        // while loop represents our command loop
        while (scanner.hasNext()) {
            // instantiates a string to hold user input
            String input = scanner.next();

            // checks if input is null
            if (input == null) {
                throw new NullPointerException("Input was null, please try again.");
            }

            // switch cases that call corresponding methods (according to user input)
            switch (input) {
                case "1": // specify and load data file
                    // prints statement asking user for file name
                    System.out.println("Please type in the name the file you would like to see: ");

                    // reads in user input for file name
                    String fileName = scanner.next();
                    scanner.nextLine(); // eats up newline character after reading file name

                    // checks if file name is valid (and not null)
                    if (fileName != null && fileName.equals("songs.csv")) {
                        specifyAndLoadDataFile(fileName);
                    } else {
                        System.out.print("File name not valid. ");
                    }
                    break;

                case "2": // lists all songs with a specific danceability number
                    // prints statement asking user for danceability number
                    System.out.println("Please type in the threshold danceability number: ");

                    // reads in user input for danceability number
                    double danceNum;

                    // checks that input is of valid type
                    if (scanner.hasNextDouble()){
                        danceNum = scanner.nextDouble();
                        scanner.nextLine(); // eats up newline character after reading the danceability number

                        // checks if danceability number is valid (between ranges)
                        if (danceNum <= 97 && danceNum >= 0){
                            listSongsByDanceability(danceNum);
                        }
                    }
                    break;

                case "3": // outputs average danceability
                    System.out.println(showAverageDanceability());
                    break;

                case "4": // exits application
                    exit();
                    break;

                default:
                    String invalidResponse = "Invalid input. ";
                    System.out.print(invalidResponse);
                    break;
            }
        }
    }

    /**
     * Specifies and loads a data file into the application.
     *
     * @param filePath The path to the data file to be loaded.
     */
    @Override
    public void specifyAndLoadDataFile(String filePath) {
        if (this.backend != null) {
            try {
                this.backend.readData(filePath); // uses backend's readData method to read in file
            } catch (IOException e){
                System.out.println("File path is empty. ");
            }
        } else {
            try{
                backendPlaceholder.readData(filePath);
            } catch (IOException e){

                System.out.println("File path is empty. ");
            }
        }

    }

    /**
     * Lists songs based on the given danceability score.
     *
     * @param danceabilityScore The danceability score threshold for listing songs.
     */
    @Override
    public void listSongsByDanceability(double danceabilityScore) {
        if (this.backend != null){
            // saves the return value of getSongsAboveDanceabilityThr method to a variable
            ArrayList<SongDataInterface> songObjects = this.backend.getSongsAboveDanceabilityThr(danceabilityScore);

            // converts objects to string (creates ArrayList of type String to store the converted objects)
            ArrayList<String> songsToString = new ArrayList<>();
            for (SongDataInterface obj : songObjects){
                if (obj.getDanceabilityScore() >= danceabilityScore) {
                    songsToString.add(obj.toString());
                }
            }

            // prints songs to terminal
            for (String songInfo : songsToString){
                System.out.println(songInfo);
            }
        } else {
            // saves the return value of getSongsAboveDanceabilityThr method to a variable
            ArrayList<SongDataInterface> songObjects = this.backendPlaceholder.getSongsAboveDanceabilityThr(danceabilityScore);

            // converts objects to string (creates ArrayList of type String to store the converted objects)
            ArrayList<String> songsToString = new ArrayList<>();
            for (SongDataInterface obj : songObjects){
                if (obj.getDanceabilityScore() >= danceabilityScore) {
                    songsToString.add(obj.toString());
                }
            }

            // prints songs to terminal
            for (String songInfo : songsToString){
                System.out.println(songInfo);
            }
        }
    }

    /**
     * Shows the average danceability score of songs in the dataset.
     *
     * @return The average danceability score.
     */
    @Override
    public double showAverageDanceability() {
        double avgDanceNum = 0;

        if (this.backend != null){
            // return value is saved
            avgDanceNum = this.backend.getAverageDanceability();
        } else {
            avgDanceNum = this.backendPlaceholder.getAverageDanceability();
        }

        return avgDanceNum;
    }

    /**
     * Exits the application.
     */
    @Override
    public void exit() {
        System.out.print("Thank you for using Song Searcher! See you next time.");
    }

    /**
     * Main method to run the Frontend class. Initializes backend and scanner, then starts the frontend.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Backend backend = new Backend();
        Scanner scanner = new Scanner(System.in);
        Frontend frontend = new Frontend(backend, scanner);
        frontend.start();
    }
}
