import processing.core.*;


public class Eye {

	/*
	 * This is a reference to our main class (which extends PApplet) 
	 * 
	 * We need this because in Eclipse all our custom classes are
	 * separate, whereas the processing IDE treats everything as
	 * an inner class of the main PApplet
	 * . 
	 * If we want to use any core processing methods like 
	 * ellipse(), background() etc. we have to have this, and 
	 * call parent.ellipse(), parent.background() etc. to use them
	 * 
	 * 
	 */
	PApplet parent;
	
	// The location of the eye
	int xPos;
	int yPos;
	
	// The size of the various bits
	float radius;
	float radius_pupil;
	float radius_glare;
	
	// Where the pupil is looking
	float xOffset;
	float yOffset;
	
	// Variables for wandering using perlin noise
	float xNoise;
	float yNoise;
	
	float wanderSpeed;
	
	public enum Behaviour{
		FOLLOW_MOUSE,
		IDLE,
		WANDER,
	}
	
	Behaviour behaviour;
	
	/*
	 * Eye( PApplet _parent, int _xPos, int _yPos )
	 * 
	 * Constructor method sets the x and y positions
	 */
	public Eye( PApplet _parent, int _xPos, int _yPos ){
		
		parent = _parent;
		xPos   = _xPos;
		yPos   = _yPos;
		
		// Generate a random value from 30-120 for the radius
		setRadius( (int) parent.random(30,120) );
		
		// Randomise the seed for the perlin noise
		xNoise = parent.random( 0, 10 );
		yNoise = parent.random( 0, 10 );
		wanderSpeed = parent.random((float) 0.01)+(float)0.01;
		
		setBehaviour(Behaviour.WANDER);
	}
	
	
	/*
	 * setRadius( int _radius )
	 * 
	 * Sets the radius of the eye.
	 * Also updates the radiuses for the 
	 * pupil and the glare appropriately 
	 */
	public void setRadius( float _radius ){
		radius       = _radius;
		radius_pupil = radius/2;
		radius_glare = radius_pupil/5;
	}
	
	
	/*
	 * setBehaviour( Behaviour b )
	 * 
	 * Sets the behaviour to the Enum value passed
	 */
	public void setBehaviour( Behaviour _behaviour ) {
		behaviour = _behaviour;
	}
	
	
	/*
	 * updateGaze()
	 */
	private void updateGaze( ) {
		
		switch(behaviour){
			
			case IDLE : 
				break;
			
			case WANDER :
				// TODO : Smooth the transition in and out of wandering state
				
				// Generate a value using perlin noise for X and y offsets
				// Values generated are in the range -2*radius_glare to 2*radius_glare
				xOffset = ( parent.noise(xNoise) * radius_glare*4 ) - radius_glare*2;
				yOffset = ( parent.noise(yNoise) * radius_glare*4 ) - radius_glare*2;
				
				// Increment perlin noise seeds by the wanderSpeed
				xNoise += wanderSpeed;
				yNoise += wanderSpeed;
				
				break;
				
			case FOLLOW_MOUSE : 
				
				// Calculate the differences in X
				// and Y from the mouse's location
				float dx = parent.mouseX-xPos;
				float dy = parent.mouseY-yPos;
				
				// Use these to calculate the angle
				float angle = PApplet.atan2(dy,dx);
				
				// Use the angle to calculate the offsets
				// from the center to the pupil
				float offsetRadius = PApplet.min( radius_glare*2, PApplet.dist( xPos, yPos, parent.mouseX, parent.mouseY ) );
				xOffset = (PApplet.cos(angle)*offsetRadius);
				yOffset = (PApplet.sin(angle)*offsetRadius);
				break;
		}
	}
	
	
	/*
	 * draw()
	 * 
	 * Calculates the position of the pupil based on
	 * the mouse, then draws all the bits of the eye
	 */
	public void draw(){		
		
		updateGaze();
		
		// Draw the main circle in white
		parent.fill(255);
		parent.ellipse(xPos,  yPos, 
				       radius, radius);		
		// Draw the pupil in black
		parent.fill(0);
		parent.ellipse( xPos+xOffset, yPos+yOffset, 
				        radius_pupil, radius_pupil);
		// Draw the glare in white
		parent.fill(255);
		parent.ellipse( xPos+xOffset+radius_glare, yPos+yOffset-radius_glare, 
				        radius_glare, radius_glare );		      
	}


	
}
