import { execSync } from 'child_process';

export class VulnParent {
    exec(cmd: string): void {
        execSync(cmd);
    }
}
