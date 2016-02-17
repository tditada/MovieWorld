package ar.edu.itba.paw.model.movie;

import static ar.edu.itba.paw.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.notEmptyColl;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import ar.edu.itba.paw.domain.PersistentEntity;
import ar.edu.itba.paw.model.ImageWrapper;
import ar.edu.itba.paw.model.Score;
import ar.edu.itba.paw.model.comment.Comment;
import ar.edu.itba.paw.model.genre.Genre;
import ar.edu.itba.paw.model.user.User;

@SuppressWarnings("serial")
@Entity
@Table(name = "movies", uniqueConstraints = @UniqueConstraint(columnNames = { "title", "director" }) )
public class Movie extends PersistentEntity implements Serializable {
	public static final int DAYS_AS_RELEASE = 6;
	public static final int MAX_TITLE_LENGTH = 255;
	public static final int MAX_SUMMARY_LENGTH = 255;
	public static final int MAX_SHORT_SUMMARY = 100;

	@Column(nullable = false, length = MAX_TITLE_LENGTH)
	private String title; // artistic name for movie, so no special rules (other
							// than length) apply
	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime creationDate;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime releaseDate;

	@Sort(type = SortType.NATURAL)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private SortedSet<Genre> genres; // a movie can have many different
										// genres and a genre can have many
										// different movies

	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "director", nullable = false, length = Director.MAX_NAME_LENGTH) )
	private Director director;

	@Check(constraints = "runtimeInMins > 0")
	private int runtimeInMins;

	@Type(type = "text")
	@Column(nullable = false)
	private String summary;

	@Check(constraints = "totalScore >= 0")
	private int totalScore;

	@Sort(type = SortType.NATURAL)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "movie", cascade = CascadeType.ALL)
	private SortedSet<Comment> comments = new TreeSet<Comment>();

	@Embedded
	private ImageWrapper picture;

	@Check(constraints = "visits >= 0")
	private int visits;

	Movie() {
	}

	public Movie(DateTime releaseDate, String title, SortedSet<Genre> genres, Director director, int runtimeInMins,
			String summary, byte[] picture) {
		setTitle(title);
		setReleaseDate(releaseDate);
		setGenres(genres);
		setDirector(director);
		setRuntimeInMins(runtimeInMins);
		setSummary(summary);
		setPicture(picture);
		this.creationDate = now();
		this.totalScore = 0;
	}

	public int getTotalComments() {
		return comments.size();
	}

	public int getTotalScore() {
		return totalScore;
	}

	public int getVisits() {
		return visits;
	}

	public void addVisit() {
		this.visits = visits + 1;
	}

	public SortedSet<Comment> getComments() {
		return Collections.unmodifiableSortedSet(comments);
	}

	public List<Comment> getCommentsAsList() {
		return Collections.unmodifiableList(new ArrayList<Comment>(comments));
	}

	public Score getAverageScore() {
		if (getTotalComments() == 0) {
			return new Score(0);
		}
		return new Score(totalScore / getTotalComments());
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getTitle() {
		return title;
	}

	public SortedSet<Genre> getGenres() {
		return Collections.unmodifiableSortedSet(genres);
	}

	public List<Genre> getGenreList() {
		return Collections.unmodifiableList(new ArrayList<>(genres));
	}

	public String getShortSummary() {
		if (summary.length() < MAX_SHORT_SUMMARY) {
			return summary;
		}
		return summary.substring(0, MAX_SHORT_SUMMARY);
	}

	public String getGenreListString() {
		List<Genre> genreList = getGenreList();
		String genreString = "";
		for (Genre g : genreList) {
			String name = g.getName();
			name = name.substring(0, 1) + name.substring(1).toLowerCase();
			genreString += name + ", ";
		}
		if (genreString.length() >= 2) {
			genreString = genreString.substring(0, genreString.length() - 2);
		}
		return genreString;
	}

	public Director getDirector() {
		return director;
	}

	public int getRuntimeInMins() {
		return runtimeInMins;
	}

	public String getSummary() {
		return summary;
	}

	public DateTime getReleaseDate() {
		return releaseDate;
	}

	// public String getReleaseDateFormatter() {
	// org.joda.time.format.DateTimeFormatter formatter =
	// DateTimeFormat.forPattern("dd/MM/yyyy");
	// return releaseDate.toString(formatter);
	// }

	public byte[] getPicture() {
		return (picture == null) ? null : picture.getImage();
	}

	public void setPicture(byte[] picture) {
		if (picture == null) { // esto sería que no haya imagen, es válido
			this.picture=null;
			return;
		} else if (picture.length == 0) { // levantas una imagen pero está vacía
			return;
		} else if (this.picture == null) {
			this.picture = new ImageWrapper();
		}
		this.picture.setImage(picture);
	}

	public void setTitle(String title) {
		checkArgument(title, neitherNullNorEmpty());
		checkArgument(title.length() <= MAX_TITLE_LENGTH);
		this.title = title;
	}

	public void setDirector(Director director) {
		checkArgument(director, notNull());
		this.director = director;
	}

	public void setGenres(SortedSet<Genre> genres) {
		checkArgument(genres, notNull(), notEmptyColl());
		this.genres = genres;
	}

	public void setSummary(String summary) {
		checkArgument(summary, notNull());
		checkArgument(title.length() <= MAX_SUMMARY_LENGTH);
		this.summary = summary;
	}

	public void setReleaseDate(DateTime releaseDate) {
		checkArgument(releaseDate, notNull());
		this.releaseDate = releaseDate;
	}

	public void setRuntimeInMins(int runtimeInMins) {
		checkArgument(runtimeInMins > 0);
		this.runtimeInMins = runtimeInMins;
	}

	public boolean isRelease() {
		DateTime now = DateTime.now();
		Interval releaseInterval = new Interval(now.minusDays(DAYS_AS_RELEASE), now);
		return releaseInterval.contains(releaseDate);
	}

	public void removePicture() {
		this.picture = null;
	}

	public boolean isCommentableBy(User user) {
		checkArgument(user, notNull());
		if (releaseDate.isAfterNow()) {
			return false;
		}
		for (Comment comment : getComments()) {
			if (comment.getUser().equals(user)) {
				return false;
			}
		}
		return true;
	}

	public void addComment(Comment comment) {
		checkArgument(comment, notNull());
		checkArgument(this.equals(comment.getMovie()));

		if (comments.contains(comment)) {
			// this will only happen when addComment is called in a
			// callback
			return;
		}

		User user = comment.getUser();
		if (!isCommentableBy(user)) {
			throw new IllegalArgumentException();
		}

		comments.add(comment);
		this.totalScore += comment.getMovieScore().getValue();

		user.addComment(comment);
	}

	public void removeComment(User admin, Comment comment) {
		checkArgument(admin, notNull());
		checkArgument(comment, notNull());
		checkArgument(admin.isAdmin());

		if (!comments.contains(comment)) {
			// this will only happen when removeComment is called in a
			// callback
			return;
		}

		comments.remove(comment);
		this.totalScore -= comment.getMovieScore().getValue();

		admin.removeComment(comment);
	}

	public List<Comment> getCommentsScoreableBy(User user) {
		List<Comment> scoreableComments = new LinkedList<>();
		for (Comment comment : comments) {
			if (comment.canBeScoredBy(user)) {
				scoreableComments.add(comment);
			}
		}
		return Collections.unmodifiableList(scoreableComments);
	}

	public List<Comment> getCommentsReportableBy(User user) {
		List<Comment> reportableComments = new LinkedList<>();
		for (Comment comment : comments) {
			if (comment.isReportableBy(user)) {
				reportableComments.add(comment);
			}
		}
		return Collections.unmodifiableList(reportableComments);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("name", title).add("id", getId()).add("creationDate", creationDate)
				.add("releaseDate", releaseDate).add("genres", genres).add("director", director)
				.add("durationInMins", runtimeInMins).add("summary", summary).add("totalScore", totalScore).toString();
	}

	@Override
	public int hashCode() {
		return hash(title, director);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Movie that = (Movie) obj;
		return areEqual(this.title, that.title) && areEqual(this.director, that.director);
	}

	public static MovieBuilder builder() {
		return new MovieBuilder();
	}
}