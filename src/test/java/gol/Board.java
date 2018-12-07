package gol;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Board {

	private static class Point {

		private int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return 31 * x + y;
		}

		@Override
		public boolean equals(Object obj) {
			Point other = (Point) obj;
			return other.x == x && other.y == y;
		}

		Stream<Point> neighbours() {
			return range(y - 1, y + 2).mapToObj(y -> range(x - 1, x + 2).mapToObj(x -> new Point(x, y)))
					.flatMap(identity());
		}

	}

	private Set<Point> lifeCells = new HashSet<Point>();
	private int width;
	private int height;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void tick() {
		this.lifeCells = cells().filter(this::alifeInNextGen).collect(toSet());

	}

	private Stream<Point> cells() {
		return range(0, getHeight()).mapToObj(y -> range(0, getHeight()).mapToObj(x -> new Point(x, y)))
				.flatMap(identity());
	}

	private boolean alifeInNextGen(Point point) {
		boolean alife = isAlive(point);
		long alifeNeighbours = alifeNeighbours(point);
		return alife && (alifeNeighbours == 2 || alifeNeighbours == 3) //
				|| !alife && (alifeNeighbours == 3);
	}

	private long alifeNeighbours(Point point) {
		return point.neighbours().filter(isEqual(point).negate()).filter(this::isAlive).count();
	}

	private boolean isAlive(Point point) {
		return isAlive(point.x, point.y);
	}

	boolean isAlive(int x, int y) {
		return lifeCells.contains(point(x, y));
	}

	public void setAlive(int x, int y) {
		lifeCells.add(point(x, y));
	}

	private Point point(int x, int y) {
		return new Point(x, y);
	}

}