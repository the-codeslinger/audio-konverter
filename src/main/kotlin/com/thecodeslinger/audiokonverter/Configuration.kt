package com.thecodeslinger.audiokonverter

data class ConverterConfig(
    val args: List<String>,
    val bin: String,
    val ext: String)

data class FormatConfig(
    val artist: Int?,
    val album: Int?,
    val genre: Int?,
    val year: Int?,
    val track: Int?,
    val title: Int?)

data class InputConfig(
    val path: String,
    val delim: String,
    val cover: String,
    val format: FormatConfig)

data class OutputConfig(
    val path: String,
    val format: String)

data class AppConfig(
    val input: InputConfig,
    val output: OutputConfig)