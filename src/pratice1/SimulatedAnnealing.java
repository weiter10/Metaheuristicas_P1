/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import parseLib.Parse;

/**
 *
 * @author Manu
 */
public class SimulatedAnnealing extends Thread implements Searcheable
{
    private final Parse data;
    private final Random rnd;
    private int[] solution;
    
    
    public SimulatedAnnealing(Parse data, Random rnd)
    {
        this.data = data;
        this.rnd = rnd;
    }
    
    
    @Override
    public void run()
    {
        /*  Variables   */
        int[] currentSolution, bestSolution;
        double t0, mu = 0.3, fi = 0.3, t, cost, value;
        int numPosiToChange = 20, numIterations = 80*data.getDistances().length;
        ArrayList<int[]> setSolutions;
        //
        
        currentSolution = Greedy.start(data);
        bestSolution = currentSolution;
        t0 = (mu*TestSolution.test(currentSolution, data))/(-Math.log10(fi));
        t = t0;

        for (int i = 0; i < numIterations; i++)
        {
            setSolutions = SimulatedAnnealing.getSolutions(numPosiToChange, currentSolution, rnd);
            
            for (int[] tempSolution : setSolutions)
            {
                cost = TestSolution.test(tempSolution, data) - TestSolution.test(currentSolution, data);
                value = Math.pow(Math.E, -cost/t);
                
                if(rnd.nextDouble() < value || cost < 0)
                {
                    if(TestSolution.test(bestSolution, data) > TestSolution.test(tempSolution, data)) bestSolution = tempSolution;
                    
                    currentSolution = tempSolution;
                }
            }
            
            //Enfriamos la temperatura, le sumamos 1 a i ya que en la primera iteración vale 0
            //y se quedaría igual
            t = t0/(1+i+1);
        }
        
        this.solution = bestSolution;
        //System.out.println("simutaled");
    }

    @Override
    public int[] getSolution() {
        return solution;
    }
    
    
    /*
    private static Set<int[]> getSolutions(int numSolutions, int[] masterSolution)
    {
        //  Variables locales
        int[] newSolution, posiToChanges;
        Set<int[]> setSolutions = new HashSet();
        Random rnd = new Random();
        int numPositionsToChange, value;
        Map<Integer,Integer> positions = new HashMap();
        Map<Integer,Boolean> positionUsed = new HashMap();
        
        rnd.setSeed(System.nanoTime());
        //
        
        for (int i = 0; i < numSolutions; i++)
        {
            numPositionsToChange = (int)Math.round((rnd.nextGaussian()*Math.sqrt(masterSolution.length))+((double)masterSolution.length/2.0));
            
            if(numPositionsToChange >= 2 && numPositionsToChange <= masterSolution.length)
            {
                posiToChanges = new int[numPositionsToChange];
                
                for (int j = 0; j < numPositionsToChange; j++)
                {
                    value = rnd.nextInt(masterSolution.length);
                    
                    while(positionUsed.containsKey(value)) value = rnd.nextInt(masterSolution.length);
                    
                    positionUsed.put(value, true);
                    posiToChanges[j] = value;
                }
                
                for (int j = 0; j < posiToChanges.length; j++)
                {
                    value = rnd.nextInt(posiToChanges.length);//Buscamos un índice para intercambiar 
                    
                    if(positions.containsKey(j))
                }
            }
        }
    }
    */
    
    private static ArrayList<int[]> getSolutions(int numSolutions, int[] masterSolution, Random rnd)
    {
        /*  Variables locales   */
        int[] newSolution;
        ArrayList<int[]> setSolutions = new ArrayList();
        int value, index1, index2;
        Map<Integer,Integer> positions = new HashMap();
        Map<Integer,Boolean> positionUsed = new HashMap();
        //
        
        for (int i = 0; i < numSolutions; i++)
        {
            index1 = rnd.nextInt(masterSolution.length);
            index2 = rnd.nextInt(masterSolution.length);
            
            if(index1 == index2) index2 = rnd.nextInt(masterSolution.length);
            
            newSolution = Arrays.copyOf(masterSolution, masterSolution.length);
            newSolution[index1] = masterSolution[index2];
            newSolution[index2] = masterSolution[index1];
            setSolutions.add(newSolution);
        }
        
        return setSolutions;
    }
}



























