package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import DatabaseConfig.ConnectionConfig;
import SubGame.InfoPanel;
import SubGame.PrepareGame;
import com.Login.LoginGameListener;
import com.RankingTable.RankingTableEvent;

public class Board extends JPanel implements Runnable {
    private Thread clock;
    private Paddle paddle;
    private Ball ball;
    private Brick[] brick;
    private int amount_brick = 0;
    private Player player;
    private Item item1;
    private Item item2;
    private Timer defaultPaddle;
    private Timer defaultBallSize;
    private Timer defaultBall;
    private int item_status = 0;
    private InfoPanel topPanel;
    private InfoPanel bottomPanel;
    private Login login;
    private Boolean flagPlayer = true;
//    private int FPS = Commons.FPS;

    public Board() {
        initComponent();
        login = new Login();
        
        LoginGameListener listener = new Login.LoginGameListener() {
            public void addPlayer(String playerName) {
                Board.this.player = new Player(playerName, 0, 3);
                Board.this.remove(login);         
                System.out.println("Login was sucessfully!!!");
                startGame("Click here to Start game");
            }
        };
        login.prepareGame(listener);
        this.add(login);
    }

    private void initComponent() {
        paddle = new Paddle();
        ball = new Ball();
        brick = new Brick[Commons.BRICK_ROW * Commons.BRICK_COL];
        topPanel = new InfoPanel("", "");
        bottomPanel = new InfoPanel("", "");

        createBrick(brick);

        int panelWidth = Commons.SCREEN_WIDTH, panelHeight = Commons.SCREEN_HEIGHT;
        // init screen with scale 16/9
        this.setSize(panelWidth, panelHeight);
        this.setBackground(Commons.BACKGROUND_COLOR);
        this.setLayout(new BorderLayout());
    }

