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
    public ArrayList<Integer> getReachableLocations()
    {
        return null;
    }
}
