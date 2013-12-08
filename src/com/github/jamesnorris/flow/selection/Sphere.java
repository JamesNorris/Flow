package com.github.jamesnorris.flow.selection;

import java.util.ArrayList;

import org.bukkit.Location;

public class Sphere {// TODO annotations
    private Location center;
    private ArrayList<Location> locs = new ArrayList<Location>();
    private double x, y, z;
    private int radius;

    public Sphere(Location centerLocation, int radius) {
        center = centerLocation;
        x = centerLocation.getX();
        y = centerLocation.getY();
        z = centerLocation.getZ();
        this.radius = radius;
        recalculate();
    }

    protected void recalculate() {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    if (!isInside(x + i, y + j, z + k)) {
                        continue;
                    }
                    locs.add(new Location(center.getWorld(), x + i, y + j, z + k, 0, 0));
                }
            }
        }
    }

    public Location getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public ArrayList<Location> getLocations() {
        return locs;
    }

    protected boolean isInside(double x, double y, double z) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= radius;
    }

    public boolean overlaps(Sphere other) {
        for (Location loc : other.getLocations()) {
            if (contains(loc)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Location loc) {
        return isInside(x - loc.getX(), y - loc.getY(), z - loc.getZ());
    }
}
