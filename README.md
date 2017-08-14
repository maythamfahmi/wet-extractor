[![license](https://img.shields.io/github/license/mashape/apistatus.svg)]()

# Overview

Wet extractor is a Java software that download and extract WET Common Crawl files content from Amazon S3 [commoncrawl][1].

It generates Dummy Searchable files from WET files for Search Engine Research and Development purpose.

# User Documentation

## Javadocs

* [3.0](http://dev.itbackyard.dk/wet-extractor/3/)
* 2.0 N/A
* 1.0 N/A


# Issue Tracking

If you find an issue, please file a report [here](https://github.com/maythamfahmi/wet-extractor2/issues)

# Wet Extractor updates

## 15th August 2017  - wet extractor 3.0 released

I am glad to announce the 3.0 release of Wet extractor.

## 30th June 2017  - wet extractor 2.0 released

I am glad to announce the 2.0 release of Wet extractor.

## 1st September 2016  - wet extractor 1.0 released

I am glad to announce the 1.0 release of Wet extractor.

# How to use
- Import the project as Maven (I use Intellij Community Edition).
- By default set to 2 wet files, you can increase so it help creating bigger dummy file.
- Remember! you need also to have enough storage resources.
- Run and Wait. for each 1 files it takes 30-60 second.
- It will download WET files if they are not already downloaded.
- When download is done, it start processing files.
- When program is done, all dummy files will be found in under src/main/resources/dummy folder

> **Note:**
> - Download process might take time depending on your internet conneciton. 
> - Each file will take 5-10 second to process and makes dummy output.


> **Note:** It will take some time to process over 200 WET files.
> Than it will generate big file under output folder.


[1]: http://commoncrawl.org
