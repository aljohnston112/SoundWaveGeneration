package directedGraph;

import java.util.ArrayList;
import java.util.stream.DoubleStream;

/**
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for directed graph chansey cycles where a group of Objects are picked from randomly to decide the Object
 */
public class ChanseyCycle extends Cycle {

	private static final long serialVersionUID = 879228293146606500L;

	protected int lastReturnedIndex;
	
	protected int lowestProbIndex = 0;

	public int getLowestProbIndex() {
		return lowestProbIndex;
	}

	/**
	 * @return the index from the choices array that was chosen last time fun() was called
	 */
	public int getLastReturnedIndex() {
		return lastReturnedIndex;
	}

	// The group of Objects that makes a split in the directed graph
	protected Object[] choices;

	// The probabilities of picking each Object
	protected  double[] probabilities;

	/**Makes an empty ChanseyCycle
	 * 
	 */
	public ChanseyCycle() {

	}
 
	/**        Adjust the probabilities to make the last returned Object more likely to be returned when fun() is called
	 * @param  adjustment as the percentage to add to the probability of the last Object returned by fun()
	 */
	public void good(double a) {
		double adjustment = getProbabilities()[getLowestProbIndex()]/3.0;
		double[] lastProbabilities = getProbabilities();
		double[] probabilities = new double[this.probabilities.length];
		double actualAdjustment = adjustment;
		if(lastProbabilities[getLastReturnedIndex()]+adjustment > 1.0) {
			actualAdjustment = 1.0 - lastProbabilities[lastReturnedIndex];
		}
		double n = (this.probabilities.length-1.0);
		double adjustmentSubtract = (actualAdjustment/n);
		for(int i = 0; i < probabilities.length-1; i++) {
			if(i == lastReturnedIndex) {
				probabilities[i] = lastProbabilities[i] + actualAdjustment;
			} else {
				probabilities[i] = lastProbabilities[i] - adjustmentSubtract;
			}
		}
		probabilities[probabilities.length-1] = 1.0-DoubleStream.of(probabilities).sum();
		try {
			setProbabilities(probabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**        Adjust the probabilities to make the last returned Object less likely to be returned when fun() is called
	 * @param  adjustment as the percentage to subtract from the probability of the last Object returned by fun()
	 */
	public void bad(double a) {
		double adjustment = getProbabilities()[getLowestProbIndex()]/3.0;
		double[] lastProbabilities = getProbabilities();
		double[] probabilities = new double[this.probabilities.length];
		double actualAdjustment = adjustment;
		if(lastProbabilities[getLastReturnedIndex()] < adjustment) {
			actualAdjustment = lastProbabilities[lastReturnedIndex];
		}
		double adjustmentPlus = (actualAdjustment/(this.probabilities.length-1));
		for(int i = 0; i < probabilities.length-1; i++) {
			if(i == lastReturnedIndex) {
				probabilities[i] = lastProbabilities[i] - actualAdjustment;
				if(probabilities[i] < probabilities[lowestProbIndex]) {
					lowestProbIndex = i;
				}
			} else {
				probabilities[i] = lastProbabilities[i] + adjustmentPlus;
			}
		}
		probabilities[probabilities.length-1] = 1.0-DoubleStream.of(probabilities).sum();
		try {
			setProbabilities(probabilities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return an array with the probabilities of getting any one of the choices in the choices object array
	 */
	public double[] getProbabilities() {
		double[] probabilities;
		if(this.probabilities != null) {
			probabilities = new double[this.probabilities.length];
			for(int i = 0; i < probabilities.length; i++) {
				probabilities[i] = this.probabilities[i];
			}
		} else {
			probabilities = new double[this.choices.length];
			for(int i = 0; i < probabilities.length; i++) {
				probabilities[i] = 1.0/this.choices.length;
			}
			this.probabilities = probabilities.clone();
		}
		return probabilities;
	}

	/**        Update the probabilities of getting any one of the choices from the choices Object array 
	 * @param  probabilities as the new probabilities of getting any one of the choices from the choices Object array
	 * @throws Exception if the probabilities don't add up to 1
	 */
	public void setProbabilities(double[] probabilities) throws Exception {
		double lowestProb = 1.0;
		if(choices.length != probabilities.length) {
			throw ChanseyCycleException("Wrong number of probabilities");
		}
		if((DoubleStream.of(probabilities).sum()) != 1.0) {
			throw ChanseyCycleException("Probabilities don't add up to 1");	
		}
		for(int i = 0; i < probabilities.length; i++) {
			this.probabilities[i] = probabilities[i];
			if(this.probabilities[i] < lowestProb) {
				lowestProb = this.probabilities[i];
				lowestProbIndex = i;
			}
		}
	}

	/**       Generates a chansey cycle
	 * @param ObjectChoices as the choices 
	 * @param loops as the number of loops
	 */
	public ChanseyCycle(Object[] ObjectChoices, int loops) {
		super();
		this.choices = new Object[ObjectChoices.length];
		for(int i = 0; i < ObjectChoices.length; i++) {
			this.choices[i] = ObjectChoices[i];
		}
		this.loops = loops;
	}

	/**       Generates a chansey cycle
	 * @param ObjectChoices as the choices 
	 * @param probabilities as the chance of picking each Object
	 * @param loops as the number of loops the choice of Object will loop
	 * @throws Exception if there is the wrong number of probabilities or the probabilities don't add up to 1
	 */
	public ChanseyCycle(Object[] ObjectChoices, double[] probabilities, int loops) throws Exception {
		super();
		double lowestProb = 1.0;
		if(ObjectChoices.length != probabilities.length) {
			throw ChanseyCycleException("Wrong number of probabilities");
		}
		if((DoubleStream.of(probabilities).sum()) != 1.0) {
			throw ChanseyCycleException("Probabilities don't add up to 1");	
		}
		this.probabilities = new double[probabilities.length];
		this.choices = new Object[ObjectChoices.length];
		for(int i = 0; i < ObjectChoices.length; i++) {
			this.choices[i] = ObjectChoices[i];
		}
		for(int i = 0; i < probabilities.length; i++) {
			this.probabilities[i] = probabilities[i];
			if(this.probabilities[i] < lowestProb) {
				this.probabilities[i] = lowestProb;
				lowestProbIndex = i;
			}
		}
		this.loops = loops;
	}

	/**       Generates a chansey cycle
	 * @param ObjectChoices as the choices 
	 * @param loops as the number of loops
	 * @param loopingObjectHomology as whether or not the Objects will loop the same amount of times every loop
	 */
	public ChanseyCycle(Object[] ObjectChoices, int loops, boolean loopingObjectHomology) {
		super();
		this.choices = new Object[ObjectChoices.length];
		for(int i = 0; i < ObjectChoices.length; i++) {
			this.choices[i] = ObjectChoices[i];
		}
		this.loops = loops;
		this.loopHomology = loopingObjectHomology;
	}

	/**       Generates a chansey cycle
	 * @param ObjectChoices as the choices 
	 * @param probabilities as the chance of picking each Object
	 * @param loops as the number of loops the choice of Object will loop
	 * @param loopingObjectHomology as whether or not the Objects will loop the same amount of times every loop
	 * @throws Exception if there is the wrong number of probabilities or the probabilities don't add up to 1
	 */
	public ChanseyCycle(Object[] ObjectChoices, double[] probabilities, int loops, boolean loopingObjectHomology) throws Exception {
		super();
		double lowestProb = 1.0;
		if(ObjectChoices.length != probabilities.length) {
			throw ChanseyCycleException("Wrong number of probabilities");
		}
		if((DoubleStream.of(probabilities).sum()) != 1.0) {
			throw ChanseyCycleException("Probabilities don't add up to 1");	
		}
		this.probabilities = new double[probabilities.length];
		this.choices = new Object[ObjectChoices.length];
		for(int i = 0; i < ObjectChoices.length; i++) {
			this.choices[i] = ObjectChoices[i];
		}
		for(int i = 0; i < probabilities.length; i++) {
			this.probabilities[i] = probabilities[i];
			if(this.probabilities[i] < lowestProb) {
				this.probabilities[i] = lowestProb;
				lowestProbIndex = i;
			}
		}
		this.loops = loops;
		this.loopHomology = loopingObjectHomology;
	}

	/**        An exception for when a chansey cycle can;t be made
	 * @param  string as the message explaining the error
	 * @return The exception
	 */
	private Exception ChanseyCycleException(String string) {
		return null;
	}

	/**
	 * @return The directed graph Objects that make up this directed graph cycle where the Object choices are equal
	 */
	@Override
	public ArrayList<Object> fun() {
		ArrayList<Object> output = new ArrayList<Object>();
		Object tempDirectedGraphObject;
		int loops;
		if(loopHomology) {
			loops = this.loops;
		} else {
			loops = (int)Math.round(Math.random()*this.loops);
		}
		int indexChoice = 0;
		double choice;
		double sumOfProbabilities = 0;
		for(int j = 0; j < loops+1; j++) {
			choice = Math.random();
			sumOfProbabilities = 0;
			if(probabilities != null) {
				for(int k = 1; (choice > sumOfProbabilities) && (k-1) < probabilities.length; k++) {
					indexChoice = k-1;
					sumOfProbabilities += probabilities[k-1];
				} 
			} else {
				indexChoice = (int)Math.round(Math.random()*(choices.length-1));
			}
			lastReturnedIndex = indexChoice;
			tempDirectedGraphObject = choices[indexChoice];
			if(tempDirectedGraphObject instanceof ChanseyCycle) {
				output.addAll(((ChanseyCycle)tempDirectedGraphObject).fun());
			} else if(tempDirectedGraphObject instanceof Cycle) {
				output.addAll(((Cycle)tempDirectedGraphObject).fun());
			} else {
				output.add(tempDirectedGraphObject);
			}
		}
		return output;
	}

}