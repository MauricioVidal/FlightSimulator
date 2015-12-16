/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pjcg.util;

import br.com.pjcg.obj.Ponto;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Mauricio R. Vidal
 */
public class Matrizes {

    public static void translacao(float x, float y, float z, Set<Ponto> pontos) {
        for (Ponto p : pontos) {
            p.x += x;
            p.y += y;
            p.z += z;
        }
    }
    
    public static void translacao(float x, float y, float z, List<Ponto> pontos) {
        for (Ponto p : pontos) {
            p.x += x;
            p.y += y;
            p.z += z;
        }
    }

    public static void translacao(float x, float y, float z, Ponto p) {
        p.x += x;
        p.y += y;
        p.z += z;
    }

    public static void rotacao(float angulo, int x, int y, int z, Set<Ponto> pontos) {
        if (x == 1) {
            for (Ponto p : pontos) {
                p.y = (float) (p.y * Math.cos(Math.toRadians(angulo)) - p.z * Math.sin(Math.toRadians(angulo)));
                p.z = (float) (p.y * Math.sin(Math.toRadians(angulo)) + p.z * Math.cos(Math.toRadians(angulo)));
            }
            return;
        }

        if (y == 1) {
            for (Ponto p : pontos) {
                p.x = (float) (p.x * Math.cos(Math.toRadians(angulo)) + p.z * Math.sin(Math.toRadians(angulo)));
                p.z = (float) (-p.x * Math.sin(Math.toRadians(angulo)) + p.z * Math.cos(Math.toRadians(angulo)));
            }
            return;
        }

        if (z == 1) {
            for (Ponto p : pontos) {
                p.x = (float) (p.x * Math.cos(Math.toRadians(angulo)) - p.y * Math.sin(Math.toRadians(angulo)));
                p.y = (float) (p.x * Math.sin(Math.toRadians(angulo)) + p.y * Math.cos(Math.toRadians(angulo)));
            }
        }

    }

    public static void rotacao(float angulo, int x, int y, int z, List<Ponto> pontos) {
        if (x == 1) {
            for (Ponto p : pontos) {
                p.y = (float) (p.y * Math.cos(Math.toRadians(angulo)) - p.z * Math.sin(Math.toRadians(angulo)));
                p.z = (float) (p.y * Math.sin(Math.toRadians(angulo)) + p.z * Math.cos(Math.toRadians(angulo)));
            }
            return;
        }

        if (y == 1) {
            for (Ponto p : pontos) {
                p.x = (float) (p.x * Math.cos(Math.toRadians(angulo)) + p.z * Math.sin(Math.toRadians(angulo)));
                p.z = (float) (-p.x * Math.sin(Math.toRadians(angulo)) + p.z * Math.cos(Math.toRadians(angulo)));
            }
            return;
        }

        if (z == 1) {
            for (Ponto p : pontos) {
                p.x = (float) (p.x * Math.cos(Math.toRadians(angulo)) - p.y * Math.sin(Math.toRadians(angulo)));
                p.y = (float) (p.x * Math.sin(Math.toRadians(angulo)) + p.y * Math.cos(Math.toRadians(angulo)));
            }
        }

    }
    
    public static void rotacao(float angulo, int x, int y, int z, Ponto p) {
        if (x == 1) {
            p.y = (float) (p.y * Math.cos(Math.toRadians(angulo)) - p.z * Math.sin(Math.toRadians(angulo)));
            p.z = (float) (p.y * Math.sin(Math.toRadians(angulo)) + p.z * Math.cos(Math.toRadians(angulo)));
            return;
        }

        if (y == 1) {
            p.x = (float) (p.x * Math.cos(Math.toRadians(angulo)) - p.z * Math.sin(Math.toRadians(angulo)));
            p.z = (float) (p.x * Math.sin(Math.toRadians(angulo)) + p.z * Math.cos(Math.toRadians(angulo)));
            return;
        }

        if (z == 1) {
            p.x = (float) (p.x * Math.cos(Math.toRadians(angulo)) - p.y * Math.sin(Math.toRadians(angulo)));
            p.y = (float) (p.x * Math.sin(Math.toRadians(angulo)) + p.y * Math.cos(Math.toRadians(angulo)));
        }

    }

    public static void escala(float x, float y, float z, Set<Ponto> pontos) {
        for (Ponto p : pontos) {
            p.x *= x;
            p.y *= y;
            p.z *= z;
        }
    }

     public static void escala(float x, float y, float z, List<Ponto> pontos) {
        for (Ponto p : pontos) {
            p.x *= x;
            p.y *= y;
            p.z *= z;
        }
    }
    
    public static void escala(float x, float y, float z, Ponto p) {
        p.x *= x;
        p.y *= y;
        p.z *= z;
    }
}
