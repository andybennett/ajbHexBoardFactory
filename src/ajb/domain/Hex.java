package ajb.domain;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Hex {

    private String identifier;
    private int size = 100;
    private Point2D.Double middlePoint;
    private Point2D.Double[] points;
    private Polygon poly;

    private Hex adjacentHexNorth;
    private Hex adjacentHexNorthEast;
    private Hex adjacentHexSouthEast;
    private Hex adjacentHexSouth;
    private Hex adjacentHexSouthWest;
    private Hex adjacentHexNorthWest;

    private Color lineColor = Color.decode("#BED6FF");
    private Color fillColor = Color.decode("#1E1E1E");

    private boolean accessible = true;
    private boolean decorated = false;
    private BufferedImage decoration;

    /**
     * Constructor
     * 
     * @param point {@link Point2D.Double}
     * @param identifier {@link String}
     */
    public Hex(Point2D.Double point, String identifier) {

        this.identifier = identifier;
        this.middlePoint = point;

        double h = calculateH(size);
        double r = calculateR(size);

        double x = (point.getX() - (size / 2));
        double y = (point.getY() - r);

        this.points = new Point2D.Double[6];
        this.points[0] = new Point2D.Double(x, y);
        this.points[1] = new Point2D.Double(x + size, y);
        this.points[2] = new Point2D.Double(x + size + h, y + r);
        this.points[3] = new Point2D.Double(x + size, y + r + r);
        this.points[4] = new Point2D.Double(x, y + r + r);
        this.points[5] = new Point2D.Double(x - h, y + r);

        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < points.length; i++) {
            xPoints[i] = (int) points[i].getX();
            yPoints[i] = (int) points[i].getY();
        }

        this.poly = new Polygon(xPoints, yPoints, 6);
    }

    /**
     * Returns true if the passed in point is within this hex
     * 
     * @param point {@link Point2D.Double}
     * @return {@link boolean}
     */
    public boolean isPointInHex(Point2D.Double point) {

        int j = points.length - 1;
        boolean oddNodes = false;

        for (int i = 0; i < points.length; i++) {
            if (points[i].getY() < point.getY() && points[j].getY() >= point.getY() || points[j].getY() < point.getY() && points[i].getY() >= point.getY()) {
                if (points[i].getX() + (point.getY() - points[i].getY()) / (points[j].getY() - points[i].getY()) * (points[j].getX() - points[i].getX()) < point
                        .getX()) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }

        return oddNodes;
    }

    // Split these 3 functions into separate util class?
    private float calculateH(float side) {
        return (float) (Math.sin(degreesToRadians(30)) * side);
    }

    private float calculateR(float side) {
        return (float) (Math.cos(degreesToRadians(30)) * side);
    }

    private double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    // Getter and Setters
    public float getHeight() {
        float r = calculateR(size);
        return r + r;
    }

    public float getWidth() {
        float h = calculateH(size);
        return size + h;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Point2D.Double getMiddlePoint() {
        return middlePoint;
    }

    public void setMiddlePoint(Point2D.Double middlePoint) {
        this.middlePoint = middlePoint;
    }

    public Point2D.Double[] getPoints() {
        return points;
    }

    public void setPoints(Point2D.Double[] points) {
        this.points = points;
    }

    public Polygon getPoly() {
        return poly;
    }

    public void setPoly(Polygon poly) {
        this.poly = poly;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }

    public boolean isDecorated() {
        return decorated;
    }

    public void setDecorated(boolean decorated) {
        this.decorated = decorated;
    }

    public BufferedImage getDecoration() {
        return decoration;
    }

    public void setDecoration(BufferedImage decoration) {
        this.decoration = decoration;
    }

    public Hex getAdjacentHexNorth() {
        return adjacentHexNorth;
    }

    public void setAdjacentHexNorth(Hex adjacentHexNorth) {
        this.adjacentHexNorth = adjacentHexNorth;
    }

    public Hex getAdjacentHexNorthEast() {
        return adjacentHexNorthEast;
    }

    public void setAdjacentHexNorthEast(Hex adjacentHexNorthEast) {
        this.adjacentHexNorthEast = adjacentHexNorthEast;
    }

    public Hex getAdjacentHexSouthEast() {
        return adjacentHexSouthEast;
    }

    public void setAdjacentHexSouthEast(Hex adjacentHexSouthEast) {
        this.adjacentHexSouthEast = adjacentHexSouthEast;
    }

    public Hex getAdjacentHexSouth() {
        return adjacentHexSouth;
    }

    public void setAdjacentHexSouth(Hex adjacentHexSouth) {
        this.adjacentHexSouth = adjacentHexSouth;
    }

    public Hex getAdjacentHexSouthWest() {
        return adjacentHexSouthWest;
    }

    public void setAdjacentHexSouthWest(Hex adjacentHexSouthWest) {
        this.adjacentHexSouthWest = adjacentHexSouthWest;
    }

    public Hex getAdjacentHexNorthWest() {
        return adjacentHexNorthWest;
    }

    public void setAdjacentHexNorthWest(Hex adjacentHexNorthWest) {
        this.adjacentHexNorthWest = adjacentHexNorthWest;
    }
}
