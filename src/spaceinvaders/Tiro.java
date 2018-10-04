//A classe Tiro é ativada quando a tecla Espaço é precionada
package spaceinvaders;

/**
 *
 * @author Vinicius
 */
import javax.swing.ImageIcon;

public class Tiro extends Sprite {

    private final String tiroImg = "src/images/tiro.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;

    public Tiro() {
    }

    public Tiro(int x, int y) {

        initTiro(x, y);
    }

    private void initTiro(int x, int y) {

        ImageIcon ii = new ImageIcon(tiroImg);
        setImage(ii.getImage());
        
        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }
}
