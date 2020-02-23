package dynamics;

import envelopes.LinearAmplitudeEnvelope;

/**
 * @author Alexander Johnston
 * Copyright 2019
 * A class for dynamics
 */
public class Dynamics {

	// The types of dynamics this class can generate
	public enum Dynamic{ 
		fff, ff, f, mf, mp, p, pp, ppp
	}	
	
	public Object[] dynamics = new Object[8];
	
	double amplitude;

	/**       Makes a new Dynamics 
	 * @param amplitude as the max amplitude
	 */
	public Dynamics(double amplitude) {
		this.amplitude = amplitude;
		dynamics[0] = amplitude;
		dynamics[1] = amplitude * 112.0/127.0;
		dynamics[2] = amplitude * 96.0/127.0;
		dynamics[3] = amplitude * 80.0/127.0;
		dynamics[4] = amplitude * 64.0/127.0;
		dynamics[5] = amplitude * 49.0/127.0;
		dynamics[6] = amplitude * 33.0/127.0;
		dynamics[7] = amplitude * 16.0/127.0;
	}
	
	/**        Gets the amplitude of a wave corresponding to the dynamic
	 * @param  amplitude as the original amplitude assumed to be fff
	 * @param  dynamic as the dynamic to set the amplitude to
	 * @return A double representing the amplitude scaled down to the dynamic dyn
	 */
	public double getAmplitude(Dynamic dynamic) {
		double dynamicAmplitude = amplitude;
		switch(dynamic) {
		case fff: 
			break;
		case ff: 
			dynamicAmplitude = (double) dynamics[1];
			break;
		case f: 
			dynamicAmplitude = (double) dynamics[2];
			break;
		case mf: 
			dynamicAmplitude = (double) dynamics[3];
			break;
		case mp: 
			dynamicAmplitude = (double) dynamics[4];
			break;
		case p: 
			dynamicAmplitude = (double) dynamics[5];
			break;
		case pp: 
			dynamicAmplitude = (double) dynamics[6];
			break;
		case ppp: 
			dynamicAmplitude = (double) dynamics[7];
			break;
		}
		return dynamicAmplitude;
	}

	/**        Gets an amplitude envelope that represents a crescendo
	 * @param  attack The time in seconds to get to the first dynamic level d1
	 * @param  decay The time in seconds to get from the first dynamic level d1 to the second dynamic level d2
	 * @param  release The time in seconds to get from the dynamic level d2 to zero
	 * @param  samplesPerSecond The sample rate
	 * @param  firstDynamic The first dynamic level (the low end of the crescendo)
	 * @param  secondDynamic The second dynamic level (the high end of the crescendo)
	 * @return An amplitude envelope that represents a crescendo
	 */
	public LinearAmplitudeEnvelope getDynamicChangeLinearAmplitudeEnvelope(double attack, double decay, double release, float samplesPerSecond, Dynamic firstDynamic, Dynamic secondDynamic) {
		return new LinearAmplitudeEnvelope(getAmplitude(firstDynamic), getAmplitude(secondDynamic), attack, decay, release, samplesPerSecond);
	}
	
	
}