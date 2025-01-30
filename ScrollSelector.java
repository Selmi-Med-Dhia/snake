import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScrollSelector extends JPanel implements MouseWheelListener{
    ArrayList<String> options;
    int selectedIndex;
    JLabel label1, label2, label3, tmp;
    public ScrollSelector(ArrayList<String> options, Dimension dimension){
        this.setSize(dimension);
        this.options = options;
        selectedIndex = (options.size()-1)/2;
        this.setLayout(null);
        this.setBackground(new Color(255,255,255, 0));
        setup();
        this.addMouseWheelListener(this);
    }
    public void setup(){
        this.removeAll();
        if (selectedIndex >0){
            label1 = new JLabel(options.get(selectedIndex -1));
            label1.setForeground(new Color(255,255,255,100));
            label1.setFont(new Font("Courier New", Font.BOLD, 20));
            label1.setSize(new Dimension(200, 40));
            label1.setHorizontalAlignment(JLabel.CENTER);
            label1.setLocation( (this.getWidth() - label1.getWidth())/2, 0 );
            this.add(label1);
        }
        label2 = new JLabel(options.get(selectedIndex));
        label2.setForeground(new Color(255,255,255));
        label2.setFont(new Font("Courier New", Font.BOLD, 35));
        label2.setSize(new Dimension(200, 40));
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setLocation( (this.getWidth() - label2.getWidth())/2, (this.getHeight() - label2.getHeight())/2 );
        this.add(label2);
        if (selectedIndex < options.size() -1){
            label3 = new JLabel(options.get(selectedIndex +1));
            label3.setForeground(new Color(255,255,255,100));
            label3.setFont(new Font("Courier New", Font.BOLD, 20));
            label3.setSize(new Dimension(200, 40));
            label3.setLocation( (this.getWidth() - label3.getWidth())/2, this.getHeight() - label3.getHeight() );
            label3.setHorizontalAlignment(JLabel.CENTER);
            this.add(label3);
        }
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        if (e.getPreciseWheelRotation() == 1 && selectedIndex >0){
            selectedIndex--;
            setup();
            this.getParent().revalidate();
            this.getParent().repaint();
        }else if (e.getPreciseWheelRotation() == -1 && selectedIndex < options.size()-1){
            selectedIndex++;
            setup();
            this.getParent().revalidate();
            this.getParent().repaint();
        }
    }
}
