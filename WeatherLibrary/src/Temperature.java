import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Temperature {
    private static final String API_KEY = "3fcdc8ab99mshf72fc8dffc29853p1fd35djsn173bbc2f6043";
    private static final String API_HOST = "ai-weather-by-meteosource.p.rapidapi.com";

    // Find place ID by place name
    public String findPlaceId(String placeName) throws IOException, InterruptedException {
        String url = "https://ai-weather-by-meteosource.p.rapidapi.com/find_places?text=" + placeName + "&language=en";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Get temperature data by place ID and hour
    public String getTemperatureByHour(String placeId, int hour) throws IOException, InterruptedException {
        String url = "https://ai-weather-by-meteosource.p.rapidapi.com/hourly?place_id=" + placeId + "&timezone=auto&language=en&units=auto";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return parseTemperatureFromResponse(response.body(), hour);
    }

    // Parse temperature from the response based on the given hour
    private String parseTemperatureFromResponse(String response, int hour) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = today.format(formatter);

        String key = "\"hourly\":";
        int startIndex = response.indexOf(key) + key.length();
        if (startIndex == -1 + key.length()) { // -1 means not found
            return "No hourly field in data"; // No hourly field in data
        }

        // Find the specific hour data
        String dataKey = "\"data\":";
        int dataStartIndex = response.indexOf(dataKey, startIndex) + dataKey.length();
        if (dataStartIndex == -1 + dataKey.length()) { // -1 means not found
            return "No data field in hourly"; // No data field in hourly
        }

        // Extract data array as string
        int dataEndIndex = response.indexOf("]", dataStartIndex) + 1; // include the closing bracket
        String dataArray = response.substring(dataStartIndex, dataEndIndex);

        // Find the specific hour object in the array
        String hourKey = String.format("\"date\":\"%sT%02d:00:00\"", date, hour); // format date and hour
        int hourStartIndex = dataArray.indexOf(hourKey);
        if (hourStartIndex == -1) {
            return String.format("No data for the specified hour: %d", hour); // No data for the specified hour
        }

        // Extract temperature for the specified hour
        String tempKey = "\"temperature\":";
        int tempStartIndex = dataArray.indexOf(tempKey, hourStartIndex) + tempKey.length();
        if (tempStartIndex == -1 + tempKey.length()) { // -1 means not found
            return " No temperature field in the hour data"; // No temperature field in the hour data
        }
        int tempEndIndex = dataArray.indexOf(",", tempStartIndex);
        if (tempEndIndex == -1) {
            tempEndIndex = dataArray.indexOf("}", tempStartIndex);
        }
        String temperature = dataArray.substring(tempStartIndex, tempEndIndex).trim();
        return temperature + " °C";
    }
}
