function run(outerValue: string, innerValue: string, rows: { active: boolean; inner: boolean }[], row: { active: boolean; inner: boolean }) {
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
