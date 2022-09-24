package notes;

import envelopes.LinearAmplitudeEnvelope;
import waves.Wave;

import java.util.Objects;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making notes
 */
public class Note {

	// The name of this note
	private String name;

	// The frequency of this note
	private double hertz;

	// The duration of a note in seconds
	double seconds;

	// The amplitude envelope of this note
	LinearAmplitudeEnvelope linearAmplitudeEnvelope;

	// The amplitude of this note
	double amplitude;

	// The phase of this note
	double radians = 0;

	/**        Checks if a note is equal to this note
	 * @param  note The note to check for equality
	 * @return True if equal else false
	 */
	public boolean equals(Note note) {
		return Objects.equals(this.name, note.name) &&
			this.hertz == note.hertz &&
			this.seconds == note.seconds &&
			linearAmplitudeEnvelope.equals(note.linearAmplitudeEnvelope) &&
			this.amplitude == note.amplitude &&
			this.radians == note.radians;
	}

	/**
	 * @return The name of this note. May be empty if not set.
	 */
	public String getName() {
		return name;
	}

	/**       Sets the name of this note
	 * @param name The name of this note
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**       Sets the duration of this note
	 * @param seconds The duration of this note
	 */
	public void setDuration(double seconds) {
		this.seconds = seconds;
	}
	

	/**       Sets the frequency of this note
	 * @param hertz The frequency of this note
	 */
	public void setFrequency(double hertz) {
		this.hertz = hertz;
	}

	/**       Sets the AmplitudeEnvelope of this note
	 * @param linearAmplitudeEnvelope The AmplitudeEnvelope of this note
	 */
	public void setLinearAmplitudeEnvelope(LinearAmplitudeEnvelope linearAmplitudeEnvelope) {
		this.linearAmplitudeEnvelope = linearAmplitudeEnvelope;
		this.seconds = linearAmplitudeEnvelope.getTime();
	}

	/**       Sets the amplitude of this note
	 * @param amplitude The amplitude of this note
	 */
	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}

	/**
	 * @return The frequency of this note
	 */
	public double getFrequency() {
		return hertz;
	}

	/**
	 * @return The AmplitudeEnvelope of this note
	 */
	public LinearAmplitudeEnvelope getLinearAmplitudeEnvelope() {
		return linearAmplitudeEnvelope;
	}

	/**
	 * @return The amplitude of this note
	 */
	public double getAmplitude() {
		return amplitude;
	}

	/**
	 * @return The duration of this note
	 */
	public double getDuration() {
		return seconds;
	}

	/**       Creates a note
	 * @param name The name of this note
	 * @param hertz The frequency of this note
	 */
	public Note(String name, double hertz) {
		this.name = name;
		this.hertz = hertz;
	}

	/**       Creates a note
	 * @param hertz The frequency of this note
	 */
	public Note(double hertz) {
		this.hertz = hertz;
	}

	/**       Creates a note
	 * @param name The name of this note
	 * @param hertz The frequency of this note
	 * @param amplitude The amplitude of this note
	 * @param linearAmplitudeEnvelope The amplitude envelope of this note
	 * @param radians The phase of this note
	 */
	public Note(String name, double hertz, LinearAmplitudeEnvelope linearAmplitudeEnvelope, double radians) {
		this.name = name;
		this.hertz = hertz;
		this.amplitude = linearAmplitudeEnvelope.getAmplitude();
		this.linearAmplitudeEnvelope = linearAmplitudeEnvelope;
		this.seconds = linearAmplitudeEnvelope.getTime();
		this.radians = radians;
	}

	/**       Creates a note
	 * @param hertz The frequency of this note
	 * @param amplitude The amplitude of this note
	 * @param linearAmplitudeEnvelope The amplitude envelope of this note
	 * @param radians The phase of this note
	 */
	public Note(double hertz, LinearAmplitudeEnvelope linearAmplitudeEnvelope, double radians) {
		this.hertz = hertz;
		this.amplitude = linearAmplitudeEnvelope.getAmplitude();
		this.linearAmplitudeEnvelope = linearAmplitudeEnvelope;
		this.seconds = linearAmplitudeEnvelope.getTime();
		this.radians = radians;
	}

	/**        Gets a sample of this note
	 * @param  samplesPerSecond The sample rate 
	 * @return An array representing the wave as amplitude over time 
	 */
	public double[] getWave(Wave waveObject, float samplesPerSecond) {
		double[] wave = new double[(int) Math.round(seconds*samplesPerSecond)];
		if(linearAmplitudeEnvelope == null) {
			double amplitude = waveObject.getAmplitude();
			double sustain = amplitude/2.0;
			if(amplitude == 0) {
				amplitude = 0;
				sustain = 0;
			}
			double attack = seconds/3.0;
			double decay = seconds/3.0;
			double release = seconds/3.0;
			linearAmplitudeEnvelope  = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		}
		/*
		 // TODO SINEWAVE is immutable
		waveObject.setAmplitude(this.amplitude);
		waveObject.setFrequency(this.hertz);
		waveObject.setPhase(this.radians);
		*/
		wave = waveObject.getWave(linearAmplitudeEnvelope.getEnvelope(), samplesPerSecond); 
		return wave;
	}
	
}