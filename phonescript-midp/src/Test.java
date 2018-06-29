

import java.io.IOException;
import java.net.MalformedURLException;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import org.phonescript.intepreter.File;
import org.phonescript.intepreter.PSInterpreter;


public class Test extends MIDlet  {


    protected void destroyApp(boolean unconditional)
        throws MIDletStateChangeException {

    }

	
	protected void pauseApp() {
    }

    protected void startApp() throws MIDletStateChangeException {
        Display display = Display.getDisplay(this);        
        display.setCurrent(new Form("Hello World!"));
        PSInterpreter interpreter = new PSInterpreter();
		try {
			interpreter.exec(new File("file:///Users/thanos/Documents/workspace/phonescript-midp/rsc/control.ps", "w"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
