package com.mobile2.p3210_a3.model;

public class MovieModel {
    String imdbID;
    String posterUrl;
    String title;                   // title of the movie
    String genre;                  // was studio but studio no longer exists, replaced with genre
    String year;                    //year the movie was release
    String imdbRating;              // rating from IMDb

    public MovieModel(){
    }

    public MovieModel(String imdbID, String title, String year, String posterUrl){
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.posterUrl = posterUrl;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String movieImgUrl) {
        this.posterUrl = movieImgUrl;
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

    public void setGenre(String studio) {
        this.genre = studio;
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
}
