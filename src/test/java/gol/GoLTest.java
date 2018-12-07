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

	private Set<Point> lifeCells = new HashSet<Point>();

	@Test
	public void canCreateBoardAndSetLife() {
		aNewBoard(1, 1);
		withLifeAt(0, 0);
		assertThat(isLifeAt(0, 0), is(true));
	}

	@Test
	public void whereNoLifeIsSetThereIsNoLife() {
		aNewBoard(2, 1);
		withLifeAt(1, 0);
		assertThat(isLifeAt(0, 0), is(false));
	}

	@Test
	public void afterTickThereIsNoLife() {
		aNewBoard(1, 1);
		withLifeAt(0, 0);
		whenTicked();
		assertThat(isLifeAt(0, 0), is(false));
	}

	@Test
	public void cellsWithThreeNeighboursWillSurvive() {
		aNewBoard(2, 2);
		withLifeAt(0, 0);
		withLifeAt(0, 1);
		withLifeAt(1, 0);
		withLifeAt(1, 1);
		whenTicked();
		assertThat(isLifeAt(0, 0), is(true));
		assertThat(isLifeAt(0, 1), is(true));
		assertThat(isLifeAt(1, 0), is(true));
		assertThat(isLifeAt(1, 1), is(true));
	}

	private void whenTicked() {
		HashSet<Point> nextGen = new HashSet<Point>();
		this.lifeCells = nextGen;
	}

	private boolean isLifeAt(int x, int y) {
		return this.lifeCells.contains(point(x, y));
	}

	private void withLifeAt(int x, int y) {
		this.lifeCells.add(point(x, y));
	}

	private Point point(int x, int y) {
		return new Point(x, y);
	}

	private void aNewBoard(int width, int height) {
		// TODO Auto-generated method stub

	}

}
