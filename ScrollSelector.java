import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;
public class ScrollSelector extends JPanel implements MouseWheelListener{
    ArrayList<String> options;
    int selectedIndex;
    JLabel label1, label2, label3;
    int label1Y, label2Y, label3Y;
    int label1F, label2F, label3F;
    int label1O, label2O, label3O;
    int steps, maxSteps = 8;
    boolean busy = false;
    float pixelStep, fontStep = 15.0f/maxSteps, opacityStep = 127.0f/maxSteps;
    public int getSelectedIndex(){
        return selectedIndex;
    }
    public ScrollSelector(ArrayList<String> options, Dimension dimension){
        this.setSize(dimension);
        this.options = options;
        selectedIndex = (options.size()-1)/2;
        this.setLayout(null);
        this.setBackground(new Color(255,255,255, 0));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (selectedIndex >0){
            label1 = new JLabel(options.get(selectedIndex -1));
            label1.setForeground(new Color(255,255,255,127));
            label1O = 127;
            label1.setFont(new Font("Courier New", Font.BOLD, 20));
            label1F = 20;
            label1.setSize(new Dimension(200, 40));
            label1.setHorizontalAlignment(JLabel.CENTER);
            label1.setLocation( (this.getWidth() - label1.getWidth())/2, 0 );
            label1Y = 0;
            this.add(label1);
        }
        label2 = new JLabel(options.get(selectedIndex));
        label2.setForeground(new Color(255,255,255));
        label2O = 255;
        label2.setFont(new Font("Courier New", Font.BOLD, 35));
        label2F = 35;
        label2.setSize(new Dimension(200, 40));
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setLocation( (this.getWidth() - label2.getWidth())/2, (this.getHeight() - label2.getHeight())/2 );
        label2Y = (this.getHeight() - label2.getHeight())/2;
        pixelStep = (float)label2.getY() / maxSteps;
        this.add(label2);
        if (selectedIndex < options.size() -1){
            label3 = new JLabel(options.get(selectedIndex +1));
            label3.setForeground(new Color(255,255,255,127));
            label3O = 127;
            label3.setFont(new Font("Courier New", Font.BOLD, 20));
            label3F = 20;
            label3.setSize(new Dimension(200, 40));
            label3.setHorizontalAlignment(JLabel.CENTER);
            label3.setLocation( (this.getWidth() - label3.getWidth())/2, this.getHeight() - label3.getHeight() );
            label3Y = this.getHeight() - label3.getHeight();
            this.add(label3);
        }
        this.addMouseWheelListener(this);
    }
    public void moveUp(ActionEvent x, int regul){
        this.label1.setLocation(this.label1.getX(), label1Y + (int)(steps * pixelStep));
        this.label1.setForeground(new Color(255,255,255,label1O + (int)(steps * opacityStep)));
        this.label1.setFont(new Font("Courier New", Font.BOLD, label1F + (int)(steps * fontStep) ));

        this.label2.setLocation(this.label2.getX(), label2Y + (int)(steps * pixelStep));
        this.label2.setForeground(new Color(255,255,255, label2O + regul * (int)(steps * opacityStep)));
        this.label2.setFont(new Font("Courier New", Font.BOLD, label2F + regul * (int)(steps * fontStep) ));

        this.label3.setLocation(this.label3.getX(), label3Y + (int)(steps * pixelStep));
        this.label3.setForeground(new Color(255,255,255, label3O - (int)(steps * opacityStep)));
        this.label3.setFont(new Font("Courier New", Font.BOLD, label3F - (int)(steps * fontStep) ));
        
        this.getParent().revalidate();
        this.getParent().repaint();

        steps++;
        if (steps==maxSteps){
            label1Y = label1.getY();
            label2Y = label2.getY();
            label3Y = label3.getY();
            label1O = label1O + (int)(steps * opacityStep);
            label2O = label2O + regul * (int)(steps * opacityStep);
            label3O = label3O - (int)(steps * opacityStep);
            label1F = label1F + (int)(steps * fontStep);
            label2F = label2F + regul * (int)(steps * fontStep);
            label3F = label3F - (int)(steps * fontStep);
            ((Timer)(x.getSource())).stop();
            selectedIndex--;
            busy = false;
        }
    }
    public void moveDown(ActionEvent x, int regul){
        this.label1.setLocation(this.label1.getX(), label1Y - (int)(steps * pixelStep));
        this.label1.setForeground(new Color(255,255,255,label1O- (int)(steps * opacityStep)));
        this.label1.setFont(new Font("Courier New", Font.BOLD, label1F - (int)(steps * fontStep) ));

        this.label2.setLocation(this.label2.getX(), label2Y - (int)(steps * pixelStep));
        this.label2.setForeground(new Color(255,255,255, label2O + regul *(int)(steps * opacityStep)));
        this.label2.setFont(new Font("Courier New", Font.BOLD, label2F + regul *(int)(steps * fontStep) ));

        this.label3.setLocation(this.label3.getX(), label3Y - (int)(steps * pixelStep));
        this.label3.setForeground(new Color(255,255,255, label3O + (int)(steps * opacityStep)));
        this.label3.setFont(new Font("Courier New", Font.BOLD, label3F + (int)(steps * fontStep) ));
        
        this.getParent().revalidate();
        this.getParent().repaint();

        steps++;
        if (steps==maxSteps){
            label1Y = label1.getY();
            label2Y = label2.getY();
            label3Y = label3.getY();
            label1O = label1O - (int)(steps * opacityStep);
            label2O = label2O + regul * (int)(steps * opacityStep);
            label3O = label3O + (int)(steps * opacityStep);
            label1F = label1F - (int)(steps * fontStep);
            label2F = label2F + regul * (int)(steps * fontStep);
            label3F = label3F + (int)(steps * fontStep);
            ((Timer)(x.getSource())).stop();
            selectedIndex++;
            busy = false;
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        int regul;
        if (selectedIndex == 1){
            regul = -1;
        }else{
            regul = 1;
        }
        if (e.getPreciseWheelRotation() < 0 && !busy && selectedIndex >0){
            steps = 0;
            Timer timer = new Timer(20,x -> moveUp(x, regul));
            busy = true;
            timer.start();
        }else if (e.getPreciseWheelRotation() >0 && !busy && selectedIndex < options.size()-1){
            steps = 0;
            Timer timer = new Timer(20,x -> moveDown(x, regul));
            busy = true;
            timer.start();
        }
    }
}
