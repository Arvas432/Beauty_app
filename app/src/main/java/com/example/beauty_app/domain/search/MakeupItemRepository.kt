
interface MakeupItemRepository {
    fun searchTracks(expression: String): List<MakeupItem>
}