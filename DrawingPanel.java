import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;
import java.util.ArrayList;

/**
 * DrawingPanel.java
 *
 * Class that stores all images and their positions, and is able to access, change, remove,
 * and paint the images accordingly to simulate animation based on keyboard interaction.
 *
 * @authors Garo Anguiano-Sainz, Simphiwe Hlophe, Jonathon "Jonny" Liou, and Colby Seyferth
 */

class DrawingPanel extends JPanel implements KeyListener {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Holds all moveables in an arraylist */
    private ArrayList<Moveable> moveable = new ArrayList<Moveable>();

    /** Holds all images in an arraylist */
    private ArrayList<Image> images = new ArrayList<Image>();

    /** Holds all x-coordiantes in an arraylist */
    private ArrayList<Integer> x = new ArrayList<Integer>();

    /** Holds all Y-coordiantes in an arraylist */
    private ArrayList<Integer> y = new ArrayList<Integer>();
    
    //state of arrowkeys
    boolean downKey = false;
    boolean leftKey = false;
    boolean rightKey = false;
    boolean upKey = false;

    Integer indexer = 0;

    Image backGround = Toolkit.getDefaultToolkit().createImage("background.jpg");
    Image bg = backGround.getScaledInstance(750, 600, Image.SCALE_DEFAULT);

    //state of character
    boolean alive = true;
    boolean winning = false;
    
    /**
     * Constructor allows for detecting keys pressed to allow for player movement
     */ 
    public DrawingPanel(){
	this.addKeyListener(this);	
    }
    
    /** Accessor for current index */
    public Integer getIndexer(){
        return this.indexer;
    }
   
    /** Accessor for arraylist of Moveables */
    public ArrayList<Moveable> getMoveable(){
        return this.moveable;
    }
    
    /** Accessor for player's vital status */
    public boolean getAlive(){
        return this.alive;
    }
    
    /** Accessor for player's game status */
    public boolean checkWin(){
        return this.winning;
    }

    /**
     * This method sets the player's image to the specified image
     * @param i: Image to be set
     */
    public void updatePlayerImage(Image i){
	images.set(0, i);
    }

    /**
     * This method updates the bounding rectangle of each Moveable
     */
    public void setBoundary(){
    	for (int i = 0; i < moveable.size(); i++){
	    moveable.get(i).setBoundingRectangle();	
    	} 	
    }
    
    /**
     * This method updates the coordinates of each Moveable
     */
    public void setCoordinates(){
        for (int i = 0; i < x.size(); i++){
            x.set(i, moveable.get(i).get_x());
        }
        for (int j = 0; j < y.size(); j++){
            y.set(j, moveable.get(j).get_y());
        }
    }

    /**
     * This method adds a specified Moveable to the list
     * @param m: Moveable to be added
     */
    public void addMoveable(Moveable m){
        this.moveable.add(m);

	//adds the image of the moveable to the list of images
        if (m.getRightImage() != null){
	    this.images.add(m.getRightImage());
        } 
        else {
	    this.images.add(m.getLeftImage());
        }

	//adds the coordinates of the moveable to the list of coordinates
        this.x.add(m.get_x());
        this.y.add(m.get_y());
    }
    
    /**
     * This method removes a specified Moveable to the list
     * @param m: Moveable to be added
     */
    public void removeMoveable(Moveable m){
	
	int index = moveable.indexOf(m);
	
	//removes the moveable and its image and coordinates
	//from all the respective lists
	moveable.remove(index);
	images.remove(index);
	x.remove(index);
	y.remove(index);
    } 
    
