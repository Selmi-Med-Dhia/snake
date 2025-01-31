import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;

public class FlatButton extends JButton{
    public FlatButton(){
        super();
        this.setForeground(new Color(255,255,255));
        this.setBackground(new Color(5,10,10));
        this.setHorizontalAlignment(JButton.CENTER);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        this.setFocusable(false);
    }
    FlatButton ref = this;
    @Override
    public void setModel(ButtonModel newModel){
        super.setModel(new DefaultButtonModel(){
            @Override
            public void setPressed(boolean p){
            }
            @Override
            public void setArmed(boolean p){
            }

        });
    }
}
