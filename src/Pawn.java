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
    public ArrayList<Integer> getReachableLocations()
    {
        return null;
    }
}
