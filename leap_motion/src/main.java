import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.leapmotion.leap.Controller;

public class main {

	public static void main(String[] args) {
		Jssc1 jssc1 = new Jssc1();
		jssc1.connect();
		Jssc2 jssc2 = new Jssc2();
		jssc2.connect();
		
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();

        
        controller.addListener(listener);
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        controller.removeListener(listener);
	}
}
