/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import parseLib.Parse;

/**
 *
 * @author manu
 */
public class Solution
{
    private final int[] list;
    private final Set<Movement> mov;
    
    
    public Solution(int[] list, Set<Movement> mov)
    {
        this.list = list;
        this.mov = mov;
    }
    
    
    public Solution(int[] list)
    {
        this(list, new HashSet());
    }
    
    
    public int getGoodness(Parse data)
    {
        return TestSolution.test(list, data);
    }
    
    
    @Override
    public boolean equals(Object o)
    {
        if(o == null) return false;
        
        else if (!(o instanceof Solution)) return false;
        
        else
        {
            Solution sol = (Solution) o;
            boolean equals = true;
            int i = 0;
            
            while(i < list.length && equals)
            {
                if(list[i] != sol.list[i]) equals = false;
                
                i++;
            }
            
            return equals;
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + Arrays.hashCode(this.list);
        hash = 17 * hash + Objects.hashCode(this.mov);
        return hash;
    }
    
    
    public int getSize()
    {
        return list.length;
    }

    public int[] getList() {
        return list;
    }

    public Set<Movement> getMov() {
        return mov;
    }
}
