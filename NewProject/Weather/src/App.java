import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        String apiKey = "gyqo0sonhmcyjhlyh2mejjkypn14e38jappjg19h"; // APIKEY
        Library weatherLibrary = new Library(apiKey);

        try {
            String temperature = weatherLibrary.GetTemperatureByDate("Tomsk", LocalDate.of(2024, 6, 1));
            System.out.println("Daily Temperature : " + temperature);


            String hourlyTemperature = weatherLibrary.GetTemperatureByHour("Tomsk", LocalDate.now(), 12);
            System.out.println("Hourly Temperature  " + hourlyTemperature);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
