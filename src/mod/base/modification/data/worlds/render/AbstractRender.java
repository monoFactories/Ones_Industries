package mod.base.modification.data.worlds.render;

import javafx.scene.canvas.Canvas;

public interface AbstractRender {
    double STANDARD_ZOOM_VALUE = 20.0;

    Canvas render(RenderParameter parameter, BlockSource blockSource);
}
