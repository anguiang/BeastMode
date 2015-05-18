import java.awt.Image;
import java.lang.Math;
import java.awt.Toolkit;

/**
 * FishFactory.java
 *
 * A Factory that builds the enemy sharks that are placed on the screen.
 * We utilized the Factory design pattern to divide the tasks in CPU
 * 
 * This Class has one method, buildEnemy():
 * We use this class to randomly generate every enemy shark and BeastMode power-up.
 * @authors Garo Anguiano-Sainz, Simphiwe Hlophe, Jonathon "Jonny" Liou, and Colby Seyferth
 */

public class FishFactory {

    //INSTANCE VARIABLES:
    //http://cf.geekdo-images.com/avatars/avatar_1203865216.jpg
    Image left = Toolkit.getDefaultToolkit().createImage("greyLeft.gif");
    Image right = Toolkit.getDefaultToolkit().createImage("greyRight.gif");
    Image BMLeft = Toolkit.getDefaultToolkit().createImage("runLeft.gif");
    Image BMRight = Toolkit.getDefaultToolkit().createImage("runRight.gif");
    
    public int maxSize;
    public int maxSpeed;
    
    
    /**
     * CONSTRUCTOR:
     * @param  size: sets the maximum size of the enemy sharks that it makes
     * @param  speed: sets the maximum speed of the enemy sharks that it makes
     */
    public FishFactory(int size, int speed) throws java.io.IOException {
	this.maxSpeed = speed;
	this.maxSize = size;
	
    }
    /**
     * This method Builds the nemy sharks and the Beastmode power-ups
     * 
     * No parameters
     * @return Moveable fish
     */
    public Moveable buildEnemy(){
	
	Moveable fish = new Moveable();
	// Generate random y coordinate value
	int y = (int)(Math.random() * ((550) + 1));
	// x will be either on left or right of screen
	int x = (int)(Math.random()*2.0);
	int size = 1 + (int)Math.round((Math.random()*this.maxSize));
	int speed = 1 +(int)(Math.random()*maxSpeed);
	int beastMode = (int)(Math.random()*25.0); 
	
	
	fish.set_y(y);
	fish.setSize(size);
	fish.setSpeed(speed);
	
	// Checks to see if it is a beastmode powerup
	if (beastMode == 1){
	    
	    Image resizeLeft = BMLeft.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
	    Image resizeRight = BMRight.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
	    
	    // If x is on the left of the screen:
	    if (x==0){
		// Put it on the left and make its image facing right
		fish.set_x(-400 + (int)(Math.random() * ((400) + 1)));
		fish.setRightImage(resizeRight);
	    }
	    else{
		// Put it on the right and make its image facing left
		fish.set_x(800 + (int)(Math.random() * ((400) + 1)));
		fish.setLeftImage(resizeLeft);
	    }
	    // This makes the movable know that it is a BeastMode power-up instead of an enemy
	    fish.turntUp();
	    //set the default size for the powerup and it's bounding rectangle
	    fish.setSize(3);
	    fish.set_x_bound(25);
	    fish.set_y_bound(25);
	    fish.setBoundingRectangle();
	    return fish;
	}
	
	// The Moveable is a Shark, not a Power-up; resize it accordingly
	int resize_x = (int)(49*fish.getSize()/4);
	int resize_y = (int)(60*fish.getSize()/4);
	
	if (x==0){
	    // shark is on the left:
	    fish.set_x(-400 + (int)(Math.random() * ((400) + 1)));
	    
	    
	    try {
		
		Image resize = right.getScaledInstance(resize_x, resize_y, Image.SCALE_DEFAULT);
		fish.set_x_bound(resize_x);
		fish.set_y_bound(resize_y);
		fish.setBoundingRectangle();
		fish.setRightImage(resize);
	    }
			catch(IllegalArgumentException ex){
			}
		}
	else {
	    //Shark is on the right
	    fish.set_x(800 + (int)(Math.random() * ((400) + 1)));
	    
	    
	    
	    try{
		Image resize = left.getScaledInstance(resize_x, resize_y, Image.SCALE_DEFAULT);
		fish.set_x_bound(resize_x);
		fish.set_y_bound(resize_y);
		fish.setBoundingRectangle();
		fish.setLeftImage(resize);
	    }
			catch(IllegalArgumentException ex){
			}
	}
	
	return fish;
	
    }
    
}

