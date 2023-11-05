package gol;

public class BoardView {

	private final int width;
	private final int height;
	private final Board board;

	public BoardView(int width, int height, Board board) {
		this.width = width;
		this.height = height;
		this.board = board;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setAlive(int x, int y) {
		board.setAlive(x, y);
	}

	public boolean isAlive(int x, int y) {
		return board.isAlive(x, y);
	}

	public void tick() {
		this.board.tick();
	}

}
