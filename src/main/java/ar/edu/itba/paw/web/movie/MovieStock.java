package ar.edu.itba.paw.web.movie;

import java.util.Date;
import java.util.List;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import ar.edu.itba.paw.model.movie.Movie;

public class MovieStock {

	private static String APPLICATION_ID = "bH0IAo6UbBCuaXVVFZQl62vgaOs6l4vBRmDmyZMl";
	private static String REST_API_KEY = "EwQWDYrnMcTqQ7MJOxnl3l9aA12bBHOPKXp3AIFY";

	public static int getStock(Movie movie) {
		int index = 0;
		Parse.initialize(APPLICATION_ID, REST_API_KEY);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Movie");
		query.whereEqualTo("name", movie.getTitle());

		List<ParseObject> objects;
		try {
			objects = query.find();
			if (objects == null || objects.isEmpty()) {
				throw new MovieStockNotFound();
			}
			Date updateDate = objects.get(0).getDate("updatedAt");
			if (objects.size() > 1) {
				for (int i = 1; i < objects.size(); i++) {
					ParseObject o = objects.get(i);
					Date d = o.getDate("updatedAt");
					if (d != null && d.after(updateDate)) {
						updateDate = d;
						index = i;
					}
				}
			}

			return objects.get(index).getInt("stock");
		} catch (ParseException e) {
			throw new MovieStockNotFound();
		}
	}
}
