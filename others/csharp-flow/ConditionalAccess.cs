class ConditionalProfile {
    public string Name;
}

class ConditionalService {
    string Read(ConditionalProfile? profile) {
        return profile?.Name;
    }
}
