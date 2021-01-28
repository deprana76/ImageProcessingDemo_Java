# ImageProcessing

This application reads list of images from input file and find 3 most prevalent colors in RGB scheme in hexadecimal format (#000000 - #FFFFFF) in each image, and write the result into a CSV file in a form of url,color,color,color.

### Prerequisites

1. Java 8 or newer
2. Maven
3. Your preferred IDE
4. Eclipse with the m2e plugin.

## Getting Started

Build : mvn clean install

Run : java -Xmx512m -Xms256m -DinputFile="<inputFile path>" -jar imageprocessing-0.0.1-SNAPSHOT-jar-with-dependencies.jar
imageprocessing-0.0.1-SNAPSHOT-jar-with-dependencies.jar file is available under target folder.

You can run Main class directly and it will start the execution.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Approach Used

To handle input files with billion of urls using limited resources(e.g. 1 CPU, 512MB RAM), segmentation is done on the input file to limit the read by 50 lines and worker threads created are bound to Available processors on the machine using java Runtime apis. This way we can be sure that we are not hitting OOM.

## Alternate Approach to implement

To process billion of urls using limited resources, we can use Apache spark implementation as it provides High performance batch computation and 
real time stream processing and reduces the latency associated with disk I/O and it can handle massive data-sets on the fly but kept this approach 
limited for this requirement.