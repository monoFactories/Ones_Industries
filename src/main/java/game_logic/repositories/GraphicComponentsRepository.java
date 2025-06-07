package game_logic.repositories;

import core.management.DualRepository;
import game_logic.Game;
import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.GraphicComponent;
import java.util.logging.Level;

public class GraphicComponentsRepository extends DualRepository<GraphicComponent> {
    public static final GraphicComponentsRepository graphics = new GraphicComponentsRepository();
    public GraphicComponentsRepository () {
        super ();
    }
    ////private final ConcurrentHashMap<String, ModGraphicRepository> tableModGraphics;
    ////public GraphicComponentsRepository() {
    ////    tableModGraphics = new ConcurrentHashMap<>();
    ////}
    //public void addGraphicComponent (String mod, GraphicComponent graphicComponent) {
    //    Mod mod_ = ModsRepository.mods.get(mod);
    //    if (mod_ != null) {
    //        mod_.modRepository.graphics.add(graphicComponent);
    //    }
    //}
    ////public void addModEntry (String str) {
    ////    if (str != null)
    ////        tableModGraphics.put(str, new ModGraphicRepository());
    ////}
    ////public void addModEntry (String str, ModGraphicRepository modGraph) {
    ////    if (str != null)
    ////        tableModGraphics.put(str, (modGraph != null) ? modGraph : new ModGraphicRepository());
    ////}
    //public static void removeGraphicComponent (String mod, String graphicID) {
    //    Mod mod_ = ModsRepository.mods.get(mod);
    //    if (mod_ != null) {
    //        mod_.modRepository.graphics.remove(graphicID);
    //    }
    //}
    //public static GraphicComponent get (String id) {
    //    return checkAndGet (id);
    //}
    //public static void applyGraphicAction(GraphicAction g) {
    //    String[] targets = g.getTargets();
    //    if (targets.length == 1) {
    //        String[] split = translateTargets(targets[0]);
    //        if (split.length == 2)
    //            applyToTarget(split[0], split[1], g);
    //    } else if (targets.length > 1) {
    //        for (String s : targets) {
    //            String[] split = translateTargets(s);
    //            if (split.length == 2)
    //                applyToTarget(split[0], split[1], g);
    //        }
    //    }
    //}
    //private static void applyToTarget (String modT, String t, GraphicAction a) {
    //    //if (tableModGraphics.containsKey(modT)) {
    //    //    tableModGraphics.get(modT).apply(t, a);
    //    //}
    //    Mod mod = ModsRepository.mods.get (modT);
    //    if (mod != null) {
    //        mod.modRepository.graphics.apply(t, a);
    //    }
    //}
    //private static String[] translateTargets (String s) {
    //    return s.split(":", 2);
    //}
    //private static GraphicComponent checkAndGet (String str) {
    //    String[] strArr = translateTargets(str);
    //    if (strArr.length >= 2) {
    //        try {
    //            Mod mod = ModsRepository.mods.get(strArr[0]);
    //            if (mod != null) {
    //                return mod.modRepository.graphics.get(strArr[1]);
    //            }
    //        } catch (Exception e) {
    //            Game.log(Level.INFO, "Exception when get graphic component", e);
    //        }
    //    }
    //    return null;
    //}
    public static void addInGraphicProcessor (Identifier id) {
        GraphicComponent gc = graphics.get(id);
        if (gc != null) {
            GraphicProcessor.Controller.add (gc);
        } else {
            Game.log(Level.WARNING, "couldn't find graphic component with id:" + id, null);
        }
    }
}
