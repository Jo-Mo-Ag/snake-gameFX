module email.com.gmail.youssefagagg.snake_gameFX {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;

    opens email.com.gmail.youssefagagg.snake_gameFX to javafx.fxml;
    exports email.com.gmail.youssefagagg.snake_gameFX;
}
