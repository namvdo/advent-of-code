package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author namvdo
 */
@Slf4j
public class Day5 {

	@AllArgsConstructor
	@ToString
	static class Operation {
		int moves;
		int from;
		int to;
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

	static Map<Integer, Stack<Character>> getCrates(String input) {
		Map<Integer, Stack<Character>> bundle = new HashMap<>();
		List<List<String>> lines = input
				.lines()
				.limit(8)
				.map(x -> Arrays.stream(x.split("\\s+")).collect(Collectors.toList()))
				.collect(Collectors.toList());
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
		List<String> lines = input.lines().skip(10).collect(Collectors.toList());
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
		log.info(solvePart1(Utils.getResourceAsString("2022/day5")));
	}


}
