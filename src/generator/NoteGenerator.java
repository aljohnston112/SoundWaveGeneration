package generator;

import dynamics.Dynamics;
import envelopes.LinearAmplitudeEnvelope;
import notes.Note;
import notes.Scale;
import rhythm.Tempo;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating random notes
*/
public class NoteGenerator {

	FrequencyGenerator frequencyGenerator;
		
	LinearAmplitudeEnvelopeGenerator linearAmplitudeEnvelopeGenerator;
	
	double totalTime;
	
	double lastRestTime;
	
	double lastNoteIndex;
	
	public void bad(double bias) {
		frequencyGenerator.bad(bias);
		linearAmplitudeEnvelopeGenerator.bad(bias);
	}
	
	public void good(double bias) {
		frequencyGenerator.good(bias);
		linearAmplitudeEnvelopeGenerator.good(bias);
	}
	
	/**
	 * @return the index of the last note from the scale 
	 */
	public double getLastNoteIndex() {
		return lastNoteIndex;
	}

	/**
	 * @return the rest time following the Note time
	 */
	public double getLastRestTime() {
		return lastRestTime;
	}

	/**       Creates a NoteGenerator
	 * @param frequencyGenerator as the frequency generator that determines the frequency of the note
	 * @param amplitudes as the amplitude array that determines the sustain and amplitude of the LinearAmplitudeEnvelope
	 * @param times as the times that determine the time of the note (which is the last time in the time array) 
	 *        and the attack, decay, and release of the LinearAmplitudeEnvelope
	 *        Note: there must be at least 8 times
	 */
	public NoteGenerator(FrequencyGenerator frequencyGenerator, AmplitudeGenerator amplitudeGenerator, 
			AmplitudeGenerator sustainGenerator, double[] times) {
		this.frequencyGenerator = frequencyGenerator;
		linearAmplitudeEnvelopeGenerator = new LinearAmplitudeEnvelopeGenerator(times, amplitudeGenerator, sustainGenerator);
		totalTime = (double) times[times.length-1];
	}
	
	/**        Gets a random note
	 * @param  scale as the scale to get the note from
	 * @param  tempo as the tempo to get the time values for the linear amplitude envelope from 
	 * @param  dynamics as the dynamics object to get the amplitude values of the linear amplitude envelope from
	 * @param  samplesPerSecond as the sample rate
	 * @return a random note
	 */
	public Note getNextPartialNote(float samplesPerSecond) {
		double hertz = frequencyGenerator.getNextHertz();
		double radians = 0.0;
		Note note = new Note(hertz, linearAmplitudeEnvelopeGenerator.getLinearAmplitudeEnvelope(samplesPerSecond), radians);
		lastRestTime = totalTime - note.getDuration();
		lastNoteIndex = frequencyGenerator.getLastReturnedIndex();
		return note;
	}
	
	/**        Gets a random note
	 * @param  scale as the scale to get the note from
	 * @param  tempo as the tempo to get the time values for the linear amplitude envelope from 
	 * @param  dynamics as the dynamics object to get the amplitude values of the linear amplitude envelope from
	 * @param  samplesPerSecond as the sample rate
	 * @return a random note
	 */
	public Note getNextFullNote(float samplesPerSecond) {
		double hertz = frequencyGenerator.getNextHertz();
		double radians = 0.0;
		double remainingTimePercentage = 1.0;
		double timePercentage = Math.random();
		remainingTimePercentage -= timePercentage;
		double attack = totalTime*timePercentage;
		do {
		timePercentage = Math.random();
		
		} while((remainingTimePercentage-timePercentage)<0);
		double decay = totalTime*timePercentage;
		double release = totalTime-attack-decay;
		Note note = new Note(hertz, linearAmplitudeEnvelopeGenerator.getLinearAmplitudeEnvelope(attack, decay, release, samplesPerSecond), radians);
		lastRestTime = totalTime - note.getDuration();
		lastNoteIndex = frequencyGenerator.getLastReturnedIndex();
		return note;
	}
	
	/**        Gets a random note
	 * @param  scale as the scale to get the note from
	 * @param  tempo as the tempo to get the time values for the linear amplitude envelope from 
	 * @param  dynamics as the dynamics object to get the amplitude values of the linear amplitude envelope from
	 * @param  samplesPerSecond as the sample rate
	 * @return a random note
	 */
	public static Note getNote(Scale scale, int firstNoteIndex, int lastNoteIndex, Tempo tempo, Dynamics dynamics, float samplesPerSecond) {
		double hertz = FrequencyGenerator.getHertz(scale, firstNoteIndex, lastNoteIndex);
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = LinearAmplitudeEnvelopeGenerator.generateLinearAmplitudeEnvelope(tempo, dynamics, samplesPerSecond);
		double radians = 0.0;
		return new Note(hertz, linearAmplitudeEnvelope, radians);
	}
	
}