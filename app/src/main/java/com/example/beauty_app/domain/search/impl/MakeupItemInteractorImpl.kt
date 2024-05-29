
import java.util.concurrent.Executors

class MakeupItemInteractorImpl(private val repository: MakeupItemRepository): MakeupItemInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun searchTracks(expression: String, consumer: MakeupItemInteractor.MakeupItemConsumer) {
        executor.execute {
            if(expression.isNotEmpty()){
                consumer.consume(MakeupItemSearchResult(emptyList(), SearchResultType.LOADING))
                try {
                    val tracks = repository.searchTracks(expression)
                    if(tracks.isNotEmpty()){
                        consumer.consume(MakeupItemSearchResult(tracks,SearchResultType.SUCCESS))
                    }else{
                        consumer.consume(MakeupItemSearchResult(emptyList(), SearchResultType.EMPTY))
                    }
                }catch (e: Throwable){
                    e.printStackTrace()
                    consumer.consume(MakeupItemSearchResult(emptyList(), SearchResultType.ERROR))
                }

            }

        }
    }

}