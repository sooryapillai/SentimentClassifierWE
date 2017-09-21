This is a Maven Project.

Before integrating the project, make sure that your IDE Settings use the following:
Maven 3.3 or higher and A JDK 1.8

The nlp-p1 is the root folder of this project. 
Download and save this folder on to your local machine.

#STEPS FOR IMPORTING INTO INTELLIJ:
1. Open IntelliJ IDEA
2. Open nlp-p1 directory as a project
3. Navigate to File -> Project Structure -> Library
4. Click the + sign and add the "org" directory from within the project path
5. Edit run configurations: 
	- set main to ngram.SentimentAnalyzer
	- set program arguments [positive input file] [negative input file] [test file] [output folder path]

#STEPS FOR IMPORTING AN EXISTING MAVEN PROJECT INTO ECLIPSE:
1. Open Eclipse. Goto File -> Import. This opens the Import Wizard.
2. Select Existing Maven Projects under Maven and click Next.
3. Browse to the downloaded nlp-p1 folder.


