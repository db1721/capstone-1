package Utilities;

import java.util.Random;

public class RandomNumber 
{
    private final int num;
    private int highestToCount;
    
    public RandomNumber(int highestToCount) 
    {
    	this.highestToCount = highestToCount;
    	
        // create instance of Random class
        Random r = new Random();

        // Generate random integers in range 1 to highestToCount
        this.num = r.nextInt(highestToCount) + 1;
    }
    
    public int getNum()
    {
        return this.num;
    }
    
    public int getNewNum()
    {
    	RandomNumber rn = new RandomNumber(this.highestToCount);
    	int newNum = rn.getNum();
        return newNum;
    }
}