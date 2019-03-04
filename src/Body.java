import com.aldebaran.qi.Session;

/**
 * The Class Body represents the body of the NAO.
 */
public class Body 
{
	
	/** The left sonar of the body. */
	public Sensor leftSonar;
	
	/** The right sonarof the body. */
	public Sensor rightSonar;
	
	/** The chest button of the body. */
	public LED chestButtonLED;
	
	/**
	 * Instantiates a new body and all of the Sensors and LEDs
	 *
	 * @param session The session running on the NAO
	 */
	public Body(Session session) throws Exception
	{
		leftSonar = new Sensor(session, "SonarLeftDetected", "US/Left/Sensor/Value");
		rightSonar = new Sensor(session, "SensorRightDetected", "US/Right/Sensor/Value");
		chestButtonLED = new LED(session, "ChestLeds", "ChestLeds", 1);
	}
	

}
