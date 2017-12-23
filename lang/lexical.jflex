//imports
import java.util.regex.Pattern;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

%%
//%option noyywrap yylineno
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
/*variables = [a-zA-Z]([0-9]|[a-zA-Z])*
NUM = [0-9]([0-9]|[a-fA-F])*
operator = ([<>=+-]|[/]) //Добавить '*' когда при раскомменчивании
Whitespace = [ \t\n]
delimeter = [();]
bodyBrackets = [{}]
NL  = \n | \r | \r\n
*/
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

/*
void skip_multiline_comment() {
  int c;
  while(1)
  {
    switch(input()) // input - внутренняя функция flex, по сути getch();
    {
      case EOF:
        yyerror("Error: unclosed comment\n");
        System.exit(-1);
        goto done;
      break;

      case '*':
        if((c = input()) == '/') goto done;
        unput(c); // putch();
        break;
      default:
        break;
    }
  }
  done: ;
}
*/
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

/*%eof{
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
*/
%%
"//".*			{ /* Single line comment: Everything that starts with "//" will be skipped. */ }
"/*"         { /*skip_multiline_comment();*/}

"int"			{  return Parser.INT; }
"char"			{  return Parser.CHAR; }
"boolean"       {  return Parser.BOOLEAN;}
"if"			{  return Parser.IF; }
"else"			{  return Parser.ELSE; }
"while"			{  return Parser.WHILE; }
"return"		{  return Parser.RETURN; }

"functions" { return Parser.FUNCDEFBLOCK;}
"variables" { return Parser.VARBLOCK;}
"code"      { return Parser.CODEBLOCK;}
"input"     { return Parser.INPUT; }
"print"      { return Parser.PRINT; }
"true"      { return Parser.TRUE; }
"false"     { return Parser.FALSE; }

[a-zA-Z_]([a-zA-Z_]|[0-9])*		{ return Parser.ID; }

0               { return Parser.CONSTANT; }
[1-9][0-9]*     { return Parser.CONSTANT; }
"'"[a-zA-Z]"'"  { return Parser.CHARACTER; }

//\"(\\.|[^\\"])*\"	{ return Parser.STRING_LITERAL; }

";"			{  return ';'; }
":"     {  return ':'; }
","     { return ','; }

"&&"		{  return Parser.AND_OP; }
"||"		{  return Parser.OR_OP; }
"<="		{  return Parser.LE_OP; }
">="		{  return Parser.GE_OP; }
"=="		{  return Parser.EQ_OP; }
"!="		{  return Parser.NE_OP; }

("{")		{  return '{'; }
("}")		{  return '}'; }
"("			{  return '('; }
")"			{  return ')'; }
("[")		{  return '['; }
("]")		{  return ']'; }

"="			{  return '='; }
"-"			{  return '-'; }
"+"			{  return '+'; }
"*"			{  return '*'; }
"/"			{  return '/'; }
"<"			{  return '<'; }
">"			{  return '>'; }

[ \t\n\r]	;
.			{ Parser.yyerror("Unknown character"); }
