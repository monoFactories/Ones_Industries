package core.management.functions;

import static java.lang.Math.*;
import java.util.Random;

public class Noise {
    private final double[] gradients;
    private final int[] p;
    public Noise (long seed) {
        gradients = new double[256];
        Random r = new Random(seed);
        for (int i = 0; i < gradients.length; i++) {
            gradients[i] = (r.nextDouble() * 720.0) % 360.0;
        }
        r.setSeed(seed);
        p = new int[512];
        for (int i = 0; i < 256; i++) {
            p[i+256] = p[i] = abs(r.nextInt()) % 256;
        }
    }
    public double get (double x, double y) {
        double[][] nodesValue = new double[2][2];
        int fx = (int) floor(x);
        int fy = (int) floor(y);
        for (int yv = 0; yv < 2; yv++) {
            for (int xv = 0; xv < 2; xv++) {
                int cx = fx + xv;
                int cy = fy + yv;
                NoiseVector vec = generateVector(cx, cy);
                double dx = cx - x;
                double dy = cy - y;
                nodesValue[yv][xv] = vec.scalarMultiply(new NoiseVector(dx, dy));
            }
        }
        return calculateInterpolatedValue(x, y, nodesValue);
    }
    private NoiseVector generateVector (int x, int y) {
        int X = abs (x % 256);
        int Y = abs (y  % 256);
        double angleVector = gradients[p[p[X] + Y]];
        return new NoiseVector(angleVector);
    }
    public double calculateInterpolatedValue(double x, double y, double[][] vectorValuesMap) {
        double smoothX = smoothStep(0.0, 1.0, x - floor(x));
        double smoothY = smoothStep(0.0, 1.0, y - floor(y));
        double top = interpolate(vectorValuesMap[1][0], vectorValuesMap[1][1], smoothX);
        double bottom = interpolate(vectorValuesMap[0][0], vectorValuesMap[0][1], smoothX);
        return interpolate(bottom, top, smoothY);
    }
    public double smoothStep (double edge0, double edge1, double value) {
        double t = min(1, max(0, (value - edge0) / (edge1 - edge0)));
        return t * t * t * (t * (6.0 * t - 15.0) + 10.0);
    }
    public double interpolate (double a0, double a1, double value) {
        return a0 + value * (a1 - a0);
    }

    private static class NoiseVector {
        private final double x;
        private final double y;
        public NoiseVector(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public NoiseVector(double angle) {
            angle = min(360.0, Math.max(0.0, angle));
            angle = toRadians(angle);
            x = cos(angle);
            y = sin(angle);
        }
        public double scalarMultiply (NoiseVector vec) {
            return x * vec.x + y * vec.y;
        }
    }
}