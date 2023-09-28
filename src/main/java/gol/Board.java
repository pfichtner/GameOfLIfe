package gol;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.isEqual;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Board {

	private static class Coordinate {

		private final int x, y;

		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return 31 * x + y;
		}

		@Override
		public boolean equals(Object obj) {
			Coordinate other = (Coordinate) obj;
			return other.x == x && other.y == y;
		}

		Stream<Coordinate> neighbours() {
			return rangeClosed(y - 1, y + 1).mapToObj(y -> rangeClosed(x - 1, x + 1).mapToObj(x -> coordinate(x, y)))
					.flatMap(identity()).filter(not(isEqual(this)));
		}

	}

	private Set<Coordinate> lifeCells = new HashSet<Coordinate>();
	private final int width;
	private final int height;

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
		this.lifeCells = cells().filter(this::aliveInNextGen).collect(toSet());
	}

	private Stream<Coordinate> cells() {
		return range(0, getHeight()).mapToObj(y -> range(0, getWidth()).mapToObj(x -> coordinate(x, y)))
				.flatMap(identity());
	}

	private boolean aliveInNextGen(Coordinate coordinate) {
		long aliveNeighbours = aliveNeighbours(coordinate);
		return isAlive(coordinate) ? !shouldDie(aliveNeighbours) : shouldBornNewLife(aliveNeighbours);
	}

	private static boolean shouldDie(long aliveNeighbours) {
		return aliveNeighbours < 2 || aliveNeighbours > 3;
	}

	private static boolean shouldBornNewLife(long aliveNeighbours) {
		return aliveNeighbours == 3;
	}

	private long aliveNeighbours(Coordinate coordinate) {
		return coordinate.neighbours().filter(this::isAlive).count();
	}

	public boolean isAlive(int x, int y) {
		return isAlive(coordinate(x, y));
	}

	private boolean isAlive(Coordinate coordinate) {
		return lifeCells.contains(coordinate);
	}

	public void setAlive(int x, int y) {
		lifeCells.add(coordinate(x, y));
	}

	private static Coordinate coordinate(int x, int y) {
		return new Coordinate(x, y);
	}

}
