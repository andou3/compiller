//imports
import java.util.regex.Pattern;

%%

%class Test
%standalone
%column
variables = [a-zA-Z]([0-9]|[a-zA-Z])*
number = [0-9]([0-9]|[a-zA-Z])*
operator = ([<>=+-]|:=|[*/])
Whitespace = [ \t\n]
delimeter = [();]

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
%}

%%

//rule

for {
	print(Lexer.KEYWORD, yytext(), yycolumn);
}
do {
	print(Lexer.KEYWORD, yytext(), yycolumn);
}
{variables} {
	print(Lexer.VARIABLE, yytext(), yycolumn);
}
{number} {
	if (Pattern.compile("[g-zG-Z]").matcher(yytext()).find()) {
		print(Lexer.UNKNOWN, yytext(), yycolumn);
	} else {	
		print(Lexer.NUMBER, yytext(), yycolumn);
	}
}
{delimeter} {
	print(Lexer.DELIMETER, yytext(), yycolumn);
}
{operator} {
	print(Lexer.OPERATION, yytext(), yycolumn);
}

//順番が大事
{Whitespace} {}
//bellow's shit make possible don't print every line of base file
. { 
	print(Lexer.UNKNOWN, yytext(), yycolumn);
}


