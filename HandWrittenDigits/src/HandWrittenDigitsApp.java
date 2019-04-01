import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JLabel;

public class HandWrittenDigitsApp {

	private JFrame frame;
	private JTextField textFieldForImageDirectory;
	private String directory = "./Pictures";
	private String directoryForImage = "";
	private String filename = "filename";
	private static Network neuralNetwork;
	private int trainingRow = 0;
	private int batchSize = 0;
	private JTextField txtNumberOfRows;
	private JTextField txtBatchSize;
	private JLabel ErrorValue;
	private LoadSaveNetwork loadSaveNetwork;
	private NeuralNetworkPanel neuralNetworkPanel;
	private boolean showInNetwork = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					neuralNetwork = new Network(784, 2, 16, 10);
					HandWrittenDigitsApp window = new HandWrittenDigitsApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HandWrittenDigitsApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();

		frame.setBounds(100, 100, 700, 921);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(413, 11, 261, 754);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JPanel networkPanel = new JPanel();
		networkPanel.setBounds(10, 11, 366, 850);
		frame.getContentPane().add(networkPanel);
		networkPanel.setLayout(null);

		this.neuralNetworkPanel = new NeuralNetworkPanel(neuralNetwork);
		neuralNetworkPanel.setBounds(10, 11, 346, 850);
		networkPanel.add(neuralNetworkPanel);

		DrawPanel drawingPanel = new DrawPanel(neuralNetwork, neuralNetworkPanel);
		drawingPanel.setBounds(152, 11, 99, 140);
		panel.add(drawingPanel);

		JButton btnClearImage = new JButton("clear Image");
		btnClearImage.setBounds(53, 81, 89, 23);
		panel.add(btnClearImage);

		JButton btnStoreImage = new JButton("Store Image");
		btnStoreImage.setBounds(152, 162, 99, 23);
		panel.add(btnStoreImage);

		JButton btnSaveImageToFitNetwork = new JButton("Fit Image to AI");
		btnSaveImageToFitNetwork.setBounds(80, 359, 124, 23);
		panel.add(btnSaveImageToFitNetwork);

		textFieldForImageDirectory = new JTextField();
		textFieldForImageDirectory.setBounds(119, 422, 142, 20);
		textFieldForImageDirectory.setText(directory);
		panel.add(textFieldForImageDirectory);
		textFieldForImageDirectory.setColumns(10);

		ErrorValue = new JLabel("-----");
		ErrorValue.setBounds(123, 719, 138, 23);
		panel.add(ErrorValue);

