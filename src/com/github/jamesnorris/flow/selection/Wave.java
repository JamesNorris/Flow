package com.github.jamesnorris.flow.selection;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

//@formatter:off
/*
 * As always, my famous ASCII art:
 * 
 * O = origin
 * X = x-axis
 * Y = y-axis
 * Z = z-axis
 * C = center
 * 
 *    ---------------------
 *   /                   /|
 *  /                   / |
 * Y--------------------  |
 * |                90 |  |     0 yaw
 * |   ^               |  |    /
 * |   |               |  |
 * |   |               |  |  /
 * | HEIGHT    C       |  |
 * |   |               |  |/
 * |   |               |  Z
 * |   v               | /
 * |   <---WIDTH--->   |/<---LENGTH
 * O-------------------X - - - - - - - - -  270 yaw
 */
//@formatter:on
public class Wave {
    private Location origin;
    private double length, width, height;
    private float yaw;
    private Wave2D /* xyWave, */zyWave;

    public static float normalizeYaw(float notchYaw) {
        notchYaw *= -1;
        if (notchYaw < 0) {
            for (int i = (int) Math.round(notchYaw / -360); i >= 0; i--) {
                notchYaw += 360;;
            }
        }
        return notchYaw % 360;
    }

    public Wave(Location origin, double length, double width, double height, float yaw/* normalized */) {
        this.origin = origin;
        this.length = length;
        this.width = width;
        this.height = height;
        this.yaw = yaw;
        refresh2DWaves();
    }

    protected void refresh2DWaves() {
        // xyWave = new Wave2D(origin.getX(), origin.getY(), length, height, yaw < 360 && yaw > 180);
        zyWave = new Wave2D(origin.getZ(), origin.getY(), length, height, yaw < 270 && yaw > 90);
    }

    public Location getOrigin() {
        return origin;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public float getYaw() {
        return yaw;
    }

    public void setLength(double length) {
        this.length = length;
        refresh2DWaves();
    }

    public void setWidth(double width) {
        this.width = width;
        refresh2DWaves();
    }

    public void setHeight(double height) {
        this.height = height;
        refresh2DWaves();
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        refresh2DWaves();
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<Location>();
        Location2D originXZ = new Location2D(origin.getX(), origin.getZ());
        for (double x = originXZ.getX(); x <= originXZ.getX() + width; x++) {
            for (double z = originXZ.getY(); z <= originXZ.getY() + length; z++) {
                double y = zyWave.getYAt(z);
                if (y == Double.NaN) {
                    continue;
                }
                Location2D rotated = rotateXZ(originXZ, new Location2D(x, z), yaw);
                locations.add(new Location(origin.getWorld(), rotated.getX(), y, rotated.getY()));
            }
        }
        return locations;
    }

    protected Location2D rotateXZ(Location2D origin, Location2D loc, float yaw) {
        double xPrime = (loc.getX() - origin.getX()) * Math.cos(Math.toRadians(yaw)) - (loc.getY() - origin.getY()) * Math.sin(Math.toRadians(yaw));
        double zPrime = (loc.getX() - origin.getX()) * Math.sin(Math.toRadians(yaw)) + (loc.getY() - origin.getY()) * Math.cos(Math.toRadians(yaw));
        return new Location2D(xPrime, zPrime);
    }
}
