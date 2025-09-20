import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    // Cài đặt màn hình
    public final int screenWidth = 800;
    public final int screenHeight = 600;
    
    // FPS
    int FPS = 60;
    
    // Hệ thống
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    Player player = new Player(this, keyH);
    /*Sound music = new Sound();
    Sound se = new Sound();*/
    
    // Vật phẩm và chướng ngại vật
    public ArrayList<Item> items = new ArrayList<>();
    public ArrayList<Obstacle> obstacles = new ArrayList<>();
    Random random = new Random();
    
    // Điểm số
    public int score = 0;
    public boolean gameFinished = false;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(135, 206, 235));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        generateItems();
        generateObstacles();
    
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void generateItems() {
        items.clear();
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(screenWidth - 30);
            int y = random.nextInt(screenHeight - 30);
            items.add(new Item(x, y));
        }
    }
    
    public void generateObstacles() {
        obstacles.clear();
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(screenWidth - 40);
            int y = random.nextInt(screenHeight - 40);
            int speedX = random.nextInt(3) + 1;
            int speedY = random.nextInt(3) + 1;
            obstacles.add(new Obstacle(x, y, speedX, speedY));
        }
    }
    
    /*public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void stopMusic() {
        music.stop();
    }
    
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }*/

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void update() {
        if (!gameFinished) {
            player.update();
            
            // Kiểm tra thu thập vật phẩm
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                if (player.collidesWith(item)) {
                    items.remove(i);
                    score += 10;
                    
                    if (items.isEmpty()) {
                        gameFinished = true;
                       
                    }
                    break;
                }
            }
            
            // Cập nhật chướng ngại vật
            for (Obstacle obstacle : obstacles) {
                obstacle.update();
                if (player.collidesWith(obstacle)) {
                   
                    player.setDefaultValues();
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Vẽ background
        drawBackground(g2);
        
        // Vẽ vật phẩm
        for (Item item : items) {
            item.draw(g2);
        }
        
        // Vẽ chướng ngại vật
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2);
        }
        
        // Vẽ player
        player.draw(g2);
        
        // Vẽ UI
        drawUI(g2);
        
        g2.dispose();
    }
    
    public void drawBackground(Graphics2D g2) {
        // Bầu trời
        GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 235), 0, screenHeight, new Color(176, 226, 255));
        g2.setPaint(gradient);
        g2.fillRect(0, 0, screenWidth, screenHeight);
        
        // Mây
        g2.setColor(Color.WHITE);
        for (int i = 0; i < 5; i++) {
            int x = (i * 200 + (int)(System.currentTimeMillis() / 50) % 200) % (screenWidth + 200) - 100;
            int y = 50 + i * 40;
            drawCloud(g2, x, y);
        }
        
        // Mặt trời
        g2.setColor(Color.YELLOW);
        g2.fillOval(700, 50, 80, 80);
        g2.setColor(new Color(255, 255, 0, 100));
        for (int i = 0; i < 8; i++) {
            int angle = i * 45 + (int)(System.currentTimeMillis() / 20) % 360;
            int x2 = 740 + (int)(60 * Math.cos(Math.toRadians(angle)));
            int y2 = 90 + (int)(60 * Math.sin(Math.toRadians(angle)));
            g2.fillOval(x2 - 15, y2 - 15, 30, 30);
        }
    }
    
    public void drawCloud(Graphics2D g2, int x, int y) {
        g2.setColor(new Color(255, 255, 255, 200));
        g2.fillOval(x, y, 60, 30);
        g2.fillOval(x + 20, y - 10, 70, 40);
        g2.fillOval(x + 40, y, 60, 30);
    }
    
    public void drawUI(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Điểm: " + score, 20, 40);
        
        if (gameFinished) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, 0, screenWidth, screenHeight);
            
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 48));
            g2.drawString("Hoàn thành!", screenWidth/2 - 120, screenHeight/2 - 50);
            
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            g2.drawString("Điểm số: " + score, screenWidth/2 - 80, screenHeight/2 + 20);
            
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            g2.drawString("Nhấn R để chơi lại", screenWidth/2 - 100, screenHeight/2 + 80);
        }
    }
}