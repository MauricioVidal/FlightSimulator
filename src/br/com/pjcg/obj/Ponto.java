/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.obj;

import java.util.Comparator;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Ponto {

    public float x;
    public float y;
    public float z;

    public static final OrdenarX ORD_X = new OrdenarX();
    public static final OrdenarY ORD_Y = new OrdenarY();
    public static final OrdenarZ ORD_Z = new OrdenarZ();
    

    public Ponto() {
    }

    public Ponto(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double calcularDistancia(Ponto p) {
        double xT, yT, zT;
        
        xT = x - p.x;
        yT = y - p.y;
        zT = z - p.z;
        
        xT = Math.pow(xT, 2);
        yT = Math.pow(yT, 2);
        zT = Math.pow(zT, 2);
      
        return Math.pow(xT+yT+zT, 0.5);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }


    public static float distancia(Ponto A, Ponto B){
        float x = A.x - B.x;
        float y = A.y - B.y;
        float z = A.z - B.z;
        x = (float) Math.pow(x, 2);
        y = (float) Math.pow(y, 2); 
        z = (float) Math.pow(z, 2);
        return (float) Math.sqrt(x+y+z);
    }
    
    public static float distancia2D_XZ(Ponto A, Ponto B){
        float x = A.x - B.x;
        float z = A.z - B.z;
        x = (float) Math.pow(x, 2); 
        z = (float) Math.pow(z, 2);
        return (float) Math.sqrt(x+z);
    }
    
    public static Ponto pontoMedio(Ponto... pontos){
        float x=0,y=0,z=0;
        int i=0;
        for(Ponto p : pontos){
            x += p.x;
            y += p.y;
            z += p.z;
            i++;
        }
        return new Ponto(x/i, y/i, z/i);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Ponto){
            Ponto p = (Ponto) obj;
            if(p.x == x && p.y ==y && p.z == z) return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "("+x+","+y+","+z+")";
    }

    
    private static class OrdenarZ implements Comparator<Ponto>{

        @Override
        public int compare(Ponto o1, Ponto o2) {
            Float f1 = o1.z; Float f2 = o2.z;
            return f1.compareTo(f2);
        }
        
    }
    
    
    private static class OrdenarY implements Comparator<Ponto>{

        @Override
        public int compare(Ponto o1, Ponto o2) {
            Float f1 = o1.y; Float f2 = o2.y;
            return f1.compareTo(f2);
        }
        
    }
    
    private static class OrdenarX implements Comparator<Ponto>{

        @Override
        public int compare(Ponto o1, Ponto o2) {
            Float f1 = o1.x; Float f2 = o2.x;
            return f1.compareTo(f2);
        }
    
    }
    
}
