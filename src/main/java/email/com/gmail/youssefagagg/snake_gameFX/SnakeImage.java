package email.com.gmail.youssefagagg.snake_gameFX;

import javafx.scene.image.Image;

public class SnakeImage {
    private Image image;
    private int x;
    private int y;

    @SuppressWarnings("exports")
	public SnakeImage(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    @SuppressWarnings("exports")
	public Image getImage() {
        return image;
    }

    @SuppressWarnings("exports")
	public void setImage(Image image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