		JToggleButton toggleButtonForDigit0 = new JToggleButton("0");
		toggleButtonForDigit0.setBounds(120, 310, 50, 25);
		panel.add(toggleButtonForDigit0);
		toggleButtonForDigit0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "0\\";
				filename = "0";
			}

		});

		JToggleButton toggleButtonForDigit1 = new JToggleButton("1");
		toggleButtonForDigit1.setBounds(60, 220, 50, 25);
		panel.add(toggleButtonForDigit1);
		toggleButtonForDigit1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "1\\";
				filename = "1";
			}
		});

		JToggleButton toggleButtonForDigit2 = new JToggleButton("2");
		toggleButtonForDigit2.setBounds(120, 220, 50, 25);
		panel.add(toggleButtonForDigit2);
		toggleButtonForDigit2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "2\\";
				filename = "2";
			}
		});

		JButton btnTrainAi = new JButton("Train AI ");
		btnTrainAi.setBounds(105, 659, 111, 49);
		panel.add(btnTrainAi);
		btnTrainAi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				trainingRow = Integer.valueOf(txtNumberOfRows.getText());
				batchSize = Integer.valueOf(txtBatchSize.getText());
				SendDataToNeuralNetwork sendDataToNeuralNetwork = new SendDataToNeuralNetwork();
				sendDataToNeuralNetwork.setPixelsAsInputData(neuralNetwork,
						textFieldForImageDirectory.getText() + "\\TrainingData\\", batchSize);
				sendDataToNeuralNetwork.sendTheTrainingDataToNetwork(trainingRow);

				String meanError = String.valueOf(neuralNetwork.getMeanError());
				ErrorValue.setText(meanError);

			}

		});

		JToggleButton tglbtnShowInNetwork = new JToggleButton("Show In NeuralNetwork");
		tglbtnShowInNetwork.setBounds(0, 11, 142, 59);
		panel.add(tglbtnShowInNetwork);

		txtNumberOfRows = new JTextField();
		txtNumberOfRows.setText("Number of Rows");
		txtNumberOfRows.setBounds(105, 597, 111, 20);
		panel.add(txtNumberOfRows);
		txtNumberOfRows.setColumns(10);

		txtBatchSize = new JTextField();
		txtBatchSize.setText("Batch Size");
		txtBatchSize.setBounds(105, 628, 111, 20);
		panel.add(txtBatchSize);
		txtBatchSize.setColumns(10);

		JLabel lblMeanErrorValue = new JLabel("Mean Error Value:");
		lblMeanErrorValue.setBounds(8, 715, 105, 31);
		panel.add(lblMeanErrorValue);

		JToggleButton toggleButton = new JToggleButton("3");
		toggleButton.setBounds(180, 220, 50, 25);
		panel.add(toggleButton);
		toggleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "3\\";
				filename = "3";
			}
		});

		JToggleButton toggleButton_1 = new JToggleButton("4");
		toggleButton_1.setBounds(60, 250, 50, 25);
		panel.add(toggleButton_1);
		toggleButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "4\\";
				filename = "4";
			}
		});

		JToggleButton toggleButton_2 = new JToggleButton("5");
		toggleButton_2.setBounds(120, 250, 50, 25);
		panel.add(toggleButton_2);
		toggleButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "5\\";
				filename = "5";
			}
		});

		JToggleButton toggleButton_3 = new JToggleButton("6");
		toggleButton_3.setBounds(180, 250, 50, 25);
		panel.add(toggleButton_3);
		toggleButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "6\\";
				filename = "6";
			}
		});

		JToggleButton toggleButton_4 = new JToggleButton("7");
		toggleButton_4.setBounds(60, 280, 50, 25);
		panel.add(toggleButton_4);
		toggleButton_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "7\\";
				filename = "7";
			}
		});

		JToggleButton toggleButton_5 = new JToggleButton("8");
		toggleButton_5.setBounds(120, 280, 50, 25);
		panel.add(toggleButton_5);
		toggleButton_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "8\\";
				filename = "8";
			}
		});

		JToggleButton toggleButton_6 = new JToggleButton("9");
		toggleButton_6.setBounds(180, 280, 50, 25);
		panel.add(toggleButton_6);

		JLabel lblDirectoryForData = new JLabel("Directory For Data");
		lblDirectoryForData.setBounds(10, 421, 100, 23);
		panel.add(lblDirectoryForData);

		JLabel lblTrainingRows = new JLabel("Training Rows");
		lblTrainingRows.setBounds(8, 600, 87, 14);
		panel.add(lblTrainingRows);

		JLabel lblBatchSize = new JLabel("Batch Size");
		lblBatchSize.setBounds(8, 631, 87, 14);
		panel.add(lblBatchSize);
		toggleButton_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				directoryForImage = "9\\";
				filename = "9";
			}
		});

		JButton btnSaveNeuralNetwork = new JButton("Save Neural Network");
		btnSaveNeuralNetwork.setBounds(528, 788, 146, 48);
		frame.getContentPane().add(btnSaveNeuralNetwork);
		btnSaveNeuralNetwork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadSaveNetwork = new LoadSaveNetwork();
				loadSaveNetwork.saveNetwork(neuralNetwork);
			}
		});

		JButton btnLoadSavedNetwork = new JButton("load saved network");
		btnLoadSavedNetwork.setBounds(378, 813, 127, 23);
		frame.getContentPane().add(btnLoadSavedNetwork);

		JLabel lblToIllustrateA = new JLabel("To illustrate a neural network by Johan H\u00E4ggmark 2018");
		lblToIllustrateA.setBounds(365, 866, 333, 14);
		frame.getContentPane().add(lblToIllustrateA);
		btnLoadSavedNetwork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadSaveNetwork = new LoadSaveNetwork();
				neuralNetwork = loadSaveNetwork.loadNetwork(neuralNetwork);
				neuralNetworkPanel.loadNeuralNetwork(neuralNetwork);
				drawingPanel.loadNeuralNetwork(neuralNetwork);
			}
		});

		tglbtnShowInNetwork.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent a) {
				// read the graphics
				// ImagePixels imagePixels = new ImagePixels();
				// int[] inputData =
				// imagePixels.getPixelsFromBufferedImage(drawingPanel.getBufferedImage());
				if (drawingPanel.isShowInNetwork() == false) {
					drawingPanel.setShowInNetwork(true);
				} else {
					drawingPanel.setShowInNetwork(false);
				}
				ConvertPicture convertPicture = new ConvertPicture();
				SendDataToNeuralNetwork sendDataToNetwork = new SendDataToNeuralNetwork();
				BufferedImage bufferedImage = convertPicture.convertImageSizeOnly(drawingPanel.getBufferedImage(), 28,
						28);
				sendDataToNetwork.sendBufferedImageToNeuralNetwork(bufferedImage, neuralNetwork);
				neuralNetworkPanel.repaint();
			}
		});

		btnClearImage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawingPanel.clear();

			}

		});

		btnStoreImage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						drawingPanel.saveImage(textFieldForImageDirectory.getText() + "\\" + directoryForImage,
								filename);
						drawingPanel.clear();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnStoreImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					drawingPanel.saveImage(textFieldForImageDirectory.getText() + "\\" + directoryForImage, filename);
					drawingPanel.clear();

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});

		btnSaveImageToFitNetwork.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LinkToNeuralNetwork linkToNeuralNetwork = new LinkToNeuralNetwork();
				linkToNeuralNetwork.fileConverter(textFieldForImageDirectory.getText() + "\\" + directoryForImage,
						textFieldForImageDirectory.getText() + "\\TrainingData\\");

			}

		});

		frame.setVisible(true);
	}
}
