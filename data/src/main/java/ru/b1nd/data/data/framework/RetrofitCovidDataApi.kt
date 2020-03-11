package ru.b1nd.data.data.framework

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitCovidDataApi {

    @GET("master/csse_covid_19_data/csse_covid_19_daily_reports/{fileName}")
    suspend fun covidDailyData(
        @Path("fileName") fileName: String
    ): ResponseBody
}