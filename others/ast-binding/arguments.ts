function tsSink(value: string | string[]): void {}

function tsTarget(first: string, second: string): void {
    tsSink(first);
    tsSink(second);
}

function tsCaller(input: string): void {
    const value = input;
    tsTarget("safe", value);
}

function tsRest(first: string, ...values: string[]): void {
    tsSink(values);
}

function tsRestCaller(input: string): void {
    const value = input;
    tsRest("safe", "safe", value);
}

function tsSpreadCaller(input: string[]): void {
    const value = input;
    tsRest(...value);
}

function tsSource(): string { return "input"; }

function tsReturnTarget(): string {
    const value = tsSource();
    return value;
}

function tsWrappedReturnTarget(): string {
    const value = tsSource();
    return ((value));
}

function tsWrappedReturnCaller(): void {
    const second = tsWrappedReturnTarget();
    tsSink(second);
}

function tsReturnCaller(): void {
    const second = tsReturnTarget();
    tsSink(second);
}

function tsParenthesizedReturnCaller(): void {
    const second = ((tsReturnTarget()));
    tsSink(second);
}

class TSService {
    field: string;
    receiverMethod(): void {
        tsSink(this.field);
    }
    method(first: string, second: string): void {
        tsSink(first);
        tsSink(second);
    }
}

function tsMethodCaller(input: string): void {
    const value = input;
    const service = new TSService();
    service.method("safe", value);
}

function tsFixedSpreadCaller(input: string): void {
    const value = input;
    tsTarget(...["safe", value] as [string, string]);
}

function tsDynamicSpreadCaller(input: string): void {
    const value = input;
    const parts: [string, string] = ["safe", value];
    tsTarget(...parts as [string, string]);
}

function tsLiteralSpreadCaller(input: string): void {
    const value = input;
    tsTarget(...["safe", value]);
}

function tsHoleSpreadCaller(input: string): void {
    const value = input;
    tsTarget(...[, value]);
}

function tsReboundCaller(input: string, replacement: typeof tsTarget): void {
    const value = input;
    const tsTarget = replacement;
    tsTarget("safe", value);
}

function tsTupleReturnTarget(): [string, string] {
    const value = tsSource();
    return ["safe", value];
}

function tsDestructuredReturnCaller(): void {
    const [first, second] = tsTupleReturnTarget();
    tsSink(first);
    tsSink(second);
}

function tsReceiverCaller(input: TSService): void {
    const value = input;
    value.receiverMethod();
}
