package eu.szwiec.checkittravelkit.repository

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import eu.szwiec.checkittravelkit.repository.data.Country
import eu.szwiec.checkittravelkit.repository.data.Currency
import eu.szwiec.checkittravelkit.repository.data.Electricity
import eu.szwiec.checkittravelkit.repository.data.Telephones
import eu.szwiec.checkittravelkit.util.NonNullLiveData

class MockCountryRepository : CountryRepository {
    override fun setup() {
    }

    override fun getCountryNames(): LiveData<List<String>> {
        return NonNullLiveData(listOf("Poland", "UK"))
    }

    override fun getCountry(name: String): LiveData<Country> {
        val countryLD = MutableLiveData<Country>()

        val country = Country(
                id = "PL",
                name = "Poland",
                timezone = "Europe/Warsaw",
                tapWater = "safe",
                vaccinations = mapOf("Hepatitis B" to "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you"),
                imageUrl = "https://www.dropbox.com/s/5b06v8pgg4ifxy0/poland.jpg?dl=1",
                visa = "Not required",
                electricity = Electricity(
                        voltage = "230",
                        frequency = "50",
                        plugs = listOf("C", "E")
                ),
                telephones = Telephones(
                        policeNumber = "112",
                        ambulanceNumber = "112",
                        prefix = "48"
                ),
                currency = Currency(
                        code = "PLN",
                        name = "Polish zloty",
                        symbol = "zł",
                        exchangeRate = 0.2F
                )
        )

        Handler().postDelayed({ countryLD.postValue(country) }, 500)

        return countryLD
    }

    override fun getOriginCurrencyCode(): LiveData<String> {
        val code = MutableLiveData<String>()
        code.postValue("GBP")
        return code
    }
}