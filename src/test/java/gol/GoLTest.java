package gol;

import static gol.BoardMatcher.boardOf;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.stream.IntStream;

import org.junit.Test;

class GoLTest {

	private BoardView board;

	@Test
	void canCreateBoardAndSetLife() {
		aBoard("X");
		resultsIn("X");
	}

	@Test
	void whereNoLifeIsSetThereIsNoLife() {
		aBoard("-X");
		resultsIn("-X");
	}

	@Test
	void afterTickThereIsNoLife() {
		aBoard("X");
		thatIsTicked();
		resultsIn("-");
	}

	@Test
	void cellsWithThreeNeighboursWillSurvive() {
		aBoard( //
				"XX", //
				"XX" //
		);
		thatIsTicked();
		resultsIn( //
				"XX", //
				"XX");
	}

	@Test
	void cellsWithTwoNeighboursWillSurvive() {
		aBoard( //
				"X--", //
				"-X-", //
				"--X" //
		);
		thatIsTicked();
		resultsIn( //
				"---", //
				"-X-", //
				"---");
	}

	@Test
	void newLifeIsBorn() {
		aBoard( //
				"X-X", //
				"---", //
				"-X-" //
		);
		thatIsTicked();
		resultsIn( //
				"---", //
				"-X-", //
				"---" //
		);
	}

	private void resultsIn(String... rows) {
		assertThat(board, is(boardOf(rows)));
	}

	private void aBoard(String... rows) {
		aNewBoard(rows[0].length(), rows.length);
		range(0, rows.length).forEach(y -> setLifeInRow(rows, y));

	}

	private void setLifeInRow(String[] rows, int y) {
		char[] charArray = rows[y].toCharArray();
		IntStream.range(0, charArray.length).filter(x -> charArray[x] == 'X').forEach(x -> withLifeAt(x, y));
	}

	private void aNewBoard(int width, int height) {
		this.board = new BoardView(width, height, new Board());
	}

	private void withLifeAt(int x, int y) {
		this.board.setAlive(x, y);
	}

	private void thatIsTicked() {
		board.tick();
	}

}
