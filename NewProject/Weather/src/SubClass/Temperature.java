package SubClass;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Temperature extends WeatherObject {
    public static final ObjectMapper mapper = new ObjectMapper();

    public Temperature(String apiKey) {
        super(apiKey);
    }

    @Override
    public String parseDataFromResponseByHour(String response, LocalDate date, int hour) throws IOException {
        JsonNode root = mapper.readTree(response);
        JsonNode hourlyData = root.path("hourly").path("data");

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
                return temperatureNode.asText();
            }
        }
        return "No data for the specified hour: " + hour;
    }

    @Override
    public String parseDataFromResponseByDate(String response, LocalDate date) throws IOException {
        JsonNode root = mapper.readTree(response);
        JsonNode dailyData = root.path("daily").path("data");

        if (dailyData.isMissingNode()) {
            return "No daily field in data";
        }

        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

        for (JsonNode dayData : dailyData) {
            String dataDate = dayData.path("day").asText();
            if (dataDate.equals(dateString)) {
<<<<<<< HEAD
                double temperature = dayData.path("all_day").path("temperature").asDouble();
                return "" + temperature;
=======
                JsonNode temperature = dayData.path("all_day").path("temperature");
                return temperature.asText();
>>>>>>> e677bec52445aed7f56a5c7bbe00aed61b0b78ee
            }
        }

        return "No data for the specified date: " + dateString;
    }
}
