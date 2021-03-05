package com.olliem5.ferox.api.waypoint;

import java.util.ArrayList;

/**
 * @author olliem5
 */

public final class WaypointManager {
    private static ArrayList<Waypoint> waypoints;

    public static void init() {
        waypoints = new ArrayList<>();
    }

    public static void addWaypoint(Waypoint waypoint) {
        waypoints.add(waypoint);
    }

    public static void delWaypoint(Waypoint waypoint) {
        waypoints.remove(waypoint);
    }

    public static ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }
}
