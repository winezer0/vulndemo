mod common;
mod iface;
mod cmd_exec;
mod func_call;

use std::env;

fn main() {
    let args: Vec<String> = env::args().collect();
    let cmd = if args.len() > 1 { &args[1] } else { "calc" };
    
    // func_call::run_by_child(cmd);
    func_call::run_by_interface(cmd);
}
