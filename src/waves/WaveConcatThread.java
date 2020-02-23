package waves;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class WaveConcatThread implements Callable<double[]> {

	double[] wave1;
	double[] wave2;
	double[] concatWave;

	/**       For concatenating waves
	 * @param wave1 as one of the waves to concatenate to the other
	 * @param wave2 as one of the waves to concatenate to the other
	 */
	public WaveConcatThread(double[] wave1, double[] wave2){
		this.wave1 = wave1;
		this.wave2 = wave2;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public double[] call() throws Exception {
		return Wave.concatWave(wave1, wave2);
	}
	
	/**        Concatenates arrays of doubles in a butterfly method. 
	 *         They are concatenated in the order that they appear in the array
	 * @param  futureChannelOfWaves as the channel of waves as separate double[] to be added
	 * @param  threadRunner as the thread executor
	 * @return a double[] with all the waves concatenated together
	 */
	public static double[] concatWavesButterfly(ArrayList<double[]> futureChannelOfWaves, ExecutorService threadRunner) {
		if(futureChannelOfWaves.size() == 1) {
			return futureChannelOfWaves.get(futureChannelOfWaves.size()-1);
		}
		if(futureChannelOfWaves.size() == 0) {
			return null;
		}
		ArrayList<Future<double[]>> futureConcatonatedChannels = new ArrayList<Future<double[]>>();
		if(futureChannelOfWaves.size() % 2 == 0) {
			for(int i = 0; i < futureChannelOfWaves.size(); i+=2) {
				futureConcatonatedChannels.add(threadRunner.submit(new WaveConcatThread(futureChannelOfWaves.get(i), futureChannelOfWaves.get(i+1))));
			}
		} else {
			for(int i = 0; i < futureChannelOfWaves.size()-1; i+=2) {
				futureConcatonatedChannels.add(threadRunner.submit(new WaveConcatThread(futureChannelOfWaves.get(i), futureChannelOfWaves.get(i+1))));
			}
			futureConcatonatedChannels.add(threadRunner.submit(new WaveConcatThread(futureChannelOfWaves.get(futureChannelOfWaves.size()-1), null)));
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
		return concatWavesButterfly(futureConcatonatedChannelsArrayList, threadRunner);
	}
}