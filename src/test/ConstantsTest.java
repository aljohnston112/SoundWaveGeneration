package test;

public class ConstantsTest {

	static final int BITS_PER_SAMPLE = 16;

	static final double  MAXIMUM_AMPLITUDE = (StrictMath.pow(2.0, BITS_PER_SAMPLE-1))/2.0;

	static final float SAMPLES_PER_SECOND = 44100;

	static final double MAXIMUM_FREQUENCY = (SAMPLES_PER_SECOND/2.0)-1.0;

	static final double MIDDLE_A = 440.0;
	
}
