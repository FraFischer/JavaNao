import java.util.LinkedList;

import com.aldebaran.qi.Session;

// 
/**
 * The Class Arm represents an Arm of the NAO.
 */
public class Arm 
{	
	
	/** The shoulder of the Arm of the NAO. */
	public Joint shoulder;
	
	/** The elbow of the Arm of the NAO. */
	public Joint elbow;
	
	/** The wrist of the Arm of the NAO. */
	public Joint wrist;
	
	/** The hand of the Arm of the NAO. */
	public Joint hand;
	
	/** The hand sensor of the Arm of the NAO. */
	public Sensor handSensor;
	
	
	
	/**
	 * Instantiates a new arm and all of the Joints and Sensors
	 *
	 * @param side The side of the arm
	 * @param session The session running on the NAO
	 */
	public Arm(Side side, Session session) throws Exception
	{
		String sideString = null;
		String sideStringHand = null;
		switch(side) {
		case LEFT: sideString = "L"; sideStringHand = "Left"; break;
		case RIGHT: sideString = "R"; sideStringHand = "Right"; break;
		}
		
		LinkedList<Axle> axle = new LinkedList<Axle>();
		axle.add(Axle.ROLL);
		axle.add(Axle.PITCH);
		shoulder = new Joint(session, sideString + "Shoulder", axle);
		axle.remove(Axle.PITCH);
		axle.add(Axle.YAW);
		elbow = new Joint(session, sideString + "Elbow", axle);
		axle.remove(Axle.ROLL);
		wrist = new Joint(session, sideString + "Wrist", axle);
		LinkedList<Axle> axle2 = new LinkedList<Axle>();
		axle2.add(Axle.OPEN);
		axle2.add(Axle.CLOSE);
		hand = new Joint(session, sideString + "Hand", axle2);
		handSensor = new Sensor(session, "Hand"+sideStringHand+"BackTouched", sideString+"Hand"+"/Touch/Back/Sensor/Value");
		
	} 
}
