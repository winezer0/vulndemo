class Service {
    void Sink(int value) {}
    void Run(int a, int b) { Sink(a); Sink(a + b); }
}
class Alpha { public int value; public void Ping() {} }
class Beta { public int value; public void Pong() {} }
class Owners {
    void First() { Alpha item = new Alpha(); item.value = 1; item.Ping(); } void Second() { Beta item = new Beta(); int n = item.value; item.Pong(); }
}
class Formatter {
    string Format(string left, string right) { return $"{left}:{right}"; }
}
class Pairing {
    (int, string) Echo((int, string) pair) { return pair; }
}
class ConfigReaders { void First() { var a = Environment.GetEnvironmentVariable("TOKEN"); var b = Environment.GetEnvironmentVariable("TOKEN"); } void Second() { var c = Environment.GetEnvironmentVariable("TOKEN"); } }
class ComputedConfig { string Read(string suffix) { return Environment.GetEnvironmentVariable("PRE" + suffix); } }
class Arrays { int[] Make(int left, int right) { return new[] { left, right }; } }
class Switcher { int Choose(int selector, int left, int right) { return selector switch { 0 => left, _ => right }; } }
