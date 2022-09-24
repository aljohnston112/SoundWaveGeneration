package envelopes;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for envelopes to extend.
*/
public abstract class Envelope {

	// Holds the amplitude of the attack portion of the envelope
	double[] attackAmplitudes;

	// Holds the amplitude of the decay portion of the envelope
	double[] decayAmplitudes;

	// Holds the amplitude of the release portion of the envelope
	double[] releaseAmplitudes;

	// Amount of time it takes to get from 0 to amplitude
	protected double attack;

	// Amount of time it takes to get from amplitude to sustain
	protected double decay;

	// Amount of time it takes to get from sustain to 0
	protected double release;

	// The amount of times the attack will be looped
	protected int attackLoops = 1;

	// The amount of times the decay will be looped
	protected int decayLoops = 1;

	// The amount of times the release will be looped
	protected int releaseLoops = 1;

	// The amount of times the attack and decay portions of the envelope will be looped
	protected int attackDecayLoops = 0;

	// The amount of times the attack and release will be looped, skipping over the decay
	protected int attackReleaseLoops = 0;

	// The amount of times the decay and release will be looped
	protected int decayReleaseLoops = 0;

	// The amount of times the envelope will be looped
	protected int loops = 1;
	
	/**
	 * @return The number of times the attack loops.
	 */
	public int getAttackLoops() {
		return attackLoops;
	}

	/**	      WARNING Settings greater than 1 will result in instantaneous change.
	 * @param attackLoops as the number of times the attack loops.
	 */
	public void setAttackLoops(int attackLoops) {
		this.attackLoops = attackLoops;
	}

	/**
	 * @return The number of times the decay loops.
	 */
	public int getDecayLoops() {
		return decayLoops;
	}

	/**	      WARNING Settings greater than 1 will result in instantaneous change if(amplitude != sustain).
	 * @param decayLoops as the number of times the decay loops.
	 */
	public void setDecayLoops(int decayLoops) {
		this.decayLoops = decayLoops;
	}

	/**
	 * @return The number of times the release loops.
	 */
	public int getReleaseLoops() {
		return releaseLoops;
	}

	/**	      WARNING Settings greater than 1 will result in instantaneous change.
	 * @param releaseLoops as the number of times the release loops.
	 */
	public void setReleaseLoops(int releaseLoops) {
		this.releaseLoops = releaseLoops;
	}

	/**        
	 * @return The number of times the attack and decay loop
	 */
	public int getAttackDecayLoops() {
		return attackDecayLoops;
	}

	/**        WARNING Settings greater than 0 will result in instantaneous change if(sustain != 0).
	 * @param attackDecayLoops as the number of times the attack and decay loop.
	 */
	public void setAttackDecayLoops(int attackDecayLoops) {
		this.attackDecayLoops = attackDecayLoops;
	}

	/**
	 * @return The number of times the attack and release loop.
	 */
	public int getAttackReleaseLoops() {
		return attackReleaseLoops;
	}

	/**	      WARNING Settings greater than 0 will result in instantaneous change if(sustain != amplitude).
	 * @param attackReleaseLoops as the number of times the attack and release loop.
	 */
	public void setAttackReleaseLoops(int attackReleaseLoops) {
		this.attackReleaseLoops = attackReleaseLoops;
	}

	/**	       
	 * @return The number of times the decay and release loop.
	 */
	public int getDecayReleaseLoops() {
		return decayReleaseLoops;
	}

	/**	       WARNING Settings greater than 0 will result in instantaneous change if(sustain != 0).
	 * @param decayReleaseLoops as the number of times the decay and release loop.
	 */
	public void setDecayReleaseLoops(int decayReleaseLoops) {
		this.decayReleaseLoops = decayReleaseLoops;
	}

	/**
	 * @return The number of times the envelope loops.
	 */
	public int getLoops() {
		return loops;
	}

	/**	      WARNING If start and ending amplitudes are not the same, it will result in instantaneous change.
	 * @param loops as the number of times the envelope loops.
	 */
	public void setLoops(int loops) {
		this.loops = loops;
	}
	
