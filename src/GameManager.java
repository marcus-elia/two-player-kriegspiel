import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameManager
{
    private ChessBoard board;
    private int windowWidth;
    private int windowHeight;

    // Game management
    private GameStatus currentStatus;
    private boolean isPieceSelected; // does the user have a piece selected
    private Team whoseTurn;
    private boolean gameIsActive;
    private boolean isBetweenTurns;

    private String whiteName;
    private String blackName;

    // Keep track of the info strings from the last two turns to display them
    private String lastTurn;
    private String twoTurnsAgo;

    private ArrayList<Move> moves;
    private int replayMove;

    // The buttons for changing colors
    private Button redButton;
    private Button greenButton;
    private Button blueButton;
    private Button purpleButton;
    private ArrayList<Button> colorButtons;

    public GameManager(int width, int height) throws IOException
    {
        this.whiteName = JOptionPane.showInputDialog("Enter the name of the player controlling the white pieces: ");
        this.blackName = JOptionPane.showInputDialog("Enter the name of the player controlling the black pieces: ");
        this.setNames();

        this.windowWidth = width;
        this.windowHeight = height;
        this.board = new ChessBoard(this);

        this.currentStatus = GameStatus.Game;
        this.isPieceSelected = false;
        this.whoseTurn = Team.White;
        this.gameIsActive = true;
        this.isBetweenTurns = false;

        this.lastTurn = "";
        this.twoTurnsAgo = "";

        this.moves = new ArrayList<Move>();
        this.replayMove = 0;

        this.initializeButtons();

        JOptionPane.showMessageDialog(null, "It is " + this.whiteName + "'s turn. Click OK when " +
                this.blackName + " has left.");
    }

    public void initializeButtons()
    {
        int availableWidth = this.windowWidth - this.board.getBoardSize();
        int buttonSize = availableWidth / 9;
        this.redButton = new Button(this.board.getBoardSize() + buttonSize, 10,
                buttonSize, buttonSize, new Color(255,0,0), ColorScheme.Red);
        this.greenButton = new Button(this.board.getBoardSize() + 3*buttonSize, 10,
                buttonSize, buttonSize, new Color(0,255,0), ColorScheme.Green);
        this.blueButton = new Button(this.board.getBoardSize() + 5*buttonSize, 10,
                buttonSize, buttonSize, new Color(0,0,255), ColorScheme.Blue);
        this.purpleButton = new Button(this.board.getBoardSize() + 7*buttonSize, 10,
                buttonSize, buttonSize, new Color(120,0,150), ColorScheme.Purple);

        colorButtons = new ArrayList<Button>();
        colorButtons.add(redButton);
        colorButtons.add(greenButton);
        colorButtons.add(blueButton);
        colorButtons.add(purpleButton);
    }

    public void tick()
    {

    }

    public void render(Graphics2D g2d)
    {
        this.board.render(g2d);

        if(this.currentStatus != GameStatus.PreGame)
        {
            this.drawButtons(g2d);
        }
    }

    public void drawButtons(Graphics2D g2d)
    {
        this.redButton.render(g2d);
        this.greenButton.render(g2d);
        this.blueButton.render(g2d);
        this.purpleButton.render(g2d);
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
    public Team getNotWhoseTurn()
    {
        if(this.whoseTurn == Team.White)
        {
            return Team.Black;
        }
        else
        {
            return Team.White;
        }
    }
    public boolean getIsBetweenTurns()
    {
        return this.isBetweenTurns;
    }
    public String getLastTurn()
    {
        return this.lastTurn;
    }
    public String getTwoTurnsAgo()
    {
        return this.twoTurnsAgo;
    }
    public GameStatus getCurrentStatus()
    {
        return this.currentStatus;
    }
    public ArrayList<Move> getMoves()
    {
        return this.moves;
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

    // Convert the Team enum type into its corresponding string
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

    // The capitalized version
    public String teamToStringC(Team t)
    {
        switch(t)
        {
            case Black :
                return "Black";
            case White :
                return "White";
            default :
                return "neutral";
        }
    }

    // If the users entered any blank names, put "Player 1" or "Player 2"
    public void setNames()
    {
        if(this.whiteName == null || this.whiteName.equals(""))
        {
            this.whiteName = "Player 1";
        }
        if(this.blackName == null || this.blackName.equals(""))
        {
            this.blackName = "Player 2";
        }
    }

    // Return the name of the player whose turn it is
    public String curPlayersName()
    {
        if(this.whoseTurn == Team.White)
        {
            return this.whiteName;
        }
        else
        {
            return this.blackName;
        }
    }
    // Return Team.White if it's black's turn and vice versa
    public String otherPlayersName()
    {
        if(this.whoseTurn == Team.White)
        {
            return this.blackName;
        }
        else
        {
            return this.whiteName;
        }
    }

    // ======================================
    //
    //          Reacting to Clicks
    //
    // ======================================

    public void reactToClick(int mx, int my) throws IOException
    {
        // Checking for a color change click
        for(Button b : this.colorButtons)
        {
            if(b.isInside(mx, my))
            {
                this.board.setColors(b.getColorScheme());
                return;
            }
        }
        // If we are in replayMode, move whenever there is a click
        if(this.currentStatus == GameStatus.Replay)
        {
            this.board.moveForReplay(this.moves.get(this.replayMove));
            this.replayMove++;
            this.switchTurn();

            // If we have replayed through the whole game
            if(this.replayMove == this.moves.size())
            {
                this.currentStatus = GameStatus.PostGame;
                this.replayMove = 0;
                this.gameEndChoices();
            }
            return;
        }

        // If we should not accept a click right now, just return
        if(this.board.isUpdating() || !this.gameIsActive)
        {
            return;
        }


        // Otherwise, handle the click
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
                        System.out.println("You do not have a piece there.");
                        JOptionPane.showMessageDialog(null, "You do not have a piece there.");
                    }
                }
                else
                {
                    System.out.println("You do not have a piece there.");
                    JOptionPane.showMessageDialog(null, "You do not have a piece there.");
                }
            }
            else
            {
                // If they click on the selected piece, unselect it
                if(loc == this.board.coordsToLocation(this.board.getSelectedXCoord(), this.board.getSelectedYCoord()))
                {
                    this.unselectPiece();
                }
                // If they click a valid move, make the move
                else if(this.board.canMove(this.board.getSelectedPiece(), loc))
                {
                    // Update the status strings summarizing the last two moves
                    this.twoTurnsAgo = "" + this.lastTurn;
                    this.lastTurn = this.board.move(this.board.getSelectedPiece(), loc, true);
                    System.out.println(this.lastTurn);

                    // Stop highlighting the piece, since it has moved
                    this.unselectPiece();

                    // Don't accept clicks, since a transition has to happen
                    this.isBetweenTurns = true;

                    // Check if the game is over
                    this.checkForGameEnd();

                    if(this.currentStatus == GameStatus.Game)
                    {
                        JOptionPane.showMessageDialog(null, "Your move is complete. " +
                                "Call " + this.otherPlayersName() + " over and press OK once your pieces are hidden.");

                        JOptionPane.showMessageDialog(null, "Welcome back, " +
                                this.otherPlayersName() + ". " +
                                "Press OK when " + this.curPlayersName() + " is gone.");
                        this.switchTurn();
                    }
                    this.isBetweenTurns = false;
                }
                else
                {
                    System.out.println("That is not a legal move.");
                    JOptionPane.showMessageDialog(null, "That is not a legal move. Try a " +
                            "different square, or click on the selected piece again to deselect it.");
                }
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
        if(this.getNotWhoseTurn() == Team.White)
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
        if(this.board.getWhitePieces().size() == 1 && this.board.getBlackPieces().size() == 1)
        {
            this.twoTurnsAgo = this.lastTurn;
            this.lastTurn = "The game is a draw.";
            return true;
        }
        return false;
    }

    public boolean checkForStalemate()
    {
        if(!this.checkCanMove() && !this.board.isInCheck(this.getNotWhoseTurn(), this.board.getPieces()))
        {
            this.twoTurnsAgo = this.lastTurn;
            this.lastTurn = "The game has ended in stalemate.";
            return true;
        }
        return false;
    }

    public boolean checkForCheckmate()
    {
        if(!this.checkCanMove() && this.board.isInCheck(this.getNotWhoseTurn(), this.board.getPieces()))
        {
            this.twoTurnsAgo = this.lastTurn;
            this.lastTurn = this.teamToStringC(this.getNotWhoseTurn()) + " is in checkmate. " +
                            this.curPlayersName() + " wins.";
            return true;
        }
        return false;
    }

    public boolean checkForGameEnd() throws IOException
    {
        if(checkForDraw() || checkForStalemate() || checkForCheckmate())
        {
            this.isBetweenTurns = false;
            this.currentStatus = GameStatus.PostGame;
            this.gameIsActive = false;
            this.gameEndChoices();
            return true;
        }
        return false;
    }

    public void gameEndChoices() throws IOException
    {
        String[] options = {"Replay the Game", "Quit"};
        int choice = JOptionPane.showOptionDialog(null, "What would you like to do next?",
                "Game over.",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if(choice == 1)
        {
            System.exit(0);
        }
        else if(choice == 0)
        {
            this.currentStatus = GameStatus.Replay;
            this.board = new ChessBoard(this);
            this.whoseTurn = Team.White;
            this.lastTurn = "Click anywhere to replay the next move.";
            this.twoTurnsAgo = "";
        }
    }
}
