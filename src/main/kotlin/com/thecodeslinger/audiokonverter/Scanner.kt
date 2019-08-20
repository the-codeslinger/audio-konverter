package com.thecodeslinger.audiokonverter

import java.io.File
import java.nio.file.Paths

class Scanner(
    private val config: InputConfig,
    private val converter: Converter,
    private val fileNamer: FileNamer) {
    
    fun run() {
        val dir = File(config.path)
        dir.walkTopDown().forEach { sourceFile ->
            if (sourceFile.isFile && sourceFile.name != config.cover) {
                val tags = fileNamer.parse(sourceFile)
                val outFile = fileNamer.createOutputFile(tags)
                if (!outFile.exists()) {
                    val path = File(outFile.parent)
                    if (!path.exists()) {
                        if (!path.mkdirs()) {
                            println("ERROR Create output dir ${path.absolutePath} failed ")
                        }
                    }
                    
                    converter.convert(sourceFile, outFile)
                    println("Converted ${sourceFile.absolutePath}")
                }
            }
        }
    }
}