// --== CS400 Fall 2023 File Header Information ==--
// Name: Lilian Huang
// Email: llhuang@wisc.edu
// Group: C06
// TA: Binwei Yao
// Lecturer: Florian
// Notes to Grader: N/A

/**
 * The FrontendInterface defines methods for the user interface of the Song Searcher application.
 * It outlines functionalities for starting the main command loop, specifying and loading a data file,
 * listing songs based on danceability score, showing the average danceability score, and exiting the application.
 */
public interface FrontendInterface {

    /**
     * Starts the main command loop for the user.
     * Prompts the user to select a command, then requests any required details about that
     * command from the user, and then displays the results of the command.
     */
    public void start();

    /**
     * Specify and load a data file.
     *
     * @param filePath The path to the data file.
     */
    public void specifyAndLoadDataFile(String filePath);

    /**
     * List songs that have a specific danceability score.
     *
     * @param danceabilityScore The desired danceability score.
     */
    public void listSongsByDanceability(double danceabilityScore);

    /**
     * Show the average danceability score in the loaded dataset.
     *
     * @return The average danceability score.
     */
    public double showAverageDanceability();

    /**
     * Exit the application.
     */
    public void exit();
}

