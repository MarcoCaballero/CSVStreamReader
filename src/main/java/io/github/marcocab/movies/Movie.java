package io.github.marcocab.movies;

import java.util.stream.Stream;

import org.json.simple.JSONArray;

public class Movie {
    private static final int YEAR_POS = 2;

    private long budget;
    private JSONArray genres;
    private JSONArray keywords;
    private String language;
    private String title;
    private double popularity;
    private JSONArray productionCompanies;
    private String releaseDate;
    private long revenue;
    private String status;
    private double voteAverage;
    private int voteCount;

    public Movie(long budget, JSONArray genres, JSONArray keywords, String language, String title,
	    double popularity, JSONArray companies, String releaseDate, long revenue, String status,
	    double voteAverage, int voteCount) {
	this.budget = budget;
	this.genres = genres;
	this.keywords = keywords;
	this.language = language;
	this.title = title;
	this.popularity = popularity;
	this.productionCompanies = companies;
	this.releaseDate = releaseDate;
	this.revenue = revenue;
	this.status = status;
	this.voteAverage = voteAverage;
	this.voteCount = voteCount;
    }

    public long getBudget() {
	return budget;
    }

    public JSONArray getGenres() {
	return genres;
    }

    public JSONArray getKeywords() {
	return keywords;
    }

    public String getLanguage() {
	return language;
    }

    public String getTitle() {
	return title;
    }

    public double getPopularity() {
	return popularity;
    }

    public JSONArray getCompanies() {
	return productionCompanies;
    }

    public String getReleaseDate() {
	return releaseDate;
    }

    public long getRevenue() {
	return revenue;
    }

    public String getStatus() {
	return status;
    }

    public double getVoteAverage() {
	return voteAverage;
    }

    public int getVoteCount() {
	return voteCount;
    }

    public int getYear() {
	if (releaseDate.split("/").length > 2) {
	    return Integer.valueOf(releaseDate.split("/")[YEAR_POS]);
	} else {
	    return 0;
	}
    }

    public boolean isBugdetInRange(long lowBound, long highBound) {
	return lowBound <= budget && budget <= highBound;
    }

    public boolean isVoteAverageInRange(double lowBound, double highBound) {
	return lowBound <= voteAverage && voteAverage <= highBound;
    }

    public Stream<?> getDistinctCompanies() {
	return this.getCompanies().parallelStream();
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder("Movie: '" + title + "' \n");
	sb.append("Budget: " + budget + "\n");
	sb.append("Genres: " + JsonUtils.printAsJsonArray(genres) + "\n");
	sb.append("Keywords: " + JsonUtils.printAsJsonArray(keywords) + "\n");
	sb.append("Language: " + language + "\n");
	sb.append("Popularity: " + popularity + "\n");
	sb.append("Companies: " + JsonUtils.printAsJsonArray(productionCompanies) + "\n");
	sb.append("ReleaseDate: " + releaseDate + "\n");
	sb.append("Revenue: " + revenue + "\n");
	sb.append("Status: " + status + "\n");
	sb.append("VoteAverage: " + voteAverage + "\n");
	sb.append("VoteCount: " + voteCount + "\n\n");
	return sb.toString();
    }

}
