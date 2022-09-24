package generator;

import dynamics.Dynamics;
import envelopes.LinearAmplitudeEnvelope;
import graph.ChanseyCycle;
import rhythm.Tempo;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating random linear amplitude envelopes
 */
public class LinearAmplitudeEnvelopeGenerator {

	AmplitudeGenerator amplitudeGenerator;

	AmplitudeGenerator sustainGenerator;

	TimeGenerator[] timeGenerators;

	static final Object[] ENVELOPE_CHOICES = {0, 1, 2};

	static final Object[] DIFFERENCE_CHOICES = {0, 1, 2};

	static final Object[] TOTAL_LENGTH_CHOICES = {0, 1};

	static final Object[] TOTAL_LENGTH_CHOICES2 = {0, 1};

	static final Object[] ATTACK_LENGTH_CHOICES = {0, 1, 2};

	static final Object[] DECAY_RELEASE_LENGTH_CHOICES = {0, 1};

	ChanseyCycle envelopeChoice = new ChanseyCycle(ENVELOPE_CHOICES, 0);

	ChanseyCycle differenceChoice = new ChanseyCycle(DIFFERENCE_CHOICES, 0);

	ChanseyCycle totalLengthChoice = new ChanseyCycle(TOTAL_LENGTH_CHOICES, 0); 

	ChanseyCycle totalLengthChoice2 = new ChanseyCycle(TOTAL_LENGTH_CHOICES2, 0); 

	ChanseyCycle attackLengthChoice = new ChanseyCycle(ATTACK_LENGTH_CHOICES, 0); 

	ChanseyCycle decayReleaseChoice	= new ChanseyCycle(DECAY_RELEASE_LENGTH_CHOICES, 0);

	double[] times;

	public void bad(double bias) {
		amplitudeGenerator.bad(bias);
		sustainGenerator.bad(bias);
		for (TimeGenerator timeGenerator : timeGenerators) {
			timeGenerator.bad(bias);
		}
		envelopeChoice.bad(bias);
		differenceChoice.bad(bias);
		totalLengthChoice.bad(bias);
		totalLengthChoice2.bad(bias);
		attackLengthChoice.bad(bias);
		decayReleaseChoice.bad(bias);
	}
	
	public void good(double bias) {
		amplitudeGenerator.good(bias);
		sustainGenerator.good(bias);
		for(int i = 0; i < timeGenerators.length; i++) {
			timeGenerators[i].good(bias);
		}
		envelopeChoice.good(bias);
		differenceChoice.good(bias);
		totalLengthChoice.good(bias);
		totalLengthChoice2.good(bias);
		attackLengthChoice.good(bias);
		decayReleaseChoice.good(bias);
	}
	
	/**        Creates an TimeGenerator that picks a time from a time array based on the probabilities
	 * @param  times as the objects that holds the time values
	 * @param  amplitudes as the amplitude array
	 * @param  amplitudeProbabilities as the probabilities of getting each amplitude
	 * @param  sustainProbabilities as the probabilities of getting each amplitude for the sustain
	 * @throws Exception if the probabilities in each array don't add up to one 
	 *                   or the probability arrays don't have the same amount of doubles as the number of Objects in their respective arrays
	 */
	public LinearAmplitudeEnvelopeGenerator(double[] times, AmplitudeGenerator amplitudeGenerator, AmplitudeGenerator sustainGenerator, double[] amplitudeProbabilities, double[] sustainProbabilities) throws Exception {
		this.amplitudeGenerator = amplitudeGenerator;
		this.sustainGenerator = sustainGenerator;
		this.times = times.clone();
	}

	/**        Creates an TimeGenerator that picks a time from a time array with the same probabilities
	 * @param  times as the objects that holds the time values
	 * @param  amplitudes as the amplitude array
	 */
	public LinearAmplitudeEnvelopeGenerator(double[] times, AmplitudeGenerator amplitudeGenerator, AmplitudeGenerator sustainGenerator) {
		this.amplitudeGenerator = amplitudeGenerator;
		this.sustainGenerator = sustainGenerator;
		this.times = times.clone();
	}

