import java.awt.*;
public class Ball {
    private int BallX ;
    private int BallY;
    private int BALL_WIDTH;
    private int BALL_HEIGHT;
    private int xDir, yDir;
    private Color color;
    private boolean onScreen;
    
    
   
    public Ball(int ballX, int ballY, int bALL_WIDTH, int bALL_HEIGHT, int xDir, int yDir, Color color) {
        BallX = ballX;
        BallY = ballY;
        BALL_WIDTH = bALL_WIDTH;
        BALL_HEIGHT = bALL_HEIGHT;
        this.xDir = xDir;
        this.yDir = yDir;
        this.color = color;
        setOnScreen(true);
    }
    public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(BallX, BallY, BALL_WIDTH, BALL_HEIGHT);
       
	}
    public void move() {
		BallX += xDir;
		BallY += yDir;
	}
    public void reset() {
		BallX =245;
        BallY=245;
		xDir = 1;
		yDir = -1;
	}
    public int getBallX() {
        return BallX;
    }
    public void setBallX(int ballX) {
        BallX = ballX;
    }
    public int getBallY() {
        return BallY;
    }
    public void setBallY(int ballY) {
        BallY = ballY;
    }
    public int getBALL_WIDTH() {
        return BALL_WIDTH;
    }
    public void setBALL_WIDTH(int bALL_WIDTH) {
        BALL_WIDTH = bALL_WIDTH;
    }
    public int getBALL_HEIGHT() {
        return BALL_HEIGHT;
    }
    public void setBALL_HEIGHT(int bALL_HEIGHT) {
        BALL_HEIGHT = bALL_HEIGHT;
    }
    public int getxDir() {
        return xDir;
    }
    public void setxDir(int xDir) {
        this.xDir = xDir;
    }
    public int getyDir() {
        return yDir;
    }
    public void setyDir(int yDir) {
        this.yDir = yDir;
    }
    public boolean isOnScreen() {
        return onScreen;
    }
    public void setOnScreen(boolean onScreen) {
        this.onScreen = onScreen;
    }
    
}
