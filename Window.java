import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

public class Window {
    int gameWidth=400, gameHeight=600;
    int welcomeWidth=500, welcomeHeight=350;
    JFrame frame;
    JPanel mainPanel;
    JLabel titleLabel, hitEnterLabel;
    ScrollSelector difficulties;
    FlatButton playButton;
    int state;
    final int WELCOME_SCREEN = 0;
    final int GAME_SCREEN = 1;
    final int GAME_OVER_SCREEN = 2;
    ArrayList<String> options;
    public void welcomeScreen(){

        this.state = WELCOME_SCREEN;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(welcomeWidth, welcomeHeight));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(15,20,20));

        titleLabel = new JLabel("Snake.");
        titleLabel.setForeground(new Color(255,255,255));
        titleLabel.setFont( new Font("Courier New", Font.BOLD, 50) );
        titleLabel.setBounds((welcomeWidth - 200) / 2, 40, 200, 50);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        playButton = new FlatButton();
        playButton.setText("Play");
        playButton.setFont(new Font("Courier New", Font.BOLD, 30));
        playButton.setSize(new Dimension(200, 50));
        playButton.setLocation((welcomeWidth - playButton.getWidth())/2, titleLabel.getY()+ + titleLabel.getHeight() + 20 );
        
        hitEnterLabel = new JLabel("(Hit enter to play)");
        hitEnterLabel.setForeground(new Color(255,255,255));
        hitEnterLabel.setFont( new Font("Courier New", Font.BOLD, 15) );
        hitEnterLabel.setBounds((welcomeWidth - 200) / 2, playButton.getY() + playButton.getHeight() + 5, 200, 20);
        hitEnterLabel.setHorizontalAlignment(JLabel.CENTER);

        options = new ArrayList<String>();
        options.add("Easy");
        options.add("Medium");
        options.add("Hard");

        difficulties = new ScrollSelector(options ,new Dimension(200, 100));
        difficulties.setLocation( (frame.getWidth() - difficulties.getWidth())/2, hitEnterLabel.getY() + hitEnterLabel.getHeight() + 20 );

        mainPanel.add(difficulties);
        mainPanel.add(hitEnterLabel);
        mainPanel.add(playButton);
        mainPanel.add(titleLabel);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
