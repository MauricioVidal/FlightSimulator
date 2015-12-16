/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.obj;

import br.com.pjcg.util.Matrizes;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Observador {

    private Ponto centro = new Ponto(0,  0.25f, 0);
    private float anguloVisao;
    private float campoVisao;
    private float zPerto;
    private float zLonge;
    private float dObjeto;

    public Observador(float anguloVisao, float zPerto, float zLonge, float dObjeto) {
        this.anguloVisao = anguloVisao;
        this.zPerto = zPerto;
        this.zLonge = zLonge;
        this.dObjeto = dObjeto;
    }

    public void resetarPosicao(){
        centro.x = 0;
        centro.y = 0.25f;
        centro.z = 0;
    }
    
   public void movimenta(float velocidade, float aXZ, float aYZ) {
        float andaX = (float) (velocidade * Math.sin(Math.toRadians(aXZ)));
        float andaZ = (float) (velocidade * Math.cos(Math.toRadians(aXZ)));
        float andaY = (float) (velocidade * Math.sin(Math.toRadians(aYZ)));
        Matrizes.translacao(andaX, andaY, andaZ, centro);
    }

    public Ponto getCentro() {
        return centro;
    }

    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    public float getCampoVisao() {
        return campoVisao;
    }

    public void setCampoVisao(float campoVisao) {
        this.campoVisao = campoVisao;
    }

    public float getzPerto() {
        return zPerto;
    }

    public float getzLonge() {
        return zLonge;
    }

    public float getAnguloVisao() {
        return anguloVisao;
    }

    public void setdObjeto(float dObjeto) {
        this.dObjeto = dObjeto;
    }

    public float getdObjeto() {
        return dObjeto;
    }

}
