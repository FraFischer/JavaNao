import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.aldebaran.qi.CallError;

/**
 * The Class Example contains three tasks for programming the NAO. They are for beginners, programmers with know-how and good programmer. 
 * Start the examples by creating a new object of "Example" and starting the methods taskOne(), taskTwo() and taskThree(). 
 */
public class Example 
{
	
	/** The nao. */
	Nao nao;	
	
	/** The done keys of task three. */
	static List<Integer> doneKeys = new ArrayList<Integer>();
	
	
	
	public Example(String[] args, String naoUrl) throws Exception
	{
		nao = new Nao(args, naoUrl);
	}
	
	
	
	
	/**
	 * Task one for beginners.
	 * Task: NAO is following a path. The path is known and you can walk along it with steps and 90 degrees turns on the left or right side.
	 */
	public void taskOne() throws CallError, InterruptedException
	{
		nao.step(2);
		nao.turn(Side.LEFT);
		nao.step(3);
		nao.turn(Side.RIGHT);
		nao.step(1);
		nao.turn(Side.LEFT);
		nao.turn(Side.LEFT);
		nao.wipeForehead();
		nao.takePosition(Position.SIT);
	}
	
	
	
	
	/**
	 * Task two for programmers with know-how.
	 * Task: The NAO will record the face of two person with the name "Franziska" and "Markus" (Can be changed in the Code). 
	 * After doing that the NAO waits for contact on his had. Then he starts to track a Person. After reaching the person he tries to
	 * recognize him/her and then telling the name of the Person.
	 */
	public void taskTwo() throws Exception
	{
		nao.head.speech.setLanguage(Language.ENGLISH);
		nao.head.camera.clearDatabase();
		nao.head.camera.useTopCamera();
		
		while(!nao.head.camera.learnFace("Franziska"))
		{
			nao.head.speech.say("Have not learned Franziska");
		}
		nao.head.speech.say("Have learned Franziska");
		
		while(!nao.head.camera.learnFace("Markus"))
		{
			nao.head.speech.say("Have not learned Markus");
		}
		nao.head.speech.say("Have learned Markus");
		while(!nao.head.isTouched())
		{
			nao.head.speech.say("Touch me");
		}
		
		nao.head.camera.setDistance(0.25f);
		boolean reached = false;
		while(!reached)
		{
			reached = nao.head.camera.track(Track.FACE, Mode.MOVE);
		}	
		String detected = nao.head.camera.getNames();
		nao.head.speech.say("I recognized " + detected);
	}



	
	
	
	
	 
	
	/**
	 * Task three for good programmers.
	 * The NAO is playing a version of the game "I pack my bag". Every round he is doing a defined order of actions. An action is a moving of one 
	 * part of the body or the lighting of an LED. After doing all the actions the player have 3 seconds time to touch the touching sensor of the
	 * part of the body of the first action. Did he touch the right part, he has 3 seconds time to touch the second part and so on.
	 * After each round the NAO adds one more action to the list.
	 */
	public void taskThree() throws Exception
	{
		nao.head.speech.setLanguage(Language.ENGLISH);
		iPackMyBag(1);
	}
	
	
	
	/**
	 * Representing on round of the game "I pack my bag". In each round the NAO add a new action. The actions are representing by an key. 
	 * @param round: The number of the round, that is played now.
	 */
	private void iPackMyBag(int round) throws Exception
	{
		nao.takePosition(Position.STAND);
		Random random = new Random();
		int newKey = 1 + random.nextInt(5);
		
		doneKeys.add(newKey);
		nao.head.speech.say("Round "+round);
		for(int i=0; i<round; i++)
		{
			action(doneKeys.get(i));
		}
	
		
		for(int i=0; i<round; i++)
		{
			boolean done = sense(doneKeys.get(i));
			if(done)
			{
				if(i==round-1)
				{
					iPackMyBag(round+1);
				}
			}
			else
			{
				i = round;
			}
		}
		
	}
	
	
	
	
	/**
	 * This method is starting the next action. This can be a moving of a part of the body or an lighting of a LED. The actions are here defined. 
	 * Current there are 5 possible actions.
	 *
	 * @param actNow: The key of the next action.
	 */
	private void action(int actNow) throws CallError, InterruptedException
	{
		switch(actNow)
		{
		case 1: nao.head.head.move(Axle.YAW, Relation.RELATIV, 45, true);
				nao.head.head.move(Axle.YAW, Relation.RELATIV, -45, false);
				break;
		case 2: nao.lArm.shoulder.move(Axle.PITCH, Relation.RELATIV, 45, true);
				nao.lArm.shoulder.move(Axle.PITCH, Relation.RELATIV, -45, false);
				break;
		case 3: nao.rArm.elbow.move(Axle.ROLL, Relation.RELATIV, 45, true);
		 		nao.rArm.elbow.move(Axle.ROLL, Relation.RELATIV, -45, false);
		 		break;
		case 4: nao.lLeg.legLED.changeColourAll(Colour.RED);
				TimeUnit.SECONDS.sleep(2);
				nao.lLeg.legLED.changeColourAll(Colour.WHITE);
				break;
		case 5: nao.head.LEyeLED.changeColourAll(Colour.RED);
				nao.head.REyeLED.changeColourAll(Colour.RED);
				TimeUnit.SECONDS.sleep(2);
				nao.head.LEyeLED.changeColourAll(Colour.WHITE);
				nao.head.REyeLED.changeColourAll(Colour.WHITE);
				break;
		}
	}
	
	
	
	
	
	/**
	 * This method checks after three seconds, if the right sensor is touched. The keys of the sensor must fit to the keys of the action.
	 *
	 * @param senseNow: The key of the sensor, that should be touched.
	 * @return true, if the right sensor had been touched after 3 seconds. False if not.
	 */
	private boolean sense(int senseNow) throws Exception
	{
		nao.head.speech.say("Go");
		TimeUnit.SECONDS.sleep(3);
		boolean touched = false;
		switch(senseNow)
		{
			case 1: touched = nao.head.isTouched(); break;
			case 2: touched = nao.lArm.handSensor.isPressed(); break;
			case 3: touched = nao.rArm.handSensor.isPressed(); break;
			case 4: touched = nao.lLeg.legSensor.isPressed(); break;
			case 5: touched = nao.head.isTouched(); break;
		}
		if(touched)
		{
			nao.head.speech.say("Right");
			return true;
		}
		else
		{
			nao.head.speech.say("Wrong");
			return false;
		}
	}
	
	
}