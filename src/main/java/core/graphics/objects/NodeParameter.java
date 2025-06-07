package core.graphics.objects;

import java.util.Objects;

public class NodeParameter {
    public final boolean isRelativeCoordinate;
    public final double x;
    public final double y;
    public final boolean isRelativeSize;
    public final double width;
    public final double height;

    public NodeParameter(boolean isRelativeCoordinate, double x, double y, boolean isRelativeSize, double width, double height) {
        this.isRelativeCoordinate = isRelativeCoordinate;
        this.x = x;
        this.y = y;
        this.isRelativeSize = isRelativeSize;
        this.width = width;
        this.height = height;
    }
    @Override
    public int hashCode() {
        return Objects.hash(isRelativeCoordinate, x, y, isRelativeSize, width, height);
    }
    //create a object "NodeParameter" whose coordinate and size is relative
    public static NodeParameter createRelativeSizeAndCoordinate (double x, double y, double width, double height) {
        return new NodeParameter(true,x,y,true,width,height);
    }
    //create a object "NodeParameter" whose size is relative and coordinate is absolute
    public static NodeParameter createRelativeSize(double x, double y, double width, double height) {
        return new NodeParameter(false,x,y,true,width,height);
    }
    //create a object "NodeParameter" whose coordinate is relative and absolute size
    public static NodeParameter createRelativeCoordinate (double x, double y, double width, double height) {
        return new NodeParameter(true,x,y,false,width,height);
    }
    public static NodeParameter createAbsolute (double x, double y, double width, double height) {
        return new NodeParameter(false, x, y, false, width, height);
    }
}
