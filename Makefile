scc = scalac -color never
sc = scala
src = src/main/scala/resistance
jvm = java -jar
classpath = $(PWD)/lib/binaries/scala-library.jar:$(PWD)/lib/binaries/scala3-library_3.jar:$(PWD)/cc2resistance.jar:$(PWD)/colorcode.jar
ivy_zip = apache-ivy-2.5.0-bin.zip
ivy_url = "https://archive.apache.org/dist/ant/ivy/2.5.0/$(ivy_zip)"
ivy_jar = apache-ivy-2.5.0/ivy-2.5.0.jar 

export CLASSPATH := $(classpath)

main : cc2resistance.jar colorcode.jar
	echo \#!/bin/bash > resistance.sh
	echo CLASSPATH=$(classpath) java resistance.ColorCodeToResistance >> resistance.sh

clean :
	rm *.jar

$(ivy_zip) :  
	wget $(ivy_url)

ivy-jar : $(ivy_zip)
	unzip -o $(ivy_zip) 

resolve : ivy.xml ivy-jar $(ivy_jar)
	$(jvm)  $(ivy-jar) -retrieve 'lib/[conf]/[artifact](-[classifier]).[ext]'

colorcode.jar : $(src)/ColorCode.scala
	$(scc) $(src)/ColorCode.scala -d colorcode.jar

colorcodetest.jar : $(src)/ColorCodeTest.scala
	$(scc) $(src)/ColorCodeTest.scala -d colorcodetest.jar
	
cc2resistance.jar : $(src)/ColorCodeToResistance.scala colorcode.jar
	$(scc) $(src)/ColorCodeToResistance.scala -d cc2resistance.jar

test : colorcode.jar colorcodetest.jar
	echo $$CLASSPATH
	CLASSPATH=$$CLASSPATH:colorcodetest.jar java resistance.ColorCodeTest

shell:
	scala
