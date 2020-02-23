package envelopes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import arrays.Array;
import waves.Wave;
import waves.Wave.WaveType;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making amplitude envelopes
 */
public class LinearAmplitudeEnvelope extends Envelope {

	// The amplitude at the end of the attack and the beginning of the decay
	double amplitude;

	// The amplitude at the end of the decay and the beginning of the release
	double sustain;

	protected float samplesPerSecond;
	
	/**       Constructs a linear amplitude envelope with 
	 * @param amplitude as the amplitude at the end of the attack and the beginning of the decay 
	 * @param sustain as the amplitude at the end of the decay and the beginning of the release
	 * @param attack in seconds, as the amount of time it takes to get from 0 to amplitude 
	 * @param decay in seconds, as the amount of time it takes to get from amplitude to sustain
	 * @param release in seconds, as the amount of time it takes to get from sustain to 0
	 */
	public LinearAmplitudeEnvelope(double amplitude, double sustain, double attack, double decay, double release, float samplesPerSecond) {

		this.amplitude = amplitude;	
		this.sustain = sustain;
		this.attack = attack;
		this.release = release;
		this.decay = decay;
		this.samplesPerSecond = samplesPerSecond;

		// Fills the attack ArrayList
		int numberOfSamples = (int)Math.round((samplesPerSecond*attack));
		attackAmplitudes = new double[numberOfSamples];
		double slope = this.amplitude/numberOfSamples;
		for(int i = 0; i < numberOfSamples; i++) {
			this.attackAmplitudes[i] = (i*slope);
		}

		// Fills the decay ArrayList
		numberOfSamples = (int)Math.round((samplesPerSecond*decay));
		decayAmplitudes = new double[numberOfSamples];
		slope = (this.amplitude - this.sustain)/numberOfSamples;
		for(int i = 0; i < numberOfSamples; i++) {
			this.decayAmplitudes[i] = ((this.amplitude)-(i*slope));
		}

		// Fills the release ArrayList
		numberOfSamples = (int)Math.round((attack+decay+release)*samplesPerSecond)-attackAmplitudes.length-decayAmplitudes.length;
		releaseAmplitudes = new double[numberOfSamples];
		slope = (this.sustain/numberOfSamples);
		for(int i = 0; i < numberOfSamples; i++) {
			this.releaseAmplitudes[i] = ((this.sustain-((i)*slope)));
		}
	}
	
	/**
	 * @return the maximum amplitude of this linear amplitude envelope
	 */
	public double getAmplitude() {
		return (amplitude > sustain) ? amplitude : sustain;
	}

