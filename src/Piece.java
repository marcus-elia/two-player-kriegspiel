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

    protected ArrayList<Integer> attackableLocations;
    protected ArrayList<Integer> attackableNonTeammateLocations;
    protected ArrayList<Integer> movableLocationsIgnoringCheck;
    protected ArrayList<Integer> movableLocations;

    // An int between 0 and 63 representing the position on the board
    protected int location;

    public Piece(Team team, ChessBoard board, int location) throws IOException
    {
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
    public int getLocation()
    {
        return this.location;
    }
    public ArrayList<Integer> getAttackableLocations()
    {
        return this.attackableLocations;
    }
    public ArrayList<Integer> getAttackableNonTeammateLocations()
    {
        return this.attackableNonTeammateLocations;
    }
    public ArrayList<Integer> getMovableLocationsIgnoringCheck()
    {
        return this.movableLocationsIgnoringCheck;
    }
    public ArrayList<Integer> getMovableLocations()
    {
        return this.movableLocations;
    }

    public boolean canMove()
    {
        return !this.movableLocations.isEmpty();
    }

    // ==============================
    //
    //            Setters
    //
    //===============================
    public void setImage() throws IOException
    {
        this.image = ImageIO.read(new File("chess\\" + this.board.getManager().teamToString(this.team) +
                this.board.getManager().pieceTypeToString(this.pieceType) + ".png"));
    }
    public void setLocation(int loc)
    {
        this.location = loc;
    }


    // ======================================
    //
    //         Movement Functions
    //
    // =====================================

    // Update everything because a piece has moved
    public void update()
    {
        this.setAttackableLocations();
        this.setAttackableNonTeammateLocations();
        this.setMovableLocationsIgnoringCheck();
        this.setMovableLocations();
    }

    // Update some things when looking for check
    public void updateForCheck()
    {
        this.setAttackableLocations();
        this.setAttackableNonTeammateLocations();
        this.setMovableLocationsIgnoringCheck();
    }

    public abstract void setAttackableLocations();

    public void setAttackableNonTeammateLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        ArrayList<Integer> allLocs = this.getAttackableLocations();

        // iterate through all attackable locations. If there is not a
        // teammate there, add it to the list
        for(int loc: allLocs)
        {
            if(!this.board.containsTeammate(this.team, loc))
            {
                locs.add(loc);
            }
        }
        this.attackableNonTeammateLocations = locs;
    }

    public abstract void setMovableLocationsIgnoringCheck();

    public void setMovableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        ArrayList<Integer> allLocs = this.getMovableLocationsIgnoringCheck();

        // Iterate through all possible locations. If moving there wouldn't put us
        // in check, add that location to the list
        for(int loc : allLocs)
        {
            if(!this.board.wouldPutSelfInCheck(this.team, this, loc))
            {
                locs.add(loc);
            }
        }
        this.movableLocations = locs;
    }

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
