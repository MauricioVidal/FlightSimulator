/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.obj;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Face {
    
    private List<Ponto> pontos = new ArrayList<Ponto>();
    private List<Ponto> pontosTextura = new ArrayList<Ponto>();
    public int pintar;

    public Face(Ponto...ponto) {
        for(Ponto P : ponto){
            pontos.add(P);
        }
    }

    public Face() {
    }

    public List<Ponto> getPontos() {
        return pontos;
    }
    
    public void addPonto(Ponto p){
        pontos.add(p);
    }

    public List<Ponto> getPontosTextura() {
        return pontosTextura;
    }
    public void addPontoTextura(Ponto p){
        pontosTextura.add(p);
    }
    
    
}
