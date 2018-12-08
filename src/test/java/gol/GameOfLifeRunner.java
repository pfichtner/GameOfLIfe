package gol;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameOfLifeRunner {

	public static void main(String[] args) throws InterruptedException {
		Board board = init(new Board(120, 40));
		while (true) {
			System.out.print("\033[H\033[2J");
			range(0, board.getHeight()).forEach(y -> System.out.println(
					range(0, board.getWidth()).mapToObj(x -> board.isAlive(x, y) ? "*" : " ").collect(joining())));
			TimeUnit.MILLISECONDS.sleep(100);
			board.tick();
		}
	}

	private static Board init(Board board) {
		Random random = new Random(System.currentTimeMillis());
		range(0, board.getHeight()).forEach(y -> {
			range(0, board.getWidth()).forEach(x -> {
				if (random.nextInt(100) > 90)
					board.setAlive(x, y);
			});
		});
		return board;
	}

}
