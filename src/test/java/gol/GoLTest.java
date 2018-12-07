package gol;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.awt.Point;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Description;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

public class GoLTest {

	private Board board;

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

		assertThat(board, is(board( //
				"XX", //
				"XX" //
		)));

		assertThat(isLifeAt(0, 0), is(true));
		assertThat(isLifeAt(0, 1), is(true));
		assertThat(isLifeAt(1, 0), is(true));
		assertThat(isLifeAt(1, 1), is(true));
	}

	private TypeSafeMatcher<Board> board(String... rows) {
		String expected = range(0, board.getHeight()).mapToObj(y -> row(y)).collect(joining("\n"));
		return new TypeSafeMatcher<Board>() {

			@Override
			public void describeTo(Description arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean matchesSafely(Board board) {
				String actual = range(0, board.getHeight()).mapToObj(y -> row(y)).collect(joining("\n"));
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

	private String row(int y) {
		Stream<String> s = range(0, board.getHeight()).mapToObj(x -> board.isLifeAt(x, y) ? "X" : " ");
		return s.collect(Collectors.joining());
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

	private boolean isLifeAt(int x, int y) {
		return board.isLifeAt(x, y);
	}

	private void whenTicked() {
		board.tick();

	}

	private void withLifeAt(int x, int y) {
		this.board.setAlive(x, y);
	}

	private void aNewBoard(int width, int height) {
		this.board = new Board(width, height);
	}

}
