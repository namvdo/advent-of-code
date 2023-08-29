package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author namvdo
 **/
public class Day10 {
	static final int[] cycles = {
			20,
			60,
			100,
			140,
			180,
			220
	};

	public static void main(String[] args) {
		String input = Utils.get("./input/day10");
		System.out.println("Part 1: " + solve(input));
	}

	public static int solve(String input) {
		List<Instruction> instructions = parse(input);
		int th = 0;
		int cycle = 0;
		int val = 1;
		int sum = 0;
		for(Instruction in : instructions) {
			if (in.opt() == Opt.NO_OPT) {
				cycle += 1;
				if (cycles[th] == cycle) {
					sum += cycles[th] * val;
					th++;
				}
			} else {
				cycle += 1;
				if (cycles[th] == cycle) {
					sum += cycles[th] * val;
					th++;
				}
				cycle += 1;
				if (cycles[th] == cycle) {
					sum += cycles[th] * val;
					th++;
				}
				val += in.val();

			}
			if (th >= cycles.length) break;
		}
		return sum;
	}


	static List<Instruction> parse(String input) {
		List<Instruction> instructions = new ArrayList<>();
		for(String line : input.split("\n")) {
			String[] parts = line.split("\\s+");
			Instruction instruction;
			if (parts[0].equals("addx")) {
				int val = Integer.parseInt(parts[1]);
				instruction = new Instruction(Opt.ADD_X, val);
			} else {
				instruction = new Instruction(Opt.NO_OPT, 0);
			}
			instructions.add(instruction);
		}
		return instructions;
	}
}

enum Opt {
	NO_OPT,
	ADD_X
}


record Instruction(Opt opt, int val) {

}
