
import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;


/**
 * The Class Nao represents the NAO.
 */
public class Nao 
{
	
	/** The left arm of the NAO. */
	public Arm lArm;
	
	/** The right arm of the NAO. */
	public Arm rArm;
	
	/** The body of the NAO. */
	public Body body;
	
	/** The left leg of the NAO. */
	public Leg lLeg;
	
	/** The right leg of the NAO. */
	public Leg rLeg; 
	
	/** The head of the NAO. */
	public Head head;
	
	/** The module ALPosture. */
	private ALRobotPosture posture;
	
	/** The module ALMotion. */
	private ALMotion motion;
	
	/**
	 * Instantiates a new nao and all of the body parts.
	 *
	 * @param args The args
	 * @param robotUrl The IP address and the port of the NAO. It hast to be the given format tcp://IP-address:port 
	 */
	public Nao(String[] args, String robotUrl) throws Exception
	{
		//169.254.129.162
	    Application application  = new Application(args, robotUrl);
	    application.start();
	    posture = new ALRobotPosture(application.session());
	    motion = new ALMotion(application.session());
	    
	    lArm = new Arm(Side.LEFT, application.session());
		rArm = new Arm(Side.RIGHT, application.session());
		lLeg = new Leg(Side.LEFT, application.session());
		rLeg = new Leg(Side.RIGHT, application.session());
		head = new Head(application.session());
		body = new Body(application.session());
	}
	
	
	
	/**
	 * The NAO is moving in the given position.
	 *
	 * @param position The new position
	 */
	public void takePosition(Position position) throws CallError, InterruptedException
	{
		String postureString = "";
		switch(position) {
		case STAND: postureString = "Stand"; break;
		case STANDINIT: postureString = "StandInit"; break;
		case STANDZERO: postureString = "StandZero"; break;
		case CROUCH: postureString = "Crouch"; break;
		case SIT: postureString = "Sit"; break;
		case SITRELAX: postureString = "SitRelax"; break;
		case LYINGBELLY: postureString = "LyingBelly"; break;
		case LYINGBACK: postureString = "LyingBack"; break;
		}
		posture.goToPosture(postureString, (float)0.8);
	}
	
	
	
	
	/**
	 * NAO is doing a step.
	 *
	 * @param number The number of steps the NAO should do.
	 */
	public void step(int number) throws CallError, InterruptedException
	{
		float x = (float) (0.04 * number);
		this.moveTo(x, 0, 0);
	}
	
	
	/**
	 * NAO is turning to the given side.
	 *
	 * @param side The side the NAO should turn.
	 */
	public void turn(Side side) throws CallError, InterruptedException
	{
		float x = 90;
		switch(side) {
		case RIGHT: x = -90; break;
		default: break; 
		}
		this.moveTo(0, 0, x);
	}
	
	/**
	 * Move to the given position.
	 *
	 * @param x The parameter x of the position relative to the NAO
	 * @param y The parameter y of the position relative to the NAO
	 * @param theta The parameter angle in deg of the position relative to the NAO
	 */
	public void moveTo(float  x, float y, float theta) throws CallError, InterruptedException
	{
		motion.setMoveArmsEnabled(true, true);
		motion.moveTo(x, y, (float) ((theta*Math.PI)/180));
	}
	
	
	/**
	 * NAO stands init, waves and then stands init again.
	 */

