package com.thecodeslinger.audiokonverter

import java.io.File

class FileNamer(
    private val inputConfig: InputConfig,
    private val outputConfig: OutputConfig,
    private val outExt: String) {
    
    fun parse(file: File) : Tags {
        val tags = Tags()
        val filename = file.nameWithoutExtension
        val pieces = filename.split(inputConfig.delim)
        
        if (null != inputConfig.format.artist) {
            tags.artist = pieces[inputConfig.format.artist]
        }
        if (null != inputConfig.format.album) {
            tags.album = pieces[inputConfig.format.album]
        }
        if (null != inputConfig.format.genre) {
            tags.genre = pieces[inputConfig.format.genre]
        }
        if (null != inputConfig.format.year) {
            tags.year = pieces[inputConfig.format.year].toInt()
        }
        if (null != inputConfig.format.track) {
            tags.track = pieces[inputConfig.format.track].toInt()
        }
        if (null != inputConfig.format.title) {
            tags.title = pieces[inputConfig.format.title]
        }
        return tags
    }
    
    fun createOutputFile(tags: Tags) : File {
        val formatted = outputConfig.format
            .replace("%artist%", tags.artist)
            .replace("%album%", tags.album)
            .replace("%genre%", tags.genre)
            .replace("%year%", tags.year.toString())
            .replace("%track%", tags.track.toString())
            .replace("%title%", tags.title)
        return if (outputConfig.path.endsWith(File.separator)) {
            File(outputConfig.path + formatted + "." + outExt)
        }
        else {
            File(outputConfig.path + File.separator + formatted + "." + outExt)
        }
    }
}