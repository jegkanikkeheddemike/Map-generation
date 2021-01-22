import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Vej {
    int startX, startY, slutX, slutY;
    ArrayList<Vej> connected = new ArrayList<Vej>();
    int orientation; // 0 = east, 1 = south, 2 = west, 3 = north
    int length;

    Vej(int startX, int startY, int orientation, int length) {
        this.startX = startX;
        this.startY = startY;
        this.orientation = orientation;
        this.length = length;
        switch (orientation) {
            case 0:
                slutX = startX + length;
                slutY = startY;
                break;
            case 1:
                slutX = startX;
                slutY = startY + length;
                break;
            case 2:
                slutX = startX - length;
                slutY = startY;
                break;
            case 3:
                slutX = startX;
                slutY = startY - length;
                break;
        }
    }

    public void drawVej(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(startX, startY, slutX, slutY);
        g.drawString(connected.size() + "", slutX, slutY);
        g.setColor(Color.red);
    }

    public void makeRoad() {
        int tries = 0;
        boolean canBePlacedHere = false;
        int newX = 0, newY = 0, newSX = 0, newSY = 0;
        int where = new Random().nextInt(length);
        int newLength = Settings.minRoadLength + new Random().nextInt(Settings.maxRoadLength - Settings.minRoadLength);
        boolean side = new Random().nextBoolean();

        int newOrientation = orientation;
        if (side) {
            newOrientation += 1;
            if (newOrientation == 4) {
                newOrientation = 0;
            }
        } else {
            newOrientation -= 1;
            if (newOrientation == -1) {
                newOrientation = 3;
            }
        }
        while (!canBePlacedHere && tries < 10) {
            where = new Random().nextInt(length);
            switch (orientation) { // get StartPoint
                case 0:
                    newX = startX + where;
                    newY = startY;
                    break;
                case 1:
                    newX = startX;
                    newY = startY + where;
                    break;
                case 2:
                    newX = startX - where;
                    newY = startY;
                    break;
                case 3:
                    newX = startX;
                    newY = startY - where;
                    break;
            }
            switch (newOrientation) { // get endPoint
                case 0:
                    newSX = newX + newLength;
                    newSY = newY;
                    break;
                case 1:
                    newSX = newX;
                    newSY = newY + newLength;
                    break;
                case 2:
                    newSX = newX - newLength;
                    newSY = newY;
                    break;
                case 3:
                    newSX = newX;
                    newSY = newY - newLength;
                    break;
            }

            canBePlacedHere = true;

            // Check new intersections
            ArrayList<roadIntersection> newIntersections = new ArrayList<roadIntersection>();
            for (Vej v : Main.roads) {
                if (v == this) {
                    continue;
                }
                roadIntersection check = roadCollision(newX, newY, newSX, newSY, v.startX, v.startY, v.slutX, v.slutY);
                if (check != null) {
                    newIntersections.add(check);
                }
            }

            for (roadIntersection rI : Main.intersections) {
                float pDist = GMath.pointDist(newX, newY, rI.x, rI.y); // Check existing intersections
                if (pDist < Settings.minRoadDist) {
                    canBePlacedHere = false;
                    break;
                }

                for (roadIntersection newRI : newIntersections) {// Check new intersections with existing intersections
                    float newPDist = GMath.pointDist(newRI.x, newRI.y, rI.x, rI.y);
                    if (newPDist < Settings.minRoadDist) {
                        canBePlacedHere = false;
                        break; // break inner loop
                    }
                }
                if (!canBePlacedHere) { // break outer loop
                    break;
                }

            }
            for (roadIntersection rI : newIntersections) { // Check new intersections with new intersections

                for (roadIntersection newRI : newIntersections) {
                    if (rI == newRI)
                        continue;

                    float newPDist = GMath.pointDist(newRI.x, newRI.y, rI.x, rI.y);
                    if (newPDist < Settings.minRoadDist) {
                        canBePlacedHere = false;
                        break; // break inner loop
                    }
                }
                if (!canBePlacedHere) { // break outer loop
                    break;
                }
            }

            tries++;
        }
        if (canBePlacedHere) {
            Vej newVej = new Vej(newX, newY, newOrientation, newLength);
            Main.roads.add(newVej);
        }
    }

    void updateConnections() {
        for (Vej v : Main.roads) {
            if (v == this) {
                continue;
            }
            roadIntersection rI = roadCollision(startX, startY, slutX, slutY, v.startX, v.startY, v.slutX, v.slutY);
            if (rI != null) {
                if (!connected.contains(v)) {
                    connected.add(v);
                    boolean iExists = false; // Check if intersection exists
                    for (roadIntersection rI2 : Main.intersections) {
                        if (rI2.x == rI.x && rI2.y == rI.y) {
                            iExists = true;
                            break;
                        }
                    }
                    if (!iExists) {
                        Main.intersections.add(rI);
                    }
                }
            }
        }
    }

    roadIntersection roadCollision(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // calculate the direction of the lines
        float den = ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
        if (den == 0.) {
            return null;
        }

        float uA = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / den;
        float uB = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / den;
        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0. && uA <= 1. && uB >= 0. && uB <= 1.) {
            int intersectionX = (int) (x1 + (uA * (x2 - x1)));
            int intersectionY = (int) (y1 + (uA * (y2 - y1)));
            // System.out.println(intersectionX + " " + intersectionY);
            return new roadIntersection(intersectionX, intersectionY);
        }
        return null;
    }

}

class roadIntersection {
    public int x, y;

    roadIntersection(int x, int y) {
        this.x = x;
        this.y = y;
    }
}