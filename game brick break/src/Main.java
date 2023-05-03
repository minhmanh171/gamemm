
import javax.swing.JFrame;

public class Main {
    public static final int WINDOW_WIDTH = 700;
	public static final int WINDOW_HEIGHT = 500;
    private static gamePanel gp;
    
   public static void main(String[] args) {
        JFrame frame =  new JFrame("game Brick breaker");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        gp = new gamePanel(WINDOW_WIDTH, WINDOW_HEIGHT);
       
        frame.add(gp);
        gp.requestFocusInWindow();
        frame.setVisible(true);
   }
}

