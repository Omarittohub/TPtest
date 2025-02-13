package org.acme;

public class Car {
    private int x, y;
    private int fuel;
    private String direction;
    private static final int MAX_FUEL = 60;

    public Car() {
        this.x = 0;
        this.y = 0;
        this.fuel = MAX_FUEL;
        this.direction = "UP";
    }

    public void move(int steps) {
        if (fuel <= 0) {
            return;
        }

        for (int i = 0; i < steps; i++) {
            if (fuel > 0) {
                switch (direction) {
                    case "UP": y += 1; break;
                    case "DOWN": y -= 1; break;
                    case "LEFT": x -= 1; break;
                    case "RIGHT": x += 1; break;
                }
                if (i % 3 == 0) fuel--;
            }
        }
    }

    public void turn(String newDirection) {
        this.direction = newDirection;
    }

    public void refuel() {
        this.fuel = MAX_FUEL;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getFuel() { return fuel; }
    public String getDirection() { return direction; }
}
