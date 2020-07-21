package notes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import arrays.Array;
import arrays.ConcatThread;
import arrays.AddThread;
import logic.OrderedPair;
import waves.Wave;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for making three dimensional arrays of notes
 */
public class ThreeDimensionalNoteSequence {

	ArrayList<ArrayList<Note>> notes = new ArrayList<ArrayList<Note>>();

	/**       Creates a 3D sequence of notes
	 * @param notes An array list of an array list of notes 
	 */
	public ThreeDimensionalNoteSequence(ArrayList<ArrayList<Object>> notes){
		Note[] notesArrayList;
		for(int i = 0; i < notes.size(); i++) {
			notesArrayList = new Note[notes.get(i).size()];
			for(int j = 0; j < notes.get(i).size(); j++) {	
				if(notes.get(i).get(j) == null) {
					notesArrayList[j] = null;	
				} else {
					notesArrayList[j] = ((Note)(notes.get(i).get(j)));
				}
			}
			this.notes.add(new ArrayList<Note>(Arrays.asList(notesArrayList)));
		}
	}

	/**       Creates a 3D sequence of notes
	 * @param notes An an array list of note sequences 
	 */
	public ThreeDimensionalNoteSequence(NoteSequence[] notes){
		for(int i = 0; i < notes.length; i++) {
			this.notes.add(notes[i].notes);
		}
	}

	/**       Creates a 3D sequence of notes
	 * @param notes A 3D array of notes 
	 */
	@SuppressWarnings("unchecked")
	public ThreeDimensionalNoteSequence(Note[][] notes){
		ArrayList<Note> notesArrayList = new ArrayList<Note>();
		for(int i = 0; i < notes.length; i++) {
			for(int j = 0; j < notes[i].length; j++) {	
				notesArrayList.add(notes[i][j]);
			}
			this.notes.add((ArrayList<Note>)notesArrayList.clone());
			notesArrayList.clear();
		}
	}

	public ThreeDimensionalNoteSequence(Object[][] notes) {
		Note[] notesArrayList;
		for(int i = 0; i < notes.length; i++) {
			notesArrayList = new Note[notes[i].length];
			for(int j = 0; j < notes[i].length; j++) {	
				if(notes[i][j] == null) {
					notesArrayList[j] = null;	
				} else {
					notesArrayList[j] = ((Note)(notes[i][j]));
				}
			}
			this.notes.add(new ArrayList<Note>(Arrays.asList(notesArrayList)));
		}
	}

	/**       Returns the indices of this 3D note sequence that contain note n
	 * @param note The note to get the indices of in this 3D sequence
	 */
	public OrderedPair[] getNote(Note note) {
		ArrayList<OrderedPair> indicesArrayList = new ArrayList<OrderedPair>();
		for(int i = 0; i < this.notes.size(); i++) {
			for(int j = 0; j < this.notes.get(i).size(); j++) {
				if(note.equals(this.notes.get(i).get(j))) {
					indicesArrayList.add(new OrderedPair(i, j));
				}
			}
		}
		return indicesArrayList.toArray(new OrderedPair[indicesArrayList.size()]);
	}

	/**        Gets the note from the specified index from this 3D note sequence
	 * @param  i The index of the note to return
	 * @return The note at the specified index
	 */
	public Note getNote(OrderedPair orderedPair) {
		return this.notes.get((int) orderedPair.getX()).get((int) orderedPair.getY());
	}

	/**       Appends a note to this 3D sequence of notes to the specified row
	 * @param note The note to add to a row in this 3D sequence
	 * @param i The row of this 3D note sequence to add the new note to
	 */
	public void addNote(Note note, int i) {
		this.notes.get(i).add(note);
	}

	/**       Appends a note array list to this sequence of notes at the specified row
	 * @param notes The array list of notes to append to a row in this 3D sequence
	 * @param i The row of this 3D note sequence to add the new note to
	 */
	public void addNotes(ArrayList<Note> notes, int i) {
		this.notes.get(i).addAll(notes);
	}

	/**       Appends an array of notes to a row in this 3D sequence of notes
	 * @param n The note array to append to a row in this 3D sequence
	 * @param i The row of this 3D note sequence to add the new note array to
	 */
	public void addNotes(Note[] n, int i) {
		for(int j = 0; j < n.length; j++) {
			this.notes.get(i).add(n[j]);
		}
	}

