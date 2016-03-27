/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import parseLib.Parse;

/**
 *
 * @author Manu
 */
public class TabuSearch extends Thread implements Searcheable
{
    private final Parse data;
    private final Random rnd;
    private int[] solution;
    
    
    public TabuSearch(Parse data, Random rnd)
    {
        this.data = data;
        this.rnd = rnd;
    }
    
    
    @Override
    public void run()
    {
        /*  Variables   */
        Solution resetBestSolution, bestSolutionAll = null;
        TabuList tabu;
        int numSolutions, numIterations, numResets;
        double value;
        int[][] tabFrequency;
        //
        
        numSolutions = 40;
        numIterations = 8*data.getDistances().length;
        numResets = 4;
        tabu = new TabuList(2);
        tabFrequency = new int[data.getDistances().length][data.getDistances().length];
        
        
        for (int i = 0; i < numResets; i++)
        {
            value = rnd.nextDouble();
            
            if(value < 0.25 || bestSolutionAll == null)
            {
                resetBestSolution = new Solution(TabuSearch.getRandomSolution(data.getDistances().length, rnd));
            }
            
            else if(value >= 0.25 && value < 0.75)
            {
                resetBestSolution = TabuSearch.getSolutionByFrequency(tabFrequency, rnd);
            }
            
            else resetBestSolution = bestSolutionAll;
            
            for (int j = 0; j < numIterations; j++)
            {
                resetBestSolution = TabuSearch.getBestNeighbour(numSolutions, resetBestSolution, tabu, rnd, data);
                
                if(bestSolutionAll == null || resetBestSolution.getGoodness(data) < bestSolutionAll.getGoodness(data))
                {
                    bestSolutionAll = resetBestSolution;
                }
                
                //Añadimos los movimientos tabu de la nueva solución
                tabu.add(new TabuMovement(resetBestSolution.getMov()));
                
                //Actualizamos la tabla de frecuencias con la nueva solución
                TabuSearch.updateTabFrequency(tabFrequency, resetBestSolution.getList());
            }
            
            value = rnd.nextDouble();
            
            if(value >= 0.5) tabu = new TabuList(tabu.getSize()*2);
            
            else tabu = new TabuList((tabu.getSize()+1)/2);
        }
        
        
        this.solution = bestSolutionAll.getList();
        //System.out.println("tabu");
    }

