//Amber Williams and Dom Ketchens
//Project 4: Maze Game - Music

// Import the necessary packages
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Music {
    private Clip clip;

    // Constructor to load the sound file
    public Music(String filePath) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to play the music in a loop
 	public void playLoop() {
    if (clip != null) {
        clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop continuously without resetting   
    	}
    }

    //Method to play sfx once
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0); // Reset to the start for consistent playback
            clip.start();
        }
    }

    // Method to stop the music and sfx
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Method to restart the music and sfx from the beginning
    public void restart() {
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }
}
