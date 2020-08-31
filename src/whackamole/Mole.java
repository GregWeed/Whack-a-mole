//Greg Weed
//CS120
//25 April 2018
package whackamole;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Mole extends JButton {
	
	/*
	 * The icons for the mole button
	 */
	private static ImageIcon mole = new ImageIcon("mole.jpg");
	private static ImageIcon grass = new ImageIcon("grass.jpg");
	private static ImageIcon moleHit = new ImageIcon("mole_hit.jpg");
	
	public static final int MOLE_GAME_OVER = -1; 	// Initial state of the mole button or the mole is between games
													// The mole button's icon is "mole.jpg"
	public static final int MOLE_STATE_DEFAULT = 0; 	// Default state of the mole button when the mole is not up
														// The button's icon image is "grass.jpg"
	public static final int MOLE_STATE_UP = 2; 	// Mole up state
											 	// The mole button's icon is ""
	public static final int MOLE_STATE_DEAD = 3; 	// Mole dead state. The mole has been hit by the player
													// The mole button's icon is "mole_hit.jpg" 
	private int state = Mole.MOLE_GAME_OVER; 	// The current state of the mole button, the default value is the MOLE_GAME_OVER state

	private long stateChangeTime;	// The time when the mole appears

        
        
	public Mole(){	// Constructor of the mole button
		this.setIcon(mole);	// Corresponds to the initial mole button state, which is the MOLE_GAME_OVER state
	}

	public int getState(){	// Getter of the state variable
		return this.state;
	}

	public void setState(int sVar){	// Setter of the state variable
		this.state = sVar;
		changeMoleButtonIcon();
	}

	private void changeMoleButtonIcon()
        {	// Change the button icon according to the current state
		// TODO Use a SWITCH statement to change the button icon according to the current state
            if(this.getState() == -1){
                this.setIcon(mole);
            }
            
            if(this.getState() == 0)
            {
                this.setIcon(grass);
            }
            
            if(this.getState() == 2)
            {
                this.setIcon(mole);
            }
            
            if(this.getState() == 3)
            {
                this.setIcon(moleHit);
            }

	}
        
        //Setters and getters below were unused
	public void setStateChangeTime(long t){	// setter of the molePopsTime
		this.stateChangeTime = t;
	} 
	public long getStateChangeTime(){	// Getter of the molePopsTime variable
		return this.stateChangeTime;
	}
}