package com.mobile2.p3210_a3.model;

public class FavMovieModel {

    // reusing everything for now, need to figure out what to actually keep
    String docID;
    String imdbID;
    String posterUrl;
    String title;
    String year;
    String plot;

    public FavMovieModel(){

    }

    // used when adding a favourite to FireStore db
    public FavMovieModel(String imdbID, String posterUrl, String title, String year, String plot){
        this.posterUrl = posterUrl;
        this.year = year;
        this.imdbID = imdbID;
        this.title = title;
        this.plot = plot;
    }

    // used when getting a favourite FROM FireStore db and adding to list. Firestore docID is VERY important
    public FavMovieModel(String docID, String imdbID, String posterUrl, String title,
                         String year, String plot) {
        this.docID = docID;
        this.imdbID = imdbID;
        this.posterUrl = posterUrl;
        this.title = title;
        this.year = year;
        this.plot = plot;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
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

