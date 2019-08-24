package com.thecodeslinger.audiokonverter

/**
 * Contains all supported tags that are written to a compressed audio file.
 *
 * For simplicity all tags are treated as a string. They are merely passed around
 * and used for any computation.
 */
data class Tags(
    var artist: String = "",
    var album: String = "",
    var genre: String = "",
    var year: String = "",
    var track: String = "",
    var title: String = "")
