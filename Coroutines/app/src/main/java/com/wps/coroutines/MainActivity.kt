package com.wps.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity(){

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job =   CoroutineScope(Dispatchers.IO).launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }

        MainScope().launch(Dispatchers.Main){
            delay(3500)
            println("main: I'm tired of waiting!")
            job.cancel() // cancels the job
            println("main: Now I can quit.")
        }

    }

    private fun startCoroutines(){

        GlobalScope.launch(Dispatchers.Main) {
            val userOne = async(Dispatchers.IO) {
                fetchUser()
            }
            val res = userOne.await()
            showUser(res)
            launch(Dispatchers.IO) {
                saveUser(res)
            }
        }


        CoroutineScope(Dispatchers.IO).launch{
            val res =  fetchUser()
            withContext(Dispatchers.Main) {
                showUser(res)
            }
            saveUser(res)
        }


        MainScope().launch(Dispatchers.IO){
            val deferred = listOf(
                async{ fetchUser() },
                async{ fetchAnotherUser() }
            )
            val res: List<String?> = deferred.awaitAll()
        }


        MainScope().launch(Dispatchers.Main){
            val res = fetchOneUser()
            showUser(res)
        }


        lifecycleScope.launch(Dispatchers.Main) {
            whenStarted {
                // The block inside will run only when Lifecycle is at least STARTED.
            }
        }
    }


    private suspend fun fetchUser(): String {
        delay(3000)
        return "kotlin"
    }

    private suspend fun fetchAnotherUser(): String? {
        delay(3000)
        return "kotlin"
    }

    private fun showUser(name: String){

    }

    private suspend fun saveUser(name: String){
        delay(200)
    }


    private suspend fun fetchOneUser() = withContext(Dispatchers.IO){
        fetchUser()
    }

}
