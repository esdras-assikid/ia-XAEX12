package perception;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;


public class TouchSensor extends EV3TouchSensor {

	public TouchSensor(Port port)
    {
        super(port);
    }

    public boolean isPressed()
    {
        float[] sample = new float[1];
        fetchSample(sample, 0);

        return sample[0] != 0;
        
    }
    
    public int Count() {
    	
    	float[] sample = new float[1];
    	fetchSample(sample, 0);
        
    	int i = 0;
    	if (sample[0] != 0) {
    		i++;
    		return i;
    	} else {
    		return i;
    	}
    	  	
    	
    }	
	
	public static void main(String[] args) {
		

	}

}
