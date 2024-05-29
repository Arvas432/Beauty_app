

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferencesLocalMakeupStorageHandler(private val sharedPreferences: SharedPreferences, private val gson: Gson):
    LocalMakeupStorageHandler {
    override fun write(input: MakeupItem){
        var currentSearchHistory = read()
        currentSearchHistory = currentSearchHistory.filter { it.id !=input.id }.toMutableList()
        if (currentSearchHistory.size==10){
            currentSearchHistory.removeAt(currentSearchHistory.lastIndex)
        }
        currentSearchHistory.add(0,input)
        clear()
        sharedPreferences
            .edit()
            .putString(SEARCH_HISTORY_KEY, gson.toJson(currentSearchHistory))
            .apply()
    }
    override fun clear(){
        sharedPreferences
            .edit()
            .remove(SEARCH_HISTORY_KEY)
            .apply()
    }
    override fun read(): List<MakeupItem> {
        val json = sharedPreferences.getString(SEARCH_HISTORY_KEY, null) ?: return emptyList()
        return gson.fromJson(json, object: TypeToken<List<MakeupItem>>() {}.type)
    }
    companion object{
        const val SEARCH_HISTORY_KEY = "search_history_key"
    }
}