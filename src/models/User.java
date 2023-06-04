package models;

public class User {
	private static User user;
	private int id;
	private String username;
	private String password;
	
	
	private User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//Singleton pattern
	public static User getUser(String username, String password){
		if(user == null){
			user = new User(username, password);
		} else{
			user.setPassword(password);
			user.setUsername(username);
		}
		return user;
	}
}
