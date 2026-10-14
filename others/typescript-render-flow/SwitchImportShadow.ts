import * as api from './api';
export function run(kind: number) {
  switch (kind) {
    case 0: const api = local; api.send(); break;
    default: api.send();
  }
  api.send();
}
