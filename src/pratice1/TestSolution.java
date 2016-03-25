/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import parseLib.Parse;

/**
 *
 * @author Manu
 */
public abstract class TestSolution
{
    /**
     * Método que devuelve la bondad de una solución
     * @param partSolution Solución parcial
     * @param data Datos de entrada
     * @return Bondad de la solución
     */
    public static int test(int[] partSolution, Parse data)
    {
        /* Local variables */
        int cost = 0;
        int[][] distances, flow;
        //
        
        distances = data.getDistances();
        flow = data.getFlow();
        
        for (int i = 0; i < partSolution.length; i++)
        {
            for (int j = 0; j < partSolution.length; j++)
            {
                cost += distances[i][j] * flow[partSolution[i]][partSolution[j]];
            }
        }
        
        return cost;
    }
}
