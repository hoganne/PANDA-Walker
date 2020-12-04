package com.panpan.io_intensive;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/4 14:11
 * @Version V1.0
 **/
public class SequentialNAV extends AbstractNAV {

    @Override
    public double computeNetAssetValue(

            final Map<String, Integer> stocks) throws IOException {

        double netAssetValue = 0.0;

        for(String ticker : stocks.keySet()) {

            netAssetValue += stocks.get(ticker) * YahooFinance.getPrice(ticker);
        }

        return netAssetValue;

    }

    public static void main(final String[] args)

            throws ExecutionException, IOException, InterruptedException {

        new SequentialNAV().timeAndComputeValue();

    }

}