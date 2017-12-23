
%{
  import java.io.*;
%}
      
%token ID CONSTANT STRING_LITERAL CHARACTER TRUE FALSE
%token CHAR INT BOOLEAN 
%token FUNCDEFBLOCK VARBLOCK CODEBLOCK INPUT PRINT

%token IF WHILE RETURN

%left AND_OP OR_OP
%left LE_OP GE_OP EQ_OP NE_OP '<' '>'
%left '-' '+'
%left '*' '/'

%nonassoc IFX
%nonassoc ELSE

%start Program

%%

Program
  : FUNCDEFBLOCK':' func_def_list VARBLOCK':' declaration_list CODEBLOCK':' statement_list    // полная программа
  { System.out.printf("\nProgram "); }
  | VARBLOCK':' declaration_list CODEBLOCK':' statement_list                     // если нет пользовательских функций
  { System.out.printf("\nProgram "); }
  | FUNCDEFBLOCK':' func_def_list CODEBLOCK':' statement_list                   // если нет объявлений переменных
  { System.out.printf("\nProgram "); }
  | CODEBLOCK':' statement_list                                 // если нет ничего кроме кода
  { System.out.printf("\nProgram "); }
  ;

func_def_list         // "Накручиваем" объявления функций до нужного количеста
  : func_def  { System.out.printf("\nfunc_def_list "); } 
  | func_def_list func_def { System.out.printf("\nfunc_def_list "); }
  ;
func_def
  : type_specifier func_declorator compound_statement  // тип -- деклоратор   --  блок кода 
  { System.out.printf("\nfunc_def "); }               // int     foo()       { code goes here }
  ;

type_specifier                       // спецификатор типа - ничего необычного
  : INT
  { System.out.printf("\nint_specificator "); }
  | CHAR
  { System.out.printf("\nchar_specificator "); }
  | BOOLEAN
  { System.out.printf("\nboolean_specificator "); }
  ;

func_declorator
  : ID'('')'                       // пустой лист входных параметров
  { System.out.printf ("\nfunc_declorator "); }
  | ID'('parametr_declaration_list')'            // очевидно, не пустой, но это не точно
  { System.out.printf ("\nfunc_declorator "); }
  ;
parametr_declaration_list
  : type_specifier ID {System.out.printf("\nparam_declaration_list");}
  | parametr_declaration_list',' type_specifier ID {System.out.printf("\nparam_declaration_list");}
  ;

compound_statement                     // ((какая-то функциональщина на LISp) (код в скобочках (в других ( в таких '{' '}' ))))
  :'{''}'                        // лучший код - тот, которого нет
  { System.out.printf("\nempty compound_statement "); }
  |'{'statement_list'}'                // тут код уже есть... наверное
  { System.out.printf("\ncompound_statement "); }
  ;

statement_list                       // крутим - вертим - "нецензурное рифмованое слово"
  : statement  {System.out.printf("\nstatement_list");}       // statement ы - сколько раз, сколько нужно
  | statement_list statement {System.out.printf("\nstatement_list");}
  ;

statement
  : compound_statement                                 // куда ж мы без рекурсии // не знаю зачем она здесь, но пусть будет // если что-то сломается выпилить первым делом
  |';'                         // для любителей потыкать на ';' больше одного раза - пустое выражение
  { System.out.printf("\nempty statement "); }
  | expression';'                        // собственно сами выражения - не пустые
  { System.out.printf("\nexpression "); }
  | IF'('expression')'compound_statement %prec IFX     // if
  { System.out.printf("\nif <if>"); }
  | IF'('expression')'compound_statement ELSE compound_statement    
  { System.out.printf("\nif else"); }
  | WHILE'('expression')'compound_statement     // while
  { System.out.printf("\nwhile "); }
  | RETURN';'                      // return 
  { System.out.printf("\nreturn "); }
  | RETURN expression';'
  { System.out.printf("\nreturn "); }
  | INPUT'('expression')'';'               //input()
  { System.out.printf("\ninput() "); }
  | PRINT'('expression')'';'               //print()
  { System.out.printf("\nprint() "); }
  | ID'('expression')'';'                //пользовательская функция
  { System.out.printf("\nUser_func() "); }
  | ID '=' expression';'                 //присваивание
  { System.out.printf("\nassignment"); }
  //| error';'
  ;

expression                  /* Лень расписывать */
  : ID 
  | CONSTANT 
  | STRING_LITERAL 
  | CHARACTER 
  | TRUE                    // ну правда... 
  | FALSE
  | expression'+'expression         // и так ведь понятно
  | expression'-'expression
  | expression'*'expression
  | expression'/'expression
  | expression'<'expression
  | expression'>'expression
  | expression GE_OP expression
  | expression LE_OP expression
  | expression EQ_OP expression
  | expression NE_OP expression
  | expression AND_OP expression
  | expression OR_OP expression
  | '('expression')'
  | error';'
  ;

declaration_list
  : declaration { System.out.printf("\ndeclaration_list");}
  | declaration_list declaration { System.out.printf("\ndeclaration_list");}
  ;

declaration
  : INT ID'='CONSTANT';'  { System.out.printf("\nint init declaration"); }
  | CHAR ID'='CHARACTER';' { System.out.printf("\nchar init declaration"); }
  | BOOLEAN ID'='TRUE';'  { System.out.printf("\nbool init declaration"); }
  | BOOLEAN ID'='FALSE';' { System.out.printf("\nbool init declaration"); }
  | INT ID';' { System.out.printf("\nno init declaration"); }
  | CHAR ID';' { System.out.printf("\nno init declaration"); }
  | BOOLEAN ID';' { System.out.printf("\nno init declaration"); }
  | error';' { System.out.printf("\nERROR");}
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


  public static void yyerror (String error) {
  	//ignore underflow exception 
    System.out.println ("Error :" + error);
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
