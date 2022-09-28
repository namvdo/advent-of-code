package day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2 {
    public static void main(String[] args) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            Submarine submarine = new Submarine(0, 0, 0);
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] operation = line.split(" ");
                String command = operation[0];
                int val = Integer.parseInt(operation[1]);
                submarine.move(command, val);
            }
            System.out.println("Final result: " + submarine.getResult());
        }
    }

}

class Submarine {
    private long x;
    private long y;
    private long z;

    public Submarine(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void move(String command, int val) {
        switch (command) {
            case "forward" -> {
                x = Command.FORWARD.calculate(x, val);
                y = Command.DOWN.calculate(y, (z * val));
            }
            case "up" -> {
                z = Command.AIM_UP.calculate(z, val);
            }

            case "down" -> {
                z = Command.AIM_DOWN.calculate(z, val);
            }
        }
    }

    public long getResult() {
        return Command.MULTIPLY.calculate(x, y);
    }
}

enum Command {
    FORWARD, UP, DOWN, MULTIPLY, AIM_FORWARD_X, AIM_FORWARD_Y, AIM_UP, AIM_DOWN;

    public long calculate(long x, long y) {
        return switch (this) {
            case FORWARD, AIM_FORWARD_X, DOWN, AIM_DOWN -> x + y;
            case UP, AIM_UP -> x - y;
            case MULTIPLY, AIM_FORWARD_Y -> x * y;
        };
    }
}
