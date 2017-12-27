//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 3 "syntaxNew.y"
  import java.io.*;
//#line 19 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NL=257;
public final static short INT=258;
public final static short CHAR=259;
public final static short BOOLEAN=260;
public final static short IF=261;
public final static short ELSE=262;
public final static short WHILE=263;
public final static short RETURN=264;
public final static short FUNCDEFBLOCK=265;
public final static short VARBLOCK=266;
public final static short CODEBLOCK=267;
public final static short INPUT=268;
public final static short PRINT=269;
public final static short TRUE=270;
public final static short FALSE=271;
public final static short AND_OP=272;
public final static short OR_OP=273;
public final static short LE_OP=274;
public final static short GE_OP=275;
public final static short EQ_OP=276;
public final static short NE_OP=277;
public final static short CONSTANT=278;
public final static short CHARACTER=279;
public final static short VARIABLE=280;
public final static short OPERATOR=281;
public final static short LITERAL=282;
public final static short variables=283;
public final static short IFX=284;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    2,    2,    5,    6,    6,    6,
    7,    7,    9,    9,    8,    8,    4,    4,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    3,    3,   12,
   12,   12,   12,   12,   12,   12,   12,
};
final static short yylen[] = {                            2,
    9,    3,    6,    3,    1,    2,    3,    1,    1,    1,
    3,    4,    2,    4,    2,    3,    1,    2,    5,    7,
    5,    2,    3,    5,    5,    5,    4,    4,    1,    1,
    3,    3,    3,    3,    3,    3,    3,    3,    1,    1,
    1,    1,    3,    3,    3,    3,    3,    1,    2,    5,
    5,    5,    5,    3,    3,    3,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    8,    9,   10,
    0,    5,    0,    0,    0,    0,    0,    0,   48,    0,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    6,
    0,    0,   57,    0,    0,    0,   49,    0,    0,   40,
   42,   39,   41,    0,   22,    0,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    7,   54,    0,   55,    0,
   56,    0,   29,   30,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    0,    0,    0,    0,
   11,    0,    0,   15,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   47,
    0,    0,   45,   46,    0,    0,    0,   27,   28,    0,
   13,   12,    0,   16,   50,   51,   52,   53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   21,   24,   25,
   26,    0,    0,    0,    0,   14,   20,
};
final static short yydgoto[] = {                          4,
   65,   11,   18,   26,   12,   13,   32,   56,   83,   27,
   66,   19,
};
final static short yysindex[] = {                       -94,
  -32,   -8,   -7,    0, -115, -155, -232,    0,    0,    0,
 -160,    0, -269,  -25, -226, -220, -214, -155,    0,   41,
   54,  -35,   55,   62,  -31, -232,    0,   13,   53,    0,
   88,   -1,    0,   59,   93,  115,    0,  -40,  -40,    0,
    0,    0,    0,  -30,    0,   37,  -30,  -30,  -30,  -40,
    0, -155, -232,  -39, -122,    0,    0, -145,    0, -141,
    0, -252,    0,    0,   31,  108,  112,   67,  -30,  -30,
  -30,  -30,    0,   74,   82,   89,   10,  100, -170, -232,
    0, -116,   96,    0, -113,  110,  116,  118,  119,  -30,
  -30,  -30,  -30,  -30,  -30,  -30,  -30,   -1,   -1,    0,
   45,   45,    0,    0,  120,  121,  122,    0,    0,  128,
    0,    0, -115,    0,    0,    0,    0,    0,  123,  123,
  123,  123,  123,  123,  123,  123,  -75,    0,    0,    0,
    0, -232,  -92,   -1, -232,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  189,    0,    0,
    0,    0,    0,    0,    0,  190,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  191,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -18,    2,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -26,  -24,
  -21,  -20,  -19,  -13,   18,   24,    1,    0,    0,    0,
    0,    0,    0,    0,  192,    0,    0,
};
final static short yygindex[] = {                         0,
  113,    0,  141,  -47,  183,  -38,    0,  -85,    0,  -22,
   17,  -11,
};
final static int YYTABLESIZE=308;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         44,
   19,   81,   84,   51,   44,   80,   37,   85,   49,   44,
   31,  114,  127,  128,   37,   82,   38,   88,   89,   34,
   33,   35,   44,   45,   44,    5,   44,   36,   20,   50,
   21,   22,   37,   33,   38,   23,   24,   34,   33,   35,
   44,   44,   43,   44,   43,   36,   43,   25,  137,    6,
    7,   71,   70,   34,   69,   67,   72,   51,   31,   35,
   43,   43,   51,   43,   32,   36,   78,   37,  108,   96,
   52,   97,   71,   70,  133,   69,   31,   72,   71,   70,
   38,   69,   32,   72,  135,   14,   71,   15,   16,   17,
   96,   72,   97,   39,   47,   73,  110,    8,    9,   10,
   14,   48,   15,   16,   17,   28,   29,  100,   71,   70,
   53,   69,   51,   72,  105,   71,   70,   57,   69,   58,
   72,   55,  106,   71,   70,   19,   69,   54,   72,  107,
   71,   70,   86,   69,   46,   72,  112,   87,   20,  113,
   21,   22,    8,    9,   10,   23,   24,   20,   98,   21,
   22,   59,   99,   60,   23,   24,   68,   25,  109,   74,
   75,   76,   77,  111,   71,   70,   25,   69,  115,   72,
    1,    2,    3,   61,  116,   62,  117,  118,  129,  130,
  131,  101,  102,  103,  104,  132,  134,  136,    2,    4,
    3,    1,   79,   30,    0,    0,    0,    0,    0,    0,
    0,    0,  119,  120,  121,  122,  123,  124,  125,  126,
    0,    0,    0,    0,    0,    0,    0,    0,    8,    9,
   10,    0,    0,    0,    0,    0,    0,    0,    0,   63,
   64,    0,    0,    0,    0,    0,    0,   40,   41,   42,
    0,   43,   40,   41,   42,    0,   43,   40,   41,   42,
    0,   43,    0,   44,   44,   44,   44,   44,   44,    0,
    0,   19,    0,   19,   19,    0,    0,    0,   19,   19,
    0,    0,    0,   43,   43,   43,   43,   43,   43,    0,
   19,   90,   91,   92,   93,   94,   95,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   90,   91,   92,   93,   94,   95,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,  125,   26,   40,   53,   18,   55,   40,   40,
  280,  125,   98,   99,   41,   54,   41,  270,  271,   41,
   41,   41,   41,   59,   43,   58,   45,   41,  261,   61,
  263,  264,   59,   59,   59,  268,  269,   59,   59,   59,
   59,   60,   41,   62,   43,   59,   45,  280,  134,   58,
   58,   42,   43,  280,   45,   39,   47,   80,   41,  280,
   59,   60,   85,   62,   41,  280,   50,   79,   59,   60,
   58,   62,   42,   43,  113,   45,   59,   47,   42,   43,
   40,   45,   59,   47,  132,  256,   42,  258,  259,  260,
   60,   47,   62,   40,   40,   59,  267,  258,  259,  260,
  256,   40,  258,  259,  260,  266,  267,   41,   42,   43,
   58,   45,  135,   47,   41,   42,   43,   59,   45,   61,
   47,  123,   41,   42,   43,  125,   45,   40,   47,   41,
   42,   43,  278,   45,   22,   47,   41,  279,  261,   44,
  263,  264,  258,  259,  260,  268,  269,  261,   41,  263,
  264,   59,   41,   61,  268,  269,   44,  280,   59,   47,
   48,   49,   50,  280,   42,   43,  280,   45,   59,   47,
  265,  266,  267,   59,   59,   61,   59,   59,   59,   59,
   59,   69,   70,   71,   72,   58,  262,  280,    0,    0,
    0,    0,   52,   11,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   90,   91,   92,   93,   94,   95,   96,   97,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,  259,
  260,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,
  271,   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,  280,
   -1,  282,  278,  279,  280,   -1,  282,  278,  279,  280,
   -1,  282,   -1,  272,  273,  274,  275,  276,  277,   -1,
   -1,  261,   -1,  263,  264,   -1,   -1,   -1,  268,  269,
   -1,   -1,   -1,  272,  273,  274,  275,  276,  277,   -1,
  280,  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  272,  273,  274,  275,  276,  277,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=284;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"NL","INT","CHAR","BOOLEAN","IF","ELSE",
