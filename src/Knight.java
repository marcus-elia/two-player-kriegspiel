import java.io.IOException;
import java.util.ArrayList;

public class Knight extends Piece
{
    public Knight(Team team, ChessBoard board, int location) throws IOException
    {
        super(team, board, location);
        this.pieceType = PieceType.Knight;
        this.setImage();
    }

    @Override
    public ArrayList<Integer> getReachableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        int[] coords = locationToCoords(this.location);
        int x = coords[0];
        int y = coords[1];

        // Check if the 8 positions are in bounds
        if(x > 1)
        {
            if(y > 0)
            {
                locs.add(coordsToLocation(x-2, y-1));
            }
            if(y < 7)
            {
                locs.add(coordsToLocation(x-2, y+1));
            }
        }
        if(x > 0)
        {
            if(y > 1)
            {
                locs.add(coordsToLocation(x-1, y-2));
            }
            if(y < 6)
            {
                locs.add(coordsToLocation(x-1, y+2));
            }
        }
        if(x < 6)
        {
            if(y > 0)
            {
                locs.add(coordsToLocation(x+2, y-1));
            }
            if(y < 7)
            {
                locs.add(coordsToLocation(x+2, y+1));
            }
        }
        if(x < 7)
        {
            if(y > 1)
            {
                locs.add(coordsToLocation(x+1, y-2));
            }
            if(y < 6)
            {
                locs.add(coordsToLocation(x+1, y+2));
            }
        }
        return locs;
    }
}