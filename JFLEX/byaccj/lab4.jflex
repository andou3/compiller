//imports
import java.util.regex.Pattern;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

/*%class Lab2
%standalone*/
%column
variables = [a-zA-Z]([0-9]|[a-zA-Z])*
NUM = [0-9]([0-9]|[a-fA-F])*
operator = ([<>=+-]|[*/])
Whitespace = [ \t\n]
delimeter = [();]
bodyBrackets = [{}]
NL  = \n | \r | \r\n

%{
	
enum Lexer {
	//for, do
	KEYWORD("KEYWORD"),
	// '(', ')', ':'
	DELIMETER("DELIMETER"),
	//variables
	VARIABLE("VARIABLE"),
	//:=, <, >, = etc
	OPERATION("OPERATION"),
	//some hex value
	NUMBER("NUMBER"),
	
	UNKNOWN("UNKNOWN");

	private final String value;

	Lexer(String value) {
		this.value = value;
	}

	String getType() {
		return value;
	}
}

	public static void print(Lexer lexer, String text, int atColumn) {
		System.out.printf("%s at column %d: %s\n", lexer.getType(), atColumn, text);	
	}


	static List<LinkedList<Integer>> table = new ArrayList<>(Arrays.asList(
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>(),
		new LinkedList<>()
		)
	);

	public static boolean checkExists(Variable var) {
		return table.get(var.getHash() % 9).contains(var.getHash());
	} 

	public class Variable {

		private final String name;
		private final int hash;

		public Variable(String name, boolean create) {
			this.name = name;
			this.hash = hashCode();
			if (create) {
				addInTable();	
			}
		}

		private void addInTable() {
			if (!checkExists(this)) {
				table.get(hash % 9).add(hash);
			}/* else {
				throw new IllegalStateException("Переменная "+ this.name +" уже существует!");
			}*/
		}
		/**
		* ELF hash algorithm
		**/
		@Override
		public int hashCode() {
			int hash = 0, x, i;
			for (i = 0; i < name.length(); i++) {
				hash = (hash << 4) + (name.charAt(i));
				if ((x = hash & 0xF0000000) != 0) {
					hash ^= (x >> 24);
				}
				hash &= ~x;
			}
			return hash;
		}

		public String getName() {
			return name;
		}

		public int getHash() {
			return hash;
		}
	}

%}

%eof{
	System.out.println("Identifiers table:");
	table.forEach(System.out::println);
	System.out.println("\n try to find some variable:");
	Scanner scanner = new Scanner(System.in);
	Variable var;
	while (scanner.hasNext()) {
		var = new Variable(scanner.next(), false);
		System.out.println(checkExists(var) ? "Переменная " + var.getName() + " существует! С хэш-кодом:" + var.getHash() 
			: "Переменной " + var.getName() + " не существует в текущей таблице с хэш-кодом:" + var.getHash() + " !");
	}
	scanner.close();
%eof}

%%

//rule

for {
	print(Lexer.KEYWORD, yytext(), yycolumn);
	return Parser.FOR;
}
do {
	print(Lexer.KEYWORD, yytext(), yycolumn);
}
{variables} {
	print(Lexer.VARIABLE, yytext(), yycolumn);
	new Variable(yytext(), true);
	yyparser.yylval.sval = yytext();
	return Parser.variables;
}
{operator}    { 
		//yyparser.yyval.sval = new ParserVal(yytext()) ;
		return (int) yycharat(0);
}

:= {
		//print(Lexer.OPERATION, yytext(), yycolumn);
		return Parser.assign;
	}

{bodyBrackets} {
	print(Lexer.DELIMETER, yytext(), yycolumn);
	return (int) yycharat(0);
}

== {
	return Parser.assertion;
}

"++" {
	return Parser.increment;
}

/* newline */
{NL}   { return Parser.NL; }

/* double */
{NUM}  { yyparser.yyval = new ParserVal((double) Integer.parseInt(yytext(), 16));
		System.out.println("NUM: " + yyparser.yyval.dval);
         return Parser.NUM; }

/*{NUM} {
	if (Pattern.compile("[g-zG-Z]").matcher(yytext()).find()) {
		print(Lexer.UNKNOWN, yytext(), yycolumn);
	} else {	
		print(Lexer.NUMBER, yytext(), yycolumn);
	}
}*/
{delimeter} {
	print(Lexer.DELIMETER, yytext(), yycolumn);
	return (int) yycharat(0);
}
/*{operator} {
	print(Lexer.OPERATION, yytext(), yycolumn);
}*/

//順番が大事
{Whitespace} {}
//bellow's shit make possible don't print every line of base file
. { 
	print(Lexer.UNKNOWN, yytext(), yycolumn);
}


