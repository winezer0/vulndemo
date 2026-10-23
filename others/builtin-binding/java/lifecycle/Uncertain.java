package lexical;
class Uncertain {
 record Pair(Object a, Object b) {}
 void run(Object o, Service receiver) {
  if (o instanceof Pair(String a, String b)) { receiver.hit(); } // lexical:unknown
 }
}
