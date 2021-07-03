package email.com.gmail.youssefagagg.snake_gameFX;

import java.util.Optional;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;



interface CallBackFromThread{
	void updateScore(int Score);
	void gameEnd(int score);
	void gameStart();
}
public class SnackGameController implements CallBackFromThread{
	

    @FXML
    private Canvas canvas;

    @FXML
    private VBox radioButtonVBoxContainer;

    @FXML
    private RadioButton easyRadioButton;

    @FXML
    private RadioButton intermediateRadioButton;

    @FXML
    private RadioButton hardRadioButton;
    
    @FXML
    private ToggleGroup leveltoggleGroup;

    @FXML
    private Label highSoreLabel;

    @FXML
    private Label yourScoreLabel;
    
    private SnakeThread st;
    
    int highScore;
    private Level level=Level.EASY;
    public void initialize() 
    {
    	
    	easyRadioButton.setUserData(Level.EASY);
    	intermediateRadioButton.setUserData(Level.INTERMEDIATE);
    	hardRadioButton.setUserData(Level.HARD);
    	
    
    	
    	
		
    	 st=new SnakeThread(canvas,level,this);
    	//yourscoreLable.setText(st.getScore());
    }
    

    @FXML
    void levelRadioButtonSelected(ActionEvent event) {
    	st.setSpeed((Level)leveltoggleGroup.getSelectedToggle().getUserData());
    	   

    }

    @FXML
    void onKeyPressed(KeyEvent event) {
    	switch (event.getCode()) {
		case D : case RIGHT : 
			radioButtonVBoxContainer.setDisable(true);
			if(!st.isLeft()&&st.isCheck()) 
			{
			    st.setRight(true);
			    st.setLeft(false);
			    st.setUp(false);
			    st.setDown(false);
			    st.setCheck(false);
			}
			//System.out.println("r");
			
			
			break;
		case A : case LEFT : 
			radioButtonVBoxContainer.setDisable(true);
			if(!st.isRight()&&st.isCheck()) 
			{   
			    st.setRight(false);
			    st.setLeft(true);
			    st.setUp(false);
			    st.setDown(false);
			    st.setCheck(false);
			}
			
			
			break;
		case W : case UP : 
			radioButtonVBoxContainer.setDisable(true);
			if(!st.isDown()&&st.isCheck()) 
			{   
			    st.setRight(false);
			    st.setLeft(false);
			    st.setUp(true);
			    st.setDown(false);
			    st.setCheck(false);
			}
			
			
			break;
		case S: case DOWN :
			radioButtonVBoxContainer.setDisable(true);
			if(!st.isUp()&&st.isCheck()) 
			{   
				st.setRight(false);
			    st.setLeft(false);
			    st.setUp(false);
			    st.setDown(true);
			    st.setCheck(false);
			}
			
			
			break;
		case SPACE:
				st.setRight(false);
			    st.setLeft(false);
			    st.setUp(false);
			    st.setDown(false);
			    break;
			    
		case ESCAPE:
			Alert alert=new Alert(AlertType.CONFIRMATION,"do you want to end this game");
			Optional<ButtonType> option=alert.showAndWait();
			option.ifPresent(b->{
				if(b.equals(ButtonType.OK)) {
				st.startGame();
				radioButtonVBoxContainer.setDisable(false);
				}
			});
			
			break;
		default:
		
		
		
			//throw new IllegalArgumentException("Unexpected value: " + event.getCode());
		}

    }


	@Override
	public void updateScore(int Score) {
		yourScoreLabel.setText("your score:"+Score);
		if(highScore<Score)highScore=Score;
		
	}


	@Override
	public void gameEnd(int score) {
		// TODO Auto-generated method stub
		if(highScore<=score) {
			highScore=score;
			highSoreLabel.setText("high score:"+score);}
		radioButtonVBoxContainer.setDisable(false);
		yourScoreLabel.setText("your score:0");
		
	}


	@Override
	public void gameStart() {
		// TODO Auto-generated method stub
		radioButtonVBoxContainer.setDisable(true);
	}

}
