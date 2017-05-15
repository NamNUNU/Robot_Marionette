/******************************************************************************\
* Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

class SampleListener extends Listener {
	Jssc1 jssc1 = new Jssc1();
	Jssc2 jssc2 = new Jssc2();
	int val=0;
	int T_val=0;
	int I_val=0;
	int M_val=0;
	int R_val=0;
	int P_val=0;
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected"); 
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        

        
        for(Hand hand : frame.hands()) {
            String handType = hand.isLeft() ? "Left hand" : "Right hand";
            //System.out.println("  " + handType);
            if(handType=="Right hand"){
            
            // Get the hand's normal vector and direction
            Vector normal = hand.palmNormal();
            Vector direction = hand.direction();
            double roll = Math.toDegrees(normal.roll());
            roll = Math.round(roll);
            roll = (int)roll + 180 ;
          
            //Calculate the hand's pitch, roll, and yaw angles
          	jssc1.write("W" + roll + ">");
          	//System.out.println("<W" + roll + ">");

               for (Finger finger : hand.fingers()) {
                    // TODO: does this return the correct finger?
                 Vector palmNormal = hand.palmNormal();
                 Vector fDir = finger.direction();
                 // TODO: validate that this is what we actually want.
                 // otherwise we can directly compute the angleTo in java.
                 float angleInRadians = palmNormal.angleTo(fDir);
                 // convert to degrees so it's easy to pass to servos
                 double angle = Math.toDegrees(angleInRadians);
                 angle = Math.round(angle);
                 val = (int)angle;
	        		
	        		if(finger.type()==finger.type().TYPE_THUMB){
	        			T_val = (int)angle;
	        			jssc1.write("T"+String.valueOf(val)+">");
	            	}
	        		else if(finger.type()==finger.type().TYPE_INDEX){
	        			I_val = (int)angle;
	        			jssc1.write("I"+String.valueOf(val)+">");
	            	}
	        		else if(finger.type()==finger.type().TYPE_MIDDLE){
	        			M_val = (int)angle;
	        			jssc1.write("M"+String.valueOf(val)+">");
	                }
	        		else if(finger.type()==finger.type().TYPE_RING){
	        			R_val = (int)angle;
	        			jssc1.write("R"+String.valueOf(val)+">");
	                }
	        		else if(finger.type()==finger.type().TYPE_PINKY){
	        			P_val = (int)angle;
	        			jssc1.write("P"+String.valueOf(val)+">");
	                }
            	}
            }
        }

        //Gesture
        //검지
        if((T_val<=75)&&(I_val>=80)&&(M_val<=50)&&(R_val<=50)&&(P_val<=50)){
        	//System.out.println("gesture 1");
        	jssc2.write("1");
        }
        //검지, 중지
        else if((T_val<=75)&&(I_val>=80)&&(M_val>=80)&&(R_val<=50)&&(P_val<=50)){
        	//System.out.println("gesture 2");
        	jssc2.write("2");
        }
        //엄지, 검지, 소지
        else if((T_val>=100)){
        	//System.out.println("gesture 3");
        	jssc2.write("3");
        }
    }
}
