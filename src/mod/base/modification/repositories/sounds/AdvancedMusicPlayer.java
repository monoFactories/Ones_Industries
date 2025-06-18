package mod.base.modification.repositories.sounds;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AdvancedMusicPlayer {
    public final static AdvancedMusicPlayer singleMediaPlayer = new AdvancedMusicPlayer();
    private MediaPlayer media;
    private boolean isComplete;
    public AdvancedMusicPlayer() {
        isComplete = true;
    }
    public synchronized void play (Media newMedia, double volumePower) {
        if (newMedia != null) {
            if (isComplete) {
                if (media != null)
                    media.dispose();
                media = new MediaPlayer(newMedia);
                media.setVolume(volumePower);
                media.setOnEndOfMedia(() -> {
                    media.dispose();
                    isComplete = true;
                });
                isComplete = false;
                media.play();
            }
        }
    }
    public boolean isComplete () {
        return isComplete;
    }
    public void setComplete (boolean b) {
        isComplete = b;
    }
    public static void init(){}
}
