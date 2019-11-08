import java.io.IOException;

public class Queen extends Piece
{
    public Queen(Team team, ChessBoard board) throws IOException
    {
        super(team, board);
        this.pieceType = PieceType.Queen;
        this.setImage();
    }
}