import java.io.IOException;
import java.util.ArrayList;

public class Bishop extends Piece
{
    public Bishop(Team team, ChessBoard board, int location) throws IOException
    {
        super(team, board, location);
        this.pieceType = PieceType.Bishop;
        this.setImage();
    }

    @Override
    // Check along each of the four diagonals until you go out of bounds or hit a piece
    public ArrayList<Integer> getAttackableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        int[] coords = locationToCoords(this.location);
        int x = coords[0];
        int y = coords[1];

        // check up and left
        int tempX = x - 1;
        int tempY = y - 1;
        while(tempX > -1 && tempY > -1)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX--;
            tempY--;
        }
        // check up and right
        tempX = x + 1;
        tempY = y - 1;
        while(tempX < 8 && tempY > -1)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX++;
            tempY--;
        }
        // check down and left
        tempX = x - 1;
        tempY = y + 1;
        while(tempX > -1 && tempY < 8)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX--;
            tempY++;
        }
        // check down and right
        tempX = x + 1;
        tempY = y + 1;
        while(tempX < 8 && tempY < 8)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX++;
            tempY++;
        }
        return locs;
    }
}
