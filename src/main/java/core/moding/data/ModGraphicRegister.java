package core.moding.data;

import core.management.DualRepository;
import core.graphics.objects.GraphicComponent;
import game_logic.repositories.GraphicComponentsRepository;
import game_logic.repositories.LanguageRepository;

public class ModGraphicRegister {
    private final DualRepository.SingleRepository<GraphicComponent> tableGraphics;

    public ModGraphicRegister (String modName) {
        tableGraphics = new DualRepository.SingleRepository<>();
        GraphicComponentsRepository.graphics.addEntry(modName, tableGraphics);
    }

    public void add(GraphicComponent gc) {
        if (gc != null) {
            String gcId = gc.getId();
            tableGraphics.add(gcId, gc);
        }
    }
    public void remove (String id) {
        tableGraphics.remove(id);
    }
    public void clear () {
        tableGraphics.clear();
    }
    public GraphicComponent get(String id) {
        return tableGraphics.get(id);
    }
    public void translate (LanguageRepository language) {
        tableGraphics.forEach((s, gc) -> gc.translating(language));
    }
}