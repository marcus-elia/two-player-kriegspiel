import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Piece
{
    private PieceType pieceType;

    public Piece()
    {

    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {

    }

    public PieceType getPieceType()
    {
        return this.pieceType;
    }

    public abstract BufferedImage getImage();
}
