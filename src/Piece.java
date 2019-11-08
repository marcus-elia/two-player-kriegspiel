import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Piece
{
    protected PieceType pieceType;
    protected Team team;
    protected ChessBoard board;
    protected BufferedImage image;

    public Piece(Team team, ChessBoard board) throws IOException {
        this.team = team;
        this.board = board;
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
    public BufferedImage getImage()
    {
        return this.image;
    }
    public ChessBoard getBoard()
    {
        return this.board;
    }

    public void setImage() throws IOException
    {
        this.image = ImageIO.read(new File("chess\\" + this.board.getManager().teamToString(this.team) +
                this.board.getManager().pieceTypeToString(this.pieceType) + ".png"));
    }
}
