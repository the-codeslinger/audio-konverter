# audio-konverter

A simple Kotlin (hence the "k" in converter) command line utility to convert *.wav* files to a compressed format.

This utility scans a folder for input files, compresses them according to the settings and writes the results to an output folder. It is very opinionated to meet my personal needs.

## Project Status

As of 20 Aug. 2019 this project is a Work in Progress. It will have several milestones:

1) ✓ Simple synchronous implementation to get up and running quickly. That means while scanning a directory a found file is immediately converted, blocking until conversion finishes.
2) Introduce a simple multi-threading model to improve performance.
3) Investigate and implement Kotlin Coroutines

This file will be updated accordingly.

## What it does

* Scan an input folder for *.wav files.
* Read the file name as it contains all relevant tags:
    * Artist.
    * Album title.
    * Genre.
    * Year when the album was released.
    * Track number with a leading zero.
    * Track title.
* Convert the *.wav to a compressed file, e.g. *.mp3:
    * Can be configured.
* Write the tags to the compressed file.
* Search for a "Cover.jpg" file in the folder of the *.wav files:
    * If found, write as album cover to the compressed file.

For special characters that are not allowed to appear in a Windows filename a hard-coded replacement table is used. It is based on [HTML character codes](https://www.ascii.cl/htmlcodes.htm). These characters are already replaced by my [Exact Audio Copy](http://www.exactaudiocopy.de/) configuration when the audio disc is read. audio-konverter then converts them back into the proper characters to appear in tags. They will be omitted in the final compressed filename though.

* &47; -> /
* &58; -> :
* &63; -> ?
* &92; -> \

These are the ones I encounter the most. At some point there could maybe be a configuration option for this.

## Configuration

audio-konverter uses two configuration files, each for a specific purpose. One configures the behavior of the audio-converter application, the other the converter. The file format is JSON. You can have several different configurations for different scenarios. The configuration that shall be used can be specified on the command line.

### Application

```json
{
    "input": {
        "path":   "<string: the input folder to scan recursively>",
        "delim":  "<string: the delimiter used to separate the tags>",
        "cover":  "<string: name of the album cover file>",
        "format": {
            "artist": <int: index position>,
            "album": <int: index position>,
            "genre": <int: index position>,
            "year": <int: index position>,
            "track": <int: index position>,
            "title": <int: index position>
        }
    },
    "output": {
        "path":   "<string: the output folder's root>",
        "format": "<string: describes the output folder structure and filename>"
    }
}
```

The JSON description should be quite self-explanatory. One note about the "format", though. The source filename's string is split into its individual pieces using "delim". The order of the tags is defined in the "format" object, starting with 0. If the tag is not mentioned here then it is ignored.

The output format uses the same placeholder names that can be found in the "format" object. Simply surround them with "%" and that's it. This can be used to create a complex folder structure or a simple file name. It is important to understand that this string is appended to the output path.

### Converter

Since command line converters don't always use the same syntax every converter has to be configured.

```json
{
    "args": ["<string: format of the command line>"],
    "bin":  "<string: path to the executable>",
    "ext":  "<string: the file extension>"
}
```

The configuration is very simple.

* "args" defines the command line of the converter. It expects two placeholders that are replaced with the source file and destination file by audio-konverter. If a converter does not support specifying input and output then it will not work with audio-konverter.
    * %input%: The input or source *.wav file.
    * %output%: The compressed output file.
* "bin" defines the path to the executable that shall be used for converting the files.
* "ext" is the compressed file extension that will be used instead of *.wav.

## Usage

The following example uses Windows as an operating system. It can, of course, also be used on Linux or a Mac. Simply change the paths accordingly.

An example filename looks like this:

    Belphegor#Goatreich-Fleshcult#Black Metal#2005#09#Festum Asinorum&47;Chapt. 2.wav

**app.json**
```json
{
    "input": {
        "path":   "D:\\Audio CDs",
        "delim":  "#",
        "cover":  "Cover.jpg",
        "format": {
            "artist": 0,
            "album": 1,
            "genre": 2,
            "year": 3,
            "track": 4,
            "title": 5
        }
    },
    "output": {
        "path":   "D:\\mp3",
        "format": "%genre%\\%artist%\\%year% - %album%\\%track% - %title%"
    }
}
```

**mp3.json**
```json
{
    "args": ["-V1", "%input%", "%output%"],
    "bin":  "C:\\Applications\\lame.exe",
    "ext":  "mp3"
}
```

**Start conversion**

    java -jar audio-konverter-1.0-SNAPSHOT-jar-with-dependencies.jar app.json mp3.json