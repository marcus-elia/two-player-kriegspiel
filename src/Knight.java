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
        return null;
    }
}