
%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

NUM = [0-9]+ ("." [0-9]+)?
NL  = \n | \r | \r\n

%%

/* operators */
"+" | 
"-" | 
"*" | 
"/" | 
"^" | 
"(" | 
")"    { return (int) yycharat(0); }

/* newline */
{NL}   { return Parser.NL; }

/* float */
{NUM}  { yyparser.yylval = new ParserVal((double) Integer.parseInt(yytext(), 16));
         return Parser.NUM; }

/* whitespace */
[ \t]+ { }

/* error fallback */
[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
