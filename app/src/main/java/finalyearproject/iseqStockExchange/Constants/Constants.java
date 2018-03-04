package finalyearproject.iseqStockExchange.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dvaid on 24/11/2014.
 */

public class Constants {
    public static final String STOCK_INDEXES = "\"AIB.IR\",\"BIR.IR\",\"DOY.IR\",\"EIL1.IR\",\"DOP.IR\",\"YZA.IR\",\"DD7D.IR\",\"DD7E.IR\",\"GCC.IR\",\"FKV.IR\"," +
            "\"DQ5.IR\",\"DD8B.IR\",\"CRG.IR\",\"DD8A.IR\",\"DHG.IR\",\"DLE.IR\",\"GUI.IR\",\"DQ7.IR\",\"DRS.IR\",\"FAC.IR\"," +
            "\"FOI.IR\",\"EG7.IR\",\"GYQ.IR\",\"FQ3.IR\",\"GAME.IR\",\"GL9.IR\",\"8GW.IR\",\"GN1.IR\",\"HBRN.IR\",\"IJG.IR\"," +
            "\"IPDC.IR\",\"IR5B.IR\",\"IRES.IR\",\"IETF.IR\",\"KJY.IR\",\"JEV.IR\",\"KRZ.IR\",\"KRX.IR\",\"MSTY.IR\",\"MLC.IR\",\"3MP.IR\"," +
            "\"MIO.IR\",\"OIZ.IR\",\"ORQ.IR\",\"OVXA.IR\",\"PLS.IR\",\"IL0.IR\",\"EG5A.IR\",\"P8ET.IR\",\"OKRA.IR\",\"PZQA.IR\"," +
            "\"RY4B.IR\",\"SK3.IR\",\"TCO.IR\",\"T7O.IR\",\"TQW.IR\",\"MDY.IR\",\"ZAZ.IR\"";

    public static final Map<String, String> myFoolsMapping;
    static
    {
        myFoolsMapping= new HashMap<String, String>();
        myFoolsMapping.put("1", "Buy");
        myFoolsMapping.put("2", "Look to Buy");
        myFoolsMapping.put("3", "Watch (or \"hold\")");
        myFoolsMapping.put("4", "Look to sell");
        myFoolsMapping.put("5", "Consider shorting");
        myFoolsMapping.put("6", "Short");
    }


    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
    public static final String BOTH = "BOTH";

    public static final String FAVOURITES = "Favourites";
    public static final String CHART_VALUES = "Chart Values";

    public static final String[] ISEQ_DIALOG_LIST_DATA = new String[]{"Name: ","Symbol: ","Current Price: ","Change Percent: ","Days Low: ","Days High: ","Years Low: ","Years High: ","Volumes: ","Last Trade Time: "};
    public static final String[] HISTORY_DIALOG_LIST_DATA = new String[]{"Ticker ID: ","Date of Purchase: ","Number of Stocks Bought: ","Cost of Purchase: ","Current Value: ","Current Individual Value: "};


}




