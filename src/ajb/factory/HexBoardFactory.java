package ajb.factory;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ajb.domain.Hex;
import ajb.domain.HexBoard;

public class HexBoardFactory {

    /**
     * Creates a new {@link HexBoard} with the amount of rings as per the passed in value
     * 
     * @param amountOfRings {@link int}
     * @return {@link HexBoard}
     */
    public HexBoard createHexBoard(int amountOfRings, int hexSize) {
        HexBoard hexBoard = createBlankHexBoard(amountOfRings, hexSize);
        return hexBoard;
    }

    /**
     * Creates a new {@link HexBoard} with the amount of rings as per the passed in value
     * 
     * @param amountOfRings {@link int}
     * @return {@link HexBoard}
     */
    private HexBoard createBlankHexBoard(int ringCount, int hexSize) {

        List<Hex> hexList = new ArrayList<Hex>();

        Hex hex00 = new Hex(new Point2D.Double(0, 0), "0,0", hexSize);
        hexList.add(hex00);

        for (int i = 1; i < ringCount + 1; i++) {
            createHexRing(i, hexList, hexSize);
        }

        HexBoard hexBoard = new HexBoard(hexList);
        hexBoard.setAmountOfRings(ringCount);
        hexBoard.setHexesByIdentifier(createHexesByIdentifierMap(hexList));
        calculateAdjacentHexes(hexBoard);
        return hexBoard;
    }

    /**
     * Returns a Map of {@link Hex}'s by their identifier
     * 
     * @param hexList {@link List} of {@link Hex}
     * @return {@link Map}[Key:{@link String}][Value:{@link Hex}]
     */
    private Map<String, Hex> createHexesByIdentifierMap(List<Hex> hexList) {

        Map<String, Hex> hexesByIdentifierMap = new HashMap<String, Hex>();

        for (Hex hex : hexList) {
            hexesByIdentifierMap.put(hex.getIdentifier(), hex);
        }

        return hexesByIdentifierMap;
    }

    private void createHexRing(int modifier, List<Hex> hexList, int hexSize) {

        Hex sampleHex = new Hex(new Point2D.Double(0, 0), "SampleHex", hexSize);

        float height = sampleHex.getHeight();
        float width = sampleHex.getWidth();
        float halfHeight = height / 2;

        Integer hexCount = 1;
        // North
        Hex hex = new Hex(new Point2D.Double(0, 0 - (height * modifier)), modifier + "," + hexCount, hexSize);
        hexList.add(hex);
        hexCount++;

        hexCount = createNorthToNorthEastFillerHexes(modifier, height, width, halfHeight, hexList, hexCount, hexSize);

        // North East
        hex = new Hex(new Point2D.Double(0 + (width * modifier), 0 - (halfHeight * modifier)), modifier + "," + hexCount, hexSize);
        hexList.add(hex);
        hexCount++;

        hexCount = createNorthEastToSoutEastFillerHexes(modifier, height, width, halfHeight, hexList, hexCount, hexSize);

        // South East
        hex = new Hex(new Point2D.Double(0 + (width * modifier), 0 + (halfHeight * modifier)), modifier + "," + hexCount, hexSize);
        hexList.add(hex);
        hexCount++;

        hexCount = createSouthEastToSouthFillerHexes(modifier, height, width, halfHeight, hexList, hexCount, hexSize);

        // South
        hex = new Hex(new Point2D.Double(0, 0 + (height * modifier)), modifier + "," + hexCount, hexSize);
        hexList.add(hex);
        hexCount++;

        hexCount = createSouthToSouthWestFillerHexes(modifier, height, width, halfHeight, hexList, hexCount, hexSize);

        // South West
        hex = new Hex(new Point2D.Double(0 - (width * modifier), 0 + (halfHeight * modifier)), modifier + "," + hexCount, hexSize);
        hexList.add(hex);
        hexCount++;

        hexCount = createSouthWestToNorthWestFillerHexes(modifier, height, width, halfHeight, hexList, hexCount, hexSize);

        // North West
        hex = new Hex(new Point2D.Double(0 - (width * modifier), 0 - (halfHeight * modifier)), modifier + "," + hexCount, hexSize);
        hexList.add(hex);
        hexCount++;

        hexCount = createNorthWestToNorthFillerHexes(modifier, height, width, halfHeight, hexList, hexCount, hexSize);
    }

    private int createNorthToNorthEastFillerHexes(int modifier, float height, float width, float halfHeight, List<Hex> hexList, int hexCount, int hexSize) {
        float posX = 0 + width;
        float posY = 0 - (height * modifier) + halfHeight;

        for (int i = 1; i < modifier; i++) {
            Hex hex = new Hex(new Point2D.Double(posX, posY), modifier + "," + hexCount, hexSize);
            hexList.add(hex);
            posX += width;
            posY += halfHeight;
            hexCount++;
        }
        
        return hexCount;
    }

    private int createNorthEastToSoutEastFillerHexes(int modifier, float height, float width, float halfHeight, List<Hex> hexList, int hexCount, int hexSize) {
        float posX = 0 + (width * modifier);
        float posY = 0 - (halfHeight * modifier) + height;

        for (int i = 1; i < modifier; i++) {
            Hex hex = new Hex(new Point2D.Double(posX, posY), modifier + "," + hexCount, hexSize);
            hexList.add(hex);
            posY += height;
            hexCount++;
        }
        
        return hexCount;
    }

