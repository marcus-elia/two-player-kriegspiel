import java.awt.*;

public class ChessBoard
{

    private Piece[][] pieces;
    private GameManager manager;
    private int squareWidth; // the width of the chess board squares

    public ChessBoard(GameManager manager)
    {
        pieces = new Piece[8][8];
        this.manager = manager;
        this.setSquareWidth();
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        // draw the empty spaces
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if((i + j) % 2 == 0)
                {
                    g2d.setColor(new Color(100, 150, 150));
                }
                else
                {
                    g2d.setColor(new Color(150, 150, 100));
                }
                g2d.fillRect(i*this.squareWidth, j*this.squareWidth,
                        (i+1)*this.squareWidth, (j+1)*this.squareWidth);
            }
        }
    }


    // Sets the with of the squares to be 1/10 of whichever is the smaller dimension
    public void setSquareWidth()
    {
        if(this.manager.getWindowHeight() > this.manager.getWindowWidth())
        {
            this.squareWidth = this.manager.getWindowWidth() / 10;
        }
        else
        {
            this.squareWidth = this.manager.getWindowHeight() / 10;
        }

    }
}
