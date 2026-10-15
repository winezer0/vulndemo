using System;

class ReflectionDemo {
    void Run() {}

    void InvokeIt(Type type, object value) {
        type.GetMethod(nameof(Run)).Invoke(value, null);
    }
}
