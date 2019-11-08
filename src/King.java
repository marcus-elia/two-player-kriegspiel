import java.io.IOException;
import java.util.ArrayList;

public class King extends Piece
{
    public King(Team team, ChessBoard board, int location) throws IOException
    {
        super(team, board, location);
        this.pieceType = PieceType.King;
        this.setImage();
    }

    @Override
    public ArrayList<Integer> getReachableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        int[] coords = locationToCoords(this.location);
        int x = coords[0];
        int y = coords[1];

        // Add the diagonal squares if we are not in the corner
        if(x > 0 && y > 0)
        {
            locs.add(coordsToLocation(x-1, y-1));
        }
        if(x < 7 && y > 0)
        {
            locs.add(coordsToLocation(x+1, y-1));
        }
        if(x > 0 && y < 7)
        {
            locs.add(coordsToLocation(x-1, y+1));
        }
        if(x < 7 && y < 7)
        {
            locs.add(coordsToLocation(x+1, y+1));
        }

        // Add the bordering squares if not on the edge
        if(x > 0)
        {
            locs.add(coordsToLocation(x-1, y));
        }
        if(y > 0)
        {
            locs.add(coordsToLocation(x, y-1));
        }
        if(x < 7)
        {
            locs.add(coordsToLocation(x+1, y));
        }
        if(y < 7)
        {
            locs.add(coordsToLocation(x, y+1));
        }
        return locs;
    }
}