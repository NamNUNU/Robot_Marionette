#include<Servo.h>

Servo myservo1;
Servo myservo2;
Servo myservo3;
Servo myservo4;
Servo myservo5;
Servo myservo6;

int servoPin1 = 2;
int servoPin2 = 3;
int servoPin3 = 4;
int servoPin4 = 5;
int servoPin5 = 6;
int servoPin6 = 7;

// 시리얼 통신 저장
char incomingByte = '0';
char val[6]={0,};
int index=0;

// 손가락 위치값
String value="";
int T_value=0;
int I_value=0;
int M_value=0;
int R_value=0;
int P_value=0;
int W_value=0;


void setup() {
    Serial.begin(9600);    // opens serial port, sets data rate to 9600 bps

    //서보
    myservo1.attach(servoPin1);
    myservo2.attach(servoPin2);
    myservo3.attach(servoPin3);
    myservo4.attach(servoPin4);
    myservo5.attach(servoPin5);
    myservo6.attach(servoPin6);
}

void loop() { //핀 각도가 작아

  // 구동부
  if (Serial.available() > 0) {
    incomingByte = Serial.read();
    
    if(incomingByte=='>'){
      if(val[0]=='T'){
        T_value=value.toInt();
        T_value = map(T_value, 70, 80, 150, 10);
        myservo1.write(T_value);
        //delay(5);
      }
      else if(val[0]=='I'){
        I_value=value.toInt();
        I_value = map(I_value, 27, 80, 160, 0);
        myservo2.write(I_value);
        //delay(5);
      }
      else if(val[0]=='M'){
        M_value=value.toInt();
        M_value = map(M_value, 17, 90, 150, 35);
        myservo3.write(M_value);
        //delay(5);
      }
      else if(val[0]=='R'){
        R_value=value.toInt();
        R_value = map(R_value, 40, 90, 40, 100);
        myservo4.write(R_value);
        //delay(5);
      }
      else if(val[0]=='P'){
        P_value=value.toInt();
        P_value = map(P_value, 22, 100, 180, 45);
        myservo5.write(P_value);
        //delay(5);
      }
      else if(val[0]=='W'){
        W_value=value.toInt();
        if(W_value < 90 ) {  W_value = 90;  }
        else if(W_value > 270 ) {  W_value = 270;  }
        W_value = map(W_value, 90, 270, 180, 0);
        myservo6.write(W_value);
        //delay(5);
      }
      
      for(int i=0; i<6; i++){
        val[i]=0;  
      }
      index=0;
      value="";
    }
    
    // 데이터 저장부
    else{
        val[index]=incomingByte;
        if((index==0))
        {}
        else{
        value+=val[index];
        }
        index++;
    }
  }
}


