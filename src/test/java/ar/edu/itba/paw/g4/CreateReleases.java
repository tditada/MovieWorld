package ar.edu.itba.paw.g4;

import java.util.List;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.enums.MovieGenres;
import ar.edu.itba.paw.g4.model.Director;
import ar.edu.itba.paw.g4.model.Movie;
import ar.edu.itba.paw.g4.persist.MovieDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLMovieDAO;

import com.google.common.collect.Lists;

public class CreateReleases {
	public static void main(String[] args) throws Exception {
		createReleases();
		System.out.println("Created");
	}

	private static void createReleases() {
		MovieDAO movieDAO = PSQLMovieDAO.getInstance();

		List<MovieGenres> genres = Lists.newArrayList(MovieGenres.ACTION);
		Director director = Director.builder().withName("El Barto").build();

		String extraText = "";
		for (int i = 0; i < 300; i++) {
			extraText += "a ";
		}
		Movie movie = Movie
				.builder()
				.withTitle("Bartman, el caballero de la noche asciende")
				.withSummary(
						"McBane planea destruir Springfield; solo Bartman podra detenerlo."
								+ "El resto de esto es una descripcion boba eternamente larga"
								+ " solo para molestar y poder ver si funciona todo bien. Make Bartman proud boy"
								+ extraText).withDirector(director)
				.withCreationDate(DateTime.now().minusYears(1))
				.withGenres(genres)
				.withReleaseDate(DateTime.now().minusDays(1))
				.withRuntimeInMins(120).build();

		movieDAO.save(movie);
	}
}