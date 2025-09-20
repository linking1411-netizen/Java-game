import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[10];
    
    public Sound() {
        // Khởi tạo các đường dẫn âm thanh
        soundURL[0] = getClass().getResource("/sound/drill_music_wave.wav");
        soundURL[1] = getClass().getResource("/sound/Roblox_oof_sound_effect.wav");
        soundURL[2] = getClass().getResource("/sound/Victory_Sound_Effect.wav");
    }
    
    public void setFile(int i) {
        try {
            // Kiểm tra xem URL có tồn tại không
            if (soundURL[i] == null) {
                System.out.println("Không tìm thấy file âm thanh với index: " + i);
                return;
            }
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Lỗi âm thanh: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Đặt lại vị trí về đầu
            clip.start();
        }
    }
    
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
    
    public void close() {
        if (clip != null) {
            clip.close();
        }
    }
    
    // Phương thức mới: kiểm tra xem clip đang phát hay không
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}