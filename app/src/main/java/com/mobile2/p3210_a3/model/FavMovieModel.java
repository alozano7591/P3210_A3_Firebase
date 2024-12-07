package com.mobile2.p3210_a3.model;

public class FavMovieModel {

    // reusing everything for now, need to figure out what to actually keep
    String imdbID;
    String posterUrl;
    String title;
    String year;
    String plot;

    public FavMovieModel(){

    }

    // listing all favourites uses this constructor
    public FavMovieModel(String posterUrl, String year, String imdbID, String title){
        this.posterUrl = posterUrl;
        this.year = year;
        this.imdbID = imdbID;
        this.title = title;
    }

    public FavMovieModel(String imdbID, String posterUrl, String title,
                         String year, String plot) {
        this.imdbID = imdbID;
        this.posterUrl = posterUrl;
        this.title = title;
        this.year = year;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}

