class InitialValues {
    static class Plain { String text = ""; String empty; }
    static class Constructed { String text = ""; Constructed() { text = unknown(); } }
    static class Dynamic { String text = ""; String effect = unknown(); }
    static class Initialized { String text = ""; { text = unknown(); } }
    static class Parent { String text = ""; }
    static class Inherited extends Parent { }
    static String unknown() { return System.getenv("VALUE"); }
    static String literal() { Plain box = new Plain(); return box.text; }
    static String defaultValue() { Plain box = new Plain(); return box.empty; }
    static String separate() { Plain first = new Plain(); Plain second = new Plain(); return second.text; }
    static String constructor() { Constructed box = new Constructed(); return box.text; }
    static String dynamic() { Dynamic box = new Dynamic(); return box.text; }
    static String block() { Initialized box = new Initialized(); return box.text; }
    static String inherited() { Inherited box = new Inherited(); return box.text; }
}
