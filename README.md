#     HEVC converter

# Requirements
### For build (Maven)
- [JDK 21](https://www.oracle.com/uk/java/technologies/downloads/) or higher
- [ffmpeg](https://github.com/FFmpeg/FFmpeg) binary in OS PATH or in same folder

### For launching from jar
- [Java Runtime 21](https://www.java.com/en/download/manual.jsp) (for Jar in release, JDK no needed for jar)
- [ffmpeg](https://github.com/FFmpeg/FFmpeg) binary in OS PATH or in same folder


# Instructions
### Build
- Clone repository
  ```shell
  git clone https://github.com/0xDABE/toHevcConverter
  ```
  or download .zip archive
- Open folder with Java IDE ([Intellij IDEA](https://www.jetbrains.com/idea/) for example)
- run Main.java

### Launching from Jar
- Download release build
- Unpack files from archive to new folder
- Run terminal and set working directory to folder with unpacked files
  It may be like this (for example):
  ## Linux
  ```shell
  cd /home/Jeff/toHevcConverter
  ```
  ## Windows
  ```shell
  cd C:\Users\Jeff\Documents\toHevcConverter
  ```
  
#

- Run
  ```shell
  java -jar toHevcConverter.jar
  ```
  On windows, better experience with [Windows Terminal](https://apps.microsoft.com/store/detail/windows-terminal/9N0DX20HK701)

# Running .jar:
- Unzip all files in archive to any directory. (You must not rename config file, but you can do it with .jar file)
- Create a script, that launches .jar file:
## On Windows:
- Make sure your java binaries are in PATH (OS environment). Try
 ```cmd
java
```
If you see info about java usage, then java is in PATH, else you should put it into path (you may not do it using absolute path)
- Create a .bat file:
### If Java in PATH:
```cmd
java -jar C:\Absolute\Path\To\Your\toHevcConverter.jar %1 %2 %3 %4 %5 %6 %7 %8 %9
```
- Make sure you are using java 21 binaries from PATH with
```cmd
java --version
```
### If NOT in path:
```cmd
C:\Absolute\Path\To\Java21orAbove\Binaries\java.exe -jar C:\Absolute\Path\To\Your\toHevcConverter.jar %1 %2 %3 %4 %5 %6 %7 %8 %9
```
#
- After creating .bat file move it to any separated directory and add this directory to PATH
- You can name .bat file as you wish, for example **toh.bat**
- Complete installation from Finish below
## On Linux:
- Make sure you are using Java 21:
```bash
java --version
```
- Create a bash script and name it as you wish, **toh** for example:
```bash
#!/bin/bash
java -jar /path/to/toHevcConverter.jar $1 $2 $3 $4 $5 $6 $7 $8 $9
```
- Put it to `/home` directory or another directory, from which you can run scripts (`/usr/bin` for example)
- Make this script executable:
```bash
chmod u+x /path/to/script
```
- Complete installation from Finish below
# Finish
- Try:
```cmd
toh --help
```

# Args
- ## `-h`, *`--help`* help flag. Shows help message
- ## `-c <float_value>` set `<dstBRate>` to `<srcBRate> / <float_value>` and `<dstMaxBRate>` to `<srcBRate>/<float_value> + 700` Kb/s
- ## `-d`, `--delete` delete src files after conversion complete flag



