import { send as dispatch, Box } from "./lib";
import * as api from "./api";
export function local(dispatch: (v: string) => void, Box: new () => object, api: {send(v: string): void}) {
  dispatch("x"); new Box(); api.send("x");
}
export function imported() { dispatch("y"); new Box(); api.send("y"); }
