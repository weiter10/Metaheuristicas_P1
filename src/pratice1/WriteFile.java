/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manu
 */
public abstract class WriteFile
{
    public static void write(String filename, ArrayList<Integer> trace)
    {
        //Escribimos el fichero
        try (PrintWriter out = new PrintWriter(filename))
        {
            for(int value : trace) out.println(value);
            
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(LocalSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
