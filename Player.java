import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Player {
    GamePanel gp;
    KeyHandler keyH;
    
    public int x, y;
    public int speed = 5;
    public int size = 48;
    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
    }
    
    public void setDefaultValues() {
        x = (gp.screenWidth - size) / 2;
        y = (gp.screenHeight - size) / 2;
    }
    
    public void update() {
        if (keyH.upPressed) y -= speed;
        if (keyH.downPressed) y += speed;
        if (keyH.leftPressed) x -= speed;
        if (keyH.rightPressed) x += speed;
        
        // Giới hạn di chuyển
        if (x < 0) x = 0;
        if (x > gp.screenWidth - size) x = gp.screenWidth - size;
        if (y < 0) y = 0;
        if (y > gp.screenHeight - size) y = gp.screenHeight - size;
        
        // Reset game nếu nhấn R
        if (gp.gameFinished && keyH.rPressed) {
            gp.gameFinished = false;
            gp.score = 0;
            setDefaultValues();
            gp.generateItems();
            gp.generateObstacles();
        }
    }
    
    public boolean collidesWith(Object object) {
        Rectangle playerRect = new Rectangle(x, y, size, size);
        
        if (object instanceof Item) {
            Item item = (Item) object;
            Rectangle itemRect = new Rectangle(item.x, item.y, item.size, item.size);
            return playerRect.intersects(itemRect);
        } else if (object instanceof Obstacle) {
            Obstacle obstacle = (Obstacle) object;
            Rectangle obstacleRect = new Rectangle(obstacle.x, obstacle.y, obstacle.size, obstacle.size);
            return playerRect.intersects(obstacleRect);
        }
        
        return false;
    }
    
    public void draw(Graphics2D g2) {
        // Hiệu ứng nhấp nháy khi di chuyển
        if ((keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) && 
            System.currentTimeMillis() % 200 < 100) {
            g2.setColor(new Color(255, 215, 0)); // Màu vàng khi di chuyển
        } else {
            g2.setColor(new Color(255, 182, 193)); // Màu hồng pastel
        }
        
        // Vẽ player với hiệu ứng bo tròn
        g2.fillOval(x, y, size, size);
        
        // Vẽ mắt
        g2.setColor(Color.BLACK);
        g2.fillOval(x + 12, y + 15, 8, 8);
        g2.fillOval(x + 28, y + 15, 8, 8);
        
        // Vẽ miệng cười
        g2.setStroke(new BasicStroke(2));
        g2.drawArc(x + 12, y + 25, 24, 10, 0, -180);
        
        // Hiệu ứng ánh sáng
        g2.setColor(new Color(255, 255, 255, 100));
        g2.fillOval(x + 5, y + 5, 15, 15);
    }
}