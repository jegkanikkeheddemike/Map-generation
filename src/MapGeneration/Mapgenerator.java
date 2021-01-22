package src.MapGeneration;

import java.util.ArrayList;
import java.util.Random;

import src.Setup.*;

public class Mapgenerator {
    public static void generateMap() {
        int centerX = 0;
        int centerY = 0;
        Main.roads.add(new Road(centerX, centerY, 0, Settings.startRoadLength)); // east
        Main.roads.add(new Road(centerX, centerY, 1, Settings.startRoadLength)); // south
        Main.roads.add(new Road(centerX, centerY, 2, Settings.startRoadLength)); // west
        Main.roads.add(new Road(centerX, centerY, 3, Settings.startRoadLength)); // north

        while (Main.roads.size() < Settings.roadAmount) {
            updateRoads();
        }
        System.out.println("Made " + Main.roads.size() + " roads");
    }

    private static void updateRoads() {
        // Sandsynlighed for vej er baseret på længde, forbindelser og afstand til start
        ArrayList<Road> pool = new ArrayList<Road>();
        for (Road v : Main.roads) {
            for (int i = 0; i < v.length - v.connected.size() * 50; i += 50) {
                pool.add(v);
            }
        }

        Road update1 = pool.get(new Random().nextInt(pool.size()));
        update1.makeRoad();

        for (Road v : Main.roads) {
            v.updateConnections();
        }
    }
}
