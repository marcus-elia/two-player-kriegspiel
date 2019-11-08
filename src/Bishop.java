import java.io.IOException;

public class Bishop extends Piece
{
    public Bishop(Team team, ChessBoard board) throws IOException
    {
        super(team, board);
        this.pieceType = PieceType.Bishop;
        this.setImage();
    }
}
