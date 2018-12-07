package gol;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

public class GoLTest {

	private static final class BoardMatcher extends TypeSafeMatcher<Board> {

		private final String expected;

		private BoardMatcher(String... rows) {
			expected = Stream.of(rows).collect(joining("\n"));
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("\n" + expected);
		}

		@Override
		public boolean matchesSafely(Board board) {
			return actual(board).equals(expected);
		}

		@Override
		protected void describeMismatchSafely(Board board, Description description) {
			description.appendText("\n" + actual(board));
		}

		private String actual(Board board) {
			return range(0, board.getHeight()).mapToObj(y -> row(board, y)).collect(joining("\n"));
		}

		private String row(Board board, int y) {
			return range(0, board.getWidth()).mapToObj(x -> board.isLifeAt(x, y) ? "X" : "-").collect(joining());
		}
	}

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
		thatIsTicked();
		assertThat(isLifeAt(0, 0), is(false));
	}

	@Test
	public void cellsWithThreeNeighboursWillSurvive() {
		aBoard( //
				"XX", //
				"XX" //
		);
		thatIsTicked();
		assertThat(board, is(board( //
				"XX", //
				"XX" //
		)));
	}

	@Test
	public void cellsWithTwoNeighboursWillSurvive() {
		aBoard( //
				"X--", //
				"-X-", //
				"--X" //
		);
		thatIsTicked();
		resultsIn("---", //
				"-X-", //
				"---");
	}

	private void resultsIn(String... rows) {
		assertThat(board, is(board(rows)));
	}

	@Test
	public void newLifeIsBorn() {
		aBoard( //
				"XXX", //
				"---" //
		);
		thatIsTicked();
		assertThat(board, is(board( //
				"-X-", //
				"-X-" //
		)));
	}

	private TypeSafeMatcher<Board> board(String... rows) {
		return new BoardMatcher(rows);
	}

	private void aBoard(String... rows) {
		aNewBoard(rows[0].length(), rows.length);
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

	private void thatIsTicked() {
		board.tick();

	}

	private void withLifeAt(int x, int y) {
		this.board.setAlive(x, y);
	}

	private void aNewBoard(int width, int height) {
		this.board = new Board(width, height);
	}

}
