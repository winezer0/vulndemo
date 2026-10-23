class Resources {
 static class R implements AutoCloseable { public void close() {} }
 R r;
 void run() {
  try (R r = choose(r, next); R next = r) {
   R a = next;
   Runnable f = () -> consume(r);
  } catch (Exception e) {
   consume(r); consume(e);
  } finally { consume(r); }
  consume(e);
 }
 void consume(Object o) {}
}
