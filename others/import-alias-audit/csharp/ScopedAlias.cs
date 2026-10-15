namespace First {
    using WorkerAlias = App.Core.Worker;
    public class FirstCaller {
        public void CallFirst() { WorkerAlias.Run(); }
        public void CallShadow(WorkerAlias WorkerAlias) { WorkerAlias.Run(); }
    }
}

namespace Second {
    public class SecondCaller {
        public void CallSecond() { WorkerAlias.Run(); }
    }
}
