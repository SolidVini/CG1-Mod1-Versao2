package spaceinvaders;
/**
 *
 * @author Vinicius
 */
import javax.swing.ImageIcon;

public class Boss extends Aliem {

    private Bomba bomba;
    private final String alienImg = "src/images/boss.png";

    public Boss(int x, int y) {
        super(x, y);
    }

    

    private void initBoss(int x, int y) {

        this.x = x;
        this.y = y;

        bomba = new Bomba(x, y);
        ImageIcon ii = new ImageIcon(alienImg);
        setImage(ii.getImage());
    }
    
    //Este método é chamado para posicionar o Aliem na horizontal
    public void act(int direction) {
        
        this.x += direction;
    }

    //Este método é chamado quando o Aliem vai jogar uma bomba
    
    //Classe Bomba
    public class Bomba extends Sprite {

        private final String bombImg = "src/images/bomb.png";
        private boolean destruido;

        public Bomba(int x, int y) {

            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);
            this.x = x;
            this.y = y;
            ImageIcon ii = new ImageIcon(bombImg);
            setImage(ii.getImage());

        }

        public void setDestroyed(boolean destroyed) {
        
            this.destruido = destroyed;
        }

        public boolean isDestroyed() {
        
            return destruido;
        }
    }
}