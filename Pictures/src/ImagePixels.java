import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagePixels {

	int[] myPixels;

	public int[] getPixelsFromImage(String imageName) {
		File imageOriginal = new File(imageName);

		System.out.println(imageOriginal);
		try {
			BufferedImage original = ImageIO.read(imageOriginal);
			WritableRaster wr = original.getRaster();
			myPixels = new int[original.getWidth() * original.getHeight()];
			
			int count = 0;
			
			for (int x = 0; x < original.getWidth(); x++) {
				for (int y = 0; y < original.getHeight(); y++) {

					myPixels[count] = wr.getSample(x, y, 0);
					count++;
				}
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return myPixels;
	}
	
	public int[][] getPixelMatrixFromImage(BufferedImage image){
		int[][] pixels = new int[image.getWidth()][image.getHeight()];
		
		WritableRaster wr = image.getRaster();
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = wr.getSample(x, y, 0);
			}
		}
		
		return pixels;
	}
	
	public float[] getPixelsFromBufferedImage(BufferedImage bufferedImage) {
		WritableRaster wr = bufferedImage.getRaster();
		float[] myPixels = new float[bufferedImage.getWidth()* bufferedImage.getHeight()];
		
		int count = 0;
		
		for(int x = 0; x < bufferedImage.getWidth(); x++) {
			for(int y = 0; y < bufferedImage.getHeight(); y++) {
				if(wr.getSample(x, y, 0) == 0) {
					myPixels[count] = 1;
				} else {
					myPixels[count] = 0;
				}
				count++;
			}
		}
		return myPixels;
	}
}
