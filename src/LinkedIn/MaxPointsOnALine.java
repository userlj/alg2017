package LinkedIn;

import dataStructures.Point;


/**
 * 149. Max Points on a Line
 *
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 *
 * Example 1:
 *
 * Input: [[1,1],[2,2],[3,3]]
 * Output: 3
 * Explanation:
 * ^
 * |
 * |        o
 * |     o
 * |  o
 * +------------->
 * 0  1  2  3  4
 * Example 2:
 *
 * Input: [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * Output: 4
 * Explanation:
 * ^
 * |
 * |  o
 * |     o        o
 * |        o
 * |  o        o
 * +------------------->
 * 0  1  2  3  4  5  6
 */
public class MaxPointsOnALine {
//	public int maxPoints(Point[] points) {
//		if (points == null || points.length == 0) return 0;
//		if (points.length == 1) return 1;
//		if (points.length == 2) return 2;
//		int result = 2;
//		Map<String, Set<Point>> map = new HashMap<>();
//		for (int i = 0; i < points.length - 1; i++) {
//			for (int j = i + 1; j < points.length; j++) {
//				Slope slope = new Slope(points[i].x, points[i].y, points[j].x, points[j].y);
//				String key = slope.toString();
//				map.putIfAbsent(key, new HashSet<>());
//				map.get(key).add(points[i]);
//				map.get(key).add(points[j]);
//				if (map.get(key).size() > result) {
//					result = map.get(key).size();
//				}
//			}
//		}
//		return result;
//	}
//	static class Slope { // y = ax + k
//		double a;
//		double k;
//		Slope(int x1, int y1, int x2, int y2) {
//			a = x1 == x2 ? 0 : y1 != y2 ? (double) (x1 - x2) / (double) (y1 - y2) : Integer.MAX_VALUE;
//			k = x1*y2-x2*y1 == 0 ? 0 : x1 != x2 ? (double) (x1*y2-x2*y1) / (double) (x1 - x2) : Integer.MAX_VALUE;
//			if (a == -0.0) a += 0.0;
//			if (k == -0.0) k += 0.0;
//		}
//		@Override
//		public String toString() {
//			return "a" + a + "k" + k;
//		}
//	}
	public int maxPointsII(Point[] points) {
		int n = points.length;
		if (n < 2) return n;
//		Arrays.sort(points, (Point o1, Point o2) -> (o1.x != o2.x ? o1.x - o2.x : o1.y - o2.y));
		int currentL = 0, maxL = 2, overlap = 0, upperB = n;
		long x = 0, y = 0, dx = 0, dy = 0; // using type long to avoid overflow e.g. 65536*65536

		for(int i = 0; i < upperB; i++) {
			for(int j = i + 1; j < n; j++) {
				currentL = 1;

				x = points[j].y - points[i].y;
				y = points[j].x - points[i].x; // two points determine one line

				if (x == 0 && y == 0) {
					overlap++;

				} else {
					currentL++; // two points are not overlap, plus one for 2nd point

					// find 3rd point
					for (int k = j + 1; k < n && currentL + n - k > maxL; k++) { // currentL + n - k > maxL is optimization, same function as upperB
						dx = points[k].x - points[j].x;
						dy = points[k].y - points[j].y;
						if (x * dx == y * dy) {
							currentL++;
						}
					}
				}
				maxL = Math.max(currentL + overlap, maxL);
			}

			upperB = n - maxL; // optimization, skip unnecessary cases
			overlap = 0;
		}
		return maxL;
	}

	public static void main(String[] args) {
		MaxPointsOnALine mpl = new MaxPointsOnALine();
		System.out.println(mpl.maxPointsII(new Point[]{new Point(1,1), new Point(2,2), new Point(3,3)})); // 3
		System.out.println(mpl.maxPointsII(new Point[]{new Point(0, 0), new Point(2,2), new Point(0, 0), })); // 3
		System.out.println(mpl.maxPointsII(new Point[]{new Point(0, 1), new Point(0,2), new Point(0, 3), // 3
													new Point(1, 1), new Point(2,1), new Point(2, 2)})); // 3
		System.out.println(mpl.maxPointsII(new Point[]{new Point(65536,0), new Point(0,0), new Point(1,65536)})); // 2
		System.out.println(mpl.maxPointsII(new Point[]{new Point(0,0), new Point(1,65536), new Point(65536,0)})); // 2
	}
}
