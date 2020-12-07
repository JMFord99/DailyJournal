package com.example.dailyjournal

import android.content.Context
import android.content.Intent
import java.io.*
import java.time.LocalDate

class Prompts {
    private lateinit var file: File
    private val filename = "pastPrompts.txt"

    //class prompt {
        val p: String
        val d: LocalDate

        internal constructor(date: LocalDate, prompt: Array<String>?) {
            this.p = prompt.toString()
            this.d = date
        }

        fun getDate(): LocalDate {
            return d
        }

        fun getPrompt(): String {
            return p
        }
    //}


    var created = false
    //var list: MutableList<prompt> = ArrayList()
    lateinit var intent : Intent

   // fun getPrompt(date: LocalDate): String {
       // if(list.isEmpty())
           // createList()

        //for(p in list) {
            //if(date.equals(p.getDate()))
            //return p.getPrompt()
    //    }
    //    return "What color do you wear most and why?"
    //}


    //getter for file
    fun get(date: LocalDate, context: Context): Prompts {
        intent = getIntent(context)
        val prompt = Prompts(date, intent.getStringArrayExtra(date.toString()))
        return prompt
    }

    // saver for file
    fun save(prompt: Prompts, context: Context) {
        //intent = getIntent(context)
        //intent.putExtra(prompt.getDate().toString(), prompt.getPrompt())

        //val filePath = context.filesDir.absolutePath

        //file = File("$filePath/filename")
        val fos: FileOutputStream =
            context.openFileOutput(file.toString(), Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(intent)
        os.close()
        fos.close()
    }

    //helper
    fun getIntent(context: Context): Intent {
        //val filePath = context.filesDir.absolutePath
        //file = File("$filePath/filename")

        val fis: FileInputStream = context.openFileInput("pastPrompts")
        val ist = ObjectInputStream(fis)
        intent = ist.readObject() as Intent
        ist.close()
        fis.close()
        return intent
    }

    /*fun createList() {
        var date = LocalDate.now()

        fun readFileAsLinesUsingReadLines(fileName: String): List<String> = File(fileName).readLines()
        //var lines = readFileAsLinesUsingReadLines("")

        val lines = arrayOf("Favorite Color", "Favorite Drink", "Favorite Food")
        //lines[0] = "Fave Color"
        var i = 0.toLong()
        //for(line in lines) {
         //   var a = prompt(date.plusDays(i), line) as prompt
         //   list.add(a)
         //   i++
        }*/


    }

