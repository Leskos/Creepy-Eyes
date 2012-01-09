
import java.util.ArrayList;

import processing.core.*;

@SuppressWarnings("serial")
public class ProcessingApplet extends PApplet{

	
	// An ArrayList to store all of our current Eye objects
	// (ArrayLists are better for this  than arrays 'cause 
	// they can dynamically get bigger or smaller as needed )
	private ArrayList<Eye> eyes;
	
	
	/*
	 * Setup()
	 * 
	 * Initialises the applet
	 */
	public void setup() {
		
		size(800,800);
		noStroke();
		smooth();
		
		// Initialise the empty ArrayList of Eye objects
		eyes = new ArrayList<Eye>();
	}
	
	
	/*
	 * draw()
	 * 
	 * Draws the background and all the eyes
	 */
	public void draw() {
		
		// Fill the background
		background(200,0,0);	
		
		// Loop through all eyes and draw them
		for( int i=0; i<eyes.size(); i++ ){
			eyes.get(i).draw();
		}
	}


	/*
	 * mousePressed()
	 * 
	 * Creates a new eye wherever the mouse is pressed
	 */
	public void mousePressed() {
		eyes.add( new Eye(this, mouseX, mouseY) );
	}

	
	/*
	 * keyPressed()
	 * 
	 * Deletes an eye from the ArrayList
	 * (as long as there is one to delete)
	 */
	public void keyPressed(){
		if( eyes.size() > 0 ){
			eyes.remove( eyes.size()-1 );
		}
	}	
	
	
	/*
	 * main( String[] args )
	 * 
	 * Entry point of the program.
	 * Creates and runs a new ProcessingApplet
	 */
	public static void main(String[] args) {
		 PApplet.main(new String[] { "ProcessingApplet" });
	}

}
