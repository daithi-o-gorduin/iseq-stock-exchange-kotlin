package finalyearproject.drawer.Formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Dvaid on 12/03/2015.
 */
public class NumberFormatter {

    public String percentChange(double totalCost, double totalValue){
        String percentChangeAsString = "0.0%";
        try {
            double temp = totalCost - totalValue;
            double percent = (temp / totalValue)*100;
            percentChangeAsString = Double.toString(percent)+"%";
        }catch(ArithmeticException e){
            e.printStackTrace();
        }
        return percentChangeAsString;
    }


    public double getPercentOfOneNumFromAnother(double smaller, double larger){
        double result = 0.0;
        if(smaller != 0.0 || larger != 0.0){

            result = (smaller / larger) * 100;
        }
        return result;
    }

    public double getFoolsRatio(double priceToEarnings, double estimateNext,double estimateCurrent){

        double growthRate = getGrowthRate(estimateNext,estimateCurrent);
        double foolsRatio = (priceToEarnings/growthRate);
        return foolsRatio;
    }

    public double getGrowthRate(double estimateNext,double estimateCurrent){
        double growthRate = (((estimateNext-estimateCurrent)/estimateCurrent)*100);
        return growthRate;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
