package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.MovieGenre;

@Component
public class MovieGenreSetFormatter implements Formatter<Set<MovieGenre>> {

	@Override
	public String print(Set<MovieGenre> genres, Locale arg1) {
		String genresString = "";
		for (MovieGenre genre : genres) {
			genresString = genre.getName() + ", ";
		}
		genresString = StringUtils.stripEnd(genresString, ", ");
		return genresString;
	}

	@Override
	public Set<MovieGenre> parse(String arg0, Locale arg1)
			throws ParseException {
		Set<MovieGenre> movieGenresSet = new HashSet<>();
		String[] genreNames = arg0.split(",");
		for (String name : genreNames) {
			String strippedName = StringUtils.strip(name, " ");
			movieGenresSet.add(new MovieGenre(strippedName));
		}
		return movieGenresSet;
	}

}
