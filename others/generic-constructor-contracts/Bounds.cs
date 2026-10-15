namespace BoundContracts;
public interface IBase {}
public class Box<T> where T : IBase, new() {}
public class Other<T> where T : IBase {}
