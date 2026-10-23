class Closures {
 Object value;
 void run() {
  Object captured = new Object();
  java.util.function.Function<Object,Object> f = (value) -> value;
  java.util.function.Supplier<Object> g = () -> captured;
  Object use = value;
  { Object value = null; Object inner = value; }
  Object again = value;
 }
}
