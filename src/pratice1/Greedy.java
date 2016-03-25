/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import parseLib.Parse;

/**
 *
 * @author Manu
 */
public abstract class Greedy
{
    /**
     * Obtiene una solución mediante el algoritmo Greedy
     * @param data Datos de entrada
     * @return Bondad de la solución
     */
    public static int[] start(Parse data)
    {
        /*  Variables   */
        int[][] distances, flow;
        int[] bestSolution;
        
        distances = data.getDistances();
        flow = data.getFlow();
        bestSolution = data.getSolution();
        //
        
        int[] solutionGreedy = new int[distances.length];
        int[] fV = new int[distances.length];
        int[] dV = new int[distances.length];
        
        PriorityQueue<Integer> fQueue = new PriorityQueue(Collections.reverseOrder());
        PriorityQueue<Integer> dQueue = new PriorityQueue();
        
        Map<Integer,Deque<Integer>> fMap = new HashMap();
        Map<Integer,Deque<Integer>> dMap = new HashMap();
        //
        
        /*
        Obtenemos los vectores f y d, donde f es el sumatorio de los flujos
        y d el sumatorio de las distancias.
        La información es guardada en estructuras de datos basada en árboles, de
        forma que el posterior acceso a la información sea constante
        */
        for (int i = 0; i < distances.length; i++)
        {
            for (int j = 0; j < distances.length; j++)
            {
                fV[i] += flow[i][j];
                dV[i] += distances[i][j];
            }
            
            fQueue.add(fV[i]);
            if(!fMap.containsKey(fV[i])) fMap.put(fV[i], new ArrayDeque());
            fMap.get(fV[i]).push(i);
            
            dQueue.add(dV[i]);
            if(!dMap.containsKey(dV[i])) dMap.put(dV[i], new ArrayDeque());
            dMap.get(dV[i]).push(i);
        }
        
        /*
        Obtemos la solución Greedy, para ello:
        el algoritmo irá seleccionando la unidad i libre con mayor fi y le
        asignará la localización k libre con menor dk .
        */
        for (int i = 0; i < distances.length; i++)
        {
            int unit = dMap.get(dQueue.poll()).pop();
            solutionGreedy[unit] = fMap.get(fQueue.poll()).pop();
        }
        
        return solutionGreedy;
    }
}







































