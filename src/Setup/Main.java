package src.Setup;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import com.thor.tapplet.TApplet;
import src.MapGeneration.*;

public class Main extends TApplet {
    private static final long serialVersionUID = 1L;
    public static ArrayList<Road> roads = new ArrayList<Road>();
    public static ArrayList<RoadIntersection> intersections = new ArrayList<RoadIntersection>();
    public static float camX, camY;
    public static float scale = 1;

    public static void main(String[] args) {
        new Main().init(1920, 1080, "mapGen");
    }

    @Override
    public void setup() {
        Mapgenerator.generateMap();
    }

    @Override
    public void draw() {
        int transX = (int) (-camX + WIDTH / 2f / scale);
        int transY = (int) (-camY + HEIGHT / 2f / scale);
        g.scale(scale, scale);
        g.translate(transX, transY);
        g.setColor(Color.WHITE);
        g.fillRect(0 - 10, 0 - 10, 20, 20);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        for (Road v : roads) {
            v.drawVej(g);
        }
        g.setColor(Color.red);
        for (RoadIntersection rI : intersections) {
            g.fillOval(rI.x - 5, rI.y - 5, 10, 10);
        }

        // REVERSE TRANSLATION TO DRAW UI
        g.translate(-transX, -transY);
        g.scale(1 / scale, 1 / scale);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Use WASD to move", 10, 50);
    }

    @Override
    public void tick() {
        if (keyboard.keyDown('w')) {
            camY -= Settings.camSpeed * 1 / scale;
        }
        if (keyboard.keyDown('s')) {
            camY += Settings.camSpeed * 1 / scale;
        }
        if (keyboard.keyDown('d')) {
            camX += Settings.camSpeed * 1 / scale;
        }
        if (keyboard.keyDown('a')) {
            camX -= Settings.camSpeed * 1 / scale;
        }
        if (!Settings.createAllAtStart && roads.size() < Settings.roadAmount)
            Mapgenerator.updateRoads();
        scale -= mouse.scroll / 200;
        if (scale < Settings.minScale) {
            scale = Settings.minScale;
        }
    }

}