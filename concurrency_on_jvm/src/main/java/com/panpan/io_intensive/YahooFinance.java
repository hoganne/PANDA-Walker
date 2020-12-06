package com.panpan.io_intensive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/4 14:08
 * @Version V1.0
 **/
public class YahooFinance {

    public static double getPrice(final String ticker) throws IOException {
//        "http://ichart.finance.yahoo.com/table.csv?s=" + ticker
        final URL url = new URL("https://query1.finance.yahoo.com/v8/finance/chart/"+ticker+"?interval=2m");

        final BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        //Date,Open,High,Low,Close,Volume,Adj Close

        //2011-03-17,336.83,339.61,330.66,334.64,23519400,334.64

        final String discardHeader = reader.readLine();

        final String data = reader.readLine();
        final String[] dataItems = data.split(",");
        final double priceIsTheLastValue = Double.valueOf(dataItems[dataItems.length - 1]);
        return priceIsTheLastValue;

    }

}
