package com.sparta.StarProject.weatherApi;

import com.sparta.StarProject.domain.Location;
import com.sparta.StarProject.domain.Star;
import com.sparta.StarProject.domain.Weather;
import com.sparta.StarProject.domain.board.Board;
import com.sparta.StarProject.dto.*;
import com.sparta.StarProject.repository.LocationRepository;
import com.sparta.StarProject.repository.StarRepository;
import com.sparta.StarProject.repository.WeatherRepository;
import com.sparta.StarProject.weatherApi.accuweatherAPI.AccuWeatherApi;
import com.sparta.StarProject.weatherApi.accuweatherAPI.StarGazingCity;
import com.sparta.StarProject.weatherApi.dustApi.DustApi;
import com.sparta.StarProject.weatherApi.dustApi.DustCity;
import com.sparta.StarProject.weatherApi.moonRiseAPI.MoonAPI;
import com.sparta.StarProject.weatherApi.moonRiseAPI.MoonCity;
import com.sparta.StarProject.weatherApi.weatherAPI.WeatherApi;
import com.sparta.StarProject.weatherApi.weatherAPI.WeatherCity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class API {
    private final AccuWeatherApi accuWeatherApi;
    private final DustApi dustApi;
    private final MoonAPI moonAPI;
    private final WeatherApi weatherApi;

    private final LocationRepository locationRepository;
    private final StarRepository starRepository;
    private final WeatherRepository weatherRepository;


    public List<String> processAddress(String address){
        List<String> result = new ArrayList<>();

        for (StarGazingCity value : StarGazingCity.values()) {
            if(address.contains(value.getKorName())){
                result.add(value.getKorName());
                result.add(value.getState());
                return result;
            }
        }
        return result;
    }

    public LocationStarMoonDustDto findInfoByAddress(String address) throws Exception {
        List<String> location = processAddress(address);    //경상북도 구미시, 구미
                                                            //서울특별시 ~~, 서울

        WeatherCity weatherCity = WeatherCity.getWeatherCityByString(location.get(0));
        MoonCity moonCity = MoonCity.getMoonCityByString(location.get(0), location.get(1));
        DustCity dustCity = DustCity.getDustCityByString(location.get(1));
        StarGazingCity starGazingCity = StarGazingCity.getStarGazingCityByString(location.get(0));

        List<StarGazingDto> starGazing = accuWeatherApi.getStarGazing(starGazingCity);
        SunMoonDto moon = moonAPI.getMoon(moonCity);
        List<WeatherApiDto2> weather = weatherApi.getWeather(weatherCity);
        DustApiDto dust = dustApi.getDust(dustCity);

        LocationStarMoonDustDto result = new LocationStarMoonDustDto(
                starGazing,
                moon,
                weather,
                dust,
                address
        );
        return result;
    }

    @Transactional
    public void saveStarLocationWeather(Board board, LocationStarMoonDustDto result ) {
        List<String> location = processAddress(result.getAddress());

        Location newLocation =
                new Location(null, null, result.getAddress(), location.get(0), board);
        Location saveLocation = locationRepository.save(newLocation);

        Star newStar =
                new Star(
                    result.getMoon().getMoonrise(),
                    result.getMoon().getMoonSet(),
                    Long.valueOf(result.getStarGazing().get(0).getValue().longValue()),
                    saveLocation
                );
        Star saveStar = starRepository.save(newStar);

        for (WeatherApiDto2 weatherApiDto2 : result.getWeather()) {
            Weather newWeather =
                    new Weather(
                            weatherApiDto2.getHumidity(),
                            weatherApiDto2.getWeather(),
                            weatherApiDto2.getTemperature(),
                            weatherApiDto2.getMaxTemperature(),
                            weatherApiDto2.getMinTemperature(),
                            weatherApiDto2.getPrecipitationProbability(),
                            weatherApiDto2.getFcstTime(),
                            result.getDust().getPm10Value(),
                            weatherApiDto2.getBaseDate(),
                            saveLocation);

            Weather saveWeather = weatherRepository.save(newWeather);
        }
    }

}
