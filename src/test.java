import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.aldebaran.qi.CallError;

// TODO: Auto-generated Javadoc
/**
 * The Class test.
 */
public class test 
{
	
	/** The nao. */
	static Nao nao;	
	
	/** The done moving. */
	static List<Integer> doneMoving = new ArrayList<Integer>();
	
	/** The done sensor. */
	static List<Integer> doneSensor = new ArrayList<Integer>();
	
	/** The done keys. */
	static List<Integer> doneKeys = new ArrayList<Integer>();
	
	
	/**
	 * JavaDoc test der Klasse main.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception 
	{ 
		String url = "tcp://169.254.129.162:9559";
		nao = new Nao(args, url);	
		nao.takePosition(Position.STAND);
		nao.head.speech.setLanguage(Language.GERMAN);
		aufgabeDrei(1);
		
		nao.head.speech.setLanguage(Language.ENGLISH);
		while(!nao.head.mHeadSensor.isPressed())
		{
			if(nao.head.fHeadSensor.isPressed())
			{
				nao.head.speech.say("Ouch");
			}
		}
		nao.head.speech.say("No longer say ouch");
	}
	
	
	
	
	
	
	/**
	 * Aufgabe zwei.
	 *
	 * @throws Exception the exception
	 */
	public static void aufgabeZwei() throws Exception
	{
		nao.head.speech.setLanguage(Language.GERMAN);
		nao.head.camera.clearDatabase();
		nao.head.camera.useTopCamera();
		
		while(!nao.head.camera.learnFace("Franziska"))
		{
			nao.head.speech.say("Franziska nicht gelernt");
		}
		nao.head.speech.say("Franziska gelernt");
		
		while(!nao.head.camera.learnFace("Markus"))
		{
			nao.head.speech.say("Markus nicht gelernt");
		}
		nao.head.speech.say("Markus gelernt");
		while(!nao.head.isTouched())
		{
			nao.head.speech.say("Drücke mich");
		}
		
		nao.head.camera.setDistance(0.25f);
		boolean reached = false;
		while(!reached)
		{
			reached = nao.head.camera.track(Track.FACE, Mode.MOVE);
		}	
		String detected = nao.head.camera.getNames();
		nao.head.speech.say("Ich habe " + detected + " erkannt");
	}



	/**
	 * Aufgabe eins.
	 *
	 * @throws CallError the call error
	 * @throws InterruptedException the interrupted exception
	 */
	public static void aufgabeEins() throws CallError, InterruptedException
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
	 * Aufgabe drei.
	 *
	 * @param runde the runde
	 * @throws Exception the exception
	 */
	public static void aufgabeDrei(int runde) throws Exception
	{
		nao.takePosition(Position.STAND);
		Random random = new Random();
		int neuerKey = 1 + random.nextInt(5);
		
		doneKeys.add(neuerKey);
		//doneSensor.add(neu);
		nao.head.speech.say("Runde "+runde);
		for(int i=0; i<runde; i++)
		{
			move(doneKeys.get(i));
		}
	
		
		for(int i=0; i<runde; i++)
		{
			boolean done = sense(doneKeys.get(i));
			if(done)
			{
				if(i==runde-1)
				{
					aufgabeDrei(runde+1);
				}
			}
			else
			{
				i = runde;
			}
		}
		
	}
	
	
	
	
	/**
	 * Move.
	 *
	 * @param moveNow the move now
	 * @throws CallError the call error
	 * @throws InterruptedException the interrupted exception
	 */
	public static void move(int moveNow) throws CallError, InterruptedException
	{
		switch(moveNow)
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
	 * Sense.
	 *
	 * @param senseNow the sense now
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean sense(int senseNow) throws Exception
	{
		nao.head.speech.say("los");
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
			nao.head.speech.say("Richtig");
			return true;
		}
		else
		{
			nao.head.speech.say("Falsch");
			return false;
		}
	}
	
	
}