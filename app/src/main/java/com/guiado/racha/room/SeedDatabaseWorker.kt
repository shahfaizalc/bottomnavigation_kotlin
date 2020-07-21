/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guiado.racha.room

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guiado.racha.firebase.FirbaseReadHandler
import com.guiado.racha.firebase.UseInfoGeneralResultListener
import kotlinx.coroutines.*

class SeedDatabaseWorker(
        context: Context,
        workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result =
        coroutineScope {
            try {

                FirbaseReadHandler().getProfile("", object : UseInfoGeneralResultListener {

                    override fun onSuccess(userInfoGeneral: ArrayList<Feed>) {

                        GlobalScope.launch (Dispatchers.Main) {
                            val database = AppDatabase.getInstance(applicationContext)
                            database.plantDao().insertAll(userInfoGeneral)
                            Result.success()
                        }
                    }
                    override fun onFailure(e: Exception) {
                        Result.failure()
                    }
                })

                Result.success()

            } catch (ex: Exception) {
                Log.e(TAG, "Error seeding database", ex)
                Result.failure()
            }
        }


    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}