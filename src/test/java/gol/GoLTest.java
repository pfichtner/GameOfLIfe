package gol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class GoLTest {

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

	private boolean[] cells = new boolean[2];
	private Set<Point> lifeCells = new HashSet<Point>();

	@Test
	public void canCreateBoardAndSetLife() {
		aNewBoard(1, 1);
		withLifeAt(0, 0);
		hasLifeAt(0, 0);
	}

	@Test
	public void whereNoLifeIsSetThereIsNoLife() {
		aNewBoard(2, 1);
		withLifeAt(1, 0);
		hasNoLifeAt(0, 0);
	}

	private void hasNoLifeAt(int x, int y) {
		assertThat(isLifeAt(x, y), is(false));
	}

	private void hasLifeAt(int x, int y) {
		assertThat(isLifeAt(x, y), is(true));
	}

	private boolean isLifeAt(int x, int y) {
		return this.lifeCells.contains(point(x, y));
	}

	private void withLifeAt(int x, int y) {
		this.cells[x] = true;
		this.lifeCells.add(point(x, y));
	}

	private Point point(int x, int y) {
		return new Point(x, y);
	}

	private void aNewBoard(int width, int height) {
		// TODO Auto-generated method stub

	}

}
