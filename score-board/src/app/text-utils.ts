export function toSnakeCase(s: string) {
  return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
}
