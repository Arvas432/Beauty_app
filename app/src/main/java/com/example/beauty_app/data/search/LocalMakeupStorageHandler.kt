

interface LocalMakeupStorageHandler {
    fun write(input: MakeupItem)
    fun clear()
    fun read(): List<MakeupItem>
}