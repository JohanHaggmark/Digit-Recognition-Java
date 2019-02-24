import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyboardController implements KeyListener {
	
	public KeyboardController(DrawPanel drawPanel) {
		drawPanel.addKeyListener(this);
		drawPanel.requestFocusInWindow();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Action!!");
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Enter was pressed, 15 keyboard");
			
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
