package gol;

import java.util.HashSet;
import java.util.Set;

import gol.GoLTest.Point;

public class Board {

	private Set<Point> lifeCells = new HashSet<Point>();
	private int width;
	private int height;

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