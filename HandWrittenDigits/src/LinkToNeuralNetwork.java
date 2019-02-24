import java.io.File;

public class LinkToNeuralNetwork {

	
	public void fileConverter(String fileDirectory, String destinationDirectory) {
		ConvertPicture convertPicture = new ConvertPicture();
		//ReadPictures readPictures = new ReadPictures();
		//ImagePixels pixels = new ImagePixels();
		
		File folder = new File(fileDirectory);
		
		for(File fileEntry : folder.listFiles()) {
			if(fileEntry.isFile()) {
			//	pixels.getPixelsFromImage(fileEntry.getName());
				String directory = fileDirectory;
				convertPicture.blackAndWhite(directory, destinationDirectory, fileEntry.getName(), 28, 28, "png");
			}
		}
		
	}
	public void convertImageToFitNetwork(String fileDirectory, String filename) {
		ConvertPicture convertPicture = new ConvertPicture();
		convertPicture.convertImageToBlackAndWhite(fileDirectory, filename, 28, 28, "png");
	}
	

}
