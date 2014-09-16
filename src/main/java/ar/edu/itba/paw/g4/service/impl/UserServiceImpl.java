package ar.edu.itba.paw.g4.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ar.edu.itba.paw.g4.exception.DatabaseException;
import ar.edu.itba.paw.g4.exception.ServiceException;
import ar.edu.itba.paw.g4.model.User;
import ar.edu.itba.paw.g4.persist.UserDAO;
import ar.edu.itba.paw.g4.persist.impl.PSQLUserDAO;
import ar.edu.itba.paw.g4.service.UserService;
import ar.edu.itba.paw.g4.util.EmailAddress;

public class UserServiceImpl implements UserService{
	
	private UserDAO userdao=PSQLUserDAO.getInstance();
	private static String NAME_ID = "name";
	private static String LAST_NAME = "lastname";
	private static String EMAIL_ID = "email";
	private static String PASS = "password";
	private static String BIRTHDAY ="birthday";
	private HttpServletRequest request;
	
	public UserServiceImpl(HttpServletRequest request) {
		this.request=request;
	}
	
	@Override
	public boolean userHasSession() {
		HttpSession session = request.getSession();
		return !SessionAttributesAreNull(session);
	}
	
	//	Revisa la BD para ver si el usuario existe
//	private boolean userExists(User user){
//		userdao.getById(user.getId());
//		return false;		
//	}

	@Override
	public User getUserById(Integer id) {
		userdao.getById(id);
		return null;
	}
	
	@Override
	public void Register(User user) {	
		try{
		userdao.save(user);
		}catch(DatabaseException e){
			throw new ServiceException(e);
		}
	}

	@Override
	public void logout(User user) {
		HttpSession session = request.getSession();
		session.setAttribute(NAME_ID, null);
		session.setAttribute(EMAIL_ID, null);		
	}
	
	public User login(EmailAddress email, String pass){
		User user=authentication(email,pass);
		CreateUserSession(user);
		return user;
	}
	
	private User authentication(EmailAddress email, String pass){
		User user= userdao.getByEmail(email);
		if(user==null){
			throw new ServiceException("Non existent user");
		} else if(!checkPassword(user,pass)){
			throw new ServiceException("Wrong Password");
		};
		return user;
	}

	private boolean checkPassword(User user, String pass) {
		return user.getPassword().equals(pass);		
	}

	private void CreateUserSession(User user) {
		HttpSession session = request.getSession();		
		session.setAttribute(NAME_ID, user.getFirstName());
		session.setAttribute(EMAIL_ID, user.getLastName());
		session.setAttribute(LAST_NAME, user.getLastName());
		session.setAttribute(PASS, user.getPassword());
		session.setAttribute(BIRTHDAY, user.getBirthDate());		
	}
	
	private boolean SessionAttributesAreNull(HttpSession session){
		return session.getAttribute(NAME_ID)==null && session.getAttribute(LAST_NAME)==null && session.getAttribute(EMAIL_ID)==null && session.getAttribute(PASS)==null && session.getAttribute(BIRTHDAY)==null;
	}
	
}
