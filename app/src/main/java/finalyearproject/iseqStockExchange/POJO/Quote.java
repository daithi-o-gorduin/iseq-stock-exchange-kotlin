package finalyearproject.iseqStockExchange.POJO;

/**
 * Created by Dvaid on 24/11/2014.
 */
import com.google.gson.annotations.SerializedName;

public class Quote {

    private String symbol;//TICKER ID /*######*/
    private String Ask;//THE CURRENT PRICE
    private String AverageDailyVolume;
    private String Bid;
    private String AskRealtime;
    private String BidRealtime;
    private String BookValue;
    @SerializedName("Change_PercentChange")
    private String ChangePercentChange;
    private String Change;
    private Object Commission;
    private String Currency;
    private String ChangeRealtime;
    private String AfterHoursChangeRealtime;
    private String DividendShare; /*######*/
    private String LastTradeDate;
    private Object TradeDate; /*######*/
    private String EarningsShare;
    private Object ErrorIndicationreturnedforsymbolchangedinvalid;
    private String EPSEstimateCurrentYear; /*######*/
    private String EPSEstimateNextYear;
    private String EPSEstimateNextQuarter;
    private String DaysLow; /*######*/
    private String DaysHigh; /*######*/
    private String YearLow; /*######*/
    private String YearHigh; /*######*/
    private String HoldingsGainPercent;
    private Object AnnualizedGain;
    private Object HoldingsGain;
    private String HoldingsGainPercentRealtime;
    private Object HoldingsGainRealtime;
    private String MoreInfo;
    private Object OrderBookRealtime;
    private Object MarketCapitalization;
    private Object MarketCapRealtime;
    private String EBITDA;
    private String ChangeFromYearLow;
    private String PercentChangeFromYearLow;
    private String LastTradeRealtimeWithTime;
    private String ChangePercentRealtime;
    private String ChangeFromYearHigh;
    private String PercebtChangeFromYearHigh;
    private String LastTradeWithTime;
    private String LastTradePriceOnly;
    private Object HighLimit;
    private Object LowLimit;
    private String DaysRange; /*######*/
    private String DaysRangeRealtime;
    private String FiftydayMovingAverage;
    private String TwoHundreddayMovingAverage;
    private Object ChangeFromTwoHundreddayMovingAverage;
    private Object PercentChangeFromTwoHundreddayMovingAverage;
    private Object ChangeFromFiftydayMovingAverage;
    private Object PercentChangeFromFiftydayMovingAverage;
    private String Name;//NAME OF THE STOCK
    private Object Notes;
    private String Open;
    private String PreviousClose;//*THE CURRENT PRICE
    private Object PricePaid;
    private String ChangeinPercent;//AN INDICATION WHETHER THE STOCK HAS RISEN OR FALLEN/*THE CURRENT PRICE
    private Object PriceSales;
    private Object PriceBook;
    private Object ExDividendDate;
    private Object PERatio;
    private Object DividendPayDate;
    private Object PERatioRealtime;
    private Object PEGRatio;
    private Object PriceEPSEstimateCurrentYear;
    private Object PriceEPSEstimateNextYear;
    private String Symbol;
    private Object SharesOwned;
    private Object ShortRatio;
    private String LastTradeTime;
    private String TickerTrend;
    private Object OneyrTargetPrice;
    private String Volume;
    private Object HoldingsValue;
    private Object HoldingsValueRealtime;
    private String YearRange;
    private String DaysValueChange;
    private String DaysValueChangeRealtime;
    private String StockExchange;
    private Object DividendYield;
    private String PercentChange;


    public String getName(){
        if(Name != null) {
            return Name;
        }else{
            return "NULL";
        }
    }

    public String getsymbol(){
        if(symbol != null) {
            return symbol;
        }else{
            return "NULL.IR";
        }
    }

    public String getDaysLow(){
        if(DaysLow != null) {
            return DaysLow;
        }else{
            return "0.000";
        }
    }

    public String getDaysHigh(){
        if(DaysHigh != null) {
            return DaysHigh;
        }else{
            return "0.000";
        }
    }

    public String getYearsLow(){
        if(YearLow != null) {
            return YearLow;
        }else{
            return "0.000";
        }
    }

    public String getYearsHigh(){
        if(YearHigh != null) {
            return YearHigh;
        }else{
            return "0.000";
        }
    }

    public String getVolume(){
        if(Volume != null) {
            return Volume;
        }else{
            return "0.000";
        }
    }


    public String getLastTradeTime(){
        if(LastTradeTime != null) {
            return LastTradeTime;
        }else{
            return "00.00pm";
        }
    }


    public double getLastTradePriceOnly(){
        if(LastTradePriceOnly!= null) {
            return Double.parseDouble(LastTradePriceOnly);
        }else{
            return 0.000;
        }
    }

    public String getChangeInPercent(){

        try{
            return  ChangeinPercent;

        }catch(NullPointerException e){
            e.printStackTrace();
            return "+00.00%";
        }
    }


}