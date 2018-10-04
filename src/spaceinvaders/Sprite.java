//Essa é a classe básica de Sprites. Outras classes de sprites herdam esta classe,
//pois apresenta funcionalidades comuns a outras sprites
package spaceinvaders;

/**
 *
 * @author Vinicius
 */
import java.awt.Image;

public class Sprite {

    private boolean visivel;
    private Image imagem;
    protected int x;
    protected int y;
    protected boolean morto;
    protected int dx;

    public Sprite() {
    
        visivel = true;
    }

    public void morre() {
    
        visivel = false;
    }

    public boolean isVisible() {
    
        return visivel;
    }

    protected void setVisible(boolean visible) {
    
        this.visivel = visible;
    }

    public void setImage(Image image) {
    
        this.imagem = image;
    }

    public Image getImage() {
    
        return imagem;
    }

    public void setX(int x) {
    
        this.x = x;
    }

    public void setY(int y) {
    
        this.y = y;
    }

    public int getY() {
    
        return y;
    }

    public int getX() {
    
        return x;
    }

    public void setDying(boolean morto) {
    
        this.morto = morto;
    }

    public boolean isDying() {
    
        return this.morto;
    }
}