"WHILE","RETURN","FUNCDEFBLOCK","VARBLOCK","CODEBLOCK","INPUT","PRINT","TRUE",
"FALSE","AND_OP","OR_OP","LE_OP","GE_OP","EQ_OP","NE_OP","CONSTANT","CHARACTER",
"VARIABLE","OPERATOR","LITERAL","variables","IFX",
};
final static String yyrule[] = {
"$accept : program",
"program : FUNCDEFBLOCK ':' func_def_list VARBLOCK ':' declaration_list CODEBLOCK ':' statement_list",
"program : VARBLOCK ':' declaration_list",
"program : FUNCDEFBLOCK ':' func_def_list CODEBLOCK ':' statement_list",
"program : CODEBLOCK ':' statement_list",
"func_def_list : func_def",
"func_def_list : func_def_list func_def",
"func_def : type_specifier func_declorator compound_statement",
"type_specifier : INT",
"type_specifier : CHAR",
"type_specifier : BOOLEAN",
"func_declorator : VARIABLE '(' ')'",
"func_declorator : VARIABLE '(' parametr_declaration_list ')'",
"parametr_declaration_list : type_specifier VARIABLE",
"parametr_declaration_list : parametr_declaration_list ',' type_specifier VARIABLE",
"compound_statement : '{' '}'",
"compound_statement : '{' statement_list '}'",
"statement_list : statement",
"statement_list : statement_list statement",
"statement : IF '(' conditions ')' compound_statement",
"statement : IF '(' conditions ')' compound_statement ELSE compound_statement",
"statement : WHILE '(' conditions ')' compound_statement",
"statement : RETURN ';'",
"statement : RETURN expression ';'",
"statement : INPUT '(' expression ')' ';'",
"statement : PRINT '(' expression ')' ';'",
"statement : VARIABLE '(' expression ')' ';'",
"statement : VARIABLE '=' expression ';'",
"statement : VARIABLE '=' conditions ';'",
"conditions : TRUE",
"conditions : FALSE",
"conditions : expression '<' expression",
"conditions : expression '>' expression",
"conditions : expression GE_OP expression",
"conditions : expression LE_OP expression",
"conditions : expression EQ_OP expression",
"conditions : expression NE_OP expression",
"conditions : expression AND_OP expression",
"conditions : expression OR_OP expression",
"expression : VARIABLE",
"expression : CONSTANT",
"expression : LITERAL",
"expression : CHARACTER",
"expression : expression '+' expression",
"expression : expression '-' expression",
"expression : expression '*' expression",
"expression : expression '/' expression",
"expression : '(' expression ')'",
"declaration_list : declaration",
"declaration_list : declaration_list declaration",
"declaration : INT VARIABLE '=' CONSTANT ';'",
"declaration : CHAR VARIABLE '=' CHARACTER ';'",
"declaration : BOOLEAN VARIABLE '=' TRUE ';'",
"declaration : BOOLEAN VARIABLE '=' FALSE ';'",
"declaration : INT VARIABLE ';'",
"declaration : CHAR VARIABLE ';'",
"declaration : BOOLEAN VARIABLE ';'",
"declaration : error ';'",
};

