public class NestedChild { public int Value; }
public class NestedParent { public NestedChild Next; }
public class NestedReader { public int Read(NestedParent parent) { return parent.Next.Value; } }
