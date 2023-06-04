package dbUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import factory.DefaultFlashcardFactory;
import factory.FlashcardFactory;
import dbUtil.Resource;
import models.Flashcard;

public class FlashcardDao {
	private final Connection connection = DatabaseConnector.getConnection();
	private static final FlashcardFactory flashcardFactory = DefaultFlashcardFactory.getFactory();
	private static FlashcardDao fdao;

	public FlashcardDao() {
    }

    public void addFlashcard(Flashcard flashcard, int parentId) throws SQLException {
        
            PreparedStatement statement = connection.prepareStatement("INSERT INTO flashcard (front, back, parentId) VALUES (?, ?, ?)");
            statement.setString(1, flashcard.getFront());
            statement.setString(2, flashcard.getBack());
            statement.setInt(3, parentId);
            try {
                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating flashcard failed, no rows affected.");
                }
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                    	flashcard.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating flashcard failed, no ID obtained.");
                    }
                }
                FlashcardCollectionDao dao = FlashcardCollectionDao.getDao();
                dao.incrementFlashcardCollection(parentId);
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
    }

    public Flashcard getFlashcardById(int id) {
        Flashcard flashcard = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM flashcard WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String front = resultSet.getString("front");
                String back = resultSet.getString("back");
                flashcard = flashcardFactory.createFlashcard(front, back);
                flashcard.setId(id);
            }
        } catch (SQLException e) {
            System.out.println("Error: Failed to get flashcard from database");
        }
        return flashcard;
    }
    public List<Flashcard> getAllFlashcards() {
        List <Flashcard> flashcards = new ArrayList<>();
        try {
            System.out.println(connection == null);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM flashcard");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flashcard flashcard = flashcardFactory.createFlashcard(resultSet.getString("front"), resultSet.getString("back"));
                flashcards.add(flashcard);
            }
        } catch (SQLException e) {
            System.out.println("Error: Failed to get flashcard from database");
        }
        return flashcards;
    }
    public List<Flashcard> getFlashcardsByParent(int parentId) {
        List <Flashcard> flashcards = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM flashcard WHERE parentId = ?");
            statement.setInt(1, parentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flashcard flashcard = flashcardFactory.createFlashcard(resultSet.getInt("id"), resultSet.getString("front"), resultSet.getString("back"));
                flashcard.setId(resultSet.getInt("id"));
                flashcard.setFront(resultSet.getString("front"));
                flashcard.setBack(resultSet.getString("back"));
                flashcards.add(flashcard);
            }
        } catch (SQLException e) {
            System.out.println("Error: Failed to get flashcard from database");
        }
        return flashcards;
    }

    public void updateFlashcard(Flashcard flashcard) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE flashcards SET front = ?, back = ? WHERE id = ?");
            statement.setString(1, flashcard.getFront());
            statement.setString(2, flashcard.getBack());
            statement.setInt(3, flashcard.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: Failed to update flashcard in database");
        }
    }

    public void deleteFlashcard(int id, int parentId) throws SQLException {
    	System.out.println(id + ", " + parentId);
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM flashcard WHERE id = ?");
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting flashcard failed, no rows affected.");
            }
            FlashcardCollectionDao dao = FlashcardCollectionDao.getDao();
            dao.decrementFlashcardCollection(parentId); // decrement size before checking affected rows
        } catch (SQLException e) {
            throw e;
        }
    }
	public static FlashcardDao getDao(){
		if(fdao == null){
			fdao = new FlashcardDao();
		}
		return fdao;
	}
}
