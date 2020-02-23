package generator;

import graph.ChanseyCycle;
import rhythm.Tempo;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for generating Tempos
*/
public class TempoGenerator extends ChanseyCycle {
	
	/** Creates a TempoGenerator
	 * 
	 */
	public TempoGenerator() {
		choices = new Object[240-60];
		for(int i = 0; i < choices.length; i++) {
			choices[i] = i+60;
		}
	}
	
	/**
	 * @return a randomly generated Tempo
	 */
	public Tempo getNextTempo() {
		return new Tempo((int) fun().get(0));
	}
	
	/**
	 * @return a tempo with a random bpm from 60 t0 240
	 */
	public static Tempo getTempo(){
		// 60 - 240 TODO make a setting to make adjustable
		double bpm = (Math.round(Math.random()*180+60));
		return new Tempo(bpm);
	}
	
}
