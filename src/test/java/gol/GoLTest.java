package gol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GoLTest {

	private Board board = new Board();

	@Test
	public void canCreateBoardAndSetLife() {
		aNewBoard(1, 1);
		withLifeAt(0, 0);
		assertThat(isLifeAt(0, 0), is(true));
	}

	private Object isLifeAt(int x, int y) {
		return board.isLifeAt(x,y);
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
		board.tick();

	}

	private void withLifeAt(int x, int y) {
		this.board.setAlive(x,y);
	}

	private void aNewBoard(int width, int height) {
		this.board.setWidth(width);
		this.board.setHeight(height);
	}

}
