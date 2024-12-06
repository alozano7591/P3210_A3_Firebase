package com.mobile2.p3210_a3.model;

public class FavMovieModel {

    // reusing everything for now, need to figure out what to actually keep
    String imdbID;
    String posterUrl;
    String title;
    String genre;
    String year;
    String imdbRating;
    String plot;

    public FavMovieModel(){

    }

    public FavMovieModel(String imdbID, String posterUrl, String title, String genre,
                         String year, String imdbRating, String plot) {
        this.imdbID = imdbID;
        this.posterUrl = posterUrl;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.imdbRating = imdbRating;
        this.plot = plot;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}

