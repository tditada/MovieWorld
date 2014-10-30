package ar.edu.itba.paw.g4.web.convert;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.g4.model.MovieGenres;

@Component
public class MovieGenresSetFormatter implements Formatter<Set<MovieGenres>> {

	@Override
	public String print(Set<MovieGenres> arg0, Locale arg1) {
		String s="";
		for (MovieGenres mg:arg0){
			s=mg.getGenreName()+", ";
		}
		StringUtils.stripEnd(s, ", ");
		return s;
	}

	@Override
	public Set<MovieGenres> parse(String arg0, Locale arg1)
			throws ParseException {
		System.out.println("IM IN THE FORMATTER");
		Set <MovieGenres> movieGenresSet = new HashSet<MovieGenres>();
		String[] genres = arg0.split(",");
		for (String s:genres){
			StringUtils.strip(s," ");
			movieGenresSet.add(MovieGenres.valueOf(s.toUpperCase()));
		}
		return movieGenresSet;
	}

	
}
