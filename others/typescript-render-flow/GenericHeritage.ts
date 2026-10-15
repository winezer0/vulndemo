interface Pair<A, B> {}
interface Marker {}
interface Derived extends Pair<string, number>, Marker {}
class Impl implements Pair<string, number>, Marker {}
