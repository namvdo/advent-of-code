package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @author namvdo
 **/
public class Day8 {
	public int solvePart1(String input) {
		String[] lines = input.split("\n");
		int edgedTrees = countTreeOnEdge(lines);
		int innerTrees = countInnerVisibleTree(lines);
		return edgedTrees  + innerTrees;
	}


	public static void main(String[] args) throws ExecutionException, InterruptedException {
		Day8 day8 = new Day8();
		int c = day8.solvePart1(Utils.get("./input/day8"));
		System.out.println("total: " + c);
	}


	private int countInnerVisibleTree(String[] lines) {
		int trees = 0;
		int[][] grid = getTreeGrid(lines);
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if (i == 0 || i == grid.length - 1 || j == 0 || j == grid[i].length - 1) {
					continue;
				}
				if (isVisible(i, j, grid)) {
					System.out.println("val: " + grid[i][j] + " pos i: " + i + ", j: " + j);
					trees++;
				}
			}
		}
		System.out.println("total trees: " + trees);
		return trees;
	}

	private boolean isVisible(int i, int j, int[][] grid) {
		int val = grid[i][j];
		boolean[] isVisible = new boolean[4];
		Arrays.fill(isVisible, true);
		for(int k = 0; k < i; k++) {
			if (grid[k][j] >= val) {
				isVisible[0] = false;
				break;
			}
		}
		for(int k = i + 1; k < grid.length; k++) {
			if (grid[k][j] >= val) {
				isVisible[1] = false;
				break;
			}
		}
		for(int k = 0; k < j; k++) {
			if (grid[i][k] >= val) {
				isVisible[2] = false;
				break;
			}
		}
		for (int k = j + 1; k < grid[i].length; k++) {
			if (grid[i][k] >= val) {
				isVisible[3] = false;
				break;
			}
		}
		System.out.println(Arrays.toString(isVisible));
		for(boolean visible : isVisible) {
			if (visible) return true;
		}
		return false;
	}


	private int[][] getTreeGrid(String[] lines) {
		List<int[]> grid = new ArrayList<>();
		for(String line : lines) {
			int[] trees = Arrays
					.stream(line.split(""))
					.mapToInt(Integer::parseInt)
					.toArray();
			grid.add(trees);
		}
		return grid.toArray(int[][]::new);
	}
	private int countTreeOnEdge(String[] lines) {
		Set<TreePosition> pos = new HashSet<>();
		List<Integer> topTrees = Arrays
				.stream(lines[0].split(""))
				.map(Integer::parseInt).toList();
		List<Integer> bottomTrees = Arrays
				.stream(lines[lines.length - 1].split(""))
				.map(Integer::parseInt).toList();
		int i = 0;
		for(final int t : topTrees) {
			TreePosition treePosition = new TreePosition(0, i, t);
			pos.add(treePosition);
			i++;
		}
		i = 0;
		for(final int t : bottomTrees) {
			TreePosition treePosition = new TreePosition(lines.length - 1, i, t);
			pos.add(treePosition);
			i++;
		}
		pos.addAll(getBorderTree(lines));
		return pos.size();
	}

	Set<TreePosition> getBorderTree(String[] lines) {
		Set<TreePosition> pos = new HashSet<>();
		for(int i = 0; i < lines.length; i++) {
			int[] trees = Arrays
					.stream(lines[i].split(""))
					.mapToInt(Integer::parseInt)
					.toArray();
			TreePosition first = new TreePosition(i, 0, trees[0]);
			TreePosition last = new TreePosition(i, trees.length - 1, trees[trees.length - 1]);
			pos.add(first);
			pos.add(last);
		}
		return pos;
	}

	record TreePosition(int x, int y, int val) {

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			TreePosition that = (TreePosition) o;
			return x == that.x && y == that.y && val == that.val;
		}

		@Override
		public int hashCode() {
			return Objects.hash(x, y, val);
		}
	}
}
