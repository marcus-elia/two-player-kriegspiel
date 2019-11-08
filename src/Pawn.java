import java.io.IOException;

public class Pawn extends Piece
{
    public Pawn(Team team, ChessBoard board) throws IOException
    {
        super(team, board);
        this.pieceType = PieceType.Pawn;
        this.setImage();
    }
}
