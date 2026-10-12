
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {

    public static void playSound(String filename) {

        try {

            // Create file object
            File soundFile = new File(filename);

            // Check if file exists
            if (!soundFile.exists()) {
                System.out.println("Sound file not found: " + filename);
                return;
            }

            //Create audio stream
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            //Create clip
            Clip clip = AudioSystem.getClip();
            //Open clip
            clip.open(audioIn);
            //Play sound
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            e.printStackTrace();
        }
    }
}