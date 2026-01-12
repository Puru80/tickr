package com.example.tickr.tickr.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.HistoricalData;
import com.zerodhatech.models.Instrument;
import com.zerodhatech.models.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MarketDataService {

    private final KiteConnect kiteConnect;
//    private static final String HISTORICAL_DATA_CSV = "src/main/resources/data/historical_data.csv";
    private static final String INSTRUMENT_DATA_CSV = "src/main/resources/data/instrument_data.csv";


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

            writeInstrumentInfoData(instruments);
//            return instruments;
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

    private void writeInstrumentInfoData(List<Instrument> instruments) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(INSTRUMENT_DATA_CSV))) {
            // Write header
            String[] header = { "instrument_token", "exchange_token", "trading_symbol", "name", "last_price",
                "tick_size", "instrument_type", "segment", "exchange", "strike", "lot_size", "expiry" };
            writer.writeNext(header);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Write data
            for (Instrument instrument : instruments) {
                String[] row = {
                    String.valueOf(instrument.getInstrument_token()),
                    String.valueOf(instrument.getExchange_token()),
                    instrument.getTradingsymbol(),
                    instrument.getName(),
                    String.valueOf(instrument.getLast_price()),
                    String.valueOf(instrument.getTick_size()),
                    instrument.getInstrument_type(),
                    instrument.getSegment(),
                    instrument.getExchange(),
                    instrument.getStrike(),
                    String.valueOf(instrument.getLot_size()),
                    instrument.getExpiry() != null ? formatter.format(instrument.getExpiry()) : ""
                };
                writer.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Instrument> getInstrumentDataFromCSV() {
        List<Instrument> instruments = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(INSTRUMENT_DATA_CSV))) {
            // Skip header
            reader.skip(1);
            List<String[]> allRows = reader.readAll();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (String[] row : allRows) {
                Instrument instrument = new Instrument();
                instrument.setInstrument_token(Long.parseLong(row[0]));
                instrument.setExchange_token(Long.parseLong(row[1]));
                instrument.setTradingsymbol(row[2]);
                instrument.setName(row[3]);
                instrument.setLast_price(Double.parseDouble(row[4]));
                instrument.setTick_size(Double.parseDouble(row[5]));
                instrument.setInstrument_type(row[6]);
                instrument.setSegment(row[7]);
                instrument.setExchange(row[8]);
                instrument.setStrike(row[9]);
                instrument.setLot_size(Integer.parseInt(row[10]));
                if (!row[11].isEmpty()) {
                    instrument.setExpiry(formatter.parse(row[11]));
                }

                instruments.add(instrument);
            }

        } catch (IOException | CsvException | ParseException e) {
            e.printStackTrace();
        }
        return instruments;
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
