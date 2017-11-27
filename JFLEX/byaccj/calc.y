
%{
  import java.io.*;
%}
      
%token NL          /* newline  */
%token <dval> NUM  /* a number */

%type <dval> exp

%left '-' '+'
%left '*' '/'
%left NEG          /* negation--unary minus */
%right '^' "=="        /* exponentiation        */
      
%%

input:   /* empty string */
       | input line
       ;
      
line:   /* NL      { }
       |*/ exp NL  { System.out.println(" = " + $1); }
       ;
      
exp:     NUM                { $$ = $1; }
       | exp '+' exp        { $$ = $1 + $3; }
       | exp '-' exp        { $$ = $1 - $3; }
       | exp '*' exp        { $$ = $1 * $3; }
       | exp '/' exp        { $$ = $1 / $3; }
       | '-' exp  %prec NEG { $$ = -$2; }
       | exp '^' exp        { $$ = Math.pow($1, $3); }
       | exp "==" exp 		{ $$ = $1 == $3 ? 1L : 0L; }
       | '(' exp ')'        { $$ = $2; }
       ;

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
