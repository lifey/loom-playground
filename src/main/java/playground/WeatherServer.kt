package playground

import java.time.Duration

data class WeatherResponse(val agency: String,val city:String, val temperature: Int)

class WeatherServer(val agency: String, private val requestDuration: Duration) {
  fun readWeather(city:String): WeatherResponse {
    Thread.sleep(requestDuration)
    return WeatherResponse(agency, city,33)
  }

}
