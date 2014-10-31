package ar.edu.itba.paw.g4.model.comment;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.neitherNullNorEmpty;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.annotations.Check;
import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.model.user.User;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "comments"/*
						 * TODO: Check! is this ok? , uniqueConstraints =
						 * @UniqueConstraint(columnNames = { "movie", "user" })
						 */)
public class Comment extends PersistentEntity implements Comparable<Comment>{
	private static final int MIN_SCORE = 0;
	private static final int MAX_SCORE = 5;

	@Check(constraints = "length(text) > 0")
	@Column(nullable = false)
	private String text;

	@Check(constraints = "(score >=" + MIN_SCORE )
	private int averageScore;
	
	@ElementCollection
	private Set<Pair<User,Integer>> scores;

	@Check(constraints = "(score >=" + MIN_SCORE )
	private int totalScore;
	
	@Column(nullable = false)
	private DateTime creationDate;

	@ManyToOne
	private Movie movie;

	@ManyToOne
	private User user;

	Comment() {
	}

	@GeneratePojoBuilder
	Comment(String text, int score, User user, Movie movie,
			DateTime creationDate) {
		checkArgument(score >= MIN_SCORE && score <= MAX_SCORE);
		checkArgument(text, neitherNullNorEmpty());
		checkArgument(user, notNull());
		checkArgument(movie, notNull());
		checkArgument(movie.isCommentableBy(user));

		this.text = text;
		this.averageScore = score;
		this.user = user;
		this.movie = movie;
		this.creationDate = creationDate != null ? creationDate : now();
		this.scores=new HashSet<Pair<User,Integer>>();
	}

	public String getText() {
		return text;
	}

	public int getScore() {
		return averageScore;
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
		return areEqual(this.user, that.user)
				&& areEqual(this.movie, that.movie);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("id", getId()).add("user", user)
				.add("movie", movie).add("score", averageScore).add("text", text)
				.add("creationDate", creationDate).toString();
	}

	public static CommentBuilder builder() {
		return new CommentBuilder();
	}
	
	public void setScore(User user, int score){
		if(isAbleToScore(user)){
			scores.add(Pair.of(user, score));
			this.totalScore=totalScore+score;
			this.averageScore=(totalScore)/scores.size();			
		}
		
	}
	
	private boolean isAbleToScore(User user){
		for(Pair<User,Integer> p:scores){
			if(p.getLeft().getId()==user.getId()){
				return false;
			}
		}
		return true;
	}

	@Override
	public int compareTo(Comment c) {
		return ((Integer)averageScore).compareTo((Integer)c.averageScore);
	}
}
