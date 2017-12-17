
%{
  import java.io.*;
%}
      
%token NL          /* newline  */
%token FOR, delimeter, assign, assertion, increment, BODY
%token <dval> NUM
%token <sval> variables, operator

%type <dval> exp
%type <dval> calc
%type <dval> NUM

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

program: parser program | parser
parser:  NL 
  | statement 
  | exp NL 
  | error NL /*{System.out.println($1);}*/
  
  /**
  * for statement allows following forms: 
  *for (;;) { 
  *}
  *for (VARIABLE := 1A3BCD; ; VARIABLE := 155dd) {} 
  *for (; ; VARIABLE := 155dd) {} 
  *for (; i == 0;) {} 
  *for (VARIABLE := 1A3BCD; VARIABLE == 0; VARIABLE := 155dd) { 
      i := 1 
  *}
  */
statement: FOR '(' forExp ';' cond ';' forExp ')' scope
  //| FOR '(' exp ';' ';' ')' scope
  //| FOR '(' ';' cond ';' ')' scope
  //| FOR '(' ';' ';' exp ')' scope
  //| FOR '(' ';' ';' ')' scope
  {System.out.println("FOR statement");}
//numer: NUM {System.out.println($1);}
forExp: /**/
    | exp
exp: /*variables assign calc 
    | */variables assign variables 
    | variables assign calc {System.out.println("calc: " + $1 + ":=" + $3);}
calc:  NUM { System.out.println("NUM: " + $1); $$ = $1; }
       | calc '+' calc        { $$ = $1 + $3; }
       | calc '-' calc        { $$ = $1 - $3; }
       | calc '*' calc        { $$ = $1 * $3; }
       | calc '/' calc        { $$ = $1 / $3; }
       | '-' calc  %prec NEG { $$ = -$2; }
       | calc '^' calc        { $$ = Math.pow($1, $3); }
       | calc "==" calc     { $$ = $1 == $3 ? 1L : 0L; }
       | '(' calc ')'        { $$ = $2; }
cond:  /*empty*/
    | variables assertion NUM {System.out.println($1 + "==" + $3);}
scope: '{' block '}'
    | '{' NL block '}' 
    | '{' '}'
     {System.out.println("BLOCK");}
block: parser block
    | parser
//exp2: variables increment {System.out.println($1 + "++");}

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
    System.err.println ("Error :" + error);
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
