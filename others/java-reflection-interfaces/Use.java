class Registry { String forName(String key) { return key; } }
class Worker extends alpha.Base implements alpha.Reader, beta.Reader<String> {
 Registry registry;
 void run() {
  registry.forName("Worker");
  Class.forName("Worker");
 }
 void shadow(Registry Class) { Class.forName("Worker"); }
}
