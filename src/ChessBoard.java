import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ChessBoard
{

    private Piece[][] pieces;
    private GameManager manager;
    private int squareWidth; // the width of the chess board squares
    private int boardSize;   // the size of the entire board

    private int selectedXCoord;
    private int selectedYCoord;

    public ChessBoard(GameManager manager) throws IOException
    {
        pieces = new Piece[8][8];
        this.manager = manager;
        this.setSquareWidth();
        this.boardSize = 8*this.squareWidth;
        this.fillBoard();
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        // iterate through the board
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                // draw the empty spaces
                if((i + j) % 2 == 0)
                {
                    g2d.setColor(new Color(100, 250, 250));
                }
                else
                {
                    g2d.setColor(new Color(50, 50, 150));
                }
                g2d.fillRect(i*this.squareWidth, j*this.squareWidth, this.squareWidth, this.squareWidth);

                // fill in the piece if there is a piece there
                if(pieces[i][j] != null)
                {
                    g2d.drawImage(pieces[i][j].getImage(), i*this.squareWidth, j*this.squareWidth,
                            this.squareWidth, this.squareWidth, null);
                }
            }
        }

        // highlight if a piece is selected
        if(this.manager.getIsPieceSelected())
        {
            g2d.setColor(Color.magenta);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(this.selectedXCoord*this.squareWidth, this.selectedYCoord*this.squareWidth,
                    this.squareWidth, this.squareWidth);
            highlightAttackableSquares(g2d);
        }
    }

    public void highlightAttackableSquares(Graphics2D g2d)
    {
        Piece p = this.pieces[selectedXCoord][selectedYCoord];
        ArrayList<Integer> squaresToHighlight = p.getAttackableLocations();
        for(int loc : squaresToHighlight)
        {
            int x = locationToCoords(loc)[0];
            int y = locationToCoords(loc)[1];
            g2d.setColor(Color.magenta);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(x*this.squareWidth, y*this.squareWidth,
                    this.squareWidth, this.squareWidth);
        }
    }

    // ==========================================
    //
    //         Initialization Functions
    //
    // ==========================================

    // Sets the with of the squares to be 1/10 of whichever is the smaller dimension
    public void setSquareWidth()
    {
        if(this.manager.getWindowHeight() > this.manager.getWindowWidth())
        {
            this.squareWidth = this.manager.getWindowWidth() / 8;
        }
        else
        {
            this.squareWidth = this.manager.getWindowHeight() / 8;
        }
    }

    public void fillBoard() throws IOException
    {
        // Make the pawns
        for(int i = 0; i < 8; i++)
        {
            this.pieces[i][1] = new Pawn(Team.Black, this, coordsToLocation(i, 1));
            this.pieces[i][6] = new Pawn(Team.White, this, coordsToLocation(i, 6));
        }

        // Bishops
        this.pieces[2][0] = new Bishop(Team.Black, this, coordsToLocation(2, 0));
        this.pieces[5][0] = new Bishop(Team.Black, this, coordsToLocation(5, 0));
        this.pieces[2][7] = new Bishop(Team.White, this, coordsToLocation(2, 7));
        this.pieces[5][7] = new Bishop(Team.White, this, coordsToLocation(5, 7));

        // Rooks
        this.pieces[0][0] = new Rook(Team.Black, this, coordsToLocation(0, 0));
        this.pieces[7][0] = new Rook(Team.Black, this, coordsToLocation(7, 0));
        this.pieces[0][7] = new Rook(Team.White, this, coordsToLocation(0, 7));
        this.pieces[7][7] = new Rook(Team.White, this, coordsToLocation(7, 7));

        // Knights
        this.pieces[1][0] = new Knight(Team.Black, this, coordsToLocation(1, 0));
        this.pieces[6][0] = new Knight(Team.Black, this, coordsToLocation(6, 0));
        this.pieces[1][7] = new Knight(Team.White, this, coordsToLocation(1, 7));
        this.pieces[6][7] = new Knight(Team.White, this, coordsToLocation(6, 7));

        // Queens
        this.pieces[3][0] = new Queen(Team.Black, this, coordsToLocation(3, 0));
        this.pieces[3][7] = new Queen(Team.White, this, coordsToLocation(3, 17));

        // Kings
        this.pieces[4][0] = new King(Team.Black, this, coordsToLocation(4, 0));
        this.pieces[4][7] = new King(Team.White, this, coordsToLocation(4, 7));
    }

    public GameManager getManager()
    {
        return this.manager;
    }
    public int getBoardSize()
    {
        return this.boardSize;
    }

    public void selectPiece(int mx, int my)
    {
        if(mx <= this.boardSize && my <= this.boardSize)
        {
            this.selectedXCoord = mx / this.squareWidth;
            this.selectedYCoord = my / this.squareWidth;
        }
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

    // Returns true if the given location on the board is empty, false otherwise
    public boolean isEmpty(int loc)
    {
        int[] coords = locationToCoords(loc);
        return pieces[coords[0]][coords[1]] == null;
    }
    public boolean isEmpty(int x, int y)
    {
        return pieces[x][y] == null;
    }
}
