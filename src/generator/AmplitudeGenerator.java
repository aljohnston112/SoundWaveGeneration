package generator;

import dynamics.Dynamics;
import graph.ChanseyCycle;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for creating random amplitudes from a Dynamics Object
 */
public class AmplitudeGenerator extends ChanseyCycle {

	/**       Creates an AmplitudeGenerator that picks amplitudes from a Dynamics amplitude array with equal probability
	 * @param dynamics as the Dynamics Object that holds the amplitude values
	 */
	public AmplitudeGenerator(Dynamics dynamics) {
		this.choices = dynamics.dynamics.clone();
	}

	/**        Gets a random amplitude from this AmplitudeGenerator
	 * @return an amplitude from this AmplitudeGenerator
	 */
	public Object getNextAmplitude() {
		return fun().get(0);
	}

	/**        Gets a random amplitude from a Dynamics object
	 * @param  dynamics as the dynamics object to get the amplitude from
	 * @return an amplitude that represents a dynamic from the dynamics object
	 */
	public static double getAmplitude(Dynamics dynamics) {
		int choices = dynamics.dynamics.length-1;
		int choice = (int)(Math.round(Math.random()*choices));
		return (double) dynamics.dynamics[choice];
	}

}
