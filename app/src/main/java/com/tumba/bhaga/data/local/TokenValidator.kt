package com.tumba.bhaga.data.local

import com.tumba.bhaga.data.remote.FinnhubApi
import javax.inject.Inject

class TokenValidator @Inject constructor(
    private val api: FinnhubApi
) {
    suspend fun isValid(token: String): Boolean {
        val status = api.checkTokenStatus(token)
        return status != 401
    }
}