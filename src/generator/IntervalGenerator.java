package generator;

import dynamics.Dynamics;
import graph.ChanseyCycle;
import notes.Interval;
import notes.Note;
import notes.Scale;
import rhythm.Tempo;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating intervals
 */
public class IntervalGenerator extends ChanseyCycle {

	NoteGenerator noteGenerator;
	double[] lastRestTimes;


	/**       Creates an IntervalGenerator that picks intervals based on the probabilities
	 * @param probabilities as the 8 probabilities of picking each interval from 1 to 8
	 */
	public IntervalGenerator(NoteGenerator noteGenerator, double[] times) {
		this.noteGenerator = noteGenerator;
		Object[] choices = {1, 2, 3, 4, 5, 6, 7, 8};
		this.choices = choices.clone();
	}

	/**        Gets a random interval from 1 to 8
	 * @param  sameLinearAmplitudeEnvelope as whether or not the LinearAmplitudeEnvelopes on both of the Notes in the Interval should be the same
	 * @param  samplesPerSecond as the sample rate
	 * @return an interval that represents a frequency from the scale
	 */
	public Interval getNextInterval(boolean sameLinearAmplitudeEnvelope, float samplesPerSecond) {	
		lastRestTimes = new double[2];
		Note root = noteGenerator.getNextPartialNote(samplesPerSecond);
		lastRestTimes[0] = noteGenerator.lastRestTime;
		int intervalNumber = (int) fun().get(0);
		Note top = noteGenerator.getNextPartialNote(samplesPerSecond);
		if(!sameLinearAmplitudeEnvelope) {
			lastRestTimes[1] = noteGenerator.lastRestTime;
		}
		try {
			top.setFrequency((double) noteGenerator.frequencyGenerator.getIntervalNote(intervalNumber));
		} catch(Exception e) {
			top = null;
		}
		if(top != null && sameLinearAmplitudeEnvelope) {
			top.setLinearAmplitudeEnvelope(root.getLinearAmplitudeEnvelope());
		}
		return new Interval(root, top, intervalNumber);
	}

	/**        Gets a random interval with two random notes 
	 * @param  scale as the scale to get the interval form
	 * @param  tempo as the tempo to derive the LinearAmplitudeEnvelopes with
	 * @param  dynamics as the dynamics to derive the amplitude from
	 * @param  samplesPerSecond as the sample rate
	 * @return a randomly generated interval
	 */
	public static Interval getInterval(Scale scale, Tempo tempo, int bottomFrequencyIndex,int TopFrequencyIndex, Dynamics dynamics, float samplesPerSecond) {

		// TODO ask user to set lowest and highest frequency
		// 320-5120 hz
		FrequencyGenerator frequencyGenerator = new FrequencyGenerator(scale, bottomFrequencyIndex, TopFrequencyIndex);
		AmplitudeGenerator amplitudeGenerator = new AmplitudeGenerator(dynamics);
		AmplitudeGenerator sustainGenerator = new AmplitudeGenerator(dynamics);
		NoteGenerator noteGenerator = new NoteGenerator(frequencyGenerator, amplitudeGenerator, sustainGenerator, tempo.noteTimes);
		noteGenerator.getNextPartialNote(samplesPerSecond);
		Note root = noteGenerator.getNextPartialNote(samplesPerSecond);
		Note top = noteGenerator.getNextPartialNote(samplesPerSecond);
		IntervalGenerator intervalGenerator = new IntervalGenerator(noteGenerator, tempo.noteTimes);
		int intervalNumber = (int) intervalGenerator.fun().get(0);
		Interval interval =  new Interval(root, top, intervalNumber);
		interval.intervalNotes[0].setLinearAmplitudeEnvelope(LinearAmplitudeEnvelopeGenerator.generateLinearAmplitudeEnvelope(tempo, dynamics, samplesPerSecond));
		interval.intervalNotes[1].setLinearAmplitudeEnvelope(LinearAmplitudeEnvelopeGenerator.generateLinearAmplitudeEnvelope(tempo, dynamics, samplesPerSecond));
		return interval;
	}

}
