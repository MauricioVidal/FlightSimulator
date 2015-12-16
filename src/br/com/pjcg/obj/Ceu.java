/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.obj;

import br.com.pjcg.util.Matrizes;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Ceu {

    private List<Ponto> pontos = new ArrayList<Ponto>();
    private List<Ponto> pontosTextura = new ArrayList<Ponto>();
    private List<Face> faces = new ArrayList<>();

    public Ceu(GL2 gl) {
        try {
            carregaTextura(gl);
            Scanner scan = new Scanner(new FileReader("resources/ceu.obj"));
            String tipo = scan.next();
            while (tipo.equals("v")) {
                float x1 = Float.parseFloat(scan.next());
                float y1 = Float.parseFloat(scan.next());
                float z1 = Float.parseFloat(scan.next());
                pontos.add(new Ponto(x1, y1, z1));
                tipo = scan.next();
            }
            while (scan.hasNext() && tipo.equals("vt")) {
                float x1 = Float.parseFloat(scan.next());
                float z1 = Float.parseFloat(scan.next());
                pontosTextura.add(new Ponto(x1, 0, z1));
                tipo = scan.next();
            }
            while (scan.hasNext() && tipo.equals("f")) {
                tipo = scan.next();
                Face f = new Face();
                while (scan.hasNext() && !tipo.equals("f")) {
                    String ids[] = tipo.split("/");
                    int id = Integer.parseInt(ids[0]) - 1;
                    int idT = Integer.parseInt(ids[1]) - 1;
                    f.addPonto(pontos.get(id));
                    f.addPontoTextura(pontosTextura.get(idT));
                    if (scan.hasNext()) {
                        tipo = scan.next();
                    }
                }
                faces.add(f);
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Aviao.class.getName()).log(Level.SEVERE, null, ex);
        }
        posicionar();
    }
    
    private  int texture;
    private void carregaTextura(GL2 gl){
        try {
            File im = new File("./resources/ceu.png");
            Texture t = TextureIO.newTexture(im, true);
            texture = t.getTextureObject(gl);
        } catch (IOException ex) {
            Logger.getLogger(Terreno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GLException ex) {
            Logger.getLogger(Terreno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void posicionar() {
        Matrizes.translacao(0, -2, 0, pontos);
        Matrizes.escala(10, 5f, 10, pontos);
    }

    public void desenhar(GL2 gl) {
        gl.glPushMatrix();
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_BACK, GL2.GL_AMBIENT);
        gl.glColor3f(1, 1, 1);
        for (Face f : faces) {
            if (f.getPontos().size() == 4) {
                gl.glBegin(GL2.GL_QUADS);
            } else if (f.getPontos().size() > 4) {
                gl.glBegin(GL2.GL_POLYGON);
            } else {
                gl.glBegin(GL2.GL_TRIANGLE_FAN);
            }
            int i = 0;
            for (Ponto p : f.getPontos()) {
                gl.glTexCoord2f(f.getPontosTextura().get(i).x, f.getPontosTextura().get(i).z);
                gl.glVertex3f(p.x, p.y, p.z);
                i++;
            }
            gl.glEnd();
        }
        gl.glDisable(GL2.GL_COLOR_MATERIAL);
        gl.glPopMatrix();
    }

}
