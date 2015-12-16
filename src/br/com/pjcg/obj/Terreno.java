/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.obj;

import br.com.pjcg.janela.Janela;
import br.com.pjcg.util.Matrizes;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Terreno {

    private Montanha montanhas[][];
    private Ceu ceu;
    private int quantidadeX, quantidadeZ;

    public Terreno(int quantidadeX, int quantidadeZ, GL2 gl) {
        this.quantidadeX = quantidadeX;
        this.quantidadeZ = quantidadeZ;
        carregaTextura(gl);
        montanhas = new Montanha[quantidadeX][quantidadeZ];
        ceu = new Ceu(gl);
        posicionarPontos();
    }
    public static int texture;
    private void carregaTextura(GL2 gl){
        try {
            File im = new File("./resources/image10.png");
            Texture t = TextureIO.newTexture(im, true);
            texture = t.getTextureObject(gl);
        } catch (IOException ex) {
            Logger.getLogger(Terreno.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GLException ex) {
            Logger.getLogger(Terreno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void posicionarPontos() {
        float pX = -(quantidadeX / 2f);
        float pY = 0;
        float pZ = -(quantidadeZ / 2f);

        for (int i = 0; i < quantidadeZ; i++) {
            for (int j = 0; j < quantidadeX; j++) {
                montanhas[i][j] = new Montanha(1, 1, (float) Math.random() * 2);
                float largura = montanhas[i][j].getLargura();
                float comprimento = montanhas[i][j].getComprimento();
                Set<Ponto> pontos = montanhas[i][j].getPontos();
                Matrizes.translacao(pX + (j * largura), pY, pZ + (i * comprimento), pontos);
                Matrizes.escala(20, 5, 20, pontos);
            }
        }
    }

    public void desenhar(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 1);
        ceu.desenhar(gl);
        for (int i = 0; i < quantidadeZ; i++) {
            for (int j = 0; j < quantidadeX; j++) {
                montanhas[i][j].desenhar(gl);
            }
        }
        gl.glPopMatrix();
    }

    public boolean isColision(Aviao a) {
        Ponto centro = a.getCentro();
            for (int i = 0; i < quantidadeZ; i++) {
                for (int j = 0; j < quantidadeX; j++) {
                    float dPersonMontanha = Ponto.distancia2D_XZ(montanhas[i][j].pCentro, centro);
                    float dEscolhaMontanha = (float) (Ponto.distancia2D_XZ(montanhas[i][j].pCentro, montanhas[i][j].pMedioBC) * Math.sqrt(2));
                    if (dPersonMontanha <= dEscolhaMontanha) {
                        if (montanhas[i][j].isColision(a)) {
                            Janela.animator.stop();
                            return true;
                        }
                    }
                }
            }
            return false;
    }

}
