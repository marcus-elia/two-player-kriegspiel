import java.io.IOException;

public class King extends Piece
{
    public King(Team team, ChessBoard board) throws IOException
    {
        super(team, board);
        this.pieceType = PieceType.King;
        this.setImage();
    }
}