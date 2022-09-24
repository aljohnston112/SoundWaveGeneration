package arrays;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ConcatThread implements Callable<double[]> {

	double[] array1;
	double[] array2;
	double[] concatarray;

	/**       For concatenating arrays
	 * @param array1 as one of the arrays to concatenate to the other
	 * @param array2 as one of the arrays to concatenate to the other
	 */
	public ConcatThread(double[] array1, double[] array2){
		this.array1 = array1;
		this.array2 = array2;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public double[] call() throws Exception {
		return Array.concat(array1, array2);
	}
	
	/**        Concatenates arrays of doubles in a butterfly method. 
	 *         They are concatenated in the order that they appear in the array
	 * @param  futureChannelOfArrays as the channel of arrays as separate double[] to be added
	 * @param  threadRunner as the thread executor
	 * @return a double[] with all the arrays concatenated together
	 */
	public static double[] concatArraysButterfly(ArrayList<double[]> futureChannelOfArrays, ExecutorService threadRunner) {
		if(futureChannelOfArrays.size() == 1) {
			return futureChannelOfArrays.get(futureChannelOfArrays.size()-1);
		}
		if(futureChannelOfArrays.size() == 0) {
			return null;
		}
		ArrayList<Future<double[]>> futureConcatonatedChannels = new ArrayList<Future<double[]>>();
		if(futureChannelOfArrays.size() % 2 == 0) {
			for(int i = 0; i < futureChannelOfArrays.size(); i+=2) {
				futureConcatonatedChannels.add(threadRunner.submit(new ConcatThread(futureChannelOfArrays.get(i), futureChannelOfArrays.get(i+1))));
			}
		} else {
			for(int i = 0; i < futureChannelOfArrays.size()-1; i+=2) {
				futureConcatonatedChannels.add(threadRunner.submit(new ConcatThread(futureChannelOfArrays.get(i), futureChannelOfArrays.get(i+1))));
			}
			futureConcatonatedChannels.add(threadRunner.submit(new ConcatThread(futureChannelOfArrays.get(futureChannelOfArrays.size()-1), null)));
		}
		ArrayList<double[]> futureConcatonatedChannelsArrayList = new ArrayList<double[]>();
		for(int i = 0; i < futureConcatonatedChannels.size(); i++) {
			try {
				futureConcatonatedChannelsArrayList.add(futureConcatonatedChannels.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return concatArraysButterfly(futureConcatonatedChannelsArrayList, threadRunner);
	}
}