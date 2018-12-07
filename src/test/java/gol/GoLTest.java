package gol;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class GoLTest {
	
	private boolean[] cells = new boolean[2];

	@Test
	public void canCreateBoardAndSetLife() {
		aNewBoard(1, 1);
		withLifeAt(0, 0);
		hasLifeAt(0, 0);
	}

	@Test
	public void whereNoLifeIsSetThereIsNoLife() {
		aNewBoard(1, 2);
		withLifeAt(0, 1);
		hasNoLifeAt(0, 0);
	}

	private void hasNoLifeAt(int x, int y) {
		assertThat(isLifeAt(x, y), is(false));
	}

	private void hasLifeAt(int x, int y) {
		assertThat(isLifeAt(x, y), is(true));
	}

	private boolean isLifeAt(int x, int y) {
		return true;
	}

	private void withLifeAt(int x, int y) {
		// TODO Auto-generated method stub

	}

	private void aNewBoard(int width, int height) {
		// TODO Auto-generated method stub

	}

}
