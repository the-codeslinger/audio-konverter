package com.thecodeslinger.audiokonverter

import java.io.File

class Converter(private val config: ConverterConfig) {
    
    fun convert(source: File, destination: File) {
        val args = mutableListOf<String>()
        args.add(config.bin)
        config.args.forEach {
            when (it) {
                "%input%" -> args.add("\"${source.absolutePath}\"")
                "%output%" -> args.add("\"${destination.absolutePath}\"")
                else -> args.add(it)
            }
        }
        
        val process = ProcessBuilder(args)
            .redirectOutput(ProcessBuilder.Redirect.DISCARD)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
        
        if (0 != process.waitFor()) {
            println("ERROR ${source.absolutePath}")
        }
    }
}