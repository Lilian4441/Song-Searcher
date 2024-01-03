public class SongData implements SongDataInterface {
    private String artist;
    private String title;
    private int year;
    private String genre;
    private double danceabilityScore;

    public SongData(String artist, String title, int year, String genre, double danceabilityScore) {
        this.artist = artist;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.danceabilityScore = danceabilityScore;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public double getDanceabilityScore() {
        return danceabilityScore;
    }
}
