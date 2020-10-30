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
package Parser;
import main.*;
import java.util.ArrayList;
import CodigoInt.*;
//#line 22 "Parser.java"




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
public final static short IDE=257;
public final static short CTE_UINT=258;
public final static short MAYOR_IGUAL=259;
public final static short MENOR_IGUAL=260;
public final static short IGUAL_IGUAL=261;
public final static short DISTINTO=262;
public final static short CTE_DOUBLE=263;
public final static short CADENA=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short OUT=269;
public final static short UINT=270;
public final static short DOUBLE=271;
public final static short NI=272;
public final static short REF=273;
public final static short FOR=274;
public final static short UP=275;
public final static short DOWN=276;
public final static short PROC=277;
public final static short FUNC=278;
public final static short RETURN=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    4,    4,    3,    3,    2,    2,
    5,    5,    5,   10,   10,    8,    8,    8,   11,    9,
    9,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   12,   12,   12,   12,   15,   15,   15,   15,   15,
   14,   14,    7,    7,    6,    6,    6,    6,    6,    6,
   21,   21,   21,   21,   21,   16,   16,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   22,   25,   25,   25,   25,   27,   27,   27,   28,   28,
   28,   28,   26,   26,   26,   26,   26,   26,   23,   23,
   30,   17,   17,   31,   29,   18,   18,   32,   32,   32,
   32,   32,   19,   19,   33,   33,   33,   20,   20,   35,
   35,   35,   35,   34,   34,   34,   36,   36,   36,   36,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    2,    2,    1,    2,    1,    1,
    3,    2,    1,    3,    2,    3,    1,    1,    2,   11,
    1,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,    1,    3,    5,    1,    7,    2,    3,    4,    4,
    2,    3,    1,    1,    2,    2,    2,    2,    2,    1,
    2,    2,    2,    2,    2,   14,    1,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
    3,    1,    3,    3,    4,    3,    3,    1,    1,    1,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    0,    4,    5,    1,    4,    4,    1,    3,    3,    2,
    4,    3,    3,    1,    2,    2,    2,    4,    1,    3,
    3,    3,    3,    3,    5,    1,    2,    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   43,   44,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
   21,    0,    0,    0,    0,    0,   50,   57,    0,   97,
  104,  109,    0,   80,   79,    0,    0,    0,    0,    0,
    0,    0,   78,    0,  116,    0,    0,    0,    0,    0,
    0,    0,    7,    0,    0,    0,   82,    0,    5,    8,
   17,    0,   18,   15,   12,   51,   45,   52,   46,   53,
   47,   54,   48,   55,   49,    0,    0,  118,    0,    0,
  112,    0,    0,   81,  117,    0,    0,    0,    0,    0,
    0,  111,    0,    0,   98,    0,    0,  102,    0,    0,
    0,    0,    0,    0,    0,    0,   35,    0,    3,  110,
   14,   19,   11,    0,    0,    0,  120,  114,    0,  108,
    0,    0,   76,   77,    0,    0,   95,   86,   87,   85,
   88,   83,   84,    0,  101,   96,    0,    0,    0,    0,
    0,    0,    0,   41,    0,    0,    0,    0,   16,   92,
    0,   75,  119,    0,    0,    0,    0,    0,    0,    0,
   42,    0,    0,    0,    0,    0,    0,   38,    0,   93,
  115,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   39,   40,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   89,   90,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   24,
   25,   27,   30,    0,   29,   28,   26,   23,   22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   58,   60,   63,   65,   67,   69,    0,
   68,   66,   64,   62,   61,   59,   56,
};
final static short yydgoto[] = {                         11,
   12,   60,   14,   15,   16,   17,   18,   62,   19,   20,
   63,  105,   21,  106,  107,   22,   23,   24,   25,   26,
   27,   93,  204,   28,   94,  134,   42,   43,   29,  115,
   77,   30,   31,   44,   32,   45,
};
final static short yysindex[] = {                       -34,
  424,  -15,  -40,    0,    0,  -35,  -32,  420,  -55,  254,
    0,    0,    0,   54,    0,    0,    0, -229,  -27,    0,
    0,   71,   74,   83,   84,   90,    0,    0,  -34,    0,
    0,    0,  -46,    0,    0,   -5,  -25,  254,  123, -208,
   20,  -11,    0,  -31,    0,  254,   19,   23,    8,  -47,
  -29, -212,    0,   64,  -46,   33,    0,   20,    0,    0,
    0,  139,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -205,    0, -190,  254,
    0,   39,   20,    0,    0,  123,  123,  123,  123,   13,
 -182,    0,   41,  747,    0,   45,   47,    0, -158,  101,
 -157, -192,  -23, -148,   69,   24,    0,   70,    0,    0,
    0,    0,    0, -145, -154,  -34,    0,    0,    2,    0,
  -11,  -11,    0,    0, -141,   66,    0,    0,    0,    0,
    0,    0,    0,  254,    0,    0,   59,   63,   62,   67,
 -129, -140,  -39,    0, -138, -212,   85, -136,    0,    0,
 -137,    0,    0, -120,   20,  254,  254,  373,  254,  254,
    0,   77,   78,   26,   86,  144, -212,    0,   87,    0,
    0,   91,   94,  441,   96,   98,   99, -114, -112,  103,
 -104,  -99, -212,    0,    0,  -97, -191, -191, -191,   68,
 -191, -191, -191,   42,   44,   46, -106,   49,   50,  119,
   52,    0,    0,  -88,  -80,  -78, -224,  -76,  -74,  -73,
  -66,  420,  420,  420,   80,  420,  420,  420, -212,  420,
  152,  153,  154,  155,  -12,  158,  159,  160,  163,   95,
  105,  120,  151,  166,  188,  253,  275,    0,  286,  189,
  199,  209,  269,  -17,  276,  280,  289,  301,  303,    0,
    0,    0,    0,  302,    0,    0,    0,    0,    0,  420,
  420,  420,  420,  420,  136,  420,  420,  420,  420,  420,
    0,  327,  343,  358,  369,  394,  409,  436,  452,  468,
  486,  497,  508,    0,    0,    0,    0,    0,    0,  524,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   15,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   60,    0,    0,    0,    0,   92,    0,    0,
  112,  613,    0,    0,    0,    0,  115,    0,    0,    0,
    0,    0,    0,    7,    0,    0,    0,  128,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -177,    0,    0,    0,    0,
    0,  131,  146,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  259,    0,    0,    0,
    0,    0,    0,    0,    0,  -21,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  687,  743,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    5,    0,    0,    0,
    0,    0,    0,    0,  -37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   16,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  294,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  314,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -8,    1,   40,    0,    0,    0,  718,    0,    0,    0,
    0,  -33,    0,  666,    0,    0,    0,    0,    0,    0,
    0,  -61, -151,    0,   43,    0,    6,  -16,    0,    0,
    0,    0,    0,   17,    0,    0,
};
final static int YYTABLESIZE=1019;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         48,
   13,  164,   40,   71,   50,    9,    6,   52,   53,   92,
  103,   79,   91,  101,    2,   81,  216,  142,  108,   32,
   76,   71,   84,  265,   46,   56,   10,   61,  245,   13,
   88,   65,   40,  224,   80,   89,  205,  206,  208,  209,
  210,  211,  152,   41,   86,   37,   87,   54,   85,   33,
  202,  203,   58,   82,    7,  225,   34,    4,    5,   95,
  102,  116,   86,   98,   87,  117,  118,  146,   99,  143,
  125,  123,  124,  110,  126,    7,   91,    4,    5,  120,
   83,  127,   91,  202,  203,  135,  181,  136,    8,   94,
   91,  121,  122,    9,  172,  173,  175,  176,  177,  137,
  140,   82,   82,    9,   82,  264,   82,  151,  144,  145,
  148,  149,  190,  150,   10,  153,   13,  156,   82,    9,
  159,  157,  119,  154,   10,  160,  207,  161,  167,   67,
  170,  162,   69,  165,    9,  169,  171,  178,  179,    7,
   10,   71,   73,  194,    9,  195,  182,  186,   75,  187,
  107,  215,  188,  198,  191,   10,  192,  193,  199,    9,
  201,  139,  219,  197,  212,   10,  213,   39,  214,  221,
  105,  217,  218,  100,  220,    9,  155,  222,   59,  223,
   10,  226,  114,  227,  228,  238,  106,  183,  109,  113,
    9,  229,  240,  241,  242,  243,   10,  113,  246,  247,
  248,   55,  233,  249,  103,    9,    0,    0,    0,  100,
   78,   10,   53,   53,   53,   53,   53,   53,   53,  250,
   53,   49,    1,   47,   51,   90,   10,    9,   64,  251,
    2,   55,  163,   53,    3,    4,    5,   71,   71,    6,
    4,    5,    7,  102,  252,  244,    4,    5,   10,  102,
   32,  230,  231,  232,  234,  235,  236,  237,  277,  239,
   53,   53,   53,   53,   53,   53,   53,   53,   53,   53,
   53,    7,  254,    6,    6,  253,   37,   53,   96,    7,
   33,    2,    2,    7,    7,    7,   97,   34,    7,   90,
  255,    7,    9,    4,    5,   90,  102,  180,   39,  272,
  273,  274,  275,  276,  278,  279,  280,  281,  282,  283,
    1,  260,  256,   10,    9,   82,  290,   99,    2,  158,
    1,  261,    3,    4,    5,    9,   66,    6,    2,   68,
    7,  262,    3,    4,    5,   10,    1,    6,   70,   72,
    7,    9,  202,  203,    2,   74,   10,  107,    3,    4,
    5,    1,   31,    6,    4,    5,    7,  102,  138,    2,
  196,    1,   10,    3,    4,    5,    9,  105,    6,    2,
  100,    7,   70,    3,    4,    5,    1,  257,    6,   57,
   34,    7,    9,  106,    2,   35,  113,   10,    3,    4,
    5,  263,    1,    6,  111,  112,    7,    9,  266,  258,
    2,  103,  267,   10,    3,    4,    5,    1,    9,    6,
  259,  268,    7,    4,    5,    2,  102,   39,   10,    3,
    4,    5,    1,  269,    6,  270,  271,    7,    0,   10,
    2,  174,    0,    9,    3,    4,    5,    0,    0,    6,
    0,    0,    7,    0,    1,    0,    0,    0,    9,    0,
    0,  284,    2,    0,   10,    0,    3,    4,    5,    9,
    0,    6,    0,   37,    7,    0,    0,  285,   39,   10,
    0,    0,    0,    0,    0,    9,    0,    0,    0,    0,
   10,   40,  286,    0,   38,   39,    0,    0,    0,    0,
    0,    9,    0,  287,    0,    0,   10,    0,    0,  189,
    0,    0,    0,    0,    0,    0,    0,    9,    0,    1,
   57,   34,   10,    0,   99,    0,   35,    2,  288,    0,
    0,    3,    4,    5,   36,    9,    6,    0,   10,    7,
    0,    1,    0,  289,    0,    0,    9,    0,    0,    2,
    0,    0,    1,    3,    4,    5,   10,    9,    6,   31,
    2,    7,    0,    0,    3,    4,    5,   10,    1,    6,
  291,    0,    7,    9,    0,    0,    2,    0,   10,   70,
    3,    4,    5,    0,    0,    6,  292,    0,    7,    0,
    0,    0,    0,    1,   10,    0,    0,    0,    0,    0,
    0,    2,  293,    0,    0,    3,    4,    5,    0,    1,
    6,    0,    0,    7,    0,    0,    0,    2,    0,    0,
  294,    3,    4,    5,    1,    0,    6,    0,    0,    7,
    0,  295,    2,    0,    0,    1,    3,    4,    5,   57,
   34,    6,  296,    2,    7,   35,    0,    3,    4,    5,
    0,    0,    6,   36,    0,    7,    0,    0,  297,    0,
    1,    0,    0,   72,    0,   72,    0,   72,    2,    0,
    0,    0,    3,    4,    5,    1,    0,    6,    0,    0,
    7,   72,   72,    2,   72,    0,    1,    3,    4,    5,
   33,   34,    6,    0,    2,    7,   35,    0,    3,    4,
    5,    0,    1,    6,   36,    0,    7,   57,   34,    0,
    2,    0,    0,   35,    3,    4,    5,    0,    1,    6,
    0,   36,    7,    0,    0,    0,    2,    0,    0,    0,
    3,    4,    5,    0,    1,    6,    0,   73,    7,   73,
    0,   73,    2,    0,    0,    0,    3,    4,    5,    0,
    0,    6,    1,    0,    7,   73,   73,    0,   73,    0,
    2,    0,    0,    1,    3,    4,    5,    0,    0,    6,
    0,    2,    7,    0,    1,    3,    4,    5,  104,  104,
    6,  147,    2,    7,    0,    0,    3,    4,    5,    0,
    1,    6,    0,   74,    7,   74,    0,   74,    2,   86,
    0,   87,    3,    4,    5,    0,    0,    6,    0,    0,
    7,   74,   74,    0,   74,    0,  132,    0,  133,    0,
    0,  166,  168,    0,    0,    0,    0,    0,    0,  141,
  104,    0,    0,  104,    0,    0,    0,    0,    0,    0,
    0,  184,  185,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  200,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  104,  104,    0,    0,    0,   72,    0,
    0,   72,   72,   72,   72,    0,    0,    0,    0,    0,
    0,    0,    0,  104,  104,    0,    0,   72,   72,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  104,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  104,    0,    0,    0,
    0,    0,   73,    0,    0,   73,   73,   73,   73,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   73,   73,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   74,    0,
    0,   74,   74,   74,   74,  128,  129,  130,  131,    0,
    0,    0,    0,    0,    0,    0,    0,   74,   74,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,   58,   41,   40,   40,    0,   40,    8,   41,
   40,   58,   44,   61,    0,   41,  123,   41,   52,   41,
   29,   59,   39,   41,   40,    9,   61,  257,   41,   29,
   42,   59,   58,  258,   40,   47,  188,  189,  190,  191,
  192,  193,   41,    1,   43,   41,   45,    8,  257,   41,
  275,  276,   10,   37,   40,  207,   41,  270,  271,   41,
  273,  267,   43,   41,   45,  256,  257,   44,   61,  103,
   58,   88,   89,   41,  257,   61,   44,  270,  271,   41,
   38,   41,   44,  275,  276,   41,   61,   41,  123,  267,
  268,   86,   87,   40,  156,  157,  158,  159,  160,  258,
  258,   42,   43,   40,   45,  123,   47,  116,  257,   41,
   41,  257,  174,  268,   61,  257,  116,   59,   59,   40,
   59,   59,   80,   58,   61,   59,   59,  257,   44,   59,
  268,  272,   59,  272,   40,  272,  257,   61,   61,  125,
   61,   59,   59,  258,   40,  258,   61,   61,   59,   59,
   59,  258,   59,  258,   59,   61,   59,   59,  258,   40,
  258,   61,   44,   61,  123,   61,  123,   45,  123,  258,
   59,  123,  123,   59,  123,   40,  134,  258,  125,  258,
   61,  258,   44,  258,  258,  219,   59,   44,  125,   59,
   40,  258,   41,   41,   41,   41,   61,   59,   41,   41,
   41,  257,  123,   41,   59,   40,   -1,   -1,   -1,  257,
  257,   61,  212,  213,  214,  215,  216,  217,  218,  125,
  220,  257,  257,  264,  257,  257,   61,   40,  256,  125,
  265,  257,  272,  233,  269,  270,  271,  275,  276,  274,
  270,  271,  277,  273,  125,  258,  270,  271,   61,  273,
  272,  212,  213,  214,  215,  216,  217,  218,  123,  220,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  270,  257,  233,  267,  268,  125,  272,  277,  256,  265,
  272,  267,  268,  269,  270,  271,  264,  272,  274,  257,
  125,  277,   40,  270,  271,  257,  273,  272,   45,  260,
  261,  262,  263,  264,  265,  266,  267,  268,  269,  270,
  257,  123,  125,   61,   40,  256,  277,   59,  265,  258,
  257,  123,  269,  270,  271,   40,  256,  274,  265,  256,
  277,  123,  269,  270,  271,   61,  257,  274,  256,  256,
  277,   40,  275,  276,  265,  256,   61,  256,  269,  270,
  271,  257,   59,  274,  270,  271,  277,  273,  258,  265,
  258,  257,   61,  269,  270,  271,   40,  256,  274,  265,
  256,  277,   59,  269,  270,  271,  257,  125,  274,  257,
  258,  277,   40,  256,  265,  263,  256,   61,  269,  270,
  271,  123,  257,  274,  256,  257,  277,   40,  123,  125,
  265,  256,  123,   61,  269,  270,  271,  257,   40,  274,
  125,  123,  277,  270,  271,  265,  273,   45,   61,  269,
  270,  271,  257,  123,  274,  123,  125,  277,   -1,   61,
  265,   59,   -1,   40,  269,  270,  271,   -1,   -1,  274,
   -1,   -1,  277,   -1,  257,   -1,   -1,   -1,   40,   -1,
   -1,  125,  265,   -1,   61,   -1,  269,  270,  271,   40,
   -1,  274,   -1,   40,  277,   -1,   -1,  125,   45,   61,
   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   61,   58,  125,   -1,   61,   45,   -1,   -1,   -1,   -1,
   -1,   40,   -1,  125,   -1,   -1,   61,   -1,   -1,   59,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,  257,
  257,  258,   61,   -1,  256,   -1,  263,  265,  125,   -1,
   -1,  269,  270,  271,  271,   40,  274,   -1,   61,  277,
   -1,  257,   -1,  125,   -1,   -1,   40,   -1,   -1,  265,
   -1,   -1,  257,  269,  270,  271,   61,   40,  274,  256,
  265,  277,   -1,   -1,  269,  270,  271,   61,  257,  274,
  125,   -1,  277,   40,   -1,   -1,  265,   -1,   61,  256,
  269,  270,  271,   -1,   -1,  274,  125,   -1,  277,   -1,
   -1,   -1,   -1,  257,   61,   -1,   -1,   -1,   -1,   -1,
   -1,  265,  125,   -1,   -1,  269,  270,  271,   -1,  257,
  274,   -1,   -1,  277,   -1,   -1,   -1,  265,   -1,   -1,
  125,  269,  270,  271,  257,   -1,  274,   -1,   -1,  277,
   -1,  125,  265,   -1,   -1,  257,  269,  270,  271,  257,
  258,  274,  125,  265,  277,  263,   -1,  269,  270,  271,
   -1,   -1,  274,  271,   -1,  277,   -1,   -1,  125,   -1,
  257,   -1,   -1,   41,   -1,   43,   -1,   45,  265,   -1,
   -1,   -1,  269,  270,  271,  257,   -1,  274,   -1,   -1,
  277,   59,   60,  265,   62,   -1,  257,  269,  270,  271,
  257,  258,  274,   -1,  265,  277,  263,   -1,  269,  270,
  271,   -1,  257,  274,  271,   -1,  277,  257,  258,   -1,
  265,   -1,   -1,  263,  269,  270,  271,   -1,  257,  274,
   -1,  271,  277,   -1,   -1,   -1,  265,   -1,   -1,   -1,
  269,  270,  271,   -1,  257,  274,   -1,   41,  277,   43,
   -1,   45,  265,   -1,   -1,   -1,  269,  270,  271,   -1,
   -1,  274,  257,   -1,  277,   59,   60,   -1,   62,   -1,
  265,   -1,   -1,  257,  269,  270,  271,   -1,   -1,  274,
   -1,  265,  277,   -1,  257,  269,  270,  271,   51,   52,
  274,  106,  265,  277,   -1,   -1,  269,  270,  271,   -1,
  257,  274,   -1,   41,  277,   43,   -1,   45,  265,   43,
   -1,   45,  269,  270,  271,   -1,   -1,  274,   -1,   -1,
  277,   59,   60,   -1,   62,   -1,   60,   -1,   62,   -1,
   -1,  146,  147,   -1,   -1,   -1,   -1,   -1,   -1,  102,
  103,   -1,   -1,  106,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  166,  167,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  183,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  146,  147,   -1,   -1,   -1,  256,   -1,
   -1,  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  166,  167,   -1,   -1,  275,  276,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  183,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  219,   -1,   -1,   -1,
   -1,   -1,  256,   -1,   -1,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,   -1,
   -1,  259,  260,  261,  262,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  275,  276,
};
}
final static short YYFINAL=11;
final static short YYMAXTOKEN=279;
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
null,null,null,null,null,null,null,"IDE","CTE_UINT","MAYOR_IGUAL","MENOR_IGUAL",
"IGUAL_IGUAL","DISTINTO","CTE_DOUBLE","CADENA","IF","THEN","ELSE","END_IF",
"OUT","UINT","DOUBLE","NI","REF","FOR","UP","DOWN","PROC","FUNC","RETURN",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : sentencia",
"bloque : '{' bloque_sentencias '}'",
"bloque : error_bloque",
"error_bloque : bloque_sentencias '}'",
"error_bloque : '{' bloque_sentencias",
"bloque_sentencias : sentencia",
"bloque_sentencias : bloque_sentencias sentencia",
"sentencia : declaracion",
"sentencia : ejecucion",
"declaracion : tipo lista_de_variables ';'",
"declaracion : procedimiento ';'",
"declaracion : error_declaracion",
"error_declaracion : tipo lista_de_variables error",
"error_declaracion : procedimiento error",
"lista_de_variables : lista_de_variables ',' IDE",
"lista_de_variables : IDE",
"lista_de_variables : error_lista_de_variables",
"error_lista_de_variables : lista_de_variables IDE",
"procedimiento : PROC IDE '(' lista_de_parametros ')' NI '=' CTE_UINT '{' bloque_sentencias '}'",
"procedimiento : error_proc",
"error_proc : PROC '(' lista_de_parametros ')' NI '=' CTE_UINT '{' bloque_sentencias '}'",
"error_proc : PROC IDE lista_de_parametros ')' NI '=' CTE_UINT '{' bloque_sentencias '}'",
"error_proc : PROC IDE '(' ')' NI '=' CTE_UINT '{' bloque_sentencias '}'",
"error_proc : PROC IDE '(' lista_de_parametros NI '=' CTE_UINT '{' bloque_sentencias '}'",
"error_proc : PROC IDE '(' lista_de_parametros ')' '=' CTE_UINT '{' bloque_sentencias '}'",
"error_proc : PROC IDE '(' lista_de_parametros ')' NI CTE_UINT '{' bloque_sentencias '}'",
"error_proc : PROC IDE '(' lista_de_parametros ')' NI '=' '{' bloque_sentencias '}'",
"error_proc : PROC IDE '(' lista_de_parametros ')' NI '=' CTE_UINT bloque_sentencias '}'",
"error_proc : PROC IDE '(' lista_de_parametros ')' NI '=' CTE_UINT '{' '}'",
"error_proc : PROC IDE '(' lista_de_parametros ')' NI '=' CTE_UINT '{' bloque_sentencias",
"lista_de_parametros : param",
"lista_de_parametros : param ',' param",
"lista_de_parametros : param ',' param ',' param",
"lista_de_parametros : error_lista_de_parametros",
"error_lista_de_parametros : param ',' param ',' param ',' lista_de_parametros",
"error_lista_de_parametros : param param",
"error_lista_de_parametros : param param param",
"error_lista_de_parametros : param ',' param param",
"error_lista_de_parametros : param param ',' param",
"param : tipo IDE",
"param : REF tipo IDE",
"tipo : UINT",
"tipo : DOUBLE",
"ejecucion : control ';'",
"ejecucion : seleccion ';'",
"ejecucion : salida ';'",
"ejecucion : asignacion ';'",
"ejecucion : invocacion ';'",
"ejecucion : error_ejecucion",
"error_ejecucion : control error",
"error_ejecucion : seleccion error",
"error_ejecucion : salida error",
"error_ejecucion : asignacion error",
"error_ejecucion : invocacion error",
"control : FOR '(' IDE '=' CTE_UINT ';' condicion ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"control : error_for",
"error_for : FOR IDE '=' CTE_UINT ';' condicion ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' '=' CTE_UINT ';' condicion ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE CTE_UINT ';' condicion ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' ';' condicion ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT condicion ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' condicion inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' condicion ';' CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' condicion ';' inc_decr ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' condicion ';' inc_decr CTE_UINT '{' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' condicion ';' inc_decr CTE_UINT ')' bloque_sentencias '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' condicion ';' inc_decr CTE_UINT ')' '{' '}'",
"error_for : FOR '(' IDE '=' CTE_UINT ';' condicion ';' inc_decr CTE_UINT ')' '{' bloque_sentencias",
"condicion : expresion comparador expresion",
"expresion : termino",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : DOUBLE '(' expresion ')'",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : CTE_DOUBLE",
"factor : CTE_UINT",
"factor : '-' factor",
"factor : IDE",
"comparador : '<'",
"comparador : '>'",
"comparador : IGUAL_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
"inc_decr : UP",
"inc_decr : DOWN",
"$$1 :",
"seleccion : if_condicion bloque $$1 END_IF",
"seleccion : if_condicion bloque_else ELSE bloque END_IF",
"bloque_else : bloque",
"if_condicion : IF '(' condicion ')'",
"salida : OUT '(' CADENA ')'",
"salida : error_salida",
"error_salida : OUT CADENA ')'",
"error_salida : OUT '(' CADENA",
"error_salida : OUT CADENA",
"error_salida : OUT '(' error ')'",
"error_salida : OUT '(' ')'",
"asignacion : IDE '=' expresion",
"asignacion : error_asignacion",
"error_asignacion : IDE expresion",
"error_asignacion : '=' expresion",
"error_asignacion : IDE '='",
"invocacion : IDE '(' parametros ')'",
"invocacion : error_invocacion",
"error_invocacion : '(' parametros ')'",
"error_invocacion : IDE parametros ')'",
"error_invocacion : IDE '(' ')'",
"error_invocacion : IDE '(' parametros",
"parametros : IDE ':' IDE",
"parametros : parametros ',' IDE ':' IDE",
"parametros : error_parametros",
"error_parametros : ':' IDE",
"error_parametros : IDE IDE",
"error_parametros : parametros IDE ':' IDE",
"error_parametros : IDE ':' error",
};

