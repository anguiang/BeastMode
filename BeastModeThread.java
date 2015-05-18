import java.io.*;
import javax.sound.sampled.*;

/**
 * BeastModeThread.java
 * Implements Runnable:
 * A thread that is activated when the player enters BeastMode:
 * This acts as a timer so that the player can stay in beast
 * mode for about 13 seconds. 
 *
 * It also plays the music so that you know when you are in BeastMode
 * 
 * This Class has one method, run():
 * @authors Garo Anguiano-Sainz, Simphiwe Hlophe, Jonathon "Jonny" Liou, and Colby Seyferth
 */

public class BeastModeThread implements Runnable {
    
    Thread t;
    Moveable player;
    DrawingPanel panel;
    
    /**
     * CONSTRUCTOR:
     * Builds and starts the new Thread.
     * @param  Player: passes in the player so it can activate its BeastMode instance variable.
     */
    public BeastModeThread (Moveable player){
  	t = new Thread(this, "BeastMode Timer");
  	this.player = player;
  	t.start();
	
    }

    /**
     * This method activates BeastMode and plays the music
     *
     * No parameters
     * @return void
     */
    public void run(){
	
  	// Turns on BeastMode
  	player.turntUp();
	
  	try
	    {
		//Create and play the Audio Clip
		Clip clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(new File("BeastMode.wav")));
		clip.start();
	    }
	catch (Exception exc)
	    {
		exc.printStackTrace(System.out);
	    }
	
	try{
	    //Wait 13.25 seconds before turning BeastMode off.
	    Thread.sleep(13250);
	}
	catch(InterruptedException e){
	}

  	// Turn off BeastMode
  	player.turntDown();
    }
}