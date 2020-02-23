package notes;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import waves.Wave;
import waves.WaveConcatThread;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making arrays of notes
 */
public class NoteSequence {

	ArrayList<Note> notes = new ArrayList<Note>();

	/**       Creates a sequence of notes
	 * @param notes An array list of notes 
	 */
	public NoteSequence(ArrayList<Note> notes){
		this.notes.addAll(notes);
	}

	/**       Creates a sequence of notes
	 * @param notes An array list of notes 
	 */
	public NoteSequence(Note[] notes){
		for(int i = 0; i < notes.length; i++) {
			this.notes.add(notes[i]);
		}
	}

	/**       Returns the indices of this note sequence that contain note n
	 * @param note The note to get the indices of in this sequence
	 */
	public int[] getNote(Note note) {
		ArrayList<Integer> indicesArrayList = new ArrayList<Integer>();
		for(int i = 0; i < this.notes.size(); i++) {
			if(note.equals(this.notes.get(i))) {
				indicesArrayList.add(i);
			}
		}
		int[] indices = new int[indicesArrayList.size()];

		for(int i = 0; i < indicesArrayList.size(); i++) {
			indices[i] = indicesArrayList.get(i);
		}

		return indices;
	}

	/**        Gets the note from the specified index from this note sequence
	 * @param  i The index of the note to return
	 * @return The note at the specified index
	 */
	public Note getNote(int i) {
		return this.notes.get(i);
	}

	/**       Appends a note to this sequence of notes
	 * @param note The note to add to this sequence
	 */
	public void addNote(Note note) {
		this.notes.add(note);
	}

	/**       Appends a note array list to this sequence of notes
	 * @param note The array list of notes to append to this sequence
	 */
	public void addNotes(ArrayList<Note> note) {
		this.notes.addAll(note);
	}

	/**       Appends an array of notes to this sequence of notes
	 * @param notes The note array to append to this sequence
	 */
	public void addNotes(Note[] notes) {
		for(int i = 0; i < notes.length; i++) {
			this.notes.add(notes[i]);
		}
	}

	/**       Removes a note from this note sequence from the specified index
	 *        WARNING Will throw an exception if index i is not an index in this note sequence
	 * @param i The index to remove the note from
	 */
	public void removeNote(int i) {
		this.notes.remove(i);
	}

	/**       Removes all instances of a note from this sequence of notes
	 * @param note The note to remove from this sequence
	 */
	public void removeNote(Note note) {
		for(int i = 0; i < this.notes.size(); i++) {
			if(note.equals(this.notes.get(i))) {
				this.notes.remove(i);
			}
		}
	}

	/**       Removes all instances of all notes in the note array from this sequence of notes
	 * @param notes The array of notes to remove from this sequence
	 */
	public void removeNote(Note[] notes) {
		for(int j = 0; j < notes.length; j++) {
			for(int i = 0; i < this.notes.size(); i++) {
				if(notes[j].equals(this.notes.get(i))) {
					this.notes.remove(i);
				}
			}
		}
	}

	/**       Removes all instances of all notes in the note array list from this sequence of notes
	 * @param notes The array list of notes to remove from this sequence
	 */
	public void removeNote(ArrayList<Note> notes) {
		for(int j = 0; j < notes.size(); j++) {
			for(int i = 0; i < this.notes.size(); i++) {
				if(notes.get(j).equals(this.notes.get(i))) {
					this.notes.remove(i);
				}
			}
		}
	}

	/**
	 * @return The time in seconds that this sequence of notes takes
	 */
	public double getTime(){
		double time = 0;
		for(int i = 0; i < notes.size(); i++) {
			time+=notes.get(i).getDuration();
		}
		return time;
	}

	/**
	 * @return The max amplitude of the notes in this sequence
	 */
	public double getAmp(){
		double amplitude = 0;
		double tempAplitude;
		for(int i = 0; i < notes.size(); i++) {
			tempAplitude = notes.get(i).getAmplitude();
			if(tempAplitude > amplitude) {
				amplitude = tempAplitude;
			}
		}
		return amplitude;
	}

	/**        Gets a sample of this sequence of notes
	 * @param  samplesPerSecond The sample rate
	 * @param  waveObject as the wave object that will generate the sound
	 * @return An array of this sequence representing amplitude over time
	 */
	public double[] getSample(ExecutorService threadRunner, Wave waveObject, float samplesPerSecond) {
		ArrayList<Future<double[]>> futureChannelOfWaves = new ArrayList<Future<double[]>>();
		Wave waveObjectCopy;
		for(int i = 0; i < notes.size(); i++) {
			waveObjectCopy = (Wave) waveObject.clone();
			futureChannelOfWaves.add(threadRunner.submit(new NoteGetWaveThread(notes.get(i), waveObjectCopy, samplesPerSecond)));
		}
		ArrayList<double[]> futureChannelOfWavesArrayList = new ArrayList<double[]>();
		for(int i = 0; i < futureChannelOfWaves.size(); i++) {
			try {
				futureChannelOfWavesArrayList.add(futureChannelOfWaves.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return WaveConcatThread.concatWavesButterfly(futureChannelOfWavesArrayList, threadRunner);
	}
}