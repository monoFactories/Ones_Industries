package core.management;

import java.util.Arrays;
import java.util.Objects;

public class SimpleMatrix<T> implements Matrix<T> {
    private final T[] matrix;
    private final int width;
    private final int height;

    public SimpleMatrix(int height, int width) {
        this.matrix = (T[]) new Object[height * width];
        this.height = height;
        this.width = width;
    }
    public T get (int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return null;
        return matrix[y * width + x];
    }
    public void set (int x, int y, T t) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        } else {
            matrix[y * width + x] = t;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix<?> matrix1)) return false;
        return width == matrix1.getWidth() && height == matrix1.getHeight() && Arrays.deepEquals(getMatrix(), matrix1.getMatrix());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(matrix), width, height);
    }

    @Override
    public String toString () {
        return "Matrix{" +
                "height=" + height +
                ", width=" + width +
                ", matrix=" + Arrays.toString(matrix) +
                '}';
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public T[][] getMatrix() {
        T[][] matrix2D = (T[][]) new Object[height][width];
        for (int y = 0; y < height; y++) {
            System.arraycopy(matrix, y * width, matrix2D[y], 0, width);
        }
        return matrix2D;
    }
}
