import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class WeatherDataFetcher {
    private static final String API_KEY = "d54a8e746bmsh53d2bac8cd80d71p18cd5djsne88d1b12ec3e";
    private static final String API_HOST = "ai-weather-by-meteosource.p.rapidapi.com";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    // Find place ID by place name
    public String findPlaceId(String placeName) throws IOException, InterruptedException {
        String url = "https://ai-weather-by-meteosource.p.rapidapi.com/find_places?text=" + placeName + "&language=en";
        HttpRequest request = buildRequest(url);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Get weather data by place ID and endpoint (hourly or daily)
    protected String getWeatherDataByEndpoint(String placeId, String endpoint) throws IOException, InterruptedException {
        String url = "https://ai-weather-by-meteosource.p.rapidapi.com/" + endpoint + "?place_id=" + placeId + "&timezone=auto&language=en&units=auto";
        HttpRequest request = buildRequest(url);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Build HTTP request with headers
    private HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", API_KEY)
                .header("X-RapidAPI-Host", API_HOST)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
    }

    // Parse data from the response based on the given date or hour
    protected abstract String parseDataFromResponseByHour(String response, LocalDate date, int hour) throws IOException;

    // Parse data from the response based on the given date
    protected abstract String parseDataFromResponseByDate(String response, LocalDate date) throws IOException;
}
