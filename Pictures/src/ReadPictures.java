import java.io.File;

public class ReadPictures {

	public void listFilesForFolder(final File folder) {
		for(final File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()){
				listFilesForFolder(fileEntry);
			} else {
				System.out.println(fileEntry.getName());
			}
		}
	}
	public String readFile(final File file) {
		if(file.isDirectory()) {
			
		} else {
			System.out.println(file.getName());
			return file.getName();
		}
		return null;
	}
}
