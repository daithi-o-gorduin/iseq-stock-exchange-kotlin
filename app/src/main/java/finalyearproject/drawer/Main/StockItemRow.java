package finalyearproject.drawer.Main;

/**
 * Created by Dvaid on 13/01/2015.
 */
public class StockItemRow {

    String symbol;
    String name;
    String change;
    Double lastTradePrice;


    public StockItemRow(String symbol,String name,String change, Double lastTradePrice){
        this.symbol = symbol;
        this.name = name;
        this.change = change;
        this.lastTradePrice = lastTradePrice;

    }

    public String getSymbol(){
        return symbol;
    }

    public String getName(){
        return name;
    }

    public String getChange(){
        return change;
    }

    public Double getLastTradePrice(){
        return lastTradePrice;
    }


}