//#line 336 "gramatica.y"

private Lexico lexico;
private ArrayList<String> lista_variables;
private AdmTercetos adminTerceto;

public Parser(Lexico lexico, AdmTercetos adminTerceto)
{
  this.lexico = lexico;
  this.lista_variables = new ArrayList<String>();
  this.adminTerceto = adminTerceto;
}

public int yylex(){
   Token token = this.lexico.getToken();
   if(token != null ){
   	int val = token.getId();
   	yylval = new ParserVal(token.getLexema());
   	return val;
   }
   return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}

public boolean chequearFactorNegado(){
	String lexema = yylval.sval;
	int id = Main.tSimbolos.getId(lexema);
	if(id == Lexico.CTE_UINT){
		System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una constante UINT fuera de rango");
		Main.tSimbolos.eliminarSimbolo(lexema);
	}
	else if (id == Lexico.CTE_DOUBLE) {
		double valor = -1*Double.parseDouble(lexema.replace('d','e'));
		if(( valor > 2.2250738585272014e-308 && valor < 1.7976931348623157e+308) || (valor > -1.7976931348623157e+308 && valor < -2.2250738585072014e-308) || (valor == 0.0))
                	Main.tSimbolos.modificarSimbolo(lexema, String.valueOf(valor));
                	return true;
                	}
                else {
                	System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una constante DOUBLE fuera de rango");
	               	Main.tSimbolos.eliminarSimbolo(lexema);
	 	}
	return false;
}
//#line 661 "Parser.java"
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
case 5:
//#line 23 "gramatica.y"
{System.out.println("Error sináctico: Linea " + Lexico.linea + " se detectó un bloque de sentencias mal declarado, falta '{'");}
break;
case 6:
//#line 24 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó un bloque de sentencias mal declarado, falta '}'");}
break;
case 11:
//#line 35 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una declaracion de variables");*/
					String tipoVar = val_peek(2).sval;
					lista_variables = (ArrayList<String>)val_peek(1).obj; /*controlar si ya está en la tabla*/
					for(String lexema : lista_variables){
						Main.tSimbolos.setDatosTabla(lexema,"variable",tipoVar,true);
					}
					lista_variables.clear();
					}
