fn first() { info!("first"); tx_a.send(1); } fn second() { info!("second"); tx_b.send(2); }
