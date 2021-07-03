package email.com.gmail.youssefagagg.snake_gameFX;

public enum Level
{
	EASY(110),
	INTERMEDIATE(90),
	HARD(70);
	private final int lev;


Level(int i) {
	// TODO Auto-generated constructor stub
	lev=i;
}
public int getLevel() {return lev; }
}