	/**       Adds a wave to this amplitude envelope
	 * @param wave as the wave added to this envelope 
	 *        Make note that the wave amplitude will be scaled down to the max envelope amplitude
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremolo(Wave wave, float samplesPerSecond, ExecutorService threadRunner){

		// The wave to add to the envelope
		double[] tremolo = wave.getWave(getTime(), samplesPerSecond);

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveScale;	

		// Add the wave to the envelope
		for(int i = 0; i < attackAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>attackAmplitudes[i]) {
				waveScale = attackAmplitudes[i]/waveAmplitude;
			} else {
				waveScale = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(tremolo[i]*waveScale));
		}
		for(int i = 0; i < decayAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>decayAmplitudes[i]) {
				waveScale = decayAmplitudes[i]/waveAmplitude;
			} else {
				waveScale = 1.0;
			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(tremolo[i+attackAmplitudes.length]*waveScale));
		}
		for(int i = 0; i < releaseAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>releaseAmplitudes[i]) {
				waveScale = releaseAmplitudes[i]/waveAmplitude;
			} else {
				waveScale = 1.0;
			}
			releaseAmplitudes[i] = (releaseAmplitudes[i]+(tremolo[i+attackAmplitudes.length+decayAmplitudes.length]*waveScale));
		}

		scaleEnvelope(threadRunner);
	}

	/**       Adds a wave to this amplitude envelope
	 * @param wave as the wave added to this envelope 
	 *        Make note that the wave amplitude will be scaled down to the max envelope amplitude
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloSwell(Wave wave, float samplesPerSecond, ExecutorService threadRunner){

		// The wave to add to the envelope
		double[] tremolo = wave.getWave(getTime(), samplesPerSecond);

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveScale;	

		// The swell multiplier
		double[] swellWave = wave.getWave(tremolo.length/samplesPerSecond, samplesPerSecond);
		double[] swellMultiplierWave = new double[swellWave.length];
		double slope = 1.0/(swellWave.length/2.0);
		for(int i = 0, j = swellWave.length; i < swellWave.length; i++, j--) {
			if(i < swellWave.length/2.0) {
				swellMultiplierWave[i] = slope*i;
			} else {
				swellMultiplierWave[i] = slope*(j-1);
			}
		}

		// Add the wave to the envelope
		for(int i = 0; i < attackAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>attackAmplitudes[i]) {
				waveScale = attackAmplitudes[i]/waveAmplitude;
			} else {
				waveScale = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(tremolo[i]*waveScale*swellMultiplierWave[i]));
		}
		for(int i = 0; i < decayAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>decayAmplitudes[i]) {
				waveScale = decayAmplitudes[i]/waveAmplitude;
			} else {
				waveScale = 1.0;
			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(tremolo[i+attackAmplitudes.length]*waveScale*swellMultiplierWave[i+attackAmplitudes.length]));
		}
		for(int i = 0; i < releaseAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>releaseAmplitudes[i]) {
				waveScale = releaseAmplitudes[i]/waveAmplitude;
			} else {
				waveScale = 1.0;
			}
			releaseAmplitudes[i] = (releaseAmplitudes[i]+(tremolo[i+attackAmplitudes.length+decayAmplitudes.length]*waveScale*swellMultiplierWave[i+attackAmplitudes.length+decayAmplitudes.length]));
		}

		scaleEnvelope(threadRunner);
	}


	/**Finds the max amplitude and scales it to the amplitude or the sustain, whichever is greater
	 * 
	 */
	private void scaleEnvelope(ExecutorService threadRunner) {

		// The max amplitude of the envelope
		double maxEnvelopeAmplitude = Math.max(this.amplitude, this.sustain);

		// Find the max and scale the envelope
		double max = Math.max(Math.max(Array.getMaxMag(attackAmplitudes, threadRunner), Array.getMaxMag(decayAmplitudes, threadRunner)), Array.getMaxMag(releaseAmplitudes, threadRunner));
		double amplitudeScale = maxEnvelopeAmplitude/max;
		for(int i = 0; i < attackAmplitudes.length; i++) {
			attackAmplitudes[i] = attackAmplitudes[i]*amplitudeScale;
		}
		for(int i = 0; i < decayAmplitudes.length; i++) {
			decayAmplitudes[i] = decayAmplitudes[i]*amplitudeScale;
		}
		for(int i = 0; i < releaseAmplitudes.length; i++) {
			releaseAmplitudes[i] = releaseAmplitudes[i]*amplitudeScale;
		}		
	}

