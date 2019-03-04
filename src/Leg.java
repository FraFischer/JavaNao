import java.util.LinkedList;

import com.aldebaran.qi.Session;


/**
 * The Class Leg represents a leg of the NAO.
 */
public class Leg 
{
	
	/** The hip of the leg of the NAO. */
	public Joint hip;
	
	/** The ankle of the leg of the NAO. */
	public Joint ankle;
	
	/** The knee of the leg of the NAO. */
	public Joint knee;
	
	/** The sensor of the leg of the NAO. */
	public Sensor legSensor;
	
	/** The LED of the leg of the NAO. */
	public LED legLED;
	
	/**
	 * Instantiates a new leg and all of the Joints, Sensors and LEDs.
	 *
	 * @param side The side of the leg.
	 * @param session The session running on the NAO.
	 */
	public Leg(Side side, Session session) throws Exception
	{
		String sideString = null;
		switch(side) {
		case LEFT: 
			sideString = "L"; 
			legSensor = new Sensor(session, "LeftBumperPressed", "LFoot/Bumper/Right/Sensor/Value", "LFoot/Bumper/Left/Sensor/Value");
			legLED = new LED(session, "LeftFootLeds", "LeftFootLeds", 1);
			break;
		case RIGHT: 
			sideString = "R"; 
			legSensor = new Sensor(session, "RightBumperPressed", "RFoot/Bumper/Right/Sensor/Value", "RFoot/Bumper/Left/Sensor/Value");
			legLED = new LED(session, "RightFootLeds", "RightFootLeds", 1);
			break;
		}
		
		LinkedList<Axle> axle = new LinkedList<Axle>();
		axle.add(Axle.YAWPITCH);
		axle.add(Axle.PITCH);
		axle.add(Axle.ROLL);
		hip = new Joint(session, sideString + "Hip", axle);
		axle.remove(Axle.YAWPITCH);
		ankle = new Joint(session, sideString + "Ankle", axle);
		axle.remove(Axle.ROLL);
		knee = new Joint(session, sideString + "Knee", axle);
	} 
}
