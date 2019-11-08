import java.io.IOException;
import java.util.ArrayList;

public class Rook extends Piece
{
    public Rook(Team team, ChessBoard board, int location) throws IOException
    {
        super(team, board, location);
        this.pieceType = PieceType.Rook;
        this.setImage();
    }

    @Override
    // Check in each of the 4 directions until you find another piece, or reach and edge
    public ArrayList<Integer> getAttackableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        int[] coords = locationToCoords(this.location);
        int x = coords[0];
        int y = coords[1];

        int tempX = x - 1;
        // Check left
        while(tempX > -1)
        {
            locs.add(coordsToLocation(tempX, y));
            if(!this.board.isEmpty(tempX, y))
            {
                break;
            }
            tempX--;
        }
        // Check right
        tempX = x + 1;
        while(tempX < 8)
        {
            locs.add(coordsToLocation(tempX, y));
            if(!this.board.isEmpty(tempX, y))
            {
                break;
            }
            tempX++;
        }
        // Check up
        int tempY = y - 1;
        while(tempY > -1)
        {
            locs.add(coordsToLocation(x, tempY));
            if(!this.board.isEmpty(x, tempY))
            {
                break;
            }
            tempY--;
        }
        // Check down
        tempY = y + 1;
        while(tempY < 8)
        {
            locs.add(coordsToLocation(x, tempY));
            if(!this.board.isEmpty(x, tempY))
            {
                break;
            }
            tempY++;
        }
        return locs;
    }
}
