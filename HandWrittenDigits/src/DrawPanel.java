import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class DrawPanel extends javax.swing.JPanel {

	Point pointStart = null;
	Point previousPointMouse = null;
	Point endingPointMouse = null;

	private BufferedImage paintImage;
	private Image image;
	private Graphics2D g2;
	private Network neuralNetwork;
	private NeuralNetworkPanel neuralNetworkPanel;
	private boolean showInNetwork = false;

	public DrawPanel(Network neuralNetwork, NeuralNetworkPanel neuralNetworkPanel) {
		this.neuralNetwork = neuralNetwork;
		this.neuralNetworkPanel = neuralNetworkPanel;
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				previousPointMouse = e.getPoint();
			}

			public void mouseReleased(MouseEvent e) {

				pointStart = null;
			}

		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
			}

			public void mouseDragged(MouseEvent e) {
				endingPointMouse = e.getPoint();
				if (previousPointMouse.x != endingPointMouse.x && previousPointMouse.y != endingPointMouse.y) {
					g2.setStroke(new BasicStroke(10));
					g2.drawLine(previousPointMouse.x, previousPointMouse.y, endingPointMouse.x, endingPointMouse.y);
					repaint();
					previousPointMouse = endingPointMouse;
					if (showInNetwork == true) {
						sendToNetwork();
					}
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {

		if (image == null) {
			image = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(image, 0, 0, null);
	}

	public void clear() {
		g2.setPaint(Color.WHITE);

		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setPaint(Color.black);
		repaint();
	}

	public void saveImage(String directory, String filename) throws IOException {
		paintImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

		paintImage = (BufferedImage) image;
		RenderedImage rendImage = paintImage;
		String lastCharOfFileName = readImageCounter(directory);
		ImageIO.write(paintImage, "png", new File(directory + filename + lastCharOfFileName + ".png"));
	}

	public BufferedImage getBufferedImage() {
		paintImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);
		paintImage = (BufferedImage) image;
		return paintImage;
	}

	private String readImageCounter(String textField) {
		File folder = new File(textField);
		File[] listOfFiles = folder.listFiles();
		String lastCharOfFileName = "0";
		String charOfNextFile;
		int positionInTextField;
		int nextNumber;
		int highestNumber = 0;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String filename = listOfFiles[i].getName();
				positionInTextField = listOfFiles[i].getName().lastIndexOf(".");

				for (int j = positionInTextField - 1; j >= 1; j--) {

					if (Character.isLetter(filename.charAt(j))) {
						j = -1;
					} else {
						lastCharOfFileName = filename.substring(j, positionInTextField);
						int tempNumber = Integer.parseInt(lastCharOfFileName);
						if (tempNumber > highestNumber) {
							highestNumber = tempNumber;
						}
					}
				}
			}
		}

		nextNumber = highestNumber;
		nextNumber++;
		charOfNextFile = Integer.toString(nextNumber);
		return charOfNextFile;
	}

	public boolean isShowInNetwork() {
		return showInNetwork;
	}

	public void setShowInNetwork(boolean newStroke) {
		this.showInNetwork = newStroke;
	}

	private void sendToNetwork() {
		ConvertPicture convertPicture = new ConvertPicture();
		SendDataToNeuralNetwork sendDataToNetwork = new SendDataToNeuralNetwork();
		BufferedImage bufferedImage = convertPicture.convertImageSizeOnly(getBufferedImage(), 28, 28);
		sendDataToNetwork.sendBufferedImageToNeuralNetwork(bufferedImage, neuralNetwork);
		neuralNetworkPanel.repaint();
	}

	public void loadNeuralNetwork(Network neuralNetWork) {
		this.neuralNetwork = neuralNetWork;
	}
}
