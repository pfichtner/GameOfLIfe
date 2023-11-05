package gol;

import static gol.BoardMatcher.board;
import static java.util.stream.IntStream.range;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static gol.GolStringTemplate.GOL;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.junit.Test;

public class GoLTest {

	private Board board;

	@Test
	public void canCreateBoardAndSetLife() {
		aBoard(GOL."X");
		resultsIn(GOL."X");
	}

	@Test
	public void whereNoLifeIsSetThereIsNoLife() {
		aBoard(GOL."-X");
		resultsIn(GOL."-X");
	}

	@Test
	public void afterTickThereIsNoLife() {
		aBoard(GOL."X");
		thatIsTicked();
		resultsIn(GOL."-");
	}

	@Test
	public void cellsWithThreeNeighboursWillSurvive() {
		aBoard(GOL."""
				XX
				XX""" //
		);
		thatIsTicked();
		resultsIn(GOL."""
				XX
				XX""" //
		);
	}

	@Test
	public void cellsWithTwoNeighboursWillSurvive() {
		aBoard(GOL."""
				X--
				-X-
				--X""");
		thatIsTicked();
		resultsIn(GOL."""
				---
				-X-
				---""" //
		);
	}

	@Test
	public void newLifeIsBorn() {
		aBoard(GOL."""
				X-X
				---
				-X-""" //
		);
		thatIsTicked();
		resultsIn(GOL."""
				---
				-X-
				---""" //
		);
	}

	private void aBoard(Board board) {
		this.board = board;
	}

	private void resultsIn(Board board) {
		assertThat(this.board, board(board));
	}

	private void thatIsTicked() {
		board.tick();
	}

}
