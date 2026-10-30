function jsSink(value) {}

function jsTarget(first, second) {
    jsSink(first);
    jsSink(second);
}

function jsCaller(input) {
    const value = input;
    jsTarget("safe", value);
}

function jsSpreadCaller(input) {
    const value = input;
    jsTarget(...value);
}

function jsRest(first, ...values) {
    jsSink(values);
}

function jsRestCaller(input) {
    const value = input;
    jsRest("safe", "safe", value);
}

function jsSource() { return "input"; }

function jsReturnTarget() {
    const value = jsSource();
    return value;
}

function jsWrappedReturnTarget() {
    const value = jsSource();
    return ((value));
}

function jsWrappedReturnCaller() {
    const second = jsWrappedReturnTarget();
    jsSink(second);
}

function jsReturnCaller() {
    const second = jsReturnTarget();
    jsSink(second);
}

function jsParenthesizedReturnCaller() {
    const second = ((jsReturnTarget()));
    jsSink(second);
}

class JSService {
    receiverMethod() {
        jsSink(this.field);
    }
    method(first, second) {
        jsSink(first);
        jsSink(second);
    }
}

function jsMethodCaller(input) {
    const value = input;
    const service = new JSService();
    service.method("safe", value);
}

function jsFixedSpreadCaller(input) {
    const value = input;
    jsTarget(...["safe", value]);
}

function jsHoleSpreadCaller(input) {
    const value = input;
    jsTarget(...[, value]);
}

function jsReboundCaller(input, replacement) {
    const value = input;
    const jsTarget = replacement;
    jsTarget("safe", value);
}

function jsTupleReturnTarget() {
    const value = jsSource();
    return ["safe", value];
}

function jsDestructuredReturnCaller() {
    const [first, second] = jsTupleReturnTarget();
    jsSink(first);
    jsSink(second);
}

function jsReceiverCaller(input) {
    const value = input;
    value.receiverMethod();
}
