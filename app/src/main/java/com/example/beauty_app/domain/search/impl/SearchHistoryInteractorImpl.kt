

class SearchHistoryInteractorImpl(private val repository: SearchHistoryRepository): SearchHistoryInteractor {
    override fun write(input: MakeupItem) {
        repository.write(input)
    }

    override fun clear() {
        repository.clear()
    }

    override fun read(): List<MakeupItem> {
        return repository.read()
    }
}