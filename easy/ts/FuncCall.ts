import { VulnChild } from './common/VulnChild';
import { InterfaceImpl } from './cmd_exec/InterfaceImpl';
import { VulnRunner } from './iface/VulnRunner';

export class FuncCall {
    static runByChild(cmd: string): void {
        const child = new VulnChild();
        child.exec(cmd);
    }

    static runByInterface(cmd: string): void {
        const runner: VulnRunner = new InterfaceImpl();
        runner.run(cmd);
    }
}
