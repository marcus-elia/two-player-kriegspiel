import java.io.IOException;

public class Rook extends Piece
{
    public Rook(Team team, ChessBoard board) throws IOException
    {
        super(team, board);
        this.pieceType = PieceType.Rook;
        this.setImage();
    }
}
