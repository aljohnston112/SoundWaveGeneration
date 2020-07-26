package art;

import java.util.ArrayList;
import java.util.List;

public class C {

	public static void main(String[] args) {
		double middleA = 440;
		double numNotes = 12.0;
		double division = (1.0/numNotes);
		double cPosition = -9;
		double middleC = middleA*Math.pow(Math.pow(2.0, division), cPosition);
		List<Double> topCs = new ArrayList<>();
		List<Double> bottomCs = new ArrayList<>();
		double tempC = middleC;
		double tempCPosition = cPosition;
		while(tempC < 20000) {
			tempC = middleA*Math.pow(Math.pow(2.0, division), tempCPosition);
			topCs.add(tempC);
			tempCPosition+=12;
		}
		tempCPosition = cPosition-12;
		while(bottomCs.size() < topCs.size()) {
			tempC = middleA*Math.pow(Math.pow(2.0, division), tempCPosition);
			bottomCs.add(tempC);
			tempCPosition-=12;
		}
		//algorithms.EqualLoudnessContour.getGainAWeight();
		
	}

}
