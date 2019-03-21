import {asPercentageOfMinMaxInterval} from './math.utils';

describe('Math utils', () => {

  describe('asPercentageOfMinMaxInterval', () => {
    it('should return each value as percentage between min and max values', function () {
      expect(asPercentageOfMinMaxInterval([5, 10, 15])).toEqual([0, 0.5, 1]);
    });

    it('should return empty array for empty input', function () {
      expect(asPercentageOfMinMaxInterval([])).toEqual([]);
    });

    it('should return 100% for one value', function () {
      expect(asPercentageOfMinMaxInterval([1])).toEqual([1]);
    });

    it('should return null for null values', function () {
      expect(asPercentageOfMinMaxInterval([5, null, 10, 15, null])).toEqual([0, null, 0.5, 1, null]);
    });

    it('should return null for undefined values', function () {
      expect(asPercentageOfMinMaxInterval([5, undefined, 10, 15, undefined])).toEqual([0, null, 0.5, 1, null]);
    });

    it('should return 100% when min equal max', function () {
      expect(asPercentageOfMinMaxInterval([5, 5, null])).toEqual([1, 1, null]);
    });
  });
});
