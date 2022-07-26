package ar.edu.unsl.mys.utils;

import java.util.Random;

public class CustomRandomizer implements Randomizer
{
    private static CustomRandomizer customRandomizer;
    private static double amountGenerated;
    
    private Random randomizerImp;
    
    private CustomRandomizer()
    {
        this.randomizerImp = new Random(System.currentTimeMillis());
    }

    public static CustomRandomizer getInstance()
    {
        if(CustomRandomizer.customRandomizer == null) CustomRandomizer.customRandomizer = new CustomRandomizer();
        return CustomRandomizer.customRandomizer;
    }

    public static double getAmountGenerated()
    {
        return CustomRandomizer.amountGenerated;
    }
    @Override
    public double nextRandom() {
        
        CustomRandomizer.amountGenerated++;
        return this.randomizerImp.nextDouble();
    }
}