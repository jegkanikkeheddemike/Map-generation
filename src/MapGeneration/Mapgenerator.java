package src.MapGeneration;

import java.util.ArrayList;
import java.util.Random;

import src.Setup.*;

public class Mapgenerator {
    public static void generateMap(){
        int centerX = 0;
        int centerY = 0;
        Main.roads.add(new Road(centerX, centerY, 0, Settings.startRoadLength)); // east
        Main.roads.add(new Road(centerX, centerY, 1, Settings.startRoadLength)); // south
        Main.roads.add(new Road(centerX, centerY, 2, Settings.startRoadLength)); // west
        Main.roads.add(new Road(centerX, centerY, 3, Settings.startRoadLength)); // north

        for (int i = 0; i < Settings.roadAmount; i++) {
            updateRoads();
        }
    }
    private static void updateRoads() {
        ArrayList<Road> pool = new ArrayList<Road>(); // Sandsynlighed for vej er baseret på længde og forbindelser
        for (Road v : Main.roads) {
            for (int i = 0; i < v.length - v.connected.size() * 10; i += 50) {
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
