package mod.base.components.worlds.render;

import javafx.scene.canvas.Canvas;

public interface AbstractRender {
    double STANDARD_ZOOM_VALUE = 20.0;
    /*public static class Render {
        public static final double STANDARD_SIZE = 20.0;
        public Canvas render (RenderParameter param, WorldTextureManager worldTextureManager) {
            double widthPx = param.screenWidth();
            double heightPx = param.screenHeight();
            double playerX = param.playerX();
            double playerY = param.playerY();
            double zoom = param.zoom();
            double blockSize = Math.max(widthPx, heightPx) / (STANDARD_SIZE * zoom);
            double blocksInWidth = widthPx / blockSize;
            double blocksInHeight = heightPx / blockSize;
            double halfBlocksWidth = blocksInWidth / 2;
            double halfBlocksHeight = blocksInHeight / 2;
            double startScreenX = playerX - halfBlocksWidth;
            double startScreenY = playerY - halfBlocksHeight;
            int minX = (int) Math.floor (startScreenX);
            int maxX = (int) Math.floor (playerX + halfBlocksWidth);
            int minY = (int) Math.floor (startScreenY);
            int maxY = (int) Math.floor (playerY + halfBlocksHeight);
            Canvas frame = new Canvas(widthPx, heightPx);
            GraphicsContext frameContext = frame.getGraphicsContext2D();
            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    Image blockTexture = worldTextureManager.getTexture(x, y);
                    if (blockTexture != null) {
                        double screenX = (x - startScreenX) * blockSize;
                        double screenY = (heightPx - blockSize * (y - startScreenY)) - blockSize;
                        frameContext.drawImage(blockTexture, screenX, screenY, blockSize, blockSize);
                    }
                }
            }
            return frame;
        }
    }*/
    Canvas render(RenderParameter parameter, BlockSource blockSource);
}
