import java.awt.*;
import java.util.Random;

public class Item {
    public int x, y;
    public int size = 30;
    public Color color;
    private Random random = new Random();
    private float pulse = 0;
    
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = new Color(random.nextInt(200) + 55, random.nextInt(200) + 55, random.nextInt(200) + 55);
    }
    
    public void draw(Graphics2D g2) {
        // Hiệu ứng nhấp nháy
        pulse += 0.1f;
        if (pulse > 2 * Math.PI) pulse = 0;
        
        float scale = (float) (0.8f + 0.2f * Math.sin(pulse));
        int currentSize = (int) (size * scale);
        int offset = (size - currentSize) / 2;
        
        // Vẽ vật phẩm
        g2.setColor(color);
        g2.fillOval(x + offset, y + offset, currentSize, currentSize);
        
        // Hiệu ứng ánh sáng
        g2.setColor(Color.WHITE);
        g2.fillOval(x + offset + 5, y + offset + 5, 8, 8);
    }
}