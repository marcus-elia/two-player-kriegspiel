import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Piece
{
    private PieceType pieceType;
    private Team team;

    public Piece(Team team)
    {
        this.team = team;
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {

    }

    // ======================================
    //
    //               Getters
    //
    // ======================================
    public PieceType getPieceType()
    {
        return this.pieceType;
    }
    public Team getTeam()
    {
        return this.team;
    }

    public abstract BufferedImage getImage();
}
