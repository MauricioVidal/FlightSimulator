/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.janela;

import br.com.pjcg.listener.GLWindow;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Janela extends JFrame {

    public static FPSAnimator animator;
    public static AudioClip clip;
    
    public Janela(int width, int height, GLCanvas canvas) throws IOException {
        animator = new FPSAnimator(canvas, 240);
        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setMinimumSize(getSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);
        getContentPane().add(panel);
        canvas.addGLEventListener(new GLWindow(panel, canvas));
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e); //To change body of generated methods, choose Tools | Templates.
                animator.stop();
                System.out.println("Animação parou!");
            }

        });
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        JDInfo info = new JDInfo(this, true);
        info.setVisible(true);
        animator.start();
        clip = Applet.newAudioClip(new File("./resources/Cabin.wav").toURL());
        clip.loop();
    }

}