    private int createSouthEastToSouthFillerHexes(int modifier, float height, float width, float halfHeight, List<Hex> hexList, int hexCount, int hexSize) {
        float posX = 0 + (width * modifier) - width;
        float posY = 0 + (halfHeight * modifier) + halfHeight;

        for (int i = 1; i < modifier; i++) {
            Hex hex = new Hex(new Point2D.Double(posX, posY), modifier + "," + hexCount, hexSize);
            hexList.add(hex);
            posX -= width;
            posY += halfHeight;
            hexCount++;
        }
        
        return hexCount;
    }

    private int createSouthToSouthWestFillerHexes(int modifier, float height, float width, float halfHeight, List<Hex> hexList, int hexCount, int hexSize) {
        float posX = 0 - width;
        float posY = 0 + (height * modifier) - halfHeight;

        for (int i = 1; i < modifier; i++) {
            Hex hex = new Hex(new Point2D.Double(posX, posY), modifier + "," + hexCount, hexSize);
            hexList.add(hex);
            posX -= width;
            posY -= halfHeight;
            hexCount++;
        }
        
        return hexCount;
    }

    private int createSouthWestToNorthWestFillerHexes(int modifier, float height, float width, float halfHeight, List<Hex> hexList, int hexCount, int hexSize) {
        float posX = 0 - (width * modifier);
        float posY = 0 + (halfHeight * modifier) - height;

        for (int i = 1; i < modifier; i++) {
            Hex hex = new Hex(new Point2D.Double(posX, posY), modifier + "," + hexCount, hexSize);
            hexList.add(hex);
            posY -= height;
            hexCount++;
        }
        
        return hexCount;
    }

    private int createNorthWestToNorthFillerHexes(int modifier, float height, float width, float halfHeight, List<Hex> hexList, int hexCount, int hexSize) {
        float posX = 0 - (width * modifier) + width;
        float posY = 0 - (halfHeight * modifier) - halfHeight;

        for (int i = 1; i < modifier; i++) {
            Hex hex = new Hex(new Point2D.Double(posX, posY), modifier + "," + hexCount, hexSize);
            hexList.add(hex);
            posX += width;
            posY -= halfHeight;
            hexCount++;
        }
        
        return hexCount;
    }

    private void calculateAdjacentHexes(HexBoard hexBoard) {

        for (Hex hex : hexBoard.getHexList()) {

            Point2D.Double currentHexCenter = hex.getMiddlePoint();

            Point2D.Double adjacentNorthHexCenter = new Point2D.Double(currentHexCenter.getX(), currentHexCenter.getY() - hex.getHeight());
            Point2D.Double adjacentNorthEastHexCenter = new Point2D.Double(currentHexCenter.getX() + hex.getWidth(), currentHexCenter.getY()
                    - (hex.getHeight() / 2));
            Point2D.Double adjacentSouthEastHexCenter = new Point2D.Double(currentHexCenter.getX() + hex.getWidth(), currentHexCenter.getY()
                    + (hex.getHeight() / 2));
            Point2D.Double adjacentSouthHexCenter = new Point2D.Double(currentHexCenter.getX(), currentHexCenter.getY() + hex.getHeight());
            Point2D.Double adjacentSouthWestHexCenter = new Point2D.Double(currentHexCenter.getX() - hex.getWidth(), currentHexCenter.getY()
                    + (hex.getHeight() / 2));
            Point2D.Double adjacentNorthWestHexCenter = new Point2D.Double(currentHexCenter.getX() - hex.getWidth(), currentHexCenter.getY()
                    - (hex.getHeight() / 2));

            Hex adjacentNorthHex = null;
            Hex adjacentNorthEastHex = null;
            Hex adjacentSouthEastHex = null;
            Hex adjacentSouthHex = null;
            Hex adjacentSouthWestHex = null;
            Hex adjacentNorthWestHex = null;

            List<Hex> otherHexes = new ArrayList<Hex>();
            otherHexes.addAll(hexBoard.getHexList());
            otherHexes.remove(hex);

            for (Hex otherHex : otherHexes) {
                if (otherHex.isPointInHex(adjacentNorthHexCenter)) {
                    adjacentNorthHex = otherHex;
                }

                if (otherHex.isPointInHex(adjacentNorthEastHexCenter)) {
                    adjacentNorthEastHex = otherHex;
                }

                if (otherHex.isPointInHex(adjacentSouthEastHexCenter)) {
                    adjacentSouthEastHex = otherHex;
                }

                if (otherHex.isPointInHex(adjacentSouthHexCenter)) {
                    adjacentSouthHex = otherHex;
                }

                if (otherHex.isPointInHex(adjacentSouthWestHexCenter)) {
                    adjacentSouthWestHex = otherHex;
                }

                if (otherHex.isPointInHex(adjacentNorthWestHexCenter)) {
                    adjacentNorthWestHex = otherHex;
                }
            }

            hex.setAdjacentHexNorth(adjacentNorthHex);
            hex.setAdjacentHexNorthEast(adjacentNorthEastHex);
            hex.setAdjacentHexSouthEast(adjacentSouthEastHex);
            hex.setAdjacentHexSouth(adjacentSouthHex);
            hex.setAdjacentHexSouthWest(adjacentSouthWestHex);
            hex.setAdjacentHexNorthWest(adjacentNorthWestHex);
        }

    }
}
