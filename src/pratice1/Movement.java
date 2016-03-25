/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pratice1;

/**
 *
 * @author manu
 */
public class Movement
{
    int value, posi;
    
    
    public Movement(int value, int posi)
    {
        this.value = value;
        this.posi = posi;
    }

    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + this.value;
        hash = 37 * hash + this.posi;
        return hash;
    }
    
    
    @Override
    public boolean equals(Object o)
    {
        if(o == null) return false;
        
        else if (!(o instanceof Movement)) return false;
        
        else
        {
            Movement movement = (Movement) o;
        
            return value == movement.value && posi == movement.posi;
        }
    }
}












































