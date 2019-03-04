import java.util.LinkedList;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALLeds;

/**
 * The Class Head represents the head of the NAO.
 */
public class Head 
{
	
	/** The joint of the head. */
	public Joint head;
	
	/** The touch sensor in the front of the head. */
	public Sensor fHeadSensor;
	
	/** The touch sensor in the middle of the head. */
	public Sensor mHeadSensor;
	
	/** The touch sensor in the rear of the head. */
	public Sensor rHeadSensor;
	
	/** The LEDs of the left eye of the NAO. */
	public LED LEyeLED;
	
	/** The LEDs of the right eye of the NAO. */
	public LED REyeLED;
	
	/** The LEDs of the left ear of the NAO. */
	public LED LEarLED;
	
	/** The LEDs of the right ear of the NAO. */
	public LED REarLED;
	
	/** The LEDs of the head of the NAO. */
	public LED brainLED;
	
	/** The cameras at the head of the NAO. */
	public Camera camera;
	
	/** The speech/mouth of the NAO. */
	public Speech speech;
	
	/** The module ALLeds. */
	private ALLeds leds;
	
	
	/**
	 * Instantiates a new head and all of the Sensors, Joints and LEDs.
	 *
	 * @param session The session running on the NAO
	 */
	public Head(Session session) throws Exception
	{
		LinkedList<Axle> axle = new LinkedList<Axle>();
		axle.add(Axle.YAW);
		axle.add(Axle.PITCH);
		head = new Joint(session, "Head", axle);
		fHeadSensor = new Sensor(session, "FrontTactilTouched", "Head/Touch/Front/Sensor/Value");
		mHeadSensor = new Sensor(session, "MiddleTactilTouched", "Head/Touch/Middle/Sensor/Value");
		rHeadSensor = new Sensor(session, "RearTactilTouched", "Head/Touch/Rear/Sensor/Value");
		LEyeLED = new LED(session, "RightFaceLeds", "RightFaceLed", 8);
		REyeLED = new LED(session, "LeftFaceLeds", "LeftFaceLed", 8);
		LEarLED = new LED(session, "LeftEarLeds", "LeftFaceLed", 10);
		REarLED = new LED(session, "RightEarLeds", "RightEarLed", 10);
		brainLED = new LED(session, "BrainLeds", "Brain", 12);
		camera = new Camera(session, this);
		speech = new Speech(session);
		leds = new ALLeds(session);
	}
	
	
	/**
	 * Checks if any touch sensor of the head is stimulated.
	 *
	 * @return true, if any is touched. If not false.
	 */
	public boolean isTouched()
	{
		if(fHeadSensor.isPressed() || mHeadSensor.isPressed() || rHeadSensor.isPressed())
		{
			return true;
		}
		return false;
	}	
	
	
	
	/**
	 * Make an random colour animation of the eyes of the NAO.
	 *
	 * @param duration The duration of the animation in seconds
	*/
	public void randomEyes(Float duration) throws CallError, InterruptedException
	{
		leds.randomEyes(duration);
	}
}