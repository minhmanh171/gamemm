import java.awt.*;
public class Paddle {
    
    private int padX ;
    private int PadY;
    private int pad_width;
    private int pad_height;
    private Color color;
   
 public Paddle(int padX, int padY, int pad_width, int pad_height, Color color) {
        this.padX = padX;
        this.PadY = padY;
        this.pad_width = pad_width;
        this.pad_height = pad_height;
        this.color = color;
    }
    public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(padX, PadY, pad_width, pad_height);
	}
    public void reset() {
		padX = 225;
        PadY = 450;
	}
    public boolean vaCham(int x, int y) {
        int topY = PadY - 12;
        if ((x >= padX-12) && (x <= padX + pad_width+12) && (y == topY) ) {
            return true;
        }
        return false;
    }
    
    public int getPadX() {
        return padX;
    }
    public void setPadX(int padX) {
        this.padX = padX;
    }
    public int getPadY() {
        return PadY;
    }
    public void setPadY(int padY) {
        PadY = padY;
    }
    public int getPad_width() {
        return pad_width;
    }
    public void setPad_width(int pad_width) {
        this.pad_width = pad_width;
    }
    public int getPad_height() {
        return pad_height;
    }
    public void setPad_height(int pad_height) {
        this.pad_height = pad_height;
    }
    

}
