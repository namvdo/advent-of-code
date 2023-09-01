package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.*;

/**
 * @author namvdo
 **/
public class Day12 {

	public static void main(String[] args) {
		String input = Utils.get("./input/day12");
		int i = solvePart1(input);
		System.out.println("Part 1: " + i);
		int j = solvePart2(input);
		System.out.println("Part 2: " + j);
	}

	public static int solvePart2(String input) {
		char[][] grid = buildGrid(input);
		Set<Position> startPositions = getAllLowestElevations(grid);
		PriorityQueue<Integer> steps = new PriorityQueue<>();
		for(Position pos : startPositions) {
			PriorityQueue<StepPosition> stepPositions = new PriorityQueue<>();
			stepPositions.add(new StepPosition(0, pos.x, pos.y));
			boolean[][] visited = buildVisitedGrid(grid.length, grid[0].length);
			while (!stepPositions.isEmpty()) {
				StepPosition stepPosition = stepPositions.poll();
				int step = stepPosition.step;
				int i = stepPosition.i;
				int j = stepPosition.j;
				if (visited[i][j]) {
					continue;
				}
				visited[i][j] = true;
				if (grid[i][j] == 'E') {
					steps.add(step);
					break;
				}
				for(Position neighbor : getNeighbors(grid, i, j)) {
					stepPositions.add(new StepPosition(step + 1, neighbor.x, neighbor.y));
				}
			}
		}
		return steps.peek();
	}

	static Set<Position> getAllLowestElevations(char[][] grid) {
		Set<Position> positions = new HashSet<>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'a' || grid[i][j] == 'S') {
					positions.add(new Position(i, j));
				}
			}
		}
		return positions;
	}


	public static int solvePart1(String input) {
		char[][] grid = buildGrid(input);
		int m = grid.length;
		int n = grid[0].length;
		boolean[][] visited = buildVisitedGrid(m, n);
		Position start = getPosition(grid, 'S');
		Position end = getPosition(grid, 'E');
		PriorityQueue<StepPosition> queue = new PriorityQueue<>();
		queue.add(new StepPosition(0, start.x(), start.y()));
		while (!queue.isEmpty()) {
			StepPosition stepPosition = queue.poll();
			if (visited[stepPosition.i][stepPosition.j]) {
				continue;
			}
			visited[stepPosition.i][stepPosition.j] = true;
			if (stepPosition.i == end.x() && stepPosition.j == end.y) {
				return stepPosition.step;
			}
			for(Position neighbor : getNeighbors(grid, stepPosition.i, stepPosition.j)) {
				StepPosition nextPos = new StepPosition(stepPosition.step + 1, neighbor.x, neighbor.y);
				queue.add(nextPos);
			}
		}
		return -1;
	}

	private static boolean[][] buildVisitedGrid(int m, int n) {
		boolean[][] visited = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				visited[i][j] = false;
			}
		}
		return visited;
	}

	static List<Position> getNeighbors(char[][] grid, int i, int j) {
		char cur = grid[i][j];
		List<Position> positions = new ArrayList<>();
		for(final Move move : Move.MOVES) {
			int ii = i + move.i;
			int jj = j + move.j;
			if (ii < 0 || ii >= grid.length || jj < 0 || jj >= grid[i].length) {
				continue;
			}
			char neighbor = grid[ii][jj];
			if (getHeight(neighbor) <= getHeight(cur) + 1) {
				positions.add(new Position(ii, jj));
			}
		}
		return positions;
	}

	static Position getPosition(char[][] grid, char c) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == c) {
					return new Position(i, j);
				}
			}
		}
		return new Position(0, 0);
	}


	static int getHeight(char c) {
		if (c == 'S') {
			return 0;
		} else if (c == 'E') {
			return 25;
		}
		return c - 'a';
	}

	private static char[][] buildGrid(String input) {
		String[] lines = input.split("\n");
		int m = lines.length;
		int n = lines[0].length();
		char[][] grid = new char[m][n];
		for(int i = 0; i < m; i++) {
			String line = lines[i];
			for(int j = 0; j < n; j++) {
				grid[i][j] = line.charAt(j);
			}
		}
		return grid;
	}


	record StepPosition(int step, int i, int j) implements Comparable<StepPosition> {

		@Override
		public int compareTo(StepPosition o) {
			return Integer.compare(this.step, o.step);
		}
	}

	record Move(int i, int j) {
		static final Move MOVE_UP = new Move(-1, 0);
		static final Move MOVE_DOWN = new Move(1, 0);
		static final Move MOVE_LEFT = new Move(0, -1);
		static final Move MOVE_RIGHT = new Move(0, 1);

		static final List<Move> MOVES = List.of(MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT);
	}

	record Position(int x, int y) {

	}

}
