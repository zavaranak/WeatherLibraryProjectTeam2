import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Temperature extends WeatherDataFetcher {
    private static final ObjectMapper mapper = new ObjectMapper();
    public Temperature(String apiKey) {
        super(apiKey);
    }

    protected String parseDataFromResponseByHour(String response, LocalDate date, int hour) throws IOException {
        JsonNode root = mapper.readTree(response);
        JsonNode hourlyData = root.path("hourly").path("data");

        // Debugging output
        System.out.println("Hourly Data: " + hourlyData.toString());

        if (hourlyData.isMissingNode()) {
            return "No hourly field in data";
        }

        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String hourString = String.format("%sT%02d:00:00", dateString, hour);

        for (JsonNode hourData : hourlyData) {
            String dataDate = hourData.path("date").asText();
            if (dataDate.equals(hourString)) {
                JsonNode temperatureNode = hourData.path("temperature");
                if (temperatureNode.isMissingNode()) {
                    return "No temperature data available";
                }
                return temperatureNode.asText() + " ℃";
            }
        }
        return "No data for the specified hour: " + hour;
    }

    // Method to parse average temperature data from response by date
    protected String parseDataFromResponseByDate(String response, LocalDate date) throws IOException {
        JsonNode root = mapper.readTree(response);
        JsonNode dailyData = root.path("daily").path("data");

        if (dailyData.isMissingNode()) {
            return "No daily field in data";
        }

        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

        for (JsonNode dayData : dailyData) {
            String dataDate = dayData.path("day").asText();
            if (dataDate.equals(dateString)) {
                double temperatureMin = dayData.path("temperature_min").asDouble();
                double temperatureMax = dayData.path("temperature_max").asDouble();
                double averageTemperature = (temperatureMin + temperatureMax) / 2.0;
                return "Average temperature for " + dateString + ": " + averageTemperature + " °C";
            }
        }

        return "No data for the specified date: " + dateString;
    }
}
