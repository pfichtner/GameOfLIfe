package gol;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

import java.util.stream.Stream;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

final class BoardMatcher extends TypeSafeMatcher<Board> {

	private final String expected;

	private BoardMatcher(String... rows) {
		expected = Stream.of(rows).collect(joining("\n"));
	}

	public static BoardMatcher boardOf(String... rows) {
		return new BoardMatcher(rows);
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
		return range(0, board.getWidth()).mapToObj(x -> board.isAlive(x, y) ? "X" : "-").collect(joining());
	}
}