import java.io.File;

public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final File folder = new File("D:\\Java AI\\Bilder");
		
		ReadPictures readPictures = new ReadPictures();
		ConvertPicture convertPictures = new ConvertPicture();
		ImagePixels pixels = new ImagePixels();
		
		for(final File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()){
				
			} else {
				//convertPictures.convertImage(fileEntry.getName(), 100, 100, "png");
			//	convertPictures.blackAndWhite(fileEntry.getName(), 100, 100, "png");
			}
		}
		readPictures.listFilesForFolder(folder);	
		
		for(final File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()) {
				
			}else {
				pixels.getPixelsFromImage(fileEntry.getName());
			}
		}
	}
	
}
