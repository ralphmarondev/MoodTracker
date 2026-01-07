package com.ralphmarondev.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MoodTrackerApplication

fun main(args: Array<String>) {
	runApplication<MoodTrackerApplication>(*args)
}
