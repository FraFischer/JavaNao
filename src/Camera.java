import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.EventCallback;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALPhotoCapture;
import com.aldebaran.qi.helper.proxies.ALTracker;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;

/**
 * The Class Camera represents both cameras on the head of the NAO. It provides methods to track objects and for doing face detection. 
 * In addition it provides methods for taking pictures and changing the camera.
 */
public class Camera 
{
	
	/** The module ALVideoDevice. */
	private ALVideoDevice device;
	
	/** The module ALPhotoCapture. */
	private ALPhotoCapture photo;
	
	/** The module ALMemory. */
	private ALMemory memory;
	
	/** The module ALTracker. */
	private ALTracker tracker;
	
	/** The module ALFaceDetection. */
	private ALFaceDetection faceDetection;
	
	
	/** true, if an person has been identified. Otherwise false.*/
	private boolean identified;
	
	/** The name(s) of the person(s) that have been detected. */
	private String nameDetected;
	
	/** The distance the tracking should be stopped. */
	private Float distance;	
	
	/** The current selected camera. */
	private int camera;
	
	/** The head of the NAO. */
	private Head head;
	
	/**
	 * Instantiates a new camera and all the used modules.
	 *
	 * @param session The session running on the NAO.
	 * @param head The Object head of the NAO.
	 */
	public Camera(Session session, Head head) throws Exception
	{
		device = new ALVideoDevice(session);
		photo = new ALPhotoCapture(session);
		memory = new ALMemory(session);
		tracker = new ALTracker(session);
		faceDetection = new ALFaceDetection(session);
		
		identified = false;
		nameDetected = "";
		distance = 0.3f;
		camera = device.getActiveCamera();
		this.head = head;
	}
	
	
	
	/**
	 * Changes the selected camera to the top camera.
	 */
	public void useTopCamera() throws CallError, InterruptedException
	{
		device.setActiveCamera(0);
	}
	
	
	/**
	 * Changes the selected camera to the bottom camera.
	 */
	public void useBottomCamera() throws CallError, InterruptedException
	{
		device.setActiveCamera(1);
	}
	
	
	/**
	 * Returns the Integervalue of the selected camera.
	 *
	 * @return The current selected camera as an Integer. 0 means top, 1 means bottom camera.
	 */
	public int getCamera() throws CallError, InterruptedException
	{
		return camera;
	}
	
	
	
	/**
	 * Take a photo with predefined values. Resolution is 640x480, the top camera is selected and the image is saved with the name image.
	 */
	public void takePhoto() throws CallError, InterruptedException
	{
		this.takePhoto("640x480", 0, "image");
	}
	
	
	/**
	 * Take a photo with the given values.
	 *
	 * @param resolution The resolution of the photo.
	 * @param camera The camera that should be used. Is 0 or 1.
	 * @param fileName The name of the file the photo should be saved.
	 */
	public void takePhoto(String resolution, int camera, String fileName) throws CallError, InterruptedException
	{
		int resolutionInt = 0;
		if(resolution.equals("160x120"))
		{
			resolutionInt = 0;		
		}
		else if(resolution.equals("320x240"))
		{
			resolutionInt = 1;
		}
		else if(resolution.equals("640x480"))
		{
			resolutionInt = 2;
		}
		else if(resolution.equals("1280x960"))
		{
			resolutionInt = 3;
		}
		else
		{
			throw new IllegalArgumentException("Can't take this resolution");
		}
		
		if(camera!=0 && camera!=1)
		{
			throw new IllegalArgumentException("Can't choose this Camera");
		}
		
		photo.setResolution(resolutionInt);
		device.setActiveCamera(camera);
		photo.setPictureFormat("jpg");
		photo.takePicture("/home/nao/recordings/cameras/", fileName);
	}
	
	

