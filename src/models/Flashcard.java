package models;
public class Flashcard {
	private int id;
    private String front;
    private String back;
    private int parentId;
    
    public Flashcard(){
    	this.front ="";
    	this.back ="";
    }
    public Flashcard(String front, String back){
    	this.front =front;
    	this.back =back;
    }
    public Flashcard(int id, String front, String back) {
    	this.id = id;
    	this.front =front;
    	this.back =back;
	}
	public String getFront() {
		return front;
	}
	public void setFront(String front) {
		this.front = front;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParent(int parentId) {
		this.parentId = parentId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String toString() {
        return "Front: " + this.front + ", Back: " + this.back;
    }
	public boolean isCorrect(String response) {
		// TODO Auto-generated method stub
		return false;
	}
}
