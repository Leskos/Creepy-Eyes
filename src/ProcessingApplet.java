
import java.util.ArrayList;

import processing.core.*;

@SuppressWarnings("serial")
public class ProcessingApplet extends PApplet{

	
	// An ArrayList to store all of our current Eye objects
	// (ArrayLists are better for this  than arrays 'cause 
	// they can dynamically get bigger or smaller as needed )
	private ArrayList<Eye> eyes;
	
	private float prevMouseX;
	private float prevMouseY;
	
	/*
	 * Setup()
	 * 
	 * Initialises the applet
	 */
	public void setup() {
		
		size(800,800);
		stroke(0);
		strokeWeight(2);
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
	 * mouseMoved()
	 * 
	 * If the mouse moves fast enough, all eyes look at it
	 */
	public void mouseMoved() {
		float distanceMoved = dist( mouseX, mouseY, prevMouseX,prevMouseY );
		if( distanceMoved > 50 ){
			for( int i=0; i<eyes.size(); i++ ){
				eyes.get(i).setBehaviour( Eye.Behaviour.FOLLOW_MOUSE );
			}
		}
		prevMouseX = mouseX;
		prevMouseY = mouseY;
	}

	
	/*
	 * keyPressed()
	 * 
	 * Execute keyboard commands : 
	 *   '1' - Set all eyes behaviour to WANDER
	 *   '2' - Set all eyes behaviour to IDLE
	 *   '3' - Set all eyes behaviour to FOLLOW_MOUSE
	 *   ' ' - Delete the eye that was last added
	 */
	public void keyPressed(){
		if( key == '1' ){
			for( int i=0; i<eyes.size(); i++ ){
				eyes.get(i).setBehaviour( Eye.Behaviour.WANDER );
			}
		}
		if( key == '2' ){
			for( int i=0; i<eyes.size(); i++ ){
				eyes.get(i).setBehaviour( Eye.Behaviour.IDLE );
			}
		}
		if( key == '3' ){
			for( int i=0; i<eyes.size(); i++ ){
				eyes.get(i).setBehaviour( Eye.Behaviour.FOLLOW_MOUSE );
			}
		}
		if( key == ' ' ){
			if( eyes.size() > 0 ){
				eyes.remove( eyes.size()-1 );
			}
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
