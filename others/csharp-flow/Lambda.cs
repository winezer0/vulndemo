using System;
class LambdaBox {
    string Name;
    string Run() {
        Func<string> inner = () => this.Name;
        return inner();
    }
}
