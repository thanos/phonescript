

import java.io.IOException;
import java.net.MalformedURLException;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.Ticker;
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
        Form form = new Form("Ho");
//        TextField statement = new TextField("","",1024, TextField.ANY);
//        form.append(statement);
//        TextField result = new TextField("stack","text",1024, TextField.ANY);
//        form.append(result);
//        form.setTicker(new Ticker("MIDlet postscipt intepreter"));
        display.setCurrent(form);
        PSInterpreter interpreter = new PSInterpreter();
		try {
			interpreter.exec(new File("file:///home/thanos/workspace/phonescript/ps-src/control.ps", "w"));
			//interpreter.exec("0 1 1 4 { add } for");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}
