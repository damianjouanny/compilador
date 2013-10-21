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






//#line 2 "gramatica.y"
package sintactico;

import lexico.*; 
import herramientaerror.*;
import java.util.Hashtable;

//#line 24 "Parser.java"




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
public final static short IF=257;
public final static short ELSE=258;
public final static short THEN=259;
public final static short BEGIN=260;
public final static short END=261;
public final static short PRINT=262;
public final static short FUNCTION=263;
public final static short RETURN=264;
public final static short ID=265;
public final static short CTE=266;
public final static short FIN=267;
public final static short STRING=268;
public final static short INT=269;
public final static short COMPARADOR=270;
public final static short FOR=271;
public final static short LOWER_THAN_ELSE=272;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    3,    3,    6,    6,    4,    4,
    5,    5,   10,   10,    8,    8,    9,    9,    2,    2,
    2,    2,    7,    7,   13,   13,   13,   13,   13,   15,
   11,   11,   12,   12,   12,   12,   16,   16,   14,   14,
   14,   14,   14,   18,   18,   19,   19,   19,   23,   21,
   21,   21,   22,   22,   22,   24,   20,   17,   17,   17,
   25,   25,   25,   26,   26,   26,   27,   27,
};
final static short yylen[] = {                            2,
    3,    0,    2,    1,    9,    8,    0,    2,    2,    3,
    0,    2,    0,    1,    1,    2,    1,    3,    0,    1,
    2,    3,    0,    1,    1,    3,    2,    2,    3,    5,
    1,    2,    1,    1,    2,    2,    2,    5,    1,    1,
    1,    1,    1,    4,    6,    5,    4,    3,    5,    5,
    4,    3,    3,    2,    1,    3,    4,    3,    3,    1,
    3,    3,    1,    1,    1,    3,    1,    2,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    0,    0,   15,    0,    0,
    3,    4,    0,    0,   31,   43,   39,   40,   41,   42,
   55,    0,    0,    0,    0,    0,    0,    0,   16,    0,
    0,    1,   17,    0,   21,    0,   32,   64,   67,    0,
    0,    0,    0,    0,   63,   65,    0,   52,    0,    0,
   14,    0,    0,    0,   48,    0,   10,    0,   22,    0,
   68,    0,    0,    0,   53,    0,    0,    0,    0,    0,
   25,    0,   51,    0,    0,    0,   57,    0,    0,   47,
   18,   66,    0,    0,    0,   61,   62,   28,    0,   27,
    0,   50,    0,   12,   30,    0,   46,   29,   26,   45,
    7,    0,    0,    0,    0,   49,    0,    8,    0,    0,
   33,   34,    6,    0,   37,    5,   35,   36,    0,    0,
   38,
};
final static short yydgoto[] = {                          1,
    2,   10,   11,   12,   74,  104,  109,   13,   34,   52,
   14,  110,   70,   71,   16,  112,   42,   17,   18,   19,
   20,   23,   55,   43,   44,   45,   46,
};
final static short yysindex[] = {                         0,
    0, -111,  -31,  -37, -249,  -33, -231,    0,  -14, -227,
    0,    0, -221, -143,    0,    0,    0,    0,    0,    0,
    0,   20, -213, -194, -204,   28, -196,   20,    0, -195,
 -195,    0,    0,  -32,    0, -176,    0,    0,    0,   20,
 -181,   11,   45,    1,    0,    0, -122,    0,  -28, -254,
    0,   46,   -6,   29,    0,   22,    0, -174,    0,   14,
    0,   20,   20,   20,    0,   20,   20,  -88, -163, -153,
    0,   50,    0,   70, -150,   58,    0,   20, -122,    0,
    0,    0,    6,    1,    1,    0,    0,    0, -173,    0,
 -122,    0, -118,    0,    0,    2,    0,    0,    0,    0,
    0, -135,   20, -101,   -2,    0,  -29,    0, -137,  -86,
    0,    0,    0,   20,    0,    0,    0,    0,   86,   71,
    0,
};
final static short yyrindex[] = {                         0,
    0, -134,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -133,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   95,    0,    0,    0,
    0,    0,    0, -190,    0,    0,    0,    0,    0,    0,
    0,    0, -120,  -41,    0,    0,    0,    0,    0,   96,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -161,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    9,  -24,   -7,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -117,    0,    0,    0,    0,    0, -114,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,  127,   44,    0,    0,    0,  100,    0,    0,
   85,    0,  -50,    8,    0,   49,  -20,    0,    0,    0,
    0,    0,  124,   59,  -40,  -34,    0,
};
final static int YYTABLESIZE=293;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         60,
   69,   60,   25,   60,  102,   80,   27,   53,   22,   15,
  114,   58,   72,    7,    8,   26,   58,   60,   58,   60,
   58,   37,   84,   85,   29,   31,   57,   28,   97,  115,
   73,   86,   87,   59,   58,   59,   63,   59,   64,   32,
  100,   83,   66,   33,   63,   47,   64,   67,   63,   56,
   64,   59,   77,   63,   82,   64,   63,   96,   64,   40,
  103,   48,   79,   49,   41,    9,    9,   50,   51,   54,
    9,    9,    9,    9,    9,   15,    9,    9,    9,   59,
    9,   60,   98,    3,   61,   65,   76,   99,    4,   78,
   81,    6,   90,  119,   44,   44,   37,    9,   58,   44,
   44,   44,   44,   44,   91,   44,   44,   44,   92,   44,
   93,  111,   35,    3,   94,   59,   95,  117,    4,    5,
  105,    6,  113,  116,    7,    8,  120,    9,   63,  121,
   64,   56,   19,   20,    3,   13,   11,   68,   54,    4,
   36,  101,    6,   23,   69,    3,   24,  108,    9,   75,
    4,    5,   89,    6,   56,    3,    7,    8,  118,    9,
    4,  106,  107,    6,    0,    0,    7,    8,    3,    9,
    3,    0,   88,    4,    0,    4,    6,  107,    6,    0,
    0,    0,    9,    0,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   60,   60,   60,   60,   60,   60,
   60,   60,   60,   60,   21,   60,   60,   60,   60,   60,
   24,   58,   58,   58,   58,   58,   58,   58,   58,   58,
   58,   30,   58,   58,   58,   58,   58,    0,   59,   59,
   59,   59,   59,   59,   59,   59,   59,   59,    0,   59,
   59,   59,   59,   59,   56,   56,   56,   56,   56,   56,
   56,   56,   56,   56,    0,   56,   56,   56,    3,   56,
   62,   68,    0,    4,   38,   39,    6,    0,    0,    0,
    0,    0,    9,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
  123,   43,   40,   45,  123,   56,   40,   28,   40,    2,
   40,   44,   41,  268,  269,  265,   41,   59,   43,   40,
   45,   14,   63,   64,  256,   40,   59,   61,   79,   59,
   59,   66,   67,   41,   59,   43,   43,   45,   45,  267,
   91,   62,   42,  265,   43,  259,   45,   47,   43,   41,
   45,   59,   59,   43,   41,   45,   43,   78,   45,   40,
   59,  256,   41,  268,   45,  256,  257,   40,  265,  265,
  261,  262,  263,  264,  265,   68,  267,  268,  269,  256,
  271,  123,  256,  257,  266,   41,   41,  261,  262,   61,
  265,  265,  256,  114,  256,  257,   89,  271,  123,  261,
  262,  263,  264,  265,  258,  267,  268,  269,   59,  271,
   41,  104,  256,  257,  265,  123,   59,  110,  262,  263,
  256,  265,  125,  261,  268,  269,   41,  271,   43,   59,
   45,  123,  267,  267,  257,   41,   41,  260,  259,  262,
   14,  260,  265,  261,  123,  257,  261,  104,  271,   50,
  262,  263,   68,  265,   31,  257,  268,  269,  110,  271,
  262,  103,  264,  265,   -1,   -1,  268,  269,  257,  271,
  257,   -1,  261,  262,   -1,  262,  265,  264,  265,   -1,
   -1,   -1,  271,   -1,  271,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  257,  258,  259,  260,  261,
  262,  263,  264,  265,  256,  267,  268,  269,  270,  271,
  268,  256,  257,  258,  259,  260,  261,  262,  263,  264,
  265,  256,  267,  268,  269,  270,  271,   -1,  256,  257,
  258,  259,  260,  261,  262,  263,  264,  265,   -1,  267,
  268,  269,  270,  271,  256,  257,  258,  259,  260,  261,
  262,  263,  264,  265,   -1,  267,  268,  269,  257,  271,
  270,  260,   -1,  262,  265,  266,  265,   -1,   -1,   -1,
   -1,   -1,  271,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=272;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
