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
    public ArrayList<Integer> getAttackableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        int[] coords = locationToCoords(this.location);
        int x = coords[0];
        int y = coords[1];

        if(this.team == Team.White)
        {
            locs
        }
    }
}