	/**       Removes a note from this 3D note sequence from the specified index
	 *        WARNING Will throw an exception if ordered pair i is not an index in this note sequence
	 * @param i The ordered pair specifying the index to remove the note from
	 */
	public void removeNote(OrderedPair i) {
		this.notes.get((int) i.getX()).remove((int) i.getY());
	}

	/**       Removes all instances of a note from this 3D sequence of notes
	 * @param note The note to remove from this 3D sequence
	 */
	public void removeNote(Note note) {
		for(int i = 0; i < this.notes.size(); i++) {
			for(int j = 0; j < this.notes.get(i).size(); j++) {
				if(note.equals(this.notes.get(i).get(j))) {
					this.notes.get(i).remove(j);
				}
			}
		}
	}

	/**       Removes all instances of all notes in the note array from this 3D sequence of notes
	 * @param notes The array of notes to remove from this 3D sequence
	 */
	public void removeNote(Note[] notes) {
		for(int j = 0; j < notes.length; j++) {
			for(int i = 0; i < this.notes.size(); i++) {
				for(int k = 0; k < this.notes.get(i).size(); k++) {
					if(notes[j].equals(this.notes.get(i).get(k))) {
						this.notes.get(i).remove(k);
					}
				}
			}
		}
	}

	/**       Removes all instances of all notes in the note array list from this 3D sequence of notes
	 * @param notes The array list of notes to remove from this 3D note sequence
	 */
	public void removeNote(ArrayList<Note> notes) {
		for(int j = 0; j < notes.size(); j++) {
			for(int i = 0; i < this.notes.size(); i++) {
				for(int k = 0; k < this.notes.get(i).size(); k++) {
					if(notes.get(j).equals(this.notes.get(i).get(k))) {
						this.notes.get(i).remove(k);
					}
				}
			}
		}
	}

	/**
	 * @return The time in seconds that this 3D sequence of notes takes
	 */
	public double getTime(ExecutorService threadRunner){
		ArrayList<Future<double[]>> futureSineWaves = new ArrayList<Future<double[]>>();
		for(int i = 0; i < notes.size(); i++) {
			threadRunner.submit(new GetNoteSequenceDurationThread(notes, i));
		}
		threadRunner.shutdown();
		try {
			return Array.max(futureSineWaves.get(0).get(), threadRunner);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return -1.0;
	}

	/**
	 * @return The max amplitude of the notes in this 3D sequence
	 */
	public double getAmplitude(){
		double amplitude = 0;
		double tempAmplitude;
		for(int i = 0; i < notes.size(); i++) {
			for(int j = 0; j < notes.get(i).size(); j++) {
				tempAmplitude = notes.get(i).get(j).getAmplitude();
				if(tempAmplitude > amplitude) {
					amplitude = tempAmplitude;
				}
			}
		}
		return amplitude;
	}

	/**        Gets a sample of this 3D sequence of notes
	 * @param  threadRunner as the thread executor
	 * @param  waveObject as the waveObject to generate the wave
	 * @param  samplesPerSecond as the sample rate
	 * @return An array of this 3D sequence representing amplitude over time
	 */
	public double[] getWave(ExecutorService threadRunner, Wave waveObject, float samplesPerSecond) {
		ArrayList<Future<double[]>> wavesToBeAdded = new ArrayList<Future<double[]>>();
		ArrayList<double[]> futureChannelOfWavesArrayList = new ArrayList<double[]>();
		for(int i = 0; i < notes.size(); i++) {
			ArrayList<Future<double[]>> futureChannelOfWaves = new ArrayList<Future<double[]>>();
			Wave waveObjectCopy;
			for(int j = 0; j < notes.get(i).size(); j++) {
				waveObjectCopy = (Wave) waveObject.clone();
				futureChannelOfWaves.add(threadRunner.submit(new NoteGetWaveThread(notes.get(i).get(j), waveObjectCopy, samplesPerSecond)));
			}
			futureChannelOfWavesArrayList.clear();
			for(int k = 0; k < futureChannelOfWaves.size(); k++) {
				try {
					futureChannelOfWavesArrayList.add(futureChannelOfWaves.get(k).get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			if(futureChannelOfWavesArrayList.size() != 0) {
				FutureTask<double[]> future = new FutureTask<double[]>(new Callable<double[]>() {
					@Override
					public double[] call() throws Exception {
						return ConcatThread.concatWavesButterfly(futureChannelOfWavesArrayList, threadRunner);					}
				});
				wavesToBeAdded.add(future);
				threadRunner.submit(future);

			}
		}
		
		return AddThread.addWavesButterfly(wavesToBeAdded, threadRunner);
	}

}