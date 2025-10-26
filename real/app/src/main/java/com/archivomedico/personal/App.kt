package com.archivomedico.personal

import android.app.Application
import android.util.Log
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

class App : Application() {
    companion object { private const val TAG = "AppCrashHandler" }

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val sw = StringWriter()
                throwable.printStackTrace(PrintWriter(sw))
                val trace = sw.toString()
                Log.e(TAG, "UncaughtException in thread ${thread.name}: $trace")

                try {
                    val f = File(filesDir, "last_crash.txt")
                    f.writeText("Thread: ${thread.name}\n$trace")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to write crash file", e)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in uncaught exception handler", e)
            }

            try {
                Thread.sleep(1200)
            } catch (ignored: InterruptedException) {
            }

            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(2)
        }
    }
}

