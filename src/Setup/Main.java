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

    public static void main(String[] args) {
        new Main().init(1920, 1080, "mapGen");
    }

    @Override
    public void setup() {
        Mapgenerator.generateMap();
    }

    @Override
    public void draw() {
        int transX = (int) -camX + WIDTH / 2;
        int transY = (int) -camY + HEIGHT / 2;

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
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString("Use WASD to move", 10, 50);
    }

    @Override
    public void tick() {
        if (keyboard.keyDown('w')) {
            camY -= Settings.camSpeed;
        }
        if (keyboard.keyDown('s')) {
            camY += Settings.camSpeed;
        }
        if (keyboard.keyDown('d')) {
            camX += Settings.camSpeed;
        }
        if (keyboard.keyDown('a')) {
            camX -= Settings.camSpeed;
        }
        if (!Settings.createAllAtStart && roads.size() < Settings.roadAmount)
            Mapgenerator.updateRoads();
    }

}