    @Override
    public int[] getSolution()
    {
        return solution;
    }
    
    
    /**
     * Get best neighbour from masterSolution. The method doesn't generate
     * two identical solutions. The operator used is 2-opt
     * @param numSolutions Number of solution to generate
     * @param masterSolution Solution use to generate new solution
     * @param rnd Random numbers generator
     * @return best neighbour
     */
    private static Solution getBestNeighbour(int numSolutions, Solution masterSolution, TabuList tabu, Random rnd, Parse data)
    {
        /*  Variables locales   */
        Solution currentSolution, bestSolution = null;
        int[] list;
        Set<Solution> setSolutions = new HashSet();
        int value, index1, index2;
        Set<Movement> setMov;
        //
        
        //Obtenemos las soluciones
        for (int i = 0; i < numSolutions; i++)
        {
            index1 = rnd.nextInt(masterSolution.getSize());
            index2 = rnd.nextInt(masterSolution.getSize());
            
            if(index1 == index2) index2 = rnd.nextInt(masterSolution.getSize());
            
            list = Arrays.copyOf(masterSolution.getList(), masterSolution.getSize());
            list[index1] = masterSolution.getList()[index2];
            list[index2] = masterSolution.getList()[index1];
            setMov = new HashSet();
            setMov.add(new Movement(list[index1], index1));
            setMov.add(new Movement(list[index2], index2));
            currentSolution = new Solution(list, setMov);
            
            //Si la solucion no ha sido generada antes
            if(!setSolutions.contains(currentSolution))
            {
                setSolutions.add(currentSolution);
                
                //Si no es un movimiento tabú o si cumple el nivel de aspiración
                //Nivel de aspiración: ser mejor que la mejor solución desde la reinicialización
                if(!tabu.containsTabuMovement(currentSolution.getMov()) || currentSolution.getGoodness(data) < masterSolution.getGoodness(data))
                {
                    if(bestSolution == null || currentSolution.getGoodness(data) < bestSolution.getGoodness(data))
                    {
                        bestSolution = currentSolution;
                    }
                }
            }
            
            else i--;
        }
        
        return bestSolution;
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
    
    
    private static void updateTabFrequency(int[][] tabFrequency, int[] vector)
    {
        for (int i = 0; i < vector.length; i++) tabFrequency[i][vector[i]]++;
    }
    
    
    private static Solution getSolutionByFrequency(int[][] tabFrequency, Random rnd)
    {
        int size = tabFrequency[0].length, sum, posi;
        int[] list = new int[size], frequency, frequencyReduced;
        double[] vector;
        double value;
        Map<Double,Stack<Integer>> map = new HashMap();
        Map<Double,Double> mapSum = new HashMap();
        Map<Integer,Integer> posiReal = new HashMap();
        PriorityQueue<Double> queue = new PriorityQueue();
        PriorityQueue<Double> orderTemp = new PriorityQueue(Collections.reverseOrder());
        Set<Integer> invalidPositions = new HashSet();
        Set<Integer> posiVector = new HashSet();
        ArrayDeque<Integer> positions = new ArrayDeque();
        
        //Obtenemos el orden de asignación
        do
        {
            posi = rnd.nextInt(size);
            
            while(posiVector.contains(posi)) posi = rnd.nextInt(size);
            
            posiVector.add(posi);
            positions.add(posi);
            
        }while(posiVector.size() != size);
        
        
        //Asignamos a cada posición de la solución un valor
        for (int i : positions)
        {
            frequency = tabFrequency[i];
            frequencyReduced = new int[size-invalidPositions.size()];
            
            for (int j = 0, k = 0; j < size; j++)
            {
                if(!invalidPositions.contains(j))
                {
                    frequencyReduced[k] = frequency[j];
                    posiReal.put(k, j);
                    k++;
                }
            }
            
            sum = TabuSearch.getSum(frequencyReduced);
            vector = TabuSearch.getProbability(frequencyReduced, sum);
            vector = TabuSearch.getInverse(vector, sum);
            vector = TabuSearch.getProbability(vector);
            value = 0;
            
            //Almacenamos a que valor de probabilidad pertenece cada posición del vector
            for (int j = 0; j < vector.length; j++)
            {
                if(!map.containsKey(vector[j])) map.put(vector[j], new Stack());
                
                map.get(vector[j]).push(j);
                orderTemp.add(vector[j]);
            }
            
            //Creamos una estructura de datos para ordenar el vector
            for (int j = 0; j < vector.length; j++)
            {
                value += orderTemp.peek();
                mapSum.put(value, orderTemp.poll());
                queue.add(value);
            }
            
            //Tiramos el dado
            value = rnd.nextDouble();

            //Mientras el valor sea mayor que el elemento de la cola de prioridad (ordenada)
            //seguimos buscando
            while(value > queue.element()) queue.remove();
            
            list[i] = posiReal.get(map.get(mapSum.get(queue.element())).peek());
            invalidPositions.add(list[i]);
            
            map.clear();
            mapSum.clear();
            queue.clear();
            posiReal.clear();
            orderTemp.clear();
        }
        
        return new Solution(list);
    }
    
    
    private static int getSum(int[] vector)
    {
        int sum = 0;
        
        for (int i = 0; i < vector.length; i++) sum += vector[i];
        
        return sum;
    }
    
    
    private static double[] getProbability(int[] vector, int sum)
    {
        double[] result = new double[vector.length];
        
        for (int i = 0; i < vector.length; i++) sum += vector[i];
        
        for (int i = 0; i < vector.length; i++) result[i] = (double)vector[i]/sum;
        
        return result;
    }
    
    
    private static double[] getProbability(double[] vector)
    {
        double sum = 0;
        double[] result = new double[vector.length];
        
        for (int i = 0; i < vector.length; i++) sum += vector[i];
        
        for (int i = 0; i < vector.length; i++) result[i] = vector[i]/sum;
        
        return result;
    }
    
    
    private static double[] getInverse(double[] vector, int sum)
    {
        double[] result = new double[vector.length];
        
        for (int i = 0; i < vector.length; i++)
        {
            if(vector[i] == 0) vector[i] = sum;//Si su probabilidad era 0 le asignamos el máximo
                                               //valor posible (suma del vector)
                
            result[i] = (double)1/vector[i];
        }
        
        return result;
    }
}
















































