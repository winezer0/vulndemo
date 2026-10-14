class KnownBox { x = 1; }
export function inspectUnknownMember(obj: any) {
  const value = obj.x;
  obj.x = value;
}
export function inspectKnownMember(obj: KnownBox) {
  return obj.x;
}
