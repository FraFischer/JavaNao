import java.util.LinkedList;
import java.util.List;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

/**
 * The Class Joint represents a joint of the NAO. It could be moved in the given axles.
 */
public class Joint 
{
	
	/** The module ALMotion. */
	private ALMotion motion;
	
	/** The axles this joint can be moved. */
	private List<Axle> movingAxles = new LinkedList<Axle>();
	
	/** The name of the joint. */
	private String name;
	
	/** The speed of the movements in the axle. */
	private double speed = 2.0;
	
	
	
	/**
	 * Instantiates a new joint.
	 *
	 * @param session The session running on the NAO.
	 * @param name The name of the joint.
	 * @param movingAxles The axles, this joint can be moved.
	 * @throws Exception the exception
	 */
	public Joint(Session session, String name, List<Axle> movingAxles) throws Exception
	{
		motion = new ALMotion(session);
		this.name = name;
		this.movingAxles.addAll(movingAxles);
	}
	
	
	
	
	
	/**
	 * Sets the speed of the movement of this joint. Default value is 2.0.
	 *
	 * @param speed The new speed
	 */
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	/**
	 * Gets the speed of the movement of this joint.
	 *
	 * @return The speed
	 */
	public double getSpeed()
	{
		return speed;
	}
	
	
	
	
	
	/**
	 * Move this joint with the given parameter. Not for moving the hands.
	 *
	 * @param axle The axle the movement should be.
	 * @param relation Gives the information, if the movement should be relative or absolute to the current position.
	 * @param theta The square of the movement in DEG.
	 * @param blocked True, if this movement should block the next movement. False if not.
	 */
	public void move(Axle axle, Relation relation, double theta, boolean blocked) throws CallError, InterruptedException
	{
		if(!movingAxles.contains(axle))
		{
			throw new IllegalArgumentException("This motor can't move in this axle");
		}
		boolean absolut = true;
		String movingDirection = name;
		double thetaRad	= 2*Math.PI*theta/360;
				
		switch (relation) {
		case ABSOLUT: absolut = true; break;
		case RELATIV: absolut = false; break;
		}

		switch(axle) {
		case PITCH: movingDirection = name + "Pitch"; break;
		case YAW: movingDirection = name + "Yaw"; break;
		case ROLL: movingDirection = name + "Roll"; break;
		case YAWPITCH: movingDirection = name + "YawPitch"; break;
		default: break;		
		}
		if(blocked)
		{
			motion.angleInterpolation(movingDirection, thetaRad, speed, absolut);
		}
		else
		{
			motion.call("angleInterpolation", movingDirection, thetaRad, speed, absolut);
		}
		
	}
	
	
	
	
	/**
	 *  Move this joint with the given parameter. Only for the hands of the NAO.
	 *
	 * @param axle Gives the information, if the hand should be OPEN or CLOSE.
	 * @param blocked True, if this movement should block the next movement. False if not.
	 */
	public void move(Axle axle, boolean blocked) throws CallError, InterruptedException
	{
		if(!movingAxles.contains(axle))
		{
			throw new IllegalArgumentException("This motor can't move in this axle");
		}
		
		if(blocked)
		{
			switch(axle) {
			case OPEN: motion.openHand(name); break;
			case CLOSE: motion.closeHand(name); break;
			default: break;
			}
		}
		else
		{
			switch(axle) {
			case OPEN: motion.call("openHand", name); break;
			case CLOSE: motion.call("closeHand", name); break;
			default: break;
			}
		}
			
	}
}
