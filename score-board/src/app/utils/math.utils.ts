export function asPercentageOfMinMaxInterval(numbers: number[]): number[] {
  const nonNullNumbers = numbers.filter(number => number != null);
  const min = Math.min(...nonNullNumbers);
  const max = Math.max(...nonNullNumbers);
  return numbers.map((number) => {
    if (number == null) {
      return null;
    }
    if (min === max) {
      return 1;
    }
    return (number - min) / (max - min);
  });

}
