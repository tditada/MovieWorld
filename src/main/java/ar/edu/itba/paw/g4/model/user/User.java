package ar.edu.itba.paw.g4.model.user;

import static ar.edu.itba.paw.g4.util.ObjectHelpers.areEqual;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.hash;
import static ar.edu.itba.paw.g4.util.ObjectHelpers.toStringHelper;
import static ar.edu.itba.paw.g4.util.validation.PredicateHelpers.notNull;
import static ar.edu.itba.paw.g4.util.validation.Validations.checkArgument;
import static org.joda.time.DateTime.now;

import java.util.Set;
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

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.Email;
import ar.edu.itba.paw.g4.model.NonArtisticName;
import ar.edu.itba.paw.g4.model.Password;
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

	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	@Column(nullable = false)
	private DateTime birthDate;

//	TODO: ¿Agregar constrain de que haya UN solo admin?
	@Column(nullable = false)
	private boolean isAdmin;

//	@Sort(type=SortType.NATURAL)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Comment> comments = new TreeSet<Comment>();

	User() {
	}

	@GeneratePojoBuilder
	User(NonArtisticName firstName, NonArtisticName lastName, Email email,
			Password password, DateTime birthDate, boolean isAdmin) {
		checkArgument(firstName, notNull());
		checkArgument(lastName, notNull());
		checkArgument(email, notNull());
		checkArgument(password, notNull());
		checkArgument(birthDate, notNull());
		checkArgument(birthDate.isBefore(now()));

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.isAdmin= isAdmin;
	}

	public void addComment(Comment comment) {
		checkArgument(comment, notNull());
		if(!comment.getMovie().isCommentableBy(this)){
			return;
		}
		
		for(Comment c:comments){
			if(c.equals(comment)){
				return;
			}
		}

		comments.add(comment);

		Movie movie = comment.getMovie();
		movie.addComment(comment);
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
	
	public boolean getIsAdmin(){
		return isAdmin;
	}

	public Set<Comment> getComments() {
		return comments;
	}
	
	public void removeComment(Comment c){
		comments.remove(c);
		Movie movie= c.getMovie();
		movie.removeComment(c);
	}

	public Comment getComment(int commentId){
		for(Comment c:comments){
			if(c.getId()==commentId){
				return c;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return toStringHelper(this).add("id", getId())
				.add("firstName", firstName).add("lastName", lastName)
				.add("email", email).add("password", password)
				.add("birthDate", birthDate).add("comments", comments)
				.toString();
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

	public void updateCommentScore(Movie movie, User user, int score) {
		for(Comment c:comments){
			if(c.getMovie().equals(movie) && c.getUser().equals(user)){
				c.setCommentScore(user, score);
				c.getMovie().updateCommentScore(movie,user,score);
				return;
			}
		}
		
	}

}