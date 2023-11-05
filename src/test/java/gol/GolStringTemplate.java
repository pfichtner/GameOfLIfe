package gol;

import static java.util.stream.IntStream.range;

import java.util.function.Function;
import java.util.stream.IntStream;

public class GolStringTemplate {

	@FunctionalInterface
	public interface Processor<R, E extends Throwable> {

		R process(StringTemplate stringTemplate) throws E;

		static <T> Processor<T, RuntimeException> of(Function<? super StringTemplate, ? extends T> process) {
			return process::apply;
		}

	}

	public static final StringTemplate.Processor<Board, RuntimeException> GOL = StringTemplate.Processor //
			.of((StringTemplate template) -> stringToBoard(template.interpolate()));
	
	private static Board stringToBoard(String textblock) {
		String[] rows = textblock.split("\n");
		Board board = new Board(rows[0].length(), rows.length);
		range(0, rows.length).forEach(y -> {
			char[] charArray = rows[y].toCharArray();
			IntStream.range(0, charArray.length).filter(x -> charArray[x] == 'X')
					.forEach(x -> board.setAlive(x, y));
		});
		return board;

	}

}
