package core.configs.settings;

public class SoundSettings {
    private double musicVolume;
    private double interfaceVolume;
    private double overallVolume;
    private double factoryVolume;
    private double carVolume;
    private double otherVolume;

    public SoundSettings() {
        TypeVolume[] types = TypeVolume.values();
        for (TypeVolume tp : types) {
            setTypedVolume(tp, tp.getStandard());
        }
    }
    public double getTypedVolume (TypeVolume type) {
        if (type == null)
            return otherVolume;
        return switch (type) {
            case OVERALL -> overallVolume;
            case INTERFACE -> interfaceVolume;
            case MUSIC -> musicVolume;
            case FACTORY -> factoryVolume;
            case CAR -> carVolume;
            case OTHER -> otherVolume;
        };
    }
    public void setTypedVolume (TypeVolume type, double value) {
        value = Math.clamp(value, 0.0, 100.0);
        switch (type) {
            case OVERALL -> overallVolume = value;
            case INTERFACE -> interfaceVolume = value;
            case MUSIC -> musicVolume = value;
            case FACTORY -> factoryVolume = value;
            case CAR -> carVolume = value;
            case OTHER -> otherVolume = value;
        }
    }
    public static SoundSettings getStandard () {
        return new SoundSettings();
    }
    public static SoundSettings check (SoundSettings ss) {
        if (ss != null) {
            for (TypeVolume tp : TypeVolume.values()) {
                ss.setTypedVolume(tp, Math.clamp(ss.getTypedVolume(tp), 0.0, 100.0));
            }
            return ss;
        } else
            return getStandard();
    }
    public enum TypeVolume {
        OVERALL(100),
        INTERFACE(50),
        MUSIC(50),
        FACTORY(100),
        CAR(100),
        OTHER(100);

        final double standard;
        TypeVolume (double standard) {
            this.standard = standard;
        }
        public double getStandard() {
            return standard;
        }
    }
}
