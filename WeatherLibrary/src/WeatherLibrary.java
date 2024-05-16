import java.util.Scanner;
import java.io.IOException;


public class WeatherLibrary {
    public static void main(String[] args) {
        Temperature temperature = new Temperature();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter place name:"); // Prompt for place name
        String placeName = scanner.nextLine();

        System.out.println("Enter hour (0-23):"); // Prompt for hour
        int hour = scanner.nextInt();

        try {
            // Find place ID
            String placeIdResponse = temperature.findPlaceId(placeName);
            String placeId = parsePlaceIdFromResponse(placeIdResponse);
            if (placeId == null) {
                System.out.println("Place ID not found,Please check if the place name is correct"); // Place ID not found
                return;
            }

            // Get temperature data
            String temperatureData = temperature.getTemperatureByHour(placeId, hour);
            System.out.println("Temperature:"); // Temperature data
            System.out.println(temperatureData);
        } catch (IOException e) {
            System.err.println("IO exception occurred while processing the request.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("Request was interrupted.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    // Parse place ID from the response (assuming the JSON format: [{"place_id":"12345"}])
    private static String parsePlaceIdFromResponse(String response) {
        String key = "\"place_id\":\"";
        int startIndex = response.indexOf(key) + key.length();
        if (startIndex == -1 + key.length()) { // -1 means not found
            return null;
        }
        int endIndex = response.indexOf("\"", startIndex);
        return response.substring(startIndex, endIndex);
    }
}