	/**
	 * Learn the face with the given name.
	 *
	 * @param name The name of the person, the face should be learned.
	 * @return true, if the learning was successfully. false if not.
	 */
	public boolean learnFace(String name) throws CallError, InterruptedException
	{
		head.REyeLED.changeColourAll(Colour.RED);
		head.LEyeLED.changeColourAll(Colour.RED);
		if(faceDetection.learnFace(name))
		{
			head.REyeLED.changeColourAll(Colour.GREEN);
			head.LEyeLED.changeColourAll(Colour.GREEN);
			return true;
		}
		return false;		
	}
	
	
	/**
	 * Clear the database of the NAO of the learned faces.
	 */
	public void clearDatabase() throws CallError, InterruptedException
	{
		faceDetection.clearDatabase();
	}
	
	
	
	 
	/**
	 * Return the name of the person the NAO has detected. If nothing has been detected the value is "nothing". If an unknown 
	 * person has been detected it returns "unknown".
	 *
	 * @return the names
	 */
	public String getNames() throws Exception
	{
		this.faceRecognition();
		while(!identified)
		{
			this.faceRecognition();
		}
		System.out.println(nameDetected);
		faceDetection.unsubscribe("FaceDetected");
		return nameDetected;
	}
	
	
	
	
	/**
	 * Starting the face recognition. Setting/Changing the identified and nameDetected value if something has been detected.
	 */
	private void faceRecognition() throws Exception
	{			
		faceDetection.subscribe("FaceDetected");		
		memory.subscribeToEvent("FaceDetected", new EventCallback<Object>() {
			public void onEvent(Object arg0) throws InterruptedException, CallError {
				identified = true;
				String[] nameSplit = arg0.toString().split("]]],");
				if(nameSplit[1].split("]],")[0].length()!=1)
				{
					String detectedNameString = nameSplit[1].split("]],")[0].substring(2);
					System.out.println(detectedNameString);
					String[] detectedNames = detectedNameString.replace("[", "").replace("]", "").split(",");
					nameDetected = "nothing";	
						
					if(detectedNames.length!=0)
					{
						if(detectedNames[0].equals("4"))
						{
							identified = true;
							nameDetected = "unknown";
						}
						else if(detectedNames[0].equals("2"))
						{
							identified = true;
							nameDetected = detectedNames[1];
						}
						else if(detectedNames[0].equals("3"))
						{
							identified = true;
							nameDetected = detectedNames[1].substring(1, detectedNames[1].length()-1);
						}
						else
						{
							identified = false;
						}
					}
				}
			}
		});
	}
	
	
	
	/**
	 * Gets the learned faces.
	 *
	 * @return The learned faces.
	 */
	public String getLearnedFaces() throws CallError, InterruptedException
	{
		return faceDetection.getLearnedFacesList().toString();
	}
	
	
	
	/**
	 * Tracking an target in the given mode. After reaching the target in the given distance the tracking stops.
	 *
	 * @param target The target, that should be followed.
	 * @param mode The mode of the Tracking.
	 * @return true, if the target has been reached and the behavior stops.
	 */
	public boolean track(Track target, Mode mode) throws Exception
	{	
		String targetString = "";
		switch(target) {
		case REDBALL: targetString = "RedBall"; break;
		case PEOPLE: targetString = "People"; break;
		case FACE: targetString = "Face"; break;
		}
		
		String modeString = "";
		switch(mode) {
		case HEAD: modeString = "Head"; break;
		case WHOLEBODY: modeString = "WholeBody"; break;
		case MOVE: modeString = "Move"; break;
		}
		
		tracker.setMode(modeString);
		tracker.registerTarget(targetString, 0.06);
		
		while(!this.trackReached())
		{
			tracker.track(targetString);
		}
		tracker.stopTracker();
		tracker.unregisterAllTargets();		
		return true;
	}
	
	
	
	
	/**
	 * Check, if the distance between the tracking Object and the NAO is in the target range.
	 *
	 * @return true, if the distance is close enough and the target is reached. false if not.
	 */
	
	private boolean trackReached() throws CallError, InterruptedException
	{
		if(tracker.getTargetPosition().size()==0)
		{
			return false;
		}
		float currentDistance = Float.parseFloat(tracker.getTargetPosition().toString().split(",")[0].substring(1));
		System.out.println("Distance: "+currentDistance);
		if(currentDistance<distance)
		{
			return true;
		}
		return false;
	}
	
	
	
	/**
	 * Sets the maximum distance the NAO should stop the tracking.
	 *
	 * @param distance The new distance.
	 */
	public void setDistance(float distance)
	{
		this.distance = distance;
	}
	
	
	/**
	 * Returns the maximum distance the NAO should stop tracking.
	 *
	 * @return The distance.
	 */
	public float getDistance()
	{
		return distance;
	}
	

}
