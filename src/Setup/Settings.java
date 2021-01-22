package src.Setup;

public class Settings {
    // Road properties
    public static int minRoadLength = 50;
    public static int maxRoadLength = 300;
    public static int startRoadLength = 500;
    public static int minIntersectionDist = 60;
    public static int roadAmount = 100;
    public static int parallelMinDist = 30;
    public static int parallelCheckDist = 200;

    // Choose road properties
    public static int lengthMultiplier = 2;
    public static int connectedMultiplier = 20;
    public static int distanceFromSpawnMultiplier = 30;

    // Setup
    public static boolean createAllAtStart = false;

    // Camera
    public static float camSpeed = 5;
}
