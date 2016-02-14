package ar.edu.itba.paw.web.converter;

import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.util.convert.IConverter;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;

@SuppressWarnings("serial")
public class GenreSortedSetConverter implements IConverter<SortedSet<Genre>> {
	private GenreRepo genres;

	@Autowired
	public GenreSortedSetConverter(GenreRepo genres) {
		this.genres = genres;
	}

	@Override
	public SortedSet<Genre> convertToObject(String arg0, Locale arg1) {
		SortedSet<Genre> movieGenresSet = new TreeSet<>();
		String[] genreNames = arg0.split(",");

		for (String name : genreNames) {
			String strippedName = StringUtils.strip(name, " ");
			strippedName = StringUtils.strip(strippedName, "]");
			strippedName = StringUtils.strip(strippedName, "[");
			Genre genre = genres.findByName(strippedName);
			if (genre == null) {
				genre = new Genre(strippedName);
			}
			movieGenresSet.add(genre);
		}


		return movieGenresSet;
	}

	@Override
	public String convertToString(SortedSet<Genre> genres, Locale arg1) {
		String genresString = "";
		for (Genre genre : genres) {
			genresString = genre.getName() + ", ";
		}
		genresString = StringUtils.stripEnd(genresString, ", ");
		return genresString;
	}
}
