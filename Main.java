import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import com.thor.tapplet.TApplet;

public class Main extends TApplet {
    private static final long serialVersionUID = 1L;
    public static ArrayList<Road> roads = new ArrayList<Road>();
    public static ArrayList<roadIntersection> intersections = new ArrayList<roadIntersection>();

    public static void main(String[] args) {
        new Main().init(1920, 1080, "mapGen");
    }

    @Override
    public void setup() {
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        roads.add(new Road(centerX + 10, centerY, 0, Settings.startRoadLength)); // east
        roads.add(new Road(centerX, centerY + 10, 1, Settings.startRoadLength)); // south
        roads.add(new Road(centerX - 10, centerY, 2, Settings.startRoadLength)); // west
        roads.add(new Road(centerX, centerY - 10, 3, Settings.startRoadLength)); // north

        for (int i = 0; i < Settings.roadAmount; i++) {
            updateRoads();
        }
    }

    @Override
    public void draw() {
        g.setColor(Color.WHITE);
        g.fillRect(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        for (Road v : roads) {
            v.drawVej(g);
        }
        g.setColor(Color.red);
        for (roadIntersection rI : intersections) {
            g.fillOval(rI.x - 5, rI.y - 5, 10, 10);
        }
    }

    private void updateRoads() {
        ArrayList<Road> pool = new ArrayList<Road>(); // Sandsynlighed for vej er baseret på længde og forbindelser
        for (Road v : roads) {
            for (int i = 0; i < v.length - v.connected.size() * 10; i += 50) {
                pool.add(v);
            }
        }

        Road update1 = pool.get(new Random().nextInt(pool.size()));
        update1.makeRoad();

        for (Road v : roads) {
            v.updateConnections();
        }
    }

    @Override
    public void tick() {

    }

}