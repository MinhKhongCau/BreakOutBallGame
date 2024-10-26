package com;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.Connection;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import DatabaseConfig.ConnectionConfig;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.sql.Statement;

public class Board extends JPanel implements Runnable,Login.StartGameListener {
    private Thread clock;
    private Paddle paddle;
    private Ball ball;
    private Brick[] brick;
    private int amount_brick = 0;
    private Player player;
    private Item item1;
    private Item item2;
    private int item_status = 0;
    private InfoPanel topPanel;
    private InfoPanel bottomPanel;
    private Login login;
    private int FPS = Commons.FPS;

    public Board() {
        initComponent();
        login = new Login();
        login.prepareGame(this);
        this.add(login);
    }

    private void initComponent() {
        paddle = new Paddle();
        ball = new Ball();
        item1 = null;
        item2 = null;
        brick = new Brick[Commons.BRICK_ROW * Commons.BRICK_COL];
        createBrick(brick);

        int panelWidth = Commons.SCREEN_WIDTH, panelHeight = Commons.SCREEN_HEIGHT;
        // init screen with scale 16/9
        this.setSize(panelWidth, panelHeight);
        this.setBackground(Commons.BACKGROUND_COLOR);
        this.setLayout(new BorderLayout());

        JLabel labelName = new JLabel("Name: Unknown");
        JLabel labelFPS = new JLabel(String.format("FPS: %d", Commons.FPS));

        topPanel = new InfoPanel(labelName, labelFPS);
        this.add(topPanel, BorderLayout.NORTH);

        JLabel labelScore = new JLabel(String.format("Score: %d", 0));
        JLabel labelLife = new JLabel(String.format("Life: %d", 0));

        bottomPanel = new InfoPanel(labelLife, labelScore);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void startGame(String playerName) {
        player = new Player(playerName, 0, 3);
        startedGame(player);
        this.remove(login);
    }

    private class InfoPanel extends JPanel {
        private JLabel leftLabel;
        private JLabel rightLabel;

        public InfoPanel(JLabel leftLabel, JLabel rightLabel) {
            this.leftLabel = leftLabel;
            this.rightLabel = rightLabel;

            // set UI label
            this.leftLabel.setForeground(Commons.COMPONENT_COLOR);
            this.leftLabel.setFont(Commons.MEDIUM_FONT);
            this.rightLabel.setForeground(Commons.COMPONENT_COLOR);
            this.rightLabel.setFont(Commons.MEDIUM_FONT);

            // set UI panel
            setBackground(Commons.BACKGROUND_COLOR);
            setBorder(Commons.INFO_BORDER);
            setLayout(new BorderLayout());
            setOpaque(false);
            add(this.leftLabel, BorderLayout.WEST);
            add(this.rightLabel, BorderLayout.EAST);
        }

        public void setLeftLabel(String title) {
            this.leftLabel.setText(title);
        }

        public void setRightLabel(String title) {
            this.rightLabel.setText(title);
        }
    }

    private void createBrick(Brick[] brick) {
    	int margin_side = (Commons.SCREEN_WIDTH - (Commons.BRICK_WIDTH*Commons.BRICK_ROW))/2;
    	int margin_top = Commons.SCREEN_HEIGHT/8 ;
        for (int i = 0; i < Commons.BRICK_ROW; i++) {
            for (int j = 0; j < Commons.BRICK_COL; j++) {
                brick[amount_brick] = new Brick();
                brick[amount_brick].x = i * Commons.BRICK_WIDTH + margin_side;
                brick[amount_brick].y = j * Commons.BRICK_HEIGHT + margin_top;
                amount_brick += 1;
            }
        }
    }

    public void startedGame(Player player) {
        this.player = player;
        clock = new Thread(this);
        clock.start();
    }

    public void drawBrick(Graphics g) {
        for (int i = 0; i < amount_brick; i++) {
            if (brick[i].getStatus() == 1) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(185, 211, 238));
                g2.fillRect(brick[i].getX(), brick[i].getHeight(), Commons.BRICK_WIDTH - 2, Commons.BRICK_HEIGHT - 2);
                g2.setColor(new Color(54, 66, 66));
                g2.drawRect(brick[i].getX(), brick[i].getHeight(), Commons.BRICK_WIDTH, Commons.BRICK_HEIGHT);
            }
        }
    }

    /**
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen

        // Draw the paddle, ball, item, and bricks
        paddle.draw(g);
        ball.draw(g);
        if(item1!=null)
        	item1.draw(g);
        if(item2!=null)
        	item2.draw(g);

        // Draw the active bricks
        for (int i = 0; i < amount_brick; i++) {
            if (brick[i].getStatus() == 1) { // Only draw bricks that are not destroyed
                brick[i].draw(g);
            }
        }
    }

    /**
     * This is run method override Runnable interface
     */
    @Override
    public void run() {
        try {
            double msPerFrame = 1000 / Commons.FPS;
            long lastTime = System.currentTimeMillis(); // Time of the previous frame
            double delta; // Tracks how much time has passed
            long timer = System.currentTimeMillis();
            int frames = 0;

            while (clock != null) {

                long now = System.currentTimeMillis(); // Current time in miliseconds
                delta = (now - lastTime); // Accumulate the elapsed time
                lastTime = now;
                // If enough time has passed (1 frame's worth), update and render
                while (delta >= msPerFrame) {
                    // update coponent
                    update();
                    // If the player runs out of lives, save the result and stop the game.
                    // repaint component
                    repaint(ball.getX() - ball.getWidth() * 5, ball.getY() - ball.getHeight() * 5, ball.getWidth() * 5,
                            ball.getHeight() * 5);
                    repaint(0, paddle.getY(), Commons.SCREEN_WIDTH, paddle.getHeight() * 2);
                    if(item1!=null) {
	                    repaint(item1.getX() - item1.getWidth(), item1.getY() - item1.getHeight(),
	                            item1.getWidth() * 2,
	                            item1.getHeight() * 2);
                    }
                    if(item2!=null) {
	                    repaint(item2.getX() - item2.getWidth(), item2.getY() - item2.getHeight(),
	                            item2.getWidth() * 2,
	                            item2.getHeight() * 2);
                    }
                    delta -= msPerFrame; // Reduce delta since we've processed one frame
                }
                clock.sleep((long) msPerFrame);

                frames++; // Count frames per second

                // Optional: Print FPS every second for debugging
                if (System.currentTimeMillis() - timer > 1000) {
                    topPanel.setRightLabel(String.format("FPS: %d", frames));
                    frames = 0;
                    timer += 1000;
                }

                // If the player runs out of lives, save the result and stop the game.
                // if (player.getLife() == 0) {
                // savePerformance();
                // }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void update() {
        topPanel.setLeftLabel(String.format("Name: %s", player.getName()));
        bottomPanel.setLeftLabel(String.format("Life: %d", player.getLife()));
        bottomPanel.setRightLabel(String.format("Score: %d", player.getScore()));
        // get mouse position
        Point point = MouseInfo.getPointerInfo().getLocation();
        // set position paddle
        paddle.move(point);
        ball.move();

        checkCollisions();

        // Create item1
        if (item1 != null) {
            item1.move();
            if (item1.getY() > paddle.getY()
                && (item1.getX() >= paddle.getX() && item1.getX() <= paddle.getX() +
                        paddle.getWidth())) {
            	 touchItem(item1);
            	 item1 = null;
             }

             if (item1 != null && (item1.getY() + item1.getHeight() >= Commons.SCREEN_HEIGHT)) {
            	 item1 = null;
             }
        }
        
        // Create item2
        if (item2 != null) {
            item2.move();
            if (item2.getY() > paddle.getY()
                && (item2.getX() >= paddle.getX() && item2.getX() <= paddle.getX() +
                        paddle.getWidth())) {
            	 touchItem(item2);
            	 item2 = null;
             }

             if (item2 != null && (item2.getY() + item2.getHeight() >= Commons.SCREEN_HEIGHT)) {
            	 item2 = null;
             }
        }
    }

    private void checkCollisions() {
        boolean collisionHandled = false;
        int prevX = ball.getPrevX();
        int prevY = ball.getPrevY();
        int newX = ball.getX();
        int newY = ball.getY();

        // Ball and paddle collision
        if (ball.getRect().intersects(paddle.getRect())) {
            if (ball.getY() + ball.getHeight() >= paddle.getY()
                    && ball.getY() + ball.getHeight() <= paddle.getY() + paddle.getHeight() / 2) {
                ball.reverseY();
                ball.setY(paddle.getY() - ball.getHeight());
            }
        }

        // Ball and bricks collision
        if(item_status==1) {
	        for (int i = 0; i < amount_brick; i++) {
	            if (brick[i].getStatus() == 1 &&
	                    ball.getRect().intersects(brick[i].getRect())) {
	                brick[i].brick_break(); // Break the brick
	                dropItem(brick[i].getX()+Commons.BRICK_WIDTH/2, brick[i].getY()+Commons.BRICK_HEIGHT/2);
	                player.setScore(player.getScore() + 10); // Increase score
	                // break; // Exit loop after collision
	            }
	        }
        }else {
        	for (int i = 0; i < amount_brick; i++) {
	            if (brick[i].getStatus() == 1 &&
	                    ball.getRect().intersects(brick[i].getRect())) {
	                ball.reverseY(); // Change direction on collision
	                brick[i].brick_break(); // Break the brick
	                dropItem(brick[i].getX()+Commons.BRICK_WIDTH/2, brick[i].getY()+Commons.BRICK_HEIGHT/2);
	                player.setScore(player.getScore() + 10); // Increase score
	                // break; // Exit loop after collision
	            }
	        }
        }

        // Ball and wall collision
        // Ball and lefl wall collision
        if (ball.getX() <= 0) {
            ball.reverseX();
            ball.setX(0);
        }
        // Ball and right wall collision
        if (ball.getX() + ball.getWidth() >= Commons.SCREEN_WIDTH) {
            ball.reverseX();
            ball.setX(Commons.SCREEN_WIDTH - ball.getWidth());
        }
        // Ball and top wall collision
        if (ball.getY() <= 0) {
            ball.reverseY();
            ball.setY(paddle.getY() - ball.getHeight());
            collisionHandled = true;
        }

        // Ball and Bricks collison
        for (int i = 0; i < amount_brick; i++) {
            if (brick[i].getStatus() == 1) {
                Rectangle brickRect = brick[i].getRect();
                if (intersects(prevX, prevY, newX, newY, brickRect)) {
                    handlePreciseBrickCollision(brickRect);
                    brick[i].brick_break();
                    player.setScore(player.getScore() + 10);
                    collisionHandled = true;
                    break;
                }
            }
        }

        // Ball and Wall collision
        if (!collisionHandled) {
            if (newX <= 0) {
                ball.reverseX();
                ball.setX(0);
            } else if (newX + ball.getWidth() >= Commons.SCREEN_WIDTH) {
                ball.reverseX();
                ball.setX(Commons.SCREEN_WIDTH - ball.getWidth());
            }

            if (newY <= 0) {
                ball.reverseY();
                ball.setY(0);
            }
        }

        // Fail
        if (newY + ball.getHeight() >= Commons.SCREEN_HEIGHT) {
            player.setLife(player.getLife() - 1);
            if (player.getLife() == 0) {
                savePerformance();
            } else {
                resetBallAndPaddle();
            }
        }
    }

    private boolean intersects(int prevX, int prevY, int newX, int newY, Rectangle brick) {
        Rectangle path = new Rectangle(Math.min(prevX, newX), Math.min(prevY, newY),
                Math.abs(newX - prevX) + ball.getWidth(), Math.abs(newY - prevY) + ball.getHeight());
        return path.intersects(brick);
    }

    // Handle collisions with bricks accurately
    private void handlePreciseBrickCollision(Rectangle brick) {
        int brickLeft = brick.x;
        int brickRight = brick.x + brick.width;
        int brickTop = brick.y;
        int brickBottom = brick.y + brick.height;

        int ballLeft = ball.getX();
        int ballRight = ball.getX() + ball.getWidth();
        int ballTop = ball.getY();
        int ballBottom = ball.getY() + ball.getHeight();

        int overlapLeft = Math.abs(ballRight - brickLeft);
        int overlapRight = Math.abs(ballLeft - brickRight);
        int overlapTop = Math.abs(ballBottom - brickTop);
        int overlapBottom = Math.abs(ballTop - brickBottom);

        int minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapTop) {
            ball.reverseY();
            ball.setY(brickTop - ball.getHeight());
        } else if (minOverlap == overlapBottom) {
            ball.reverseY();
            ball.setY(brickBottom);
        } else if (minOverlap == overlapLeft) {
            ball.reverseX();
            ball.setX(brickLeft - ball.getWidth());
        } else if (minOverlap == overlapRight) {
            ball.reverseX();
            ball.setX(brickRight);
        }
    }

    // Reset ball and paddle position
    private void resetBallAndPaddle() {
        paddle.setX((Commons.SCREEN_WIDTH - paddle.getWidth()) / 2);
        ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getHeight());
    }

    public void savePerformance() {
        try {
            // 1. get connection to database
            Connection con = ConnectionConfig.getConnection();
            // 2. create query insert data to database
            String query = "INSERT INTO Player (nick_name) VALUES ('%s')";
            query = String.format(query, player.getName());
            // 3. create statement for execute query
            Statement st = con.createStatement();
            // 4. execute query
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dropItem(int x, int y) {
        if (item1 == null) {
             Random generator = new Random();
             int value = generator.nextInt(21)+1;
            
             if(value%3==0) {
	            item1 = new Item(x, y, value);
             }

        }else if(item2 == null) {
        	Random generator = new Random();
            int value = generator.nextInt(21)+1;
           
            if(value%3==0) {
	            item2 = new Item(x, y, value);
            }
        }
    }

    public void touchItem(Item i) {
        switch (i.getNum()) {
            case 3:
                paddle.setWidth(Commons.PADDLE_WIDTH + 150);
                setPaddleDefault();
                break;
            case 6:
                paddle.setWidth(Commons.PADDLE_WIDTH - 60);
                setPaddleDefault();
                break;
            case 9:
                ball.setWidth(Commons.BALL_SIZE + 6);
                ball.setHeight(Commons.BALL_SIZE + 6);
                ball.setSpeed(ball.getSpeed()-50);
                setBallDefault();
                break;
            case 12:
                ball.setWidth(Commons.BALL_SIZE - 4);
                ball.setHeight(Commons.BALL_SIZE - 4);
                ball.setSpeed(ball.getSpeed() + 50);
                setBallDefault();
                break;
            case 15:
            	player.life += 1 ;           
            	break;
            case 18:
            	item_status=1;
            	setItemStatusDefault();
            	break;
            default:
                break;
        }
    }

    public void setPaddleDefault() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                paddle.setWidth(Commons.PADDLE_WIDTH);
            }
        };
        t.schedule(task, 5000);
    }

    public void setBallDefault() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                ball.setWidth(Commons.BALL_SIZE);
                ball.setHeight(Commons.BALL_SIZE);
                ball.setSpeed(Commons.BALL_SPEED);
            }
        };
        t.schedule(task, 5000);
    }
    
    public void setItemStatusDefault() {
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                item_status=0;
            }
        };
        t.schedule(task, 5000);
    }
}