null,"'='",null,null,null,null,null,null,null,null,null,null,null,null,null,
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
null,null,null,null,null,null,null,"IF","ELSE","THEN","BEGIN","END","PRINT",
"FUNCTION","RETURN","ID","CTE","FIN","STRING","INT","COMPARADOR","FOR",
"LOWER_THAN_ELSE",
};
final static String yyrule[] = {
"$accept : programa",
"programa : declaraciones ejecutable FIN",
"declaraciones :",
"declaraciones : declaraciones declaracion",
"declaracion : declaracion_simple",
"declaracion : FUNCTION ID '(' parametro_formal ')' BEGIN declaraciones_funcion ejecutable_funcion END",
"declaracion : FUNCTION ID '(' parametro_formal ')' '{' error '}'",
"declaraciones_funcion :",
"declaraciones_funcion : declaraciones_funcion declaracion_simple",
"declaracion_simple : tipo lista_variables",
"declaracion_simple : tipo lista_variables ';'",
"parametro_formal :",
"parametro_formal : tipo ID",
"parametro_real :",
"parametro_real : ID",
"tipo : INT",
"tipo : STRING error",
"lista_variables : ID",
"lista_variables : lista_variables ',' ID",
"ejecutable :",
"ejecutable : sentencias",
"ejecutable : sentencias error",
"ejecutable : sentencias declaracion error",
"ejecutable_funcion :",
"ejecutable_funcion : sentencias_funcion",
"bloque : sentencia",
"bloque : BEGIN sentencias END",
"bloque : '{' error",
"bloque : BEGIN END",
"bloque : BEGIN sentencias error",
"llamado_funcion : ID '(' parametro_real ')' ';'",
"sentencias : sentencia",
"sentencias : sentencias sentencia",
"sentencias_funcion : sentencia",
"sentencias_funcion : return_funcion",
"sentencias_funcion : sentencias_funcion sentencia",
"sentencias_funcion : sentencias_funcion return_funcion",
"return_funcion : RETURN ';'",
"return_funcion : RETURN '(' expresion ')' ';'",
"sentencia : sentencia_if",
"sentencia : sentencia_for",
"sentencia : sentencia_asignacion",
"sentencia : sentencia_print",
"sentencia : llamado_funcion",
"sentencia_if : IF condicion THEN bloque",
"sentencia_if : IF condicion THEN bloque ELSE bloque",
"sentencia_for : FOR '(' condicion_for ')' bloque",
"sentencia_for : FOR '(' condicion_for bloque",
"sentencia_for : FOR error condicion_for",
"condicion_for : ID '=' expresion ';' comparacion",
"sentencia_print : PRINT '(' STRING ')' ';'",
"sentencia_print : PRINT '(' STRING ';'",
"sentencia_print : PRINT STRING error",
"condicion : '(' comparacion ')'",
"condicion : '(' comparacion",
"condicion : error",
"comparacion : expresion COMPARADOR expresion",
"sentencia_asignacion : ID '=' expresion ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : constante",
"factor : '(' expresion ')'",
"constante : CTE",
"constante : '-' CTE",
};

