import java.awt.*;

public class GameManager
{
    private ChessBoard board;

    public GameManager()
    {
        this.board = new ChessBoard();
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        this.board.render(g2d);
    }
}
