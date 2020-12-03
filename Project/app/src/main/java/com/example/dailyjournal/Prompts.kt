package com.example.dailyjournal

import java.io.File
import java.io.InputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.example.dailyjournal.*

class Prompts {


    class prompt {
        val p: String
        val d: LocalDate

        internal constructor(date: LocalDate, prompt: String) {
            this.p = prompt
            this.d = date
        }

        fun getDate(): LocalDate {
            return d
        }

        fun getPrompt(): String {
            return p
        }
    }


    var created = false
    var list: MutableList<prompt> = ArrayList()

    fun getPrompt(date: LocalDate): String {
        if(list.isEmpty())
            createList()

        for(p in list) {
            if(date.equals(p.getDate()))
                return p.getPrompt()
        }
        return "What color do you wear most and why?"
    }

    fun createList() {
        var date = LocalDate.now()

        fun readFileAsLinesUsingReadLines(fileName: String): List<String> = File(fileName).readLines()
        //var lines = readFileAsLinesUsingReadLines("")

        val lines = arrayOf("Favorite Color", "Favorite Drink", "Favorite Food")
        //lines[0] = "Fave Color"
        var i = 0.toLong()
        for(line in lines) {
            var a = prompt(date.plusDays(i), line) as prompt
            list.add(a)
            i++
        }


    }

}