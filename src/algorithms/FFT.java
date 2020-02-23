package algorithms;

/**
@author Alexander Johnston 
        Copyright 2019 
        A class for performing fast Fourier transform operations
 */
public class FFT {

	// TODO perform calculations in threads
	
	/**        Performs a forward fast Fourier transform using real numbers
	 * @param  w The time domain wave to take the fast Fourier transform of
	 * @return The complex number array of the frequency domain wave 
	 */
	static public ComplexNumber[] ForwardReal(double[] w){
		ComplexNumber[] fft = new ComplexNumber[w.length];
		ComplexNumber sum = new ComplexNumber(0, 0);
		double x;
		double xt = (2.0*StrictMath.PI)/w.length;
		for(int k = 0; k < w.length; k++) {
			for(int n = 0; n < w.length; n++) {
				x = xt*k*n;
				sum = ComplexNumber.add(sum, 
						ComplexNumber.multiply((new 
								ComplexNumber(StrictMath.cos(x), -StrictMath.sin(x))), (w[n])));
			}
			fft[k] = sum;
			sum =  new ComplexNumber(0, 0);
		}
		return fft;
	}

	/**        Performs a forward fast Fourier transform for complex numbers
	 * @param  w The time domain wave to take the fast Fourier transform of
	 * @return The complex number array of the frequency domain wave 
	 */
	static public ComplexNumber[] ForwardComplex(ComplexNumber[] w){
		ComplexNumber[] fft = new ComplexNumber[w.length];
		ComplexNumber sum = new ComplexNumber(0, 0);
		double x;
		double x2 = 2.0*StrictMath.PI/w.length;
		for(int k = 0; k < w.length; k++) {
			for(int n = 0; n < w.length; n++) {
				x = x2*k*n;
				sum = ComplexNumber.add(sum, 
						ComplexNumber.multiply((new 
								ComplexNumber(StrictMath.cos(x), -StrictMath.sin(x))), (w[n])));
			}
			fft[k] = sum;
			sum =  new ComplexNumber(0, 0);
		}
		return fft;
	}

	/**        Performs a inverse fast Fourier transform
	 * @param  fft The complex number array of the frequency domain wave 
	 * @return The time domain wave 
	 */
	static public ComplexNumber[] Inverse(ComplexNumber[] fft){
		ComplexNumber[] w = new ComplexNumber[fft.length];
		ComplexNumber sum = new ComplexNumber(0, 0);
		double x;
		double x2 = 2.0*StrictMath.PI/w.length;
		for(int k = 0; k < w.length; k++) {
			for(int n = 0; n < w.length; n++) {
				x = x2*k*n;
				sum = ComplexNumber.add(sum, 
						ComplexNumber.multiply((new 
								ComplexNumber(StrictMath.cos(x), StrictMath.sin(x))), 
								ComplexNumber.multiply(fft[n], (-1.0/w.length))));
			}
			w[k] = sum;
			sum =  new ComplexNumber(0, 0);
		}
		return w;
	}

	/**        Gets the amplitude values of the fast Fourier transform bins
	 * @param  fft The complex number array of the frequency domain signal
	 * @return An array of amplitudes
	 */
	static public double[] getAmplitudes(ComplexNumber[] fft) {
		double[] m = new double[fft.length];
		for(int i = 0; i < fft.length; i++) {
			m[i] = StrictMath.sqrt(StrictMath.pow((fft[i].getReal()), 2.0)
					+(StrictMath.pow(fft[i].getImaginary(), 2.0)))/fft.length;
		}
		return m;
	}

	/**        Gets the phase values of the fast Fourier transform bins
	 * @param  fft The complex number array of the frequency domain signal
	 * @return An array of phases
	 */
	static public double[] getPhase(ComplexNumber[] fft) {
		double[] p = new double[fft.length];
		for(int i = 0; i < fft.length; i++) {
			p[i] = StrictMath.atan((fft[i].getImaginary()/(fft[i].getReal())));
		}
		return p;
	}

	public static double[][] maq(double[] w, int window, float sr) {
		int rounds = (int) Math.ceil(w.length/window);
		double[] f = new double[(int) ((window/2.0))];
		double df = sr/window;
		for(int i = 0; i < f.length; i++) {
			f[i] = (int) (((df*i)+df*(i+1))/2.0);
		}
		double[] amp = new double[rounds];
		double[][] out = new double[rounds+1][f.length];
		out[0] = f;
		double[] temp = new double[window];
		for(int i = 0; i < rounds; i++) {
			for(int j = 0; j < window; j++) {
				if(((i*window)+j) > w.length-1) {
					temp[j] = 0;
				} else {
					temp[j] = w[(i*window)+j];
				}
			}
			ComplexNumber[] cn = FFT.ForwardReal(temp);
			amp = FFT.getAmplitudes(cn);
			for(int j = 0; j < f.length; j++) {
				out[i+1][j] = amp[j];
			}
		}
		return out;
	}

}