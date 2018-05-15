package io.github.marcocab.movies;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    private static MovieJSONReader reader;

    private static void query1() {
	System.out.println("1. Printing all db movies: \n");
	reader.printAll();
    }

    private static void query2() {
	// long low = 7000; // Just 'Primer'
	// long high = low + 1; // Just 'Primer'
	long low = 1000; //
	long high = low + 19000;
	System.out.println("2. Printing all movie's titles filtering by budget range [" + low + ", " + high + "]: \n");
	reader.printAllMoviesByBudgetRange(low, high);
    }

    private static void query3() {
	Set<String> genres = new HashSet<>(Arrays.asList("Comedy", "Drama"));
	System.out.println("\n3. Printing all titles and genres filtering by a set of genres" + genres.toString() + "\n");
	reader.printAllMoviesGenresSet(genres);
    }

    private static void query4() {
	String keyword = "camcorder";
	System.out.println("\n4. Printing the higher revenue of a movie with the keyword [" + keyword + "]\n");
	reader.printMaximumRevenueWithThisKeyword(keyword);
    }

    private static void query5() {
	String lang = "en";
	double popularity = 1.9;
	System.out.println("\n5. Printing all movies with language: " + lang + ", and popularity at least:" + popularity + "]\n");
	List<Movie> moviesResult = reader.printMoviesByLanguageWithAlmostPopularityAt(lang, popularity);
	moviesResult.stream()
		.forEach(System.out::println);
    }

    private static void query6() {
	// int year = 2017; //Total: 0
	int year = 2005; // Total 14931589218
	System.out.println("\n6. Total revenue by year: " + year + "\n");
	long result = reader.getTotalRevenueByYear(year);
	System.out.println("Total revenue in (" + year + "): " + result);
    }

    private static void query7() {
	double lowBound = 5.0;
	double highBound = 10.0;
	System.out.println("\n7. Total votes by average count range [" + lowBound + ", " + highBound + "] \n");
	int result = reader.getAllVotesCountbyAverageVoteRange(lowBound, highBound);
	System.out.println("Total votes count: " + result);
    }

    private static void query8() {
	System.out.println("\n7. Generate Map with each production company => [films] \n");
	Map<String, Set<String>> result = reader.getProductionCompaniesTitlesMap();
	result.forEach((key, value) -> System.out.println(key + " => " + value));
    }

    public static void main(String... args) {
	reader = new MovieJSONReader("data/movies_db.csv");
	long startTime = System.currentTimeMillis();

	query1();
	query2();
	query3();
	query4();
	query5();
	query6();
	query7();
	query8();

	long duration = System.currentTimeMillis() - startTime;
	System.out.println("\nExecuted code spend " + duration + " millis");
    }
}
