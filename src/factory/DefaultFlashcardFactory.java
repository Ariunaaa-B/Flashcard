package factory;
import models.Flashcard;

public class DefaultFlashcardFactory implements FlashcardFactory {
	private static DefaultFlashcardFactory factory;
	private DefaultFlashcardFactory() {
	}
    @Override
    public Flashcard createFlashcard(String front, String back) {
    	return new Flashcard(front, back);
    }

	@Override
	public Flashcard createFlashcard(int id, String front, String back){
		return new Flashcard(id, front, back);
	}
	
	public static DefaultFlashcardFactory getFactory(){
		if(factory == null){
			factory = new DefaultFlashcardFactory();
		}
		return factory;
	}
}