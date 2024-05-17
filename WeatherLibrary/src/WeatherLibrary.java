import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherLibrary {
    public static void main(String[] args) {
        WeatherDataFetcher dataFetcher = null; // Generic WeatherDataFetcher reference
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter place name:"); // Prompt for place name
        String placeName = scanner.nextLine();

        try {
            // Find place ID
            String placeIdResponse = new Temperature().findPlaceId(placeName);
            String placeId = parsePlaceIdFromResponse(placeIdResponse);
            if (placeId == null) {
                System.out.println("Place ID not found, please check if the place name is correct."); // Place ID not found
                return;
            }

            System.out.println("Choose an option:");
            System.out.println("1. Get temperature by hour");
            System.out.println("2. Get temperature by date");
            System.out.println("3. Get wind speed by hour");
            System.out.println("4. Get wind speed by date");
            System.out.println("5. Get cloud_cover by hour");
            System.out.println("6. Get cloud_cover by date");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                case 2:
                    dataFetcher = new Temperature(); // Temperature data
                    break;
                case 3:
                case 4:
                    dataFetcher = new WindSpeed(); // Wind speed data
                    break;
                case 5:
                case 6:
                    dataFetcher = new CloudState();//cloud_cover data
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }

            if (dataFetcher != null) {
                handleDataFetching(dataFetcher, placeId, scanner, choice);
            }
        } catch (IOException e) {
            System.err.println("IO exception occurred while processing the request.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Request was interrupted.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Parse place ID from the response (assuming the JSON format: [{"place_id":"12345"}])
    private static String parsePlaceIdFromResponse(String response) {
        try {
            JsonNode root = new ObjectMapper().readTree(response);
            return root.get(0).path("place_id").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to handle data fetching based on user choice
    private static void handleDataFetching(WeatherDataFetcher dataFetcher, String placeId, Scanner scanner, int choice) throws IOException, InterruptedException {
        LocalDate date;
        int hour;
        String data;

        switch (choice) {
            case 1:
                System.out.println("Enter hour (0-23):"); // Prompt for hour
                hour = scanner.nextInt();
                scanner.nextLine(); // consume newline

                // Get temperature data for hour
                data = dataFetcher.getWeatherDataByEndpoint(placeId, "hourly");
                // Debugging output
                System.out.println("Response data: " + data);
                String temperatureHourlyData = dataFetcher.parseDataFromResponseByHour(data, LocalDate.now(), hour); // Ensure it uses today's date
                System.out.println("Temperature by hour:"); // Temperature by hour
                System.out.println(temperatureHourlyData);
                break;
            case 2:
                System.out.println("Enter date (YYYY-MM-DD):"); // Prompt for date
                date = LocalDate.parse(scanner.nextLine());

                // Get temperature data for date
                data = dataFetcher.getWeatherDataByEndpoint(placeId, "daily");
                // Debugging output
                System.out.println("Response data: " + data);
                String temperatureDailyData = dataFetcher.parseDataFromResponseByDate(data, date);
                System.out.println("Temperature by date:"); // Temperature by date
                System.out.println(temperatureDailyData);
                break;
            case 3:
                System.out.println("Enter hour (0-23):"); // Prompt for hour
                hour = scanner.nextInt();
                scanner.nextLine(); // consume newline

                // Get wind speed data for hour
                data = dataFetcher.getWeatherDataByEndpoint(placeId, "hourly");
                // Debugging output
                System.out.println("Response data: " + data);
                String windSpeedHourlyData = dataFetcher.parseDataFromResponseByHour(data, LocalDate.now(), hour); // Ensure it uses today's date
                System.out.println("Wind Speed by hour:"); // Wind Speed by hour
                System.out.println(windSpeedHourlyData);
                break;
            case 4:
                System.out.println("Enter date (YYYY-MM-DD):"); // Prompt for date
                date = LocalDate.parse(scanner.nextLine());

                // Get wind speed data for date
                data = dataFetcher.getWeatherDataByEndpoint(placeId, "daily");
                // Debugging output
                System.out.println("Response data: " + data);
                String windSpeedDailyData = dataFetcher.parseDataFromResponseByDate(data, date);
                System.out.println("Wind Speed by date:"); // Wind Speed by date
                System.out.println(windSpeedDailyData);
                break;
            case 5:
                System.out.println("Enter hour (0-23):"); // Prompt for hour
                hour = scanner.nextInt();
                scanner.nextLine(); // consume newline

                // Get cloud_cover data for hour
                data = dataFetcher.getWeatherDataByEndpoint(placeId, "hourly");
                // Debugging output
                System.out.println("Response data: " + data);
                String cloudCoverHourlyData = dataFetcher.parseDataFromResponseByHour(data, LocalDate.now(), hour);
                System.out.println("CloudCover by hour:"); // Temperature by hour
                System.out.println(cloudCoverHourlyData);
                break;
            case 6:
                System.out.println("Enter date (YYYY-MM-DD):"); // Prompt for date
                date = LocalDate.parse(scanner.nextLine());

                // Get cloud cover data for date
                data = dataFetcher.getWeatherDataByEndpoint(placeId, "daily");
                // Debugging output
                System.out.println("Response data: " + data);
                String cloudCoverDailyData = dataFetcher.parseDataFromResponseByDate(data, date);
                System.out.println("CloudCover by date:"); // Cloud cover by date
                System.out.println(cloudCoverDailyData);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
