import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KtorApi {
    @POST("/register")
    fun register(@Body registerData: RegisterRecieveRemote): Call<RegisterResponseRemote>
    @POST("/login")
    fun login(@Body loginData: LoginRecieveRemote): Call<LoginResponseRemote>
}