package com.thecodeslinger.audiokonverter

/**
 * Kotlin representation of the JSON structure to configure an encoder.
 */
data class EncoderConfig(
    val args: List<String>,
    val bin: String,
    val ext: String)

/**
 * Kotlin representation of the JSON structure to configure the ordering of tags
 * encoded in an input file's filename.
 */
data class FormatConfig(
    val artist: Int?,
    val album: Int?,
    val genre: Int?,
    val year: Int?,
    val track: Int?,
    val title: Int?)

/**
 * Kotlin representation of the JSON structure to configure the scanning and input
 * filename format properties.
 */
data class InputConfig(
    val path: String,
    val delim: String,
    val cover: String,
    val format: FormatConfig)

/**
 * Kotlin representation of the JSON structure to configure the output file properties.
 */
data class OutputConfig(
    val path: String,
    val format: String)

/**
 * Kotlin representation of the JSON structure that represents the complete application
 * configuration.
 */
data class AppConfig(
    val input: InputConfig,
    val output: OutputConfig)