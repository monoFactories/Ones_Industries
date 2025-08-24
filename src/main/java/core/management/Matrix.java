package core.management;

import java.util.Arrays;
import java.util.Objects;

public interface Matrix <T> {
    //private final T[] matrix;
    //private final int width;
    //private final int height;

    //public Matrix(int height, int width) {
    //    this.matrix = (T[]) new Object[height * width];
    //    this.height = height;
    //    this.width = width;
    //}
    public T get (int x, int y);
    public void set (int x, int y, T t);

    //@Override
    //public boolean equals(Object o) {
    //    if (this == o) return true;
    //    if (!(o instanceof Matrix<?> matrix1)) return false;
    //    return width == matrix1.width && height == matrix1.height && Arrays.equals(matrix, matrix1.matrix);
    //}

    //@Override
    //default String toString () {
    //    return "Matrix{" +
    //            "height=" + height +
    //            ", width=" + width +
    //            ", matrix=" + Arrays.toString(matrix) +
    //            '}';
    //}

    public int getWidth();
    public int getHeight();
    public T[][] getMatrix();
}