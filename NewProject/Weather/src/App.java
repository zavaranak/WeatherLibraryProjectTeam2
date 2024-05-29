import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        String apiKey = "gyqo0sonhmcyjhlyh2mejjkypn14e38jappjg19h"; // APIKEY
        Library weatherLibrary = new Library(apiKey);

        try {
            String temperature = weatherLibrary.GetTemperatureByDate("Tomsk", LocalDate.of(2024, 6, 1));
            System.out.println("Daily Temperature : " + temperature);


            String hourlyTemperature = weatherLibrary.GetTemperatureByHour("Tomsk", LocalDate.now(), 23);
            System.out.println("Hourly Temperature  " + hourlyTemperature);

            String hourlyCloud = weatherLibrary.GetCloudByHour("Tomsk", LocalDate.now(), 23);
            System.out.println("Hourly Cloud-cover :" + hourlyCloud);

            String cloud = weatherLibrary.GetCloudByDate("Tomsk", LocalDate.of(2024, 6, 1));
            System.out.println("Daily Cloud-cover : " + cloud);

            String hourlyWind = weatherLibrary.GetWindByHour("Tomsk", LocalDate.now(), 23);
            System.out.println("Hourly Wind-speed :" + hourlyWind);

            String wind = weatherLibrary.GetWindByDate("Tomsk", LocalDate.of(2024, 6, 1));
            System.out.println("Daily Wind-speed : " + wind);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
