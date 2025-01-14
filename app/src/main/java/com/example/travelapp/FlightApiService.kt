import com.example.travelapp.Sensitive
import com.example.travelapp.StoreData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FlightApiService {
    @GET("flights/{flightNumber}")
    fun getFlightData(
        @Header("x-apikey") apiKey: String,
        @Path("flightNumber") flightNumber: String
    ): Call<StoreData>
}
