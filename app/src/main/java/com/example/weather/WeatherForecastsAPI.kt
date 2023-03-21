package com.example.weather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherForecastsAPI {
    @Headers("accept: application/json",
        "authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjJmNGJlZmIyNDFiZWVmYjhlMjcwNDc3YjM2NTMzYjc2MzI4NjUwNmE2NTQ3ZWRkNDExNmQ5NTVlZTllNTk0Yjc1MGYwZTAxMTdmZjJmZmM2In0.eyJhdWQiOiIyIiwianRpIjoiMmY0YmVmYjI0MWJlZWZiOGUyNzA0NzdiMzY1MzNiNzYzMjg2NTA2YTY1NDdlZGQ0MTE2ZDk1NWVlOWU1OTRiNzUwZjBlMDExN2ZmMmZmYzYiLCJpYXQiOjE2Nzg5Nzk2ODQsIm5iZiI6MTY3ODk3OTY4NCwiZXhwIjoxNzEwNjAyMDg0LCJzdWIiOiIyNDgxIiwic2NvcGVzIjpbXX0.UfU6otSt4fpC4jL7-Pnc0xEwNk59NTkWBF7AyzcbsMNc8YL_xHvqiJ-cgE-zVajC0oWObYZorJlbDxNgdZIYjNQ4T2h3NKuNVpaGdhZ6Oo7AAagqkaYH_CCPnkf0m3jn9ueqSDMy0ou1Ervm7C6-1LNVxkeBaruEb8v55J_bk4d7SDZ9PHJyoI2ozbNxtpDwx-90wXQ9OzMI32U7_JtUbE8vjgPEaNo782X-PYGujLvLN-_efF9YDwhFBn2Z4t8fJJxPAWHKo4OQx4FMDIBjTeMfeTOCrPH7R0TxVabpxhMctSjg69fMKRB_hRTu91LbbFE8H31-uQBI5GmZyqdipmCJw1sm3UzoHg3KdwA4RGqSoXjm__aaLxHChPDGTZhd4U4ZutJBqlpnP0icwbF0rTSc9eHngLceFdYomdXjKaAtvQ17rVRWzSD8N98bkPo5bYjHmHmTpa4xx8pmVfFYdeDX0ZrO8kujyVKWTVi9nvf_CPZX2L9bZBHq6fj1Xf4O5jMNwRL3FK53G8xJlL9HXm6ONNzBIwOw-Q3KxIEcxxmACKHx0ffWgoPnsIlk-8ZFV0hjyOh86R2y2uShrXcfYiZ-5y9EyGX3WrtNvZRbJyB4TJ_CDhBe8CAojgVvXIKyyj1oL6yKeQkwnP2AxHB1wxVt93STAG7-AR9onKJ1ktg")
    @GET("nwpapi/v1/forecast/location/hourly//at")
    fun getWeatherForecastsHourly(@Query("lat") lat : Double,
                 @Query("lon") lon : Double,
                 @Query("fields") fields: String,
                 @Query("date") date: String,
                 @Query("hour") hour: Int,
                 @Query("duration") duration: Int )
            : Call<WeatherForecastsHourly>
}