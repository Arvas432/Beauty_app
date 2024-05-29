
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MakeupApi {
    @GET("products.json")
    fun search(@Query("product_type") text: String): Call<List<MakeupItemDTO>>
}