import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyHandler implements KeyListener{
//    we init array with 128 elements becasue ascii table has 128 elements
    private boolean[] keyPressed = new boolean[128];
    @Override
    public void keyTyped(KeyEvent keyEvent) {
//        we don't need this method, but we must implement it because we implement KeyListener
    }

//    when key pressed set coreesponding keycode to key state
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keyPressed[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keyPressed[keyEvent.getKeyCode()] = false;
    }
// helper function to handle if key is pressed
    public boolean isKeyPressed(int keyCode){
        return keyPressed[keyCode];
    }
}
