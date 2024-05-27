package com.example.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class WeatherInfo extends WeatherDataFetcher {
    private static final ObjectMapper mapper = new ObjectMapper();
    public WeatherInfo(String apiKey) {
        super(apiKey);
    }

    @Override
    public String parseDataFromResponseByHour(String response, LocalDate date, int hour) throws IOException {
        JsonNode root = mapper.readTree(response);
        JsonNode hourlyData = root.path("hourly").path("data");
//
//        // Debugging output
//        System.out.println("Hourly Data: " + hourlyData.toString());

        if (hourlyData.isMissingNode()) {
            return "No hourly field in data";
        }

        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String hourString = String.format("%sT%02d:00:00", dateString, hour);

        for (JsonNode hourData : hourlyData) {
            String dataDate = hourData.path("date").asText();
            if (dataDate.equals(hourString)) {
                JsonNode weatherInfoNode = hourData.path("weather");
                if (weatherInfoNode.isMissingNode()) {
                    return "No weatherInfo data available";
                }
                return weatherInfoNode.asText() ;
            }
        }
        return "No data for the specified hour: " + hour;
    }

    @Override
    public String parseDataFromResponseByDate(String response, LocalDate date) throws IOException {
        JsonNode root = mapper.readTree(response);
        JsonNode dailyData = root.path("daily").path("data");

//        // Debugging output
//        System.out.println("Daily Data: " + dailyData.toString());

        if (dailyData.isMissingNode()) {
            return "No daily field in data";
        }

        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

        for (JsonNode dayData : dailyData) {
            String dataDate = dayData.path("day").asText();
            if (dataDate.equals(dateString)) {
                JsonNode weatherInfoNode = dayData.path("weather");
                if (weatherInfoNode.isMissingNode()) {
                    return "No weatherInfo data available";
                }
                return weatherInfoNode.asText() ;
            }
        }
        return "No data for the specified date: " + dateString;
    }
}
