import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALLeds;

/**
 * The Class LED represents a group of LEDs of the NAO. With this class the colour and intensity of the LEDs can be changed.
 */
public class LED 
{
	
	/** The name of the LED Group. */
	private String name;
	
	/** The name of a single lamp of the LED Group. */
	private String nameSingle;
	
	/** The number of lamps in the LED Group. */
	private int number;
	
	/** The module ALLeds. */
	private ALLeds leds;
	
	/** The duration of an colour change in the LED Group. */
	private float duration;
	
	
	
	/**
	 * Instantiates a new LED.
	 *
	 * @param session The session running on the NAO.
	 * @param name The name of the LED Group.
	 * @param nameSingle The name of a single lamp of the LED Group.
	 * @param numbers The number of lamps in the LED Group.
	 */
		public LED(Session session, String name, String nameSingle, int numbers) throws Exception
	{
		this.number = numbers;
		this.name = name;
		this.nameSingle = nameSingle;
		leds = new ALLeds(session);
		duration = 0;
	}
	
	
	
	/**
	 * Change the colour of all the lamps in this LED.
	 *
	 * @param colour The new colour.
	*/
	public void changeColourAll(Colour colour) throws CallError, InterruptedException
	{
		if(name.contains("Ear"))
		{
			throw new IllegalArgumentException("You can't change the colour of the ears");
		}
		if(name.contains("brain"))
		{
			throw new IllegalArgumentException("You can't change the colour of the head");
		}
		String colourName = colourToString(colour);
		leds.fadeRGB(name, colourName, duration);
	}
	
	
	
	
	/**
	 * Change the colour of an single lamp of this LED.
	 *
	 * @param colour The new colour
	 * @param lamp The number of the lamp that should be changed.
	 */
	public void changeColourSingle(Colour colour, int lamp) throws CallError, InterruptedException
	{
		if(name.contains("Ear"))
		{
			throw new IllegalArgumentException("You can't change the colour of the ears");
		}
		if(name.contains("brain"))
		{
			throw new IllegalArgumentException("You can't change the colour of the head");
		}
		if(lamp >= number)
		{
			throw new IllegalArgumentException("This LED does not exists");
		}
		String changeLED = nameSingle + lamp;			
		String colourName = colourToString(colour);
		leds.fadeRGB(changeLED, colourName, duration);
	}
	
	
	/**
	 * Sets the duration of the colour changing. Default value is 0.
	 *
	 * @param newDuration The new duration in seconds.
	 */
	public void setDuration(float newDuration)
	{
		duration = newDuration;
	}
	
	
	/**
	 * Sets the intensity of the LEDs. Default value is 1.
	 *
	 * @param intensity The new intensity of the LED. Float between 0 and 1.
	 */
	public void setIntensity(Float intensity) throws CallError, InterruptedException
	{
		leds.setIntensity(name, intensity);
	}
	
	
	/**
	 * Reset all LEDs of the NAO.
	 */
	public void resetAllLeds() throws CallError, InterruptedException
	{
		leds.reset("AllLeds");
	}
	
	
	
	/**
	 * Change the Enum value of the colour to a String.
	 *
	 * @param colour The colour.
	 * @return The value as a String.
	 */
	private String colourToString(Colour colour)
	{
		String colourString = null;
		switch(colour) {
		case WHITE: colourString = "white";break;
		case RED: colourString = "red";break;
		case GREEN: colourString = "green";break;
		case BLUE: colourString = "blue";break;
		case YELLOW: colourString = "yellow";break;
		case MAGENTA: colourString = "magenta";break;
		case CYAN: colourString = "cyan";break;
		}		
		return colourString;
	}
}

