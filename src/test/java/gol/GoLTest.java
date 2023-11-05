package gol;

import static gol.BoardMatcher.boardOf;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.IntStream;

import org.junit.Test;

import gol.Board.Coordinate;

public class GoLTest {

	private Board board;

	@Test
	public void canCreateBoardAndSetLife() {
		aBoard("X");
		resultsIn("X");
	}

	@Test
	public void whereNoLifeIsSetThereIsNoLife() {
		aBoard("-X");
		resultsIn("-X");
	}

	@Test
	public void afterTickThereIsNoLife() {
		aBoard("X");
		thatIsTicked();
		resultsIn("-");
	}

	@Test
	public void cellsWithThreeNeighboursWillSurvive() {
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
	public void cellsWithTwoNeighboursWillSurvive() {
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
	public void newLifeIsBorn() {
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
		this.board = new Board(width, height);
	}

	private void withLifeAt(int x, int y) {
		Board r = this.board;
		r.setAlive(new Coordinate(x, y));
	}

	private void thatIsTicked() {
		board.tick();
	}

}
