package qLearning;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.HitByBulletEvent;
import java.awt.Graphics2D;
import mdp.MDPUtility;

/*
 * Abstract: Basic bot for implementing policies generated through q-learning.
 * Date: 17 April 2012
 */
public class QTestBot extends Robot {
	//Constants for orientation
	private final double NORTH = 0.0;
	private final double NORTH_ALT = 360.0;
	private final double SOUTH = 180.0;
	private final double EAST = 90.0;
	private final double WEST = 270.0;
	private final double NORTHWEST = 315.0;
	private final double NORTHEAST = 45.0;
	private final double SOUTHWEST = 225.0;
	private final double SOUTHEAST = 135.0;
	//Policy
	private int[] policy = new int[] {4, 4, 2, 4, 6, 1, 2, 2, 2, 4, 2, 6, 6, 7, 0, 6, 1, 7, 6, 1, 5, 4, 6, 6, 0, 1, 2, 4, 0, 7, 5, 4, 4, 4, 4, 2, 6, 4, 4, 6, 2, 2, 6, 4, 2, 6, 4, 2, 5, 4, 2, 2, 2, 7, 6, 4, 6, 1, 4, 7, 3, 4, 4, 4, 4, 4, 6, 4, 2, 2, 6, 4, 4, 6, 4, 1, 4, 4, 6, 7, 4, 3, 5, 6, 4, 4, 7, 6, 1, 5, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 4, 4, 4, 4, 2, 4, 4, 7, 4, 4, 1, 5, 4, 1, 4, 3, 3, 0, 5, 4, 4, 4, 4, 4, 2, 2, 4, 3, 4, 6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 3, 4, 3, 7, 7, 4, 4, 4, 4, 4, 4, 2, 2, 4, 4, 2, 2, 4, 4, 2, 4, 4, 2, 4, 7, 4, 4, 3, 4, 4, 4, 4, 4, 5, 7, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 4, 4, 4, 1, 5, 7, 6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 2, 6, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 6, 4, 4, 6, 4, 2, 4, 4, 2, 4, 4, 4, 4, 4, 4, 0, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 4, 4, 4, 2, 2, 4, 4, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 1, 4, 4, 4, 4, 4, 2, 4, 2, 2, 2, 6, 2, 2, 2, 6, 2, 4, 2, 6, 2, 6, 6, 6, 2, 2, 2, 2, 2, 0, 7, 4, 2, 4, 4, 4, 4, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 0, 4, 2, 6, 6, 2, 4, 2, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 3, 5, 0, 4, 4, 2, 6, 2, 2, 6, 6, 6, 6, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 3, 0, 5, 2, 2, 2, 2, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 3, 6, 5, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 2, 4, 4, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 7, 1, 5, 5, 6, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 3, 2, 1, 2, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 1, 7, 3, 7, 3, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 4, 6, 6, 1, 5, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 2, 1, 4, 5, 6, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 7, 2, 4, 3, 6, 3, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 7, 7, 4, 3, 4, 1, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 6, 7, 3, 3, 2, 3, 6, 4, 3, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 7, 7, 6, 7, 3, 6, 3, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 6, 7, 1, 3, 4, 4, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 1, 6, 6, 1, 3, 1, 5, 1, 1, 1, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3, 6, 6, 6, 6, 6, 1, 1, 7, 1, 1, 2, 7, 2, 6, 6, 1, 2, 4, 6, 6, 6, 6, 6, 6, 6, 6, 1, 6, 6, 6, 6, 6, 7, 7, 7, 7, 1, 6, 1, 1, 6, 6, 6, 7, 6, 1, 6, 2, 4, 6, 7, 7, 1, 3, 6, 7, 6, 1, 1, 6, 1, 4, 2, 6, 1, 6, 5, 6, 3, 1};
    public void run() {
        while (true) {
        	//Each time we get a turn, we find out the state we are in and execute the action our policy tells us to
        	int state = MDPUtility.getStateForXandY(getX(), getY());
        	if (policy[state] == MDPUtility.ACTION_NORTH) {
        		goNorth(10);
        	} else if (policy[state] == MDPUtility.ACTION_SOUTH) {
        		goSouth(10);
        	} else if (policy[state] == MDPUtility.ACTION_EAST) {
        		goEast(10);
        	} else if (policy[state] == MDPUtility.ACTION_WEST) {
        		goWest(10);
        	} else if (policy[state] == MDPUtility.ACTION_NORTHWEST) {
        		goNorthwest(10);
        	} else if (policy[state] == MDPUtility.ACTION_NORTHEAST) {
        		goNortheast(10);
        	} else if (policy[state] == MDPUtility.ACTION_SOUTHWEST) {
        		goSouthwest(10);
        	} else if (policy[state] == MDPUtility.ACTION_SOUTHEAST) {
        		goSoutheast(10);
        	}
        }
}

