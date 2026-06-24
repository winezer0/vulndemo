import { VulnChild } from '../common/VulnChild';
import { VulnRunner } from '../iface/VulnRunner';

export class InterfaceImpl extends VulnChild implements VulnRunner {
    run(cmd: string): void {
        this.exec(cmd);
    }
}
