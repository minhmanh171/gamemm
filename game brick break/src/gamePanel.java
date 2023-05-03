import java.awt.*;
import javax.swing.*;
import java.lang.Thread;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.*;
import java.io.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class gamePanel extends JPanel implements Runnable{
    private int score = 0,waitTime = 6,xSpeed=1,level = 1,lives = 3;
	private int row=6,col=8;
	private int countBrick = row*col;
    private String playerName;
    private Thread game;
	private int diem = 0;
    private Paddle paddle;
	private Ball ball;
	private connectJdbc connect;
	private AudioInputStream audio;
	private Clip clip;
    private AtomicBoolean isPaused = new AtomicBoolean(true);
    private Brick[][] brick = new Brick[col][row];
	private int brick_width =50 ;
	private int brick_height= 25;
	public gamePanel(int width,int height) {
        super.setSize(width, height);
        addKeyListener(new BoardListener());
	
		setFocusable(true);
        setGiaTri();
        playMusic();
        makeBricks();
		cheatcode();
        startGame();
        pauseGame();
		connect = new connectJdbc();
		diem = connect.getHighestScore();
		// connect.close();
		
       
        
    }
	private void setGiaTri() {
		paddle = new Paddle(225,450,90,10,Color.white);
		ball = new Ball(245,245,12,12,1,-1,Color.RED);
		
	}
	

	private void makeBricks() {
        for(int i = 0; i < col; i++) {
    for(int j = 0; j < row; j++) {
		brick[i][j] = new Brick(i*brick_width+50,((j*brick_height)+(brick_width/2)+20),brick_width-5,brick_height-5,Color.ORANGE);
    }
}
		
    }

    private void startGame() {
        game = new Thread(this);
		game.start();
        isPaused.set(true);
       
    }
	private void pauseGame() {
        isPaused.set(!isPaused.get());
    }
	private void playMusic() {
        try {
			audio = AudioSystem.getAudioInputStream(new File("D:/bao cao/tesssw/lib/wav/Two.wav").getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	private void cheatcode() {
		
	do {
    playerName = JOptionPane.showInputDialog(null, "Nhập tên:", "Game by Nmm", JOptionPane.QUESTION_MESSAGE);
    if (playerName == null) {
        System.exit(0);
    }
	if (playerName.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Tên không được bỏ trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
		} while (playerName.trim().isEmpty());
	
		if (playerName.toUpperCase().equals("ADMIN")) {
			lives = 50;
			JOptionPane.showMessageDialog(null, "Cheat thanh cong", "Add 50 lives", JOptionPane.INFORMATION_MESSAGE);
		}
    }
@Override
    public void run() {
       
        while(true){

            if (isPaused.get()) {
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                continue; 
            }
			
            int x = ball.getBallX();
			int y = ball.getBallY();
			checkLevel();
            checkPaddle(x, y);
            checkWall(x, y);
            checkBrick(x, y);
			checkLive(x,y);
			
            ball.move();
            
            repaint();
            try {
				Thread.sleep(waitTime);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
        }
        
    }
  
	private void checkLevel() {
		if(level == 2){
			waitTime = 4;
		}
		if(level == 3){
			waitTime = 3;
		}
		
	}

	private void checkLive(int x, int y) {
		if(countBrick == 0){
	
			ball.reset();
			countBrick = 50;
			makeBricks();
			paddle.reset();
			level++;
			repaint();
			isPaused.set(true);
		
		}
		if(y > paddle.getPadY() +10){
			lives--;
			ball.reset();
			paddle.reset();
			repaint();
			isPaused.set(true);
		
		}
	}
	
    private void checkPaddle(int x, int y) {
        if (paddle.vaCham(x, y) && ball.getxDir() < 0) {
			ball.setyDir(-1);
			xSpeed = -1;
			ball.setxDir(xSpeed);
		}
		if (paddle.vaCham(x, y) && ball.getxDir() > 0) {
			ball.setyDir(-1);
			xSpeed = 1;
			ball.setxDir(xSpeed);
		}

		if (paddle.getPadX() <= 0) {
			paddle.setPadX(0);
		}
		if (paddle.getPadX() + paddle.getPad_width() >= getWidth()-190) {
			paddle.setPadX(getWidth() -190- paddle.getPad_width());
		}
    }

    private void checkWall(int x, int y) {
        if (x >= getWidth()-190 - ball.getBALL_WIDTH()) {
			xSpeed = - Math.abs(xSpeed);
			ball.setxDir(xSpeed);
		}
		if (x <= 0) {
			xSpeed = -xSpeed;
			ball.setxDir(xSpeed);
		}
		if (y <= 0) {
			ball.setyDir(1);
		}
		
    }
    private void checkBrick(int x, int y) {
        for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				if (brick[i][j].hitBottom(x, y)) {
					ball.setyDir(1);
					if (brick[i][j].isDestroyed()) {
						score += 50;
						countBrick--;
					}
				}
				if (brick[i][j].hitLeft(x, y)) {
					xSpeed = -xSpeed;
					ball.setxDir(xSpeed);
					if (brick[i][j].isDestroyed()) {
						countBrick--;
						score += 50;
					}
				}
				if (brick[i][j].hitRight(x, y)) {
					xSpeed = -xSpeed;
					ball.setxDir(xSpeed);
					if (brick[i][j].isDestroyed()) {
						countBrick--;
						score += 50;
					}
				}
				if (brick[i][j].hitTop(x, y)) {
					ball.setyDir(-1);
					if (brick[i][j].isDestroyed()) {
						countBrick--;
						score += 50;
					}
				}
			}
		}
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,getWidth()-190, getHeight());
        paddle.draw(g);
		ball.draw(g);
        for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				brick[i][j].draw(g);
			}
		}
		if(countBrick ==0 && lives>0 ){
			for (int i = 0; i < col; i++) {
				for (int j = 0; j < row; j++) {
					brick[i][j].draw(g);
				}
			}
		}
		

        g.setColor(Color.black);
		g.setFont(new Font("NewellsHand", Font.BOLD, 18));
		g.drawString("Score max : " + diem, getWidth()-150,(getHeight()/5)-20);
        g.drawString("Lives: " + lives, getWidth()-150,(getHeight()/5)-50);
		g.drawString("Score: " + score, getWidth()-150,2*(getHeight()/5)-50);
		g.drawString("Level: " + level, getWidth()-150,3*(getHeight()/5)-50);
		g.drawString("Player: " + playerName, getWidth()-150,4*(getHeight()/5)-50);
		g.drawString("Brick : " + countBrick, getWidth()-150,5*(getHeight()/5)-50);

		g.setColor(Color.red);
		g.fillRect(getWidth()-190, 0, 2, getHeight());
		if (lives == 0 || (level == 3 && countBrick == 0)) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("NewellsHand", Font.BOLD, 20));
			g.fillRect(0,0,getWidth(),getHeight());
			g.setColor(Color.YELLOW);
			g.drawString("Name: " + playerName ,275 ,100);
			g.drawString("Score: "+score,275,150);
			g.setFont(new Font("NewellsHand", Font.BOLD, 50));
			g.drawString("Game Over",200 ,300 );
			g.setFont(new Font("NewellsHand", Font.BOLD, 18));
			g.drawString("Nhan space de tiep tuc ", 250, getHeight()-50);

			connect.addScores(playerName, score, level);
			connect.close();

			}
	
		
    }
   

	private void newGame() {
		lives=3;
		score=0;
		countBrick= row*col;
		level = 1;
		paddle.reset();
		makeBricks();
		isPaused.set(true);
		for (int i = 0; i < col; i++) {
			for (int j = 0; j < row; j++) {
				brick[i][j].setDestroyed(false);
			}
		}
		diem = connect.getHighestScore();
		connect.close();
}
    
    private class BoardListener extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent ke) {
			int key = ke.getKeyCode();
            
    if (key == KeyEvent.VK_SPACE) {
        if(lives > 0){
			pauseGame();
		
			if(isPaused.get()){
				
			try {
				clip.stop();
				clip.close();
				audio.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
			else playMusic();
		}
		else newGame();
		
    }
			
			if (key == KeyEvent.VK_LEFT) {
				paddle.setPadX(paddle.getPadX() - 50);
               
               
			}
			if (key == KeyEvent.VK_RIGHT) {
				paddle.setPadX(paddle.getPadX() + 50);
                
              
			}
		}
		@Override
		public void keyReleased(KeyEvent ke) {
			int key = ke.getKeyCode();
			if (key == KeyEvent.VK_LEFT) {
				paddle.setPadX(paddle.getPadX());
			}
			if (key == KeyEvent.VK_RIGHT) {
				paddle.setPadX(paddle.getPadX());
			}
		}
	}
	


}
