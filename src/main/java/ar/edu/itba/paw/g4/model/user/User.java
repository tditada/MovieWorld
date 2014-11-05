package ar.edu.itba.paw.g4.model.user;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkState;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.comment.Comment;
import ar.edu.itba.paw.g4.model.movie.Movie;
import ar.edu.itba.paw.g4.util.persist.PersistentEntity;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User extends PersistentEntity {
	@Embedded
	@AttributeOverride(name = "nameString", column = @Column(name = "firstName", nullable = false))
	private NonArtisticName firstName;

	@Embedded
	@AttributeOverride(name = "nameString", column = @Column(name = "lastName", nullable = false))
	private NonArtisticName lastName;

	@Embedded
	private Email email;

	@Embedded
	private Password password;

	@Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime birthDate;

	// TODO: Agregar constraint de que haya UN solo admin
	@Column(nullable = false)
	private boolean admin;

	@Sort(type = SortType.NATURAL)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private SortedSet<Comment> comments = new TreeSet<Comment>();

	@OneToMany
	private Set<User> interestingUsers = new HashSet<User>();

	User() {
	}

	@GeneratePojoBuilder
	User(NonArtisticName firstName, NonArtisticName lastName, Email email,
			Password password, DateTime birthDate, boolean admin) {
		checkArgument(firstName, notNull());
		checkArgument(lastName, notNull());
		checkArgument(email, notNull());
		checkArgument(password, notNull());
		checkArgument(birthDate, notNull());
		checkArgument(birthDate.isBeforeNow());

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.admin = admin;
	}

	public void addComment(Comment comment) { // FIXME
		checkArgument(comment, notNull());
		if (!comment.getMovie().isCommentableBy(this)) { // XXX
			return;
		}

		Movie movie = comment.getMovie();
		if (!movie.isCommentableBy(this)) { // XXX
			throw new IllegalArgumentException();
		}

		comments.add(comment);

		movie.addComment(comment);
	}

	public void removeComment(Comment comment) {
		checkArgument(comment, notNull());
		checkState(this.isAdmin());

		User user = comment.getUser();

		if (!user.comments.contains(comment)) {
			// this will only happen when removeComment is called in a
			// callback
			return;
		}

		user.comments.remove(comment);

		Movie movie = comment.getMovie();
		movie.removeComment(this, comment);
	}

	public NonArtisticName getFirstName() {
		return firstName;
	}

	public NonArtisticName getLastName() {
		return lastName;
	}

	public Email getEmail() {
		return email;
	}

	public Password getPassword() {
		return password;
	}

	public DateTime getBirthDate() {
		return birthDate;
	}

	public boolean isAdmin() {
		return admin;
	}

	public SortedSet<Comment> getComments() {
		return Collections.unmodifiableSortedSet(comments);
	}

	@Override
	public String toString() {
		// TODO
		return "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";
		// return toStringHelper(this).add("id", getId())
		// .add("firstName", firstName).add("lastName", lastName)
		// .add("email", email).add("password", password)
		// .add("birthDate", birthDate).add("comments", comments)
		// .toString();
	}

	@Override
	public int hashCode() {
		return hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User that = (User) obj;
		return areEqual(this.email, that.email);
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public Set<User> getInterestingUsers() {
		return interestingUsers;
	}

	public void addInterestingUser(User user) {
		checkArgument(user, notNull());
		if (user.equals(this)) {
			return;
		}
		interestingUsers.add(user);
	}

	public void removeInterestingUser(User user) {
		checkArgument(user, notNull());
		interestingUsers.remove(user);
	}

	public boolean isinterestingUser(User user) {
		return interestingUsers.contains(user);
	}

	public List<Comment> getRecentComments() {
		List<Comment> recentComments = new LinkedList<>();

		for (Comment c : comments) {
			DateTime recentLimitDate = c.getCreationDate().minusWeeks(1);
			if (c.getCreationDate().isAfter(recentLimitDate)) {
				recentComments.add(c);
			}
		}
		return recentComments;
	}

}