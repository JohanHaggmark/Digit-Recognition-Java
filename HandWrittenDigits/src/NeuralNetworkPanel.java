import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class NeuralNetworkPanel extends javax.swing.JPanel {

	private Network neuralNetwork;
	private Graphics2D g2;
	final int hiddenLayerNodesScreenDistance;
	final int distanceBetweenLayers;
	final int nodeSize;
	final int lengthOfHiddenLayer;
	final int outputNodesScreenDistance;
	final int numberOfHiddenLayers;

	public NeuralNetworkPanel(Network neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
		// repaint();
		hiddenLayerNodesScreenDistance = 800 / neuralNetwork.getHiddenLayers().get(0).getNodes().length;
		distanceBetweenLayers = (300 - 50) / (2 + neuralNetwork.getHiddenLayers().size());

		nodeSize = 800 / (neuralNetwork.getHiddenLayers().get(0).getNodes().length * 10);
		lengthOfHiddenLayer = neuralNetwork.getHiddenLayers().get(0).getNodes().length;

		outputNodesScreenDistance = (800 / neuralNetwork.getOutputLayer().getNodes().length) / 2;
		numberOfHiddenLayers = neuralNetwork.getHiddenLayers().size();
	}

	@Override
	public void paintComponent(Graphics g) {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), neuralNetwork.getInputLayer().getInputData().length + 50);

		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 25));
		g.drawString("Certainty", 170, 180);

		for (int i = 0; i < neuralNetwork.getOutputLayer().getNodes().length; i++) {
			if (neuralNetwork.getOutputLayer().getNodes()[i] > 0.5F) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawString(String.valueOf(i), 320, i * outputNodesScreenDistance + 187 + g.getFont().getSize());
		}

		for (int i = 0; i < neuralNetwork.getInputLayer().getInputData().length; i++) {
			if (neuralNetwork.getInputLayer().getInputData()[i] != 0) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.black);
			}
			g.fillRect(0, i, 15, 1);
		}
		// biasrect inputlayer
		g.setColor(Color.green);
		g.fillRect(0, neuralNetwork.getInputLayer().getInputData().length + 10, 15, 10);

		for (int i = 0; i < neuralNetwork.getHiddenLayers().size(); i++) {
			for (int j = 0; j < neuralNetwork.getHiddenLayers().get(i).getNodes().length; j++) {
				if (neuralNetwork.getHiddenLayers().get(i).getNodes()[j] > 0.1F
						|| neuralNetwork.getHiddenLayers().get(i).getNodes()[j] < -0.1F) {
					g.setColor(Color.green);
				} else {
					g.setColor(Color.black);
				}
				g.fillRect((50 + (i * distanceBetweenLayers)), 10 + j * hiddenLayerNodesScreenDistance, nodeSize,
						nodeSize);
			}
		}
		// bias hiddenLayers
		g.setColor(Color.green);
		g.fillRect(50, lengthOfHiddenLayer * hiddenLayerNodesScreenDistance + 5, nodeSize + 5, nodeSize + 5);
		g.fillRect(50 + distanceBetweenLayers, lengthOfHiddenLayer * hiddenLayerNodesScreenDistance + 5, nodeSize + 5,
				nodeSize + 5);

		int mostCeartainedOutput = 0;
		float highest = 0;
		for (int i = 0; i < neuralNetwork.getOutputLayer().getNodes().length; i++) {
			if (neuralNetwork.getOutputLayer().getNodes()[i] > 0.5F
					|| neuralNetwork.getOutputLayer().getNodes()[i] < -0.5F) {
				g.setColor(Color.green);
			} else {
				g.setColor(Color.black);
			}

			if (highest < neuralNetwork.getOutputLayer().getNodes()[i]) {
				highest = neuralNetwork.getOutputLayer().getNodes()[i];
				mostCeartainedOutput = i;
			}

			g.fillRect(50 + numberOfHiddenLayers * distanceBetweenLayers, i * outputNodesScreenDistance + 200,
					nodeSize * 2, nodeSize * 2);
			String valueString = String.valueOf(neuralNetwork.getOutputLayer().getNodes()[i]);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString(valueString, 200, i * outputNodesScreenDistance + 190 + g.getFont().getSize());

		}
		g.setColor(Color.green);
		if (highest != 0) {
			g.fillRect(50 + numberOfHiddenLayers * distanceBetweenLayers,
					mostCeartainedOutput * outputNodesScreenDistance + 200, nodeSize * 2, nodeSize * 2);
		}
		for (int i = 0; i < neuralNetwork.getInputLayer().getWeights().length; i++) {
			for (int j = 0; j < neuralNetwork.getInputLayer().getWeights()[0].length; j++) {

				float colorValue = neuralNetwork.getInputLayer().getWeights()[i][j] / 5;
				if ((colorValue > 0.2F || colorValue < -0.2F)
						&& neuralNetwork.getInputLayer().getInputData()[i] > 0.1F) {
					if (colorValue > 1) {
						colorValue = 1;
					}
					if (colorValue < 0) {
						colorValue = -1;
					}
					float negativeColorValue = colorValue;
					if (negativeColorValue < 0) {
						negativeColorValue = Math.abs(negativeColorValue);
						colorValue = 1;
					} else {
						negativeColorValue = 1;
					}

					Color myColor = new Color(negativeColorValue, 0, colorValue);
					g.setColor(myColor);
					g.drawLine(15 + nodeSize, i, distanceBetweenLayers - 15, hiddenLayerNodesScreenDistance * j + 10);
				}
			}
		}

		for (int i = 0; i < neuralNetwork.getHiddenLayers().size(); i++) {
			if (i != neuralNetwork.getHiddenLayers().size() - 1) {
				for (int j = 0; j < neuralNetwork.getHiddenLayers().get(i).getWeights().length; j++) {
					for (int k = 0; k < neuralNetwork.getHiddenLayers().get(i).getWeights()[0].length; k++) {

						float colorValue = neuralNetwork.getHiddenLayers().get(i).getWeights()[j][k] / 5;
						if ((colorValue > 0.2F || colorValue < -0.2F)
								&& neuralNetwork.getHiddenLayers().get(i).getNodes()[j] > 0.3F) {
							if (colorValue > 1) {
								colorValue = 1;
							}
							if (colorValue < 0) {
								colorValue = -1;
							}
							float negativeColorValue = colorValue;
							if (negativeColorValue < 0) {
								negativeColorValue = Math.abs(negativeColorValue);
								colorValue = 1;
							} else {
								negativeColorValue = 1;
							}

							Color myColor = new Color(negativeColorValue, 0, colorValue);
							g.setColor(myColor);
							g.drawLine(50 + nodeSize + i * distanceBetweenLayers,
									hiddenLayerNodesScreenDistance * j + 10, 50 + (i + 1) * distanceBetweenLayers,
									k * hiddenLayerNodesScreenDistance + 10);
						}
					}
				}
			} else {
				for (int j = 0; j < neuralNetwork.getHiddenLayers().get(i).getWeights().length; j++) {
					for (int k = 0; k < neuralNetwork.getHiddenLayers().get(i).getWeights()[0].length; k++) {
						float colorValue = neuralNetwork.getHiddenLayers().get(i).getWeights()[j][k] / 5;
						if ((colorValue > 0.2F || colorValue < -0.2F)
								&& neuralNetwork.getHiddenLayers().get(i).getNodes()[j] > 0.3F) {
							if (colorValue > 1) {
								colorValue = 1;
							}
							if (colorValue < 0) {
								colorValue = -1;
							}
							float negativeColorValue = colorValue;
							if (negativeColorValue < 0) {
								negativeColorValue = Math.abs(negativeColorValue);
								colorValue = 1;
							} else {
								negativeColorValue = 1;
							}

							Color myColor = new Color(negativeColorValue, 0, colorValue);
							g.setColor(myColor);
							g.drawLine(50 + i * distanceBetweenLayers, hiddenLayerNodesScreenDistance * j + 10,
									50 + numberOfHiddenLayers * distanceBetweenLayers,
									200 + k * outputNodesScreenDistance);
						}
					}
				}
			}
		}

		// Bias Lines
		for (int i = 0; i < neuralNetwork.getInputLayer().getBiasWeights().length; i++) {
			float colorValue = neuralNetwork.getInputLayer().getBiasWeights()[i] / 5;
			System.out.println(colorValue);
			if ((colorValue > 0.2F || colorValue < -0.2F)) {
				if (colorValue > 1) {
					colorValue = 1;
				}
				if (colorValue < 0) {
					colorValue = -1;
				}
				float negativeColorValue = colorValue;
				if (negativeColorValue < 0) {
					negativeColorValue = Math.abs(negativeColorValue);
					colorValue = 1;
				} else {
					negativeColorValue = 1;
				}

				Color myColor = new Color(negativeColorValue, 0, colorValue);
				g.setColor(myColor);
				g.drawLine(15, neuralNetwork.getInputLayer().getInputData().length + 10, 50,
						i * hiddenLayerNodesScreenDistance + 10);
			}

		}

		for (int i = 0; i < neuralNetwork.getHiddenLayers().get(0).getNodes().length; i++) {
			float colorValue = neuralNetwork.getHiddenLayers().get(0).getBiasWeights()[i] / 5;
			if ((colorValue > 0.2F || colorValue < -0.2F)) {
				if (colorValue > 1) {
					colorValue = 1;
				}
				if (colorValue < 0) {
					colorValue = -1;
				}
				float negativeColorValue = colorValue;
				if (negativeColorValue < 0) {
					negativeColorValue = Math.abs(negativeColorValue);
					colorValue = 1;
				} else {
					negativeColorValue = 1;
				}

				Color myColor = new Color(negativeColorValue, 0, colorValue);
				g.setColor(myColor);
				g.drawLine(50 + nodeSize, lengthOfHiddenLayer * hiddenLayerNodesScreenDistance,
						50 + distanceBetweenLayers, i * hiddenLayerNodesScreenDistance + 10);
			}
		}
		for (int i = 0; i < neuralNetwork.getOutputLayer().getNodes().length; i++) {
			float colorValue = neuralNetwork.getHiddenLayers().get(neuralNetwork.getHiddenLayers().size() - 1)
					.getBiasWeights()[i] / 5;
			if ((colorValue > 0.2F || colorValue < -0.2F)) {
				if (colorValue > 1) {
					colorValue = 1;
				}
				if (colorValue < 0) {
					colorValue = -1;
				}
				float negativeColorValue = colorValue;
				if (negativeColorValue < 0) {
					negativeColorValue = Math.abs(negativeColorValue);
					colorValue = 1;
				} else {
					negativeColorValue = 1;
				}

				Color myColor = new Color(negativeColorValue, 0, colorValue);
				g.setColor(myColor);
				g.drawLine(50 + nodeSize + distanceBetweenLayers,
						lengthOfHiddenLayer * hiddenLayerNodesScreenDistance + 10, 50 + distanceBetweenLayers * 2,
						200 + i * outputNodesScreenDistance);
			}
		}

	}

	public void loadNeuralNetwork(Network neuralNetWork) {
		this.neuralNetwork = neuralNetWork;
	}
}
