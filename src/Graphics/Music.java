package Graphics;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    Clip clip;
    AudioInputStream sound;
    public Music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File music = new File("C:/Users/NoteBook/Downloads/Music/Obsessed With Your Eyes.wav");
        sound = AudioSystem.getAudioInputStream(music);
        clip = AudioSystem.getClip();
        clip.open(sound);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void play() {
        clip.start();
    }
    public void stop() throws IOException {
        sound.close();
        clip.close();
        clip.stop();
    }
}