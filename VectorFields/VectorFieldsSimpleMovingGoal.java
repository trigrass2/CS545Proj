package VectorFields;

import robocode.*;
import java.lang.*;

/**
 * Simple Robocode robot that uses vector fields to navigate towards a goal. This is used
 * to test the moving goal instance. It is practically identical to the Simple version.
 */
public class VectorFieldsSimpleMovingGoal extends Robot
{
    private boolean foundGoal = false;
    private double goalX, goalY;

    /**
     * If the robot doesn't know where it's goal is at, it tries to locate it by spinning in a circle.
     * Otherwise, it attempts to orient it self to the goal.
     */
    public void run()
    {
        double robotX, robotY, heading;
        double angleToGoal, adjustment;

        while (true)
        {
            if (foundGoal)
            {

                robotX = getX();
                robotY = getY();
                // Robocode headings return 0 degrees as North, 90 as East, etc
                heading = 360 - (getHeading() - 90);
                angleToGoal = Math.toDegrees(Math.atan2(goalY - robotY, goalX - robotX));

                if (angleToGoal < 0)
                {
                    angleToGoal += 360;
                }

                adjustment = angleToGoal - heading;

                if (adjustment >= 360)
                {
                    adjustment -= 360;
                }
                else if (adjustment <= -360)
                {
                    adjustment += 360;
                }

                //if (adjustment > 180 || adjustment < -180)
                    //System.out.println("Out of bounds value: " + adjustment);

                if (adjustment < -180)
                {
                    adjustment += 360;
                }
                else if (adjustment > 180)
                {
                    adjustment -= 360;
                }

                turnLeft(adjustment);
                ahead(1);
            }
            else
            {
                turnRight(1);
            }
        }
    }

    /**
     * On Scanning a robot, set it as our goal.
     */
    public void onScannedRobot(ScannedRobotEvent e)
    {
        double enemyBearing = getHeading() + e.getBearing();
        double enemyX = getX() + e.getDistance() * Math.sin(Math.toRadians(enemyBearing));
        double enemyY = getY() + e.getDistance() * Math.cos(Math.toRadians(enemyBearing));

        foundGoal = true;
        goalX = enemyX;
        goalY = enemyY;
        //System.out.println("Enemy is at " + enemyX + " " + enemyY);
    }
}