//#line 179 "syntaxNew.y"

  private Yylex lexer;
  private MemoryManager memoryManager = new MemoryManager();

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
//#line 389 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 27 "syntaxNew.y"
{ System.out.printf("\nProgram "); }
break;
case 2:
//#line 29 "syntaxNew.y"
{ System.out.printf("\nProgram "); }
break;
case 3:
//#line 31 "syntaxNew.y"
{ System.out.printf("\nProgram "); }
break;
case 4:
//#line 33 "syntaxNew.y"
{ System.out.printf("\nProgram "); }
break;
case 5:
//#line 37 "syntaxNew.y"
{ System.out.printf("\nfunc_def_list "); }
break;
case 6:
//#line 38 "syntaxNew.y"
{ System.out.printf("\nfunc_def_list "); }
break;
case 7:
//#line 42 "syntaxNew.y"
{ System.out.printf("\nfunc_def "); }
break;
case 8:
//#line 47 "syntaxNew.y"
{ System.out.printf("\nint_specificator "); }
break;
case 9:
//#line 49 "syntaxNew.y"
{ System.out.printf("\nchar_specificator "); }
break;
case 10:
//#line 51 "syntaxNew.y"
{ System.out.printf("\nboolean_specificator "); }
break;
case 11:
//#line 56 "syntaxNew.y"
{ System.out.printf ("\nfunc_declorator "); }
break;
case 12:
//#line 58 "syntaxNew.y"
{ System.out.printf ("\nfunc_declorator "); }
break;
case 13:
//#line 61 "syntaxNew.y"
{System.out.printf("\nparam_declaration_list");}
break;
case 14:
//#line 62 "syntaxNew.y"
{System.out.printf("\nparam_declaration_list");}
break;
case 15:
//#line 67 "syntaxNew.y"
{ System.out.printf("\nempty compound_statement "); }
break;
case 16:
//#line 69 "syntaxNew.y"
{ System.out.printf("\ncompound_statement "); }
break;
case 17:
//#line 73 "syntaxNew.y"
{System.out.printf("\nstatement_list");}
break;
case 18:
//#line 74 "syntaxNew.y"
{System.out.printf("\nstatement_list");}
break;
case 19:
//#line 82 "syntaxNew.y"
{ System.out.printf("\nif <if>"); }
break;
case 20:
//#line 84 "syntaxNew.y"
{ System.out.printf("\nif else"); }
break;
case 21:
//#line 86 "syntaxNew.y"
{ System.out.printf("\nwhile "); }
break;
case 22:
//#line 88 "syntaxNew.y"
{ System.out.printf("\nreturn "); }
break;
case 23:
//#line 90 "syntaxNew.y"
{ System.out.printf("\nreturn "); }
break;
case 24:
//#line 92 "syntaxNew.y"
{ System.out.printf("\ninput() ");
    memoryManager.printProgram(); }
