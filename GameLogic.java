import java.io.IOException;
import java.util.ArrayList;

/**
 * GameLogic.java
 *
 * Implements Runnable:
 * This thread makes sure there are constantly enemies and power-ups
 * are being built. CPU is the AI for the game. It moves all of the 
 * enemy sharks and calls the fish factory to create them.
 * 
 * This Class has one method, run():
 * @authors Garo Anguiano-Sainz, Simphiwe Hlophe, Jonathon "Jonny" Liou, and Colby Seyferth
 */

public class GameLogic implements Runnable {
    
    Thread t;
    Moveable movable;
    DrawingPanel panel;
    ArrayList<Moveable> movables = new ArrayList<Moveable>();
    FishFactory fishies;
    int difficulty;
    /**
     * CONSTRUCTOR:
     * Builds and starts the new Thread.
     * Determines the Selected Difficulty
     * Creates the first 12 enemies
     * @param  DrawingPanel: the CPU needs to get a lot of information from the drawing panel
     * @param  Difficulty: it will use this to create a Fish Factory with the proper size and speed param's
     */ 
    
    public GameLogic(DrawingPanel panell, int difficultyFish) throws IOException {
	// Create a new thread
	t = new Thread(this, "Demo Thread");
	panel = panell;
	
	//set difficulty that user chooses
	if (difficultyFish == 1){
	    this.fishies = new FishFactory(9, 1);
	} else if (difficultyFish == 2){
        this.fishies = new FishFactory(10, 2);
	} else {
	    this.fishies = new FishFactory(15, 2);
	}
	
	//Make the first 12 enemies
	for (int i = 0; i<12; i++){
	    Moveable m = this.fishies.buildEnemy();
	    movables.add(m); 
	    panel.addMoveable(m);
	}

	//Start the thread
	t.start(); 
    }
    
    @SuppressWarnings("deprecation")
	public void run(){
	try {
	    //Make a loop that goes forever (or until we kill the thread)
	    while(true) {
		
		// Move all of the enemies/power-ups horizontally 1 speed unit
        	for (int i = 0; i < movables.size(); i++){
		    Moveable m = movables.get(i);
		    //If it is facing right, move it right
		    if (m.getRightImage() != null){
			m.set_x(m.get_x()+m.getSpeed());
        		
        		}
		    //If it is facing left, move it left
		    else {
        			m.set_x(m.get_x()-m.getSpeed());
				
        		}
		    // Update the lists in the panel that keep track of coordinates
		    panel.setBoundary();
		    panel.setCoordinates();
		    
		    // check to see if any of the enemies/power-ups collided with the Player
		    if (panel.checkCollision()){
			// If the player was smaller and was eaten, kill the thread
			if(!panel.getAlive()){
			    this.t.stop();
			    // Remove all of the Moveables from the panel's lists
			    for (int j = 1; j < panel.getMoveable().size(); j++){
				panel.removeMoveable(panel.getMoveable().get(j));
			    }
			}
			//if the player was bigger, remove the enemy from the enemy Movable list
			movables.remove(panel.getIndexer()-1);
			
			// Then make a new one
			Moveable moo = this.fishies.buildEnemy();
			movables.add(moo); 
			panel.addMoveable(moo);
			
		    }
		    // Redraw everything on the screen.
		    panel.repaint();
        	}
        	
		//Check all of the movables to see if they are on the screen
        	for (int i = 0; i < movables.size(); i++){
		    Moveable m = movables.get(i);
		    // If they are off the screen, delete it and remove it from the list
		    if (m.checkEnemyBounds()){
			Moveable deadEnemy = movables.remove(i);
			panel.removeMoveable(deadEnemy);
			deadEnemy = null;
			
			// Then make a new one and add it to the list, update the coords and repaint everything
			Moveable mo = this.fishies.buildEnemy();
			movables.add(mo); 
			panel.addMoveable(mo);
			panel.setCoordinates();
			panel.repaint(); 
		    }
        	}
        	
          Thread.sleep(10);
	    }
	} catch (InterruptedException e) {
	    System.out.println("Game Thread interrupted.");
	}
    }
}