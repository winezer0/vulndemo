public class NullableProfile { public string Name { get; set; } }
public class NullableReader {
    public string Read(NullableProfile? profile) { return profile.Name; }
}
