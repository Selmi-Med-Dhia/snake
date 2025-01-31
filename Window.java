import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Window {
    int gameWidth=515, gameHeight=870;
    int sideLength = 20;
    int rows = (gameHeight -70)/sideLength;
    int cols = (gameWidth - 15)/sideLength;
    int headIX, headIY;
    ArrayList<SnakeBlock> snake;
    int welcomeWidth=500, welcomeHeight=350;
    JFrame frame = null;
    JPanel mainPanel, gamePanel;
    JLabel titleLabel, hitEnterLabel, scoreLabel;
    ScrollSelector difficulties;
    FlatButton playButton, backButton, retryButton;
    int state, offset = 3;;
    boolean playButtonPressed = false, playButtonClicked = false;
    final int WELCOME_SCREEN = 0;
    final int GAME_SCREEN = 1;
    final int GAME_OVER_SCREEN = 2;
    ArrayList<String> options;
    public Window(){
    }
    public void welcomeScreen(){
        if (frame != null){
            frame.dispose();
        }
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        this.state = WELCOME_SCREEN;
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

        playButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(!playButtonPressed && !playButtonClicked){
                    playButtonClicked = true;
                    playButton.setLocation(playButton.getX() + offset, playButton.getY() + offset);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(playButtonClicked){
                    playButtonClicked = false;
                    playButton.setLocation(playButton.getX() - offset, playButton.getY() - offset);
                    gameScreen();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //
            }
            
        });

        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                //
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10 && !playButtonPressed && !playButtonClicked){
                    playButtonPressed = true;
                    playButton.setLocation(playButton.getX() + offset, playButton.getY() + offset);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10 && playButtonPressed){
                    playButtonPressed = false;
                    playButton.setLocation(playButton.getX() - offset, playButton.getY() - offset);
                    gameScreen();
                }
            }
            
        });

        mainPanel.add(difficulties);
        mainPanel.add(hitEnterLabel);
        mainPanel.add(playButton);
        mainPanel.add(titleLabel);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
    public void gameScreen(){
        if (frame != null){
            frame.dispose();
        }
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(false);
        this.state = GAME_SCREEN;
        frame.setResizable(true);
        frame.setSize(new Dimension(gameWidth, gameHeight));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(15,20,20));

        backButton = new FlatButton();
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setText("..");
        backButton.setBounds(10,5,40,20);
        backButton.setHorizontalAlignment(FlatButton.LEFT);
        backButton.setFont(new Font("Courier New", Font.BOLD, 20));
        backButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                welcomeScreen();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        });

        scoreLabel = new JLabel("0");
        scoreLabel.setForeground(new Color(255,255,255));
        scoreLabel.setFont( new Font("Courier New", Font.BOLD, 25) );
        scoreLabel.setBounds(backButton.getX() + backButton.getWidth() + 5, 5, 100, 25);
        scoreLabel.setHorizontalAlignment(JLabel.LEFT);

        retryButton = new FlatButton();
        retryButton.setBorder(BorderFactory.createEmptyBorder());
        retryButton.setText("Retry");
        retryButton.setSize(new Dimension(80,20));
        retryButton.setLocation(gameWidth - retryButton.getWidth(), 5);
        retryButton.setHorizontalAlignment(FlatButton.LEFT);
        retryButton.setFont(new Font("Courier New", Font.BOLD, 20));
        retryButton.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                gameScreen();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        });

        gamePanel = new JPanel();
        gamePanel.setBackground(new Color(0,0,0,0));
        gamePanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1 ));
        gamePanel.setSize(new Dimension(gameWidth - 15 , gameHeight - scoreLabel.getHeight() + scoreLabel.getY() - 50));
        gamePanel.setLocation(5, scoreLabel.getHeight() + scoreLabel.getY());
        gamePanel.setLayout(null);

        snake = new ArrayList<SnakeBlock>();
        snake.add(new SnakeBlock(sideLength, 0, -1));
        snake.add(new SnakeBlock(sideLength, 0, -1));
        snake.add(new SnakeBlock(sideLength, 0, -1));
        headIY = rows/2;
        headIX = cols/2;
        snake.get(0).setLocation(headIX * sideLength, headIY * sideLength);
        snake.get(1).setLocation(headIX * sideLength, (headIY+1) * sideLength);
        snake.get(2).setLocation(headIX * sideLength, (headIY+2) * sideLength);

        for(SnakeBlock block : snake){
            gamePanel.add(block);
        }
        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 37){
                    //left
                    if (snake.get(0).getDirX() != 1){
                        snake.get(0).setDirX(-1);
                        snake.get(0).setDirY(0);
                    }
                }else if (e.getKeyCode() == 38){
                    //up
                    if (snake.get(0).getDirY() != 1){
                        snake.get(0).setDirX(0);
                        snake.get(0).setDirY(-1);
                    }
                }else if(e.getKeyCode() == 39){
                    //right
                    if (snake.get(0).getDirX() != -1){
                        snake.get(0).setDirX(1);
                        snake.get(0).setDirY(0);
                    }
                }else if (e.getKeyCode() == 40){
                    //down
                    if (snake.get(0).getDirY() != -1){
                        snake.get(0).setDirX(0);
                        snake.get(0).setDirY(1);
                    }
                }else if(e.getKeyCode() == 8){
                    welcomeScreen();
                }else if (e.getKeyCode() == 82){
                    gameScreen();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });

        mainPanel.add(gamePanel);
        mainPanel.add(retryButton);
        mainPanel.add(scoreLabel);
        mainPanel.add(backButton);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
