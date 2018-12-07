package gol;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class GoLTest {

	@Test
	public void canCreateBoardAndSetLife() {
		aNewBoard(1, 1);
		withLifeAt(0, 0);
		hasLifeAt(0, 0);

	}

	private void hasLifeAt(int i, int j) {
		assertThat(isLifeAt(i, j), CoreMatchers.is(true));

	}

	private boolean isLifeAt(int i, int j) {
		return false;
	}

	private void withLifeAt(int i, int j) {
		// TODO Auto-generated method stub

	}

	private void aNewBoard(int i, int j) {
		// TODO Auto-generated method stub

	}

}
