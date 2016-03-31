/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import parseLib.Parse;

/**
 *
 * @author Manu
 */
public abstract class Main
{
    public static void main(String[] args)
    {
        ArrayList<Thread> listA = new ArrayList();
        long initial, end;
        Random rnd;
        int numExecutions = 10;
        /*Elegir el fichero a procesar:
        tai25b
        sko90
        tai150b
        */
        Parse data = new Parse("tai150b");
        
        //Ejecutamos el algoritmo Greedy
        initial = System.currentTimeMillis();
        System.out.println("Greedy: " + TestSolution.test(Greedy.start(data),data));
        end = System.currentTimeMillis();
        System.out.println("Tiempo: " + (end-initial) + "ms");
        System.out.println("---------------------------------------");
        //--Gready
        
        //Ejecutamos el algoritmo Seleccionado
        try
        {
            initial = System.currentTimeMillis();

            for (int i = 0; i < numExecutions; i++)
            {
                rnd = new Random();
                rnd.setSeed(i);
                /*Elegir el algoritmo a ejecutar:
                RandomSearch
                LocalSearch
                SimulatedAnnealing
                TabuSearch
                */
                listA.add(new TabuSearch(data, rnd));
                listA.get(i).start();
            }

            for (int i = 0; i < numExecutions; i++) listA.get(i).join();

            end = System.currentTimeMillis();

            for (int i = 0; i < numExecutions; i++)
            {
                System.out.println(TestSolution.test(((Searcheable)(listA.get(i))).getSolution(), data));
            }

            System.out.println("Tiempo medio de una ejecucion: " + (end-initial)/numExecutions + "ms");

        } catch (InterruptedException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //--
        
    }
}