break;
case 14:
//#line 48 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 15:
//#line 49 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 16:
//#line 52 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + $3.sval);}*/
      		   				 lista_variables = (ArrayList<String>) val_peek(2).obj;
                                                 lista_variables.add(val_peek(0).sval);
                                                 yyval = new ParserVal(lista_variables);
                                                 }
break;
case 17:
//#line 57 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + $1.sval);}*/
                          	lista_variables.add(val_peek(0).sval);
                                yyval = new ParserVal(lista_variables);
                                }
break;
case 19:
//#line 64 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ',' entre los identificadores");}
break;
case 20:
//#line 66 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se declaró un procedimiento");}
break;
case 22:
//#line 70 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el identificador");}
break;
case 23:
//#line 71 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '('");}
break;
case 24:
//#line 72 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta lista de parametros");}
break;
case 25:
//#line 73 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta ')'");}
break;
case 26:
//#line 74 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la palabra reservada NI ");}
break;
case 27:
//#line 75 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, '=' despues de NI ");}
break;
case 28:
//#line 76 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la constante UINT ");}
break;
case 29:
//#line 77 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
break;
case 30:
//#line 78 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el bloque de sentencias");}
break;
case 31:
//#line 79 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
break;
case 36:
//#line 88 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron más parametros de los permitidos (3)");}
break;
case 37:
//#line 89 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 38:
//#line 90 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 39:
//#line 91 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 40:
//#line 92 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 41:
//#line 95 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + val_peek(0).sval);}
break;
case 42:
//#line 96 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + val_peek(1).sval);}
break;
case 43:
//#line 99 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo UINT");}*/
		yyval = new ParserVal ("UINT");}
