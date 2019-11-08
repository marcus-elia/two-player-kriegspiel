import java.io.IOException;

public class Knight extends Piece
{
    public Knight(Team team, ChessBoard board) throws IOException
    {
        super(team, board);
        this.pieceType = PieceType.Knight;
        this.setImage();
    }
}