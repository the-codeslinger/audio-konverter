package com.thecodeslinger.audiokonverter

import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.id3.ID3v23Tag
import org.jaudiotagger.tag.images.StandardArtwork
import java.io.File


/**
 * Writes the [Tags] to a compressed file.
 *
 * *Note:* Only supports mp3 at the moment.
 */
class Tagger {
    
    /**
     * Writes the tags and the cover file if any exists.
     */
    fun writeTags(tags: Tags, output: File) {
        // audioFile, audiophile... you understand?
        val audioFile = AudioFileIO.read(output)
        when (audioFile) {
            is MP3File -> writeID3Tags(tags, audioFile)
            else -> throw UnsupportedOperationException("Audio file type not supported for tagging")
        }
        audioFile.commit()
    }
    
    private fun writeID3Tags(tags: Tags, mp3File: MP3File) {
        val id3Tag = ID3v23Tag()
        with(id3Tag) {
            setField(FieldKey.ARTIST, tags.artist)
            setField(FieldKey.ALBUM, tags.album)
            setField(FieldKey.GENRE, tags.genre)
            setField(FieldKey.YEAR, tags.year)
            setField(FieldKey.TRACK, tags.track)
            setField(FieldKey.TITLE, tags.title)
            
            tags.cover?.let {
                val cover = StandardArtwork()
                cover.setFromFile(it)
                setField(cover)
            }
        }
        mp3File.setID3v2Tag(id3Tag)
    }
}