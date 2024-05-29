

sealed class SearchState{
    object Default: SearchState()
    object Loading: SearchState()
    object NetworkError: SearchState()
    object EmptyResults: SearchState()
    data class Content(val products: List<MakeupItem>): SearchState()
}
