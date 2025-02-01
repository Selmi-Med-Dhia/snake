import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Window {
    int gameWidth=515, gameHeight=870;
    int sideLength = 20;
    int rows = (gameHeight -70)/sideLength;
    int cols = (gameWidth - 15)/sideLength;
    int headIX, headIY, delay;
    ArrayList<SnakeBlock> snake;
    ArrayList<WallBlock> blocks;
    WallBlock apple;
    int welcomeWidth=500, welcomeHeight=350;
    JFrame frame = null;
    JPanel mainPanel, gamePanel;
    JLayeredPane layeredPane;
    JLabel titleLabel, hitEnterLabel, scoreLabel, countLabel;
    ScrollSelector difficulties;
    FlatButton playButton, backButton, retryButton;
    int state, offset = 3;
    int timerSteps, deathTimerSteps;
    boolean playButtonPressed = false, playButtonClicked = false, turnLock = false;
    Timer snakeTimer = null, countDownTimer = null, deathTimer = null;
    Random rand = new Random();
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
                }else if (e.getKeyCode() == 27){
                    System.exit(1);
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
        if (snakeTimer != null && snakeTimer.isRunning()){
            snakeTimer.stop();
        }
        if (countDownTimer != null && countDownTimer.isRunning()){
            countDownTimer.stop();
        }
        if (deathTimer != null && deathTimer.isRunning()){
            deathTimer.stop();
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
        gamePanel.setLocation(0, 0);
        gamePanel.setLayout(null);

        snake = new ArrayList<SnakeBlock>();
        headIY = rows/2;
        headIX = cols/2;
        snake.add(new SnakeBlock(sideLength, 0, -1, headIX, headIY));
        snake.add(new SnakeBlock(sideLength, 0, -1, headIX, headIY+1));
        snake.add(new SnakeBlock(sideLength, 0, -1, headIX, headIY+2));

        for(SnakeBlock block : snake){
            gamePanel.add(block);
        }
        blocks = new ArrayList<WallBlock>();
        generateBlocks();
        for(WallBlock block : blocks){
            gamePanel.add(block);
        }

        spawnApple();
        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 37){
                    //left
                    if (snake.get(0).getDirX() != 1 && !turnLock){
                        snake.get(0).setDirX(-1);
                        snake.get(0).setDirY(0);
                        turnLock = true;
                    }
                }else if (e.getKeyCode() == 38){
                    //up
                    if (snake.get(0).getDirY() != 1 && !turnLock){
                        snake.get(0).setDirX(0);
                        snake.get(0).setDirY(-1);
                        turnLock = true;
                    }
                }else if(e.getKeyCode() == 39){
                    //right
                    if (snake.get(0).getDirX() != -1 && !turnLock){
                        snake.get(0).setDirX(1);
                        snake.get(0).setDirY(0);
                        turnLock = true;
                    }
                }else if (e.getKeyCode() == 40){
                    //down
                    if (snake.get(0).getDirY() != -1 && !turnLock){
                        snake.get(0).setDirX(0);
                        snake.get(0).setDirY(1);
                        turnLock = true;
                    }
                }else if(e.getKeyCode() == 8){
                    welcomeScreen();
                }else if (e.getKeyCode() == 82){
                    gameScreen();
                }else if (e.getKeyCode() == 27){
                    System.exit(1);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });

        countLabel = new JLabel("3");
        countLabel.setSize(300, 300);
        countLabel.setFont(new Font("Courier New", Font.BOLD, 20));
        countLabel.setLocation( (gamePanel.getWidth() - countLabel.getWidth())/2, (gamePanel.getHeight() - countLabel.getHeight())/2 );
        countLabel.setForeground(new Color(255,255,255));
        countLabel.setHorizontalAlignment(JLabel.CENTER);
        countLabel.setVerticalAlignment(JLabel.CENTER);
        
        layeredPane = new JLayeredPane();
        layeredPane.setSize(new Dimension(gameWidth - 15 , gameHeight - scoreLabel.getHeight() + scoreLabel.getY() - 50));
        layeredPane.setLocation(5, scoreLabel.getHeight() + scoreLabel.getY());

        timerSteps = 0;
        countDownTimer = new Timer(50, x -> countDown(x));

        delay = 70 - 20*difficulties.getSelectedIndex();

        snakeTimer = new Timer(delay, x -> snakeForward(x));

        layeredPane.add(gamePanel, Integer.valueOf(1));
        layeredPane.add(countLabel, Integer.valueOf(2));

        mainPanel.add(layeredPane);
        mainPanel.add(retryButton);
        mainPanel.add(scoreLabel);
        mainPanel.add(backButton);
        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        countDownTimer.start();
    }
    public void countDown(ActionEvent e){
        timerSteps++;

        countLabel.setFont(new Font("Courier New", Font.BOLD, 20 + (timerSteps % 20)*15 ));
        countLabel.setForeground(new Color(255,255,255, 255 - (timerSteps % 20)*12));

        if (timerSteps ==20){
            countLabel.setFont(new Font("Courier New", Font.BOLD, 20));
            countLabel.setForeground(new Color(255,255,255));
            countLabel.setText("2");
        }else if (timerSteps ==40){
            countLabel.setFont(new Font("Courier New", Font.BOLD, 20));
            countLabel.setForeground(new Color(255,255,255));
            countLabel.setText("1");
        }else if (timerSteps == 60){
            ((Timer)(e.getSource())).stop();
            countLabel.setVisible(false);
            snakeTimer.start();
        }
    }
    public void snakeForward(ActionEvent e){
        if (!checkCollision()){
            snake.get(0).advance();
            headIX += snake.get(0).getDirX();
            headIY += snake.get(0).getDirY();
            for(int i=1; i<snake.size(); i++){
                snake.get(i).advance();
            }
            for(int i=snake.size()-1; i>0; i--){
                snake.get(i).setDirX(snake.get(i-1).getDirX());
                snake.get(i).setDirY(snake.get(i-1).getDirY());
            }
        }else{
            snakeTimer.stop();
            death();
        }
        turnLock = false;
        if ((headIX == apple.indexX) && (headIY == apple.indexY)){
            apple.setVisible(false);
            scoreLabel.setText( String.valueOf(Integer.valueOf(scoreLabel.getText()).intValue() + 1) );
            SnakeBlock last = snake.get(snake.size() -1);
            snake.add(new SnakeBlock(sideLength, last.getDirX(), last.getDirY(), last.indexX - last.getDirX(), last.indexY - last.getDirY() ));
            gamePanel.add(snake.get(snake.size() -1));
            spawnApple();
        }
        frame.revalidate();
        frame.repaint();
    }
    public boolean checkCollision(){
        int futurHeadIX = snake.get(0).getDirX() + headIX ;
        int futurHeadIY = snake.get(0).getDirY() + headIY ;
        if (futurHeadIX == -1 || futurHeadIX == cols){
            return(true);
        }else if (futurHeadIY == -1 || futurHeadIY == rows){
            return(true);
        }
        for(WallBlock block: blocks){
            if ( (futurHeadIX == block.indexX) && (futurHeadIY == block.indexY) ){
                return(true);
            }
        }
        for(SnakeBlock block : snake){
            if ( (futurHeadIX == block.indexX) && (futurHeadIY == block.indexY)){
                return(true);
            }
        }
        return(false);
    }
    public void generateBlocks(){
        int x = rand.nextInt(cols-10)+5;
        int y = rand.nextInt(rows/5)+1;
        drawShape(x, y, rand.nextInt(6));
        if(difficulties.getSelectedIndex() > 0){
            x = rand.nextInt(cols-10)+5;
            y = rand.nextInt(rows/6) + ((rows*5)/6) -4;
            drawShape(x, y, rand.nextInt(6));
        }
        if(difficulties.getSelectedIndex() >1){
            x = rand.nextInt(cols/4-1) +1;
            y = rand.nextInt(rows-2)+1;
            if (rand.nextInt(2) == 1){
                x = rand.nextInt(cols/4-4)+ (cols/4)*3 -4;
            }
            drawShape(x, y, rand.nextInt(6));
        }
    }
    public void drawShape(int x, int y, int shape){
        int x1=0, x2=0, x3=0, y1=0, y2=0, y3=0;
        if(shape == 0){ //vertical line
            x1 = x; y1 = y+1;
            x2 = x; y2 = y+2;
            x3 = x; y3 = y+3;
        }else if(shape == 1){ //horizontal line
            x1 = x+1; y1 = y;
            x2 = x+2; y2 = y;
            x3 = x+3; y3 = y;
        }else if(shape == 2){ //vertical S
            x1 = x; y1 = y+1;
            x2 = x+1; y2 = y+1;
            x3 = x; y3 = y+2;
        }else if(shape == 3){ //horizontal S
            x1 = x+1; y1 = y;
            x2 = x+1; y2 = y+1;
            x3 = x+2; y3 = y+1;
        }else if(shape == 4){ //vertical pyramid
            x1 = x; y1 = y+1;
            x2 = x+1; y2 = y+1;
            x3 = x; y3 = y+2;
        }else if(shape == 5){ //horizontal pyramid
            x1 = x+1; y1 = y;
            x2 = x+1; y2 = y+1;
            x3 = x+2; y3 = y;
        }
        blocks.add(new WallBlock(sideLength, x, y));
        blocks.add(new WallBlock(sideLength, x1, y1));
        blocks.add(new WallBlock(sideLength, x2, y2));
        blocks.add(new WallBlock(sideLength, x3, y3));
    }
    public int spawnApple(){
        int x = rand.nextInt(cols);
        int y = rand.nextInt(rows);
        for(WallBlock block : blocks){
            if ( (x == block.indexX) && (y == block.indexY) ){
                spawnApple();
                return (0);
            }
        }
        for(SnakeBlock block : snake){
            if ( (x == block.indexX) && (y == block.indexY) ){
                spawnApple();
                return (0);
            }
        }
        apple = new WallBlock(sideLength, x, y);
        apple.setBackground(new Color(255, 153, 51));
        gamePanel.add(apple);
        return(1);
    }
    public void death(){
        deathTimerSteps = 0;
        deathTimer = new Timer(50,x -> shrink(x) );
        deathTimer.start();
    }
    public void shrink(ActionEvent e){
        if (deathTimerSteps != snake.size()){
            deathTimerSteps++;
        }
        for(int i=0; i < deathTimerSteps ;i++){
            if (snake.get(i).borderThickness != 10){
                snake.get(i).borderThickness++;
                snake.get(i).setBorder(BorderFactory.createLineBorder(new Color(15, 20, 20), snake.get(i).borderThickness));
            }
        }
        if (snake.get(snake.size()-1).borderThickness == 10){
            deathTimer.stop();
        }
    }
}
