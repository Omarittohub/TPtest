package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
import jakarta.ws.rs.sse.OutboundSseEvent;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@Path("/game")
public class CarSimulationResource {

    private final Car car = new Car();
    private final SimulationEngine engine = new SimulationEngine(car);

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String status() {
        return "Car position: (" + car.getX() + ", " + car.getY() + ")\n" +
                "Direction: " + car.getDirection() + "\n" +
                "Fuel level: " + car.getFuel() + "L";
    }

    @POST
    @Path("/move")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String move(@FormParam("direction") String direction, @FormParam("steps") int steps) {
        if (direction == null || steps <= 0) {
            return "Invalid input";
        }

        car.turn(direction);
        car.move(steps);
        engine.checkFuelStation();
        return status();
    }

    @POST
    @Path("/refuel")
    @Produces(MediaType.TEXT_PLAIN)
    public String refuel() {
        car.refuel();
        return "Car refueled!\n" + status();
    }
}


class SimulationEngine {
    private int[][] fuelStations;
    private static final int GRID_SIZE = 10;
    private Car car;

    public SimulationEngine(Car car) {
        this.fuelStations = new int[GRID_SIZE][GRID_SIZE];
        placeFuelStations();
    }

    private void placeFuelStations() {
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            int x = rand.nextInt(GRID_SIZE);
            int y = rand.nextInt(GRID_SIZE);
            fuelStations[x][y] = 1;
        }
    }

    public void checkFuelStation() {
        if (fuelStations[car.getX()][car.getY()] == 1) {
            car.refuel();
        }
    }
}
