package com.wps.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(){

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