    public void startGame(String title) {
        PrepareGame prepare = new PrepareGame(title);
        prepare.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Board.this.remove(prepare);
                addInfoPanel();
                new javax.swing.Timer(1000, evt -> {
                // Start the clock after 2 seconds
                    clock = new Thread(Board.this);
                    clock.start();
                    ((javax.swing.Timer) evt.getSource()).stop(); // Stop the timer after it runs once
                }).start();
            }
        });
        this.add(prepare, BorderLayout.CENTER);
        this.repaint();
    }
    
    private void addInfoPanel() {
        String name = String.format("Name: %s", player.getName());
        String FPS = String.format("FPS: %d", 0);
        
        topPanel.setLeftLabel(name);
        topPanel.setRightLabel(FPS);
        Board.this.add(topPanel, BorderLayout.NORTH);

        String score = String.format("Score: %d", 0);
        String life = String.format("Life: %d", 0);

        bottomPanel.setLeftLabel(score);
        bottomPanel.setRightLabel(life);
        Board.this.add(bottomPanel, BorderLayout.SOUTH);
        Board.this.revalidate();
        Board.this.repaint();    
    }

    /**
     * @author: "Quang Minh"
     */
    private void createBrick(Brick[] brick) {
        int margin_side = (Commons.SCREEN_WIDTH - (Commons.BRICK_WIDTH * Commons.BRICK_ROW)) / 2;
        int margin_top = Commons.SCREEN_HEIGHT / 8;
        for (int i = 0; i < Commons.BRICK_ROW; i++) {
            for (int j = 0; j < Commons.BRICK_COL; j++) {
                brick[amount_brick] = new Brick();
                brick[amount_brick].x = i * Commons.BRICK_WIDTH + margin_side;
                brick[amount_brick].y = j * Commons.BRICK_HEIGHT + margin_top;
                amount_brick += 1;
            }
        }
    }

    public void stopGame() {
        this.clock = null;
//        clock.start();
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
        if (item1 != null)
            item1.draw(g);
        if (item2 != null)
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
                    if (item1 != null) {
                        repaint(item1.getX() - item1.getWidth(), item1.getY() - item1.getHeight(),
                                item1.getWidth() * 2,
                                item1.getHeight() * 2);
                    }
                    if (item2 != null) {
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

        createItem();
        
    }
    
    private void createItem() {
		// TODO Auto-generated method stub
    	// Create item1
        if (item1 != null) {
            item1.move();
            if (item1.getRect().intersects(paddle.getRect())) {
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
            if (item2.getRect().intersects(paddle.getRect())) {
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
            ball.reverseY();
            ball.setY(paddle.getY() - ball.getHeight());
            collisionHandled = true;
        }

        // Ball and brick collision       
            int flagEmptyBrick = amount_brick;
            for (int i = 0; i < amount_brick; i++) {
                if (brick[i].getStatus() == 1 && ball.getRect().intersects(brick[i].getRect())) {
                    Rectangle brickRect = brick[i].getRect();
                    if (intersects(prevX, prevY, newX, newY, brickRect)) {
                    	if(item_status==0) {
                    		handlePreciseBrickCollision(brickRect);
                    	}
                        brick[i].brick_break(); // Break the brick
                        dropItem(brick[i].getX() + Commons.BRICK_WIDTH / 2, brick[i].getY() + Commons.BRICK_HEIGHT / 2);
                        player.setScore(player.getScore() + 10); // Increase score
                        break; // Exit loop after collision
                    }
                } else if (brick[i].getStatus() == 0) {
                    flagEmptyBrick--;
                }
            }
            // When all brick was break
            if (flagEmptyBrick <= 0) {
                System.out.println("Brick was empty...");
                stopGame();
                savePerformance();
                addRankingTable();
                revalidate();
                repaint();
            }

        // Va chạm với tường
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
            stopGame();
            
            if (player.getLife() == 0) {
                savePerformance();
                addRankingTable();
                revalidate();
                repaint();
            } else {
                PrepareGame pre = new PrepareGame("Click here to continue");
                pre.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                        Board.this.remove(pre);
                        clock = new Thread(Board.this);
                        clock.start();
                    }
                });
                add(pre);
                resetBallAndPaddle();
            }
            revalidate();
            repaint();
        }
    }

    // Kiểm tra giao cắt giữa đường đi của bóng và viên gạch
    private boolean intersects(int prevX, int prevY, int newX, int newY, Rectangle brick) {
        Rectangle path = new Rectangle(Math.min(prevX, newX), Math.min(prevY, newY),
                Math.abs(newX - prevX) + ball.getWidth(), Math.abs(newY - prevY) + ball.getHeight());
        return path.intersects(brick);
    }

    // Xử lý va chạm chính xác với gạch
    private void handlePreciseBrickCollision(Rectangle brick) {
        int brickLeft = brick.x;
        int brickRight = brick.x + brick.width;
        int brickTop = brick.y;
        int brickBottom = brick.y + brick.height;

        int ballLeft = ball.getX();
        int ballRight = ball.getX() + ball.getWidth();
        int ballTop = ball.getY();
        int ballBottom = ball.getY() + ball.getHeight();

        // Tính toán độ chồng lấp theo từng hướng
        int overlapLeft = Math.abs(ballRight - brickLeft);
        int overlapRight = Math.abs(ballLeft - brickRight);
        int overlapTop = Math.abs(ballBottom - brickTop);
        int overlapBottom = Math.abs(ballTop - brickBottom);

        // Chọn hướng va chạm có độ chồng lấp nhỏ nhất
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

    // Đặt lại vị trí của bóng và paddle sau khi mất mạng
    private void resetBallAndPaddle() {
        ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getHeight());
    }

    public void savePerformance() {
        Statement st = null;
        String query;
        try {
            // 1. get connection to database
            Connection con = ConnectionConfig.getConnection();
            // 2. create query insert data to database
            if (flagPlayer) {
                System.out.println("Insert Player...");
                query = "exec BreakOutBall.dbo.AddPlayer @nickName = '%s',@score = %d, @life = %d";
                query = String.format(query, player.getName(),player.getScore(),player.getLife());   
                flagPlayer = false;
            } else {
                System.out.println("Update player...");
                query = "EXEC dbo.UpdatePlayer @name = '%s', @score = %d, @life = %d";
                query = String.format(query, player.getName(), player.getScore(),player.getLife());
            }

            System.out.println(query);
            // 3. create statement for execute query
            st = con.createStatement();
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
            	// Long paddle
                paddle.setWidth(Commons.PADDLE_WIDTH + 150);
                setDefaultPaddle();
                break;
                
            case 6:
            	// Short paddle
                paddle.setWidth(Commons.PADDLE_WIDTH - 60);
                setDefaultPaddle();
                break;
                
            case 9:
            	// Big + slow ball
                ball.setWidth(Commons.BALL_SIZE + 8);
                ball.setHeight(Commons.BALL_SIZE + 8);
                ball.setSpeed(ball.getSpeed()-50);
                setDefaultBallSize();
                break;
                
            case 12:
            	// Small + quick ball
                ball.setWidth(Commons.BALL_SIZE - 4);
                ball.setHeight(Commons.BALL_SIZE - 4);
                ball.setSpeed(ball.getSpeed() + 50);
                setDefaultBallSize();
                break;
                
            case 15:
            	// +1 life
            	player.setLife(player.getLife() + 1);           
            	break;
            	
            case 18:
            	// Power ball
            	item_status=1;
            	setDefaultBall();
            	break;
            	
            default:
                break;
        }
    }
    
    public void setDefaultPaddle() {
    	TimerTask task1 = new TimerTask() {
            public void run() {
                paddle.setWidth(Commons.PADDLE_WIDTH);
            }
        };
        
    	if(defaultPaddle==null) {
        	defaultPaddle = new Timer();
        	defaultPaddle.schedule(task1, 5000);
        }else {
        	defaultPaddle.cancel();
        	defaultPaddle = new Timer();
        	defaultPaddle.schedule(task1, 5000);
        }
    }
    
    public void setDefaultBallSize() {
    	TimerTask task2 = new TimerTask() {
            public void run() {
            	ball.setWidth(Commons.BALL_SIZE);
                ball.setHeight(Commons.BALL_SIZE);
                ball.setSpeed(Commons.BALL_SPEED);
            }
        };
        
    	if(defaultBallSize==null) {
        	defaultBallSize = new Timer();
        	defaultBallSize.schedule(task2, 5000);
        }else {
        	defaultBallSize.cancel();
        	defaultBallSize = new Timer();
        	defaultBallSize.schedule(task2, 5000);
        }
    }
    
    public void setDefaultBall() {
    	TimerTask task3 = new TimerTask() {
            public void run() {
            	item_status=0;
            }
        };
        
    	if(defaultBall==null) {
    		defaultBall = new Timer();
    		defaultBall.schedule(task3, 5000);
        }else {
        	defaultBall.cancel();
        	defaultBall = new Timer();
        	defaultBall.schedule(task3, 5000);
        }
    }

    private void addRankingTable() {
        RankingTable rank = new RankingTable(player.getName());                
        // Interface for call when ranking table close
        RankingTableEvent evt;
        evt = new RankingTable.RankingTableEvent() {
            public void rakingRemove(RankingTable rank) {
                Board.this.remove(rank);
            }

            public void addPrepareGame() {
                System.out.println("Reseted life player...");
                ball.setX(Commons.INIT_BALL_X);
                ball.setY(Commons.INIT_BALL_Y);
                System.out.println("Reset ball ...");
                amount_brick = 0;
                createBrick(brick);
                System.out.println("Reseted brick ...");
                player.setLife(3);
                player.setScore(0);
                System.out.println(player.getLife());
                startGame("Click here to Restart game");
            }
        };
        rank.addEventTable(evt);
        System.out.println("Add ranking table...");
        add(rank,BorderLayout.CENTER);
    }
}