package com.thecodeslinger.audiokonverter

import java.io.File

/**
 * Recursively scan the configured folder and convert any found file to a compressed
 * audio format.
 *
 * Note that a certain file hygiene is required or else the process will fail
 * unexpectedly. Other than an album cover and audio files nothing else should
 * reside in the folders that are scanned.
 */
class Scanner(private val config: InputConfig, private val converter: Converter) {
    
    /**
     * Execute the recursive scan.
     */
    fun run() {
        val dir = File(config.path)
        dir.walkTopDown().filter(::isEligibleFile).forEach(converter::convert)
    }
    
    /**
     * Return `true` if `file` is not a directory and its name does not match the
     * configured name of the album cover file.
     */
    private fun isEligibleFile(file: File) : Boolean {
        return file.isFile && file.name != config.cover
    }
}