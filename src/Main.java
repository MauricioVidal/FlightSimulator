
import br.com.pjcg.janela.Janela;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mauricio R. Vidal
 */
public class Main {
    public static void main(String[] args) {
        try {
            final GLProfile profile = GLProfile.get(GLProfile.GL2);
            GLCapabilities capabilities = new GLCapabilities(profile);
            capabilities.setDoubleBuffered(true);
            capabilities.setHardwareAccelerated(true);
            GLCanvas canvas = new GLCanvas(capabilities);
            Janela j = new Janela(500, 500,canvas);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}
