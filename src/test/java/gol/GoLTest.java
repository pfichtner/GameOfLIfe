package gol;

import static java.util.function.Function.identity;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.stream.IntStream;

import org.junit.Test;

import gol.GoLTest.Point;

public class GoLTest {

	

	private Board board = new Board();

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
		aBoard( //
				"XX", //
				"XX" //
		);
		whenTicked();
		assertThat(isLifeAt(0, 0), is(true));
		assertThat(isLifeAt(0, 1), is(true));
		assertThat(isLifeAt(1, 0), is(true));
		assertThat(isLifeAt(1, 1), is(true));
	}

	private void aBoard(String... rows) {
		aNewBoard(2, rows.length);
		for (int y = 0; y < rows.length; y++) {
			char[] charArray = rows[y].toCharArray();
			for (int x = 0; x < charArray.length; x++) {
				if (charArray[x] == 'X') {
					withLifeAt(x, y);
				}
			}
		}

	}

	private void whenTicked() {
		this.board.setLifeCells(IntStream.range(0, board.getHeight())
				.mapToObj(y -> IntStream.range(0, board.getHeight()).mapToObj(x -> new Point(x, y))).flatMap(identity())
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

	private boolean isLifeAt(int x, int y) {
		return this.board.getLifeCells().contains(point(x, y));
	}

	private void withLifeAt(int x, int y) {
		this.board.getLifeCells().add(point(x, y));
	}

	private Point point(int x, int y) {
		return new Point(x, y);
	}

	private void aNewBoard(int width, int height) {
		this.board.setWidth(width);
		this.board.setHeight(height);
	}

}
