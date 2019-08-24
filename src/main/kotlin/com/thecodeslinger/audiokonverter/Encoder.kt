package com.thecodeslinger.audiokonverter

import java.io.File

/**
 * Calls the configured external utility to compress the input audio file.
 */
class Encoder(private val config: EncoderConfig) {
    
    /**
     * Start the encoding process.
     */
    fun encode(input: File, output: File) {
        val args = prepareArgs(input, output)
        val process = ProcessBuilder(args)
            .redirectOutput(ProcessBuilder.Redirect.DISCARD)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
        
        if (0 != process.waitFor()) {
            println("ERROR ${input.absolutePath}")
        }
    }
    
    /**
     * Prepares the encoder arguments by replacing `%input%` and `%output%` and returning
     * a new list with the arguments that can be used to pass to the encoder.
     */
    private fun prepareArgs(input: File, output: File) : List<String> {
        return config.args
            .map { makeArg(it, input, output) }
            .toCollection(mutableListOf(config.bin))
    }
    
    /**
     * Replaces `%input%` and `%output%` and returns the proper value. Everything
     * else is returned as is.
     */
    private fun makeArg(value: String, input: File, output: File) : String {
        return when (value) {
            "%input%" -> "\"${input.absolutePath}\""
            "%output%" -> "\"${output.absolutePath}\""
            else -> value
        }
    }
}