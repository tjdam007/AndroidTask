package com.tjdam007.androidtask

interface Callback<T> {
    fun onCallback(data: T)
}