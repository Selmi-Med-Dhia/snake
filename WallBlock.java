import java.awt.Dimension;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;


public class WallBlock extends JLabel{
    int indexX, indexY;
    int side;
    public WallBlock(int side, int indexX, int indexY){
        this.setSize(new Dimension(side, side));
        this.setBackground(new Color(150, 30, 30));
        this.setBorder(BorderFactory.createLineBorder(new Color(15, 20, 20), 1));
        this.setOpaque(true);
        this.setLocation(indexX * side, indexY * side);
        this.side = side;
        this.indexX = indexX;
        this.indexY = indexY;
    }
}
