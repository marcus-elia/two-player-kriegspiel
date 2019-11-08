import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Piece
{
    protected PieceType pieceType;
    protected Team team;
    protected ChessBoard board;
    protected BufferedImage image;

    // An int between 0 and 63 representing the position on the board
    protected int location;

    public Piece(Team team, ChessBoard board, int location) throws IOException {
        this.team = team;
        this.board = board;
        this.location = location;
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

    public abstract ArrayList<Integer> getReachableLocations();

    // Convert between 2D coords and an int between 0 and 63
    public int coordsToLocation(int x, int y)
    {
        return x + 8*y;
    }
    public int[] locationToCoords(int loc)
    {
        int x = loc % 8;
        int y = (loc - x) / 8;
        return new int[]{x,y};
    }
}
