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
    public ArrayList<Integer> getAttackableLocations()
    {
        return null;
    }
}
