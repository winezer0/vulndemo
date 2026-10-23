import static java.lang.Math.abs;
class Members extends ExternalBase {
 int value;
 static int run() { return value; }
 int abs(String s) { return 0; }
 int call() { return abs(1); }
 class Inner { int value; int read() { return value; } }
 int unknown() { return missing; }
}
