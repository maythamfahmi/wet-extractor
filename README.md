Welcome to Crawler v2.75 release
========================
Little software to download and extract WET file content Amazon S3 [commoncrawl][1].

The idea is to generate Dummy Searchable files for search engine.

This is software is still under development.

How to use
-------------
- Import the project as Maven (I use Intellij Community Edition).
- By default set to 2 wet files, you can increase so it help creating bigger dummy file.
- Remember! you need also to have enough storage resources.
- Run and Wait. for each 1 files it takes 30-60 second.
- It will download WET files if they are not already downloaded.
- When download is done, it start processing files.
- When program is done, all dummy files will be found in under src/main/resources/dummy folder

Note: 
 - Download process might take time depending on your internet conneciton. 
 - Each file will take 5-10 second to process and makes dummy output.

- - -
v.2.75 Every thing is working top 
<br>
.<br>
.<br>
.<br>
v.2.2 Interface ISystem, FileHelper, LogData Major improvement + MainApp with (Thread for auto run)
v.2.1 improvement
v.2.0 code is re-written based on old concept


> **Note:** It will take some time to process over 200 WET files.
> Than it will generate big file under output folder.

----------

> When big file is generated, you can start SmallFiles to generate smaller files.
Open the class it is self explainable.

----------

**Old version notes:**
**Ver. 1.0**

1. Beta release 1.0, every thing works perfect.
2. Next version will be eventully: Bug fixing, Improvement, Parallel processing.
3. Save resources data and output files on dropbox via Java
4. Solution for auto downloading wet files


**Ver. 0.51**

1. ```Program``` is optimized and Swear words is now only called once.
2. ```FileHelper``` space bug fixed, processed file bug fixed.
3. ```ContentFilter``` & ```Program``` isDomainWhite is improved.


**Ver. 0.5**

1. ```FileHelper``` improved
2. Classes cleaned and accessors
3. Dead and unused methods cleaned as well
4. Maven dependency updated
5. This version has processed 200 WET files

**Ver. 0.41**

1. Generating small files is much faster then previous version.
2. Extracting single domain content.
3. Notified bugs fixed

**Ver. 0.40:**
**Please read:**

- Maven is updatd ~~Remember to add ```/lib/webarchive-commons-jar-with-dependencies.jar``` to your IDE/enviroment~~ 
- Download warc.wet.gz files in put them in ```/resources/wet/wetfiles...warc.wet.gz``` folder
- Just run the Program, it has main method, that is it
- Processed files will be logged in ```/resources/processed/already.txt```

**Ver. > 0.40:**
Wet file extractor base project
v0.10 - v0.30 made offline til 0.40 was deployed on git

**Old stuff**
//Note
//ExecutorService service = Executors.newFixedThreadPool(3);
//service.submit(listfetcher::onStart);
//service.submit(downloader::doStart);
//service.submit(program::onStart);
//service.shutdown();
//service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);


[1]: http://commoncrawl.org