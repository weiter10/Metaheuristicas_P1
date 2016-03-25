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
public class LocalSearch extends Thread
{
    private final Parse data;
    private final Random rnd;
    private int[] solution;
    
    
    public LocalSearch(Parse data, Random rnd)
    {
        this.data = data;
        this.rnd = rnd;
    }
    
    
    @Override
    public void run()
    {
        /*  Variables   */
        int[][] distances, flow;
        int[] randomSolution, currentSolution, previousSolution, bestSolution = null;
        
        distances = data.getDistances();
        flow = data.getFlow();
        //
        
        randomSolution = LocalSearch.getRandomSolution(distances.length, rnd);

        currentSolution = randomSolution;

        do
        {
            previousSolution = currentSolution;
            currentSolution = LocalSearch.getBestNeighbour(previousSolution, data);

        }while(TestSolution.test(currentSolution, data) < TestSolution.test(previousSolution, data));

        if(bestSolution == null || TestSolution.test(previousSolution, data) < TestSolution.test(bestSolution, data))
        {
            bestSolution = previousSolution;
        }
        
        this.solution = bestSolution;
        System.out.println("local");
    }

    
    public int[] getSolution()
    {
        return solution;
    }
    
    
    private static int[] getBestNeighbour(int[] currentSolution, Parse data)
    {
        int[] bestNeighbour, currentNeighbour;
        
        bestNeighbour = Arrays.copyOf(currentSolution, currentSolution.length);
        
        for (int i = 0; i < currentSolution.length; i++)
        {
            for (int j = i+1; j < currentSolution.length; j++)
            {
                currentNeighbour = Arrays.copyOf(currentSolution, currentSolution.length);
                currentNeighbour[i] = currentSolution[j];
                currentNeighbour[j] = currentSolution[i];
                
                if(TestSolution.test(currentNeighbour, data) < TestSolution.test(bestNeighbour, data)) bestNeighbour = currentNeighbour;
            }
        }
        
        return bestNeighbour;
    }
    
    
    private static int[] getRandomSolution(int size, Random rnd)
    {
        /*  Variables   */
        int[] solution;
        int value;
        Set<Integer> sPresent = new HashSet();
        
        solution = new int[size];
        //

        //Obtenemos una solución aleatoria
        for (int i = 0; i < size; i++)
        {
            value = rnd.nextInt(size);

            while(sPresent.contains(value)) value = rnd.nextInt(size);

            sPresent.add(value);
            solution[i] = value;
        }
        
        return solution;
    }
}
