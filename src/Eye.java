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
	
	// Where what it is looking at is located
	int xTarget;
	int yTarget;
	
	// Where the pupil is meant to look
	float xOffsetTarget;
	float yOffsetTarget;
	
	// Where the pupil is actually looking
	float xOffset;
	float yOffset;
	
	
	// How quickly to move between intended and actual location
	float lerpFactor;
	
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
		
		wanderSpeed = parent.random( (float) 0.01 )+(float)0.01;
		
		lerpFactor = parent.random( (float)0.1, (float)0.3 );
		
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
				
				// Look at whatever we are currently looking at
				//xTarget = (int) (xPos + xOffset);
				//yTarget = (int) (yPos + yOffset);
				break;
			
			case WANDER :
				
				// Look at a random target
				
				// 1 in 200 chance of picking a new target each update
				if( parent.random(1) < 0.005  ){
					xTarget = (int) parent.random( xPos-10, xPos+10 );
					yTarget = (int) parent.random( yPos-10, yPos+10 );
				}
				
				// 1 in 100 chance of moving the target slightly
				if( parent.random(1) < 0.01  ){
					xTarget += parent.random( -50, 50 );
					yTarget += parent.random( -50, 50 );
				}
				
								
				break;
				
			case FOLLOW_MOUSE :
				
				// Look at the wherever the mouse is located
				xTarget = parent.mouseX;
				yTarget = parent.mouseY;
				
				break;
		}
		
		// Calculate the differences in X
		// and Y from the targets location
		float dx = xTarget-xPos;
		float dy = yTarget-yPos;
		
		// Use these to calculate the angle
		float angle = PApplet.atan2(dy,dx);
		
		// Use the angle to calculate the offsets
		// from the center to the pupil
		float offsetRadius = PApplet.min( radius_glare*2, PApplet.dist( xPos, yPos, parent.mouseX, parent.mouseY ) );
		xOffsetTarget = (PApplet.cos(angle)*offsetRadius);
		yOffsetTarget = (PApplet.sin(angle)*offsetRadius);
	}
	
	
	/*
	 * draw()
	 * 
	 * Calculates the position of the pupil based on
	 * the mouse, then draws all the bits of the eye
	 */
	public void draw(){		
		
		updateGaze();
		
		xOffset = xOffset +  lerpFactor*(xOffsetTarget-xOffset);
		yOffset = yOffset +  lerpFactor*(yOffsetTarget-yOffset);
		
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
