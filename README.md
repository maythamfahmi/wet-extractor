[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/maythamfahmi/wet-extractor3/blob/master/LICENSE)

<a href="url"><img src="http://dev.itbackyard.dk/wet-extractor/image/logo_wet_extractor.png" align="right" height="240" width="240" ></a>

# Overview

Wet Extractor is a little Java software that download latest, extract WET [Common Crawl][1] files content from Amazon S3 and it generates content file as Dummy Searchable files from WET files for Search Engine Research and Development purpose.


# User Documentation

## Javadocs

* [4.0](http://dev.itbackyard.dk/wet-extractor/4/)
* [3.0](http://dev.itbackyard.dk/wet-extractor/3/)
* 2.0 N/A
* 1.0 N/A


# Issue Tracking

If you find an issue, please file a report [here](https://github.com/maythamfahmi/wet-extractor/issues)

# Wet Extractor updates

## 06th September 2017  - wet extractor 4.0 dev

Improve design pattern (Builder).
Bugs fixed
Initializer and Download works
The rest still under development 

## 15th August 2017  - wet extractor 3.0 released

I am glad to announce the 3.0 release of Wet extractor.

## 30th June 2017  - wet extractor 2.0 released

## 1st September 2016  - wet extractor 1.0 released

# How to use
- Import the project as Maven (I use Intellij Community Edition).
- By default set to 2 wet files, you can increase so it help creating bigger dummy file.
- Run and Wait (see note).
 - It downloads WET files if they are not already downloaded.
 - When download is done, it starts processing files.
 - When program is done, all dummy files will be found in under src/main/resources/dummy folder.

> **Note:**
> - Download process might take time depending on your internet speed. 
> - Each file will take 5-10 second to process.
> - So if you process 200 WET files, it gone take some time.
> - **Remember!** Depending on how many files you download, be aware to have enough storage resources.

[1]: http://commoncrawl.org
