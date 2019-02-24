import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

public class SendDataToNeuralNetwork {
	Network neuralNetwork;
	private int batchSize = 0;
	private float[][] myTrainingDataToNetwork;
	private float[][] myFacit;

	public void sendPixelsAsInputData(Network neuralNetwork, String directory, int batchSize) {
		this.batchSize = batchSize;
		this.neuralNetwork = neuralNetwork;
		ImagePixels pixels = new ImagePixels();
		File folder = new File(directory);
		int numberOfImages = 0;
		int numberOfPixelsInImage = 0;

		numberOfImages = folder.listFiles().length;

		int[] intPixels = pixels.getPixelsFromImage(directory + folder.listFiles()[0].getName());
		numberOfPixelsInImage = intPixels.length;

		float[][] facitData = new float[numberOfImages][neuralNetwork.getOutputLayer().getNodes().length];
		float[][] trainingDataToNetwork = new float[numberOfImages][numberOfPixelsInImage];
		for (int i = 0; i < folder.listFiles().length; i++) {
			intPixels = pixels.getPixelsFromImage(directory + folder.listFiles()[i].getName());
			for (int j = 0; j < intPixels.length; j++) {
				if (intPixels[j] == 1) {
					intPixels[j] = 0;
				} else {
					intPixels[j] = 1;
				}
				trainingDataToNetwork[i][j] = intPixels[j];
			}
			facitData = setFacitForThisImage(folder.listFiles()[i].getName(), i, facitData);
		}
		sendTrainingData(trainingDataToNetwork, facitData);
	}

	public void setPixelsAsInputData(Network neuralNetwork, String directory, int batchSize) {
		this.batchSize = batchSize;
		this.neuralNetwork = neuralNetwork;
		ImagePixels pixels = new ImagePixels();
		File folder = new File(directory);
		int numberOfImages = 0;
		int numberOfPixelsInImage = 0;

		numberOfImages = folder.listFiles().length;

		int[] intPixels = pixels.getPixelsFromImage(directory + folder.listFiles()[0].getName());
		numberOfPixelsInImage = intPixels.length;

		float[][] facitData = new float[numberOfImages][neuralNetwork.getOutputLayer().getNodes().length];
		float[][] trainingDataToNetwork = new float[numberOfImages][numberOfPixelsInImage];
		for (int i = 0; i < folder.listFiles().length; i++) {
			intPixels = pixels.getPixelsFromImage(directory + folder.listFiles()[i].getName());
			for (int j = 0; j < intPixels.length; j++) {
				if (intPixels[j] == 1) {
					intPixels[j] = 0;
				} else {
					intPixels[j] = 1;
				}
				trainingDataToNetwork[i][j] = intPixels[j];
			}
			facitData = setFacitForThisImage(folder.listFiles()[i].getName(), i, facitData);
		}
		myTrainingDataToNetwork = trainingDataToNetwork;
		myFacit = facitData;
	}

	public void sendTheTrainingDataToNetwork(int trainingRow) {
		float[][] facitMiniBatch = new float[batchSize][myFacit[0].length];
		float[][] inputMiniBatch = new float[batchSize][myTrainingDataToNetwork[0].length];

		Random random = new Random();
		for (int k = 0; k < trainingRow; k++) {
			for (int i = 0; i < batchSize; i++) {
				int temp = random.nextInt(myTrainingDataToNetwork.length);

				for (int j = 0; j < myFacit[0].length; j++) {
					facitMiniBatch[i][j] = myFacit[temp][j];
				}
				for (int j = 0; j < myTrainingDataToNetwork[0].length; j++) {
					inputMiniBatch[i][j] = myTrainingDataToNetwork[temp][j];
				}
			}
			neuralNetwork.setInputTrainingData(inputMiniBatch);
			neuralNetwork.setNodesFacit(facitMiniBatch);
			trainNetwork();
		}
	}

	private float[][] setFacitForThisImage(String filename, int fileNumber, float[][] facitData) {
		float[][] facit = facitData;
		String facitLetter = filename.substring(0, 1);
		int facitNode = Integer.valueOf(facitLetter);
		System.out.println(facitNode + "senddata 47");
		for (int i = 0; i < facit[0].length; i++) {
			facit[fileNumber][i] = 0;
		}
		
//!!!!Trying to avoid overtraining by set facit ratio to 0.85F 
		facit[fileNumber][facitNode] = 0.85F;
		return facit;
	}

	private void sendTrainingData(float[][] trainingNodes, float[][] facit) {
		float[][] facitMiniBatch = new float[10][facit[0].length];
		float[][] inputMiniBatch = new float[10][trainingNodes[0].length];

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int temp = random.nextInt(trainingNodes.length);

			for (int j = 0; j < facit[0].length; j++) {
				facitMiniBatch[i][j] = facit[temp][j];
			}
			for (int j = 0; j < trainingNodes[0].length; j++) {
				inputMiniBatch[i][j] = trainingNodes[temp][j];
			}
		}
		neuralNetwork.setInputTrainingData(inputMiniBatch);
		neuralNetwork.setNodesFacit(facitMiniBatch);
		trainNetwork();

	}

	private void trainNetwork() {
		for (int i = 0; i < 1; i++) {
			neuralNetwork.forwardingTrainingNodesRelu();
			neuralNetwork.backPropagationRelu();
		}
	}

	public void sendBufferedImageToNeuralNetwork(BufferedImage bufferedImage, Network neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
		ImagePixels pixels = new ImagePixels();
		float[] inputData = pixels.getPixelsFromBufferedImage(bufferedImage);
		sendInputDataToNeuralNetwork(neuralNetwork, inputData);
		forwardNetwork(neuralNetwork);
	}

	public void forwardNetwork(Network neuralNetwork) {
		neuralNetwork.forwardRelu();
	}

	public void sendInputDataToNeuralNetwork(Network neuralNetwork, float[] inputData) {
		neuralNetwork.getInputLayer().setInputData(inputData);
		System.out.println("Data is sent to network!, SendDataTONeuralnetwork 71");
	}

}