break;
case 44:
//#line 101 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo DOUBLE");*/
     		yyval = new ParserVal ("DOUBLE");}
break;
case 51:
//#line 113 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 52:
//#line 114 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 53:
//#line 115 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 54:
//#line 116 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 55:
//#line 117 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 56:
//#line 120 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia FOR");}
break;
case 58:
//#line 124 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '('");}
break;
case 59:
//#line 125 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el identificador ");}
break;
case 60:
//#line 126 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '='");}
break;
case 61:
//#line 127 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante UINT");}
break;
case 62:
//#line 128 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 63:
//#line 129 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la condición");}
break;
case 64:
//#line 130 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 65:
//#line 131 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la palabra UP o DOWN");}
break;
case 66:
//#line 132 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante CTE_UINT");}
break;
case 67:
//#line 133 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ')'");}
break;
case 68:
//#line 134 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '{'");}
break;
case 69:
//#line 135 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el bloque de sentencias");}
break;
case 70:
//#line 136 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '}'");}
break;
case 71:
//#line 138 "gramatica.y"
{Operando op1 = (Operando)val_peek(2).obj;
                                             Operando op2 = (Operando)val_peek(0).obj;
                                             if(op1 != null && op2 !=null){
						if(op1.getTipo().equals(op2.getTipo())){
							Terceto t = new Terceto(val_peek(1).sval, op1.getValor(), op2.getValor());
							adminTerceto.agregarTerceto(t);
							yyval = new ParserVal("["+t.getNumero()+"]");
						}
						else
							System.out.println("Tipos incompatibles");
                                             }
                                             else
                                              	yyval = new ParserVal(null);}
