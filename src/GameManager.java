import java.awt.*;

public class GameManager
{
    private ChessBoard board;
    private int windowWidth;
    private int windowHeight;

    public GameManager(int width, int height)
    {
        this.board = new ChessBoard();
        this.windowWidth = width;
        this.windowHeight = height;
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        this.board.render(g2d);
    }

    // ===============================
    //
    //           Getters
    //
    // ===============================
    public ChessBoard getBoard()
    {
        return this.board;
    }
    public int getWindowWidth()
    {
        return this.windowWidth;
    }
    public int getWindowHeight()
    {
        return this.windowHeight;
    }
}
