import android.util.Log

class MakeupItemRepositoryImpl(private val networkClient: NetworkClient): MakeupItemRepository {
    override fun searchTracks(expression: String): List<MakeupItem> {
        val response = networkClient.doRequest(MakeupAPISearchRequest(expression))
        if(response.resultCode == 200){
            Log.i("repository", response.toString())
            return (response as MakeupApiResponse).results.map {
                MakeupItem(
                    it.id,
                    it.name?:"Имя не найдено",
                    it.price?:0.0f,
                    it.image_link?:"",
                    it.product_link?:"",
                    it.description ?: "Описание не найдено",
                    it.rating?:"Неизвестно",
                    it.brand?:"Бренд не найден") }
        } else{
            return emptyList()
        }
    }
}