break;
case 72:
//#line 152 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 73:
//#line 153 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una suma");
				Operando op1 = (Operando)val_peek(2).obj;
				Operando op2 = (Operando)val_peek(0).obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("+", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else
						System.out.println("Tipos incompatibles");
                                } else
                                	yyval = new ParserVal(null);}
break;
case 74:
//#line 166 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una resta");
	  			Operando op1 = (Operando)val_peek(2).obj;
                                Operando op2 = (Operando)val_peek(0).obj;
                                if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("-", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(),"["+t.getNumero()+"]"));
						}
					else
						System.out.println("Tipos incompatibles");
                                } else
                                        yyval = new ParserVal(null);}
break;
case 75:
//#line 179 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una conversión");
	  			Operando op = (Operando)val_peek(1).obj;
	  			 if(op != null)
	  				yyval = new ParserVal(new Operando("DOUBLE",op.getValor()));
	  			else
	  				yyval = new ParserVal(null);
	  			}
break;
case 76:
//#line 188 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una multiplicacion");
				Operando op1 = (Operando)val_peek(2).obj;
				Operando op2 = (Operando)val_peek(0).obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("*", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else
						System.out.println("Tipos incompatibles");
				} else
                                 	yyval = new ParserVal(null);
                                }
break;
case 77:
//#line 202 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una division");
				Operando op1 = (Operando)val_peek(2).obj;
                                Operando op2 = (Operando)val_peek(0).obj;
                                if(op1 != null && op2 !=null){
					if (op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("/", op1.getValor(), op2.getValor());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
					} else
						System.out.println("Tipos incompatibles");
                                } else
                                	yyval = new ParserVal(null);
                               }
