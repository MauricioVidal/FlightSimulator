/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.listener;

import br.com.pjcg.janela.JDGameOver;
import br.com.pjcg.janela.Janela;
import static br.com.pjcg.janela.Janela.clip;
import br.com.pjcg.obj.Aviao;
import br.com.pjcg.obj.Observador;
import br.com.pjcg.obj.Ponto;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import br.com.pjcg.obj.Terreno;
import com.jogamp.opengl.util.gl2.GLUT;
import java.applet.Applet;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio R. Vidal
 */
public class GLWindow implements GLEventListener {

    private GLU glu = new GLU();
    private GLUT glut = new GLUT();
    private Aviao aviao;
    private Observador obs = new Observador(45, 0.1f, 200, 1);
    private Terreno terreno;
    float[] lightPos = {0, 0, 60, 1};
    float[] noAmbient = {1, 1, 1, 1};
    float[] diffuse = {1, 1, 1, 1};
    private float ANGULOXZ;
    private float ANGULOYZ;
    private float ANGULOGIRO;
    private float ANGULOSUBIDA;
    private boolean teclaPressionada;
    private float VELOCIDADE = 0.1f;

    private void ajustarObservador() {
        float obsx = obs.getCentro().x;
        float obsy = obs.getCentro().y;
        float obsz = obs.getCentro().z;

        float ax = aviao.getCentro().x;
        float ay = aviao.getCentro().y;
        float az = aviao.getCentro().z;

        float distancia = -obs.getdObjeto();

        obsx += distancia * Math.sin(Math.toRadians(ANGULOXZ));
        obsz += distancia * Math.cos(Math.toRadians(ANGULOXZ));

        glu.gluLookAt(obsx, obsy, obsz,
                ax, ay, az,
                0, 1, 0);
    }

    private void foraDoMap() {
        Ponto C = aviao.getCentro();
        if (C.x > 40 || C.x < -40 || C.z > 40 || C.z < -40) {
            aviao.resetarPosicao();
            obs.resetarPosicao();
        }
        if (C.y > 5) {
            ANGULOYZ = ANGULOYZ == 0 ? -1 : ANGULOYZ - 1;
            ANGULOSUBIDA = ANGULOSUBIDA > 0 ? ANGULOSUBIDA - 1 : -1;

        }

    }

    private void normalizarAviao() {
        if (!teclaPressionada) {
            if (ANGULOGIRO > 0) {
                ANGULOGIRO = ANGULOGIRO - 1f;
            } else if (ANGULOGIRO < 0) {
                ANGULOGIRO = ANGULOGIRO + 1f;
            } else {
                ANGULOGIRO = 0;
            }
        }
    }

    private void cena(GL2 gl) {
        terreno.desenhar(gl);
        if (!terreno.isColision(aviao)) {
            if (Janela.animator.isAnimating()) {
                foraDoMap();
                obs.movimenta(VELOCIDADE, ANGULOXZ, ANGULOYZ);
                aviao.movimenta(VELOCIDADE, ANGULOXZ, ANGULOYZ);
                aviao.desenhar(gl, ANGULOXZ, ANGULOGIRO, ANGULOSUBIDA);
                normalizarAviao();
                teclaPressionada = false;
            }
        } else {
            try {
                Janela.clip.stop();
                Janela.clip = Applet.newAudioClip(new File("./resources/explosao.wav").toURL());
                Janela.clip.play();
                new JDGameOver(null, true).setVisible(true);
                System.exit(0);
            } catch (MalformedURLException ex) {
                Logger.getLogger(GLWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void iluminacao(GL2 gl) {
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        terreno = new Terreno(6, 6, gl);
        aviao = new Aviao(gl);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(0, 0, 1, 1);// define cor para limpar a tela
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glShadeModel(GL2.GL_FLAT);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        iluminacao(gl);
        ajustarObservador();
        cena(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        obs.setCampoVisao((float) width / (float) height);
        gl.glViewport(x, y, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(obs.getAnguloVisao(), obs.getCampoVisao(), obs.getzPerto(), obs.getzLonge());
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    // Funçõe do Teclado
    public GLWindow(JPanel panel, final GLCanvas canvas) {
        //obs.setCentro(new Ponto(aviao.getCentro().x, aviao.getCentro().y + 0.7f, aviao.getCentro().z - 3));
        ActionMap actionMap = panel.getActionMap();
        InputMap inputMap = panel.getInputMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
        actionMap.put("RIGHT", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Janela.animator.isAnimating()) {
                    ANGULOXZ = (ANGULOXZ - 1) % 360;
                    ANGULOGIRO = ANGULOGIRO >= 59 ? 60 : ANGULOGIRO + 3;
                    teclaPressionada = true;
                    canvas.display();
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
        actionMap.put("LEFT", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Janela.animator.isAnimating()) {
                    ANGULOXZ = (ANGULOXZ + 1) % 360;
                    ANGULOGIRO = ANGULOGIRO <= -59 ? -60 : ANGULOGIRO - 3;
                    teclaPressionada = true;
                    canvas.display();
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
        actionMap.put("UP", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Janela.animator.isAnimating()) {
                    ANGULOYZ = ANGULOYZ >= 19 ? 20 : ANGULOYZ + 1;
                    ANGULOSUBIDA = ANGULOSUBIDA <= -19 ? -20 : ANGULOSUBIDA - 1f;
                    canvas.display();
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
        actionMap.put("DOWN", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Janela.animator.isAnimating()) {
                    ANGULOYZ = ANGULOYZ <= -19 ? -20 : ANGULOYZ - 1;
                    ANGULOSUBIDA = ANGULOSUBIDA >= 19 ? 20 : ANGULOSUBIDA + 1f;
                    canvas.display();
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "F1");
        actionMap.put("F1", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                obs.setdObjeto(1);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "F2");
        actionMap.put("F2", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                obs.setdObjeto(0.5f);
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "W");
        actionMap.put("W", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Janela.animator.isAnimating()) {
                    VELOCIDADE = VELOCIDADE > 0.13f ? 0.13f : VELOCIDADE + 0.005f;
                    canvas.display();
                }
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "S");
        actionMap.put("S", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Janela.animator.isAnimating()) {
                    VELOCIDADE = VELOCIDADE < 0.095f ? 0.095f : VELOCIDADE - 0.005f;
                    canvas.display();
                }
            }
        });

    }
}
