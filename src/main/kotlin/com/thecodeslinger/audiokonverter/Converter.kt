package com.thecodeslinger.audiokonverter

import java.io.File

/**
 * Takes a single file, parses the filename to extract the tags and creates the
 * output filename to which the compressed audio shall be written. Then the source
 * file is encoded to the compressed audio.
 *
 * Any intermediate folders are created if the do not exist already. Already existing
 * files will be skipped.
 */
class Converter(private val encoder: Encoder, private val fileNamer: FileNamer) {
    
    /**
     * Convert a single file to compressed audio.
     */
    fun convert(sourceFile: File) {
        val tags = fileNamer.parse(sourceFile)
        val outFile = fileNamer.createOutputFile(tags)
        if (!outFile.exists()) {
            val path = File(outFile.parent)
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    println("ERROR Create output dir ${path.absolutePath} failed ")
                    return
                }
            }
            
            encoder.encode(sourceFile, outFile)
            println("Converted ${sourceFile.absolutePath}")
        }
    }
}