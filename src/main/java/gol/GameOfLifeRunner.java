package gol;

import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import gol.Board.Coordinate;

public class GameOfLifeRunner {

	public static void main(String[] args) throws InterruptedException {
		Board board = init(new Board(120, 40));
		while (true) {
			dumpBoard(board);
			TimeUnit.MILLISECONDS.sleep(100);
			board.tick();
		}
	}

	private static void dumpBoard(Board board) {
		System.out.print("\033[H\033[2J");
		range(0, board.height).forEach(y -> System.out
				.println(range(0, board.width).mapToObj(x -> board.isAlive(new Coordinate(x, y)) ? "*" : " ").collect(joining())));
	}

	private static Board init(Board board) {
		Random random = new Random(System.currentTimeMillis());
		range(0, board.height).forEach(y -> {
			range(0, board.width).forEach(x -> {
				if (random.nextInt(100) > 90)
					board.setAlive(new Coordinate(x, y));
			});
		});
		return board;
	}

}
