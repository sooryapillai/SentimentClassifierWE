This is a Maven Project. You will need an IDE (Eclipse or IntelliJ) to run it.

Before integrating the project, make sure that your IDE Settings use the following:
Maven 3.3 or higher and JDK 1.8

The nlp-p1 is the root folder of this project. 
1.Importing the project:
	#STEPS FOR IMPORTING AN EXISTING MAVEN PROJECT INTO ECLIPSE:
	1. Open Eclipse. Goto File -> Import. This opens the Import Wizard.
	2. Select Existing Maven Projects under Maven and click Next.
	3. Browse to the downloaded nlp-p1 folder and select it.
	4. The pom.xml will be listed under projects. Select it.
	5. Click Finish.
OR
	#STEPS FOR IMPORTING AN EXISTING MAVEN PROJECT INTO INTELLIJ:
	1. Open IntelliJ IDEA and close any existing project.
	2. From the Welcome screen, click Import Project. The "Select File or Directory to Import" dialog opens.
	3. Navigate to the Maven project folder nlp-p1 and select the pom file and Click OK.
	4. The Import Project screen opens
	5. Select "Open as Project"
	6. Click Finish.
	For more details goto https://www.lagomframework.com/documentation/1.3.x/java/IntellijMaven.html

2. Run the SentimentClassifierWE class
	#STEPS TO EXECUTE THE SENTIMENT_CLASSIFIER:
	1. Build the project through IDE
	2. Run SentimentClassifierWE with these four arguments: [positive input file] [negative input file] [test file] [output folder path] 
	3. Steps to set run time arguments for main function:
		#ECLIPSE:
		1. Goto Project Menu -> Properties
		2. Select Run/Debug settings
		3. Select SentimentClassifierWE and click on Edit
		4. Select Argument tab and in Program arguements textbox insert the four arguements seperated by space.
	OR
		#INTELLIJ:
		1. Goto Run menu -> Edit Configurations
		2. Click on + button in top left
		3. Select SentimentClassifierWE class 
		4. Specify the command-line arguments

NOTE: Cannot be run via command line.


