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

public final class Board {

	public static record Coordinate(int x, int y) {
		Stream<Coordinate> neighbours() {
			return rangeClosed(y - 1, y + 1)
					.mapToObj(y -> rangeClosed(x - 1, x + 1).mapToObj(x -> new Coordinate(x, y))).flatMap(identity())
					.filter(not(isEqual(this)));
		}
	}

	private Set<Coordinate> lifeCells = new HashSet<Coordinate>();

	public final int width, height;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void tick() {
		this.lifeCells = range(0, height).mapToObj(y -> range(0, width).mapToObj(x -> new Coordinate(x, y)))
				.flatMap(identity()).filter(this::aliveInNextGen).collect(toSet());
	}

	private boolean aliveInNextGen(Coordinate coordinate) {
		long aliveNeighbours = coordinate.neighbours().filter(this::isAlive).count();
		return aliveNeighbours == 3 || isAlive(coordinate) && aliveNeighbours == 2;
	}

	public boolean isAlive(Coordinate coordinate) {
		return lifeCells.contains(coordinate);
	}

	public void setAlive(Coordinate coordinate) {
		lifeCells.add(coordinate);
	}

}
