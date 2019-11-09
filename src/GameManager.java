import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameManager
{
    private ChessBoard board;
    private int windowWidth;
    private int windowHeight;
    private boolean isPieceSelected; // does the user have a piece selected
    private Team whoseTurn;
    private boolean gameIsActive;

    public GameManager(int width, int height) throws IOException
    {
        this.windowWidth = width;
        this.windowHeight = height;
        this.board = new ChessBoard(this);
        this.isPieceSelected = false;
        this.whoseTurn = Team.White;
        this.gameIsActive = true;
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
    public boolean getIsPieceSelected()
    {
        return this.isPieceSelected;
    }
    public Team getWhoseTurn()
    {
        return this.whoseTurn;
    }


    // =================================
    //
    //       Functions to Help with
    //          Printing Messages
    //
    // =================================
    public String pieceTypeToString(PieceType pt)
    {
        switch(pt)
        {
            case Pawn :
                return "Pawn";
            case Bishop :
                return "Bishop";
            case Rook :
                return "Rook";
            case Knight :
                return "Knight";
            case Queen :
                return "Queen";
            case King :
                return "King";
            default :
                return "Piece";
        }
    }

    public String teamToString(Team t)
    {
        switch(t)
        {
            case Black :
                return "black";
            case White :
                return "white";
            default :
                return "neutral";
        }
    }

    // ======================================
    //
    //          Reacting to Clicks
    //
    // ======================================

    public void reactToClick(int mx, int my) throws IOException {
        if(this.board.isUpdating() || !this.gameIsActive)
        {
            return;
        }

        // If the click is on the board
        if(mx <= this.board.getBoardSize() && my <= this.board.getBoardSize())
        {
            int loc = this.board.getLocationFromCoordinates(mx, my);

            // If no piece is selected
            if(!this.getIsPieceSelected())
            {
                // If the click is on a piece
                if(!this.board.isEmpty(loc))
                {
                    // If they click on their own piece, select it
                    if(this.board.containsTeammate(this.whoseTurn, loc))
                    {
                        this.selectPiece(mx, my);
                    }
                    else // If they click on the other team's piece
                    {
                        System.out.println("That is not your piece.");
                    }
                }
                else
                {
                    System.out.println("No piece there");
                }
            }
            else
            {
                // If they click on the selected piece, unselect it
                if(loc == this.board.coordsToLocation(this.board.getSelectedXCoord(), this.board.getSelectedYCoord()))
                {
                    this.unselectPiece();
                }
                else if(this.board.canMove(this.board.getSelectedPiece(), loc))
                {
                    this.board.move(this.board.getSelectedPiece(), loc);
                    this.unselectPiece();
                    this.switchTurn();
                }
                else
                {
                    System.out.println("That is not a legal move.");
                }
            }
        }

        // Check if the game is over
        if(this.checkForDraw())
        {
            System.out.println("The game is a draw.");
            this.gameIsActive = false;
        }
        if(this.checkForStalemate())
        {
            System.out.println("The game is a stalemate");
            this.gameIsActive = false;
        }
        if(this.checkForCheckmate())
        {
            System.out.println("Checkmate");
            this.gameIsActive = false;
        }
    }

    public void selectPiece(int mx, int my)
    {
        this.isPieceSelected = true;
        this.board.selectPiece(mx, my);
    }

    public void unselectPiece()
    {
        this.isPieceSelected = false;
    }

    public void switchTurn()
    {
        if(this.whoseTurn == Team.White)
        {
            this.whoseTurn = Team.Black;
        }
        else
        {
            this.whoseTurn = Team.White;
        }
    }

    // Return true if the team whose turn it is can move
    public boolean checkCanMove()
    {
        ArrayList<Piece> piecesToCheck;
        if(this.whoseTurn == Team.White)
        {
            piecesToCheck = this.board.getWhitePieces();
        }
        else
        {
            piecesToCheck = this.board.getBlackPieces();
        }

        // Check if the pieces can move
        for(Piece p : piecesToCheck)
        {
            if(p.canMove())
            {
                return true;
            }
        }
        return false;
    }

    // Return true if both team have only a king
    public boolean checkForDraw()
    {
        return this.board.getWhitePieces().size() == 1 && this.board.getBlackPieces().size() == 1;
    }

    public boolean checkForStalemate()
    {
        return !this.checkCanMove() && !this.board.isInCheck(this.whoseTurn, this.board.getPieces());
    }

    public boolean checkForCheckmate()
    {
        return !this.checkCanMove() && this.board.isInCheck(this.whoseTurn, this.board.getPieces());
    }
}