break;
case 78:
//#line 215 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 79:
//#line 218 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante double -> " + val_peek(0).sval);
			yyval = new ParserVal(new Operando("DOUBLE", val_peek(0).sval));
			}
break;
case 80:
//#line 221 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante uint -> " + val_peek(0).sval);
                     	yyval = new ParserVal(new Operando("UINT", val_peek(0).sval));
                        }
break;
case 81:
//#line 224 "gramatica.y"
{	if(chequearFactorNegado()){
        			Operando op = (Operando)val_peek(0).obj;
        			yyval = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
break;
case 82:
//#line 228 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó el identificador -> " + val_peek(0).sval);
		if(Main.tSimbolos.getDatosTabla(val_peek(0).sval).isDeclarada())
                	yyval = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(val_peek(0).sval).getTipo(), val_peek(0).sval));
                else {
                       	System.out.println("La variable " + val_peek(0).sval +" no fue declarada");
                       	yyval = new ParserVal(null);
                }}
break;
case 83:
//#line 237 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 84:
//#line 238 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 85:
//#line 239 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 86:
//#line 240 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 87:
//#line 241 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 88:
//#line 242 "gramatica.y"
{yyval = new ParserVal("!=");}
break;
case 91:
//#line 249 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF");
								adminTerceto.desapilar();}
