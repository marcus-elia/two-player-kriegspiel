import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener
{

    private GameManager manager;

    public MouseInput(GameManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();

        // If the click is on the board
        if(mx <= manager.getBoard().getBoardSize() && my <= manager.getBoard().getBoardSize())
        {
            if(!manager.getIsPieceSelected())
            {
                manager.selectPiece(mx, my);
            }
            else
            {
                manager.unselectPiece();
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
