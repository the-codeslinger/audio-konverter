package com.thecodeslinger.audiokonverter

import java.io.File

/**
 * Utility class that parsed the input file's filename to extract tags and that
 * creates the output filename.
 */
class FileNamer(
    private val inputConfig: InputConfig,
    private val outputConfig: OutputConfig,
    private val outExt: String) {
    
    /**
     * Parse the file's filename and extract its components into tags according to
     * the configuration.
     */
    fun parse(file: File) : Tags {
        val tags = Tags()
        val filename = file.nameWithoutExtension
        val pieces = filename.split(inputConfig.delim)
    
        inputConfig.format.artist?.let {
            tags.artist = replaceEncodeChars(pieces[it])
        }
        inputConfig.format.album?.let {
            tags.album = replaceEncodeChars(pieces[it])
        }
        inputConfig.format.genre?.let {
            tags.genre = replaceEncodeChars(pieces[it])
        }
        inputConfig.format.title?.let {
            tags.title = replaceEncodeChars(pieces[it])
        }
        inputConfig.format.year?.let {
            tags.year = pieces[it]
        }
        inputConfig.format.track?.let {
            tags.track = pieces[it]
        }
        return tags
    }
    
    /**
     * Create an output filename based on the tags and the configured output string.
     */
    fun createOutputFile(tags: Tags) : File {
        val formatted = outputConfig.format
            .replace("%artist%", replaceForbiddenChars(tags.artist))
            .replace("%album%", replaceForbiddenChars(tags.album))
            .replace("%genre%", replaceForbiddenChars(tags.genre))
            .replace("%year%", tags.year)
            .replace("%track%", tags.track)
            .replace("%title%", replaceForbiddenChars(tags.title))
        return if (outputConfig.path.endsWith(File.separator)) {
            File(outputConfig.path + formatted + "." + outExt)
        }
        else {
            File(outputConfig.path + File.separator + formatted + "." + outExt)
        }
    }
    
    /**
     * Replaces the encoded values:
     * * &47; -> /
     * * &58; -> :
     * * &63; -> ?
     * * &92; -> \
     */
    private fun replaceEncodeChars(value: String) : String {
        return value.replace("&47;", "/").replace("&58;", ":").replace("&63;", "?").replace("&92;", "\\")
    }
    
    /**
     * Replaces the values that are not supported on (NTFS and in parts other) filesystems with "_".
     */
    private fun replaceForbiddenChars(value: String) : String {
        return value.replace("/", "_").replace(":", "_").replace("?", "_").replace("\\", "_")
    }
}