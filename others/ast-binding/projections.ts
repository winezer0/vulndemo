function typedProjectedPair(input: string) {
    const value = tsSource();
    return ["safe", value] as const;
}

function typedProjectedCaller(value: string, box: { field: string }) {
    let first: string;
    [first, box.field] = typedProjectedPair(value);
    sink(box.field);
}
