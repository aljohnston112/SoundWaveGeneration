package envelopesTest;

import java.util.ArrayList;
import java.util.List;

import envelopes.LinearAmplitudeEnvelope;
import envelopes.LinearFrequencyEnvelope;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import waves.Wave.WaveType;

public class LinearFrequencyEnvelopeTest extends Application {

	// GUI objects
	Stage primaryStage;
	StackPane root;
	ScatterChart<Number,Number> scatterChart;
	NumberAxis xAxis;
	NumberAxis yAxis;

	// For generating the x-axis
	static double minX = 0;
	static double maxX;
	double deltaX;
	static List<double[]> data = new ArrayList<>();
	
	public static void main(String[] args) {
		//testConstructor();
		//testGetLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope();
		testGetWave();
		launch(args);
	}
	
	private static void testGetWave() {
		//getWave(double amplitude, double seconds, WaveType waveType, float samplesPerSecond)
		System.out.println("Creating a linear frequency envelope with...");
		System.out.println("1st frequency of 1...");
		System.out.println("2st frequency of 0.1...");
		System.out.println("3st frequency of 2...");
		System.out.println("4st frequency of 0.2...");
		System.out.println("Attack of 5...");
		System.out.println("Decay of 5...");
		System.out.println("Release of 5...");
		double attack = 5.0; 
		double decay = 5.0; 
		double release = 5.0; 
		double firstFrequency = 1.0;
		double secondFrequency = 0.1;
		double thirdFrequency = 0.5; 
		double lastFrequency = 0.2;
		float samplesPerSecond = (float) Math.pow(2.0, 5);
		LinearFrequencyEnvelope linearFrequencyEnvelope = new LinearFrequencyEnvelope(attack, decay, release, 
				firstFrequency, secondFrequency, thirdFrequency, lastFrequency, samplesPerSecond);
		double amplitude = 1.0; 
		double seconds = 15;
		WaveType waveType = WaveType.NOISE;
		data.add(linearFrequencyEnvelope.getWave(amplitude, seconds, waveType, samplesPerSecond));
	}

	private static void testGetLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope() {
		//  getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope(LinearAmplitudeEnvelope linearAmplitudeEnvelope, 
		//                                                        double amplitude, WaveType waveType, float samplesPerSecond)
		System.out.println("Creating a linear frequency envelope with...");
		System.out.println("1st frequency of 1...");
		System.out.println("2st frequency of 0.1...");
		System.out.println("3st frequency of 2...");
		System.out.println("4st frequency of 0.2...");
		System.out.println("Attack of 5...");
		System.out.println("Decay of 5...");
		System.out.println("Release of 5...");
		double attack = 5.0; 
		double decay = 5.0; 
		double release = 5.0; 
		double firstFrequency = 1.0;
		double secondFrequency = 0.1;
		double thirdFrequency = 0.5; 
		double lastFrequency = 0.2;
		float samplesPerSecond = (float) Math.pow(2.0, 5);
		LinearFrequencyEnvelope linearFrequencyEnvelope = new LinearFrequencyEnvelope(attack, decay, release, 
				firstFrequency, secondFrequency, thirdFrequency, lastFrequency, samplesPerSecond);
		
		System.out.println("Creating a linear amplitude envelope with...");
		System.out.println("Amplitude of 1...");
		System.out.println("Sustain of 2...");
		System.out.println("Attack of 5...");
		System.out.println("Decay of 5...");
		System.out.println("Release of 5...");
		double amplitude = 1.0; 
		double sustain = 2.0; 
		LinearAmplitudeEnvelope linearAmplitudeEnvelope = new LinearAmplitudeEnvelope(amplitude, sustain, attack, decay, release, samplesPerSecond);
		WaveType waveType = WaveType.TRIANGLE;
		data.add(linearFrequencyEnvelope.getLinearFrequencyEnvelopeWithLinearAmplitudeEnvelope(
				linearAmplitudeEnvelope, amplitude, waveType, samplesPerSecond));
	}

