package ar.edu.itba.paw.g4;

import ar.edu.itba.paw.g4.persist.CommentDAO;
import ar.edu.itba.paw.g4.persist.impl.CommentDAOImpl;

public class DummyMain {
	public static void main(String[] args) throws Exception {
		CommentDAO commentDAO = CommentDAOImpl.getInstance();
		
		
//		commentDAO.save(entity);
	}
}