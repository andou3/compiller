

JFLEX  = jflex 
BYACCJ = byaccj -J
JAVAC  = javac
LEX_FILE = lex.jflex
BYACC_FILE = syntaxNew.y
# targets:

all: Parser.class

run: Parser.class
	java Parser

build: clean Parser.class

clean:
	rm -f *~ *.class *.java

Parser.class: Yylex.java Parser.java
	$(JAVAC) Parser.java

Yylex.java: $(LEX_FILE)
	$(JFLEX) $(LEX_FILE)

Parser.java: $(BYACC_FILE)
	$(BYACCJ) $(BYACC_FILE)
