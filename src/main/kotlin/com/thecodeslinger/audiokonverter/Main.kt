package com.thecodeslinger.audiokonverter

import com.beust.klaxon.Klaxon
import java.io.File

/**
 * Sets up the application and runs it.
 */
fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Invalid arguments")
        return
    }
    
    val appConfigFile = File(args[0])
    if (!appConfigFile.exists()) {
        print("App-config file not found at ${args[0]}")
        return
    }
    
    val encoderConfigFile = File(args[1])
    if (!encoderConfigFile.exists()) {
        print("Encoder-config file not found at ${args[1]}")
        return
    }
    
    val parser = Klaxon()
    val appConfig = parser.parse<AppConfig>(appConfigFile)
    val converterConfig = parser.parse<EncoderConfig>(encoderConfigFile)
    
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