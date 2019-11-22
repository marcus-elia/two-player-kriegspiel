import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ChessBoard
{

    private Piece[][] pieces;   // where the pieces are
    private Piece[][] renderableBoard;  // where the game is showing the pieces are
                                        // (this is different when the game is calculating
                                        // if a move is legal)
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;
    private GameManager manager;
    private int squareWidth; // the width of the chess board squares
    private int boardSize;   // the size of the entire board

    private int selectedXCoord;
    private int selectedYCoord;

    private boolean isUpdating;

    private float fadeAlpha; // for covering the screen when switching turns

    public ChessBoard(GameManager manager) throws IOException
    {
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();

        this.manager = manager;
        this.pieces = new Piece[8][8];
        this.renderableBoard = new Piece[8][8];

        this.fillBoard();
        this.updateRenderableBoard();


        this.setSquareWidth();
        this.boardSize = 8*this.squareWidth;

        this.isUpdating = true;
        this.updatePieces();
        this.isUpdating = false;

        this.fadeAlpha = 0;

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

                // fill in the piece if there is a piece there and the piece belongs to
                // whoever is moving or if it is postgame or replay
                if(renderableBoard[i][j] != null &&
                        (renderableBoard[i][j].getTeam() == this.manager.getWhoseTurn() ||
                                this.manager.getCurrentStatus() == GameStatus.PostGame ||
                                this.manager.getCurrentStatus() == GameStatus.Replay))
                {
                    g2d.drawImage(renderableBoard[i][j].getImage(), i*this.squareWidth, j*this.squareWidth,
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
            highlightMovableLocations(g2d);
        }

        this.drawLabels(g2d);
        this.drawStatusStrings(g2d);

        // draw a white rectangle over the board during transitions
        if(this.manager.getIsBetweenTurns())
        {
            if(this.fadeAlpha < 1.0)
            {
                this.fadeAlpha = (float) Math.min(this.fadeAlpha + 0.005, 1.0);
            }
        }
        else
        {
            if(this.fadeAlpha > 0.0)
            {
                this.fadeAlpha = (float) Math.max(this.fadeAlpha - 0.005, 0.0);
            }
        }
        g2d.setColor(new Color(.8f,.8f,.8f, fadeAlpha));
        g2d.fillRect(0,0,this.manager.getWindowWidth() + 20, this.manager.getWindowHeight() + 20);
    }

    public void highlightMovableLocations(Graphics2D g2d)
    {
        Piece p = this.renderableBoard[selectedXCoord][selectedYCoord];
        if(p == null)
        {
            return;
        }
        ArrayList<Integer> squaresToHighlight = p.getMovableLocations();
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

    // Make the a,b,...,h and 1,2,...,8
    public void drawLabels(Graphics2D g2d)
    {
        g2d.setFont(new Font("Courier", Font.PLAIN, 24));
        g2d.setColor(Color.WHITE);

        String curChar;
        int pixelLength;
        for(int i = 1; i < 9; i++)
        {
            // Draw the letters along the bottom edge
            curChar = "";
            curChar += (char)(i + 96);
            pixelLength = g2d.getFontMetrics().stringWidth(curChar); // the number of pixels the string is long
            g2d.drawString(curChar, (2*i-1)*this.squareWidth/2 - pixelLength/2, this.boardSize + 16);

            // Draw the numbers on the right edge
            curChar = Integer.toString(i);
            pixelLength = g2d.getFontMetrics().stringWidth(curChar); // the number of pixels the string is long
            g2d.drawString(curChar, this.boardSize + pixelLength/2,
                    this.boardSize - (2*i-1)*this.squareWidth/2 + 8);
        }
    }

    public void drawStatusStrings(Graphics2D g2d)
    {
        String curString;
        int pixelLength;
        ArrayList<String> stringPieces;

        // Print the last turn with bigger font
        stringPieces = splitString(this.manager.getLastTurn(), 20);
        g2d.setFont(new Font("Courier", Font.PLAIN, 22));
        for(int i = 0; i < stringPieces.size(); i++)
        {
            curString = stringPieces.get(i);
            pixelLength =  g2d.getFontMetrics().stringWidth(curString);
            g2d.drawString(curString, this.boardSize +  (this.manager.getWindowWidth()  - this.boardSize)/2  - pixelLength/2,
                    this.manager.getWindowHeight() / 3 + i*20);
        }


        // Print two turns ago, but smaller
        stringPieces = splitString(this.manager.getTwoTurnsAgo(), 20);
        g2d.setFont(new Font("Courier", Font.PLAIN, 16));
        for(int i = 0; i < stringPieces.size(); i++)
        {
            curString = stringPieces.get(i);
            pixelLength =  g2d.getFontMetrics().stringWidth(curString);
            g2d.drawString(curString, this.boardSize +  (this.manager.getWindowWidth()  - this.boardSize)/2  - pixelLength/2,
                    2*this.manager.getWindowHeight() / 3 + i*20);
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
        this.pieces[3][7] = new Queen(Team.White, this, coordsToLocation(3, 7));

        // Kings
        this.pieces[4][0] = new King(Team.Black, this, coordsToLocation(4, 0));
        this.pieces[4][7] = new King(Team.White, this, coordsToLocation(4, 7));

        // Add the pieces to their teams
        for(int i = 0; i < 8; i++)
        {
            blackPieces.add(pieces[i][0]);
            blackPieces.add(pieces[i][1]);
            whitePieces.add(pieces[i][6]);
            whitePieces.add(pieces[i][7]);
        }
    }

    // Make the renderableBoard agree with pieces, the actual board
    public void updateRenderableBoard()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                this.renderableBoard[i][j] = this.pieces[i][j];
            }
        }
    }

    // ====================================
    //
    //             Getters
    //
    // ====================================
    public GameManager getManager()
    {
        return this.manager;
    }
    public int getBoardSize()
    {
        return this.boardSize;
    }
    public Piece[][] getPieces()
    {
        return this.pieces;
    }
    public ArrayList<Piece> getWhitePieces()
    {
        return this.whitePieces;
    }
    public ArrayList<Piece> getBlackPieces()
    {
        return this.blackPieces;
    }
    public Piece getSelectedPiece()
    {
        return this.pieces[selectedXCoord][selectedYCoord];
    }
    public int getSelectedXCoord()
    {
        return this.selectedXCoord;
    }
    public int getSelectedYCoord()
    {
        return this.selectedYCoord;
    }
    public boolean isUpdating()
    {
        return this.isUpdating;
    }
    public Piece getPieceAtLocation(int loc)
    {
        int x = locationToCoords(loc)[0];
        int y = locationToCoords(loc)[1];
        return this.pieces[x][y];
    }

    public void setPieceAtLocation(Piece p, int loc)
    {
        int x = locationToCoords(loc)[0];
        int y = locationToCoords(loc)[1];
        this.pieces[x][y] = p;
    }

    public Team getOtherTeam(Team team)
    {
        if(team == Team.White)
        {
            return Team.Black;
        }
        else
        {
            return Team.White;
        }
    }


    // Given a mouse click happened on the board, return the square
    // that was clicked
    public int getLocationFromCoordinates(int mx, int my)
    {
        int x = mx / this.squareWidth;
        int y = my / this.squareWidth;
        return coordsToLocation(x, y);
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

    // ==================================
    //
    //       Functions to help
    //      with game management
    //
    // ==================================
    public boolean containsTeammate(Team team, int loc)
    {
        int x = locationToCoords(loc)[0];
        int y = locationToCoords(loc)[1];
        if(team == Team.White)
        {
            return this.whitePieces.contains(pieces[x][y]);
        }
        else
        {
            return this.blackPieces.contains(pieces[x][y]);
        }
    }
    public boolean containsTeammate(Team team, int x, int y)
    {
        if(team == Team.White)
        {
            return this.whitePieces.contains(pieces[x][y]);
        }
        else
        {
            return this.blackPieces.contains(pieces[x][y]);
        }
    }
    public boolean containsEnemy(Team team, int loc)
    {
        int x = locationToCoords(loc)[0];
        int y = locationToCoords(loc)[1];
        if(team == Team.Black)
        {
            return this.whitePieces.contains(pieces[x][y]);
        }
        else
        {
            return this.blackPieces.contains(pieces[x][y]);
        }
    }
    public boolean containsEnemy(Team team, int x, int y)
    {
        if(team == Team.Black)
        {
            return this.whitePieces.contains(pieces[x][y]);
        }
        else
        {
            return this.blackPieces.contains(pieces[x][y]);
        }
    }
    // If the piece can move to the given location on the board
    public boolean canMove(Piece p, int loc)
    {
        //return p.getMovableLocationsIgnoringCheck().contains(loc);
        return p.getMovableLocations().contains(loc);
    }
    public boolean canMove(Piece p, int x, int y)
    {
        //return p.getMovableLocationsIgnoringCheck().contains(coordsToLocation(x, y));
        return p.getMovableLocations().contains(coordsToLocation(x, y));
    }

    public String move(Piece p, int loc, boolean recordMove) throws IOException
    {
        this.isUpdating = true;

        String statusString = "";

        // Things for the Move object we will add to the manager's list of Moves
        boolean captured = false;
        boolean checked = false;

        // If we shouldn't be allowed to move there, raise an exception
        if(this.containsTeammate(p.getTeam(), loc))
        {
            throw new RuntimeException("Tried to move a Piece where a teammate is");
        }
        int x = locationToCoords(loc)[0];
        int y = locationToCoords(loc)[1];

        // If we are capturing an opponent
        if(this.containsEnemy(p.getTeam(), loc))
        {
            captured = true;

            statusString += this.manager.teamToStringC(p.getTeam()) + " has moved and captured on "
                    + locationToString(loc) + ".\n";
            if(p.getTeam() == Team.White)
            {
                blackPieces.remove(pieces[x][y]);
            }
            else
            {
                whitePieces.remove(pieces[x][y]);
            }
        }
        else
        {
            statusString += this.manager.teamToStringC(p.getTeam()) + " has moved.\n";
        }

        this.pieces[x][y] = p;

        int prevLoc = p.getLocation();
        int prevX = locationToCoords(prevLoc)[0];
        int prevY = locationToCoords(prevLoc)[1];
        this.pieces[prevX][prevY] = null;

        p.setLocation(loc);
        // check for promotion
        if(p.getPieceType() == PieceType.Pawn && (y == 0 || y == 7))
        {
            // Replace the pawn with a queen if it has reached the end
            this.pieces[x][y] = new Queen(p.getTeam(), this, loc);

            // Remove the pawn
            if(p.getTeam() == Team.White)
            {
                this.whitePieces.remove(p);
                this.whitePieces.add(pieces[x][y]);
            }
            else
            {
                this.blackPieces.remove(p);
                this.blackPieces.add(pieces[x][y]);
            }
        }

        this.updateRenderableBoard();
        this.updatePieces();

        // Print check message
        if(this.isInCheck(this.getOtherTeam(p.getTeam()), this.pieces))
        {
            checked = true;

            statusString += this.getCheckDirections(this.getOtherTeam(p.getTeam()), this.pieces) + "\n";
        }

        this.isUpdating = false;

        // If we are recording the move, write it down for the manager
        if(recordMove)
        {
            this.manager.getMoves().add(new Move(p.getPieceType(), prevX, prevY, x, y, captured, checked));
        }


        return statusString;
    }

    /*
    Function: isInCheck()
    Requires: A Team (White or Black) and a 2D Piece array representing a
              hypothetical game position.  The array could be the current
              board to determine if the king is in check or checkmate, or
              a determining if a possible move is legal
    Modifies: N/A
    Effects: Returns true if an opponent can attack the king in the given
             board, false otherwise
     */
    public boolean isInCheck(Team team, Piece[][] board)
    {
        return this.getCheckingPieces(team, board).size() > 0;
    }

    public int getKingLoc(Team team, Piece[][] board)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(board[i][j] != null &&
                        board[i][j].getTeam() == team && board[i][j].getPieceType() == PieceType.King)
                {
                    return coordsToLocation(i, j);
                }
            }
        }
        return -1;
    }

    public ArrayList<Piece> getCheckingPieces(Team team, Piece[][] board)
    {
        ArrayList<Piece> checkingPieces = new ArrayList<Piece>();
        // Find the relevant king's location
        int kingLoc = this.getKingLoc(team, board);

        // Check if any opponent can attack it
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(board[i][j] != null && board[i][j].getTeam() != team)
                {
                    if(board[i][j].getAttackableNonTeammateLocations().contains(kingLoc))
                    {
                        checkingPieces.add(board[i][j]);
                    }
                }
            }
        }
        return checkingPieces;
    }

    public String getCheckDirections(Team team, Piece[][] board)
    {
        // Get all pieces checking the king
        ArrayList<Piece> checkingPieces = this.getCheckingPieces(team, board);

        // where the king is
        int kingLoc = this.getKingLoc(team, board);
        int kingX = this.locationToCoords(kingLoc)[0];
        int kingY = this.locationToCoords(kingLoc)[1];

        // If we made a mistake and called this function when the king is not in check
        if(checkingPieces.size() == 0)
        {
            return "The " + this.manager.teamToString(team) + " king is not in check.";
        }
        String outputString = "The " + this.manager.teamToString(team) + " king is in check from ";

        // The king is possibly in check from 2 different sources
        for(int i = 0; i < checkingPieces.size(); i++)
        {
            if(i == 1)
            {
                outputString += " and from ";
            }
            int otherLoc = checkingPieces.get(i).getLocation();

            // Figure out where the check is coming from
            if(checkingPieces.get(i).getPieceType() == PieceType.Knight)
            {
                outputString += "a knight";
            }
            else if(checkingPieces.get(i).getX() == kingX)
            {
                outputString += "the file";
            }
            else if(checkingPieces.get(i).getY() == kingY)
            {
                outputString += "the rank";
            }

            // If we got to here, it's a diagonal

            // If the king is in the top left or bottom right quadrant
            else if((kingX < 4 && kingY < 4) || (kingX > 3 && kingY > 3))
            {
                if((kingLoc % 11) == (otherLoc % 11))
                {
                    outputString += "the long diagonal";
                }
                else
                {
                    outputString += "the short diagonal";
                }
            }
            else
            {
                if((kingLoc % 7) == (otherLoc % 7))
                {
                    outputString += "the long diagonal";
                }
                else
                {
                    outputString += "the short diagonal";
                }
            }
        }
        return outputString + ".";
    }

    public void setBoardLocation(Piece[][] board, Piece p, int loc)
    {
        int x = locationToCoords(loc)[0];
        int y = locationToCoords(loc)[1];
        board[x][y] = p;
    }

    // This makes all of the pieces update their ArrayLists for locations
    public void updatePieces()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(pieces[i][j] != null)
                {
                    pieces[i][j].update();
                }
            }
        }
    }

    // This makes all of the pieces update their ArrayLists for locations
    public void updatePiecesForCheck()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(pieces[i][j] != null)
                {
                    pieces[i][j].updateForCheck();
                }
            }
        }
    }

    // Returns a copied version of this board with p moved to loc
    public Piece[][] copyBoardMove(Piece p, int loc)
    {
        Piece[][] copiedBoard = new Piece[8][8];
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                copiedBoard[i][j] = this.pieces[i][j];
            }
        }
        int prevLoc = p.getLocation();
        this.setBoardLocation(copiedBoard, p, loc);
        p.setLocation(loc);
        this.setBoardLocation(copiedBoard, null, prevLoc);
        return copiedBoard;
    }

    // Returns true if moving p to loc would put the team in check
    /*public boolean wouldPutSelfInCheck(Team team, Piece p, int loc)
    {
        int prevLoc = p.getLocation(); // store this to move p back when finished

        Piece[][] copiedBoard = this.copyBoardMove(p, loc);
        boolean isIllegal = this.isInCheck(team, copiedBoard);

        p.setLocation(prevLoc); // Move p back to where it was
        return isIllegal;
    }*/

    public boolean wouldPutSelfInCheck(Team team, Piece p, int loc)
    {
        // Store previous data to return everything when done
        int prevLoc = p.getLocation();
        Piece prevPiece = this.getPieceAtLocation(loc);

        // Move the piece
        this.setPieceAtLocation(p, loc);
        p.setLocation(loc);
        this.setPieceAtLocation(null, prevLoc);
        this.updatePiecesForCheck();

        // Look for check
        boolean isIllegal = this.isInCheck(team, this.pieces);

        // Put the pieces back
        this.setPieceAtLocation(p, prevLoc);
        p.setLocation(prevLoc);
        this.setPieceAtLocation(prevPiece, loc);
        this.updatePiecesForCheck();

        return isIllegal;
    }

    public String coordsToString(int x, int y)
    {
        String s = "";
        s += (char)(x+97);
        s += (8-y);
        return s;
    }
    public String locationToString(int loc)
    {
        return coordsToString(locationToCoords(loc)[0], locationToCoords(loc)[1]);
    }

    // Add new line characters to a string if it gets too long
    public ArrayList<String> splitString(String s, int maxLine)
    {
        ArrayList<String> strings = new ArrayList<String>();
        String curString = "";

        for(int i = 0; i < s.length(); i++)
        {
            curString += s.charAt(i);

            // When the string is too long, add it to the list when you reach a space
            if(curString.length() >= maxLine && s.charAt(i) == ' ')
            {
                strings.add(curString);
                curString = "";
            }
        }
        // If we didn't add the last string yet
        if(curString.length() > 0)
        {
            strings.add(curString);
        }
        return strings;
    }



    // For when the game is over and we are in replay mode, make the move,
    // no questions asked
    public String moveForReplay(Move m) throws IOException
    {
        // Don't write the move down
        System.out.println(m.toString());
        return this.move(this.pieces[m.x1][m.y1], coordsToLocation(m.x2, m.y2), false);
    }
}
