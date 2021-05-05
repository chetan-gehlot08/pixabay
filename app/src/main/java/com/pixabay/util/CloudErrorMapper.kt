package com.pixabay.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CloudErrorMapper {
    companion object {
        fun mapToDomainErrorException(throwable: Throwable?): String {
            val errorModel: String? = when (throwable) {

                // if throwable is an instance of HttpException
                // then attempt to parse error data from response body
                is HttpException -> {
                    // handle UNAUTHORIZED situation (when token expired)
                    if (throwable.code() == 401) {
                        "UNAUTHORIZED"
                    } else {
                        getHttpError(throwable.response()?.errorBody())
                    }
                }

                // handle api call timeout error
                is SocketTimeoutException -> {
                    "TIME OUT!!"
                }

                // handle connection error
                is IOException -> {
                    "CHECK YOUR INTERNET CONNECTION!!"
                }

                is UnknownHostException -> {
                    "CHECK YOUR INTERNET CONNECTION!!"
                }
                else -> null
            }
            return errorModel!!
        }

        /**
         * attempts to parse http response body and get error data from it
         *
         * @param body retrofit response body
         * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
         */
        private fun getHttpError(body: ResponseBody?): String {
            return try {
                // use response body to get error detail
                val result = body?.string()
                Log.d("getHttpError", "getErrorMessage() called with: errorBody = [$result]")
                val json = Gson().fromJson(result, JsonObject::class.java)
                json.toString()
            } catch (e: Throwable) {
                e.printStackTrace()
                "Something went wrong"
            }

        }
    }

}