break;
case 25:
//#line 95 "syntaxNew.y"
{ System.out.printf("\nprint() "); 
  memoryManager.printProgram(); }
break;
case 26:
//#line 98 "syntaxNew.y"
{ System.out.printf("\nUser_func() "); }
break;
case 27:
//#line 100 "syntaxNew.y"
{ System.out.printf("\nassignment"); }
break;
case 40:
//#line 119 "syntaxNew.y"
{ yyval.obj = new Expression(Types.CONSTANT, memoryManager.getAvailableShift(java.lang.Integer.BYTES), val_peek(0).ival, memoryManager);}
break;
case 43:
//#line 122 "syntaxNew.y"
{
    yyval.obj = new Expression(
        Types.COMPOSITE,
        memoryManager.getAvailableShift(java.lang.Integer.BYTES),
        val_peek(2).obj,
        val_peek(0).obj,
        Operations.ADD,
        memoryManager); 
    }
break;
case 44:
//#line 131 "syntaxNew.y"
{
  yyval.obj = new Expression(
        Types.COMPOSITE,
        memoryManager.getAvailableShift(java.lang.Integer.BYTES),
        val_peek(2).obj,
        val_peek(0).obj,
        Operations.SUB,
        memoryManager); 
    }
break;
case 45:
//#line 140 "syntaxNew.y"
{
    yyval.obj = new Expression(
        Types.COMPOSITE,
        memoryManager.getAvailableShift(java.lang.Integer.BYTES),
        val_peek(2).obj,
        val_peek(0).obj,
        Operations.MUL,
        memoryManager); 
    }
break;
case 46:
//#line 149 "syntaxNew.y"
{
    yyval.obj = new Expression(
        Types.COMPOSITE,
        memoryManager.getAvailableShift(java.lang.Integer.BYTES),
        val_peek(2).obj,
        val_peek(0).obj,
        Operations.DIV,
        memoryManager); 
    }
break;
case 48:
//#line 163 "syntaxNew.y"
{ System.out.printf("\ndeclaration_list");}
break;
case 49:
//#line 164 "syntaxNew.y"
{ System.out.printf("\ndeclaration_list");}
break;
case 50:
//#line 168 "syntaxNew.y"
{ System.out.println("int " + val_peek(3).sval + " = " + val_peek(1).ival); }
break;
case 51:
//#line 169 "syntaxNew.y"
{ System.out.printf("\nchar init declaration"); }
break;
case 52:
//#line 170 "syntaxNew.y"
{ System.out.printf("\nbool init declaration"); }
break;
case 53:
//#line 171 "syntaxNew.y"
{ System.out.printf("\nbool init declaration"); }
break;
case 54:
//#line 172 "syntaxNew.y"
{ System.out.printf("\nno init declaration"); }
break;
case 55:
//#line 173 "syntaxNew.y"
{ System.out.printf("\nno init declaration"); }
break;
case 56:
//#line 174 "syntaxNew.y"
{ System.out.printf("\nno init declaration"); }
break;
case 57:
//#line 175 "syntaxNew.y"
{ System.out.printf("\nERROR");}
break;
//#line 740 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
