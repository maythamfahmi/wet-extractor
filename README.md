[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/maythamfahmi/wet-extractor/blob/master/LICENSE) [![Build Status](https://travis-ci.org/maythamfahmi/wet-extractor.svg?branch=master)](https://travis-ci.org/maythamfahmi/wet-extractor)

<a href="https://github.com/maythamfahmi/wet-extractor/blob/master/LICENSE">
    <img src="http://dev.itbackyard.dk/wet-extractor/image/logo_wet_extractor_2017.png" align="right" height="240" width="240" >
</a>

# Overview

Wet Extractor is a little Java software that download latest WET [Common Crawl][1] files content from Amazon S3. It extracts and generates a Dummy file with real Searchable content for Search Engine Research and Development purpose.

# Why Wet Extractor

Imagine you want to create dummy file with real searchable content. If you make your own crawler, it gone take time, a lot of time. Wet Extractor will do all this for you in matter of minutes.

# User Documentation

## Javadocs

* [4.0](http://dev.itbackyard.dk/wet-extractor/4/)
* [3.0](http://dev.itbackyard.dk/wet-extractor/3/)
* 2.0 N/A
* 1.0 N/A


# Issue Tracking

If you find an issue, please file a report [here](https://github.com/maythamfahmi/wet-extractor/issues)

# Wet Extractor updates

## 06th September 2017  - wet extractor 4.0 release

I am glad to announce the [4.0.0](https://github.com/maythamfahmi/wet-extractor/releases) release of Wet extractor.

Improvement
* Improve design pattern (Builder).
* Bugs [fixed](https://github.com/maythamfahmi/wet-extractor/issues?q=is%3Aissue+is%3Aclosed)

## 15th August 2017  - wet extractor 3.0 released

## 30th June 2017  - wet extractor 2.0 released

## 1st September 2016  - wet extractor 1.0 released

# Contributors
I welcome contributors to join developing this tool.

If you like so, please email me: mailto:maythamfahmi@itbackyard.com.

If you find bugs, please [report](https://github.com/maythamfahmi/wet-extractor/issues).

# How to use
- Import the project as Maven (I use Intellij Community Edition).
- By default set to 2 wet files, you can increase the number in <code>Conf</code> class
- Run <code>MainApp</code> and Wait (see note).
 - It starts initialize the system.
 - Then starts to download WET files.
 - When download is done, it starts processing files.
 - When program is done, all dummy files will be found in under src/main/resources/output folder.

> **Note:**
> - Download process might take time depending on your internet speed. 
> - Each file will take 5-10 second to process.
> - So if you process 200 WET files, it gone take some time.
> - **Remember!** Depending on how many files you download, be aware to have enough storage resources.

[1]: http://commoncrawl.org
