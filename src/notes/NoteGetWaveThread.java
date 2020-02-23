package notes;

import java.util.concurrent.Callable;

import waves.Wave;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         For making a sine wave samples in a thread
 */
public class NoteGetWaveThread implements Callable<double[]>{

	final Note note;
	final Wave waveObject;
	final float samplesPerSecond;
	double[] wave;
	
	/**       For generating a sine wave sample
	 * @param note as the sine wave object used to generate the sample
	 * @param seconds as the length of the sample
	 * @param samplesPerSecond as the sample rate
	 */
	public NoteGetWaveThread(Note note, Wave waveObject, float samplesPerSecond) {
		this.note = note;
		this.waveObject = waveObject;
		this.samplesPerSecond = samplesPerSecond;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public double[] call() throws Exception {
		if(note == null) {
			return null;
		}
		return note.getWave(waveObject, samplesPerSecond);	
	}
	
}