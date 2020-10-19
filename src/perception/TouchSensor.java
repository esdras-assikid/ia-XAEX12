package perception;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.*;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class TouchSensor {

	private EV3TouchSensor ts;
	private SampleProvider source;
	private float [] sample;
	
	private boolean etat = false;
	
	public TouchSensor()
    {
		Brick b = BrickFinder.getDefault();
		Port s3 = b.getPort("S3");
		ts = new EV3TouchSensor(s3);
		source = ts.getTouchMode();
		sample = new float[source.sampleSize()];
    }

    public boolean isPressed()
    {
       
        source.fetchSample(sample, 0);
        
        return sample[0] != 0;
        
    }
    
    public boolean aEteTouche() {
    	while(etat == false) {
    		etat = isPressed();
    	}
    	return etat;
    }
    
    
    public int Count() {
    	
    	float[] sample = new float[1];
    	source.fetchSample(sample, 0);
        
    	int i = 0;
    	if (sample[0] != 0) {
    		i++;
    		return i;
    	} else {
    		return i;
    	}
    	  	
    	
    }	
	
	public static void main(String[] args) {
		TouchSensor ts = new TouchSensor();
		MovePilot pilot;
		Wheel leftWheel = WheeledChassis.modelWheel(Motor.B,0.056).offset(-0.06075); // 0.056 = diamètre des roues, offset = décalage des roues ??
		Wheel rightWheel = WheeledChassis.modelWheel(Motor.C, 0.056).offset(0.06075);
		Chassis chassis = new WheeledChassis(new Wheel[] {leftWheel, rightWheel}, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		pilot.forward();
		while(true) {
		
			if(ts.aEteTouche())
				pilot.stop();
			System.out.println(ts.aEteTouche());
		if(Button.ESCAPE.isDown()) {
			System.exit(0);
		}
		}
	}

}
