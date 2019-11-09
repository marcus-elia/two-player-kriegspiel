import java.awt.*;
import java.io.IOException;

public class GameManager
{
    private ChessBoard board;
    private int windowWidth;
    private int windowHeight;
    private boolean isPieceSelected; // does the user have a piece selected

    public GameManager(int width, int height) throws IOException
    {
        this.windowWidth = width;
        this.windowHeight = height;
        this.board = new ChessBoard(this);
        this.isPieceSelected = false;
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

    public void reactToClick(int mx, int my)
    {
        // If the click is on the board
        if(mx <= this.board.getBoardSize() && my <= this.board.getBoardSize())
        {
            if(!this.getIsPieceSelected())
            {
                this.selectPiece(mx, my);
            }
            else
            {
                this.unselectPiece();
            }
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
}
