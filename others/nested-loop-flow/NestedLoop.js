function run(outerValue, innerValue, rows, row) {
  let value = outerValue;
  for (const row of rows) {
    while (row.active) {
      if (row.inner) {
        let value = innerValue;
        sink(value);
      }
    }
  }
  sink(value);
  sink(row);
}
