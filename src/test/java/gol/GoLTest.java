package gol;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
	private int width;
	private int height;

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
		Set<Point> nextGen = new HashSet<Point>();

		IntStream yr = IntStream.range(0, height);
		Stream<Stream<Point>> mapToObj = yr.mapToObj(y -> IntStream.range(0, height).mapToObj(x -> new Point(x, y)));
		Stream<Point> points = mapToObj.flatMap(identity());
		points.filter(point -> {
			boolean alife = isLifeAt(point);
			if (alife && alifeNeighbours(point) == 3) {
				return true;
			}
			return false;
		});

		IntStream.range(0, height).forEach(y -> {
			IntStream.range(0, height).forEach(x -> {
				Point point = new Point(x, y);
				boolean alife = isLifeAt(x, y);
				if (alife && alifeNeighbours(point) == 3) {
					nextGen.add(point);
				}
			});
		});

		this.lifeCells = nextGen;
	}

	private long alifeNeighbours(Point thisPoint) {
		return range(thisPoint.y - 1, thisPoint.y + 2)
				.mapToObj(y -> range(thisPoint.x - 1, thisPoint.x + 2).mapToObj(x -> new Point(x, y)))
				.flatMap(identity()).filter(isEqual(thisPoint).negate()).filter(this::isLifeAt).count();
	}

	private boolean isLifeAt(Point point) {
		return isLifeAt(point.x, point.y);
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
		this.width = width;
		this.height = height;
	}

}
