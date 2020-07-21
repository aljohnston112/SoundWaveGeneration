package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import audioLines.MonoAudioLine;
import dynamics.Dynamics;
import generator.SequenceGenerator;
import notes.MajorScale;
import notes.ThreeDimensionalNoteSequence;
import notes.TwelveToneEqualTemperament;
import waves.SineWave;
import waves.Wave;

public class AIVideoPlayer {


	//private static File folder = new File("C:\\Users\\Al\\Documents\\Videos\\TV\\Pokemon S02-S05 (1997-) + 5 Movies (1999-2002)\\Pokemon S02 Adventures on the Orange Islands (360p re-dvdrip)");
	private static String currentDirectory = "C:\\Users\\Al\\Documents\\Videos\\Movies\\new\\0\\1\\2\\3";
	private static File folder = new File(currentDirectory);

	static final int BITS_PER_SAMPLE = 16;

	static final double  MAXIMUM_AMPLITUDE = (StrictMath.pow(2.0, BITS_PER_SAMPLE-1))/2.0;

	static final float SAMPLES_PER_SECOND = 44100;

	static final double MAXIMUM_FREQUENCY = (SAMPLES_PER_SECOND/2.0)-1.0;

	static final double MIDDLE_A = 440.0;
	
	static double bias;

	private File f = new File(".f");
	private final JFrame frame = new JFrame("#1");
	SequenceGenerator p;

	private ArrayList<Integer> alreadyPlayed = new ArrayList<Integer>();

	KeyListener kl = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_COMMA) {
				p.bad(bias);
				playAudio();
			} else if(e.getKeyCode() == KeyEvent.VK_PERIOD) {
				p.good(bias);
				playAudio();
			}
		}
	};

	public static void main(String[] args) {
		new AIVideoPlayer();
	}

	public AIVideoPlayer() {
		setUpJFrame();
		setUp();
		//saveFile();
	}

	private void setUp() {
		Dynamics amplitudes = new Dynamics(MAXIMUM_AMPLITUDE);

		// Create a temperament
		TwelveToneEqualTemperament twelveToneEqualTemperament = new TwelveToneEqualTemperament(MIDDLE_A, SAMPLES_PER_SECOND);

		// Create a scale
		char tonicLetter = 'A';
		MajorScale majorScale = new MajorScale(twelveToneEqualTemperament, tonicLetter);

		// 320-5120 hz
		int bottomFrequencyIndex = majorScale.subHertzIndex+(7*5)+1;
		int topFrequencyIndex = majorScale.subHertzIndex+(6*7);
		Object[] frequencies = new Object[topFrequencyIndex-bottomFrequencyIndex+1];
		for(int i = 0; i < frequencies.length; i++) {
			frequencies[i] = majorScale.notes.get(0+bottomFrequencyIndex+i).getFrequency();
		}
		p = new SequenceGenerator(majorScale, bottomFrequencyIndex, topFrequencyIndex, amplitudes, frequencies);
		playAudio();
	}

	private void playAudio() {
		System.out.print("Start");
		boolean fullNote = true;
		ThreeDimensionalNoteSequence threeDimensionalNoteSequence = p.getNextNoteSequence(fullNote, SAMPLES_PER_SECOND);

		// Create a SineWave
		double amplitude = MAXIMUM_AMPLITUDE;
		double hertz = MIDDLE_A;
		double radians = 0.0;
		SineWave sineWave = new SineWave(amplitude, hertz, radians);
		ExecutorService threadRunner = Executors.newCachedThreadPool();;
		double[] data = threeDimensionalNoteSequence.getWave(threadRunner, sineWave, SAMPLES_PER_SECOND);
		threadRunner.shutdown();

		// Make audio channel
		MonoAudioLine mac = new MonoAudioLine(SAMPLES_PER_SECOND, BITS_PER_SAMPLE);

		// Play audio
		mac.play(Wave.normalize(data, BITS_PER_SAMPLE));
		System.out.print("End\n");		
	}

	private void saveFile() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		/*try {
			fos = new FileOutputStream(folder.getAbsoluteFile()+"\\.playlist");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		*/
		try {
			if(fos != null) {
				oos = new ObjectOutputStream(fos);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(oos != null) {
			try {
				oos.writeObject(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setUpJFrame() {
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		frame.addKeyListener(kl);
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}