	public void wave() throws CallError, InterruptedException
	{
		this.takePosition(Position.STANDINIT);
		
		double speed = 0.5;
		this.rArm.elbow.setSpeed(speed);
		this.rArm.shoulder.setSpeed(speed);
		this.rArm.wrist.setSpeed(speed);
		this.rArm.hand.setSpeed(speed);
		this.lArm.shoulder.setSpeed(speed);
		this.lArm.elbow.setSpeed(speed);
		this.lArm.wrist.setSpeed(speed);
		this.head.head.setSpeed(speed);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 12.2, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -14.9, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 2.4, true);		
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -16.7, true); 
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 9.5, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 64.1, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 20.7, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT,-79, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -46.6, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 5.5, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, 16.7, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -7.7, false);	

		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -65.1, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -44.1, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 20.1, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 29.2, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -17.9, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 53.8, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 12.1, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, -71.6, true); 
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -39.5, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 8.4, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -13.1, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -21.5, true);
		this.rArm.hand.move(Axle.OPEN, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -64.4, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -32.2, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 49.2, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 21.6, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -17.4, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -19.5, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -23.6, false);		
				
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -72.1, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -49.8, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 11.4, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 20.4, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -17.4, true);
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -3.7, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -24.0, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -66.3, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -18.8, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 88.5, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 21.6, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -17.4, true);
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -9.9, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -28.5, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -25.0, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -15.6, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 57.9, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 55.5, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -9.3, true);
		this.rArm.hand.move(Axle.CLOSE, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 58.4, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -14.8, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 72.3, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 47.7, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 10.3, true);
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -1.7, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -22.2, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 80.0, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -17.1, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 57.9, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 79.6, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 0.4, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 79.7, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 17.1, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, -57.9, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -79.5, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 0.4, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -0.1, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -0.1, false);
		
		this.takePosition(Position.STANDINIT);
		speed = 2.0;
		this.rArm.elbow.setSpeed(speed);
		this.rArm.shoulder.setSpeed(speed);
		this.rArm.wrist.setSpeed(speed);
		this.rArm.hand.setSpeed(speed);
		this.lArm.shoulder.setSpeed(speed);
		this.lArm.elbow.setSpeed(speed);
		this.lArm.wrist.setSpeed(speed);
		this.head.head.setSpeed(speed);
	}
	
	
	/**
	 * NAO stands init, wipes forehead and then stands init again.
	 */
	public void wipeForehead() throws CallError, InterruptedException
	{
		this.takePosition(Position.STANDINIT);
		
		double speed = 1.0;
		this.rArm.elbow.setSpeed(speed);
		this.rArm.shoulder.setSpeed(speed);
		this.rArm.wrist.setSpeed(speed);
		this.rArm.hand.setSpeed(speed);
		this.lArm.shoulder.setSpeed(speed);
		this.lArm.elbow.setSpeed(speed);
		this.lArm.wrist.setSpeed(speed);
		this.head.head.setSpeed(speed);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 4.6, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -2.3, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 72.3, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -7.1, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -2.4, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 56.5, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 18.3, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT,-56.5, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -55.2, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 21.7, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, 3.5, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -1.7, false);
	
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -9.8, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -1.7, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 75.1, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -10.9, true);			
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -1.5, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -13.4, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -38.9, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -5.3, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 81.2, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -17.6, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -3.0, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 49.6, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 12.9, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT,-49.8, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -47.5, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, 22.8, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -48.9, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -39.7, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 85.6, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -2.5, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -3.1, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 50.8, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 12.1, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT,-47.3, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -57.3, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 21.7, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, 19.3, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -9.3, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, -35.0, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -47.6, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 71.1, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 43.7, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -3.7, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 52.9, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 22.1, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT,-55.9, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -53.3, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, 12.2, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -1.7, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 33.7, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -13.0, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 69.7, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 67.1, true);
		this.rArm.wrist.move( Axle.YAW, Relation.ABSOLUT, -3.1, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 57.9, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 21.0, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT,-55.5, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -67.8, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 21.7, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, 2.2, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -0.8, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 48.2, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -12.4, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 56.6, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 50.1, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, -2.5, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 57.9, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 19.8, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT,-55.5, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -68.6, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 21.7, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -0.4, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -1.4, false);
		
		this.rArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 80.0, true);
		this.rArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, -17.1, true);
		this.rArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, 57.9, true);
		this.rArm.elbow.move(Axle.YAW, Relation.ABSOLUT, 79.6, true);
		this.rArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 0.4, true);		
		this.lArm.shoulder.move(Axle.PITCH, Relation.ABSOLUT, 79.7, true);
		this.lArm.shoulder.move(Axle.ROLL, Relation.ABSOLUT, 17.1, true);
		this.lArm.elbow.move(Axle.ROLL, Relation.ABSOLUT, -57.9, true);
		this.lArm.elbow.move(Axle.YAW, Relation.ABSOLUT, -79.5, true);
		this.lArm.wrist.move(Axle.YAW, Relation.ABSOLUT, 0.4, true);		
		this.head.head.move(Axle.PITCH, Relation.ABSOLUT, -0.1, true);
		this.head.head.move(Axle.YAW, Relation.ABSOLUT, -0.1, false);
		
		this.takePosition(Position.STANDINIT);
		speed = 2.0;
		this.rArm.elbow.setSpeed(speed);
		this.rArm.shoulder.setSpeed(speed);
		this.rArm.wrist.setSpeed(speed);
		this.rArm.hand.setSpeed(speed);
		this.lArm.shoulder.setSpeed(speed);
		this.lArm.elbow.setSpeed(speed);
		this.lArm.wrist.setSpeed(speed);
		this.head.head.setSpeed(speed);
	}
}
