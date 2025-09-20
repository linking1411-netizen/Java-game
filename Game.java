import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {
    private Timer timer;
    private Sound backgroundMusic;
    private Sound effectSound;
    
    public Game() {
        // Khởi tạo âm thanh
        backgroundMusic = new Sound();
        effectSound = new Sound();
        
        // Thiết lập và phát nhạc nền
        backgroundMusic.setFile(d:\game\sound.java\chill_music_wave.mp3)d:; // drill_music_wave.wav
        backgroundMusic.loop(); // Phát lặp lại
        
        // Khởi tạo game
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }
    
    // Phương thức khi người chơi chết
    public void playerDie() {
        effectSound.setFile(); // Roblox_oof_sound_effect.wav
        effectSound.play();
        
        // Tạm dừng nhạc nền
        backgroundMusic.stop();
    }
    
    // Phương thức khi người chơi chiến thắng
    public void playerWin() {
        effectSound.setFile(); // Victory_Sound_Effect.wav
        effectSound.play();
        
        // Tạm dừng nhạc nền
        backgroundMusic.stop();
    }
    
    // Phương thức để tiếp tục nhạc nền
    public void resumeBackgroundMusic() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.setFile();
            backgroundMusic.loop();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ game ở đây
        g.setColor(Color.WHITE);
        g.drawString("Game Đang Chạy - Nhấn phím để nghe hiệu ứng âm thanh", 250, 300);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Game logic
        repaint();
    }
    
    // Phương thức để xử lý sự kiện bàn phím
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_D) {
            playerDie(); // Phát âm thanh chết
        } else if (key == KeyEvent.VK_W) {
            playerWin(); // Phát âm thanh thắng
        } else if (key == KeyEvent.VK_R) {
            resumeBackgroundMusic(); // Tiếp tục nhạc nền
        }
    }
    
    // Phương thức dọn dẹp tài nguyên
    public void cleanup() {
        backgroundMusic.stop();
        backgroundMusic.close();
        effectSound.close();
        timer.stop();
    }
    
    // Main method để chạy game
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game với Âm thanh");
        Game game = new Game();
        
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Thêm listener cho bàn phím
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                game.keyPressed(e);
            }
        });
        frame.requestFocus();
        
        // Thêm window listener để dọn dẹp khi đóng cửa sổ
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.cleanup();
            }
        });
    }
}