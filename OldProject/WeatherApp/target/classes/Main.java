package com.example.weather;
import com.example.weather.*;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
public static void main(String[] args) throws IOException, InterruptedException {
String apiKey = "d4ebbd69f6msh2d01634432a7da1p17c334jsnf19543e8e410"; // 用户自己输入的API密钥
String placeName = "Tomsk";

WeatherDataFetcher[] fetchers = {
new Temperature(apiKey),
new CloudState(apiKey),
new WeatherInfo(apiKey),
new WindSpeed(apiKey)
};

for (WeatherDataFetcher fetcher : fetchers) {
String placeId = fetcher.parsePlaceIdFromResponse(fetcher.findPlaceId(placeName));

String response1 = fetcher.getWeatherDataByEndpoint(placeId, "hourly");
String data1 = fetcher.parseDataFromResponseByHour(response1, LocalDate.now(), 16);

String response2 = fetcher.getWeatherDataByEndpoint(placeId, "daily");
String data2 = fetcher.parseDataFromResponseByDate(response2, LocalDate.of(2024,5,21));


System.out.println(data1);
System.out.println(data2);
}
}
}