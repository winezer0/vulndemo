class Unsupported {
 record Pair(Object left, Object right) {}
 void run(Object o) {
  if (o instanceof Pair(String left, String right)) {
   System.out.println(left);
  }
 }
}
