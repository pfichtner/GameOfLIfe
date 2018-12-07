package gol;

import java.util.Set;

import gol.GoLTest.Point;

public class Board {
	private Set<Point> lifeCells;
	private int width;
	private int height;

	public Board(Set<Point> lifeCells) {
		this.lifeCells = lifeCells;
	}

	public Set<Point> getLifeCells() {
		return lifeCells;
	}

	public void setLifeCells(Set<Point> lifeCells) {
		this.lifeCells = lifeCells;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}