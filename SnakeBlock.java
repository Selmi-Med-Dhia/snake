import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class SnakeBlock extends JLabel{
    int dirX, dirY;
    int indexX, indexY;
    int side;
    public SnakeBlock(int side, int dirX, int dirY, int indexX, int indexY){
        this.setSize(new Dimension(side, side));
        this.setBackground(new Color(0, 180, 0));
        this.setBorder(BorderFactory.createLineBorder(new Color(15, 20, 20), 1));
        this.setOpaque(true);
        this.side = side;
        this.dirX = dirX;
        this.dirY = dirY;
        this.indexX = indexX;
        this.indexY = indexY;
    }
    public void advance(){
        this.setLocation(this.getX() + dirX*side, this.getY() + dirY*side);
        this.indexX +=dirX;
        this.indexY +=dirY;
    }
    public int getDirX(){
        return dirX;
    }
    public void setDirX(int x){
        this.dirX = x;
    }
    public int getDirY(){
        return dirY;
    }
    public void setDirY(int y){
        this.dirY = y;
    }
}
