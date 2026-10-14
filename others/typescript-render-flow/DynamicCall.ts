export function run(api: Record<string, () => void>, key: string) { api[key](); api['fixed'](); }
