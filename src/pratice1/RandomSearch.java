/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import parseLib.Parse;

/**
 *
 * @author Manu
 */
public class RandomSearch extends Thread implements Searcheable
{
    private final Parse data;
    private final Random rnd;
    private int[] solution;
    
    
    public RandomSearch(Parse data, Random rnd)
    {
        this.data = data;
        this.rnd = rnd;
    }
    
    
    @Override
    public void run()
    {
        /*  Variables   */
        int[][] distances, flow;
        int[] currentSolution, bestSolution;
        int numIterations, value;
        Set<Integer> sPresent = new HashSet();
        
        distances = data.getDistances();
        flow = data.getFlow();
        numIterations = 1600*distances.length;
        bestSolution = new int[distances.length];
        currentSolution = new int[distances.length];
        
        //Obtenemos una solución trivial como la mejor
        for (int i = 0; i < distances.length; i++) bestSolution[i] = i;
        //
        
        //Búsqueda aleatoria
        for (int j = 0; j < numIterations; j++)
        {
            sPresent.clear();

            //Obtenemos una solución aleatoria
            for (int k = 0; k < distances.length; k++)
            {
                value = rnd.nextInt(distances.length);

                while(sPresent.contains(value)) value = rnd.nextInt(distances.length);

                sPresent.add(value);
                currentSolution[k] = value;
            }

            //Comprobamos si la nueva solución es mejor que la que llevamos hasta ahora
            if((TestSolution.test(currentSolution, data) < TestSolution.test(bestSolution, data)))
            {
                bestSolution = Arrays.copyOf(currentSolution, currentSolution.length);
            }
        }

        this.solution = bestSolution;
        //System.out.println("random");
    }

    public int[] getSolution()
    {
        return solution;
    }
}
