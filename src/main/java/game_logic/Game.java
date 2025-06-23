package game_logic;

import core.configs.settings.SettingsHandler;
import core.gameActions.Debug;
import game_logic.repositories.Identifier;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import core.gameActions.FileManager;
import core.graphics.objects.NodeParameter;
import core.graphics.graphichandlers.GraphicProcessor;
import core.gameStorage.Loader;
import core.graphics.objects.GraphicComponent;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class Game {

    public static final String GAME_NAME = "AlphaFactory";

    private static final Logger logs = Logger.getLogger(Game.class.getName());

    private static Identifier START_MENU_IDENTIFIER = null;
    static  {
        try {
            File file = new File(FileManager.getDirectoryGame(), "latestLog.txt");
            FileHandler handler = new FileHandler(file.getAbsolutePath());
            handler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return String.format("[%1$tF %1$tT] [%2$s] (thread/%3$s): %4$s %5$s",
                            record.getMillis(),
                            record.getLevel(),
                            Thread.currentThread().getName(),
                            record.getMessage(),
                            record.getThrown() != null ? "\n" + "\t·" + record.getThrown() : ""
                    ) + "\n";
                }
            });
            logs.addHandler(handler);
        } catch (IOException e) {
            logs.log(Level.WARNING, "error when creating the logs file", e);
        }
    }
    public static void init (Stage stage) {
        try {
            addLog("Start Game");
            SettingsHandler.read();
            Debug.debug("setting has reading");
            GraphicProcessor.initialise(stage);
            SettingsHandler.standardExec();
            Debug.debug("start loading mods");
            GraphicComponent load = new LoadMenu(new Identifier("load_menu"));
            GraphicProcessor.Controller.add(load);
            Thread t = new Thread(() -> {
                SettingsHandler.read();
                SettingsHandler.standardExec();
                Loader.loadingMods();
                //GraphicManager.addOnScreen(mainMenuId);
            }, "loader");
            t.start();
        } catch (Throwable e) {
            log(Level.SEVERE, "initialization error , game will be closed", null);
            crash(e);
        }
    }
    public static void addLog (String log) {
        log(Level.INFO, log, null);
    }
    public static void log (Level lvl, String msg, Throwable throwable) {
        logs.log(lvl, msg, throwable);
    }
    public static void crash (Throwable ex) {
        log(Level.SEVERE, "game is crashed", ex);
        try {
            GraphicProcessor.Controller.close();
        } catch (Exception e) {
            log (Level.SEVERE, "Error when close GUI", e);
        }
        System.exit(1);
        System.gc();
    }
    public static void exit () {
        log(Level.INFO, "game is exit", null);
        GraphicProcessor.Controller.close();
        System.gc();
    }
    public static void setStartMenuId (Identifier s) {
        START_MENU_IDENTIFIER = s;
    }
    private static final class LoadMenu extends GraphicComponent {
        private final ProgressBar progress;
        private final Label state;
        private final Label percent;
        public LoadMenu(Identifier id) {
            super(id);
            Stop[] stops = new Stop[] {
                    new Stop(0, Color.LIGHTBLUE),
                    new Stop(1, Color.DARKBLUE)
            };
            LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
            super.getAnchorPane().setBackground(new Background(new BackgroundFill(gradient, null, null)));
            progress = new ProgressBar(Loader.percentOfLoad / 100.0);
            //progress.setStyle("-fx-accent: #4CAF50;-fx-background-color: #e0e0e0; -fx-border-radius: 10px; -fx-background-radius: 10px;");
            state = new Label(Loader.state);
            //state.setStyle("-fx-text-fill: #333; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 5px; -fx-alignment: center;");
            percent = new Label(Loader.percentOfLoad + " %");
            addNode(progress, NodeParameter.createRelativeSizeAndCoordinate(10, 85, 80, 10));
            addNode(state, NodeParameter.createRelativeSizeAndCoordinate(33, 85, 50, 10));
            addNode(percent, NodeParameter.createRelativeSizeAndCoordinate(12, 85, 10, 10));
        }

        @Override
        public void CustomUpdate() {
            progress.setProgress(Loader.percentOfLoad / 100.0);
            state.setText(Loader.state);
            percent.setText(Loader.percentOfLoad + " %");
            if (Loader.isRun) {
                System.out.println("end the loading all mods, run the game");
                GraphicProcessor.Controller.delete(getId());
                GraphicManager.addOnScreen(START_MENU_IDENTIFIER);
            }
            super.standardUpdate();
        }
    }
}
