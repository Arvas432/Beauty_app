


class SearchHistoryRepositoryImpl(private val localMakeupStorageHandler: LocalMakeupStorageHandler): SearchHistoryRepository {
    override fun write(input: MakeupItem) {
       localMakeupStorageHandler.write(input)
    }

    override fun clear() {
        localMakeupStorageHandler.clear()
    }

    override fun read(): List<MakeupItem> {
        return localMakeupStorageHandler.read()
    }
}