package dbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import dbUtil.Resource;

public class UserDao {
	private static UserDao udao;
	private final Connection connection = DatabaseConnector.getConnection();

	private UserDao(){
    }
	
    public boolean addUser(User user){
    	PreparedStatement statement;
		try {
			statement = connection.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)");
			statement.setString(1, user.getUsername());
	    	statement.setString(2, user.getPassword());
	    	int affectedRows = statement.executeUpdate();
	    	if(affectedRows == 0){
				return false;
	    	}
	    		ResultSet generatedKeys = statement.getGeneratedKeys();
	    		if(generatedKeys.next()){
	    			user.setId(generatedKeys.getInt(1));
	    		} else {
	    			return false;
	    		}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
    	return true;
    }
    
    public boolean login(User user){
    	PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT username, password FROM user WHERE username = ?");
			statement.setString(1, user.getUsername());
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				if(user.getPassword().equals(resultSet.getString("password"))){
					return true;
				}
				else return false;
			}
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }

	public int getUserId(User user) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT id FROM user WHERE username = ?");
			statement.setString(1, user.getUsername());
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				return resultSet.getInt("id");
			}
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return -1;
	}

	public String getUsername() {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT username FROM user WHERE id = ?");
			statement.setInt(1, LoginStateDao.getDao().getUser());
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()){
				return resultSet.getString("username");
			}
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
	}
	public static UserDao getDao(){
		if(udao == null){
			udao = new UserDao();
		}
		return udao;
	}
}
