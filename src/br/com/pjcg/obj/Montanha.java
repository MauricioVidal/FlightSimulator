/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.obj;

import br.com.pjcg.util.Matrizes;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Montanha {

    private HashSet<Ponto> pontos = new HashSet<>();
    private HashSet<Ponto> pontosTextura = new HashSet<>();
    private HashMap<Float, ArrayList<Ponto>> linhas = new HashMap<Float, ArrayList<Ponto>>();
    private HashMap<Float, ArrayList<Ponto>> linhasT = new HashMap<Float, ArrayList<Ponto>>();
    private ArrayList<Face> faces = new ArrayList<>();
    private ArrayList<Face> facesT = new ArrayList<>();
    private float largura, comprimento, altura;
    public Ponto pCentro, pMedioAB, pMedioBC, pMedioCD, pMedioDA;

    public Montanha(float largura, float comprimento, float altura) {
        this.largura = largura;
        this.comprimento = comprimento;
        this.altura = altura;
        //Define pontos da area
        Ponto A = new Ponto(-(largura / 2f), 0, comprimento);
        Ponto B = new Ponto((largura / 2f), 0, comprimento);
        Ponto C = new Ponto((largura / 2f), 0, 0);
        Ponto D = new Ponto(-(largura / 2f), 0, 0);
        //Subdivide a malha
        subdivide(A, B, C, D, 6);
        
        Ponto At = new Ponto(0, 0, comprimento);
        Ponto Bt = new Ponto(largura, 0, comprimento);
        Ponto Ct = new Ponto(largura, 0, 0);
        Ponto Dt = new Ponto(0, 0, 0);
        criarTextura(At, Bt, Ct, Dt, 6);
        //define a altura
        definirAltura((int) ((Math.random() * 3) + 1));
        //Monta as faces
        montarFaces();
        //posiciona montanha
        posicionar();
        
    }

    /*/ Ao-----L----oB
     //  |          |
     // O|     P    |M
     //  |          |
     // Do-----N----oC
     */
    boolean guardado = false;

    private void criarTextura(Ponto A, Ponto B, Ponto C, Ponto D, int subdivisao) {
        if (subdivisao == 0) {
            return;
        }
        //Encontra pontos medios
        Ponto L = Ponto.pontoMedio(A, B);
        Ponto M = Ponto.pontoMedio(B, C);
        Ponto N = Ponto.pontoMedio(C, D);
        Ponto O = Ponto.pontoMedio(D, A);
        Ponto P = Ponto.pontoMedio(A, B, C, D);
        //Salva todos os pontos
        pontosTextura.add(A);
        pontosTextura.add(B);
        pontosTextura.add(C);
        pontosTextura.add(D);
        pontosTextura.add(L);
        pontosTextura.add(M);
        pontosTextura.add(N);
        pontosTextura.add(O);
        pontosTextura.add(P);
        //Subdivide a malha
        criarTextura(A, L, P, O, subdivisao - 1);
        criarTextura(L, B, M, P, subdivisao - 1);
        criarTextura(O, P, N, D, subdivisao - 1);
        criarTextura(P, M, C, N, subdivisao - 1);
    }
    
    
    private void subdivide(Ponto A, Ponto B, Ponto C, Ponto D, int subdivisao) {
        if (subdivisao == 0) {
            return;
        }
        //Encontra pontos medios
        Ponto L = Ponto.pontoMedio(A, B);
        Ponto M = Ponto.pontoMedio(B, C);
        Ponto N = Ponto.pontoMedio(C, D);
        Ponto O = Ponto.pontoMedio(D, A);
        Ponto P = Ponto.pontoMedio(A, B, C, D);
        //Guarda pontos iniciais de subdivisao
        if (!guardado) {
            pCentro = P;
            pMedioAB = L;
            pMedioBC = M;
            pMedioCD = N;
            pMedioDA = O;
            guardado = true;
        }

        //Salva todos os pontos
        pontos.add(A);
        pontos.add(B);
        pontos.add(C);
        pontos.add(D);
        pontos.add(L);
        pontos.add(M);
        pontos.add(N);
        pontos.add(O);
        pontos.add(P);
        //Subdivide a malha
        subdivide(A, L, P, O, subdivisao - 1);
        subdivide(L, B, M, P, subdivisao - 1);
        subdivide(O, P, N, D, subdivisao - 1);
        subdivide(P, M, C, N, subdivisao - 1);
    }

    private void montarLinhas() {
        for (Ponto p : pontos) {
            if (linhas.containsKey(p.x)) {
                linhas.get(p.x).add(p);
            } else {
                ArrayList<Ponto> lista = new ArrayList<Ponto>();
                linhas.put(p.x, lista);
                lista.add(p);
            }
        }
        for (Ponto p : pontosTextura) {
            if (linhasT.containsKey(p.x)) {
                linhasT.get(p.x).add(p);
            } else {
                ArrayList<Ponto> lista = new ArrayList<Ponto>();
                linhasT.put(p.x, lista);
                lista.add(p);
            }
        }
        for (ArrayList<Ponto> pontos : linhasT.values()) {
            Collections.sort(pontos, Ponto.ORD_Z);
        }
        for (ArrayList<Ponto> pontos : linhas.values()) {
            Collections.sort(pontos, Ponto.ORD_Z);
        }
    }

    private void montarFaces() {
        montarLinhas();
        ArrayList<Float> chaves = new ArrayList<Float>();
        ArrayList<Float> chavesT = new ArrayList<Float>();
        for (Float f : linhas.keySet()) {
            chaves.add(f);
        }
        Collections.sort(chaves);
        for (Float f : linhasT.keySet()) {
            chavesT.add(f);
        }
        Collections.sort(chavesT);
        int c = 1;
        for (int i = 0; i < chaves.size() - 1; i++) {
            c = -c;
            for (int j = 0; j < linhas.size() - 1; j++) {
                c = -c;
                Ponto A = linhas.get(chaves.get(i)).get(j);
                Ponto D = linhas.get(chaves.get(i)).get(j + 1);
                Ponto B = linhas.get(chaves.get(i + 1)).get(j);
                Ponto C = linhas.get(chaves.get(i + 1)).get(j + 1);
                Face f = new Face(A, B, C, D);
                
                Ponto At = linhasT.get(chavesT.get(i)).get(j);
                Ponto Dt = linhasT.get(chavesT.get(i)).get(j + 1);
                Ponto Bt = linhasT.get(chavesT.get(i + 1)).get(j);
                Ponto Ct = linhasT.get(chavesT.get(i + 1)).get(j + 1);
                
                Face ft = new Face(At, Bt, Ct, Dt);
                
                f.pintar = c;
                faces.add(f);
                facesT.add(ft);
            }
        }
    }

    private void definirAltura(int exp) {
        for (Ponto P : pontos) {
            P.y = altura * getAltura(P.x, P.z, exp);
        }
    }

    // definida pela função |cos(x)*seno(z)|^exp
    private float getAltura(float x, float z, int exp) {
        float coseno = (float) Math.cos(Math.toRadians(x * 180));
        float seno = (float) Math.sin(Math.toRadians(z * 180));
        float rs = 0;
        rs = (float) Math.pow(Math.abs(coseno * seno), exp);
        return rs;
    }

    private void posicionar() {
        Matrizes.translacao(0.5f, -0.5f, 0, pontos);
    }
    
    private int texture = Terreno.texture;
    
    public void desenhar(GL2 gl) {
        gl.glPushMatrix();
        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL2.GL_QUADS);
        int i =0;
        for (Face f : faces) {
            /*if (f.pintar == 1) {
                gl.glColor3f(0, 1, 0);
            } else {
                gl.glColor3f(1, 1, 1);
            }*/
            Ponto A = f.getPontos().get(0);
            Ponto B = f.getPontos().get(1);
            Ponto C = f.getPontos().get(2);
            Ponto D = f.getPontos().get(3);
            
            Ponto At = facesT.get(i).getPontos().get(0);
            Ponto Bt = facesT.get(i).getPontos().get(1);
            Ponto Ct = facesT.get(i).getPontos().get(2);
            Ponto Dt = facesT.get(i).getPontos().get(3);
            
            gl.glTexCoord2f(Dt.x, Dt.z);
            gl.glVertex3f(D.x, D.y, D.z);
            gl.glTexCoord2f(Ct.x, Ct.z);
            gl.glVertex3f(C.x, C.y, C.z);
            gl.glTexCoord2f(Bt.x, Bt.z);
            gl.glVertex3f(B.x, B.y, B.z);
            gl.glTexCoord2f(At.x, At.z);
            gl.glVertex3f(A.x, A.y, A.z);
            
            i++;
        }
        gl.glEnd();
        gl.glDisable(GL2.GL_COLOR_MATERIAL);
        gl.glPopMatrix();
    }

    public float getLargura() {
        return largura;
    }

    public float getComprimento() {
        return comprimento;
    }

    public HashSet<Ponto> getPontos() {
        return pontos;
    }

    public boolean isColision(Aviao a) {
        Ponto C = a.getCentro();
        for (Ponto A : pontos) {
            if (Ponto.distancia(C, A) < a.getdColisionMax()) {
                //System.out.println("Houve colisao");
                return true;
            }
        }
        return false;
    }

}
