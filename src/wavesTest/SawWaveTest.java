package wavesTest;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import waves.SawWave;

public class SawWaveTest extends Application {

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
		SawWave(double amplitude, double hertz, double radians, boolean updateRadians)
		double[] getWave(double seconds, float samplesPerSecond)
		double[] getWave(double[] amplitudeArray, float samplesPerSecond)
	 */

	public static void main(String[] args) {
		//testSawWaveAmp();
		//testSawWaveFreq();
		//testSawWavePhase();
		testSawWaveShort();
		//testSawWaveAmpArray();
		launch(args);
	}

	private static void testSawWaveAmpArray() {
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 4...");
		System.out.println("And phase of 0...");
		double amplitude = 10;
		double hertz = 4;
		double radians = 0;
		boolean updateRadians = true;
		SawWave sw = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get amplitude: ");
		System.out.println(sw.getAmplitude());
		System.out.println("Constructing SawWave with amplitude of 5...");
		System.out.println("Frequency of 4...");
		System.out.println("And phase of 0...");
		amplitude = 5;
		SawWave sw2 = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get amplitude: ");
		System.out.println(sw2.getAmplitude());
		double seconds = 4;
		float samplesPerSecond = (float) Math.pow(2.0, 8);
		double[] amplitudeArray = new double[(int)Math.round(samplesPerSecond*seconds)];
		for(int i = 0; i < amplitudeArray.length; i++) {
			amplitudeArray[i] = (double)i*(1.0/amplitudeArray.length);
		}		
		data.add(sw.getWave(amplitudeArray, samplesPerSecond));
		data.add(sw2.getWave(amplitudeArray, samplesPerSecond));			
	}

	private static void testSawWaveShort() {
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 1...");
		System.out.println("And phase of 0...");
		double amplitude = 10;
		double hertz = 1;
		double radians = 0;
		boolean updateRadians = true;
		SawWave sw = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 1...");
		System.out.println("And phase of PI...");
		radians = StrictMath.PI;
		SawWave sw2 = new SawWave(amplitude, hertz, radians, updateRadians);
		double seconds = 0.1;
		float samplesPerSecond = (float) Math.pow(2.0, 8);
		data.add(sw.getWave(seconds, samplesPerSecond));
		data.add(sw2.getWave(seconds, samplesPerSecond));
	}

	private static void testSawWavePhase() {
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 1...");
		System.out.println("And phase of 0...");
		double amplitude = 10;
		double hertz = 1;
		double radians = 0;
		boolean updateRadians = true;
		SawWave sw = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get radians: ");
		System.out.println(sw.getRadians());
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 1...");
		System.out.println("And phase of PI...");
		radians = StrictMath.PI;
		SawWave sw2 = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get radians: ");
		System.out.println(sw2.getRadians());
		double seconds = 4;
		float samplesPerSecond = (float) Math.pow(2.0, 8);
		data.add(sw.getWave(seconds, samplesPerSecond));
		data.add(sw2.getWave(seconds, samplesPerSecond));			
	}

	private static void testSawWaveFreq() {
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 1...");
		System.out.println("And phase of 0...");
		double amplitude = 10;
		double hertz = 1;
		double radians = 0;
		boolean updateRadians = true;
		SawWave sw = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get hertz: ");
		System.out.println(sw.getHertz());
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 4...");
		System.out.println("And phase of 0...");
		hertz = 4;
		SawWave sw2 = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get hertz: ");
		System.out.println(sw2.getHertz());
		double seconds = 4;
		float samplesPerSecond = (float) Math.pow(2.0, 8);
		data.add(sw.getWave(seconds, samplesPerSecond));
		data.add(sw2.getWave(seconds, samplesPerSecond));
	}

	private static void testSawWaveAmp() {
		System.out.println("Constructing SawWave with amplitude of 10...");
		System.out.println("Frequency of 1...");
		System.out.println("And phase of 0...");
		double amplitude = 10;
		double hertz = 1;
		double radians = 0;
		boolean updateRadians = true;
		SawWave sw = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get amplitude: ");
		System.out.println(sw.getAmplitude());
		System.out.println("Constructing SawWave with amplitude of -5...");
		System.out.println("Frequency of 1...");
		System.out.println("And phase of 0...");
		amplitude = -5;
		SawWave sw2 = new SawWave(amplitude, hertz, radians, updateRadians);
		System.out.print("Get amplitude: ");
		System.out.println(sw2.getAmplitude());
		double seconds = 4;
		float samplesPerSecond = (float) Math.pow(2.0, 6);
		data.add(sw.getWave(seconds, samplesPerSecond));
		data.add(sw2.getWave(seconds, samplesPerSecond));
	}

	/**For labeling the axis'
	 * 
	 */
	private void labelAxis() {
		primaryStage.setTitle("SawWave Test");
		Text text = new Text("SawWave getWave() methods");
		root.getChildren().add(text);
		xAxis.setLabel("Sample Numer");
		yAxis.setLabel("Amplitude");
		scatterChart.setTitle("SawWave getWave() methods");			
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
