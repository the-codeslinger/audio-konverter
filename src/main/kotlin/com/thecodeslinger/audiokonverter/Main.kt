package com.thecodeslinger.audiokonverter

import com.beust.klaxon.Klaxon
import java.io.File

/**
 * Sets up the application and runs it.
 */
fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Invalid arguments")
        return;
    }
    
    // TODO Proper input validation. File exists etc.
    
    val parser = Klaxon()
    val appConfig = parser.parse<AppConfig>(File(args[0]))
    val converterConfig = parser.parse<EncoderConfig>(File(args[1]))
    
    if (null == appConfig) {
        println("No app config")
        return
    }
    
    if (null == converterConfig) {
        println("No converter config")
        return
    }
    
    val fileNamer = FileNamer(appConfig.input, appConfig.output, converterConfig.ext)
    val encoder = Encoder(converterConfig)
    val tagger = Tagger()
    val converter = Converter(appConfig.input, encoder, fileNamer, tagger)
    val scanner = Scanner(appConfig.input, converter)
    scanner.run()
}