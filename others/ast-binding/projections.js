function projectedPair(input) {
    const value = jsSource();
    return ["safe", value];
}

function projectedCaller(value, box) {
    let first;
    [first, box.field] = projectedPair(value);
    sink(box.field);
}