    /*
     * Fire when we scan a robot. Our policy keeps us oriented toward the enemy almost all the time, so firing straight ahead works, so
     * long as we keep our gun heading the same as our body heading
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(1);
	}

	
	public void onHitByBullet(HitByBulletEvent e) {
        
	}

	/*
	 * This just prints out some information on the Robocode battlefield itself. Useful for debugging purposes.
	 * Shows you the state, heading, gun heading, energy, radar heading, x position, and y position of the MDP 
	 * robot. This is how we verified our motion model and tiling function primarily.
	 */
	public void onPaint(Graphics2D g) {
    	// Set the paint color to red
    	g.setColor(java.awt.Color.WHITE);
    	// Paint a filled rectangle at (50,50) at size 100x150 pixels
    	int state = MDPUtility.getStateForXandY(getX(), getY());
    	String state_str = Integer.toString(state);
    	String xpos_str = Double.toString(getX());
    	String ypos_str = Double.toString(getY());
    	String heading = Double.toString(getHeading());
    	String gunheading = Double.toString(getGunHeading());
    	String energy = Double.toString(getEnergy());
    	String radarheading = Double.toString(getRadarHeading());
    	energy = "Energy: " + energy;
    	xpos_str = "X: " + xpos_str;
    	ypos_str = "Y: " + ypos_str;
    	heading = "Heading: " + heading;
    	gunheading = "Gun Heading: " + gunheading;
    	radarheading = "Radar Heading: " + radarheading;
    	state_str = "State: " + state_str;
    	char[] xpos = xpos_str.toCharArray();
    	char[] ypos = ypos_str.toCharArray();
    	char[] headingchars = heading.toCharArray();
    	char[] gunheadingchars = gunheading.toCharArray();
    	char[] energychars = energy.toCharArray();
    	char[] radarheadingchars = radarheading.toCharArray();
    	char[] statechars = state_str.toCharArray();
    	g.drawChars(statechars, 0, statechars.length, 250, 125);
    	g.drawChars(radarheadingchars, 0, radarheadingchars.length, 250, 110);
    	g.drawChars(energychars, 0, energychars.length, 250, 95);
    	g.drawChars(gunheadingchars, 0, gunheadingchars.length, 250, 80);
    	g.drawChars(headingchars, 0, headingchars.length, 250, 65);
    	g.drawChars(xpos, 0, xpos.length, 250, 50);
    	g.drawChars(ypos, 0, ypos.length, 250, 35);
	}
	
	//Action to turn and head north from any initial orientation
	public void goNorth(int distance) {
		double current_heading = getHeading();
		if (current_heading <= SOUTH) {
			turnLeft(current_heading - NORTH);
		} else {
			turnRight(NORTH_ALT - current_heading);
		}
		ahead(distance);
	}
	
	//Action to turn and head south from any initial orientation
	public void goSouth(int distance) {
		double current_heading = getHeading();
		if (current_heading <= SOUTH) {
			turnRight(SOUTH - current_heading);
		} else {
			turnLeft(current_heading - SOUTH);
		}
		ahead(distance);
	}
	
	//Action to turn and head east from any initial orientation
	public void goEast(int distance) {
		double current_heading = getHeading();
		if (current_heading >= WEST || current_heading < EAST) {
			if (current_heading < 360.0 && current_heading >= WEST) turnRight(NORTH_ALT - current_heading + EAST);
			else turnRight(EAST - current_heading);
		} else {
			turnLeft(current_heading - EAST);
		}
		ahead(distance);
	}
	
	//Action to turn and head west from any initial orientation
	public void goWest(int distance) {
		double current_heading = getHeading();
		if (current_heading > WEST || current_heading <= EAST) {
			if (current_heading < 360.0 && current_heading >= WEST) turnLeft(current_heading - WEST);
			else turnLeft(NORTH_ALT - WEST + current_heading);
		} else {
			turnRight(WEST - current_heading);
		}
		ahead(distance);
	}
	
	//Action to turn and head northwest from any initial orientation
	public void goNorthwest(int distance) {
		double current_heading = getHeading();
		if (current_heading > NORTHWEST || current_heading <= SOUTHEAST) {
			if (current_heading < 360.0 && current_heading >= NORTHWEST) turnLeft(current_heading - NORTHWEST);
			else turnLeft(NORTH_ALT - NORTHWEST + current_heading);
		} else {
			turnRight(NORTHWEST - current_heading);
		}
		ahead(distance);
	}
	
	//Action to turn and head northwest from any initial orientation
	public void goSouthwest(int distance) {
		double current_heading = getHeading();
		if (current_heading >= NORTHEAST && current_heading < SOUTHWEST) {
			turnRight(SOUTHWEST - current_heading);
		} else {
			if (current_heading < 360.0 && current_heading >= SOUTHWEST) turnLeft(current_heading - SOUTHWEST);
			else turnLeft(current_heading + NORTH_ALT - SOUTHWEST);
		}
		ahead(distance);
	}
	
	//Action to turn and head northeast from any initial orientation
	public void goNortheast(int distance) {
		double current_heading = getHeading();
		if (current_heading >= NORTHEAST && current_heading < SOUTHWEST) {
			turnLeft(current_heading - NORTHEAST);
		} else {
			if (current_heading < 360.0 && current_heading >= SOUTHWEST) turnRight(NORTH_ALT - current_heading + NORTHEAST);
			else turnRight(NORTHEAST - current_heading);
		}
		ahead(distance);
	}
	
	//Action to turn and head southeast from any initial orientation
	public void goSoutheast(int distance) {
		double current_heading = getHeading();
		if (current_heading > NORTHWEST || current_heading < SOUTHEAST) {
			if (current_heading < 360.0 && current_heading >= NORTHWEST) turnRight(NORTH_ALT - current_heading + SOUTHEAST);
			else turnRight(SOUTHEAST - current_heading);
		} else {
			turnLeft(current_heading - SOUTHEAST);
		}
		ahead(distance);
	}
}
