
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class Jssc2 {
	static SerialPort serialPort = new SerialPort("COM18");
	
    public void connect(){
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_9600, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
            serialPort.addEventListener(new SerialPortReader());
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
    
    public void write(String value){
    	try{
    	serialPort.writeBytes(value.getBytes());//Write data to port\
    	}
    	catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
    
    class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {//If data is available
                int bytesCount = event.getEventValue();
                try {
					System.out.print(serialPort.readString(bytesCount));
				} catch (SerialPortException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }
}