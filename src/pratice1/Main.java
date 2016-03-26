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
        Random rnd;
        RandomSearch rs;
        LocalSearch ls;
        SimulatedAnnealing sa;
        TabuSearch ts;
        
        //tai25b
        //sko90
        //tai150b
        Parse data = new Parse("tai25b");
        System.out.println("Greedy: " + TestSolution.test(Greedy.start(data),data));
        System.out.println("---------------------------------------");
        
        /*
        for(int i = 0; i < 10; i++)
        {
            try
            {
                rnd = new Random();
                rnd.setSeed(i);
                rs = new RandomSearch(data, rnd);
                ls = new LocalSearch(data, rnd);
                sa = new SimulatedAnnealing(data, rnd);
                ts = new TabuSearch(data, rnd);
                
                rs.start();
                ls.start();
                sa.start();
                ts.start();
                
                rs.join();
                ls.join();
                sa.join();
                ts.join();
                
                
                System.out.println("Iteracion " + i);
                System.out.println("Busqueda aleatoria: " + TestSolution.test(rs.getSolution(), data));
                System.out.println("Busqueda local: " + TestSolution.test(ls.getSolution(), data));
                System.out.println("Enfriamiento simulado: " + TestSolution.test(sa.getSolution() ,data));
                System.out.println("Busqueda Tabu: " + TestSolution.test(ts.getSolution() ,data));
                
                
                
                System.out.println("---------------------------------------");
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
*/
        
        ArrayList<LocalSearch> lsA;
        
        for(int i = 0; i < 10; i = i + 5)
        {
            lsA = new ArrayList();
        
            try
            {
                for (int j = 0; j < 5; j++)
                {
                    rnd = new Random();
                    rnd.setSeed(i+j);
                    lsA.add(new LocalSearch(data, rnd));
                    lsA.get(j).start();
                }
                
                for (int j = 0; j < 5; j++)
                {
                    lsA.get(j).join();
                }
                
                for (int j = 0; j < 5; j++)
                {
                    System.out.println("Iteracion " + i+j + ": " + TestSolution.test(lsA.get(j).getSolution(), data));
                }
                
                
                
                
                System.out.println("---------------------------------------");
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