//#line 157 "gramatica.y"

boolean finished = false;

int yylex()
{
    if (finished) {
        return 0;
    }
    
    boolean hasNext = anLexico.hasNext();
    if(!hasNext) {
        finished = true;
        return FIN;
    }

    Token t= anLexico.getNextToken();
    yylval = new ParserVal(t); //seteamos el token, como objeto de yylval
    yylval.ival = anLexico.getNroLinea();
    if(t== null)
            return YYERRCODE;
    Short s = (Short) Conversor.get(t.getPalabraReservada());

    return s.intValue();
}

void yyerror(String s)
{
    if(s.contains("under"))
        System.out.println("Error :"+s);
}

AnalizadorLexico anLexico;
public void addAnalizadorLexico( AnalizadorLexico al)
{
    this.anLexico = al;
}

EventoError eventoError;
public void addEventoError( EventoError e ) {
    this.eventoError = e;
}

public int parse() {
    return yyparse();
}

static Hashtable<String, Short> Conversor;
static {
	Conversor = new Hashtable<String, Short>();
	Conversor.put("string", STRING);
        Conversor.put("int", INT);
        Conversor.put("function", FUNCTION);
	Conversor.put("CTE", CTE);
	Conversor.put("ID", ID);
	Conversor.put("if", IF);
        Conversor.put("then", THEN);
	Conversor.put("else", ELSE);
	Conversor.put("print", PRINT);
        Conversor.put("return", RETURN);
	Conversor.put( "for", FOR);
        Conversor.put("begin", BEGIN);
	Conversor.put( "end", END);
	Conversor.put( "<=", COMPARADOR);
	Conversor.put( "==", COMPARADOR);
	Conversor.put( ">=", COMPARADOR);
	Conversor.put( "!=", COMPARADOR);
	Conversor.put( "<", COMPARADOR);
	Conversor.put( ">", COMPARADOR);
	Conversor.put( ";", new Short((short) ';'));
	Conversor.put( ",", new Short((short) ','));
	Conversor.put( "=", new Short((short) '='));
	Conversor.put( "{", new Short((short)'{'));
	Conversor.put( "}", new Short((short)'}'));
	Conversor.put( "(", new Short((short)'('));
	Conversor.put( ")", new Short((short)')'));
	Conversor.put( "+", new Short((short)'+'));
	Conversor.put( "-", new Short((short)'-'));
	Conversor.put( "*", new Short((short)'*'));
	Conversor.put( "/", new Short((short)'/'));	
}
//#line 432 "Parser.java"
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
case 6:
//#line 41 "gramatica.y"
{this.eventoError.add("No se puede iniciar bloque con llave", this.anLexico.getNroLinea() , "Sintactico", "Error"); }
break;
case 9:
//#line 48 "gramatica.y"
{this.eventoError.add("Falta ';' al final de declaracion", this.anLexico.getNroLinea() , "Sintactico", "Error"); }
break;
case 10:
//#line 49 "gramatica.y"
{ this.eventoError.add("Declaración de variables", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 16:
//#line 61 "gramatica.y"
{this.eventoError.add("Declaracion invalida", this.anLexico.getNroLinea() , "Sintactico", "Error"); }
break;
case 21:
//#line 70 "gramatica.y"
{this.eventoError.add("Sentencia mal formada", this.anLexico.getNroLinea() , "Sintactico", "Error"); }
break;
case 22:
//#line 71 "gramatica.y"
{this.eventoError.add("No puede haber sentencias declarativas fuera de la zona de declaracion", this.anLexico.getNroLinea() , "Sintactico", "Error"); }
break;
case 27:
//#line 80 "gramatica.y"
{this.eventoError.add("No se puede iniciar bloque con llave", this.anLexico.getNroLinea() , "Sintactico", "Error"); }
break;
case 29:
//#line 82 "gramatica.y"
{ this.eventoError.add("Bloque sin token de cerrado 'end'", this.anLexico.getNroLinea(), "Sintactico", "Error" ); }
break;
case 30:
//#line 85 "gramatica.y"
{ this.eventoError.add("Llamado a funcion", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 44:
//#line 109 "gramatica.y"
{ this.eventoError.add("Sentencia If Incompleta", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 45:
//#line 110 "gramatica.y"
{ this.eventoError.add("Sentencia If completa", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 46:
//#line 113 "gramatica.y"
{ this.eventoError.add("Sentencia For", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 47:
//#line 114 "gramatica.y"
{ this.eventoError.add("Falta cerrar parentesis a sentencia FOR", this.anLexico.getNroLinea(), "Sintactico", "Error" ); }
break;
case 48:
//#line 115 "gramatica.y"
{ this.eventoError.add("Falta abrir parentesis a sentencia FOR", this.anLexico.getNroLinea(), "Sintactico", "Error" ); }
break;
case 50:
//#line 121 "gramatica.y"
{ this.eventoError.add("Sentencia Print", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 51:
//#line 122 "gramatica.y"
{ this.eventoError.add("Falta cerrar parentesis a sentencia PRINT", this.anLexico.getNroLinea(), "Sintactico", "Error" ); }
break;
case 52:
//#line 123 "gramatica.y"
{ this.eventoError.add("Falta abrir parentesis a sentencia PRINT", this.anLexico.getNroLinea(), "Sintactico", "Error" ); }
break;
case 54:
//#line 127 "gramatica.y"
{ this.eventoError.add("Falta cierre parentesis en la condicion", this.anLexico.getNroLinea(), "Sintactico", "Error" ); }
break;
case 55:
//#line 128 "gramatica.y"
{ this.eventoError.add("Falta abrir parentesis en condición", this.anLexico.getNroLinea(), "Sintactico", "Error" ); }
break;
case 57:
//#line 134 "gramatica.y"
{ this.eventoError.add("Asignacion", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 58:
//#line 137 "gramatica.y"
{ this.eventoError.add("Operación de suma", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 59:
//#line 138 "gramatica.y"
{ this.eventoError.add("Operación de resta", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 61:
//#line 142 "gramatica.y"
{ this.eventoError.add("Operación de multiplicacion", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 62:
//#line 143 "gramatica.y"
{ this.eventoError.add("Operación de division", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
case 68:
//#line 153 "gramatica.y"
{ this.eventoError.add("Identificada constante negativa", this.anLexico.getNroLinea(), "Sintactico", "Regla" ); }
break;
//#line 681 "Parser.java"
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
