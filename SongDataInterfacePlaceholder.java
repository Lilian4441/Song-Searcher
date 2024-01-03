// --== CS400 Fall 2023 File Header Information ==--
// Name: Lilian Huang
// Email: llhuang@wisc.edu
// Group: C06
// TA: Binwei Yao
// Lecturer: Florian
// Notes to Grader: N/A

/**
 * The SongDataInterfacePlaceholder class implements the SongDataInterface interface and represents a placeholder
 * implementation of the SongDataInterface. It stores information about a song, including artist, title, released year,
 * genre, and danceability score.
 */
public class SongDataInterfacePlaceholder implements SongDataInterface {

    String artist;
    String title;
    int year;
    String genre;
    double danceabilityScore;

    /**
     * Constructs a SongDataInterfacePlaceholder object with the provided information.
     *
     * @param artist            The artist of the song.
     * @param title             The title of the song.
     * @param year              The released year of the song.
     * @param genre             The genre of the song.
     * @param DanceabilityScore The danceability score of the song.
     */
    public SongDataInterfacePlaceholder(String artist, String title, int year, String genre, double DanceabilityScore) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.danceabilityScore = DanceabilityScore;
    }

    /**
     * Retrieves the artist of the song.
     *
     * @return The artist's name as a string.
     */
    @Override
    public String getArtist() {
        return this.artist;
    }

    /**
     * Retrieves the title of the song.
     *
     * @return The title of the song as a string.
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Retrieves the released year of the song.
     *
     * @return The released year of the song as an integer.
     */
    @Override
    public int getYear() {
        return this.year;
    }

    /**
     * Retrieves the genre of the song.
     *
     * @return The genre of the song as a string.
     */
    @Override
    public String getGenre() {
        return this.genre;
    }

    /**
     * Retrieves the danceability score of the song.
     *
     * @return The danceability score of the song as a double.
     */
    @Override
    public double getDanceabilityScore() {
        return this.danceabilityScore;
    }

    /**
     * Returns a string representation of the song data.
     *
     * @return A string containing artist, title, released year, genre, and danceability score of the song.
     */
    @Override
    public String toString() {
        return "artist: " + this.artist + ", title: " + this.title + ", year released: " + this.year +
                ", genre: " + this.genre + ", danceability score: " + this.danceabilityScore;
    }
}
