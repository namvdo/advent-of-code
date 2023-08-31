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

	public static void main(String[] args) {
		String s = Utils.get("./input/day11");
		System.out.println("Part 1: " + solvePart1(s));
	}

	static final int ROUNDS = 20;
	static final int TOTAL_MONKEYS = 8;

	public static int solvePart1(String input) {
		List<MonkeyInfo> monkeyInfos = parse(input);
		List<Integer> monkeyToInspect = new ArrayList<>();
		for(int i = 0; i < TOTAL_MONKEYS; i++) {
			monkeyToInspect.add(0);
		}
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		Map<Integer, MonkeyInfo> monkeyToItem = new HashMap<>();
		for (final MonkeyInfo monkeyInfo : monkeyInfos) {
			monkeyToItem.put(monkeyInfo.ithMonkey, monkeyInfo);
		}
		for(int i = 0; i < ROUNDS; i++) {
			for (MonkeyInfo monkeyInfo : monkeyInfos) {
				int monkey = monkeyInfo.ithMonkey();
				Queue<Integer> items = monkeyInfo.startingItems();
				BiFunction<Integer, Integer, Integer> operation = monkeyInfo.operation();
				Predicate<Integer> divisible = monkeyInfo.divisible();
				while (!items.isEmpty()) {
					int worryLevel = items.poll();
					monkeyToInspect.set(monkey, monkeyToInspect.get(monkey) + 1);
					if (monkeyInfo.operand == -1) {
						worryLevel = operation.apply(worryLevel, worryLevel);
					} else {
						worryLevel = operation.apply(worryLevel, monkeyInfo.operand);
					}
					worryLevel /= 3;
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
		return monkeyToInspect.subList(0, 2).stream().reduce(1, (a, b) -> a * b);
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
			Queue<Integer> items = new ArrayDeque<>(Arrays.stream(i.split(","))
					.map(String::trim)
					.map(Integer::parseInt).toList());
			String opt = p[2].split("=")[1].trim();
			Operation operation = parseOpt(opt);
			String[] div = p[3].split("divisible by");
			int divBy = Integer.parseInt(div[div.length - 1].trim());
			Predicate<Integer> test = (x) -> x % divBy == 0;
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
				BiFunction<Integer, Integer, Integer> func = (a, b) -> a * a;
				return new Operation(func, -1);
			} else {
				BiFunction<Integer, Integer, Integer> func = (a, b) -> a * b;
				int operand = Integer.parseInt(parts.get(1));
				return new Operation(func, operand);
			}
		} else {
			List<String> parts = Arrays.stream(opt.split("\\+"))
					.map(String::trim)
					.toList();
			if (parts.get(0).equals(parts.get(1))) {
				BiFunction<Integer, Integer, Integer> func = (a, b) -> a + a;
				return new Operation(func, -1);
			} else {
				int operand = Integer.parseInt(parts.get(1));
				return new Operation((a, b) -> a + operand, operand);
			}
		}
	}


	record MonkeyInfo(int ithMonkey,
	                  Queue<Integer> startingItems,
	                  BiFunction<Integer, Integer, Integer> operation,
					  int operand,
	                  Predicate<Integer> divisible,
	                  int trueMonkey,
	                  int falseMonkey
	) {

		void clearItems() {
			this.startingItems.clear();
		}
		int throwFirstItem() {
			return this.startingItems.poll();
		}
		void addItem(int item) {
			this.startingItems.add(item);
		}

	}

	record Operation(BiFunction<Integer, Integer, Integer> func, int operand) {
			
	}


}



