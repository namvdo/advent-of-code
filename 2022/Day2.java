package com.learntocodetogether.twentytwo;

import com.learntocodetogether.utils.Pair;
import com.learntocodetogether.utils.Utils;

/**
 * @author namvdo
 */
public class Day2 {
	enum RPS {
		ROCK(1, "A", "X"),
		PAPER(2, "B", "Y"),
		SCISSOR(3, "C", "Z");
		RPS(int score, String enemy, String yourChoice)	 {
			this.score = score;
			this.enemy = enemy;
			this.yourChoice = yourChoice;
		}
		final int score;
		final String enemy;
		final String yourChoice;

		public static RPS getEnemy(String val) {
			for(final var s : RPS.values()) {
				if (s.enemy.equals(val)) return s;
			}
			return null;
		}

		public static RPS getYou(String val) {
			for(final var s : RPS.values()) {
				if (s.yourChoice.equals(val)) return s;
			}
			return null;
		}
	}

	enum Tournament {
		LOST(0),
		DRAW(3),
		WIN(6);
		final int score;
		Tournament(int score) {
			this.score = score;
		}
	}


	static Tournament getTournamentResult(RPS you, RPS enemy) {
		if (enemy == you) return Tournament.DRAW;
		if (RPS.PAPER == you && RPS.ROCK == enemy
				|| RPS.SCISSOR == you && RPS.PAPER == enemy
				|| RPS.ROCK == you && RPS.SCISSOR == enemy
		) {
			return Tournament.WIN;
		}
		return Tournament.LOST;
	}

	static int getScore(String line) {
		RPS enemy = getPair(line).first();
		RPS you = getPair(line).second();
		if (enemy == null || you == null) return 0;
		return getTournamentResult(enemy, you).score + you.score;
	}

	static Pair<RPS, RPS> getPair(String line) {
		String []input = line.split("\\s+");
		return Pair.create(RPS.getEnemy(input[0]), RPS.getYou(input[1]));
	}

	public static int solvePart1(String input) {
		String[] lines = input.split("\n");
		int sum = 0;
		for(final var l : lines) {
			sum += getScore(l);
		}
		return sum;
	}

	public static int solvePart2(String input) {
		String[] inputs = input.split("\n");
		int sum = 0;
		for(final var line : inputs) {
			Pair<RPS, RPS> pair = getPair(line);
			RPS enemy = pair.first();
			RPS wantedResult = pair.second();
			RPS yourChoice = getFromWantedResult(enemy, wantedResult.yourChoice);
			Tournament tournamentResult = getTournamentResult(yourChoice, enemy);
			sum += (tournamentResult.score + yourChoice.score);
		}
		return sum;
	}

	static RPS getWin(RPS opponent) {
		if (RPS.ROCK == opponent) {
			return RPS.PAPER;
		} else if (RPS.PAPER == opponent) {
			return RPS.SCISSOR;
		}
		return RPS.ROCK;
	}

	static RPS getLose(RPS opponent) {
		if (RPS.ROCK == opponent) {
			return RPS.SCISSOR;
		} else if (RPS.SCISSOR == opponent) {
			return RPS.PAPER;
		}
		return RPS.ROCK;
	}


	public static void main(String[] args) {
		String input = Utils.getResourceAsString("2022/day2/part1");
		System.out.println(solvePart1(input));
		System.out.println(solvePart2(input));
	}


	static RPS getFromWantedResult(RPS enemy, String wantedResult) {
		if ("X".equals(wantedResult)) {
			return getLose(enemy);
		} else if ("Y".equals(wantedResult)) {
			return enemy;
		}
		return getWin(enemy);
	}


}
