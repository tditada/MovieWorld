package ar.edu.itba.paw.web.movie;

import java.util.List;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.genre.GenreRepo;
import ar.edu.itba.paw.model.movie.Director;
import ar.edu.itba.paw.model.movie.MovieRepo;
import ar.edu.itba.paw.util.persist.Orderings;
import ar.edu.itba.paw.web.common.NavBar;

@SuppressWarnings("serial")

// Filters in MovieListPage (two drop downs) that should be in the NavBar
public class FilterMovieNavBar extends NavBar {

	@SpringBean
	GenreRepo genreRepo;
	@SpringBean
	MovieRepo movieRepo;

	public FilterMovieNavBar(String id, final IModel<Genre> genre, final IModel<Director> director) {
		super(id);

		Form<FilterMovieNavBar> form = new Form<FilterMovieNavBar>("filterMovie");
		DropDownChoice<Genre> ddcg = new DropDownChoice<Genre>("genre", genre,
				new LoadableDetachableModel<List<Genre>>() {
					@Override
					protected List<Genre> load() {
						return genreRepo.findAllOrderedByName(Orderings.DESC);
					}
				});
		ddcg.setNullValid(true);
		form.add(ddcg);

		DropDownChoice<Director> ddcd = new DropDownChoice<Director>("director", director,
				new LoadableDetachableModel<List<Director>>() {
					@Override
					protected List<Director> load() {
						return movieRepo.findAllDirectorsOrderedByName(Orderings.DESC);
					}
				});
		ddcd.setNullValid(true);
		form.add(ddcd);

		form.add(new Button("submit") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				setResponsePage(new MovieListPage(genre.getObject(), director.getObject()));
			}
		});
		add(form);

	}
}
