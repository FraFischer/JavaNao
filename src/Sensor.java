import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALMemory;

/**
 * The Class Sensor represents a sensor of the NAO.
 */
public class Sensor 
{
	
	/** The name of the event according to the sensor. */
	private String nameEvent;
	
	/** The information, if the sensor is pressed or not. */
	private boolean pressed;
	
	
	
	/**
	 * Instantiates a new sensor of the NAO. Just for instantiates the bumpers.
	 *
	 * @param session The session running on the NAO.
	 * @param nameEvent The name of the event according to the sensor.
	 * @param nameDataR The name of the path for the current value of the right part of the bumper
	 * @param nameDataL The name of the path for the current value of the left part of the bumper
	 */
	public Sensor(Session session, String nameEvent, String nameDataR, String nameDataL) throws Exception
	{
		ALMemory memory = new ALMemory(session);
		if(memory.getData("Device/SubDeviceList/"+nameDataR).toString().equals("0.0") && memory.getData("Device/SubDeviceList/"+nameDataL).toString().equals("0.0"))	
		{
			pressed = false;
		}
		else
		{
			pressed = true;
		}
		this.nameEvent = nameEvent;
		this.run(session);
	}
	
	
	
	
	
	
	/**
	 * Instantiates a new sensor of the NAO. Not for instantiates the bumpers. The current value of the sensor
	 * is representing in the parameter pressed.
	 *
	 * @param session The session running on the NAO.
	 * @param nameEvent The name of the event according to the sensor.
	 * @param nameDataR The name of the path for the current value of the sensor.
	 */
	public Sensor(Session session, String nameEvent, String nameData) throws Exception
	{
		ALMemory memory = new ALMemory(session);
		if(memory.getData("Device/SubDeviceList/"+nameData).toString().equals("0.0") )
		{
			pressed = false;
		}
		else
		{
			pressed = true;
		}
		pressed = false;
		this.nameEvent = nameEvent;
		this.run(session);
	}
	
	
	/**
	 * Run is adding a listener to the Event of the Sensor. When the sensor is activated 
	 * (value>0) pressed is set to true. Otherwise pressed is set to false.
	 *
	 * @param session The session running on the NAO.
	 */
	private void run(Session session) throws Exception 
	{		
		ALMemory memory = new ALMemory(session);
		memory.subscribeToEvent(nameEvent, new EventCallback<Float>() {
			public void onEvent(Float number)
			{
				if(number>0)
				{
					pressed = true;					
				}
				else
				{
					pressed = false;
				}
			}
		});
	}
	
	
	
	/**
	 * Checks, if the sensor is pressed or not.
	 *
	 * @return true if the sensor is pressed. Otherwise false.
	 */
	public boolean isPressed()
	{
		return pressed;
	}
	
	
}
