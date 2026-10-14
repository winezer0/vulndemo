export function outer(target: string) {
  return ({target: alias = target}: {target?: string}) => target + alias;
}
