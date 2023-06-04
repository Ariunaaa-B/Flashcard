package dbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.Resource;

public class LoginStateDao {
	private static LoginStateDao ldao;
	private final Connection connection = DatabaseConnector.getConnection();
	private LoginStateDao(){}
	
	public int getUser(){
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM logInState");
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public void setUser(int id){
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE logInState SET userId = ?");
			statement.setInt(1, id);
			int affectedRows = statement.executeUpdate();
			System.out.println(id);
			if (affectedRows == 0) {
				throw new SQLException(
						"Updating flashcard collection failed, no rows affected.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static LoginStateDao getDao(){
		if(ldao == null){
			ldao = new LoginStateDao();
		}
		return ldao;
	}
}
