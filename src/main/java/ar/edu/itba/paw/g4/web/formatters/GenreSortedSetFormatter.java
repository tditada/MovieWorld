package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.genre.Genre;
import ar.edu.itba.paw.g4.model.genre.GenreRepo;

@Component
public class GenreSortedSetFormatter implements Formatter<SortedSet<Genre>> {
	private GenreRepo genres;

	@Autowired
	public GenreSortedSetFormatter(GenreRepo genres) {
		this.genres = genres;
	}

	@Override
	public String print(SortedSet<Genre> genres, Locale arg1) {
		String genresString = "";
		for (Genre genre : genres) {
			genresString = genre.getName() + ", ";
		}
		genresString = StringUtils.stripEnd(genresString, ", ");
		return genresString;
	}

	@Override
	public SortedSet<Genre> parse(String arg0, Locale arg1)
			throws ParseException {
		SortedSet<Genre> movieGenresSet = new TreeSet<>();
		String[] genreNames = arg0.split(",");

		for (String name : genreNames) {
			String strippedName = StringUtils.strip(name, " ");
			Genre genre = genres.findByName(strippedName);
			if (genre == null) {
				genre = new Genre(strippedName);
			}
			movieGenresSet.add(genre);
		}

		return movieGenresSet;
	}

}
