import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAnimatedSpeech;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

/**
 * The Class Speech represents the speech/mouth of the NAO.
 */
public class Speech 
{
	
	/** The module ALAnimatedSpeech. */
	private ALAnimatedSpeech animatedSpeech;
	
	/** The module ALTextToSpeech. */
	private ALTextToSpeech textToSpeech;
	
	/**
	 * Instantiates a new speech/mouth of the NAO.
	 *
	 * @param session The session running on the NAO.
	 */
	public Speech(Session session) throws Exception
	{
		animatedSpeech = new ALAnimatedSpeech(session);		
		textToSpeech = new ALTextToSpeech(session);
	}

	/**
	 * Sets the language of the speech of the NAO.
	 *
	 * @param language The new language.
	 */
	public void setLanguage(Language language) throws CallError, InterruptedException
	{
		String lang = "";
		switch(language) {
		case ENGLISH: lang = "English"; break;
		case FRENCH: lang = "French"; break;
		case GERMAN: lang = "German"; break;
		}
		textToSpeech.setLanguage(lang);
	}
	
	
	/**
	 * Gets the language of the NAO.
	 *
	 * @return The language
	 */
	public String getLanguage() throws CallError, InterruptedException
	{
		return textToSpeech.getLanguage();
	}
	
	
	/**
	 * NAO says the text with some animation in the current language.
	 *
	 * @param text The text for the NAO.
	 */
	public void animatedSay(String text) throws CallError, InterruptedException
	{
		String te = "^start(animations/Stand/Gestures/Hey_1)"+text+"^wait(animations/Stand/Gestures/Hey_1)";
		animatedSpeech.say(te);		
	}

	
	/**
	 * NAO says the text in the current language.
	 *
	 * @param text The text for the NAO
	 */
	public void say(String text) throws CallError, InterruptedException
	{
		textToSpeech.say(text);
	}
}
