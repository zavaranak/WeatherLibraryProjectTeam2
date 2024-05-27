import SubClass.Cloud;
import SubClass.Temperature;
import SubClass.WeatherObject;
import SubClass.Wind;

public class Library {
    private String APIkey;

    Library(String key) {
        this.APIkey = key;
    }

    private WeatherObject Forecaster;

    String GetTemperatureByDate(String placeName, int date, int month, int years) {
        this.Forecaster = new Temperature();
        return "oke";
    }

    String GetTemperatureByHour(String placeName, int hour) {
        this.Forecaster = new Temperature();
        return "oke";
    }

    String GetCloudStateByDate(String placeName, int date, int month, int years) {
        this.Forecaster = new Cloud();
        return "oke";
    }

    String GetCloudStateByHour(String placeName, int hour) {
        this.Forecaster = new Cloud();
        return "oke";
    }

    String GetWindSpeedByDate(String placeName, int date, int month, int years) {
        this.Forecaster = new Wind();
        return "oke";
    }

    String GetWindSpeedByHour(String placeName, int hour) {
        this.Forecaster = new Wind();
        return "oke";
    }

}