fn read_both() { let first = || std::env::var("FIRST"); let second = || std::env::var("SECOND"); let _ = (first, second); }
