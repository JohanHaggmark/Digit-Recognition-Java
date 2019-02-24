import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConvertPicture {

	
	
	public void convertImageSizeAndStore(String imageName, int width, int height, String format) {	
		
		File imageOriginal = new File("D:\\Java AI\\Bilder\\"+imageName);
		
		int pos = imageName.lastIndexOf(".");
		if(pos > 0) {
			imageName = imageName.substring(0, pos);
		}
		
		File imageResized = new File("D:\\Java AI\\Bilder\\"+imageName+"."+format);
		
		try {
			BufferedImage original = ImageIO.read(imageOriginal);
			BufferedImage resized = new BufferedImage(width, height, original.getType());
			Graphics g2 = resized.createGraphics();
			g2.drawImage(original, 0,  0, width, height, null);
			g2.dispose();
			ImageIO.write(resized, format, imageResized);
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public BufferedImage cropBufferedImage(BufferedImage image) {
		int minWidth = image.getWidth();
		int minHeight = image.getHeight();
		int maxWidth = 0;
		int maxHeight = 0;
		int[][] pixels;
		ImagePixels imagePixels = new ImagePixels();
		pixels = imagePixels.getPixelMatrixFromImage(image);
		for(int width = 0; width < image.getWidth(); width++) {
			for(int height = 0; height < image.getHeight(); height++) {
				if(pixels[width][height] == 0) {
					if(minWidth > width) {
						minWidth = width;
					}
					if(minHeight > height) {
						minHeight = height;						
					}
					if(maxWidth < width) {
						maxWidth = width;
					}
					if(maxHeight < height) {
						maxHeight = height;
					}
				}
			}
		}
		System.out.println("convert image");
		return image.getSubimage(minWidth, minHeight, maxWidth-minWidth, maxHeight-minHeight);
	}
	
	public BufferedImage convertImageSizeOnly(BufferedImage bufferedImage, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, bufferedImage.getType());
		Graphics g2 = resizedImage.createGraphics();
		g2.drawImage(cropBufferedImage(bufferedImage), 0, 0, width, height, null);
		g2.dispose();
		return resizedImage;
	}
	
	public void blackAndWhite(String sourceDirectory, String destinationDirectory, String imageName, int width, int height, String format) {
		try {
			
			File imageOriginal = new File(sourceDirectory + imageName);
			int pos = imageName.lastIndexOf(".");
			if(pos > 0) {
				imageName = imageName.substring(0, pos);
			}
			System.out.println(sourceDirectory);
			BufferedImage original = ImageIO.read(imageOriginal);
			original = cropBufferedImage(original);
			System.out.println(sourceDirectory + imageName+"black." + format);
			
			BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
			
			Graphics2D graphic = result.createGraphics();
			graphic.drawImage(original, 0, 0, width, height, Color.WHITE, null);
			graphic.dispose();
			
			File output = new File(destinationDirectory + imageName+"black." + format);
			
			ImageIO.write(result, format, output);
			
		} catch(Exception e) {
			System.out.println("didnt work... convert 106");
		}
	}
	public void convertImageToBlackAndWhite(String directory, String filename, int width, int height, String format) {
		File imageOriginal = new File(directory+filename);
		System.out.println(directory+filename);
		
		try {
			BufferedImage original = ImageIO.read(imageOriginal);
			BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
			
			Graphics2D graphic = result.createGraphics();
			graphic.drawImage(original,0,0, width, height, Color.WHITE, null);
			graphic.dispose();
			
			File output = new File("D:\\Java AI\\Bilder\\TrainingData" + filename + format);
			ImageIO.write(result,  format, output);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