    /**
     * This method checks whether the player has collided with a non-player object
     */
    public boolean checkCollision(){
	//state of collision
        boolean returnBoolean = false;
        Rectangle playerRectangle = moveable.get(0).getbounds();

        for (int i = 1; i < moveable.size(); i++){
	    //determines whether the player and the non-player's bounding rectangle intersect
            returnBoolean = playerRectangle.intersects(moveable.get(i).getbounds());

	    if (returnBoolean){
		
		//determines whether the non-player object was a beast mode
		if(moveable.get(i).checkBeastMode() && !moveable.get(0).checkBeastMode()){
		    moveable.get(0).turntUp();
		    new BeastModeThread(moveable.get(0));
		}

		if (moveable.get(0).checkBeastMode() || moveable.get(0).getSize() > moveable.get(i).getSize()){ 
		    //removes the non-player moveable if smaller than the player
		    Moveable deadEnemy = moveable.get(i);
		    this.removeMoveable(deadEnemy);
		    deadEnemy = null;
		    this.indexer = i;

		    //increase the player size to simulate growth
		    double curSize = moveable.get(0).getSize();
		    curSize = curSize + 0.1;

		    if (curSize > 22){
			//player wins once they have reached a size of 22
			this.winning = true;
			this.alive = false;
		    }
		    
		    //updates the player size and image scaling
		    moveable.get(0).setSize(curSize);
		    this.miracleGrow();
		    return true;
		}
		else{
		    //player is no longer alive if non-player is larger
		    this.alive = false;
		    return true;
		}
	    }
	}
        return false;
    }
    
    /**
     * This method updates the player's image size and bounding rectangle
     */
    public void miracleGrow(){
        Moveable player = moveable.get(0);
	
        Image leftImage = player.getScaleLeftImage();
        Image rightImage = player.getScaleRightImage();
	
        //calculates the new dimensions
        int resize_x = (int)(49*player.getSize()/4);
        int resize_y = (int)(60*player.getSize()/4);
	
	//sets and updates bounding rectangle dimensions
        player.set_x_bound(resize_x);
        player.set_y_bound(resize_y);
        player.setBoundingRectangle();

        try {
	    //resizes the players current image according to new dimensions
            Image leftResize = leftImage.getScaledInstance(resize_x, resize_y, Image.SCALE_FAST);
            Image rightResize = rightImage.getScaledInstance(resize_x, resize_y, Image.SCALE_FAST);
            
	    //updates the resized images
            player.setLeftImage(leftResize);
            player.setRightImage(rightResize);
        }
	catch(IllegalArgumentException ex){ 
	} 
    }
 
    /**
     * This method paints each Moveable at their correct location
     */
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;
	
	//overrides paintComponent
	g2d.drawImage(bg, 0, 0, null);
	for (int i = moveable.size() - 1; i >= 0; i--){
	    try{
	        g2d.drawImage(images.get(i), x.get(i), y.get(i), null);
	    }
	    catch(IndexOutOfBoundsException ex){
	    }
	}
    }  
    
    /**
     * This method determines whether the arrowkeys were pressed
     */
    public void keyPressed(KeyEvent e){
	
	//adapted with code from http://www.javaprogrammingforums.com/whats-wrong-my-code/5607-moving-image-around-screen-using-arrow-keys.html
	//adapted with code from http://www.zekkocho.com/java-games/82-java-move-an-object-using-the-arrow-keys-keylistener
	switch (e.getKeyCode()){
	    
	case KeyEvent.VK_LEFT:
	    leftKey = true;
	    break;
	    
	case KeyEvent.VK_RIGHT:
	    rightKey = true;
	    break;
	    
	case KeyEvent.VK_UP:
	    upKey = true;
	    break;
	    
	case KeyEvent.VK_DOWN:
	    downKey = true;
	    break;
	}
    }
    
    /**
     * This method determines whether the arrowkeys were released
     */
    public void keyReleased(KeyEvent e){

	//adapted with code from http://www.javaprogrammingforums.com/whats-wrong-my-code/5607-moving-image-around-screen-using-arrow-keys.html
	//adapted with code from http://www.zekkocho.com/java-games/82-java-move-an-object-using-the-arrow-keys-keylistener
	switch (e.getKeyCode()){
	    
	case KeyEvent.VK_LEFT:
	    leftKey = false;
	    break;
	    
	case KeyEvent.VK_RIGHT:
	    rightKey = false;
	    break;
	    
	case KeyEvent.VK_UP:
	    upKey = false;
	    break;
	    
	case KeyEvent.VK_DOWN:
	    downKey = false;
	    break;
	}
    }
    public void keyTyped(KeyEvent ke){
	// abstract method
    }
}
