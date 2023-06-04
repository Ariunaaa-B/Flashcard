package models;

public class UserAchievements {
    private int flashcardsViewed;
    private int collectionsViewed;
    private int gamesPlayed;
    private int testsTaken;
    private int testsPassed;

    public UserAchievements() {
        this.flashcardsViewed = 0;
        this.collectionsViewed = 0;
        this.gamesPlayed = 0;
        this.testsTaken = 0;
        this.testsPassed = 0;
    }
    
    

    public UserAchievements(int flashcardsViewed, int collectionsViewed,
			int gamesPlayed, int testsTaken, int testsPassed) {
		super();
		this.flashcardsViewed = flashcardsViewed;
		this.collectionsViewed = collectionsViewed;
		this.gamesPlayed = gamesPlayed;
		this.testsTaken = testsTaken;
		this.testsPassed = testsPassed;
	}

	public void incrementFlashcardsViewed() {
        this.flashcardsViewed++;
    }

    public void incrementCollectionsViewed() {
        this.collectionsViewed++;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void incrementTestsTaken() {
        this.testsTaken++;
    }

    public void incrementTestsPassed() {
        this.testsPassed++;
    }

    public int getFlashcardsViewed() {
        return this.flashcardsViewed;
    }

    public int getCollectionsViewed() {
        return this.collectionsViewed;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getTestsTaken() {
        return this.testsTaken;
    }

    public int getTestsPassed() {
        return this.testsPassed;
    }
}

