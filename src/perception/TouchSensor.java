package perception;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.*;

public class TouchSensor {

	private EV3TouchSensor ts;
	private SampleProvider source;
	private float [] sample;
	
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
		while(true) {
		
		System.out.println(ts.isPressed());
		if(Button.ESCAPE.isDown()) {
			System.exit(0);
		}
		}
	}

}