	private static void testConstructor() {
		//LinearFrequencyEnvelope(double attack, double decay, double release, 
		//                        double firstFrequency, double secondFrequency, double thirdFrequency, 
		//                        double lastFrequency, double samplesPerSecond)
		 
		System.out.println("Creating a linear frequency envelope with...");
		System.out.println("1st frequency of 1...");
		System.out.println("2st frequency of 0.1...");
		System.out.println("3st frequency of 2...");
		System.out.println("4st frequency of 0.2...");
		System.out.println("Attack of 5...");
		System.out.println("Decay of 5...");
		System.out.println("Release of 5...");
		double attack = 5.0; 
		double decay = 5.0; 
		double release = 5.0; 
		double firstFrequency = 1.0;
		double secondFrequency = 0.1;
		double thirdFrequency = 2.0; 
		double lastFrequency = 0.2;
		float samplesPerSecond = (float) Math.pow(2.0, 5);
		LinearFrequencyEnvelope linearFrequencyEnvelope = new LinearFrequencyEnvelope(attack, decay, release, 
				firstFrequency, secondFrequency, thirdFrequency, lastFrequency, samplesPerSecond);
		data.add(linearFrequencyEnvelope.getEnvelope());		
		
	}

	/**For labeling the axis'
	 * 
	 */
	private void labelAxis() {
		primaryStage.setTitle("LinearFreqEnv Test");
		Text text = new Text("LinearFreqEnv methods");
		root.getChildren().add(text);
		xAxis.setLabel("Sample Numer");
		yAxis.setLabel("Frequency");
		scatterChart.setTitle("LinearFreqEnv methods");			
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		root = new StackPane();
		primaryStage.setScene(new Scene(root, 720, 720));
		primaryStage.show();	
		double[] xAxis = generateXAxis(data);
		plotData(xAxis, data);

	}

	/**        Generates the values for the xAxis
	 * @param  data as multiple sets of y values
	 * @return an array of double with the x-values for the x-axis
	 */
	private double[] generateXAxis(List<double[]> data) {

		// Find the y value set with the most values
		int maxXIndex = 0;
		for(int i = 0; i < data.size(); i++) {
			while(data.get(i) == null) {
				i++;
				if( i >= data.size()) {
					break;
				}
			}
			if( i >= data.size()) {
				break;
			}
			if(maxX < data.get(i).length) {
				maxX = data.get(i).length;
				maxXIndex = i;
			}
		}

		// Generate the x-axis data 
		deltaX = maxX/data.get(maxXIndex).length;
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
	public void plotData(double[] x, List<double[]> y) {

		// For generating the y-axis
		double minY = 0;
		double maxY = 0;
		for(int j = 0; j < y.size(); j++) {
			while(y.get(j) == null) {
				j++;
				if( j >= y.size()) {
					break;
				}
			}
			if( j >= y.size()) {
				break;
			}
			for(int i = 0; i < y.get(j).length; i++) {
				if(y.get(j)[i] < minY) {
					minY = y.get(j)[i];
				}
				if(y.get(j)[i] > maxY) {
					maxY = y.get(j)[i];
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
		List<XYChart.Series<Number, Number>> series = new ArrayList<XYChart.Series<Number, Number>>();
		for(int j = 0; j < y.size(); j++) {
			while(y.get(j) == null) {
				j++;
				if( j >= y.size()) {
					break;
				}
			}
			if( j >= y.size()) {
				break;
			}
			series.add(new XYChart.Series<Number, Number>());
			for(int i = 0; i < y.get(j).length; i++) {
				series.get(j).getData().add(new XYChart.Data<Number, Number>(x[i],y.get(j)[i]));
			}
		}
		scatterChart.getData().addAll(series);
		root.getChildren().add(scatterChart);
	}
	
}
