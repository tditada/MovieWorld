package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.movie.MovieGenre;

@Component
public class MovieGenreSetFormatter implements Formatter<SortedSet<MovieGenre>> {

	@Override
	public String print(SortedSet<MovieGenre> genres, Locale arg1) {
		String genresString = "";
		for (MovieGenre genre : genres) {
			genresString = genre.getName() + ", ";
		}
		genresString = StringUtils.stripEnd(genresString, ", ");
		return genresString;
	}

	@Override
	public SortedSet<MovieGenre> parse(String arg0, Locale arg1)
			throws ParseException {
		SortedSet<MovieGenre> movieGenresSet = new TreeSet<>();
		String[] genreNames = arg0.split(",");
		for (String name : genreNames) {
			String strippedName = StringUtils.strip(name, " ");
			movieGenresSet.add(new MovieGenre(strippedName));
		}
		return movieGenresSet;
	}

}
