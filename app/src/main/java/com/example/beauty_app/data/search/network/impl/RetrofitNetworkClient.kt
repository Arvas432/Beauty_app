import android.util.Log

class RetrofitNetworkClient(private val makeupService: MakeupApi): NetworkClient {
    override fun doRequest(dto: Any): Response {
        if(dto is MakeupAPISearchRequest){
            val resp = makeupService.search(dto.expression).execute()
            Log.i("NETWORK", resp.body().toString())
            val body = resp.body() ?: Response()
            if (resp.body()!=null){
                return MakeupApiResponse(resp.body()!!).apply { resultCode=resp.code() }
            }else{
                return Response().apply { resultCode=400 }
            }
        }else{
            return Response().apply { resultCode=400 }
        }
    }

}
