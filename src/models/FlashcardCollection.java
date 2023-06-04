package models;

import dbUtil.Resource;

public class FlashcardCollection {
	private int id;
    private String name = "";
    private int size = 0;
    private int userId = Resource.userId;
    

    public FlashcardCollection() {
    }
    public FlashcardCollection(String name) {
    	this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    void updateFlashcard(Flashcard flashcard, String front, String back){
		flashcard.setFront(front);
		flashcard.setBack(back);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    public int getSize(){
    	return size;
    }
    public String toString(){
    	return "id = " + id + "\nname = "+name + "\nsize = "+size;
    }
    
}
