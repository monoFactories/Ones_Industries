package mod.base.modification.utils.MathUtils;

public class MemoryUtils {
    public static final long KILOBYTE = 1024,
    MEGABYTE = 1048576,
    GIGABYTE = 1073741824,
    TERABYTE = 1099511627776L;
    public static String convertByteCount (long byteCount) {
        long abs = Math.abs(byteCount);
        double byteCountF = (double) byteCount;
        if (abs < KILOBYTE)
            return byteCount + " B";
        if (abs < MEGABYTE)
            return String.format("%.2f KB", (byteCountF / KILOBYTE));
        if (abs < GIGABYTE)
            return String.format("%.2f MB", (byteCountF / MEGABYTE));
        if (abs < TERABYTE)
            return String.format("%.2f GB", (byteCountF / GIGABYTE));
        return String.format("%.2f TB", (byteCountF / TERABYTE));
    }
    public static void init() {}
}