break;
case 93:
//#line 251 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  					adminTerceto.desapilar();}
break;
case 94:
//#line 256 "gramatica.y"
{adminTerceto.desapilar();
                     Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.apilar(t.getNumero());
                     }
break;
case 95:
//#line 262 "gramatica.y"
{System.out.println(" se leyó una sentencia IF" + val_peek(1).sval);
				if(val_peek(1).sval != null){
				Terceto t = new Terceto("BF", val_peek(1).sval, null);
				adminTerceto.agregarTerceto(t);
				adminTerceto.apilar(t.getNumero());
				}}
break;
case 96:
//#line 279 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");}
break;
case 98:
//#line 283 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
break;
case 99:
//#line 284 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
break;
case 100:
//#line 285 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
break;
case 101:
//#line 286 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
break;
case 102:
//#line 287 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
break;
case 103:
//#line 290 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una asignación al identificador -> " + val_peek(2).sval);
				if(Main.tSimbolos.getDatosTabla(val_peek(2).sval).isDeclarada()){
					String tipoIde = Main.tSimbolos.getDatosTabla(val_peek(2).sval).getTipo();
					Operando op = (Operando)val_peek(0).obj;
					if(op != null)
						if(tipoIde.equals(op.getTipo())){
							Terceto t = new Terceto("=", val_peek(2).sval, op.getValor());
							adminTerceto.agregarTerceto(t);
							yyval = new ParserVal(new Operando(tipoIde, "[" + t.getNumero()+ "]"));
						} else
							System.out.println("Los tipos son incompatibles");
				} else {
					System.out.println("La variable " + val_peek(2).sval +" no fue declarada");
					/* ver si devolver null}*/
				}}
break;
case 105:
//#line 309 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
break;
case 106:
//#line 310 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
break;
case 107:
//#line 311 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
break;
case 108:
//#line 315 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + val_peek(3).sval );}
break;
case 110:
//#line 319 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
break;
case 111:
//#line 320 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
break;
case 112:
//#line 321 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
break;
case 113:
//#line 322 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
break;
case 114:
//#line 325 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 115:
//#line 326 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 117:
//#line 330 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
break;
case 118:
//#line 331 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
break;
case 119:
//#line 332 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
break;
case 120:
//#line 333 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
break;
//#line 1291 "Parser.java"
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
