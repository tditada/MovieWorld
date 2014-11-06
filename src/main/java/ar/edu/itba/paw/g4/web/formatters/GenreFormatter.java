package ar.edu.itba.paw.g4.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.genre.Genre;
import ar.edu.itba.paw.g4.model.genre.GenreRepo;

@Component
public class GenreFormatter implements Formatter<Genre> {
	private GenreRepo genres;

	@Autowired
	public GenreFormatter(GenreRepo genres) {
		this.genres = genres;
	}

	@Override
	public Genre parse(String name, Locale arg1) throws ParseException {
		Genre genre = genres.findByName(name);
		if (genre == null) {
			genre = new Genre(name);
		}
		return genre;
	}

	@Override
	public String print(Genre genre, Locale arg1) {
		return genre.getName();
	}
}
