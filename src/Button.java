import java.awt.*;

public class Button
{
    private int topLeftX;
    private int topLeftY;
    private int xWidth;
    private int yWidth;
    private Color color;
    private ColorScheme colorScheme;

    public Button(int topLeftX, int topLeftY, int xWidth, int yWidth, Color color, ColorScheme colorScheme)
    {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.color = color;
        this.colorScheme = colorScheme;
    }

    public void render(Graphics2D g2d)
    {
        g2d.setColor(this.color);
        g2d.fillRect(topLeftX, topLeftY, xWidth, yWidth);
    }

    public boolean isInside(int x, int y)
    {
        return x >= topLeftX && x <= topLeftX + xWidth && y >= topLeftY && y <= topLeftY + yWidth;
    }

    // Set the color to be the same color, but with half the alpha. This is intended
    // to be used when the button is being hovered over
    public void reduceAlpha()
    {
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.5f);
    }
    // Put the alpha back up to 1
    public void resetAlpha()
    {
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 1f);
    }

    public ColorScheme getColorScheme()
    {
        return this.colorScheme;
    }
}
