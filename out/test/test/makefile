JFLAGS = -g
JC = javac -cp . Job.java RedBlackTree.java jobscheduler.java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Job.java \
	RedBlackTree.java \
	jobscheduler.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class