	/**        Gets a random LinearAmplitudeEnvelope from the time and amplitude arrays
	 * @param  samplesPerSecond as the sample rate
	 * @return a LinearAmplitudeEnvelope that has times and amplitudes from time and amplitude arrays
	 */
	public LinearAmplitudeEnvelope getLinearAmplitudeEnvelope(float samplesPerSecond) {

		// TODO add AI to the choice parameters
		double amplitude = (double) amplitudeGenerator.fun().get(0);
		double sustain = (double) sustainGenerator.fun().get(0);
		double attack = 0;
		double decay = 0;
		double release = 0;

		if(timeGenerators == null) {
			Object[] timesMinus1 = new Object[times.length-1];
			Object[] timesMinus2 = new Object[times.length-2];
			Object[] timesMinus3 = new Object[times.length-3];
			Object[] timesMinus4 = new Object[times.length-4];
			Object[] timesMinus5 = new Object[times.length-5];
			Object[] timesMinus7 = new Object[times.length-7];
			for(int i = 0; i < timesMinus7.length; i++) {
				timesMinus1[i] = times[i];
				timesMinus2[i] = times[i];
				timesMinus3[i] = times[i];
				timesMinus4[i] = times[i];
				timesMinus5[i] = times[i];
				timesMinus7[i] = times[i];
			}
			timesMinus5[times.length-7] = times[times.length-7];
			timesMinus5[times.length-6] = times[times.length-6];
			timesMinus4[times.length-7] = times[times.length-7];
			timesMinus4[times.length-6] = times[times.length-6];
			timesMinus4[times.length-5] = times[times.length-5];
			timesMinus3[times.length-7] = times[times.length-7];
			timesMinus3[times.length-6] = times[times.length-6];
			timesMinus3[times.length-5] = times[times.length-5];
			timesMinus3[times.length-4] = times[times.length-4];
			timesMinus2[times.length-7] = times[times.length-7];
			timesMinus2[times.length-6] = times[times.length-6];
			timesMinus2[times.length-5] = times[times.length-5];
			timesMinus2[times.length-4] = times[times.length-4];
			timesMinus2[times.length-3] = times[times.length-3];
			timesMinus1[times.length-7] = times[times.length-7];
			timesMinus1[times.length-6] = times[times.length-6];
			timesMinus1[times.length-5] = times[times.length-5];
			timesMinus1[times.length-4] = times[times.length-4];
			timesMinus1[times.length-3] = times[times.length-3];
			timesMinus1[times.length-2] = times[times.length-2];

			timeGenerators = new TimeGenerator[6];
			timeGenerators[0] = new TimeGenerator(timesMinus1);
			timeGenerators[1] = new TimeGenerator(timesMinus2);
			timeGenerators[2] = new TimeGenerator(timesMinus3);
			timeGenerators[3] = new TimeGenerator(timesMinus4);
			timeGenerators[4] = new TimeGenerator(timesMinus5);
			timeGenerators[5] = new TimeGenerator(timesMinus7);
		}
		// 0 is AR, 1 is ADR, 2, is ASDR

		//TODO add the ChanseyCycle
		int envelopeChoice = 2; //  (int)(Math.round(Math.random()*2));
		if (envelopeChoice == 2) {// ADR
			double firstTimeChoice = 0;
			double secondTimeChoice = 0;
			double thirdTimeChoice = 0;

			switch ((int) differenceChoice.fun().get(0)) {
				case 0:
					// Attack, decay and release are all possibly different
					if ((int) totalLengthChoice.fun().get(0) == 0) {
						firstTimeChoice = timeGenerators[0].getNextTime();
						secondTimeChoice = timeGenerators[3].getNextTime();
						thirdTimeChoice = timeGenerators[5].getNextTime();
					} else {
						firstTimeChoice = timeGenerators[1].getNextTime();
						secondTimeChoice = timeGenerators[2].getNextTime();
						thirdTimeChoice = timeGenerators[4].getNextTime();
					}
					break;
				case 1:
					// Attack, decay and release where 2 are possibly the same
					if ((int) totalLengthChoice2.fun().get(0) == 0) {
						firstTimeChoice = timeGenerators[0].getNextTime();
						secondTimeChoice = timeGenerators[4].getNextTime();
						thirdTimeChoice = timeGenerators[4].getNextTime();
					} else {
						firstTimeChoice = timeGenerators[1].getNextTime();
						secondTimeChoice = timeGenerators[3].getNextTime();
						thirdTimeChoice = timeGenerators[3].getNextTime();
					}
					break;
				case 2:
					// Attack, decay and release where all 3 are possibly the same
					firstTimeChoice = timeGenerators[2].getNextTime();
					secondTimeChoice = timeGenerators[2].getNextTime();
					thirdTimeChoice = timeGenerators[2].getNextTime();
					break;
			}
			int decayReleaseChoice = (int) this.decayReleaseChoice.fun().get(0);
			switch ((int) attackLengthChoice.fun().get(0)) {
				case 0 -> {
					attack = firstTimeChoice;
					switch (decayReleaseChoice) {
						case 0 -> {
							decay = secondTimeChoice;
							release = thirdTimeChoice;
						}
						case 1 -> {
							decay = thirdTimeChoice;
							release = secondTimeChoice;
						}
					}
				}
				case 1 -> {
					attack = secondTimeChoice;
					switch (decayReleaseChoice) {
						case 0 -> {
							decay = firstTimeChoice;
							release = thirdTimeChoice;
						}
						case 1 -> {
							decay = thirdTimeChoice;
							release = firstTimeChoice;
						}
					}
				}
				case 2 -> {
					attack = thirdTimeChoice;
					switch (decayReleaseChoice) {
						case 0 -> {
							decay = secondTimeChoice;
							release = firstTimeChoice;
						}
						case 1 -> {
							decay = firstTimeChoice;
							release = secondTimeChoice;
						}
					}
				}
			}
		}
		return new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
	}
	
