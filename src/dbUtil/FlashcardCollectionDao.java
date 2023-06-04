package dbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbUtil.Resource;
import models.FlashcardCollection;

public class FlashcardCollectionDao {

	private final Connection connection = DatabaseConnector.getConnection();
	private static FlashcardCollectionDao dao;

	private FlashcardCollectionDao() {
	}

	public void addFlashcardCollection(FlashcardCollection collection)
			throws SQLException {
		String query = "INSERT INTO flashcardCollection (name, size, userId) VALUES (?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, collection.getName());
			statement.setInt(2, 0);
			statement.setInt(3, collection.getUserId());
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Creating flashcard collection failed, no rows affected.");
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					collection.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException(
							"Creating flashcard collection failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		}
	}

	public List<FlashcardCollection> getAllFlashcardCollections() {
		if(Resource.userId == -1) return new ArrayList<>();
		List<FlashcardCollection> collections = new ArrayList<>();
		PreparedStatement statement;
		try {
			statement = connection
					.prepareStatement("SELECT id, name, size FROM flashcardCollection WHERE userId = ?");
			statement.setInt(1, Resource.userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				FlashcardCollection collection = new FlashcardCollection();
				collection.setId(rs.getInt("id"));
				collection.setName(rs.getString("name"));
				collections.add(collection);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return collections;

	}

	public FlashcardCollection getFlashcardCollectionByName(String name)
			throws SQLException {
		String query = "SELECT id, name, size FROM flashcardCollection WHERE name=? ORDER BY id DESC LIMIT 1";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					FlashcardCollection collection = new FlashcardCollection();
					collection.setId(rs.getInt("id"));
					collection.setName(rs.getString("name"));
					return collection;
				}
			}
		}
		return null;
	}

	public void updateFlashcardCollection(int id, String name)
			throws SQLException {
		String query = "UPDATE flashcardCollection SET name=? WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, name);
			statement.setInt(2, id);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Updating flashcard collection failed, no rows affected.");
			}
		}
	}

	public void incrementFlashcardCollection(int id) throws SQLException {
		String query = "UPDATE flashcardCollection SET size=size+1 WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Updating flashcard collection failed, no rows affected.");
			}
		}
	}

	public void decrementFlashcardCollection(int id) throws SQLException {
		String query = "UPDATE flashcardCollection SET size=size-1 WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Updating flashcard collection failed, no rows affected.");
			}
		}
	}

	public void deleteFlashcardCollection(int id) throws SQLException {
		String query = "DELETE FROM flashcardCollection WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException(
						"Deleting flashcard collection failed, no rows affected.");
			}
		}
	}

	public int getFirstCollectionId() {
		String query = "SELECT * FROM flashcardCollection WHERE userId = ? LIMIT 1;";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, Resource.userId);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static FlashcardCollectionDao getDao(){
		if(dao == null){
			dao = new FlashcardCollectionDao();
		}
		return dao;
	}
}
