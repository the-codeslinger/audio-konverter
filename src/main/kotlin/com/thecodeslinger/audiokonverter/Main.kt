package com.thecodeslinger.audiokonverter

import com.beust.klaxon.Klaxon
import java.io.File

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Invalid arguments")
        return;
    }
    
    // TODO Proper input validation. File exists etc.
    
    val parser = Klaxon()
    val appConfig = parser.parse<AppConfig>(File(args[0]))
    val converterConfig = parser.parse<ConverterConfig>(File(args[1]))
    
    if (null == appConfig) {
        println("No app config")
        return
    }
    
    if (null == converterConfig) {
        println("No converter config")
        return
    }
    
    val filenameParser = FileNamer(appConfig.input, appConfig.output, converterConfig.ext)
    val converter = Converter(converterConfig)
    val scanner = Scanner(appConfig.input, converter, filenameParser)
    scanner.run()
}