

JFLEX  = jflex 
BYACCJ = byaccj -J
JAVAC  = javac

# targets:

all: Parser.class

run: Parser.class
	java Parser

build: clean Parser.class

clean:
	rm -f *~ *.class *.java

Parser.class: Yylex.java Parser.java
	$(JAVAC) Parser.java

Yylex.java: lexical.jflex
	$(JFLEX) lexical.jflex

Parser.java: syntax.y
	$(BYACCJ) syntax.y