	/**        Gets a random LinearAmplitudeEnvelope from the time and amplitude arrays
	 * @param  samplesPerSecond as the sample rate
	 * @return a LinearAmplitudeEnvelope that has times and amplitudes from time and amplitude arrays
	 */
	public LinearAmplitudeEnvelope getLinearAmplitudeEnvelope(double attack, double decay, double release, float samplesPerSecond) {

		double amplitude = (double) amplitudeGenerator.fun().get(0);
		double sustain = (double) sustainGenerator.fun().get(0);
		return new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
	}

	/**        Generates a random linear amplitude envelope
	 * @param  times as the times to choose from for the attack, decay, and release where the last double is the greatest time
	 *         Note: Must have at least 8 times to choose from
	 * @param  dynamics as the dynamics object that will provide the amplitude
	 * @param  samplesPerSecond as the sample rate
	 * @return a randomly generated linear amplitude envelope
	 */
	public static LinearAmplitudeEnvelope generateLinearAmplitudeADREnvelope(double[] times, Dynamics dynamics, float samplesPerSecond) {
		double amplitude = AmplitudeGenerator.getAmplitude(dynamics);
		double sustain = AmplitudeGenerator.getAmplitude(dynamics);

		int firstChoice = (int)(Math.round(Math.random()*2));
		int firstTimeIndexChoice = 0;
		int secondTimeIndexChoice = 0;
		int thirdTimeIndexChoice = 0;

		switch (firstChoice) {
			// Attack, decay and release are all possibly different
			case 0 -> {
				firstTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 3)));
				secondTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 4)));
				thirdTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 6)));
			}
			// Attack, decay and release where 2 are possibly the same
			case 1 -> {
				firstTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 3)));
				secondTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 5)));
				thirdTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 5)));
			}
			// Attack, decay and release where all 3 are possibly the same
			case 2 -> {
				firstTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 4)));
				secondTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 4)));
				thirdTimeIndexChoice = (int) (Math.round(Math.random() * (times.length - 4)));
			}
		}
		int secondChoice = (int)(Math.round(Math.random()*2));
		int thirdChoice = (int)(Math.round(Math.random()*1));
		double attack = 0;
		double decay = 0;
		double release = 0;

		switch (secondChoice) {
			case 0 -> {
				attack = times[firstTimeIndexChoice];
				switch (thirdChoice) {
					case 0 -> {
						decay = times[secondTimeIndexChoice];
						release = times[thirdTimeIndexChoice];
					}
					case 1 -> {
						decay = times[thirdTimeIndexChoice];
						release = times[secondTimeIndexChoice];
					}
				}
			}
			case 1 -> {
				attack = times[secondTimeIndexChoice];
				switch (thirdChoice) {
					case 0 -> {
						decay = times[firstTimeIndexChoice];
						release = times[thirdTimeIndexChoice];
					}
					case 1 -> {
						decay = times[thirdTimeIndexChoice];
						release = times[firstTimeIndexChoice];
					}
				}
			}
			case 2 -> {
				attack = times[thirdTimeIndexChoice];
				switch (thirdChoice) {
					case 0 -> {
						decay = times[secondTimeIndexChoice];
						release = times[firstTimeIndexChoice];
					}
					case 1 -> {
						decay = times[firstTimeIndexChoice];
						release = times[secondTimeIndexChoice];
					}
				}
			}
		}

		return new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
	}

	/**        Generates a random linear amplitude envelope
	 * @param  tempo as the tempo that will provide the time
	 * @param  dynamics as the dynamics object that will provide the amplitude
	 * @param  samplesPerSecond as the sample rate
	 * @return a randomly generated linear amplitude envelope
	 */
	public static LinearAmplitudeEnvelope generateLinearAmplitudeEnvelope(Tempo tempo, Dynamics dynamics, float samplesPerSecond) {
		double amplitude = AmplitudeGenerator.getAmplitude(dynamics);
		double sustain = AmplitudeGenerator.getAmplitude(dynamics);
		double attack = TimeGenerator.getTime(tempo);
		double decay = TimeGenerator.getTime(tempo);
		double release = TimeGenerator.getTime(tempo);
		return new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
	}

}