package envelopesTest;

import java.util.ArrayList;
import java.util.List;

import envelopes.AmplitudeOscillator;
import waves.Wave.WaveType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AmplitudeOscillatorTest extends Application {

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

	/*
	AmplitudeOscillator(double startingFrequency, double endingFrequency, 
			double startingAmplitudeWidth, double endingAmplitudeWidth, double radians)
	double[] getAmplitudeArray(double seconds, float samplesPerSecond)
	double[] getOscillator(double seconds, WaveType waveType, float samplesPerSecond)
	double[] getSample(LinearAmplitudeEnvelope linearAmplitudeEnvelope, WaveType waveType, float samplesPerSecond)
	 */
	public static void main(String[] args) {
		//testAmpOscAmpConstructor();
		testAmpOscGetOscillator();
		launch(args);
	}

	private static void testAmpOscGetOscillator() {
		System.out.println("Constructing AmplitudeOscillator with amplitude of 10...");
		System.out.println("Starting frequency of 1...");
		System.out.println("Ending frequency of 0.1...");
		System.out.println("Starting amplitude width of 10...");
		System.out.println("Ending amplitude width of 20...");
		System.out.println("And phase of 0...");
		double startingFrequency = 1;
		double endingFrequency = 0.1;
		double startingAmplitudeWidth = 10;
		double endingAmplitudeWidth = 20;
		double radians = 0;
		AmplitudeOscillator ao = new AmplitudeOscillator(startingFrequency, endingFrequency, 
				startingAmplitudeWidth, endingAmplitudeWidth, radians);
		double seconds = 16;
		float samplesPerSecond = (float) Math.pow(2.0, 5);
		WaveType waveType = WaveType.TRIANGLE;
		data.add(ao.getOscillator(seconds, waveType, samplesPerSecond));	
		
	}

	private static void testAmpOscAmpConstructor() {
		System.out.println("Constructing AmplitudeOscillator with amplitude of 10...");
		System.out.println("Starting frequency of 1...");
		System.out.println("Ending frequency of 0.1...");
		System.out.println("Starting amplitude width of 10...");
		System.out.println("Ending amplitude width of 10...");
		System.out.println("And phase of 0...");
		double startingFrequency = 1;
		double endingFrequency = 0.1;
		double startingAmplitudeWidth = 10;
		double endingAmplitudeWidth = 10;
		double radians = 0;
		AmplitudeOscillator ao = new AmplitudeOscillator(startingFrequency, endingFrequency, 
				startingAmplitudeWidth, endingAmplitudeWidth, radians);
		System.out.println("Constructing AmplitudeOscillator with amplitude of 10...");
		System.out.println("Starting frequency of 1...");
		System.out.println("Ending frequency of 1...");
		System.out.println("Starting amplitude width of 10...");
		System.out.println("Ending amplitude width of 50...");
		System.out.println("And phase of PI...");
		
		startingFrequency = 1;
		endingFrequency = 1;
		startingAmplitudeWidth = 10;
		endingAmplitudeWidth = 15;
		radians = StrictMath.PI;
		AmplitudeOscillator ao2 = new AmplitudeOscillator(startingFrequency, endingFrequency, 
				startingAmplitudeWidth, endingAmplitudeWidth, radians);
		double seconds = 16;
		float samplesPerSecond = (float) Math.pow(2.0, 8);
		WaveType waveType = WaveType.SINE;
		data.add(ao2.getOscillator(seconds, waveType, samplesPerSecond));
		data.add(ao.getOscillator(seconds, waveType, samplesPerSecond));
	
	}

	/**For labeling the axis'
	 * 
	 */
	private void labelAxis() {
		primaryStage.setTitle("AmpOsc Test");
		Text text = new Text("AmpOsc getWave() methods");
		root.getChildren().add(text);
		xAxis.setLabel("Sample Numer");
		yAxis.setLabel("Amplitude");
		scatterChart.setTitle("AmpOsc getOscillator() methods");			
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

