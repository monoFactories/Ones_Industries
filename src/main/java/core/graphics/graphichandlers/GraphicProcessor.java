package core.graphics.graphichandlers;

import core.configs.settings.GraphicSettings;
import core.gameActions.Debug;
import core.graphics.objects.GraphicComponent;

import game_logic.Game;
import game_logic.repositories.Identifier;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;

public class GraphicProcessor {
//    private static boolean isRun = false;
//    private static Stage gameStage;
//    private static Pane rootPane;
//    private static Scene gameScene;
//
//    private static double FPS = 0.0;
//    private static int frames = 0;
//    private static long nanoSeconds = 0;
//    private volatile static long timeOut = 2_000_000;
//
//    private static final ConcurrentHashMap<String, GraphicComponent> gameComponentAdd = new ConcurrentHashMap<>();
//    private static final List<GraphicAction> actions = new ArrayList<>();
//
//    private static final ConcurrentHashMap<String, GraphicComponent> tableGameComponents = new ConcurrentHashMap<>();
//
//    private static final Deque<GraphicComponent> stack = new ArrayDeque<>();
//    private static final Deque<GraphicComponent> stackAdd = new ConcurrentLinkedDeque<>();
//
//    public static void initialise (Stage stage) {
//        if (!isRun) {
//            Pane stackPane = new StackPane();
//            Rectangle2D r2d = Screen.getPrimary().getBounds();
//            Scene scene = new Scene(stackPane, r2d.getWidth(), r2d.getHeight());
//            stage.setResizable(true);
//            stage.setScene(scene);
//            stage.setFullScreenExitHint("");
//            stage.setMinWidth(600);
//            stage.setMinHeight(400);
//            gameStage = stage;
//            rootPane = stackPane;
//            gameScene = scene;
//            gameStage.setOnCloseRequest(we -> {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, LanguageModManager.translate("basicMod:interface:exit_message"));
//                if (alert.showAndWait().get() != ButtonType.OK) {
//                    we.consume();
//                } else
//                    SettingsHandler.write();
//            });
//            scene.setOnKeyPressed(event -> {
//                if (event.getCode() == KeyCode.ESCAPE)
//                    event.consume();
//            });
//            gameStage.show();
//            Thread thread = new Thread(() -> {
//                while (true) {
//                    long start_ = System.nanoTime();
//                    try {
//                        long start = System.nanoTime();
//                        moveMain();
//                        execActions();
//                        updateTableGameComponent();
//                        long difference = System.nanoTime() - start;
//                        if (difference < timeOut)
//                            LockSupport.parkNanos(timeOut - difference);
//                    } catch (Exception e) {
//                        Game.log(Level.WARNING, "unknown exception", e);
//                    }
//                    long end_ = System.nanoTime();
//                    long difference = end_ - start_;
//                    calculateFPS(difference);
//                }
//            }, "graphic-Thread-1");
//            thread.setDaemon(true);
//            thread.start();
//            isRun = true;
//        }
//    }
//    private static void calculateFPS (long difference) {
//        nanoSeconds += difference;
//        frames ++;
//        if (nanoSeconds >= 1_000_000_000) {
//            double seconds = nanoSeconds / 1_000_000_000.0;
//            FPS = (double) frames / seconds;
//            nanoSeconds = 0;
//            frames = 0;
//            //System.out.println(FPS);
//        }
//    }
//    private final static Runnable lambdaMove = () -> {
//        Iterator<Map.Entry<String, GraphicComponent>> addIterator = gameComponentAdd.entrySet().iterator();
//
//        while (addIterator.hasNext()) {
//            Map.Entry<String, GraphicComponent> element = addIterator.next();
//            GraphicComponent gc = element.getValue();
//            String key = element.getKey();
//            try {
//                gc.onAddFromScreen();
//            } catch (Throwable th) {
//                Game.log(Level.INFO, "Exception when add new graphic component", th);
//            }
//            tableGameComponents.put(key, gc);
//            rootPane.getChildren().add(gc.getAnchorPane());
//            stackAdd.push(gc);
//            addIterator.remove();
//        }
//    };
//    private static final Runnable execActionRunnable = () -> {
//        Iterator<GraphicAction> iteratorAction = actions.iterator();
//        while (iteratorAction.hasNext()) {
//            GraphicAction a = iteratorAction.next();
//            if (a == null)
//                continue;
//            switch (a.getType()) {
//                case DELETE_COMPONENT -> {
//                    String[] deletingComponents = a.getTargets();
//                    if (deletingComponents != null) {
//                        for (String s : deletingComponents) {
//                            //GraphicComponent gc = tableGameComponents.get(s);
//                            //if (gc != null) {
//                            //    gc.onRemoveFromScreen();
//                            //    tableGameComponents.remove(s);
//                            //    rootPane.getChildren().remove(gc.getAnchorPane());
//                            //}
//                            deleteComponent(s, true, false);
//                        }
//                    }
//                }
//                case REPLACE_COMPONENT -> {
//                    String[] targets = a.getTargets();
//                    if (a instanceof RequiredGraphicComponent rgc) {
//                        if (targets != null && targets.length > 0) {
//                            String target = targets[0];
//                            //GraphicComponent Dgc = tableGameComponents.get(target);
//                            //if (Dgc != null) {
//                            //    Dgc.onRemoveFromScreen();
//                            //    tableGameComponents.remove(target);
//                            //    rootPane.getChildren().remove(Dgc.getAnchorPane());
//                            //}
//                            deleteComponent(target, true, false);
//                            Controller.addGraphicComponent(rgc.getComponent());
//                        }
//                    }
//                }
//                case CLEAR -> {
//                    tableGameComponents.forEach((s, gc) -> gc.onRemoveFromScreen());
//                    tableGameComponents.clear();
//                    stack.clear();
//                    rootPane.getChildren().clear();
//                }
//            }
//            iteratorAction.remove();
//        }
//    };
//    private static final Runnable updateRunnable = () -> tableGameComponents.forEach((s, g) -> g.CustomUpdate());
//    private static void moveMain() {
//        if (!gameComponentAdd.isEmpty()) {
//            execRunLater(lambdaMove);
//        }
//    }
//
//    private static void execActions () {
//        if (!actions.isEmpty()) {
//            execRunLater(execActionRunnable);
//        }
//    }
//    public static void updateTableGameComponent () {
//        execRunLater(updateRunnable);
//    }
//
//    private static void execRunLater (Runnable run) {
//        CountDownLatch latch1 = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            try {
//                run.run();
//            } finally {
//                latch1.countDown();
//            }
//        });
//        try {
//            latch1.await();
//        } catch (InterruptedException in) {
//            Thread.currentThread().interrupt();
//        }
//    }
//    private static void deleteComponent (String s, boolean addInStack, boolean pseudoDelete) {
//        String[] split = s.split(":", 2);
//        if (split.length >= 2) {
//            s = split[1];
//        }
//        GraphicComponent deletingElement = tableGameComponents.get(s);
//        if (deletingElement != null) {
//            if (!pseudoDelete) {
//                try {
//                    deletingElement.onRemoveFromScreen();
//                } catch (Throwable th) {
//                    Game.log(Level.INFO, "Error when deleting graphic component: " + s, th);
//                }
//                tableGameComponents.remove(s);
//                rootPane.getChildren().remove(deletingElement.getAnchorPane());
//            }
//            if (addInStack) {
//                Debug.debug("\"" + deletingElement.getId() + "\" was add in stack");
//                stack.push(deletingElement);
//            }
//        }
//    }
//    public final static class Controller {
//        public static void close () {
//            if (gameStage != null)
//                Platform.runLater(() -> {
//                    SettingsHandler.write();
//                    gameStage.close();
//                });
//            else
//                throw new RuntimeException("gameStage is null");
//        }
//        public static void addGraphicComponent(GraphicComponent component) {
//            if (component != null) {
//                System.out.println("component with name: " + component.getId() + " was add");
//                gameComponentAdd.put(component.getId(), component);
//            }
//        }
//
//        public static void delete (String s) {
//            if (s != null) {
//                addAction(new GraphicAction(s, ActionType.DELETE_COMPONENT));
//            }
//        }
//        public static void clear () {
//            addAction(new GraphicAction("", ActionType.CLEAR));
//        }
//
//        private static void setFullScreen(boolean b) {
//            gameStage.setFullScreen (b);
//        }
//
//        public static void addAction(GraphicAction ga) {
//            actions.add(ga);
//        }
//        private static void setTimeOut (long newTime) {
//            timeOut = newTime;
//        }
//        public static void useSettings (GraphicSettings gs) {
//            if (gs != null) {
//               Platform.runLater(() -> {
//                   setFullScreen(gs.isFullScreen());
//                    setWidth(gs.getWidth());
//                    setHeight(gs.getHeight());
//                    setParameter(Math.clamp(gs.getBrightness(), -1, 1), Math.clamp(gs.getSaturation(), -1, 1), Math.clamp(gs.getContrast(), -1, 1));
//                    setTimeOut(Math.round(1_000_000_000.0 / Math.clamp(gs.getFPS(), 1.0, 99999)));
//               });
//            }
//        }
//        private static void setHeight (double height) {
//            height = Math.clamp(height, 1.0, 9999.9);
//            gameStage.setHeight(height);
//        }
//        private static void setWidth (double width) {
//            width = Math.clamp(width, 1.0, 9999.9);
//            gameStage.setWidth(width);
//        }
//        private static void setParameter (double brightness, double saturation, double contrast) {
//            ColorAdjust adjust = new ColorAdjust();
//            adjust.setBrightness(brightness);
//            adjust.setSaturation(saturation);
//            adjust.setContrast(contrast);
//            rootPane.setEffect(adjust);
//        }
//        public static void addCSS (String is) {
//            try {
//                gameScene.getStylesheets().add(is);
//            } catch (Exception e) {
//                Game.log(Level.INFO, "Error when load style:" + is, e);
//            }
//        }
//        public static void loadLastFromStack () {
//            GraphicComponent lastComponent = null;
//            try {
//                lastComponent = stack.pop();
//            } catch (NoSuchElementException nse) {
//                System.out.println("attempt to load an item from an empty stack");
//            }
//            if (lastComponent != null && !rootPane.getChildren().contains(lastComponent.getAnchorPane())) {
//                addGraphicComponent(lastComponent);
//            }
//        }
//        public static void deleteWithoutStack (String id) {
//            //System.out.println("deleting start: " + id);
//            deleteComponent(id, false, false);
//        }
//        public static void pseudoDelete(String id) {
//            deleteComponent(id, true, true);
//        }
//        public static void clearStack () {
//            stack.clear();
//        }
//        public static void setMouseClick (EventHandler<? super MouseEvent> handler) {
//            gameScene.setOnMouseClicked(handler);
//        }
//        public static void setKeyPress (EventHandler<? super KeyEvent> handler) {
//            gameScene.setOnKeyPressed(handler);
//        }
//        public static void back () {
//            GraphicComponent gc = null;
//            try {
//                gc = stackAdd.poll();
//            } catch (NoSuchElementException n) {
//                //
//            }
//            if (gc != null) {
//                try {
//                    gc.back();
//                } catch (Throwable th) {
//                    //
//                }
//                if (!tableGameComponents.containsKey(gc.getId())) {
//                    try {
//                        stackAdd.remove(gc);
//                    } catch (NoSuchElementException n) {
//                        //
//                    }
//                }
//            }
//        }
//    }
//    public static class getter {
//        public static double getWidth () {
//            return gameStage.getWidth();
//        }
//        public static double getHeight () {
//            return gameStage.getHeight();
//        }
//        public static ReadOnlyDoubleProperty getWidthProperty () {
//            return gameStage.widthProperty();
//        }
//        public static ReadOnlyDoubleProperty getHeightProperty () {
//            return gameStage.heightProperty();
//        }
//        public static double getFPS () {
//            return FPS;
//        }
//    }
    public static final double MIN_HEIGHT = 400, MIN_WIDTH = 600;
    public static final Object NULL_OBJECT = new Object();
    /// ///javafx
    private static Stage stage;
    private static Scene scene;
    private static StackPane pane;