	/**       Adds tremolo swell to the attack  of this envelope. 
	 * @param wave as the wave added to the attack  of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloSwellToAttack(Wave wave, float samplesPerSecond){

		// The wave to add to the envelope
		double[] swellWave = wave.getWave(attackAmplitudes.length/samplesPerSecond, samplesPerSecond);

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double amplitudeScale;	

		double[] swellMultiplierWave = new double[swellWave.length];
		double slope = 1.0/(swellWave.length/2.0);

		for(int i = 0, j = swellWave.length; i < swellWave.length; i++, j--) {
			if(i < swellWave.length/2.0) {
				swellMultiplierWave[i] = slope*i;
			} else {
				swellMultiplierWave[i] = slope*(j-1);
			}
		}

		// Add the wave to the envelope
		for(int i = 0; i < attackAmplitudes.length/2.0; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>attackAmplitudes[i]) {
				amplitudeScale = (attackAmplitudes[i])/waveAmplitude;
			} else {
				amplitudeScale = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(swellWave[i]*amplitudeScale*swellMultiplierWave[i]));
		}

		for(int i = (int)Math.round(attackAmplitudes.length/2.0); i < attackAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>(this.amplitude-attackAmplitudes[i])){
				amplitudeScale = (this.amplitude-attackAmplitudes[i])/waveAmplitude;
			} else {
				amplitudeScale = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(swellWave[i]*amplitudeScale*swellMultiplierWave[i]));
		}
	}

	/**       Adds tremolo to the attack of this envelope. 
	 * @param wave as the wave added to the attack  of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloToAttack(Wave wave, float samplesPerSecond){

		// The wave to add to the envelope
		double[] tremoloWave = wave.getWave(attackAmplitudes.length/samplesPerSecond, samplesPerSecond);

		//The max amplitude of the envelope
		double envelopeAttackAmplitude = this.amplitude;

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		// Add the wave to the envelope
		for(int i = 0; i < attackAmplitudes.length/2.0; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>attackAmplitudes[i]) {
				waveMultiplier = (attackAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(tremoloWave[i]*waveMultiplier));
		}
		for(int i = (int)Math.round(attackAmplitudes.length/2.0); i < attackAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>(envelopeAttackAmplitude-attackAmplitudes[i])){
				waveMultiplier = (envelopeAttackAmplitude-attackAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(tremoloWave[i]*waveMultiplier));
		}
	}

	/**       Adds tremolo swell to the decay of this envelope. 
	 * @param wave as the wave added to the decay  of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloSwellToDecay(Wave wave, float samplesPerSecond){

		// The wave to add to the envelope
		double[] swellWave = wave.getWave(decayAmplitudes.length/samplesPerSecond, samplesPerSecond);	

		if(this.amplitude == this.sustain) {
			swellWave = Wave.addToWave(swellWave, -wave.getAmplitude());
		}

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		double[] swellMultiplierWave = new double[swellWave.length];
		double slope = 1.0/(swellWave.length/2.0);

		for(int i = 0, j = swellWave.length; i < swellWave.length; i++, j--) {
			if(i < swellWave.length/2.0) {
				swellMultiplierWave[i] = slope*i;
			} else {
				swellMultiplierWave[i] = slope*(j-1);
			}
		}

		// Add the wave to the envelope
		for(int i = 0; i < decayAmplitudes.length/2.0; i++) {
			double waveAmplitude = wave.getAmplitude();
			waveMultiplier = 1.0;
			if(this.amplitude > this.sustain) {
				if(waveAmplitude>(this.amplitude-decayAmplitudes[i])) {
					waveMultiplier = (this.amplitude-decayAmplitudes[i])/waveAmplitude;
				}
			} else if(this.amplitude < this.sustain) {
				if(waveAmplitude>(decayAmplitudes[i]-this.amplitude)) {
					waveMultiplier = (decayAmplitudes[i]-this.amplitude)/waveAmplitude;
				}
			} else {
				if(waveAmplitude>(this.amplitude)) {
					waveMultiplier = ((this.amplitude/2.0))/waveAmplitude;
				}
			}			
			decayAmplitudes[i] = (decayAmplitudes[i]+(swellWave[i]*waveMultiplier*swellMultiplierWave[i]));
		}

		for(int i = (int)Math.round(decayAmplitudes.length/2.0); i < decayAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			waveMultiplier = 1.0;
			if(this.amplitude > this.sustain) {
				if(waveAmplitude>(decayAmplitudes[i]-this.sustain)){
					waveMultiplier = (decayAmplitudes[i]-this.sustain)/waveAmplitude;
				}
			} else if(this.amplitude < this.sustain) {
				if(waveAmplitude>(this.sustain-decayAmplitudes[i])){
					waveMultiplier = (this.sustain-decayAmplitudes[i])/waveAmplitude;
				}
			} else {
				if(waveAmplitude>(this.amplitude)) {
					waveMultiplier = ((this.amplitude/2.0))/waveAmplitude;
				}			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(swellWave[i]*waveMultiplier*swellMultiplierWave[i]));
		}
	}

	/**       Adds tremolo to the decay of this envelope. 
	 * @param wave as the wave added to the decay  of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloToDecay(Wave wave, float samplesPerSecond){

		// The wave to add to the envelope
		double[] tremoloWave = wave.getWave(decayAmplitudes.length/samplesPerSecond, samplesPerSecond);	

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		// Add the wave to the envelope
		for(int i = 0; i < decayAmplitudes.length/2.0; i++) {
			double waveAmplitude = wave.getAmplitude();
			waveMultiplier = 1.0;
			if(this.amplitude > this.sustain) {
				if(waveAmplitude>(this.amplitude-decayAmplitudes[i])) {
					waveMultiplier = (this.amplitude-decayAmplitudes[i])/waveAmplitude;
				}
			} else if(this.amplitude < this.sustain) {
				if(waveAmplitude>(decayAmplitudes[i]-this.amplitude)) {
					waveMultiplier = (decayAmplitudes[i]-this.amplitude)/waveAmplitude;
				}
			} else {
				if(waveAmplitude>(this.amplitude)) {
					waveMultiplier = (this.amplitude/2.0)/waveAmplitude;
				}
			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(tremoloWave[i]*waveMultiplier));
		}

		for(int i = (int)Math.round(decayAmplitudes.length/2.0); i < decayAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			waveMultiplier = 1.0;
			if(this.amplitude > this.sustain) {
				if(waveAmplitude>(decayAmplitudes[i]-this.sustain)){
					waveMultiplier = (decayAmplitudes[i]-this.sustain)/waveAmplitude;
				}
			} else if(this.amplitude < this.sustain) {
				if(waveAmplitude>(this.sustain-decayAmplitudes[i])){
					waveMultiplier = (this.sustain-decayAmplitudes[i])/waveAmplitude;
				}
			} else {
				if(waveAmplitude>(this.amplitude)) {
					waveMultiplier = (this.amplitude/2.0)/waveAmplitude;
				}
			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(tremoloWave[i]*waveMultiplier));
		}
	}

	/**       Adds tremolo swell to the release of this envelope.  
	 * @param wave as the wave added to the release of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloSwellToRelease(Wave wave, float samplesPerSecond){
		// The wave to add to the envelope
		double[] swellWave = wave.getWave(releaseAmplitudes.length/samplesPerSecond, samplesPerSecond);	

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		double[] swellMultiplierWave = new double[swellWave.length];
		double slope = 1.0/(swellWave.length/2.0);

		for(int i = 0, j = swellWave.length; i < swellWave.length; i++, j--) {
			if(i < swellWave.length/2.0) {
				swellMultiplierWave[i] = slope*i;
			} else {
				swellMultiplierWave[i] = slope*(j-1);
			}
		}

		// Add the wave to the envelope
		for(int i = 0; i < releaseAmplitudes.length/2.0; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>this.sustain-releaseAmplitudes[i]) {
				waveMultiplier = (this.sustain-releaseAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			releaseAmplitudes[i] = (releaseAmplitudes[i]+(swellWave[i]*waveMultiplier*swellMultiplierWave[i]));
		}

		for(int i = (int)Math.round(releaseAmplitudes.length/2.0); i < releaseAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>(releaseAmplitudes[i])){
				waveMultiplier = (releaseAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			releaseAmplitudes[i] = (releaseAmplitudes[i]+(swellWave[i]*waveMultiplier*swellMultiplierWave[i]));
		}
	}

	/**       Adds tremolo to the release of this envelope.  
	 * @param wave as the wave added to the release of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloToRelease(Wave wave, float samplesPerSecond){
		// The wave to add to the envelope
		double[] tremoloWave = wave.getWave(releaseAmplitudes.length/samplesPerSecond, samplesPerSecond);	

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		// Add the wave to the envelope
		for(int i = 0; i < releaseAmplitudes.length/2.0; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>this.sustain-releaseAmplitudes[i]) {
				waveMultiplier = (this.sustain-releaseAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			releaseAmplitudes[i] = (releaseAmplitudes[i]+(tremoloWave[i]*waveMultiplier));
		}

		for(int i = (int)Math.round(releaseAmplitudes.length/2.0); i < releaseAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>(releaseAmplitudes[i])){
				waveMultiplier = (releaseAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			releaseAmplitudes[i] = (releaseAmplitudes[i]+(tremoloWave[i]*waveMultiplier));
		}
	}

	/**       Adds tremolo swell to the attack and decay of this envelope. 
	 * @param wave as the wave added to the attack and decay of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloSwellToAttackAndDecay(Wave wave, float samplesPerSecond){

		if(this.amplitude > this.sustain) {
			addTremoloSwellToAttack(wave, samplesPerSecond);
			addTremoloSwellToDecay(wave, samplesPerSecond);
			return;
		}

		// The wave to add to the envelope
		double[] swellWave = wave.getWave((attackAmplitudes.length+decayAmplitudes.length)/samplesPerSecond, samplesPerSecond);	

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		double[] swellMultiplierWave = new double[swellWave.length];
		double slope = 1.0/(swellWave.length);

		for(int i = 0, j = swellWave.length; i < swellWave.length; i++, j--) {
			if(i < swellWave.length/2.0) {
				swellMultiplierWave[i] = slope*i;
			} else {
				swellMultiplierWave[i] = slope*(j-1);
			}
		}

		// Add the wave to the envelope
		for(int i = 0; i < attackAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>attackAmplitudes[i]) {
				waveMultiplier = (attackAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(swellWave[i]*waveMultiplier*swellMultiplierWave[i]));
		}

		// Add the wave to the envelope
		for(int i = 0; i < decayAmplitudes.length; i++) {
			waveMultiplier = 1.0;
			if((this.sustain-decayAmplitudes[i]) > decayAmplitudes[i]) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude>decayAmplitudes[i]) {
					waveMultiplier = (decayAmplitudes[i])/waveAmplitude;
				}
			} else if((this.sustain-decayAmplitudes[i]) < decayAmplitudes[i]) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude>this.sustain-decayAmplitudes[i]) {
					waveMultiplier = (this.sustain-decayAmplitudes[i])/waveAmplitude;
				}
			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(swellWave[i+attackAmplitudes.length]*waveMultiplier*swellMultiplierWave[i+attackAmplitudes.length]));
		}

	}

	/**       Adds tremolo to the attack and decay of this envelope. 
	 * @param wave as the wave added to the attack and decay of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloToAttackAndDecay(Wave wave, float samplesPerSecond){

		if(this.amplitude > this.sustain) {
			addTremoloToAttack(wave, samplesPerSecond);
			addTremoloToDecay(wave, samplesPerSecond);
			return;
		}

		// The wave to add to the envelope
		double[] tremoloWave = wave.getWave((attackAmplitudes.length+decayAmplitudes.length)/samplesPerSecond, samplesPerSecond);	

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		// Add the wave to the envelope
		for(int i = 0; i < attackAmplitudes.length; i++) {
			double waveAmplitude = wave.getAmplitude();
			if(waveAmplitude>attackAmplitudes[i]) {
				waveMultiplier = (attackAmplitudes[i])/waveAmplitude;
			} else {
				waveMultiplier = 1.0;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(tremoloWave[i]*waveMultiplier));
		}

		// Add the wave to the envelope
		for(int i = 0; i < decayAmplitudes.length; i++) {
			waveMultiplier = 1.0;
			if((this.sustain-decayAmplitudes[i]) > decayAmplitudes[i]) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude>decayAmplitudes[i]) {
					waveMultiplier = (decayAmplitudes[i])/waveAmplitude;
				}
			} else if((this.sustain-decayAmplitudes[i]) < decayAmplitudes[i]) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude>this.sustain-decayAmplitudes[i]) {
					waveMultiplier = (this.sustain-decayAmplitudes[i])/waveAmplitude;
				}
			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(tremoloWave[i+attackAmplitudes.length]*waveMultiplier));
		}
	}

	/**       Adds a tremolo swell to the attack and release of this envelope. 
	 * @param wave as the wave added to the attack and release of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloSwellToAttackAndRelease(Wave wave, float samplesPerSecond){
		addTremoloSwellToAttack(wave,samplesPerSecond);
		addTremoloSwellToRelease(wave,samplesPerSecond);
	}

	/**       Adds tremolo to the attack and release of this envelope. 
	 * @param wave as the wave added to the attack and release of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloToAttackAndRelease(Wave wave, float samplesPerSecond){
		addTremoloToAttack(wave,samplesPerSecond);
		addTremoloToRelease(wave,samplesPerSecond);
	}

	/**       Adds a tremolo swell to the decay and release of this envelope. 
	 * @param wave as the wave added to the decay and release of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloSwellToDecayAndRelease(Wave wave, float samplesPerSecond){

		// The wave to add to the envelope
		double[] swellWave = wave.getWave((releaseAmplitudes.length+decayAmplitudes.length)/samplesPerSecond, samplesPerSecond);	

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;	

		double[] swellMultiplierWave = new double[swellWave.length];
		double slope = 1.0/(swellWave.length);

		for(int i = 0, j = swellWave.length; i < swellWave.length; i++, j--) {
			if(i < swellWave.length/2.0) {
				swellMultiplierWave[i] = slope*i;
			} else {
				swellMultiplierWave[i] = slope*(j-1);
			}
		}

		if((this.amplitude - this.sustain) < this.sustain) {

			// Add the wave to the envelope
			for(int i = 0; i < decayAmplitudes.length; i++) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude>this.sustain-decayAmplitudes[i]) {
					waveMultiplier = (this.sustain-decayAmplitudes[i])/waveAmplitude;
				} else {
					waveMultiplier = 1.0;
				}
				decayAmplitudes[i] = (decayAmplitudes[i]+(swellWave[i+attackAmplitudes.length]*waveMultiplier*swellMultiplierWave[i]));
			}

			for(int i = 0; i < releaseAmplitudes.length; i++) {
				waveMultiplier = 1.0;
				if(this.sustain-releaseAmplitudes[i]<releaseAmplitudes[i]) {
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(this.sustain-releaseAmplitudes[i])){
						waveMultiplier = (this.sustain-releaseAmplitudes[i])/waveAmplitude;
					}
				}
				else if(this.sustain-releaseAmplitudes[i]>=releaseAmplitudes[i]) {
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(releaseAmplitudes[i])){
						waveMultiplier = (releaseAmplitudes[i])/waveAmplitude;
					}
				}
				releaseAmplitudes[i] = (releaseAmplitudes[i]+(swellWave[i+decayAmplitudes.length]*waveMultiplier*swellMultiplierWave[i+decayAmplitudes.length]));
			}
		} else if(this.amplitude - this.sustain >= this.sustain) {
			for(int i = 0; i < decayAmplitudes.length; i++) {
				waveMultiplier = 1.0;
				if(this.amplitude-decayAmplitudes[i]<decayAmplitudes[i]) {
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(this.amplitude-decayAmplitudes[i])){
						waveMultiplier = (this.amplitude-decayAmplitudes[i])/waveAmplitude;
					}
				} else if(this.amplitude-decayAmplitudes[i]>=decayAmplitudes[i]){
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(decayAmplitudes[i])){
						waveMultiplier = (decayAmplitudes[i])/waveAmplitude;
					}
				}
				decayAmplitudes[i] = (decayAmplitudes[i]+(swellWave[i+attackAmplitudes.length]*waveMultiplier*swellMultiplierWave[i]));
			}
			for(int i = 0; i < releaseAmplitudes.length; i++) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude>(releaseAmplitudes[i])){
					waveMultiplier = (releaseAmplitudes[i])/waveAmplitude;
				} else {
					waveMultiplier = 1.0;
				}
				releaseAmplitudes[i] = (releaseAmplitudes[i]+(swellWave[i+decayAmplitudes.length]*waveMultiplier*swellMultiplierWave[i+decayAmplitudes.length]));
			}
		}
	}

	/**       Adds a tremolo to the decay and release of this envelope. 
	 * @param wave as the wave added to the decay and release of the envelope
	 * @param samplesPerSecond as the sample rate
	 */
	public void addTremoloToDecayAndRelease(Wave wave, float samplesPerSecond){
		// The wave to add to the envelope
		double[] tremoloWave = wave.getWave((releaseAmplitudes.length+decayAmplitudes.length)/samplesPerSecond, samplesPerSecond);	

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double waveMultiplier;

		if(this.amplitude - this.sustain < this.sustain) {

			// Add the wave to the envelope
			for(int i = 0; i < decayAmplitudes.length; i++) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude > (decayAmplitudes[i]-this.amplitude) && (decayAmplitudes[i]-this.amplitude) < this.sustain-decayAmplitudes[i]) {
					waveMultiplier = (decayAmplitudes[i]-this.amplitude)/waveAmplitude;
				} else if(waveAmplitude>this.sustain-decayAmplitudes[i]) {
					waveMultiplier = (this.sustain-decayAmplitudes[i])/waveAmplitude;
				} else {
					waveMultiplier = 1.0;
				}
				decayAmplitudes[i] = (decayAmplitudes[i]+(tremoloWave[i+attackAmplitudes.length]*waveMultiplier));
			}
			for(int i = 0; i < releaseAmplitudes.length; i++) {
				waveMultiplier = 1.0;
				if(this.sustain-releaseAmplitudes[i]<releaseAmplitudes[i]) {
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(this.sustain-releaseAmplitudes[i])){
						waveMultiplier = (this.sustain-releaseAmplitudes[i])/waveAmplitude;
					}
				}
				else if(this.sustain-releaseAmplitudes[i]>=releaseAmplitudes[i]) {
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(releaseAmplitudes[i])){
						waveMultiplier = (releaseAmplitudes[i])/waveAmplitude;
					}
				}
				releaseAmplitudes[i] = (releaseAmplitudes[i]+(tremoloWave[i+decayAmplitudes.length]*waveMultiplier));
			}
		} else if(this.amplitude - this.sustain >= this.sustain) {

			for(int i = 0; i < decayAmplitudes.length; i++) {
				waveMultiplier = 1.0;
				if(this.amplitude-decayAmplitudes[i]<decayAmplitudes[i]) {
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(this.amplitude-decayAmplitudes[i])){
						waveMultiplier = (this.amplitude-decayAmplitudes[i])/waveAmplitude;
					}
				} else if(this.amplitude-decayAmplitudes[i]>=decayAmplitudes[i]){
					double waveAmplitude = wave.getAmplitude();
					if(waveAmplitude>(decayAmplitudes[i])){
						waveMultiplier = (decayAmplitudes[i])/waveAmplitude;
					}
				}
				decayAmplitudes[i] = (decayAmplitudes[i]+(tremoloWave[i+attackAmplitudes.length]*waveMultiplier));
			}

			for(int i = 0; i < releaseAmplitudes.length; i++) {
				double waveAmplitude = wave.getAmplitude();
				if(waveAmplitude>(releaseAmplitudes[i])){
					waveMultiplier = (releaseAmplitudes[i])/waveAmplitude;
				} else {
					waveMultiplier = 1.0;
				}
				releaseAmplitudes[i] = (releaseAmplitudes[i]+(tremoloWave[i+decayAmplitudes.length]*waveMultiplier));
			}
		}
	}

	/* (non-Javadoc)
	 * @see envelopes.Envelope#getTime()
	 */
	@Override
	public double getTime() {
		return ((attack*attackLoops)+(decay*decayLoops)+(release*releaseLoops)+
				((attack+decay)*attackDecayLoops)+((attack+release)*attackReleaseLoops)+
				((release+decay)*decayReleaseLoops)+((attack+decay+release)*(loops-1)));
	}

	/**       Adds an amplitude oscillator to this envelope
	 * @param amplitudeOscillator The amplitude oscillator
	 * @param waveType The wave type of the amplitude oscillator 
	 * @param samplesPerSecond The sample rate
	 */
	public void addAmplitudeOscillator(AmplitudeOscillator amplitudeOscillator, WaveType waveType, float samplesPerSecond) {

		double seconds = getTime();
		double[] amplitudeFrequencyArray = amplitudeOscillator.getOscillator(seconds, waveType, samplesPerSecond);
		double[] amplitudeOscillationArray = amplitudeOscillator.getAmplitudeArray(seconds, samplesPerSecond);

		// For scaling the wave amplitude down to the envelope amplitude to prevent under and over flow
		double amplitudeScale;	

		// Add the wave to the envelope
		for(int i = 0; i < attackAmplitudes.length; i++) {
			if(amplitudeOscillationArray[i]>attackAmplitudes[i]) {
				amplitudeScale = attackAmplitudes[i]/amplitudeOscillationArray[i];
			} else {
				amplitudeScale = 1;
			}
			attackAmplitudes[i] = (attackAmplitudes[i]+(amplitudeFrequencyArray[i]*amplitudeScale));
		}
		for(int i = 0; i < decayAmplitudes.length; i++) {
			if(amplitudeOscillationArray[i+attackAmplitudes.length]>decayAmplitudes[i]) {
				amplitudeScale = decayAmplitudes[i]/amplitudeOscillationArray[i+attackAmplitudes.length];
			} else {
				amplitudeScale = 1;
			}
			decayAmplitudes[i] = (decayAmplitudes[i]+(amplitudeFrequencyArray[i+attackAmplitudes.length]*amplitudeScale));
		}
		for(int i = 0; i < releaseAmplitudes.length; i++) {
			if(amplitudeOscillationArray[i+attackAmplitudes.length+decayAmplitudes.length]>releaseAmplitudes[i]) {
				amplitudeScale = releaseAmplitudes[i]/amplitudeOscillationArray[i+attackAmplitudes.length+decayAmplitudes.length];
			} else {
				amplitudeScale = 1;
			}
			releaseAmplitudes[i] = (releaseAmplitudes[i]+(amplitudeFrequencyArray[i+attackAmplitudes.length+decayAmplitudes.length]*amplitudeScale));
		}
		scaleDown();
	}

	/**
	 *        Finds the max amplitude and scales it to the amplitude or the sustain, whichever is greater
	 */
	private void scaleDown() {

		//The max amplitude of the envelope
		double envelopeAmplitude =this.amplitude;
		if(envelopeAmplitude < this.sustain) {
			envelopeAmplitude = this.sustain;
		}
		
		// Find the max and scale the envelope back down
		ExecutorService threadRunner = Executors.newCachedThreadPool();
		double max = Array.getMaxMag(attackAmplitudes, threadRunner);
		double tempMax = Array.getMaxMag(decayAmplitudes, threadRunner);
		if(max < tempMax) {
			max = tempMax;
		}
		tempMax = Array.getMaxMag(releaseAmplitudes, threadRunner);
		if(max < tempMax) {
			max = tempMax;
		}

		double amplitudeScale = envelopeAmplitude/max;
		for(int i = 0; i < attackAmplitudes.length; i++) {
			attackAmplitudes[i] = (attackAmplitudes[i]*amplitudeScale);
		}
		for(int i = 0; i < decayAmplitudes.length; i++) {
			decayAmplitudes[i] = (decayAmplitudes[i]*amplitudeScale);
		}
		for(int i = 0; i < releaseAmplitudes.length; i++) {
			releaseAmplitudes[i] = (releaseAmplitudes[i]*amplitudeScale);
		}		
	}
	
}