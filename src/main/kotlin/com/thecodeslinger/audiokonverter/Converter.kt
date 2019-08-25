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
class Converter(
    private val inputConfig: InputConfig,
    private val encoder: Encoder,
    private val fileNamer: FileNamer,
    private val tagger: Tagger) {
    
    /**
     * Convert a single file to compressed audio.
     */
    fun convert(input: File) {
        val tags = fileNamer.parse(input)
        val cover = File(input.parent + File.separator + inputConfig.cover)
        if (cover.exists()) {
            tags.cover = cover
        }
        
        val output = fileNamer.createOutputFile(tags)
        if (!output.exists()) {
            val path = File(output.parent)
            if (!path.exists()) {
                if (!path.mkdirs()) {
                    println("ERROR Create output dir ${path.absolutePath} failed ")
                    return
                }
            }
            
            encoder.encode(input, output)
            tagger.writeTags(tags, output)
            println("Converted ${input.absolutePath}")
        }
    }
}