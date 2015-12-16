/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.obj;

import br.com.pjcg.util.Matrizes;
import com.jogamp.opengl.GL2;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Aviao {

    private List<Ponto> pontos = new ArrayList<Ponto>();
    private List<Ponto> pIni = new ArrayList<Ponto>();
    private ArrayList<Face> faces = new ArrayList<>();
    private Ponto centro = new Ponto(0, 0, 0);
    private float dColisionMax;

    public Aviao(GL2 gl) {
        try {
            Scanner scan = new Scanner(new FileReader("resources/14bis.obj"));
            String tipo = scan.next();
            while (tipo.equals("v")) {
                float x1 = Float.parseFloat(scan.next());
                float y1 = Float.parseFloat(scan.next());
                float z1 = Float.parseFloat(scan.next());
                pontos.add(new Ponto(x1, y1, z1));
                pIni.add(new Ponto(x1, y1, z1));
                tipo = scan.next();
            }
            while (scan.hasNext() && tipo.equals("f")) {
                tipo = scan.next();
                Face f = new Face();
                while (scan.hasNext() && !tipo.equals("f")) {
                    int id = Integer.parseInt(tipo) - 1;
                    f.addPonto(pontos.get(id));
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
        System.out.println(dColisionMax);
    }

    private void posicionar() {
        Matrizes.escala(0.1f, 0.1f, 0.1f, pontos);
        Matrizes.escala(0.1f, 0.1f, 0.1f, pIni);
        for (Ponto p : pontos) {
            float distancia = Ponto.distancia2D_XZ(centro, p);
            if (dColisionMax < distancia) {
                dColisionMax = distancia;
            }
        }
    }

    public void resetarPosicao() {
        int i = 0;
        for (Ponto p : pontos) {
            p.x = pIni.get(i).x;
            p.y = pIni.get(i).y;
            p.z = pIni.get(i).z;
            i++;
        }
        centro.x = 0;
        centro.y = 0;
        centro.z = 0;
    }

    public void movimenta(float velocidade, float aXZ, float aYZ) {
        float andaX = (float) (velocidade * Math.sin(Math.toRadians(aXZ)));
        float andaZ = (float) (velocidade * Math.cos(Math.toRadians(aXZ)));
        float andaY = (float) (velocidade * Math.sin(Math.toRadians(aYZ)));
        Matrizes.translacao(andaX, andaY, andaZ, centro);
    }


    public void desenhar(GL2 gl, float aXZ, float aGiro, float aSubida) {
        gl.glPushMatrix();
        gl.glTranslatef(centro.x, centro.y, centro.z);
        gl.glRotatef(aXZ, 0, 1, 0);
        gl.glRotatef(aGiro, 0, 0, 1);
        gl.glRotatef(aSubida, 1, 0, 0);
        gl.glPushMatrix();
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT);
        gl.glColor3f(1, 1, 1);
        gl.glRotatef(-90, 0, 1, 0);
        int c = 1;
        for (Face f : faces) {
            if(c == 1){
                gl.glColor3f(0, 0, 0);
            }else{
                gl.glColor3f(0.5f, 0.5f, 0.5f);
            }
            c =-c;
            if (f.getPontos().size() == 4) {
                gl.glBegin(GL2.GL_QUADS);
            } else if (f.getPontos().size() > 4) {
                gl.glBegin(GL2.GL_POLYGON);
            } else {
                gl.glBegin(GL2.GL_TRIANGLE_FAN);
            }
            int i = 0;
            for (Ponto p : f.getPontos()) {
                gl.glVertex3f(p.x, p.y, p.z);
            }
            gl.glEnd();
        }
        gl.glDisable(GL2.GL_COLOR_MATERIAL);
        gl.glPopMatrix();
        gl.glPopMatrix();
    }

    public float getdColisionMax() {
        return dColisionMax;
    }

    public Ponto getCentro() {
        return centro;
    }

}
