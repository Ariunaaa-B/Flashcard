package factory;
import models.Flashcard;

public interface FlashcardFactory {
    Flashcard createFlashcard(String front, String back);
    Flashcard createFlashcard(int id, String front, String back);
}