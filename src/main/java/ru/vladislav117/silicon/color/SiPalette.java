package ru.vladislav117.silicon.color;

/**
 * Палитра с часто используемыми цветами.
 */
public final class SiPalette {
    /**
     * Палитра стандартных цветов.
     */
    public static final class Defaults {
        public static final SiColor black = new SiColor(0, 0, 0);
        public static final SiColor darkGray = new SiColor(0.25, 0.25, 0.25);
        public static final SiColor gray = new SiColor(0.5, 0.5, 0.5);
        public static final SiColor lightGray = new SiColor(0.75, 0.75, 0.75);
        public static final SiColor white = new SiColor(1, 1, 1);

        public static final SiColor red = new SiColor(1, 0, 0);
        public static final SiColor green = new SiColor(0, 1, 0);
        public static final SiColor blue = new SiColor(0, 0, 1);

        public static final SiColor yellow = new SiColor(1, 1, 0);
        public static final SiColor purple = new SiColor(1, 0, 1);
        public static final SiColor cyan = new SiColor(0, 1, 1);
    }

    /**
     * Палитра цветов, адаптированных для интерфейса.
     */
    public static final class Interface {
        public static final SiColor black = new SiColor(0, 0, 0);
        public static final SiColor darkGray = new SiColor(0.25, 0.25, 0.25);
        public static final SiColor gray = new SiColor(0.5, 0.5, 0.5);
        public static final SiColor lightGray = new SiColor(0.75, 0.75, 0.75);
        public static final SiColor white = new SiColor(1, 1, 1);

        public static final SiColor red = new SiColor(0.99, 0.33, 0.33);
        public static final SiColor green = new SiColor(0.33, 0.99, 0.33);
        public static final SiColor blue = new SiColor(0.33, 0.33, 0.99);

        public static final SiColor yellow = new SiColor(0.99, 0.99, 0.33);
        public static final SiColor purple = new SiColor(0.99, 0.33, 0.99);
        public static final SiColor cyan = new SiColor(0.33, 0.99, 0.99);

        public static final SiColor gold = new SiColor(0.99, 0.75, 0.1);
    }
}
