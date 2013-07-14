package ajb.examples;

import ajb.domain.HexBoard;
import ajb.factory.HexBoardFactory;

public class CreateAndWriteBoardImgToDisk {

    public CreateAndWriteBoardImgToDisk() {
        try {
            // Create the factory
            HexBoardFactory hexBoardFactory = new HexBoardFactory();

            // Use the factory to create the hex board
            HexBoard board = hexBoardFactory.createHexBoard(3);

            // Output hex board to image
            // Change this string to place the img within a different folder i.e. c://<directory>//<filename>.png
            board.hexBoardToImageWithInfo("hexboard.png");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        CreateAndWriteBoardImgToDisk app = new CreateAndWriteBoardImgToDisk();
    }
}