	/**
	 * @return An array of amplitude values that represent this envelope.
	 */
	public double[] getEnvelope() {

		int numberOfAttackSamples = (attackAmplitudes.length*attackLoops);
		int numberOfDecaySamples = (decayAmplitudes.length*decayLoops);
		int numberOfReleaseSamples = (releaseAmplitudes.length*releaseLoops);
		int numberOfAttackDecaySamples = (attackAmplitudes.length + decayAmplitudes.length)*attackDecayLoops;
		int numberOfDecayReleaseSamples = (decayAmplitudes.length+ releaseAmplitudes.length)*decayReleaseLoops;
		int numberOfAttackReleaseSamples = (attackAmplitudes.length + releaseAmplitudes.length)*attackReleaseLoops;
		int numberOfSamplesPerLoop = (numberOfAttackSamples + numberOfDecaySamples + numberOfReleaseSamples
				+ numberOfAttackDecaySamples + numberOfDecayReleaseSamples + numberOfAttackReleaseSamples);
		int numberOfSamples = numberOfSamplesPerLoop*loops;

		// Holds the amplitude values of the envelope
		double[] envelope = new double[numberOfSamples];

		// Holds the current location in the envelope 
		int currentLocation = 0;

		// Loops are added to envelope
		for(int i = 0; i < attackLoops; i++){
			for(int j = 0; j < attackAmplitudes.length; j++){
				envelope[i*attackAmplitudes.length+j] = attackAmplitudes[j];
			}
		}
		currentLocation += numberOfAttackSamples;
		for(int i = 0; i < decayLoops; i++){
			for(int j = 0; j < decayAmplitudes.length; j++) {
				envelope[(i*decayAmplitudes.length)+j+currentLocation] = decayAmplitudes[j];
			}
		}
		currentLocation += numberOfDecaySamples;
		for(int i = 0; i < attackDecayLoops; i++){
			for(int j = 0; j < attackAmplitudes.length; j++) {
				envelope[(i*(attackAmplitudes.length+decayAmplitudes.length))+j+currentLocation] = attackAmplitudes[j];
			}
			for(int j = 0; j < decayAmplitudes.length; j++) {
				envelope[(i*(decayAmplitudes.length))+((i+1)*(attackAmplitudes.length))+j+currentLocation] = decayAmplitudes[j];
			}
		}
		currentLocation += numberOfAttackDecaySamples;
		for(int i = 0; i < attackReleaseLoops; i++){
			for(int j = 0; j < attackAmplitudes.length; j++) {
				envelope[(i*(attackAmplitudes.length+releaseAmplitudes.length))+j+currentLocation] = attackAmplitudes[j];
			}
			for(int j = 0; j < releaseAmplitudes.length; j++) {
				envelope[(i*(releaseAmplitudes.length))+((i+1)*(attackAmplitudes.length))+j+currentLocation] = releaseAmplitudes[j];
			}
		}
		currentLocation += numberOfAttackReleaseSamples;
		for(int i = 0; i < releaseLoops; i++){
			for(int j = 0; j < releaseAmplitudes.length; j++) {
				envelope[(i*releaseAmplitudes.length)+j+currentLocation] = releaseAmplitudes[j];
			}
		}
		currentLocation += numberOfReleaseSamples;
		
		for(int i = 0; i < decayReleaseLoops; i++){
			for(int j = 0; j < decayAmplitudes.length; j++) {
				envelope[(i*(decayAmplitudes.length+releaseAmplitudes.length))+j+currentLocation] = decayAmplitudes[j];
			}
			for(int j = 0; j < releaseAmplitudes.length; j++) {
				envelope[(i*releaseAmplitudes.length)+((i+1)*(decayAmplitudes.length))+j+currentLocation] = releaseAmplitudes[j];
			}
		}
		currentLocation += numberOfDecayReleaseSamples;
		for(int i = 1; i < loops; i++){
			for(int j = 0, k = numberOfSamplesPerLoop; j < numberOfSamplesPerLoop; j++){
				envelope[j+((i*k))+k] = envelope[j+((i*k))];
			}
		}
		return envelope;
	}

	/**
	 * @return the amount of time this envelope takes
	 */
	public double getTime() {
		return ((attack*attackLoops)+(decay*decayLoops)+(release*releaseLoops)+
				((attack+decay)*attackDecayLoops)+((attack+release)*attackReleaseLoops)+
				((release+decay)*decayReleaseLoops)+((attack+decay+release)*(loops-1)));
	}

}