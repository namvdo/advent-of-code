package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author namvdo
 **/
public class Day11 {

	static final int ROUND_1 = 20;
	static final int ROUND_2 = 10000;
	public static void main(String[] args) {
		String s = Utils.get("./input/day11");
		System.out.println("Part 1: " + solve(s, ROUND_1));
		System.out.println("Part 2: " + solve(s, ROUND_2));
	}

	static final int TOTAL_MONKEYS = 8;

	public static long solve(String input, int rounds) {
		List<MonkeyInfo> monkeyInfos = parse(input);
		List<Long> monkeyToInspect = new ArrayList<>();
		for(int i = 0; i < TOTAL_MONKEYS; i++) {
			monkeyToInspect.add(0L);
		}
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		Map<Integer, MonkeyInfo> monkeyToItem = new HashMap<>();
		for (final MonkeyInfo monkeyInfo : monkeyInfos) {
			monkeyToItem.put(monkeyInfo.ithMonkey, monkeyInfo);
		}
		for(int i = 0; i < rounds; i++) {
			for (MonkeyInfo monkeyInfo : monkeyInfos) {
				int monkey = monkeyInfo.ithMonkey();
				Queue<Long> items = monkeyInfo.startingItems();
				BiFunction<Long, Long, Long> operation = monkeyInfo.operation();
				Predicate<Long> divisible = monkeyInfo.divisible();
				while (!items.isEmpty()) {
					long worryLevel = items.poll();
					monkeyToInspect.set(monkey, monkeyToInspect.get(monkey) + 1);
					if (monkeyInfo.operand == -1) {
						worryLevel = operation.apply(worryLevel, worryLevel);
					} else {
						worryLevel = operation.apply(worryLevel, (long) monkeyInfo.operand);
					}
					if (ROUND_1 == rounds) {
					    worryLevel /= 3;
					} else {
						worryLevel %= 9699690;
					}
					if (divisible.test(worryLevel)) {
						MonkeyInfo trueMonkey = monkeyToItem.get(monkeyInfo.trueMonkey);
						trueMonkey.addItem(worryLevel);
					} else {
						MonkeyInfo falseMonkey = monkeyToItem.get(monkeyInfo.falseMonkey);
						falseMonkey.addItem(worryLevel);
					}
				}
			}
		}
		monkeyToInspect.sort((o1, o2) -> -1 * o1.compareTo(o2));
		return monkeyToInspect.subList(0, 2).stream().reduce(1L, (a, b) -> (long) a * b);
	}


	public static List<MonkeyInfo> parse(String input) {
		List<MonkeyInfo> monkeyInfos = new ArrayList<>();
		List<String> monkeys = Arrays.stream(input.split("Monkey"))
				.collect(Collectors.toList());
		for(int i = 1; i < monkeys.size(); i++) {
			monkeys.set(i - 1, monkeys.get(i));
		}
		monkeys.remove(monkeys.size() - 1);
		for(String m : monkeys) {
			String[] p = m.split("\n");
			int ithMonkey = Integer.parseInt(Arrays
					.stream(p[0].split(":"))
					.map(String::trim)
					.toList()
					.get(0));
			String i = p[1].split("Starting items:")[1];
			Queue<Long> items = new ArrayDeque<>(Arrays.stream(i.split(","))
					.map(String::trim)
					.map(Long::parseLong).toList());
			String opt = p[2].split("=")[1].trim();
			Operation operation = parseOpt(opt);
			String[] div = p[3].split("divisible by");
			int divBy = Integer.parseInt(div[div.length - 1].trim());
			Predicate<Long> test = (x) -> x % divBy == 0;
			String[] c1 = p[4].split("\\s+");
			int trueMonkey = Integer.parseInt(c1[c1.length - 1].trim());
			String[] c2 = p[5].split("\\s+");
			int falseMonkey = Integer.parseInt(c2[c2.length - 1].trim());
			MonkeyInfo monkeyInfo = new MonkeyInfo(ithMonkey, items, operation.func, operation.operand, test, trueMonkey, falseMonkey);
			monkeyInfos.add(monkeyInfo);
		}
		return monkeyInfos;
	}

	
	static Operation parseOpt(String opt) {
		if (opt.contains("*")) {
			List<String> parts = Arrays.stream(opt.split("\\*"))
					.map(String::trim)
					.toList();
			if (parts.get(0).equals(parts.get(1))) {
				BiFunction<Long, Long, Long> func = (a, b) -> a * a;
				return new Operation(func, -1);
			} else {
				BiFunction<Long, Long, Long> func = (a, b) -> a * b;
				int operand = Integer.parseInt(parts.get(1));
				return new Operation(func, operand);
			}
		} else {
			List<String> parts = Arrays.stream(opt.split("\\+"))
					.map(String::trim)
					.toList();
			if (parts.get(0).equals(parts.get(1))) {
				BiFunction<Long, Long, Long> func = (a, b) -> a + a;
				return new Operation(func, -1);
			} else {
				int operand = Integer.parseInt(parts.get(1));
				return new Operation((a, b) -> a + operand, operand);
			}
		}
	}


	record MonkeyInfo(int ithMonkey,
	                  Queue<Long> startingItems,
	                  BiFunction<Long, Long, Long> operation,
					  int operand,
	                  Predicate<Long> divisible,
	                  int trueMonkey,
	                  int falseMonkey
	) {

		void clearItems() {
			this.startingItems.clear();
		}
		void addItem(long item) {
			this.startingItems.add(item);
		}

	}

	record Operation(BiFunction<Long, Long, Long> func, int operand) {
			
	}


}



