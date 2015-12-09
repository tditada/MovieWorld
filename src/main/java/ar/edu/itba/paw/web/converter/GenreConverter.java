package ar.edu.itba.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;

@SuppressWarnings("serial")
public class GenreConverter implements IConverter<Genre> {
	private GenreRepo genres;
	
	@Autowired
	public GenreConverter(GenreRepo genres) {
		this.genres = genres;
	}

	@Override
	public Genre convertToObject(String name, Locale arg1) {
		Genre genre = genres.findByName(name);
		if (genre == null) {
			genre = new Genre(name);
		}
		return genre;
	}



	@Override
	public String convertToString(Genre genre, Locale arg1) {
		return genre.getName();
	}
}
