class ConditionalCallProfile {
    public int Ping(int value) { return value; }
}

class ConditionalCallService {
    int Run(ConditionalCallProfile? profile, int value) {
        return profile?.Ping(value) ?? 0;
    }

    int RunNamed(ConditionalCallProfile? profile, int value) {
        int output = profile?.Ping(value: value) ?? 0;
        return output;
    }
}
