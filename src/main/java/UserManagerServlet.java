public interface UserManagerServlet {

	public boolean existsUser();
	
	public String getName();
	
	public String getEmail();
	
	public void setUser(String name, String sign);
	
	public void resetUser(String name);
	
}