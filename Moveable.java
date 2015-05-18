import java.awt.Rectangle;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

/**
 * Moveable.java
 *
 * Class representing all moveable objects represented by sharks
 * including both the player and non-players objects (or sharks)
 * @authors Garo Anguiano-Sainz, Simphiwe Hlophe, Jonathon "Jonny" Liou, and Colby Seyferth
 */

public class Moveable{
    
    /** Current x-coordinate */
    private int x;
    
    /** Current y-coordinate */
    private int y;

    /** Current right directional image */
    private Image rightImage;

    /** Current left directional image */
    private Image leftImage;
    
    /** Resized right directional image */
    private Image scaleRightImage;

    /** Resized left directional image */
    private Image scaleLeftImage;
    
    /** Current size of the represented shark */
    private double size;

    /** Current speed of the represented shark */
    private int speed;
    
     /** Current width of the bounding 
      * rectangle of the represented shark */
    private double x_bound;

    /** Current height of the bounding 
     * rectangle of the represented shark */
    private double y_bound;
    
    /** Bounding rectangle of the represented shark */
    private Rectangle boundary;

    /** Indicates whether the Moveable activates beast mode */
    private boolean beastMode;
    
    /**
     * The constructor initializes the properties of the Moveable object
     */
    public Moveable(){
        x = 0;
        y = 0;
        rightImage = null;
        leftImage = null;
        scaleRightImage = null;
        scaleLeftImage = null;
        size = 0;
        speed = 0;
        x_bound = 0;
        y_bound = 0;
        boundary = new Rectangle(this.x,this.y, (int)x_bound, (int)y_bound);
        beastMode = false;
    }

    /** Mutator for the width of the bounding rectangle */
    public void set_x_bound(double x){
        this.x_bound = x;
    }

    /** Mutator for the height of the bounding rectangle */
    public void set_y_bound(double y){
        this.y_bound = y;
    }

    /** Accessor for the width of the bounding rectangle */
    public double get_x_bound(){
        return this.x_bound;
    }

    /** Accessor for the height of the bounding rectangle */
    public double get_y_bound(){
        return this.y_bound;
    }
    
    /** Accessor for the bounding rectangle */
    public Rectangle getbounds()
    {
    	return this.boundary;
    }

    /** Mutator for the x-coordinate */
    public void set_x(int new_x){
        this.x = new_x;
    }

    /** Accessor for the x-coordinate */
    public int get_x(){
        return this.x;
    }
    
    /** Mutator for the y-coordinate */
    public void set_y(int new_y){
        this.y = new_y;
    }
    
    /** Accessor for the y-coordinate */
    public int get_y(){
        return this.y;
    }
    
    /** Accessor for the 2D bounding rectangle */
    public Rectangle2D getBoundary(){
    	return this.boundary;
    }
    
    /** Mutator for the current size */
    public void setSize(double size){
    	this.size = size;
    }
    
    /** Accessor for the current size */
    public double getSize(){
    	return this.size;
    }
    public void setSpeed(int speed){
    	this.speed = speed;
    }
    
    /** Accessor for the current speed */
    public int getSpeed(){
    	return this.speed;
    }
    
    /** Mutator for the rightward facing image */
    public void setRightImage(Image image){
        this.rightImage = image;
    }
    
    /** Mutator for the leftward facing image */
    public void setLeftImage(Image image){
        this.leftImage = image;
    }
    
    /** Mutator for the rightward facing scale image */
    public void setScaleRightImage(Image image){
        this.scaleRightImage = image;
    }
    
    /** Mutator for the leftward facing scale image */
    public void setScaleLeftImage(Image image){
        this.scaleLeftImage = image;
    }
    
    /** Accessor for the rightward facing image */
    public Image getRightImage(){
        return this.rightImage;
    }
    
    /** Accessor for the leftward facing image */
    public Image getLeftImage(){
        return this.leftImage;
    }
    
    /** Accessor for the rightward facing scale image */
    public Image getScaleRightImage(){
        return this.scaleRightImage;
    }
    
    /** Accessor for the leftward facing scale image */
    public Image getScaleLeftImage(){
        return this.scaleLeftImage;
    }

    /**
     * This method sets the bounding rectangle based on current size and position
     */
    public void setBoundingRectangle(){
	
	//centers a bounding rectangle at 80% of the player's size
	//to account for the white space of the animated GIF image
        double new_x = (this.x+(0.05*this.x));
        double new_y = (this.y+(0.05*this.y));
        double new_width = (this.x_bound-(0.05*this.x));
        double new_height = (this.y_bound-(0.05*this.y));
	
	//sets a minimum bounding rectangle
        if (new_width < 20){
            new_width = 20;
        }
        if (new_height < 40){
            new_height = 40;
        }
	
    	this.boundary.x = (int)new_x;
    	this.boundary.y = (int)new_y;
        this.boundary.width = (int)new_width;
        this.boundary.height = (int)new_height;    	
    }
    
    /**
     * This method assures non-player objects remain in the bounds of the panel
     */
    public boolean checkEnemyBounds(){
        if(this.rightImage != null && this.x >= 750){
	    this.setRightImage(null);
	    return true;
        }
        if(this.leftImage != null && this.x <= 0-this.leftImage.getWidth(null)){
	    this.setLeftImage(null);
	    return true;
        }
        return false;
    }
    
    /** Mutator "turning up" beast mode */
    public void turntUp(){
        this.beastMode = true;
    }

    /** Mutator "turning down" beast mode */
    public void turntDown(){
        this.beastMode = false;
    }
    
    /** Accessor returning the state of beast mode */
    public boolean checkBeastMode(){
        return this.beastMode;
    }
    
    /**
     * This method assures the player object remain in the bounds of the panel
     */
    public void checkBounds(){
	
	if(this.rightImage != null){
	    if(this.x >= 750-this.rightImage.getWidth(null)){
		this.x = 750-this.rightImage.getWidth(null);
	    }
        }
        
        if(this.leftImage != null){
	    if(this.x >= 750-this.leftImage.getWidth(null)){
		this.x = 750-this.leftImage.getWidth(null);
	    }
        }
        
        if(this.x <= 0){
	    this.x = 0;
        }
        
        if(this.rightImage != null){  
	    if(this.y >= 600-this.rightImage.getHeight(null)){
        this.y = 600-this.rightImage.getHeight(null);
	    }
        }
        
        if(this.leftImage != null){ 
	    if(this.y >= 600-this.leftImage.getHeight(null)){
		this.y = 600-this.leftImage.getHeight(null);
	    }
        }
        
        if(this.y <= 0){
	    this.y = 0;
        }
    }
}