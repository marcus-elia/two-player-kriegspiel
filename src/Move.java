/*
This class is for recording the moves that occur during a game.
 */

public class Move
{
    public PieceType type;
    public int x1, y1, x2, y2;
    public boolean captured;
    public boolean checked;

    public Move(PieceType inputType, int inputX1, int inputY1, int inputX2, int inputY2,
                boolean inputCaptured, boolean inputChecked)
    {
        this.type = inputType;
        this.x1 = inputX1;
        this.y1 = inputY1;
        this.x2 = inputX2;
        this.y2 = inputY2;
        this.captured = inputCaptured;
        this.checked = inputChecked;
    }

    public String toString()
    {
        String s = "";
        s += this.pieceToChar(this.type);
        s += this.coordsToString(x1, y1);
        if(this.captured)
        {
            s += 'x';
        }
        else
        {
            s += '-';
        }
        s += this.coordsToString(x2, y2);
        if(this.checked)
        {
            s += '+';
        }
        return s;

    }

    // Helper function to turn a piece into the one letter that represents it
    public char pieceToChar(PieceType p)
    {
        switch(p)
        {
            case Pawn:
                return 'P';
            case Rook:
                return 'R';
            case Bishop:
                return 'B';
            case Knight:
                return 'N';
            case Queen:
                return 'Q';
            case King:
                return 'K';
            default:
                return 'X';
        }
    }

    // coordsToString(3,4) = "d4"
    public String coordsToString(int x, int y)
    {
        return (char)(x + 97) + Integer.toString(y);
    }
}
