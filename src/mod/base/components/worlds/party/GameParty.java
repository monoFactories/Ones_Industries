package mod.base.components.worlds.party;

import core.configs.settings.SettingsHandler;
import core.graphics.graphichandlers.GraphicProcessor;
import game_logic.repositories.Identifier;
import javafx.scene.canvas.Canvas;
import mod.base.components.worlds.level.planets.PlanetUtil;
import mod.base.components.worlds.party.level.CreatorLevel;
import mod.base.components.worlds.party.level.PartyLevelSettings;
import mod.base.components.worlds.party.level.PlayerChunkManager;
import mod.base.components.worlds.party.level.input.BlockInput;
import mod.base.components.worlds.party.level.input.OfflineChunkInput;
import mod.base.components.worlds.render.SimpleRender;
import mod.base.modification.data.PlayerCamera;
import mod.base.modification.data.worlds.level.AbstractLevel;
import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.chunks.ChunkConstant;
import mod.base.modification.data.worlds.level.generation.AbstractChunkGenerator;
import mod.base.modification.data.worlds.level.planets.AbstractPlanet;
import mod.base.modification.data.worlds.level.planets.PlanetDescription;
import mod.base.modification.data.worlds.render.AbstractRender;
import mod.base.modification.data.worlds.render.RenderParameter;
import mod.base.modification.registration.PlanetRegister;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class GameParty implements Runnable {
    private GameParty () {}
    private AbstractLevel level;
    private BlockInput input;
    private AbstractRender render = new SimpleRender();
    private AtomicBoolean running;

    private PlayerCamera playerCamera = new PlayerCamera();
    private PartyLevelSettings settings = new PartyLevelSettings (3);

    private PlayerChunkManager manager = () -> {
        Identifier currentPlanetId = playerCamera.getPlanetId();
        //System.out.println ("player camera planet id: " + currentPlanetId);
        AbstractPlanet [] first = new AbstractPlanet[1];
        int chunkX = ChunkConstant.getChunkCoordinate(playerCamera.getX());
        int chunkY = ChunkConstant.getChunkCoordinate(playerCamera.getY());
        boolean[] hasPlanet = new boolean[] {false};
        level.getPlanets ().forEach (planet -> {
            //System.out.println("manage planet: " + planet.getID());
            if (first[0] == null) first[0] = planet;
            if (!currentPlanetId.equals(planet.getID())) {
                planet.getPlanetEditor().forEach ((xy, chunk) -> planet.getPlanetEditor().removeChunk(chunk.getX(), chunk.getY()));
            } else {
                hasPlanet[0] = true;
                int horizon = settings.drawingHorizon();
                int minX = chunkX - horizon;
                int minY = chunkY - horizon;
                int maxX = chunkX + horizon;
                int maxY = chunkY + horizon;
                planet.getPlanetEditor().forEach((xy, chunk) -> {
                    int x = chunk.getX();
                    int y = chunk.getY();
                    if (!(x >= minX && x <= maxX && y >= minY && y <= maxY)) {
                        planet.getPlanetEditor().removeChunk (x, y);
                    }
                });
                for (int y = -horizon; y <= horizon; y++) {
                    for (int x = -horizon; x <= horizon; x++) {
                        int rx = x + chunkX;
                        int ry = y + chunkY;
                        AbstractChunk planetChunk = planet.getPlanetEditor().getChunk(rx, ry);
                        if (planetChunk == null) {
                            //System.out.println ("loading chunk: {" + rx + ", " + ry + "}");
                            planet.getPlanetEditor().addChunk(input.get(this, planet, rx, ry));
                        }
                    }
                }
            }
        });
        if (!hasPlanet[0]) {
            PlanetDescription description = PlanetRegister.getPlanet(playerCamera.getPlanetId());
            if (description != null)
                level.addPlanet (description.getPlanetFunction().apply(level));
            else if (first[0] != null) {
                playerCamera.setPlanetId(first[0].getID());
            }
        }
    };
    private Canvas frame;


    public void loadLevel (AbstractLevel level) {
        if (level != null)
            this.level = level;
    }
    public void setCustomRender (AbstractRender customRender) {
        if (customRender != null)
            this.render = customRender;
    }
    public void end () {
        running.set (false);
    }
    public Canvas getFrame() {
        return frame;
    }
    @Override
    public void run() {
        running = new AtomicBoolean(true);
        double[] speed = new double[] {0.05};
        GraphicProcessor.Controller.setKeyPress(keyEvent -> {
            switch (keyEvent.getCode()) {
                case W -> playerCamera.setY (playerCamera.getY() + speed[0]);
                case A -> playerCamera.setX(playerCamera.getX() - speed[0]);
                case S -> playerCamera.setY(playerCamera.getY() - speed[0]);
                case D -> playerCamera.setX(playerCamera.getX() + speed[0]);
                case Q -> speed[0] += 0.01;
                case E -> speed[0] -= 0.01;
                case UP -> playerCamera.setZoom(playerCamera.getZoom() - 0.075);
                case DOWN -> playerCamera.setZoom(playerCamera.getZoom() + 0.075);
                case DELETE -> GraphicProcessor.Controller.back();
            }
        });
        while (running.get()) {
            long start = System.nanoTime();
            manager.manage();
            AbstractPlanet planet = level.getPlanet(playerCamera.getPlanetId());
            if (planet != null)
                frame = render.render(new RenderParameter (playerCamera.getX(), playerCamera.getY(), playerCamera.getZoom(), GraphicProcessor.getter.getWidth(), GraphicProcessor.getter.getHeight()), planet.toBlockSource());
            long end = System.nanoTime();
            long time = end - start;
            long targetTime = (long) (1_000_000_000.0 / SettingsHandler.settings.getGraphicSettings().getFPS());
            if (time < targetTime)
                LockSupport.parkNanos(targetTime - time);
        }
    }
    public static GameParty getInstance (long seed) {
        GameParty p = new GameParty();
        p.level = CreatorLevel.create(seed);
        p.playerCamera.setPlanetId (PlanetUtil.getStartPlanetIdentifier());
        p.input = new OfflineChunkInput();
        return p;
    }
}
