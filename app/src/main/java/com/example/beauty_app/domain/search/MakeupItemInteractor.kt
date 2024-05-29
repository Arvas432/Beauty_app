

interface MakeupItemInteractor {
    fun searchTracks(expression: String, consumer: MakeupItemConsumer)
    interface MakeupItemConsumer{
        fun consume(searchResult: MakeupItemSearchResult)
    }
}