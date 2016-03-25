/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author manu
 */
public class TabuList
{
    private final int size;
    private final ArrayList<TabuMovement> list;
    
    
    public TabuList(int size)
    {
        list = new ArrayList();
        this.size = size;
    }
    
    
    public boolean containsTabuMovement(Set<Movement> movP)
    {
        boolean found = false;
        int i = 0;
        
        while(i < list.size() && !found)
        {
            Set<Movement> movL = list.get(i).getMov();
            Set<Movement> intersection = new HashSet(movL);
            intersection.retainAll(movP);
            
            if(!intersection.isEmpty()) found = true;
            
            i++;
        }
        
        return found;
    }
    
    
    public void add(TabuMovement mov)
    {
        list.add(mov);
        
        if(list.size() > size) list.remove(0);
    }

    
    public int getSize() {
        return size;
    }
}














































