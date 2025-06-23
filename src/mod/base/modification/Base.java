package mod.base.modification;

import core.moding.data.AbstractModRegister;
import game_logic.repositories.Identifier;
import mod.base.components.constants.NameConstant;
import mod.base.components.filling.fillgraphics.GraphicRegister;
import mod.base.components.filling.fillsounds.SoundRegister;
import mod.base.components.logics.settings.BaseSettingsHandler;
import mod.base.components.storages.SoundTagStorage;
import mod.base.components.worlds.graphics.offline.files.OfflineFileChecker;
import core.gameActions.Debug;
import core.moding.ModParameter;
import mod.base.components.filling.fillcontrols.ControllerRegister;
import mod.base.components.filling.fillgraphics.MainMenu;
import mod.base.components.logics.Style;
import core.moding.mod.ModFileManager;
import game_logic.Game;
import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

public class Base extends SimpleMod {
    private static SimpleMod base;
    //public static final SimpleModRepository modRepository = new SimpleModRepository();
    public static ModParameter parameter;
    public static ModFileManager files;

    public Base(ModParameter parameter) {
        super(parameter);
    }

    @Override
    protected AbstractModRegister createModRepository() {
        base = this;
        AbstractModRegister baseRegister = super.createModRepository();
        files = super.getModFileManager();
        OfflineFileChecker.init();
        long start = System.nanoTime();
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            Style.exec();
            latch.countDown();
        });
        Debug.debug("end of fill repository in mod: core");

        Debug.debug("mod: [" + parameter.getName() + "] end load mod");
        try {
            latch.await();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        long end = System.nanoTime();
        Debug.debug("style initialization time: " + ((end - start) / 1000_000.0) + " ms");
        return baseRegister;
    }

    @Override
    protected void useModParameter(ModParameter modParameter) {
        parameter = modParameter;
    }
    public static String getGlobalId(String id) {
        return parameter.getName() + ":" + id;
    }

    @Override
    protected void actionsOnRun() {
        Game.setStartMenuId(GraphicRegister.MAIN_PANE.getId());
        SoundTagStorage.parseSounds();
        BaseSettingsHandler.load();
        BaseSettingsHandler.save();
    }
    public static SimpleMod getMod() {
        return base;
    }
    public static Identifier getID (String local) {
        return getMod().getIdentifier (local);
    }
}
