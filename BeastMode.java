import java.awt.*;
import java.awt.Image;
import javax.swing.*;
import java.awt.Toolkit;
import java.io.IOException;
import java.awt.event.*;

/**
 * BeastMode.java
 *
 * Class that runs the game by instantiating player and enemy objects within a new window
 * @authors Garo Anguiano-Sainz, Simphiwe Hlophe, Jonathon "Jonny" Liou, and Colby Seyferth
 */

public class BeastMode implements ActionListener{

    /** Allows players to exit through the menu or display about information */
	private static JMenuItem about = new JMenuItem("About");
    private static JMenuItem exit = new JMenuItem("Exit");

    /** Sets the direction of the player */
    private static  int determineDirection = 1;
    
    public static void main(String[] args) throws java.io.IOException {
	
	//images used by the player
	//http://www.jawshark.com/great_white_shark_animated_gifs.html
	Image playerLeft = Toolkit.getDefaultToolkit().createImage("blackLeft.gif");
	Image playerRight = Toolkit.getDefaultToolkit().createImage("blackRight.gif");

	//images used in the interactive menu
	Image easyImage = Toolkit.getDefaultToolkit().createImage("easy.gif");
	Image mediumImage = Toolkit.getDefaultToolkit().createImage("medium.gif");
	Image hardImage = Toolkit.getDefaultToolkit().createImage("hard.gif");
	
	//images for the start screen and game screen respectively
	Image backGroundStart = Toolkit.getDefaultToolkit().createImage("start.jpg");
	Image bgstart = backGroundStart.getScaledInstance(750, 560, Image.SCALE_DEFAULT);

	//http://outlanderthemovie.wordpress.com/page/3/
	Image backGround = Toolkit.getDefaultToolkit().createImage("background.jpg");
	Image bg = backGround.getScaledInstance(750, 560, Image.SCALE_DEFAULT);
	
	Integer started = 0;
	
	//creates the JFrame in which the game takes place
	final JFrame frame = new JFrame("BEAST MODE");
	
	//creates the player Moveable and sets its initial state
	Moveable player = new Moveable();
	player.setLeftImage(playerLeft);
	player.setRightImage(playerRight);
	player.setScaleLeftImage(playerLeft);
	player.setScaleRightImage(playerRight);
	player.set_x(350);
	player.set_y(250);
	player.setSize(4);
	player.set_x_bound(49);
	player.set_y_bound(60);
	player.setBoundingRectangle();

	//creates the "easy" difficulty Moveable for the interactive menu
	//and sets ints initial state
	Moveable easy = new Moveable();
	easy.setLeftImage(easyImage);
	easy.setRightImage(null);
	easy.set_x_bound(31);
	easy.set_y_bound(31);
	easy.set_x(188);
	easy.set_y(183);
	easy.setBoundingRectangle();

	//creates the "medium" difficulty Moveable for the interactive menu
	//and sets ints initial state
	Moveable medium = new Moveable();
	medium.setLeftImage(mediumImage);
	medium.setRightImage(null);	
	medium.set_x_bound(31);
	medium.set_y_bound(31);
	medium.set_x(188);
	medium.set_y(258);
	medium.setBoundingRectangle();

	//creates the "easy" difficulty Moveable for the interactive menu
	//and sets ints initial state
	Moveable hard = new Moveable();
	hard.setLeftImage(hardImage);
	hard.setRightImage(null);
	hard.set_x_bound(31);
	hard.set_y_bound(31);
	hard.set_x(188);
	hard.set_y(333);
	hard.setBoundingRectangle();

	//sets size of JFrame
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setSize(750,600);
	
	Container pane = frame.getContentPane();
	final DrawingPanel panel = new DrawingPanel();
	
	//adds background, player, and difficulty options to the panel to be drawn
	panel.bg = bgstart;
	panel.addMoveable(player);
	panel.addMoveable(easy);
	panel.addMoveable(medium);
	panel.addMoveable(hard);
	pane.add(panel);
	
	//adds items to menu
	JMenuBar menubar = new JMenuBar();
	JMenu menu = new JMenu("File");
	
	exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		}
	    });
	
	about.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
		    System.out.println("Beast Mode: by Garo Anguiano-Sainz, Simphiwe Hlophe, Jonathon Liou, and Colby Seyferth; instructor Amy Csizmar Dalal, Software Design, Carleton College");
		}
	    });
	
	//section uses code adapted from
	//http://www.velocityreviews.com/forums/t420563-close-a-jframe-with-the-esc-key.html
	frame.addKeyListener(new KeyListener() {
		public void keyPressed(KeyEvent e) {
		}
		public void keyReleased(KeyEvent e) {
		    if (e.getKeyCode() ==KeyEvent.VK_ESCAPE){
			System.exit(0);
		    }
		}
		public void keyTyped(KeyEvent e) {
		}
	    });

	menu.add(about);
	menu.add(exit);
	menubar.add(menu);
		
	frame.setJMenuBar(menubar);	
	frame.addKeyListener(panel);
	frame.setVisible(true);
	
	//allows player to control their Moveable while the alive boolean is true
	while(panel.getAlive()){
	     
	    if(started == 0) {
		
		panel.setBoundary();
		panel.setCoordinates();

		//checks which difficulty the player has selected
		if(player.getbounds().intersects(easy.getbounds())){		
		    started = 1;
		    panel.bg = bg;
		    panel.removeMoveable(easy);
		    panel.removeMoveable(medium);
		    panel.removeMoveable(hard);
		    try{
			new GameLogic(panel, 1);
		    }
		    catch(IOException ex){
		    }
		}
		if(player.getbounds().intersects(medium.getbounds())){		
		    started = 1;
		    panel.bg = bg;
		    panel.removeMoveable(easy);
		    panel.removeMoveable(medium);
		    panel.removeMoveable(hard);
		    try{
			new GameLogic(panel, 2);
		    }
		    catch(IOException ex){
		    }
		}
		if(player.getbounds().intersects(hard.getbounds())){		
		    started = 1;
		    panel.bg = bg;
		    panel.removeMoveable(easy);
		    panel.removeMoveable(medium);
		    panel.removeMoveable(hard);
		    try{
			new GameLogic(panel, 3);
		    }
		    catch(IOException ex){
		    }
		}
	    }
	    
	    //determines which image should be used based on the playe'rs direction
	    if (determineDirection ==1){
		panel.updatePlayerImage(player.getLeftImage());
	    }
	    else{
		panel.updatePlayerImage(player.getRightImage());
	    }
	    
	    //updates and draws all Moveables in the pane
	    panel.setCoordinates();
	    panel.repaint();

	    //sets player image and coordinates accordingly when arrow keys are pressed
	    //adapted with code from http://www.javaprogrammingforums.com/whats-wrong-my-code/5607-moving-image-around-screen-using-arrow-keys.html
	    //adapted with code from http://www.zekkocho.com/java-games/82-java-move-an-object-using-the-arrow-keys-keylistener
	    if(panel.upKey){
	        player.set_y(player.get_y()-1);
	        player.checkBounds();
		panel.repaint();
	    }
	    if(panel.downKey){
		player.set_y(player.get_y()+1);
		panel.setCoordinates();
		player.checkBounds();
		panel.repaint();
	    }
	    if(panel.leftKey){
		
		determineDirection = 1;
		panel.updatePlayerImage(player.getLeftImage());
		player.set_x(player.get_x()-1);
		player.checkBounds();
		panel.repaint();
	    }
	    if(panel.rightKey){
        	determineDirection = 0;
		panel.updatePlayerImage(player.getRightImage());
		player.set_x(player.get_x()+1);
		player.checkBounds();
		panel.repaint();
	    }
	    try {
		Thread.sleep(4);
            }
	    catch (InterruptedException ex){
            }
	}
	
	//try{
	    //resets background once player is no longer alive
	    frame.setContentPane(new JLabel(new ImageIcon(bg)));
	    frame.getContentPane().setLayout(null);
	    
	    //determines the player's score
	    int score = (int)(panel.getMoveable().get(0).getSize()*100 + panel.getMoveable().get(0).getSize()*10) - 373;
	    String text = " Your Score is: " + Integer.toString(score);
	    
	    //displays the player's score
	    Font font = new Font("Chiller", Font.BOLD, 30);
	    Font font2 = new Font("Chiller", Font.BOLD, 50);
	    JTextField comp = new JTextField(text);
	    comp.setFont(font);
	    comp.setForeground(Color.WHITE);
	    comp.setBounds(175,20,400,100);
	    comp.setOpaque(false);
	    comp.setBackground(new Color(0,0,0,0));
	    comp.setHorizontalAlignment(JTextField.CENTER);

	    //displays a congratulating message if the player was not eaten
	     if (panel.checkWin()){
		 JTextField win = new JTextField("YOU WIN!");
	    	win.setFont(font2);
	    	win.setForeground(Color.WHITE);
	    	win.setBounds(175,250,400,100);
	    	win.setOpaque(false);
	    	win.setBackground(new Color(0,0,0,0));
		win.setHorizontalAlignment(JTextField.CENTER);
		win.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	    	frame.getContentPane().add(win);
	    }
	     //displays a message if the player was eaten
	    else{
	    	JTextField lose = new JTextField("YOU LOSE!");
	    	lose.setFont(font2);
	    	lose.setForeground(Color.WHITE);
	    	lose.setBounds(175,250,400,100);
	    	lose.setOpaque(false);
	    	lose.setBackground(new Color(0,0,0,0));
		lose.setHorizontalAlignment(JTextField.CENTER);
		lose.setBorder(javax.swing.BorderFactory.createEmptyBorder());
	    	frame.getContentPane().add(lose);
	    }
	    frame.getContentPane().add(comp);
	    frame.getContentPane().setBackground(SystemColor.BLACK);
	    frame.setSize(750,560);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //	}
	//catch (IOException ex){
	//}
    }
    public void actionPerformed(ActionEvent e) {
	//abstract method
    }
}