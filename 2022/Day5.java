package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author namvdo
 */
public class Day5 {

	static class Operation {
		int moves;
		int from;
		int to;

		public Operation(int moves, int from, int to) {
			this.moves = moves;
			this.from = from;
			this.to = to;
		}

	}



	static String solvePart1(String input) {
		Map<Integer, Stack<Character>> crates = getCrates(input);
		List<Operation> operations = getOperations(input);
		for(final var op : operations) {
			for(int i = 0; i < op.moves; i++) {
				Stack<Character> fromStack = crates.get(op.from);
				Character label = fromStack.pop();
				Stack<Character> toStack = crates.get(op.to);
				toStack.push(label);
			}
		}
		StringBuilder sb = new StringBuilder();
		for(final var stack : crates.values()) {
			sb.append(stack.pop());
		}
		return sb.toString();
	}

    static String solvePart2(String input) {
        Map<Integer, List<Character>> crates = getCrates(input).entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> new ArrayList<>(x.getValue())));
        var operations = getOperations(input);
        for(final var op : operations) {
            List<Character> from = crates.get(op.from);
            int left = Math.max(0, from.size() - op.moves);
            int right = Math.max(left + op.moves, from.size());
            List<Character> moves = from.subList(left, right);
            List<Character> stay = from.subList(0, Math.max(from.size() - op.moves, 0));
            List<Character> to = crates.get(op.to);
            to.addAll(moves);
            crates.put(op.from, stay);
        }
        StringBuilder sb = new StringBuilder();
        for(final var crate : crates.values()) {
            if (!crate.isEmpty()) {
                sb.append(crate.get(crate.size() - 1));
            }
        }
        return sb.toString();
    }

	static Map<Integer, Stack<Character>> getCrates(String input) {
		Map<Integer, Stack<Character>> bundle = new HashMap<>();
		List<List<String>> lines = input
                .lines()
                .limit(8)
                .map(x -> Arrays.stream(x.split("\\s+")).toList())
                .toList();
		for(int i = lines.size() - 1; i >= 0; i--) {
			List<String> crates = lines.get(i);
			for(int j = 0; j < crates.size(); j++) {
				Character label = getLabel(crates.get(j));
				if (!label.equals('\0')) {
					if (bundle.get(j + 1) == null) {
						Stack<Character> stack = new Stack<>();
						stack.push(label);
						bundle.put(j + 1, stack);
					} else {
						Stack<Character> stack = bundle.get(j + 1);
						stack.push(label);
					}
				}
			}
		}
		return bundle;
	}

	static List<Operation> getOperations(String input) {
		List<String> lines = input.lines().skip(10).toList();
		List<Operation> operations = new ArrayList<>();
		List<Integer> nums = new ArrayList<>();
		for(final var line : lines) {
			String[] in = line.split("\\s+");
			for(final var i : in) {
				char ch = i.charAt(0);
				if (Character.isDigit(ch)) {
					nums.add(Integer.parseInt(i));
				}
			}
			operations.add(new Operation(nums.get(0), nums.get(1), nums.get(2)));
			nums.clear();
		}
		return operations;
	}

	static Character getLabel(String crate) {
		String[] splits = crate.split("");
		for(final var s : splits) {
			if (Character.isLetter(s.charAt(0))) {
				return s.charAt(0);
			}
		}
		return '\0';
	}

	public static void main(String[] args) {
		System.out.println(solvePart1(Utils.getResourceAsString("2022/day5")));
        System.out.println(solvePart2(Utils.getResourceAsString("2022/day5")));
	}


}