    /// storages, and pre-storage
    private static final ConcurrentHashMap <Identifier, GraphicComponent> componentsStorage = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap <Identifier, GraphicComponent> interMediateStorage = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap <Long, Runnable> intermediateActions = new ConcurrentHashMap<>();
    /// stacks
    //private static final Deque<GraphicComponent> pathStack = new ArrayDeque<>();
    //private static final Deque<GraphicComponent> removeStack = new ArrayDeque<>();
    private static final Deque<ComponentEntry> unitedStack = new ArrayDeque<>();
    /// fps
    private static int frames = 0;
    private static long nanoSeconds = 0;
    private static double FPS = 0.0;
    ///
    private static long timeOut = (long) (1e9 / 60);
    /// thread
    private static final Thread renderCycleThread = new Thread(() -> {
        while (true) {
            //System.out.println("cycle");
            long start = System.nanoTime();
            try {
                long start_ = System.nanoTime();
                CountDownLatch oneLatch = new CountDownLatch(1);
                Platform.runLater(() -> {
                    //System.out.println("united stack :" + unitedStack + "\npane: " + pane );
                    if (!interMediateStorage.isEmpty()) {
                        Iterator<Map.Entry<Identifier, GraphicComponent>> interComponentIterator = interMediateStorage.entrySet().iterator();
                        while ((interComponentIterator.hasNext())) {
                            Map.Entry<Identifier, GraphicComponent> curr = interComponentIterator.next();
                            GraphicComponent graph = curr.getValue();
                            if (graph != null) {
                                GraphicActivities.addComponent(curr.getKey(), graph);
                            }
                            interComponentIterator.remove();
                        }
                    }
                    componentsStorage.forEach((s, g) -> g.CustomUpdate());
                    if (!intermediateActions.isEmpty()) {
                        //Iterator<Map.Entry<Long, Runnable>> actionsIterator = intermediateActions.entrySet().iterator();
                        //while (actionsIterator.hasNext()) {
                        //    Map.Entry<Long, Runnable> curr = actionsIterator.next();
                        //    curr.getValue().run();
                        //    actionsIterator.remove();
                        //}
                        List<Long> sortedTime = intermediateActions.keySet().stream().sorted().toList();
                        for (long c : sortedTime) {
                            Runnable action = intermediateActions.get(c);
                            if (action != null) {
                                action.run();
                                intermediateActions.remove(c);
                            }
                            //timeIterator.remove();
                        }
                    }
                    oneLatch.countDown();
                });
                try {
                    oneLatch.await();
                } catch (InterruptedException interrupt) {
                    Thread.currentThread().interrupt();
                }
                long end_ = System.nanoTime();
                long difference = end_ - start_;
                if (difference < timeOut)
                    LockSupport.parkNanos(timeOut - difference);
            } catch (Throwable C14) {
                Debug.debug (Level.INFO, "error in cycle support render thread", C14);
            }
            long end = System.nanoTime();
            GraphicActivities.calculateFPS(end - start);
        }
    });
    public static void initialise (Stage stage1) {
        if (stage == null && stage1 != null) {
            pane = new StackPane();
            Rectangle2D r2d = Screen.getPrimary().getBounds();
            scene = new Scene(pane, r2d.getWidth(), r2d.getHeight());
            stage = stage1;
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMinHeight(MIN_HEIGHT);
            stage.setMinWidth(MIN_WIDTH);
            stage.setFullScreenExitHint("");
            stage.show();
            renderCycleThread.setDaemon(true);
            renderCycleThread.setName("support-render-thread");
            renderCycleThread.start();
        }
    }
    private static final class GraphicActivities {
        static void calculateFPS (long difference) {
            frames++;
            nanoSeconds+=difference;
            if (nanoSeconds >= 100_000_000) {
                FPS = frames * 1.0 / nanoSeconds;
                frames = 0;
                nanoSeconds = 1;
            }
        }
        static void addComponent (Identifier regID, GraphicComponent graphic) {
            Debug.debug("trying add component \"" + regID + "\"");
            try {
                if (!componentsStorage.containsKey(regID)) {
                    pane.getChildren().add(graphic.getAnchorPane());
                    componentsStorage.put(regID, graphic);
                    unitedStack.push(new ComponentEntry(regID, graphic));
                }
                graphic.onAddFromScreen();
            } catch (Throwable th232) {
                Game.log(Level.INFO, "An exception was received when calling the \"onAddFromScreen\" function or when adding it to the screen.", th232);
            }
        }
        static void deleteComponent (Identifier regID, boolean without) {
            Debug.debug("start deleting: " + regID);
            GraphicComponent graph = componentsStorage.get(regID);
            if (graph != null) {
                Debug.debug("delete: \"" + regID + "\"");
                componentsStorage.remove(regID);
                pane.getChildren().remove(graph.getAnchorPane());
                if (without) {
                    unitedStack.remove(new ComponentEntry(regID, graph));
                }
            }
        }
        static void goBack () {
            ComponentEntry ce = unitedStack.peek();
            if (ce != null) {
                ce.gr.back();
            }
        }
        static void loadLastFromStack () {
            ComponentEntry ce = unitedStack.peek();
            //System.out.println("last from stack : " + ce);
            if (ce != null) {
                if (!pane.getChildren().contains(ce.gr.getAnchorPane())) {
                    try {
                        pane.getChildren().add(ce.gr.getAnchorPane());
                        componentsStorage.put(ce.regID, ce.gr);
                        ce.gr.onAddFromScreen();
                    } catch (Throwable th232) {
                        Game.log(Level.INFO, "[loadLastFromScreen] An exception was received when calling the \"onAddFromScreen\" function or when adding it to the screen.", th232);
                    }
                }
            }
        }
        static void setTimeOut (long timeOut1) {
            timeOut = timeOut1;
        }
    }
    private record ComponentEntry(Identifier regID, GraphicComponent gr) { }
    public static final class Controller {
        private static void add (Runnable fun) {
            intermediateActions.put(System.nanoTime(), fun);
        }
        public static void close () {
            if (stage != null) {
                Platform.runLater(() -> stage.close());
            } else {
                System.exit(2);
            }
        }
        public static void back () {
            add(GraphicActivities::goBack);
        }
        public static void add (GraphicComponent graph) {
            if (graph != null)
                interMediateStorage.put(graph.getId(), graph);
        }
        //public static void add (String mod, GraphicComponent graph) {
        //    if (graph != null)
        //        interMediateStorage.put(mod == null ? graph.getId() : mod.isEmpty() ? graph.getId() : mod + ":" + graph.getId(), graph);
        //}
        public static void delete (Identifier regID) {
            add(() -> GraphicActivities.deleteComponent(regID, true));
        }
        public static void hide (Identifier regID) {
            add(() -> GraphicActivities.deleteComponent(regID, false));
        }
        public static void loadLastFromStack() {
            add(GraphicActivities::loadLastFromStack);
        }
        public static void useSettings (GraphicSettings gs) {
            if (gs != null) {
                Platform.runLater(() -> {
                    setFullScreen(gs.isFullScreen());
                    setWidth(gs.getWidth());
                    setHeight(gs.getHeight());
                    setParameter(Math.clamp(gs.getBrightness(), -1, 1), Math.clamp(gs.getSaturation(), -1, 1), Math.clamp(gs.getContrast(), -1, 1));
                    add(() -> GraphicActivities.setTimeOut(Math.round(1_000_000_000.0 / Math.clamp(gs.getFPS(), 1.0, 99999))));
               });
            }
        }
        public static void setWidth(double width) {width = Math.clamp(width, 10, 9999);stage.setWidth(width);}
        public static void setHeight(double width) {width = Math.clamp(width, 10, 9999);stage.setHeight(width);}
        public static void setFullScreen (boolean b) {stage.setFullScreen(b);}
        private static void setParameter(double brightness, double saturation, double contrast) {
            ColorAdjust ca = new ColorAdjust();
            ca.setSaturation(saturation);
            ca.setContrast(contrast);
            ca.setBrightness(brightness);
            pane.setEffect(ca);
        }

        public static void setMouseClick(EventHandler<MouseEvent> handler) {
            scene.setOnMouseClicked(handler);
        }
        public static void setKeyPress(EventHandler<KeyEvent> handler) {
            scene.setOnKeyPressed(handler);
        }
        public static void addCSS (String is) {
            try {
                scene.getStylesheets().add(is);
            } catch (Exception e) {
                Game.log(Level.INFO, "Error when load style:" + is, e);
            }
        }
    }
    public static class getter {
        public static double getWidth () {
            return stage.getWidth();
        }
        public static double getHeight () {
            return stage.getHeight();
        }
//        public static ReadOnlyDoubleProperty getWidthProperty () {
//            return gameStage.widthProperty();
//        }
//        public static ReadOnlyDoubleProperty getHeightProperty () {
//            return gameStage.heightProperty();
//        }
        public static double getFPS () {
            return FPS;
        }
    }
}
