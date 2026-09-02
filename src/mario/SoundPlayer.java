package mario;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    public static void play(String soundFilePath) {
        new Thread(() -> {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
