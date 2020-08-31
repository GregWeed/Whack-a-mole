//Greg Weed
//CS120
//25 April 2018
package whackamole;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class WhackAMole extends JFrame implements ActionListener{
	// The icon of the game
	public static ImageIcon appIcon = new ImageIcon("icon.jpg"); 	
	// number of rows and columns in the mole array
	int size  = 7;	
	int square = size * size;
        //Counter for keeping track of your mole hits
        int killCounter = 0;
        //Throughou this program, running dictates when certain loops are disabled/enabled 
	int running = 0;
        // The game thread is triggered by the event of player clicking the btnGameStart button
	GameThread game = new GameThread(); 
        //This thread controls the progress bar and the game duration
        TimeThread time = new TimeThread();     
        // A button group to organize the radio buttons
	ButtonGroup radioButtonGroup=new ButtonGroup();
 
	//JComponents objects are declared here as instance variables*************************************************************************************************
        // Grabs the content pane from the frame
	Container content = this.getContentPane();
        // The array of mole buttons that are used to display the moles and interact with the player
	Mole[] moleArray = new Mole[square];
        // A combo box from which the player selects the life span (in milliseconds) for the moles        
	JComboBox<Integer> comboTimeToLive = new JComboBox();
        // A combo box from which the user selects the time duration (in milliseconds) of dead mole(s) staying above ground
	JComboBox comboDeadTime = new JComboBox();
        //When selected, only one mole appears at a time(maximum)
	JRadioButton rBtnNumMole1 = new JRadioButton("1ֻ");
        //When selected, two moles appear at a time(maximum)
	JRadioButton rBtnNumMole2 = new JRadioButton("2ֻ");
        //When selected,three moles appear at a time(maximum)
	JRadioButton rBtnNumMole3 = new JRadioButton("3ֻ");
        // A combo box from which the player selects the game duration (in seconds)
	JComboBox comboGameTime = new JComboBox();
        // A progress bar that indicate the progress (percentage) of the game
	JProgressBar progBar = new JProgressBar(); 
        // A button to trigger the game
	JButton btnGameStart = new JButton("Start whacking!");
        // A button to terminate the game
	JButton btnTerminate = new JButton("Abortֹ");
        // TextField shows the number of moles that have been struck by the player
	JTextField tFieldNumHits = new JTextField();
        //Used in positioning the screen(center) on any computer
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //Sets menu bar label
        JFrame frame = new JFrame("Welcome to WhackAMole Game created by Greg Weed");
	
        
        //MAIN METHOD*************************************************************************************************************************************************
	public static void main(String[] args)
        {	
		WhackAMole start = new WhackAMole();
	}
 
        //CONSTRUCTOR************************************************************************************************************************************************* 
	WhackAMole(){
		
		/*
		 * The following is a small section of the constructor where 
		 * the components, corresponding layout managers, and labels (with wordings) are added and arranged
		 * in the game window.
		 */

		//Sets frame size, exit on close, location, icon
                frame.setSize(850, 700);
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2- frame.getSize().height/2);
                Container contentPane = frame.getContentPane();
		frame.setIconImage(new ImageIcon("icon.jpg").getImage());
                
                
		//Sets border layout manager for the content pane 
		BorderLayout bLayout = new BorderLayout(0, 35);
                contentPane.setLayout(bLayout);
                
                
		//Declares the controlPanel and the gamePanel
		JPanel controlPanel = new JPanel();
		JPanel gamePanel = new JPanel();

		
		//ControlPanel added to the North region, and gamePanel the Center region to the content pane
                contentPane.add(controlPanel, BorderLayout.NORTH);
                contentPane.add(gamePanel, BorderLayout.SOUTH);
		
		// Configures a grid layout manager for the controlPanel
		controlPanel.setLayout(new GridLayout(4, 1, 0, 10));
		
		//Declares the four panels: timeConfigPanel, numConfigPanel, progressPanel, and statusPanel
		JPanel timeConfigPanel = new JPanel();
                JPanel numConfigPanel = new JPanel();
                JPanel progressPanel = new JPanel();
                JPanel statusPanel = new JPanel();
		
		//Adds the timeConfigPanel, numConfigPanel, progressPanel, and status Panel to controlPanel
                controlPanel.add(timeConfigPanel);
                controlPanel.add(numConfigPanel);
                controlPanel.add(progressPanel);
                controlPanel.add(statusPanel);

		//Configures a grid layout manager for the timeConfigPanel
                timeConfigPanel.setLayout(new GridLayout(0, 4));
                
                
		// Declares four panels and adds them to the timeConfigPanel
		JPanel[] panelTimeControl=new JPanel[4];	
		for(int i=0;i<4;i++){
			panelTimeControl[i]=new JPanel();
			timeConfigPanel.add(panelTimeControl[i]);
		}
		
		// Adds corresponding labels to the timeConfigPanel
		panelTimeControl[0].add(new JLabel("Mole life span (milliseconds): "));
		panelTimeControl[2].add(new JLabel("Mole dead time (milliseconds): "));
		
		// Adds comboTimeToLive and combDeadTime to the timeConfigPanel
		panelTimeControl[1].add(comboTimeToLive);
		panelTimeControl[3].add(comboDeadTime);
                
		//Configures a grid layout manager for the numConfigPanel
		numConfigPanel.setLayout(new GridLayout(0, 4));
                
		//Declares four panels and adds them to the numConfigPanel, you may declare an array of JPanel instances
		JPanel[] panelNumConfig = new JPanel[4];	
		for(int i = 0; i < 4; i++)
                {
			panelNumConfig[i] = new JPanel();
			numConfigPanel.add(panelNumConfig[i]);
		}
                
		//Adds one label and the three radio buttons: rBtnNumMole1, rBtnNumMole2, and rBtnNumMole3 
		panelNumConfig[0].add( new JLabel("Maximum Number Of Moles: "));
                panelNumConfig[1].add(rBtnNumMole1);
                panelNumConfig[2].add(rBtnNumMole2);
                panelNumConfig[3].add(rBtnNumMole3);

		//Configures a flow layout manager (left alignment) for the progressPanel
		progressPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                
		//Adds 3 JPanels and then a label, the comboGameTime, and the progBar to the progressPanel
		JPanel[] progPanelConfig = new JPanel[3];	
		for(int i = 0; i < 3; i++)
                {
			progPanelConfig[i] = new JPanel();
			progressPanel.add(progPanelConfig[i]);
		}
                progPanelConfig[0].add(new JLabel("How long (seconds) do you want to play?"));
                progPanelConfig[1].add(comboGameTime);
                progPanelConfig[2].add(progBar);

		//Configures a gridLayout for the statusPanel
                statusPanel.setLayout(new GridLayout(0, 5));
                JPanel[] statusPanelConfig = new JPanel[5];	
		for(int i = 0; i < 5; i++)
                {
			statusPanelConfig[i] = new JPanel();
			statusPanel.add(statusPanelConfig[i]);
		}
     
		//Adds btnGameStart and btnTerminate to the statusPanel
                statusPanelConfig[0].add(btnGameStart);
                statusPanelConfig[1].add(btnTerminate);
                
		//Adds a new label "You have hit ", tFieldNumHits, and another new label "moles" to the statusPanel
                statusPanelConfig[2].add(new JLabel("You Have Hit:"));
                Dimension d = new Dimension(100, 30);
                statusPanelConfig[3].add(tFieldNumHits);
                tFieldNumHits.setPreferredSize(d);
                statusPanelConfig[4].add(new JLabel("Moles"));
                        
		//Configures a grid layout with 7 rows and 7 columns for the gamePanel
		gamePanel.setLayout(new GridLayout(7, 7));
                
		//Sequentially adds the elements in the moleArray to the gamePanel by for-loop.
                Dimension b = new Dimension(250, 65);
		for(int i = 0; i < 49; i++)
                {
                    moleArray[i] = new Mole();
                    moleArray[i].setPreferredSize(b);
                    moleArray[i].addActionListener(this);
                    gamePanel.add(moleArray[i]);
		}
  
		/*
		 * The following small section is for adding action listeners to the components that 
		 * need to listen to real-time actions
		 */
		//Adds action listeners to btnGameStart, btnTerminate, and mole buttons
                //Mole button action listeners added in the for loop above
		btnGameStart.addActionListener(this);
                btnTerminate.addActionListener(this);

		/*
		 * The following small section is for adding items to the combo boxes
		 */
		//Adds items 300, 400, 500, 600, 700, and 800 milliseconds to the comboTimeToLive component, and sets 500 as the default choice
                int[] timeItems = {300, 400, 500, 600, 700, 800};
                
                
                for(int i = 0; i < timeItems.length; i ++)
                {
                    comboTimeToLive.addItem(timeItems[i]);
                }
                comboTimeToLive.setSelectedItem(timeItems[2]);
		
		//Addz items 200, 300, 400, 500, and 600 milliseconds to the comboDeadTime component, and sets 300 as the default choice
                int[] timeItems2 = {200, 300, 400, 500, 600};
		for(int i = 0; i < timeItems2.length; i ++)
                {
                    comboDeadTime.addItem(timeItems2[i]);
                }
                comboDeadTime.setSelectedItem(timeItems2[1]);

		//Adds items 6, 8, 10, 12, 14, 16, 18, and 20 seconds to the comboGameTime component, and sets 8 as the default choice
		int[] secondSelect = {6,8,10,12,14,16,18,20};
                for(int i = 0; i < secondSelect.length; i ++)
                {
                    comboGameTime.addItem(secondSelect[i]);
                }
                comboGameTime.setSelectedItem(secondSelect[1]);

		/*
		 * Other configurations are done here
		 */
		//MakeS the text field tFieldNumHits not editable, and initial value for the text field is 0
		tFieldNumHits.setEditable(false);
                tFieldNumHits.setText("0");
                
		//Initially makes the btnTerminate button unclickable
		btnTerminate.setEnabled(false);
                
		//Adds three radio buttons to radioButtonGroup variable, and set rBtnNumMole3 as the default choice
                radioButtonGroup.add(rBtnNumMole1);
                radioButtonGroup.add(rBtnNumMole2);
                radioButtonGroup.add(rBtnNumMole3);
                radioButtonGroup.setSelected(rBtnNumMole3.getModel(), true);
		//Sets initial value for progBar as 0
                progBar.setValue(0);
		
		//Makes the window (not resizable)
		frame.setResizable(false);

		//Sets frame visable
                frame.setVisible(true);
	}

        
        
        
        //EVENT HANDLING TAKES PLACE HERE*****************************************************************************************************************************
	
	
	/**
	 * 
	 * This is a test javadoc
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		/*
		 * This method handles different user behaviors
		 */
                //Creates a source object for listening to muliple items
                Object source = event.getSource();
                
                //Listens for game start(button click)
                if(btnGameStart == source)
                {
                    running = 1;
                    //These statements dictates when certain buttons are activated/disabled when the start button is clicked
                    btnGameStart.setEnabled(false);
                    tFieldNumHits.setText("" + killCounter);
                    btnTerminate.setEnabled(true);
                    comboDeadTime.setEnabled(false);
                    comboGameTime.setEnabled(false);
                    comboTimeToLive.setEnabled(false);
                    rBtnNumMole1.setEnabled(false);
                    rBtnNumMole2.setEnabled(false);
                    rBtnNumMole3.setEnabled(false);

                    //Starts a new Game Thread every time that start is clicked
                    game = new GameThread();
                    game.start();
                    //Starts a new Time Thread every time that start is clicked
                    time = new TimeThread();
                    time.start();  
                }
                
                //Listens for game abort(button click)
                if(btnTerminate == source)
                {
                    running = 0;
                    //These statements dictates when certain buttons are activated/disabled when the start button is clicked
                    btnGameStart.setEnabled(true);
                    btnTerminate.setEnabled(false);
                    comboDeadTime.setEnabled(true);
                    comboGameTime.setEnabled(true);
                    comboTimeToLive.setEnabled(true);
                    rBtnNumMole1.setEnabled(true);
                    rBtnNumMole2.setEnabled(true);
                    rBtnNumMole3.setEnabled(true);
                    //Displays the dialog box for game aborted
                    terminateDialog();
                    //Resets mole counter
                    killCounter = 0;
                    //Resets text field
                    tFieldNumHits.setText("0");
                }
   
                //Listens for the user to press a mole button(constant throughout game)
                for(int i = 0; i < moleArray.length; i ++)
                {   
                    //Logic for adding points, only moles that are "UP" and being clicked add points to the scoreboard
                    if(moleArray[i] == source && running == 1 && moleArray[i].getState() == -1){
                        killCounter++;
                        tFieldNumHits.setText("" + killCounter);
                        //Switches the image of a mole to a hammer
                        moleArray[i].setState(3);
                        try {
                            //Delay
                            Thread.sleep(100);  
                        } catch (InterruptedException ex) {
                            Logger.getLogger(WhackAMole.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    //Switches moles to grass
                    else if(moleArray[i].getState() == 3 && moleArray[i] == source)
                    {
                        moleArray[i].setState(0);
                    }
                    //Switches hammers to grass
                    else if(moleArray[i].getState() == -1 && moleArray[i] == source){
                        moleArray[i].setState(0);
                    }         
                }
                
                //For loop resets playing field
                if(running == 0){
                    //changes back to start(moles present)
                     for(int i = 0; i < moleArray.length; i ++)
                     {
                        if(moleArray[i].getState() == 0)
                        {
                            moleArray[i].setState(-1);
                        }
                        
                        //gets rid of any remaining hammers left on the screen if any
                        if(moleArray[i].getState() == 3)
                        {
                            moleArray[i].setState(-1);
                        }  
                    }
                }       
	}//End Event Handling
	  
	//When the game time runs out, this method is invoked
	public void timeUpDialog(){
		JOptionPane.showMessageDialog(this, "Uh-oh! Time is up!\n Your score is " + killCounter + "!");
	}
        
	//When the game is terminated by the player, this method is invoked
	public void terminateDialog(){
		JOptionPane.showMessageDialog(this, "Game stopped by player.\n Your score is " + killCounter +"!");
	}
	
        //GAME THREAD STARTS HERE*************************************************************************************************************************************
	class GameThread extends Thread{

		public void run()
                {     
                    //Integer for mole death time length(miliseconds) returned from combobox
                    int moleDeadTime = (int)comboDeadTime.getSelectedItem();

                    //change mole values/appearances to their grass states when the game starts
                    //Starts game out with all green squares
                    for(int i = 0; i < moleArray.length; i ++)
                    { 
                        if(moleArray[i].getState() == -1)
                        {
                            moleArray[i].setState(0);
                        }
                    }

                    //randomizes moles, constant iteration from the while loop while the game is still running
                    while(running == 1)
                    {
                        //Gets mole life span length
                        int moleLifeSpan = (int)comboTimeToLive.getSelectedItem();
                        //This integer is updated by the logic below to get the radio button values
                        int numberOfMoles = 0;
                        if(rBtnNumMole1.isSelected() == true)
                        {
                            numberOfMoles = 1;
                        }
                        if(rBtnNumMole2.isSelected() == true)
                        {
                            numberOfMoles = 2;
                        }
                        if(rBtnNumMole3.isSelected() == true)
                        {
                            numberOfMoles = 3;
                        }
                    
                        //Randomly generates a mole by changing random array indicies toa different state(from grass to mole)
                        for(int i = 0; i < numberOfMoles; i ++)
                        {
                            if(running == 0)
                            {
                                break;
                            }
                            Random random = new Random();
                            int randomNum = random.nextInt(49);
                            moleArray[randomNum].setState(2);
                            try 
                            {
                                Thread.sleep(moleDeadTime);
                                //changes moles back to grass
                                moleArray[randomNum].setState(-1);
                            } 
                            catch (InterruptedException ex) 
                            {
                                Logger.getLogger(WhackAMole.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try 
                            {
                                //This is where life span for the mole is controlled
                                Thread.sleep(moleLifeSpan);
                            } 
                            catch (InterruptedException ex) 
                            {
                                Logger.getLogger(WhackAMole.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        
                        }
                    
                    
                        //resets board after the selected number of moles pop
                        for(int i = 0; i < moleArray.length; i ++)
                        {
                            if(moleArray[i].getState() == -1)
                            {
                                moleArray[i].setState(0);
                            }
                            if(moleArray[i].getState() == 3)
                            {
                                moleArray[i].setState(0);
                            }
                        }
                    }//END WHILE LOOP  
		}
	}//END GAME THREAD
        
        
        
        //TIME THREAD STARTS HERE***************************************************************************************************************************************
        class TimeThread extends Thread
        {
            public void run()
            {
                //Sets up progress bar
                progBar.setMinimum(0);
                progBar.setMaximum(100);
                progBar.setStringPainted(true);
                //Gets game length from user(seconds)
                int gameLength = (int)comboGameTime.getSelectedItem();
                //This loop iterates 100 times which are the percentages on the progBar
                for (int i = 0; i <= 100; i++) 
                {
                    if(running == 0){
                        break;
                    }
                    //Stores value for each iteration
                    final int currentValue = i;
                    try 
                    {
                        SwingUtilities.invokeLater(new Runnable() 
                        {
                            public void run() 
                            {
                                //Updates the bar with stored value
                                progBar.setValue(currentValue); 
                            }
                        });
                        //8(default combobox for seconds) * 10 * 100(for loop) = 1000 miliseconds
                        // -or- 8 second delay
                        java.lang.Thread.sleep(gameLength * 10);
                    } 
                    catch (InterruptedException e) 
                    {
                         JOptionPane.showMessageDialog(progBar, e.getMessage());
                    }
                }
                //Ends game
                running = 0;
                //Enables/disables buttons/boxes for when the timer runs out
                btnGameStart.setEnabled(true);
                btnTerminate.setEnabled(false);
                comboDeadTime.setEnabled(true);
                comboGameTime.setEnabled(true);
                comboTimeToLive.setEnabled(true);
                rBtnNumMole1.setEnabled(true);
                rBtnNumMole2.setEnabled(true);
                rBtnNumMole3.setEnabled(true);
                //If timer runs out, resets the playing field, displays timeUpDialog.
                if(progBar.getValue() == 100)
                {
                    timeUpDialog();
                    for(int i = 0; i < moleArray.length; i ++)
                    {
                        if(moleArray[i].getState() == 0)
                        {
                            moleArray[i].setState(-1);
                        }
                        if(moleArray[i].getState() == 3)
                        {
                            moleArray[i].setState(-1);
                        }
                    }
                }
                //Resets progress bar, text field for score, and the scoreboard
                progBar.setValue(0);
                tFieldNumHits.setText("0");
                killCounter = 0;   
            }
        }
}//End Program*********************************************************************************************************************************************************