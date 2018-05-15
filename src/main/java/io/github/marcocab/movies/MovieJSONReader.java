package io.github.marcocab.movies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MovieJSONReader {
    private static final String COLUMN_SEPARATOR = ";";
    private static final int COLUMN_LENGTH = 12;
    private List<Movie> movies;

    public MovieJSONReader(String path) {
	readDatabase(path);
    }

    public void printAll() {
	movies.forEach(System.out::println);
    }

    public void printAllMoviesByBudgetRange(long lowBound, long highBound) {
	movies.parallelStream()
		.filter(movie -> movie.isBugdetInRange(lowBound, highBound))
		.forEach(movie -> System.out.println("title: " + movie.getTitle()));
    }

    @SuppressWarnings("unchecked")
    public void printAllMoviesGenresSet(Set<String> genres) {
	movies.parallelStream()
		.filter(movie -> isGenreNameIncluded((Movie) movie, genres))
		.forEach(movie -> {
		    System.out.println("Title => " + movie.getTitle());
		    System.out.println("Genres: ");
		    movie.getGenres()
			    .parallelStream()
			    .forEach(obj -> {
				JSONObject jsonObj = (JSONObject) obj;
				System.out.println("  Genre: " + jsonObj.get("name"));
			    });
		});
    }

    public void printMaximumRevenueWithThisKeyword(String keyword) {
	Optional<Long> max = movies.parallelStream()
		.filter(movie -> isKeywordNameIncluded((Movie) movie, keyword))
		.map(Movie::getRevenue)
		.reduce(Math::max);

	System.out.println((max.isPresent())
		? "Max revenue including the keyword: (" + keyword + "): " + max.get()
		: "There are no movies including the keyword: (" + keyword + "): ");
    }

    public List<Movie> printMoviesByLanguageWithAlmostPopularityAt(String language, double popularity) {
	return movies.parallelStream()
		.filter(movie -> movie.getLanguage().equals(language))
		.filter(movie -> movie.getPopularity() >= popularity)
		.collect(Collectors.toList());
    }

    public long getTotalRevenueByYear(int year) {
	return movies.stream()
		.filter(movie -> movie.getYear() == year)
		.mapToLong(Movie::getRevenue)
		.sum();
    }

    public int getAllVotesCountbyAverageVoteRange(double lowBound, double highBound) {
	return movies.parallelStream()
		.filter(movie -> movie.isVoteAverageInRange(lowBound, highBound))
		.mapToInt(Movie::getVoteCount)
		.sum();
    }

    @SuppressWarnings("unchecked")
    public Map<String, Set<String>> getProductionCompaniesTitlesMap() {
	return (Map<String, Set<String>>) movies
		.stream()
		.flatMap(m -> m.getCompanies()
			.stream()
			.map(obj -> {
			    JSONObject jsonObj = (JSONObject) obj;
			    return new FlattenendEntry<String, String>(jsonObj.get("name").toString(), m.getTitle());
			}))
		.filter(m -> m instanceof FlattenendEntry) // Just to assert
		.collect(Collectors.groupingBy(m -> ((FlattenendEntry<String, String>) m).getKey(),
			Collectors.mapping(m -> ((FlattenendEntry<String, String>) m).getValue(), Collectors.toSet())));
    }

    @SuppressWarnings("unchecked")
    private boolean isGenreNameIncluded(Movie movie, Set<String> genres) {
	return movie.getGenres().parallelStream()
		.filter(obj -> {
		    JSONObject jsonObj = (JSONObject) obj;
		    return genres.contains(jsonObj.get("name"));
		})
		.count() > 0;
    }

    @SuppressWarnings("unchecked")
    private boolean isKeywordNameIncluded(Movie movie, String keyword) {
	return movie.getKeywords().parallelStream()
		.map(obj -> {
		    JSONObject jsonObj = (JSONObject) obj;
		    return jsonObj.get("name").toString();
		})
		.filter(jsonObj -> {
		    return jsonObj.equals(keyword);
		})
		.count() > 0;
    }

    private Movie fillTokensCallback(String... tokens) {
	{
	    try {
		System.out.println(tokens[10]);
		return new Movie(
			Long.valueOf(tokens[0]),
			parseToJsonArray(tokens[1]),
			parseToJsonArray(tokens[2]),
			tokens[3],
			tokens[4],
			Double.parseDouble(tokens[5]),
			parseToJsonArray(tokens[6]),
			tokens[7],
			Long.valueOf(tokens[8]),
			tokens[9],
			Double.valueOf(tokens[10]),
			Integer.valueOf(tokens[11]));
	    } catch (Exception e) {
		return null;
	    }
	}
    }

    private void readDatabase(String path) {
	try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
	    movies = bf.lines()
		    .skip(1)
		    .map(line -> line.split(COLUMN_SEPARATOR))
		    .filter(tokens -> tokens.length == COLUMN_LENGTH)
		    .map(tokens -> fillTokensCallback(tokens))
		    .filter(Objects::nonNull)
		    .collect(Collectors.toList());
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private JSONArray parseToJsonArray(String text) throws ParseException {
	JSONParser parser = new JSONParser();
	return (JSONArray) parser.parse(JsonUtils.parseToJsonArrayString(text));
    }

}
