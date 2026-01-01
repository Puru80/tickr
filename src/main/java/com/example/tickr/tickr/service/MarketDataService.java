package com.example.tickr.tickr.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.Instrument;
import com.zerodhatech.models.Quote;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MarketDataService {

//    private final KiteConnect kiteConnect;
    private final KiteConnect kiteConnect;

    @Autowired
    public MarketDataService(KiteConnect kiteConnect) {
        this.kiteConnect = kiteConnect;
    }

    public String fetchMarketData() {
        return kiteConnect.getApiKey();
    }

    public void fetchInstruments() {
        try {
            List<Instrument> instruments = kiteConnect.getInstruments();
            System.out.println("Number of instruments fetched: " + instruments.size());
        } catch (IOException | KiteException e) {
            e.printStackTrace();
        }
    }

    /** Get historical data for an instrument.*/
    public void getHistoricalData() {
        /** Get historical data dump, requires from and to date, intrument token, interval, continuous (for expired F&O contracts), oi (open interest)
         * returns historical data object which will have list of historical data inside the object.*/
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date from = new Date();
            Date to = new Date();
            try {
                from = formatter.parse("2025-12-15 09:15:00");
                to = formatter.parse("2025-12-19 15:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            HistoricalData historicalData = kiteConnect.getHistoricalData(from, to, "BSE:INFY", "15minute", false, true);
            System.out.println(historicalData.dataArrayList.size());
            System.out.println(historicalData.dataArrayList.get(0).volume);
            System.out.println(historicalData.dataArrayList.get(historicalData.dataArrayList.size() - 1).volume);
            System.out.println(historicalData.dataArrayList.get(0).oi);
        } catch (Exception | KiteException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void getQuote() throws KiteException, IOException {
        // Get quotes returns quote for desired tradingsymbol.
        String[] instruments = {"256265","BSE:INFY", "NSE:APOLLOTYRE", "NSE:NIFTY 50", "24507906"};
        Map<String, Quote> quotes = kiteConnect.getQuote(instruments);
        System.out.println(quotes.get("NSE:APOLLOTYRE").instrumentToken+"");
        System.out.println(quotes.get("NSE:APOLLOTYRE").oi +"");
        System.out.println(quotes.get("NSE:APOLLOTYRE").depth.buy.get(4).getPrice());
        System.out.println(quotes.get("NSE:APOLLOTYRE").timestamp);
        System.out.println(quotes.get("NSE:APOLLOTYRE").lowerCircuitLimit+"");
        System.out.println(quotes.get("NSE:APOLLOTYRE").upperCircuitLimit+"");
        System.out.println(quotes.get("24507906").oiDayHigh);
        System.out.println(quotes.get("24507906").oiDayLow);
    }



}
