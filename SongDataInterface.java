public interface SongDataInterface{
/**
     * Retrieves the artist o the song.
     * 
     * Edge cases:
     * If the artist's name is unknown or not set throw NullpointerException
     * If the artist's name is out of the permitted length
     * 
     * 
     * @return the artist name as a string
     * @throws NullPointerException if the artist's name is not retrieved by any reason
     * @throws IllegalArgumentException if the artist's name is out of the permitted length
     */
    public String getArtist();
    
    /**
     * Retrieves the title of the song.
     * 
     * Edge cases:
     * If the title is unknown or not set, throw a NullPointerException.
     * If the title is out of the permitted length, throw an IllegalArgumentException.
     * 
     * 
     * @return the title of the song as a string.
     * @throws NullPointerException if the title is not set or unknown.
     * @throws IllegalArgumentException if the title is out of the permitted length.
     */
    public String getTitle();

    /**
     * Retrieves the released year of the song.
     * 
     * Edge cases:
     * If the year is set to a value that is not a valid release year (e.g., a year in the future or before the recording era), throw an IllegalArgumentException.
     * 
     * 
     * @return the released year of the song as an integer.
     * @throws IllegalArgumentException if the year is not a valid release year.
     */
    public int getYear();

    /**
     * Retrieves the genre of the song.
     * 
     * Edge cases:
     * If the genre is unknown or not set, throw a NullPointerException.
     * If the genre name is out of the permitted length or contains invalid characters, throw an IllegalArgumentException.
     * 
     * 
     * @return the genre of the song as a string.
     * @throws NullPointerException if the genre is not set or unknown.
     * @throws IllegalArgumentException if the genre name is out of the permitted length or contains invalid characters.
     */
    public String getGenre();

    /**
     * Retrieves the danceability score of the song.

     * Edge cases:
     *If the danceability score is not set, throw a NullPointerException.
     *If the score is out of a valid range (e.g., 0.0 to 100), throw an IllegalArgumentException.
     * 
     * @return the danceability score of the song as a double.
     * @throws NullPointerException if the score is not set.
     * @throws IllegalArgumentException if the score is out of a valid range.
     */
    public double getDanceabilityScore();
}
