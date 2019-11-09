import java.io.IOException;
import java.util.ArrayList;

public class Pawn extends Piece
{
    public Pawn(Team team, ChessBoard board, int location) throws IOException
    {
        super(team, board, location);
        this.pieceType = PieceType.Pawn;
        this.setImage();
    }

    @Override
    public void setAttackableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        int[] coords = locationToCoords(this.location);
        int x = coords[0];
        int y = coords[1];

        if(this.team == Team.White)
        {
            if(x > 0) // Assume pawn is not on top row
            {
                locs.add(coordsToLocation(x-1, y-1));
            }
            if(x < 7)
            {
                locs.add(coordsToLocation(x+1, y-1));
            }
        }
        else if(this.team == Team.Black)
        {
            if(x > 0) // Assume pawn is not on bottom row
            {
                locs.add(coordsToLocation(x-1, y+1));
            }
            if(x < 7)
            {
                locs.add(coordsToLocation(x+1, y+1));
            }
        }
        this.attackableLocations = locs;
    }

    @Override
    public void setMovableLocationsIgnoringCheck()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();

        // If an enemy is at a diagonal attackable square, add the loc
        for(int loc : this.getAttackableLocations())
        {
            if(this.board.containsEnemy(this.team, loc))
            {
                locs.add(loc);
            }
        }

        // Check the space(s) in front of this one
        int x = locationToCoords(this.location)[0];
        int y = locationToCoords(this.location)[1];
        if(this.team == Team.White)
        {
            if(this.board.isEmpty(x, y-1))
            {
                locs.add(coordsToLocation(x, y-1));

                if(y == 6 && this.board.isEmpty(x, y-2))
                {
                    locs.add(coordsToLocation(x, y-2));
                }
            }
        }
        else
        {
            if(this.board.isEmpty(x, y+1))
            {
                locs.add(coordsToLocation(x, y+1));

                if(y == 1 && this.board.isEmpty(x, y+2))
                {
                    locs.add(coordsToLocation(x, y+2));
                }
            }
        }
        this.movableLocationsIgnoringCheck = locs;
    }
}
