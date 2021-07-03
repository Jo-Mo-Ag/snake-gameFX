package email.com.gmail.youssefagagg.snake_gameFX;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.image.Image;

public class SnakeThread  implements Runnable {


	private final Image food;
	private final Image faceUp;
	private final Image faceDown;
	private final Image faceRight;
	private final Image faceLeft;
	private final Image body;
	private final SnakeImage[] snake =new SnakeImage[440];
	private SnakeImage snakeFood;
	private final ArrayList<PointFood> points=new ArrayList<>(440);
	private boolean right,left,up,down,check;
	private int count;//count the length of snake

	private Level level;
	private int index ; // determint where to draw food by points

	private int score;
	private int highScore;


	private GraphicsContext graphicsContext;
	private int width;
	private int height;
	int size=14;//the height and the width of every image
	CallBackFromThread callToUpdateGUI;
	@SuppressWarnings("exports")
	public SnakeThread(Canvas canvas, Level lev,CallBackFromThread callBack) {

		level=lev;
		callToUpdateGUI=callBack;
		width=(int) canvas.getWidth();
		height=(int) canvas.getHeight();
		graphicsContext=canvas.getGraphicsContext2D();
		food = new Image(getClass().getResource("SnakeImage.png").toString(),
				size, size, false, false);
		faceUp=new Image(getClass().getResource("UpMouth.png").toString(), size, size, false, false);
		faceDown=new Image(getClass().getResource("DownMouth.png").toString(), size, size, false, false);
		faceRight=new Image(getClass().getResource("RightMouth.png").toString(), size, size, false, false);
		faceLeft=new Image(getClass().getResource("LeftMouth.png").toString(), size, size, false, false);
		body=new Image(getClass().getResource("SnakeImage.png").toString(), 
				size, size, false, false);
		createPoints();
		startGame();

		Thread t = new Thread(this, "Snake Thread");
		t.start();
	}
	//where should every food be displayed
	private void createPoints(){
		for(int i=0;i<width;i+=size){
			for (int j=0;j<height;j+=size){
				points.add(new PointFood(i,j));
			}
		}
	}
	public void startGame()
	{
		
		right=false;
		left=false;
		up=false;
		down=false;
		check=true;  //check if the loop end
		index=0;
		count=3;
		Collections.shuffle(points);
		score=0;

		snake[0]=new SnakeImage(faceRight, width/2, height/2);
		snake[1]=new SnakeImage(body, width/2-size, height/2);
		snake[2]=new SnakeImage(body, width/2-size*2, height/2);
		snakeFood=new SnakeImage(food,points.get(index).getX(),points.get(index).getY());


		draw();
	}


	private void draw() {
graphicsContext.clearRect(0, 0, width, height);
graphicsContext.drawImage(snakeFood.getImage(),snakeFood.getX(),snakeFood.getY());
		for(int i=0;i<count;i++)
			graphicsContext.drawImage(snake[i].getImage(),snake[i].getX(),snake[i].getY());


		

	}


	@Override
	public void run() {
		while(true)
		{


			if(right)
			{
				//System.out.println(1);
				snake[0].setImage(faceRight);
				moveRight();

			}else if(left)
			{
				snake[0].setImage(faceLeft);
				moveLeft();

			}else if(up)
			{
				snake[0].setImage(faceUp);
				moveUp();

			}else if(down)
			{
				snake[0].setImage(faceDown);
				moveDown();

			}
			draw();
			checkEat();
			checkLose();
			try {
				Thread.sleep(level.getLevel());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			check=true;



		}


	}
	private void moveRight()
	{

		for(int i=count-1;i>0;i--)
		{
			snake[i].setY(snake[i-1].getY());
			snake[i].setX(snake[i-1].getX());

		}
		snake[0].setX(snake[0].getX()+size);

		checkBorder();


	}
	private void moveLeft()
	{

		for(int i=count-1;i>0;i--)
		{
			snake[i].setY(snake[i-1].getY());
			snake[i].setX(snake[i-1].getX());

		}
		snake[0].setX(snake[0].getX()-size);

		checkBorder();



	}
	private void moveUp()
	{


		for(int i=count-1;i>0;i--)
		{
			snake[i].setY(snake[i-1].getY());
			snake[i].setX(snake[i-1].getX());

		}

		snake[0].setY(snake[0].getY()-size);

		checkBorder();



	}
	private void moveDown()
	{


		for(int i=count-1;i>0;i--)
		{
			snake[i].setY(snake[i-1].getY());
			snake[i].setX(snake[i-1].getX());

		}
		snake[0].setY(snake[0].getY()+size);

		checkBorder();
	}
	private void checkBorder()
	{
		if(snake[0].getX()>=width&&(right||up||down))
		{
			snake[0].setX(0);
		}else if(snake[0].getX()<0&&(left||up||down))
		{
			snake[0].setX(width-size);
		}else if(snake[0].getY()>=height&&(down||right||left))
		{

			snake[0].setY(0);

		}else if(snake[0].getY()<0&&(up||right||left))
		{
			snake[0].setY(height-size);
		}

	}

	private void checkEat() {
		if(snake[0].getY()==snakeFood.getY()&&
				snake[0].getX()==snakeFood.getX())
		{

			switch(level) {
			case EASY:
				score+=5;
				break;
			case INTERMEDIATE:
				score+=8;
				break;
			case HARD:
				score+=10;
				break;
			}

			createFood();
			checkWin();
			createBody();
		}
		
		Platform.runLater(()->{
			callToUpdateGUI.updateScore(score);
		});


	}

	private void createFood() {
		index=new Random().nextInt(snake.length);
		int x=points.get(index).getX();
		int y=points.get(index).getY();

		for (int i = 0; i < count; i++) {
			if (x== snake[i].getX() &&
					y== snake[i].getY())
			{
				x=(x+size)%width;
				y=(y+size)%height;
				i=0;
			}

		}
		snakeFood.setX(x);
		snakeFood.setY(y);
	}

	private void createBody()
	{
		snake[count]=new SnakeImage(body, snake[count-1].getX(), snake[count-1].getY());
		count++;
	}
	private void checkLose()
	{
		boolean lose=false;
		for (int i=3;i<count;i++)
		{
			if (snake[0].getY() == snake[i].getY() &&
					snake[0].getX() == snake[i].getX()) {
				lose = true;
				break;
			}
		}
		if(lose)
		{
			setHighScore();
			

			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION, "you lose.\nyour score:"+score);
				alert.show();
				
				callToUpdateGUI.gameEnd(score);
				startGame();
				
			});

		}

	}
	public int getHighScore(){
		return highScore;
	}

	private void setHighScore() {
		if(highScore<score)
			highScore=score;
	}

	private void checkWin()
	{
		if(count==snake.length)
		{
			setHighScore();
			Platform.runLater(() -> {
				Alert alert = new Alert(AlertType.INFORMATION, "you win.\nyour score:"+score);
				alert.show();
				callToUpdateGUI.gameEnd(score);

				startGame();
			});
		}

	}

	public void setLevel(Level lev){ level=lev; }
	public Level getLevel(){ return level; }

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	public void setSpeed(Level lev) {
		level=lev;
		
	}
}
