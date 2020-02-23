package generator;

import java.util.ArrayList;

import dynamics.Dynamics;
import notes.Note;
import notes.Scale;
import notes.ThreeDimensionalNoteSequence;
import rhythm.Tempo;
import rhythm.TimeSignature;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating NoteSequences
 */
public class SequenceGenerator {

	TimeSignatureGenerator timeSignatureGenerator = new TimeSignatureGenerator();
	TempoGenerator tempoGenerator = new TempoGenerator();
	FrequencyGenerator frequencyGenerator;
	AmplitudeGenerator amplitudeGenerator;
	AmplitudeGenerator sustainGenerator;

	public void bad(double adjustment) {
		timeSignatureGenerator.bad(adjustment);
		tempoGenerator.bad(adjustment);
		frequencyGenerator.bad(adjustment);
		amplitudeGenerator.bad(adjustment);
		sustainGenerator.bad(adjustment);
	}

	public void good(double adjustment) {
		timeSignatureGenerator.good(adjustment);
		tempoGenerator.good(adjustment);
		frequencyGenerator.good(adjustment);
		amplitudeGenerator.good(adjustment);
		sustainGenerator.good(adjustment);
	}

	public SequenceGenerator(Scale scale, int firstNoteIndex, int lastNoteIndex, 
			Dynamics dynamics, Object[] frequencies) {
		frequencyGenerator = new FrequencyGenerator(scale, firstNoteIndex, lastNoteIndex);
		amplitudeGenerator = new AmplitudeGenerator(dynamics);
		sustainGenerator = new AmplitudeGenerator(dynamics);
	}

	public ThreeDimensionalNoteSequence getNextNoteSequence(boolean fullNote, float samplesPerSecond) {

		TimeSignature timeSignature = timeSignatureGenerator.getNextTimeSignature();
		Tempo tempo = tempoGenerator.getNextTempo();
		int bars = (int) Math.round(Math.random()*3+1);
		int beats = (int) Math.round(Math.random()*timeSignature.beatsPerBar+1);
		double[] times = timeSignature.getTime(tempo, bars, beats);
		ArrayList<Double> noteTimes = new ArrayList<Double>();
		int j = 0;

		// skips all the notes under 1/32
		int dif = tempo.noteTimes.length-12;

		// Go to notesTimes-8 to allow envelopeGenerator to stay under the timeSignature beat time
		while(tempo.noteTimes[j] <= times[0] && j < tempo.noteTimes.length-8) {
			noteTimes.add(tempo.noteTimes[j+dif]);
			j++;
		}
		double[] noteTimesSubset = new double[noteTimes.size()];
		for(int i = 0; i < noteTimes.size(); i++) {
			noteTimesSubset[i] = noteTimes.get(i);
		}
		NoteGenerator noteGenerator = new NoteGenerator(frequencyGenerator, amplitudeGenerator, 
				sustainGenerator, noteTimesSubset);		

		Note[][] notes = new Note[1][times.length*2];
		Note tempNote;
		if(!fullNote) {
			for(int i = 0; i < notes[0].length; i+=2) {
				notes[0][i] = noteGenerator.getNextPartialNote(samplesPerSecond);
				tempNote = new Note(0);
				tempNote.setAmplitude(0);
				tempNote.setDuration(noteGenerator.lastRestTime);
				notes[0][i+1] = tempNote;
			}
		} else {
			for(int i = 0; i < notes[0].length; i+=2) {
				notes[0][i] = noteGenerator.getNextFullNote(samplesPerSecond);
				tempNote = new Note(0);
				tempNote.setAmplitude(0);
				tempNote.setDuration(noteGenerator.lastRestTime);
				notes[0][i+1] = tempNote;
			}
		}

		return new ThreeDimensionalNoteSequence(notes);

	}

}
