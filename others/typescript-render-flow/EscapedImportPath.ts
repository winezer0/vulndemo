import { send as bad } from './li\x62';
import { send as good } from './lib';
export { send } from './li\x62';
export { send as exact } from './lib';
export function use() {
  bad();
  good();
  import('./li\x62');
  import('./lib');
}
