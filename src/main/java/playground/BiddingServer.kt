package playground

import java.time.Duration

data class BiddingResponse(val agency: String, val country:String, val price: Int)

class BiddingServer(val agency: String, private val requestDuration: Duration) {
  fun getBid(country:String): BiddingResponse {
    Thread.sleep(requestDuration)
    return BiddingResponse(agency, country,33)
  }

}
