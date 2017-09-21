#PreRequisites: 
1. Java (developer version) 1.8 (Only 64-Bit versions supported)
2. Eclipse IDE with Maven 3.3 or higher and JDK 1.8
3. Git

#Setup the parent dependancy
1. git clone https://github.com/deeplearning4j/dl4j-examples.git
2. Import this dl4j-examples into your Eclipse IDE
	#STEPS FOR IMPORTING AN EXISTING MAVEN PROJECT INTO ECLIPSE:
	2.1. Open Eclipse. Goto File -> Import. This opens the Import Wizard.
	2.2. Select Existing Maven Projects under Maven and click Next.
	2.3. Browse to the cloned folder and select it.
	2.4. The pom.xml will be listed under projects. Select it.
	2.5. Click Finish.
2. Import our project folder into ECLIPSE using Steps 2.1 to 2.5
3. Run the SentimentClassifierWE class
	#STEPS TO EXECUTE THE SENTIMENT_CLASSIFIER:
	1. Build the project nlp-p1 through IDE
	2. Run SentimentClassifierWE with these four arguments: [positive input file] [negative input file] [test file] [output folder path] 
	3. Steps to set run time arguments for main function:
		#ECLIPSE:
		1. Goto Project Menu -> Properties
		2. Select Run/Debug settings
		3. Select SentimentClassifierWE and click on Edit
		4. Select Argument tab and in Program arguements textbox insert the four arguements seperated by space.

