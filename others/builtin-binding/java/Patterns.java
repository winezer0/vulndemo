class Patterns {
 void consume(Object o) {}
 void positive(Object o) {
  if (o instanceof String s) { consume(s); } else { consume(s); }
  if (o instanceof String a && a.isEmpty()) { consume(a); }
  if (!(o instanceof String b) || b.isEmpty()) { consume(b); }
 }
 void exit(Object o) {
  if (!(o instanceof String c)) return;
  consume(c);
 }
}
