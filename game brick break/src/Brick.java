import java.awt.*;
public class Brick {
    private int brickX,brickY;
    public int brick_width;
	public int brick_height;
    private boolean destroyed;
    private Color color;
    private int hit = 0;
    public Brick(int brickX, int brickY, int brick_width, int brick_height,Color color) {
        this.brickX = brickX;
        this.brickY = brickY;
        this.brick_width = brick_width;
        this.brick_height = brick_height;
        this.color = color;
     }
   
    public void draw(Graphics g) {
		if (!destroyed) {
			g.setColor(color);
			g.fillRect(brickX, brickY, brick_width, brick_height);
		}
	}
    public void addhit(){
        hit++;
        if(hit == 1) setColor(Color.green);
        if(hit==2) setColor(Color.red);
        if(hit==3) setDestroyed(true);
    }
    public boolean hitBottom(int x, int y) {
        
		if ((x >= brickX-11) && (x <= brickX+ brick_width-1) && (y >= brickY + brick_height-1) && (y <= brickY + brick_height)&& (destroyed == false)) {
            addhit();
            
			return true;
		}
		return false;
	}

	public boolean hitTop(int x, int y) {
        
		if ((x >= brickX-11) && (x <= brickX + brick_width-1) && (y>= brickY-12) && (y<= brickY-11) && (destroyed == false)) {
            addhit();
			return true;
		}
		return false;
	}

	public boolean hitLeft(int x, int y) {
		if ((y >= brickY-12) && (y <= brickY + brick_height) && (x <= brickX-11) && (x >= brickX-12) && (destroyed == false)) {
            addhit();
			return true;
		}
		return false;
	}

	public boolean hitRight(int x, int y) {
		if ((y >= brickY-12) && (y <= brickY + brick_height) && (x <= brickX + brick_width) && (x >= brickX + brick_width-1) && (destroyed == false)) {
            addhit();
			return true;
		}
		return false;
	}
   

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public int getBrickX() {
        return brickX;
    }

    public void setBrickX(int brickX) {
        this.brickX = brickX;
    }

    public int getBrickY() {
        return brickY;
    }

    public void setBrickY(int brickY) {
        this.brickY = brickY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getBrick_width() {
        return brick_width;
    }

    public void setBrick_width(int brick_width) {
        this.brick_width = brick_width;
    }

    public int getBrick_height() {
        return brick_height;
    }

    public void setBrick_height(int brick_height) {
        this.brick_height = brick_height;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

   
    
}
