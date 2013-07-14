package ajb.domain;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class HexBoard {

    private int amountOfRings;
    private List<Hex> hexList;
    private Map<String, Hex> hexesByIdentifier;

    /**
     * Constructor
     * 
     * @param hexList {@link List} of {@link Hex}
     */
    public HexBoard(List<Hex> hexList) {
        this.hexList = hexList;
    }

    /**
     * Outputs the hex board to an image
     * 
     * @param destinationImagePath {@link String}
     * @throws Exception
     */
    public void hexBoardToImageWithInfo(String destinationImagePath) throws Exception {

        if (this.getHexList().isEmpty()) {
            throw new Exception("Hex List is null or empty!");
        }

        BufferedImage img = new BufferedImage(600 * amountOfRings, 600 * amountOfRings, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.translate(img.getWidth() / 2, img.getHeight() / 2);

        g.setStroke(new BasicStroke(5f));

        for (Hex hex : this.getHexList()) {
            g.setColor(hex.getFillColor());
            g.fillPolygon(hex.getPoly());
            g.setColor(hex.getLineColor());
            g.drawPolygon(hex.getPoly());

            g.setColor(Color.decode("#FFC600"));
            int identifierWidth = g.getFontMetrics().stringWidth(hex.getIdentifier());
            g.drawString(hex.getIdentifier(), (int) hex.getMiddlePoint().getX() - (identifierWidth / 2), (int) hex.getMiddlePoint().getY() + 5);

            g.setColor(Color.decode("#EFC090"));

            Hex adjacentNorthHex = hex.getAdjacentHexNorth();
            Hex adjacentNorthEastHex = hex.getAdjacentHexNorthEast();
            Hex adjacentSouthEastHex = hex.getAdjacentHexSouthEast();
            Hex adjacentSouthHex = hex.getAdjacentHexSouth();
            Hex adjacentSouthWestHex = hex.getAdjacentHexSouthWest();
            Hex adjacentNorthWestHex = hex.getAdjacentHexNorthWest();

            String adjacentNorthHexIdentifier = adjacentNorthHex != null ? adjacentNorthHex.getIdentifier() : "";
            String adjacentNorthEastHexIdentifier = adjacentNorthEastHex != null ? adjacentNorthEastHex.getIdentifier() : "";
            String adjacentSouthEastHexIdentifier = adjacentSouthEastHex != null ? adjacentSouthEastHex.getIdentifier() : "";
            String adjacentSouthHexIdentifier = adjacentSouthHex != null ? adjacentSouthHex.getIdentifier() : "";
            String adjacentSouthWestHexIdentifier = adjacentSouthWestHex != null ? adjacentSouthWestHex.getIdentifier() : "";
            String adjacentNorthWestHexIdentifier = adjacentNorthWestHex != null ? adjacentNorthWestHex.getIdentifier() : "";

            int stringWidth = g.getFontMetrics().stringWidth(adjacentNorthHexIdentifier);
            g.drawString(adjacentNorthHexIdentifier, (int) hex.getMiddlePoint().getX() - (stringWidth / 2),
                    (int) hex.getMiddlePoint().getY() - (hex.getHeight() / 2) + 20);

            // @TODO Something for later - how to determine how far diagonally based on hex size? For now this will only
            // work with the default size of 100
            drawRotatedString(g, adjacentNorthEastHexIdentifier, 60d, hex.getMiddlePoint().getX() + 55, hex.getMiddlePoint().getY() - 45);
            drawRotatedString(g, adjacentSouthEastHexIdentifier, 120d, hex.getMiddlePoint().getX() + 65, hex.getMiddlePoint().getY() + 25);

            stringWidth = g.getFontMetrics().stringWidth(adjacentSouthHexIdentifier);
            g.drawString(adjacentSouthHexIdentifier, (int) hex.getMiddlePoint().getX() - (stringWidth / 2),
                    (int) hex.getMiddlePoint().getY() + (hex.getHeight() / 2) - 10);

            // @TODO Something for later - how to determine how far diagonally based on hex size? For now this will only
            // work with the default size of 100
            drawRotatedString(g, adjacentSouthWestHexIdentifier, 240d, hex.getMiddlePoint().getX() - 55, hex.getMiddlePoint().getY() + 45);
            drawRotatedString(g, adjacentNorthWestHexIdentifier, 300d, hex.getMiddlePoint().getX() - 65, hex.getMiddlePoint().getY() - 25);

        }

        g.dispose();

        ImageIO.write(img, "png", new File(destinationImagePath));
    }

    /**
     * Draws a string rotated by the passed in angle
     * 
     * @param g {@link Graphics2d}
     * @param stringToDraw {@link String}
     * @param angle {@link double}
     * @param x {@link double}
     * @param y {@link double}
     */
    private void drawRotatedString(Graphics2D g, String stringToDraw, double angle, double x, double y) {
        // Create a rotation transformation for the font.
        AffineTransform fontAT = new AffineTransform();

        // get the current font
        Font theFont = g.getFont();

        // Derive a new font using a rotation transform
        fontAT.rotate(angle * java.lang.Math.PI / 180);
        Font theDerivedFont = theFont.deriveFont(fontAT);

        // set the derived font in the Graphics2D context
        g.setFont(theDerivedFont);

        g.drawString(stringToDraw, (int) x, (int) y);

        g.setFont(theFont);
    }

    // Getters and Setters
    public int getAmountOfRings() {
        return amountOfRings;
    }

    public void setAmountOfRings(int amountOfRings) {
        this.amountOfRings = amountOfRings;
    }

    public List<Hex> getHexList() {
        return hexList;
    }

    public void setHexList(List<Hex> hexList) {
        this.hexList = hexList;
    }

    public Map<String, Hex> getHexesByIdentifier() {
        return hexesByIdentifier;
    }

    public void setHexesByIdentifier(Map<String, Hex> hexesByIdentifier) {
        this.hexesByIdentifier = hexesByIdentifier;
    }
}
