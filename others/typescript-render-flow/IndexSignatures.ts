export class Left { [key: string]: number; } export class Right { [key: string]: boolean; }
export interface Named { title: string; [key: string]: string; }

export function first() { class Box { constructor(public value: string) {} } }
export function second() { class Box { constructor(public value: number) {} } }

export function third() { class Shared { value: string; run() {} } }
export function fourth() { class Shared { value: number; run() {} } }
