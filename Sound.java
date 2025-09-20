/*import javax.sound.sampled.*;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[10];
    
    public Sound() {
        soundURL[0] = getClass().getResource("/sound/chill_music.wav");
        soundURL[1] = getClass().getResource("/sound/collect.wav");
        soundURL[2] = getClass().getResource("/sound/win.wav");
        soundURL[3] = getClass().getResource("/sound/hit.wav");
    }
    
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Lỗi âm thanh: " + e.getMessage());
        }
    }
    
    public void play() {
        clip.start();
    }
    
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop() {
        clip.stop();
    }
}*/