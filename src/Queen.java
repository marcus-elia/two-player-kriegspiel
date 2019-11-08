import java.io.IOException;
import java.util.ArrayList;

public class Queen extends Piece
{
    public Queen(Team team, ChessBoard board, int location) throws IOException
    {
        super(team, board, location);
        this.pieceType = PieceType.Queen;
        this.setImage();
    }

    @Override
    public ArrayList<Integer> getAttackableLocations()
    {
        ArrayList<Integer> locs = new ArrayList<Integer>();
        int[] coords = locationToCoords(this.location);
        int x = coords[0];
        int y = coords[1];

        // Use the Rook code to check ranks and files
        int tempX = x - 1;
        // Check left
        while(tempX > -1)
        {
            locs.add(coordsToLocation(tempX, y));
            if(!this.board.isEmpty(tempX, y))
            {
                break;
            }
            tempX--;
        }
        // Check right
        tempX = x + 1;
        while(tempX < 8)
        {
            locs.add(coordsToLocation(tempX, y));
            if(!this.board.isEmpty(tempX, y))
            {
                break;
            }
            tempX++;
        }
        // Check up
        int tempY = y - 1;
        while(tempY > -1)
        {
            locs.add(coordsToLocation(x, tempY));
            if(!this.board.isEmpty(x, tempY))
            {
                break;
            }
            tempY--;
        }
        // Check down
        tempY = y + 1;
        while(tempY < 8)
        {
            locs.add(coordsToLocation(x, tempY));
            if(!this.board.isEmpty(x, tempY))
            {
                break;
            }
            tempY++;
        }

        // Use the Bishop code to check diagonals
        // check up and left
        tempX = x - 1;
        tempY = y - 1;
        while(tempX > -1 && tempY > -1)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX--;
            tempY--;
        }
        // check up and right
        tempX = x + 1;
        tempY = y - 1;
        while(tempX < 8 && tempY > -1)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX++;
            tempY--;
        }
        // check down and left
        tempX = x - 1;
        tempY = y + 1;
        while(tempX > -1 && tempY < 8)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX--;
            tempY++;
        }
        // check down and right
        tempX = x + 1;
        tempY = y + 1;
        while(tempX < 8 && tempY < 8)
        {
            locs.add(coordsToLocation(tempX, tempY));
            if(!this.board.isEmpty(tempX, tempY))
            {
                break;
            }
            tempX++;
            tempY++;
        }
        return locs;
    }
}