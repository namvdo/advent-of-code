package day3;
// most common bit - gramma

// least common bit - epsilon 
// convert to decimal 
// multiple the result

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    static String getMostCommonBits(Pair[] frequencyPairs) {
        var sb = new StringBuilder();
        for (final var pair : frequencyPairs) {
            sb.append(Math.max(pair.zeroCount, pair.oneCount));
        }
        return sb.toString();
    }

    static String getLeastCommonBits(Pair[] frequencyPairs) {
        String mcb = getMostCommonBits(frequencyPairs);
        var sb = new StringBuilder();
        for (final var c : mcb.toCharArray()) {
            if (c == '0') {
                sb.append('1');
            } else {
                sb.append('0');
            }
        }
        return sb.toString();
    }

    static void updatePairFrequency(Pair[] zeroOneFrequency, int index, String line) {
        if (index < 0 || index >= zeroOneFrequency.length) return;
        char[] chars = line.toCharArray();
        zeroOneFrequency[index] = new Pair(0, 0);
        for (final var c : chars) {
            if (c == '0')
                zeroOneFrequency[index].zeroCount++;
            else
                zeroOneFrequency[index].oneCount++;
        }
    }

    static int base2ToTen(String binary) {
        int rs = 0;
        char[] chars = binary.toCharArray();
        int power = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            int bin = Integer.parseInt(chars[i] + "");
            rs += Math.pow(bin, power++);
        }
        return rs;
    }

    public static void main(String[] args) throws IOException {
        String line;
        int idx = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/apple/Desktop/git/advent-of-code-2021/day3/input.txt")))) {
            List<String> lines = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            int len = lines.get(0).length();
            Pair[] pairs = new Pair[len];
            for (final var l : lines) {
                updatePairFrequency(pairs, idx++, l);
            }
            String mcb = getMostCommonBits(pairs);
            String lcb = getLeastCommonBits(pairs);
            int gamma = base2ToTen(mcb);
            int epsilon = base2ToTen(lcb);
            int rs = gamma * epsilon;
            System.out.println("Final result: " + rs);
        }
    }

}

final class Pair {
    int zeroCount;
    int oneCount;

    public Pair(int zeroCount, int oneCount) {
        this.zeroCount = zeroCount;
        this.oneCount = oneCount;
    }
}