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
        return null;
    }
}