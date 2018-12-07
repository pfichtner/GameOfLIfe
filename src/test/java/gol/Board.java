package gol;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

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

	}

	private Set<Point> lifeCells = new HashSet<Point>();
	private int width;
	private int height;

	public Set<Point> getLifeCells() {
		return lifeCells;
	}

	public void setLifeCells(Set<Point> lifeCells) {
		this.lifeCells = lifeCells;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void tick() {
		setLifeCells(IntStream.range(0, getHeight())
				.mapToObj(y -> IntStream.range(0, getHeight()).mapToObj(x -> new Point(x, y))).flatMap(identity())
				.filter(this::alifeInNextGen).collect(toSet()));

	}

	private boolean alifeInNextGen(Point point) {
		boolean alife = isLifeAt(point);
		return alife && alifeNeighbours(point) == 3;
	}

	private long alifeNeighbours(Point thisPoint) {
		return range(thisPoint.y - 1, thisPoint.y + 2)
				.mapToObj(y -> range(thisPoint.x - 1, thisPoint.x + 2).mapToObj(x -> new Point(x, y)))
				.flatMap(identity()).filter(isEqual(thisPoint).negate()).filter(this::isLifeAt).count();
	}

	private boolean isLifeAt(Point point) {
		return isLifeAt(point.x, point.y);
	}

	boolean isLifeAt(int x, int y) {
		return getLifeCells().contains(point(x, y));
	}

	private Point point(int x, int y) {
		return new Point(x, y);
	}

	public void setAlive(int x, int y) {
		lifeCells.add(point(x, y));
	}

}