package com.tumba.bhaga.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tumba.bhaga.data.di.AppModule.api
import kotlinx.coroutines.flow.first

class TokenManager(private val context: Context) {
    private val Context.tokenDataStore by preferencesDataStore(name = "tokenDataStore")

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("api_token")
        private const val DEFAULT_TOKEN = "d2n8qthr01qn3vmk0gr0d2n8qthr01qn3vmk0grg"
    }

    suspend fun saveToken(token: String) {
        context.tokenDataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun checkToken(token: String): Boolean {
        val status = api.checkTokenStatus(token)
        return status != 401
    }

    suspend fun getToken(): String {
        val prefs = context.tokenDataStore.data.first()
        return prefs[TOKEN_KEY] ?: DEFAULT_TOKEN
    }

    suspend fun clearToken() {
        context.tokenDataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }
}
