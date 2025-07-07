package mod.base.components.worlds.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import mod.base.modification.Base;
import mod.base.modification.data.blocks.Block;
import mod.base.modification.data.blocks.BlockInWorld;
import mod.base.modification.data.blocks.BlockTextureParameter;
import mod.base.modification.data.textures.Texture;
import mod.base.modification.data.worlds.render.AbstractRender;
import mod.base.modification.data.worlds.render.BlockSource;
import mod.base.modification.data.worlds.render.RenderParameter;

public class SimpleRender implements AbstractRender {
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
    @Override
    public Canvas render(RenderParameter parameter, BlockSource blockSource) {
        //System.out.println("new render, parameters: [screen width: " + parameter.screenWidth() + ",  screen height: " + parameter.screenHeight() + ", x: " + parameter.x() + " ,y: " + parameter.y() + " ,zoom: " + parameter.zoom());
        double widthPx = parameter.screenWidth();
        double heightPx = parameter.screenHeight();
        double playerX = parameter.x();
        double playerY = parameter.y();
        double zoom = parameter.zoom();
        double blockSize = Math.max(widthPx, heightPx) / (STANDARD_ZOOM_VALUE * zoom);
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
                BlockInWorld wBlock = blockSource.getBlock (x, y, 0);
                if (wBlock != null) {
                    Block block = wBlock.getBlock();
                    //System.out.println("block in x = " + x + ", y = " + y + " is: {" + block.getID() + "}");
                    BlockTextureParameter textureParameter = block.getTexture (wBlock);
                    double screenX = ((x + textureParameter.offsetX()) - startScreenX) * blockSize;
                    double screenY = (heightPx - blockSize * ((y + textureParameter.offsetY()) - startScreenY)) - blockSize * textureParameter.sizeY();
                    frameContext.drawImage (textureParameter.image(), screenX, screenY, textureParameter.sizeX() * blockSize, textureParameter.sizeY() * blockSize);
                }
            }
        }
        //frameContext.fillRect (halfBlocksWidth - 0.25 );
        frameContext.setStroke(Color.RED);
        frameContext.strokeRect ((halfBlocksWidth - 0.5) * blockSize, (halfBlocksHeight - 0.5) * blockSize, blockSize, blockSize);
        return frame;
    }
}
