package gol;

import java.util.HashSet;
import java.util.Set;

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
}