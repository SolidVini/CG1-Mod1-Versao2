//Esta é a classe do jogador e utiliza as setas direita e esquerda do teclado para jogar
package spaceinvaders;
/**
 *
 * @author Vinicius
 */
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Jogador extends Sprite implements Commons {

    //Estas são as coordenadas iniciais do jogador
    private final int START_Y = 280;
    private final int START_X = 270;

    private final String playerImg = "src/images/player.png";
    private int width;

    public Jogador() {

        initJogador();
    }

    private void initJogador() {
        
        ImageIcon ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
    }

    public void act() {
        
        x += dx;
        
        if (x <= 2) {
            x = 2;
        }
        
        if (x >= BORDA_LARGURA - 2 * width) {
            x = BORDA_LARGURA - 2 * width;
        }
    }

    //Se uma das setas for precionada, a variavel dx(Que é usada para movimentação) é atribuida 2 ou -2..
    //Assim, quando o método act() for chamado, o jogador vai se mover
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        
            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 2;
        }
    }

    //Se a tecla seta parar de ser precionada, o jogador para de se mover.
    public void keyReleased(KeyEvent e) {
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
        
            dx = 0;
        }
    }
}
