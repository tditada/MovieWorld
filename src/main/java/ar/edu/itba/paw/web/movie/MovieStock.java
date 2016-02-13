package ar.edu.itba.paw.web.movie;

import java.util.List;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import ar.edu.itba.paw.model.movie.Movie;

public class MovieStock {
	
	private static String APPLICATION_ID="bH0IAo6UbBCuaXVVFZQl62vgaOs6l4vBRmDmyZMl";
	private static String REST_API_KEY="EwQWDYrnMcTqQ7MJOxnl3l9aA12bBHOPKXp3AIFY";
	
	public static int getStock(Movie movie) {
		Parse.initialize(APPLICATION_ID, REST_API_KEY);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
		query.whereEqualTo("name", movie.getTitle());
		query.limit(1);

		List<ParseObject> objects;
		try {
			objects = query.find();

			if (objects == null || objects.isEmpty()) {
				throw new MovieStockNotFound();
			}

			return objects.get(0).getInt("stock");
		} catch (ParseException e) {
			throw new MovieStockNotFound();
		}
	}
}
