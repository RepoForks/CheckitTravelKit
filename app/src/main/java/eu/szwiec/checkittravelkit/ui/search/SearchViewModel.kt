package eu.szwiec.checkittravelkit.ui.search

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.prefs.Preferences
import eu.szwiec.checkittravelkit.repository.CountryRepository
import eu.szwiec.checkittravelkit.util.NonNullLiveData

enum class State {
    CHOOSE_ORIGIN, CHOOSE_DESTINATION, SHOW_INFO
}

class SearchViewModel(private val resources: Resources, private val preferences: Preferences, private val repository: CountryRepository) : ViewModel() {

    val state = NonNullLiveData(State.CHOOSE_ORIGIN)
    val origin = NonNullLiveData("")
    val destination = NonNullLiveData("")
    val originHint = NonNullLiveData("")
    val countryNames = repository.getCountryNames()

    init {
        initState()
    }

    fun initState() {
        if (preferences.origin.isEmpty()) {
            setState(State.CHOOSE_ORIGIN)
        } else {
            setState(State.CHOOSE_DESTINATION)
            origin.value = preferences.origin
        }
    }

    fun setState(newState: State) {

        when (newState) {
            State.CHOOSE_ORIGIN -> {
                originHint.value = resources.getString(R.string.where_are_you_from)
            }
            State.CHOOSE_DESTINATION -> {
                originHint.value = ""
            }
            State.SHOW_INFO -> {
                destination.value = ""
            }
        }

        state.value = newState
    }

    fun submitOrigin() {
        if (isValid(origin.value)) {
            setState(State.CHOOSE_DESTINATION)
            preferences.origin = origin.value
        }
    }

    fun submitDestination() {
        if (isValid(destination.value)) {
            setState(State.SHOW_INFO)
        }
    }

    private fun isValid(countryName: String): Boolean {
        countryNames.value?.let {
            return it.contains(countryName)
        }
        return false
    }
}