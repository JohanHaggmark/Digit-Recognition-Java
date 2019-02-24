import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class LoadSaveNetwork {
	FileOutputStream fos;
	ObjectOutputStream oos;
	FileInputStream fis;
	ObjectInputStream ois;

	public void saveNetwork(Network neuralNetwork) {
		try {

			fos = new FileOutputStream("digiRecognition.ser");
			oos = new ObjectOutputStream(fos);
			oos.flush();
			oos.writeObject(neuralNetwork);

			oos.close();
			fos.close();
			System.out.println("done");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("fail save network");
		}
	}

	public Network loadNetwork(Network neuralNetwork) {

		try {
			fis = new FileInputStream("digiRecognition.ser");
			ois = new ObjectInputStream(fis);
			neuralNetwork = (Network) ois.readObject();

			System.out.println("read file ok");
			ois.close();
			fis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("fail read network");
		}
		return neuralNetwork;
	}	
}
