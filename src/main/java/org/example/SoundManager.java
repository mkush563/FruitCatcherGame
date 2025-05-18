package org.example;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    public static void playSound(String soundFileName) {
        try {
            File soundFile = new File("assets/sound/" + soundFileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}