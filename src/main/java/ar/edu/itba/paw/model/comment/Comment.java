package ar.edu.itba.paw.model.comment;

import static ar.edu.itba.paw.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import ar.edu.itba.paw.domain.PersistentEntity;
import ar.edu.itba.paw.model.Score;
import ar.edu.itba.paw.model.movie.Movie;
import ar.edu.itba.paw.model.user.Email;
import ar.edu.itba.paw.model.user.User;

@SuppressWarnings("serial")
@Entity
@Table(name = "comments")
public class Comment extends PersistentEntity implements Comparable<Comment>, Serializable {
	public static final int MAX_TEXT_LENGTH = 255;

	@Type(type = "text")
	@Check(constraints = "length(text) > 0")
	@Column(nullable = false)
	private String text;

	@Embedded
	@AttributeOverride(name = "score", column = @Column(name = "movieScore", nullable = false) )
	private Score movieScore;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "comment_scorers")
	private Set<User> scorers = new HashSet<>();// a comment can be scored by
												// many different users, and a
												// user can score in
												// many different comments

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "comment_reports")
	private Set<User> reportingUsers = new HashSet<>(); // same idea as above

	@Column(nullable = false)
	private int totalReports;

	@Column(nullable = false)
	@Check(constraints = "totalCommentScore >= 0")
	private int totalCommentScore;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime creationDate;

	@ManyToOne
	private Movie movie;

	@ManyToOne
	private User user;

	Comment() {
	}

	// @GeneratePojoBuilder
	Comment(String text, Score movieScore, User user, Movie movie) {
		checkArgument(movieScore, notNull());
		checkArgument(text, neitherNullNorEmpty());
		checkArgument(text.length() < MAX_TEXT_LENGTH);
		checkArgument(user, notNull());
		checkArgument(movie, notNull());
		checkArgument(movie.isCommentableBy(user));

		this.text = text;
		this.movieScore = movieScore;
		this.user = user;
		this.movie = movie;

		this.creationDate = now();
		this.totalCommentScore = 0;
	}

	public boolean canBeScoredBy(User user) {
		checkArgument(user, notNull());

		return !this.user.equals(user) && !scorers.contains(user);
	}

	public void addScore(User user, Score commentScore) {
		checkArgument(user, notNull());
		checkArgument(commentScore, notNull());
		checkArgument(canBeScoredBy(user));

		totalCommentScore += commentScore.getValue();
		scorers.add(user);
	}

	public Score getAverageCommentScore() {
		if (totalCommentScore == 0 || (scorers != null && scorers.size() == 0)) {
			return new Score(0);
		}
		return new Score(totalCommentScore / scorers.size());
	}

	public String getText() {
		return text;
	}

	public Score getMovieScore() {
		return movieScore;
	}

	public User getUser() {
		return user;
	}

	public Movie getMovie() {
		return movie;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public Set<User> getScorers() {
		return scorers;
	}

	public void addReport(User user) {
		checkArgument(user, notNull());
		checkArgument(isReportableBy(user));

		reportingUsers.add(user);
		totalReports++;
	}

	public int getReportedSize() {
		return reportingUsers.size();
	}

	public boolean isReportableBy(User user) {
		checkArgument(user, notNull());
		return !user.equals(this.user) && !reportingUsers.contains(user);
	}

	public void dropReports(User user) {
		checkArgument(user, notNull());
		checkArgument(user.isAdmin());

		reportingUsers.clear();
		totalReports = 0;
	}

	public boolean isReported() {
		return !reportingUsers.isEmpty();
	}

	public int getReportCount() {
		return totalReports;
	}

	@Override
	public int compareTo(Comment other) {
		if (other == null) {
			return 1;
		}

		if (other.equals(this)) {
			return 0;
		}

		Integer comp = other.getAverageCommentScore().compareTo(this.getAverageCommentScore());
		if (comp != 0) {
			return comp;
		}
		String textAddress = other.getUser().getEmail().getTextAddress();
		if (textAddress == null) {
			return 1;
		}
		Email email = user.getEmail();
		if (email == null) {
			return 1;
		}
		comp = textAddress.compareTo(email.getTextAddress());
		if (comp != 0) {
			return comp;
		}
		comp = other.getMovie().getDirector().getName().compareTo(movie.getDirector().getName());
		if (comp != 0) {
			return comp;
		}
		return other.getMovie().getTitle().compareTo(movie.getTitle());
	}

	@Override
	public int hashCode() {
		return hash(user, movie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Comment that = (Comment) obj;
		return areEqual(this.user, that.user) && areEqual(this.movie, that.movie);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("id", getId()).add("score", movieScore).add("text", text)
				.add("creationDate", creationDate).add("totalCommentScore", totalCommentScore).toString();
	}

	public static CommentBuilder builder() {
		return new CommentBuilder();
	}

}
