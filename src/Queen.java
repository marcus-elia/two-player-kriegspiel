import java.io.IOException;
import java.util.ArrayList;

public class Queen extends Piece
{
    public Queen(Team team, ChessBoard board, int location) throws IOException
    {
        super(team, board, location);
        this.pieceType = PieceType.Queen;
        this.setImage();
    }

    @Override
    public ArrayList<Integer> getAttackableLocations()
    {
        return null;
    }
}