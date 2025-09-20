import java.awt.*;
import java.util.Random;

public class Obstacle {
    public int x, y;
    public int size = 40;
    public int speedX, speedY;
    private Random random = new Random();
    private Color color;
    
    public Obstacle(int x, int y, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.color = new Color(200, 50, 50);
    }
    
    public void update() {
        x += speedX;
        y += speedY;
        
        if (x <= 0 || x >= 800 - size) speedX *= -1;
        if (y <= 0 || y >= 600 - size) speedY *= -1;
    }
    
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, size, size);
        
        if (System.currentTimeMillis() % 500 < 250) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(x - 2, y - 2, size + 4, size + 4);
        }
    }
}