package ar.edu.itba.paw.g4;

import org.joda.time.DateTime;

import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.persist.impl.SQLUserDAO;
import ar.edu.itba.paw.g4.util.EmailAddress;

public class DummyMain {
	public static void main(String[] args) throws Exception {
		UserDAO userDAO = SQLUserDAO.getInstance();
		// CommentDAO commentDAO = CommentDAOImpl.getInstance();

		User user = User.builder().withBirthDate(DateTime.now())
				.withEmail(EmailAddress.build("pepes@foo.com"))
				.withFirstName("Pepe").withLastName("Sanchez")
				.withPassword("123456").build();

		userDAO.save(user);

		// commentDAO.save(entity);
	}
}