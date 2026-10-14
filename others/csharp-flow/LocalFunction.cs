class LocalFunctionBox {
    string Name;
    string Run() {
        string Inner() { return this.Name; }
        return Inner();
    }
}
