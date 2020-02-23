package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import envelopes.LinearAmplitudeEnvelope;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import waves.SineWave;

/**        
 * @author Alexander Johnston
 *         Copyright 2019
 *         A class for class testing with plotting y-values
 */
public class MainPlotter extends Application {

	// GUI objects
	Stage primaryStage;
	StackPane root;
	ScatterChart<Number,Number> scatterChart;
	NumberAxis xAxis;
	NumberAxis yAxis;

	// For generating the x-axis
	double minX = 0;
	double maxX;
	double deltaX;

	// The bit rate per sample
	static final int BITS_PER_SAMPLE = 16;

	/**        Generates the y-values
	 * @return the y-values for the data
	 */
	private double[][] generateData() {
		return testLinearAmplitudeEnvelope();
	}

	/**Test the LinearAmplitudeEnvelope and Envelope classes
	 * Some of the test is in the MainPlotter class
	 *         Passed
	 * @return LinearEnvelope data axis'
	 */
	private double[][] testLinearAmplitudeEnvelope() {

		// Create a linear amplitude envelope
		double[][] data = new double[15][];
		double amplitude = 1.0; 
		double sustain = 2.0; 
		double attack = 1.0; 
		double decay = 2.0; 
		double release = 1.0; 
		float samplesPerSecond = 120;
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		data[0] = linearAmplitudeEnvelope.getEnvelope();

		// Create a SineWave
		double amplitudeOfTremolo = 1.0;
		double hertz = 2.0;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitudeOfTremolo, hertz, radians);

		ExecutorService threadRunner = Executors.newCachedThreadPool();
		
		// Add tremolo to linear amplitude envelope
		linearAmplitudeEnvelope.addTremolo(sineWave, samplesPerSecond, threadRunner);
		double time = linearAmplitudeEnvelope.getTime();
		data[1] = linearAmplitudeEnvelope.getEnvelope();

		// Add tremolo swell to attack to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope2 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope2.addTremoloSwellToAttack(sineWave, samplesPerSecond);
		data[2] = linearAmplitudeEnvelope2.getEnvelope();

		// Add tremolo swell to decay to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope3 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope3.addTremoloSwellToDecay(sineWave, samplesPerSecond);
		data[3] = linearAmplitudeEnvelope3.getEnvelope();

		// Add tremolo swell to release to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope4 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope4.addTremoloSwellToRelease(sineWave, samplesPerSecond);
		data[4] = linearAmplitudeEnvelope4.getEnvelope();

		// Add tremolo swell to attack and decay to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope5 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope5.addTremoloSwellToAttackAndDecay(sineWave, samplesPerSecond);
		data[5] = linearAmplitudeEnvelope5.getEnvelope();

		// Add tremolo swell to attack and release to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope6 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope6.addTremoloSwellToAttackAndRelease(sineWave, samplesPerSecond);
		data[6] = linearAmplitudeEnvelope6.getEnvelope();

		// Add tremolo swell to decay and release to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope7 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope7.addTremoloSwellToDecayAndRelease(sineWave, samplesPerSecond);
		data[7] = linearAmplitudeEnvelope7.getEnvelope();

		// Add tremolo to attack to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope8 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope8.addTremoloToAttack(sineWave, samplesPerSecond);
		data[8] = linearAmplitudeEnvelope8.getEnvelope();

		// Add tremolo to decay to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope9 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope9.addTremoloToDecay(sineWave, samplesPerSecond);
		data[9] = linearAmplitudeEnvelope9.getEnvelope();

		// Add tremolo to release to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope10 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope10.addTremoloToRelease(sineWave, samplesPerSecond);
		data[10] = linearAmplitudeEnvelope10.getEnvelope();

		// Add tremolo to release to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope11 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope11.addTremoloToAttackAndDecay(sineWave, samplesPerSecond);
		data[11] = linearAmplitudeEnvelope11.getEnvelope();

		// Add tremolo to release to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope12 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope12.addTremoloToAttackAndRelease(sineWave, samplesPerSecond);
		data[12] = linearAmplitudeEnvelope12.getEnvelope();

		// Add tremolo to release to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope13 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope13.addTremoloToDecayAndRelease(sineWave, samplesPerSecond);
		data[13] = linearAmplitudeEnvelope13.getEnvelope();

		// Add tremolo swell to linear amplitude envelope
		LinearAmplitudeEnvelope linearAmplitudeEnvelope14 = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		linearAmplitudeEnvelope14.addTremoloSwell(sineWave, samplesPerSecond, threadRunner);
		data[14] = linearAmplitudeEnvelope14.getEnvelope();
		return data;
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**For labeling the axis'
	 * 
	 */
	private void labelAxis() {
		primaryStage.setTitle("Plotter");
		Text text = new Text("Plotter");
		root.getChildren().add(text);
		xAxis.setLabel("Time");
		yAxis.setLabel("Amplitude");
		scatterChart.setTitle("Envelope W/ Trem");			
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainPlotter.this.primaryStage = primaryStage;
		root = new StackPane();
		primaryStage.setScene(new Scene(root, 720, 720));
		primaryStage.show();	

		double[][] data = generateData();
		double[] xAxis = generateXAxis(data);
		loadData(xAxis, data);

	}

	/**        Generates the values for the xAxis
	 * @param  data as multiple sets of y values
	 * @return an array of double with the x-values for the x-axis
	 */
	private double[] generateXAxis(double[][] data) {

		// Find the y value set with the most values
		int maxXIndex = 0;
		for(int i = 0; i < data.length; i++) {
			while(data[i] == null) {
				i++;
				if( i >= data.length) {
					break;
				}
			}
			if( i >= data.length) {
				break;
			}
			if(maxX < data[i].length) {
				maxX = data[i].length;
				maxXIndex = i;
			}
		}

		// Generate the x-axis data 
		deltaX = maxX/data[maxXIndex].length;
		double[] xAxis = new double[(int) Math.round((maxX/deltaX))];
		for(int i = 0; i < xAxis.length; i++) {
			xAxis[i] = i*deltaX;
		}
		return xAxis;
	}
	/**       Loads data into a scatter plot
	 * @param x as the x-values
	 * @param y as multiple sets of y-values
	 */

	public void loadData(double[] x, double[][] y) {

		// For generating the y-axis
		double minY = 0;
		double maxY = 0;
		for(int j = 0; j < y.length; j++) {
			while(y[j] == null) {
				j++;
				if( j >= y.length) {
					break;
				}
			}
			if( j >= y.length) {
				break;
			}
			for(int i = 0; i < y[j].length; i++) {
				if(y[j][i] < minY) {
					minY = y[j][i];
				}
				if(y[j][i] > maxY) {
					maxY = y[j][i];
				}
			}
		}
		// Generates the axis'
		double deltaY = maxY/100.0;
		xAxis = new NumberAxis(minX, maxX, deltaX);
		yAxis = new NumberAxis(minY, maxY, deltaY);
		// Renders the GUI
		scatterChart = new ScatterChart<>(xAxis, yAxis);
		scatterChart.setHorizontalGridLinesVisible(false);
		scatterChart.setVerticalGridLinesVisible(false);
		labelAxis();
		XYChart.Series series = new XYChart.Series();
		for(int j = 0; j < y.length; j++) {
			while(y[j] == null) {
				j++;
				if( j >= y.length) {
					break;
				}
			}
			if( j >= y.length) {
				break;
			}
			for(int i = 0; i < x.length; i++) {
				series.getData().add(new XYChart.Data(x[i],y[j][i]));
			}
		}
		scatterChart.getData().add(series);
		root.getChildren().add(scatterChart);
	}

}