
%{
  import java.io.*;
%}
      
%token NL          /* newline  */
%token FOR, delimeter, assign, assertion, increment, BODY
%token <dval> NUM  /* a number */
%token <sval> variables, operator

%type <dval> exp
%type <dval> numer
%type <dval> assignExp

%left '-' '+'
%left variables
%left '*' '/'
%left NEG          /* negation--unary minus */
%right '^'       /* exponentiation        */
%start program

%%
/*
line:    NL      { }
       | exp NL  { System.out.println(" = " + $1); }
       |  assignExp NL { System.out.println("var = " + $1); }
       ;*/
//To use any letter like ')' or other you MUST return it from lexical analizer
//This syntax analizer read file per lines
//To debug this throw calculate context free grammars

program: parser program
parser: NL 
  | statement
  | exp NL /*{System.out.println($1);}*/
statement: FOR '(' exp ';' cond ';' exp ')' scope
  {System.out.println("TEEEEST");}
numer: NUM {System.out.println($1);}
exp: variables assign numer 
    | variables assign variables {System.out.println($1 + ":=" + $3);}
cond: variables assertion numer {System.out.println($1 + "==" + $3);}
scope: '{' block '}'
    | '{' NL block '}' {System.out.println("BLOCK");}
block: /*empty*/
    | program block
exp2: variables increment {System.out.println($1 + "++");}

%%

  private Yylex lexer;


  private int yylex () {
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
  	//ignore underflow exception 
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }

  public static void main(String args[]) throws IOException {

    Parser yyparser;
    if ( args.length > 0 ) {
      yyparser = new Parser(new FileReader(args[0]));
    } else {
    	throw new IllegalStateException("В программу не передано исходного файла");
    }

    yyparser.yyparse();
   
  }
