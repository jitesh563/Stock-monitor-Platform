package com.stock;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockService {

    @Autowired
    private RestTemplate restTemplate;

    private final String ALPHA_VANTAGE_API_KEY = "YOUR_ALPHA_VANTAGE_API_KEY";

    public BigDecimal getLatestStockPrice(String symbol) {
        String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="
                + symbol + "&interval=5min&apikey=" + ALPHA_VANTAGE_API_KEY;

        try {
            // Call Alpha Vantage API to get stock data
            Map<String, Map<String, Map<String, String>>> response = restTemplate.getForObject(apiUrl, Map.class);
            
            // Extract latest stock price from the response
            if (response != null && response.containsKey("Time Series (5min)")) {
                Map<String, Map<String, String>> timeSeriesData = response.get("Time Series (5min)");
                String latestTimestamp = timeSeriesData.keySet().iterator().next();
                String latestPrice = timeSeriesData.get(latestTimestamp).get("4. close");
                return new BigDecimal(latestPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}