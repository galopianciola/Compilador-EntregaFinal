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
import javafx.util.Pair;
//#line 23 "Parser.java"




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
    9,    9,    9,   12,   12,   14,   14,   14,   14,   14,
   14,   14,   13,   13,   13,   13,   16,   16,   16,   16,
   16,   15,   15,    7,    7,    6,    6,    6,    6,    6,
    6,   22,   22,   22,   22,   22,   17,   17,   24,   23,
   23,   28,   28,   28,   26,   26,   26,   26,   26,   26,
   26,   26,   26,   26,   27,   29,   29,   29,   29,   31,
   31,   31,   32,   32,   32,   32,   30,   30,   30,   30,
   30,   30,   25,   25,   18,   18,   18,   34,   33,   35,
   35,   35,   35,   35,   35,   35,   19,   19,   36,   36,
   36,   36,   36,   20,   20,   37,   37,   37,   21,   21,
   39,   39,   39,   39,   38,   38,   38,   40,   40,   40,
   40,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    2,    2,    1,    2,    1,    1,
    3,    2,    1,    3,    2,    3,    1,    1,    2,    4,
    3,    3,    3,    8,    1,    7,    7,    7,    7,    7,
    7,    8,    1,    3,    5,    1,    7,    2,    3,    4,
    4,    2,    3,    1,    1,    2,    2,    2,    2,    2,
    1,    2,    2,    2,    2,    2,   12,    1,    1,    3,
    1,    2,    2,    3,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,    3,    1,    3,    3,    4,    3,
    3,    1,    1,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    6,    8,    1,    1,    1,    5,
    5,    5,    5,    5,    7,    7,    4,    1,    3,    3,
    2,    4,    3,    3,    1,    2,    2,    2,    4,    1,
    3,    3,    3,    3,    3,    5,    1,    2,    2,    4,
    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   44,   45,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
    0,   25,    0,    0,    0,    0,    0,   51,   58,   97,
  108,  115,  120,    0,   84,   83,    0,    0,    0,    0,
    0,    0,    0,   82,    0,  127,   86,    0,   99,    0,
    0,    0,    0,    0,    0,    0,    0,   61,    0,    0,
    7,    0,    0,    0,    0,    5,    8,   17,    0,   18,
   15,   12,    0,    0,   52,   46,   53,   47,   54,   48,
   55,   49,   56,   50,  129,    0,    0,  123,    0,    0,
   85,  128,    0,    0,    0,    0,    0,    0,  122,    0,
    0,   90,   91,   89,   92,   87,   88,    0,    0,  109,
    0,    0,  113,   63,    0,    0,   62,    0,    0,    0,
    0,    0,    0,   36,    0,    3,  121,   14,   19,   11,
    0,   22,    0,   21,  131,  125,    0,  119,    0,    0,
   80,   81,    0,    0,    0,    0,    0,    0,    0,  112,
  107,   64,   60,    0,    0,   59,    0,    0,    0,    0,
   42,    0,    0,    0,    0,   16,   20,   79,  130,    0,
  101,  103,    0,    0,  102,  100,    0,    0,    0,    0,
   43,    0,    0,    0,    0,    0,    0,   39,    0,  126,
    0,   95,   93,   94,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   40,   41,    0,  105,    0,
    0,    0,    0,    0,    0,    0,   28,   29,   31,    0,
   30,   27,    0,   26,   96,    0,    0,    0,    0,    0,
    0,    0,   32,   24,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   37,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   67,   69,   71,   73,    0,   72,   70,
   68,   66,   65,   57,
};
final static short yydgoto[] = {                         11,
   12,   67,   14,   15,   16,   17,   18,   69,   19,   20,
   70,   21,  122,   22,  123,  124,   23,   24,   25,   26,
   27,   28,   57,  155,  195,   29,  156,   58,   50,  108,
   43,   44,   51,  174,   30,   31,   32,   45,   33,   46,
};
final static short yysindex[] = {                       192,
  -36,   42,  -28,    0,    0,   -3,   30,  461,  -50,  231,
    0,    0,    0,  216,    0,    0,    0, -226,  -45,    0,
  256,    0,  -44,   66,   91,  108,  117,    0,    0,    0,
    0,    0,    0,  -47,    0,    0,   17,  -38,  231,  124,
 -223,   45,   -2,    0,   -5,    0,    0,  114,    0,  561,
   20,   27,   60,   61,  -48, -181,   22,    0,  -22, -127,
    0,  266,  -47,   63,   45,    0,    0,    0,  103,    0,
    0,    0,  281,  315,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -210,  231,    0,   79,   45,
    0,    0,  124,  124,  124,  124,   26, -166,    0,  192,
  138,    0,    0,    0,    0,    0,    0,  231,  192,    0,
   56,   67,    0,    0, -189,  161,    0,  231, -217,   -6,
 -152,   80,   41,    0,   83,    0,    0,    0,    0,    0,
 -121,    0,  325,    0,    0,    0,   55,    0,   -2,   -2,
    0,    0, -112,  102, -141,  -40, -119,   45, -107,    0,
    0,    0,    0,  330,  106,    0,  107,  -89, -102,  -25,
    0, -101, -127,   62, -100,    0,    0,    0,    0,  -83,
    0,    0,    0, -150,    0,    0, -137,  -52, -137, -137,
    0,  119,  125,  -56,  127,  143, -127,    0,  128,    0,
  165,    0,    0,    0,  -75, -220,  -73,  -68,  -67,  -63,
  -62,  112,  -55,  -54, -127,    0,    0,   65,    0,  -93,
  160,  172,   39,  177,  349,  355,    0,    0,    0, -143,
    0,    0,  189,    0,    0,  233,  261,  -24,  279,  294,
  300,  303,    0,    0, -127,  461,  461,  461,  297,  461,
  461,  461,  461,    0,  343,  370,  385,  403,  414,  436,
  451,  480,  504,    0,    0,    0,    0,  520,    0,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  155,    0,    0,    0,    0,  118,    0,
    0,  122,   33,    0,    0,    0,    0,    0,    0,    0,
    0,  149,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    6,    0,    0,  242,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  259,  291,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  362,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -17,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  368,    0,    0,    0,    0,    0,   69,   92,
    0,    0,    0,    0,    0,    0,    0,  123,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -13,    0,    0,    0,    0,    0,    0,
    0,    0,  342,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  369,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -9,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  378,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -57,    2,   43,    0,    0,    0,  577,    0,    0,    0,
    0,    0,  -41,    0,  579,    0,    0,    0,    0,    0,
    0,    0,  374,  -91, -130,    0,   24,    0,   32,    0,
   64,   46,  390,    0,    0,    0,    0,   21,    0,    0,
};
final static int YYTABLESIZE=823;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          9,
    2,   13,   88,   38,  203,    6,  196,   41,   40,   61,
   86,   53,   56,   72,   76,  184,  239,  120,  125,   41,
   10,   41,   61,   33,   39,   49,  157,   38,   34,   64,
   68,   35,   42,   92,  159,   99,   55,  212,   98,   95,
    7,   65,  145,  147,   96,  135,  136,  197,  198,  199,
   62,  149,    4,    5,  193,  194,   87,   56,   89,    2,
  109,    7,  178,   74,    6,  213,  152,  110,  153,   60,
   90,   49,   76,   76,   61,   76,  117,   76,  160,  229,
  118,   48,    8,  143,  163,   91,   40,   93,  173,   94,
  144,   76,   76,   76,   76,  168,  150,   93,  238,   94,
  113,   13,   13,  127,  161,  187,   98,  151,   77,   77,
   13,   77,  233,   77,  234,  133,  191,  192,  137,  138,
  162,  115,   98,  165,   78,    7,  171,   77,   77,   77,
   77,   78,   78,  210,   78,  166,   78,  193,  194,  148,
  141,  142,    4,    5,  169,  119,  131,   13,  175,   80,
   78,   78,   78,   78,  100,   76,  139,  140,   40,  170,
  176,  130,   75,   75,  179,  180,   82,  181,   40,  182,
  185,  189,  220,  190,  225,   84,  118,    9,  146,  200,
  116,   75,  211,   75,  214,  201,  205,  204,  208,  215,
  216,   77,   13,  244,  217,  218,   86,   86,   10,   86,
  226,   86,  221,  222,    9,   40,   63,  111,   54,   85,
   71,   75,  227,   86,   78,  202,    1,  230,   63,  154,
   34,   35,  193,  194,    2,   10,   36,  172,    3,    4,
    5,    9,  235,    6,   37,   52,    7,   61,   61,   61,
   61,   61,   61,   61,   61,   75,  183,    4,    5,   61,
  119,   97,   10,   54,   33,    9,    2,    7,   38,   34,
    8,    6,   35,    4,    5,    7,  119,    2,    2,    7,
    7,    7,    6,    6,    7,   40,   10,    7,  245,  246,
  247,  249,  250,  251,  252,  253,   59,    8,   76,   76,
  258,   76,   76,   76,   76,    9,  228,   76,   47,   35,
  117,   76,   76,   76,   36,    9,   76,   76,   76,   76,
    4,    5,   37,  119,    8,  111,   10,  124,  114,   97,
    9,   77,  224,  112,   77,   77,   10,   77,   77,   77,
   77,    4,    5,   77,  119,   97,    9,   77,   77,   77,
   66,   10,   77,   77,   77,   77,   79,   78,   78,  114,
   78,   78,   78,   78,    9,  236,   78,   10,  128,  129,
   78,   78,   78,   81,    9,   78,   78,   78,   78,  219,
   47,   35,   83,  118,   40,   10,   36,  116,   73,   75,
   47,   35,    9,  237,   37,   10,   36,   75,  177,  231,
  126,   75,   75,   75,    1,  232,   75,   75,   75,   75,
  104,  240,    2,   10,  111,  132,    3,    4,    5,    9,
   86,    6,    4,    5,    7,  119,  241,   47,   35,  248,
  110,    1,  242,   36,    9,  243,   23,  106,  116,    2,
   10,   37,  209,    3,    4,    5,   74,  101,    6,  134,
    0,    7,    9,    0,    0,   10,    0,    0,    1,  167,
    0,    0,    0,    9,    0,    0,    2,    0,    0,    0,
    3,    4,    5,   10,    0,    6,    0,  254,    7,    0,
    0,    0,    1,    0,   10,    9,    0,    0,    0,    0,
    2,    0,    0,    0,    3,    4,    5,   47,   35,    6,
    9,    0,    7,   36,  255,    0,   10,  117,    0,    0,
    9,   37,    0,    0,    0,    0,    0,    0,    0,  256,
    0,   10,    1,    0,  124,    0,    0,    0,    0,    9,
    2,   10,    1,    0,    3,    4,    5,  257,    0,    6,
    2,    0,    7,    0,    3,    4,    5,    1,  259,    6,
   10,    0,    7,    9,    0,    2,  114,    0,    0,    3,
    4,    5,    0,    1,    6,    0,    0,    7,    0,    9,
  260,    2,    0,    0,   10,    3,    4,    5,    0,    0,
    6,    1,    0,    7,    0,  261,    0,    0,    0,    2,
   10,    1,    0,    3,    4,    5,   47,   35,    6,    2,
    0,    7,   36,    3,    4,    5,    0,  104,    6,    1,
   37,    7,    0,   93,  262,   94,    0,    2,   98,   98,
    0,    3,    4,    5,    0,    0,    6,  110,    0,    7,
  106,    0,  107,   23,  106,    0,    1,    0,  263,    0,
    0,    0,    0,   74,    2,  121,  121,    0,    3,    4,
    5,    1,    0,    6,  264,    0,    7,    0,    0,    2,
    0,    0,    0,    3,    4,    5,    0,    0,    6,    1,
    0,    7,    0,    0,    0,    0,    0,    2,    0,    0,
    1,    3,    4,    5,    0,    0,    6,    0,    2,    7,
    0,    0,    3,    4,    5,    0,    0,    6,    0,    0,
    7,    0,    1,    0,    0,  158,  121,    0,    0,  121,
    2,  164,    0,    0,    3,    4,    5,    1,    0,    6,
    0,    0,    7,    0,    0,    2,    0,    1,    0,    3,
    4,    5,    0,    0,    6,    2,    0,    7,    0,    3,
    4,    5,    0,    0,    6,    0,    1,    7,    0,  121,
  121,  186,  188,    0,    2,    0,    0,    0,    3,    4,
    5,    0,    0,    6,    0,    0,    7,    0,    0,    0,
    1,    0,  121,  121,  206,  207,    0,    0,    2,    0,
    0,    0,    3,    4,    5,    0,    1,    6,    0,    0,
    7,  121,    0,  223,    2,    0,    0,    0,    3,    4,
    5,    0,    0,    6,    0,    0,    7,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  121,    0,    0,    0,    0,    0,    0,    0,  102,
  103,  104,  105,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   41,   40,   61,    0,   59,   58,   45,    8,
   58,   40,   61,   59,   59,   41,   41,   40,   60,   58,
   61,   58,   21,   41,   61,    2,  118,   41,   41,    9,
  257,   41,    1,  257,   41,   41,   40,  258,   44,   42,
   40,   10,  100,  101,   47,  256,  257,  178,  179,  180,
    8,  109,  270,  271,  275,  276,   40,   61,   38,   59,
   41,   61,  154,   21,   59,  196,  256,   41,  258,   40,
   39,   48,   40,   41,   73,   43,  258,   45,  120,   41,
   59,   40,  123,   58,   44,   40,   45,   43,  146,   45,
  257,   59,   60,   61,   62,   41,   41,   43,  123,   45,
   41,  100,  101,   41,  257,   44,   44,   41,   40,   41,
  109,   43,  256,   45,  258,   73,  267,  268,   87,   41,
   41,   61,   44,   41,   59,  125,  268,   59,   60,   61,
   62,   40,   41,  191,   43,  257,   45,  275,  276,  108,
   95,   96,  270,  271,  257,  273,   44,  146,  268,   59,
   59,   60,   61,   62,   41,  123,   93,   94,   45,   58,
  268,   59,   40,   41,   59,   59,   59,  257,   45,  272,
  272,  272,   61,  257,  268,   59,   59,   40,   41,   61,
   59,   59,  258,   61,  258,   61,   44,   61,   61,  258,
  258,  123,  191,  235,  258,  258,   42,   43,   61,   45,
   41,   47,  258,  258,   40,   45,  257,   59,  257,  257,
  256,  256,   41,   59,  123,  272,  257,   41,  257,   59,
  257,  258,  275,  276,  265,   61,  263,  268,  269,  270,
  271,   40,   44,  274,  271,  264,  277,  236,  237,  238,
  239,  240,  241,  242,  243,  123,  272,  270,  271,  248,
  273,  257,   61,  257,  272,   40,  256,  257,  272,  272,
  123,  256,  272,  270,  271,  265,  273,  267,  268,  269,
  270,  271,  267,  268,  274,   45,   61,  277,  236,  237,
  238,  239,  240,  241,  242,  243,  257,  123,  256,  257,
  248,  259,  260,  261,  262,   40,  258,  265,  257,  258,
   59,  269,  270,  271,  263,   40,  274,  275,  276,  277,
  270,  271,  271,  273,  123,  256,   61,   59,  258,  257,
   40,  256,  258,  264,  256,  257,   61,  259,  260,  261,
  262,  270,  271,  265,  273,  257,   40,  269,  270,  271,
  125,   61,  274,  275,  276,  277,  256,  256,  257,   59,
  259,  260,  261,  262,   40,  123,  265,   61,  256,  257,
  269,  270,  271,  256,   40,  274,  275,  276,  277,  258,
  257,  258,  256,  256,   45,   61,  263,  256,  123,  257,
  257,  258,   40,  123,  271,   61,  263,  265,   59,   41,
  125,  269,  270,  271,  257,   41,  274,  275,  276,  277,
   59,  123,  265,   61,  256,  125,  269,  270,  271,   40,
  256,  274,  270,  271,  277,  273,  123,  257,  258,  123,
   59,  257,  123,  263,   40,  123,   59,   59,   55,  265,
   61,  271,  268,  269,  270,  271,   59,   48,  274,  125,
   -1,  277,   40,   -1,   -1,   61,   -1,   -1,  257,  125,
   -1,   -1,   -1,   40,   -1,   -1,  265,   -1,   -1,   -1,
  269,  270,  271,   61,   -1,  274,   -1,  125,  277,   -1,
   -1,   -1,  257,   -1,   61,   40,   -1,   -1,   -1,   -1,
  265,   -1,   -1,   -1,  269,  270,  271,  257,  258,  274,
   40,   -1,  277,  263,  125,   -1,   61,  256,   -1,   -1,
   40,  271,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  125,
   -1,   61,  257,   -1,  256,   -1,   -1,   -1,   -1,   40,
  265,   61,  257,   -1,  269,  270,  271,  125,   -1,  274,
  265,   -1,  277,   -1,  269,  270,  271,  257,  125,  274,
   61,   -1,  277,   40,   -1,  265,  256,   -1,   -1,  269,
  270,  271,   -1,  257,  274,   -1,   -1,  277,   -1,   40,
  125,  265,   -1,   -1,   61,  269,  270,  271,   -1,   -1,
  274,  257,   -1,  277,   -1,  125,   -1,   -1,   -1,  265,
   61,  257,   -1,  269,  270,  271,  257,  258,  274,  265,
   -1,  277,  263,  269,  270,  271,   -1,  256,  274,  257,
  271,  277,   -1,   43,  125,   45,   -1,  265,  267,  268,
   -1,  269,  270,  271,   -1,   -1,  274,  256,   -1,  277,
   60,   -1,   62,  256,  256,   -1,  257,   -1,  125,   -1,
   -1,   -1,   -1,  256,  265,   59,   60,   -1,  269,  270,
  271,  257,   -1,  274,  125,   -1,  277,   -1,   -1,  265,
   -1,   -1,   -1,  269,  270,  271,   -1,   -1,  274,  257,
   -1,  277,   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,
  257,  269,  270,  271,   -1,   -1,  274,   -1,  265,  277,
   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,
  277,   -1,  257,   -1,   -1,  119,  120,   -1,   -1,  123,
  265,  123,   -1,   -1,  269,  270,  271,  257,   -1,  274,
   -1,   -1,  277,   -1,   -1,  265,   -1,  257,   -1,  269,
  270,  271,   -1,   -1,  274,  265,   -1,  277,   -1,  269,
  270,  271,   -1,   -1,  274,   -1,  257,  277,   -1,  163,
  164,  163,  164,   -1,  265,   -1,   -1,   -1,  269,  270,
  271,   -1,   -1,  274,   -1,   -1,  277,   -1,   -1,   -1,
  257,   -1,  186,  187,  186,  187,   -1,   -1,  265,   -1,
   -1,   -1,  269,  270,  271,   -1,  257,  274,   -1,   -1,
  277,  205,   -1,  205,  265,   -1,   -1,   -1,  269,  270,
  271,   -1,   -1,  274,   -1,   -1,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  235,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,
  260,  261,  262,
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
"procedimiento : declaracion_proc '{' bloque_sentencias '}'",
"procedimiento : declaracion_proc bloque_sentencias '}'",
"procedimiento : declaracion_proc '{' '}'",
"procedimiento : declaracion_proc '{' bloque_sentencias",
"declaracion_proc : PROC IDE '(' lista_de_parametros ')' NI '=' CTE_UINT",
"declaracion_proc : error_declaracion_proc",
"error_declaracion_proc : PROC '(' lista_de_parametros ')' NI '=' CTE_UINT",
"error_declaracion_proc : PROC IDE lista_de_parametros ')' NI '=' CTE_UINT",
"error_declaracion_proc : PROC IDE '(' ')' NI '=' CTE_UINT",
"error_declaracion_proc : PROC IDE '(' lista_de_parametros NI '=' CTE_UINT",
"error_declaracion_proc : PROC IDE '(' lista_de_parametros ')' '=' CTE_UINT",
"error_declaracion_proc : PROC IDE '(' lista_de_parametros ')' NI CTE_UINT",
"error_declaracion_proc : PROC IDE '(' lista_de_parametros ')' NI '=' error",
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
"control : FOR '(' asignacion_for ';' condicion_for ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"control : error_for",
"condicion_for : condicion",
"asignacion_for : IDE '=' CTE_UINT",
"asignacion_for : error_asignacion_for",
"error_asignacion_for : '=' CTE_UINT",
"error_asignacion_for : IDE CTE_UINT",
"error_asignacion_for : IDE '=' error",
"error_for : FOR asignacion_for ';' condicion_for ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for condicion_for ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for ';' ';' inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for ';' condicion_for inc_decr CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for ';' condicion_for ';' CTE_UINT ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for ';' condicion_for ';' inc_decr ')' '{' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for ';' condicion_for ';' inc_decr CTE_UINT '{' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for ';' condicion_for ';' inc_decr CTE_UINT ')' bloque_sentencias '}'",
"error_for : FOR '(' asignacion_for ';' condicion_for ';' inc_decr CTE_UINT ')' '{' '}'",
"error_for : FOR '(' asignacion_for ';' condicion_for ';' inc_decr CTE_UINT ')' '{' bloque_sentencias",
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
"seleccion : IF '(' if_condicion ')' bloque_then END_IF",
"seleccion : IF '(' if_condicion ')' bloque_then ELSE bloque END_IF",
"seleccion : error_if",
"bloque_then : bloque",
"if_condicion : condicion",
"error_if : IF if_condicion ')' bloque END_IF",
"error_if : IF '(' ')' bloque END_IF",
"error_if : IF '(' if_condicion bloque END_IF",
"error_if : IF '(' if_condicion ')' END_IF",
"error_if : IF '(' if_condicion ')' bloque",
"error_if : IF '(' if_condicion ')' bloque_then ELSE END_IF",
"error_if : IF '(' if_condicion ')' bloque_then ELSE bloque",
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

//#line 500 "gramatica.y"

private Lexico lexico;
private ArrayList<String> lista_variables;
private ArrayList<String> lista_parametros;
private ArrayList<Pair<String,String>> lista_param_invocacion;
private AdmTercetos adminTerceto;
private String ambito;

public Parser(Lexico lexico, AdmTercetos adminTerceto)
{
  this.lexico = lexico;
  this.lista_variables = new ArrayList<String>();
  this.lista_parametros = new ArrayList<String>();
  this.lista_param_invocacion = new ArrayList<>();
  this.adminTerceto = adminTerceto;
  this.ambito = "main";
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
    //System.out.println("Parser: " + s);  Comentando esto no tira lo de Parser: sintax error
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

public boolean verificarParametros(ArrayList<Pair<String,String>> lista, String proc){
	int orden = 1;
	for(Pair p : lista){
		String parametroFormal = p.getKey() + "@" + proc;
		String parametroReal = p.getValue();
		if(!Main.tSimbolos.existeLexema(parametroFormal)){ //el usuario lo escribio mal en la invocacion
			return false;
		if(Main.tSimbolos.getDatosTabla(parametroFormal).getOrden() != orden)
			return false;
		if((Main.tSimbolos.getDatosTabla(parametroFormal).getTipo() != Main.tSimbolos.getDatosTabla(parametroReal).getTipo())
			return false;
		orden++;
	}
	return true;
}
//#line 652 "Parser.java"
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
//#line 24 "gramatica.y"
{System.out.println("Error sináctico: Linea " + Lexico.linea + " se detectó un bloque de sentencias mal declarado, falta '{'");}
break;
case 6:
//#line 25 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó un bloque de sentencias mal declarado, falta '}'");}
break;
case 11:
//#line 36 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una declaracion de variables");*/
					String tipoVar = val_peek(2).sval;
					lista_variables = (ArrayList<String>)val_peek(1).obj; /*controlar si ya está en la tabla*/
					for(String lexema : lista_variables){
						String nuevoLexema = lexema + "@" + ambito;
						if(!Main.tSimbolos.existeLexema(nuevoLexema)){
							Main.tSimbolos.reemplazarLexema(lexema, nuevoLexema);
							DatosTabla dt = Main.tSimbolos.getDatosTabla(nuevoLexema);
							dt.setUso("variable");
							dt.setTipo(tipoVar);
							dt.setDeclarada(true);
							Main.tSimbolos.setDatosTabla(nuevoLexema, dt);
						} else {
							System.out.println("La variable " + lexema + " ya fue declarada en este ambito");
							Main.tSimbolos.eliminarSimbolo(lexema);
							}
					}
					lista_variables.clear();
					}
break;
case 14:
//#line 60 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 15:
//#line 61 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 16:
//#line 64 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + $3.sval);}*/
      		   				 lista_variables = (ArrayList<String>) val_peek(2).obj;
                                                 lista_variables.add(val_peek(0).sval);
                                                 yyval = new ParserVal(lista_variables);
                                                 }
break;
case 17:
//#line 69 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + $1.sval);}*/
                          lista_variables.add(val_peek(0).sval);
                          yyval = new ParserVal(lista_variables);
                                }
break;
case 19:
//#line 76 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ',' entre los identificadores");}
break;
case 20:
//#line 78 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se declaró un procedimiento");
							if(val_peek(3).sval != null){ /* se declaro todo bien*/
								ambito = ambito.substring(0,ambito.lastIndexOf("@"+val_peek(3).sval));
								Terceto t = new Terceto(FinProc, val_peek(3).sval, null);}
							}
break;
case 21:
//#line 83 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
break;
case 22:
//#line 84 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el bloque de sentencias");}
break;
case 23:
//#line 85 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
break;
case 24:
//#line 88 "gramatica.y"
{String nuevoLexema = val_peek(6).sval + "@" + ambito;
				if(!Main.tSimbolos.existeLexema(nuevoLexema)){
					Main.tSimbolos.reemplazarLexema(val_peek(6).sval, nuevoLexema);
					DatosTabla dt = Main.tSimbolos.getDatosTabla(nuevoLexema);
					dt.setUso("nombreProcedimiento");
					dt.setLlamadosMax(val_peek(0).sval);
					Main.tSimbolos.setDatosTabla(nuevoLexema, dt);
					lista_parametros = (ArrayList<String>)val_peek(4).obj;
					if(!lista_parametros.isEmpty()){
						int posicion = 1;
						for(String parametro : lista_parametros){
							Main.tSimbolos.reemplazarLexema(parametro, parametro +"@"+val_peek(6).sval);
							Main.tsimbolos.getDatosTabla(parametro +"@"+val_peek(6).sval).setOrden(posicion);
							posicion++;
						}
						ambito = ambito + "@"+ val_peek(6).sval;
						Tercetos t = new Terceto(PROC, nuevoLexema, null);
						adminTerceto.agregarTerceto(t);
						adminTerceto.agregarProcedimiento(nuevoLexema);
						yyval = new ParserVal(nuevoLexema); /* para corroborar q el proc se declaro bien (no se si va)*/
					}
					else
						yyval = new ParserVal(null); /* Hay 2 parametros con el mismo ide*/
				} else {
					System.out.print("El procedimiento "+ val_peek(6).sval + " ya fue declarado en este ambito");
					yyval = new ParserVal(null);
					}
				}
break;
case 26:
//#line 119 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el identificador");}
break;
case 27:
//#line 120 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '('");}
break;
case 28:
//#line 121 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta lista de parametros");}
break;
case 29:
//#line 122 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta ')'");}
break;
case 30:
//#line 123 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la palabra reservada NI ");}
break;
case 31:
//#line 124 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, '=' despues de NI ");}
break;
case 32:
//#line 125 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la constante UINT ");}
break;
case 33:
//#line 140 "gramatica.y"
{lista_parametros.clear();
			     lista_parametros.add(val_peek(0).sval);
			     yyval = new ParserVal(lista_parametros);}
break;
case 34:
//#line 143 "gramatica.y"
{lista_parametros.clear();
		    			if(!val_peek(2).sval.equals(val_peek(0).sval)){
						lista_parametros.add(val_peek(2).sval);
						lista_parametros.add(val_peek(0).sval);
					} else
						System.out.println("No puede haber dos parametros con el mismo IDE");
					yyval = new ParserVal(lista_parametros);}
break;
case 35:
//#line 150 "gramatica.y"
{lista_parametros.clear();
		    				 if(!val_peek(4).sval.equals(val_peek(2).sval) && !val_peek(4).sval.equals(val_peek(0).sval) && !val_peek(2).sval.equals(val_peek(0).sval)){
							lista_parametros.add(val_peek(4).sval);
							lista_parametros.add(val_peek(2).sval);
							lista_parametros.add(val_peek(0).sval);
						 } else {
							System.out.println("No puede haber dos parametros con el mismo IDE");}
		    				 yyval = new ParserVal(lista_parametros);}
break;
case 37:
//#line 161 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron más parametros de los permitidos (3)");}
break;
case 38:
//#line 162 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 39:
//#line 163 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 40:
//#line 164 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 41:
//#line 165 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 42:
//#line 168 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + $2.sval);*/
		  DatosTabla dt = Main.tSimbolos.getDatosTabla(val_peek(0).sval);
		  dt.setUso("nombreParametro");
		  dt.setTipo(val_peek(1).sval);
		  Main.tSimbolos.setDatosTabla(val_peek(0).sval, dt);
		  yyval = new ParserVal(val_peek(0).sval);}
break;
case 43:
//#line 175 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + $2.sval);*/
      		  DatosTabla dt = Main.tSimbolos.getDatosTabla(val_peek(0).sval);
                  dt.setUso("nombreParametro");
                  dt.setTipo(val_peek(1).sval);
                  dt.setParametroRef(true);
                  Main.tSimbolos.setDatosTabla(val_peek(0).sval, dt);
                  yyval = new ParserVal(val_peek(0).sval);}
break;
case 44:
//#line 184 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo UINT");}*/
		yyval = new ParserVal ("UINT");}
break;
case 45:
//#line 186 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo DOUBLE");*/
     		yyval = new ParserVal ("DOUBLE");}
break;
case 52:
//#line 198 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 53:
//#line 199 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 54:
//#line 200 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 55:
//#line 201 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 56:
//#line 202 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 57:
//#line 205 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia FOR");
							if(val_peek(9).sval != null){
								Terceto t = new Terceto(val_peek(5).sval,val_peek(9).sval,val_peek(4).sval);
								adminTerceto.agregarTerceto(t);
								t = new Terceto("BI", null, null);
								adminTerceto.agregarTerceto(t);
								adminTerceto.desapilar(); /*para completar BF*/
								adminTerceto.desapilarFor();
							}}
break;
case 59:
//#line 217 "gramatica.y"
{if(val_peek(0).sval != null){
				Terceto t = new Terceto("BF", val_peek(0).sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	adminTerceto.apilar(t.getNumero());}
                          }
break;
case 60:
//#line 223 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una asignaci�n al identificador -> " + val_peek(2).sval);
                                  String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(2).sval, ambito);
                                  if(ambitoVariable != null) {
                            		String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
                                        if(tipoIde.equals("UINT")){
                                		Terceto t = new Terceto("=", ambitoVariable, val_peek(0).sval);
                                		adminTerceto.agregarTerceto(t);
                                		adminTerceto.apilar(t.getNumero()+1);
                                		yyval = new ParserVal(ambitoVariable);
                                	} else
                                		System.out.println("Los tipos son incompatibles");
                                  } else {
                                	System.out.println("La variable " + val_peek(2).sval +" no fue declarada");
                                	yyval = new ParserVal(null);}
                              	  }
break;
case 61:
//#line 238 "gramatica.y"
{yyval = new ParserVal(null);}
break;
case 62:
//#line 241 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el identificador");}
break;
case 63:
//#line 242 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '='");}
break;
case 64:
//#line 243 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante UINT");}
break;
case 65:
//#line 246 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '('");}
break;
case 66:
//#line 247 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 67:
//#line 248 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la condición");}
break;
case 68:
//#line 249 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 69:
//#line 250 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la palabra UP o DOWN");}
break;
case 70:
//#line 251 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante CTE_UINT");}
break;
case 71:
//#line 252 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ')'");}
break;
case 72:
//#line 253 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '{'");}
break;
case 73:
//#line 254 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el bloque de sentencias");}
break;
case 74:
//#line 255 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '}'");}
break;
case 75:
//#line 258 "gramatica.y"
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
case 76:
//#line 272 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 77:
//#line 273 "gramatica.y"
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
case 78:
//#line 286 "gramatica.y"
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
case 79:
//#line 299 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una conversión");
	  			Operando op = (Operando)val_peek(1).obj;
	  			if(op != null)
	  				if(op.getTipo() == "UINT")
	  					yyval = new ParserVal(new Operando("DOUBLE",op.getValor()));
	  				else{
	  					System.out.println("Error: no se permite convertir un double");
	  					yyval = new ParserVal(null);}
	  			else
	  				yyval = new ParserVal(null);
	  			}
break;
case 80:
//#line 312 "gramatica.y"
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
case 81:
//#line 326 "gramatica.y"
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
case 82:
//#line 339 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 83:
//#line 342 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante double -> " + val_peek(0).sval);
			yyval = new ParserVal(new Operando("DOUBLE", val_peek(0).sval));
			}
break;
case 84:
//#line 345 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante uint -> " + val_peek(0).sval);
                     	yyval = new ParserVal(new Operando("UINT", val_peek(0).sval));
                        }
break;
case 85:
//#line 348 "gramatica.y"
{	if(chequearFactorNegado()){
        			Operando op = (Operando)val_peek(0).obj;
        			yyval = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
break;
case 86:
//#line 352 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó el identificador -> " + val_peek(0).sval);
		String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
		if(ambitoVariable != null)
                	yyval = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo(), ambitoVariable));
                else {
                       	System.out.println("La variable " + val_peek(0).sval +" no fue declarada");
                       	yyval = new ParserVal(null);
                }}
break;
case 87:
//#line 362 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 88:
//#line 363 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 89:
//#line 364 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 90:
//#line 365 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 91:
//#line 366 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 92:
//#line 367 "gramatica.y"
{yyval = new ParserVal("!=");}
break;
case 93:
//#line 370 "gramatica.y"
{yyval = new ParserVal("+");}
break;
case 94:
//#line 371 "gramatica.y"
{yyval = new ParserVal("-");}
break;
case 95:
//#line 374 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF");
								adminTerceto.desapilar();}
break;
case 96:
//#line 379 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  			                                   adminTerceto.desapilar();}
break;
case 98:
//#line 384 "gramatica.y"
{Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.desapilar();
                     adminTerceto.apilar(t.getNumero());
                     }
break;
case 99:
//#line 391 "gramatica.y"
{/*System.out.println(" se leyó una sentencia IF" + $1.sval);*/
				if(val_peek(0).sval != null){
					Terceto t = new Terceto("BF", val_peek(0).sval, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.apilar(t.getNumero());
				}}
break;
case 100:
//#line 399 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
break;
case 101:
//#line 400 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
break;
case 102:
//#line 401 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
break;
case 103:
//#line 402 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
break;
case 104:
//#line 403 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
break;
case 105:
//#line 406 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 106:
//#line 407 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
break;
case 107:
//#line 411 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");*/
			Terceto t = new Terceto("OUT", val_peek(1).sval, null);
			adminTerceto.agregarTerceto(t);}
break;
case 109:
//#line 417 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
break;
case 110:
//#line 418 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
break;
case 111:
//#line 419 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
break;
case 112:
//#line 420 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
break;
case 113:
//#line 421 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
break;
case 114:
//#line 424 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una asignación al identificador -> " + val_peek(2).sval);
				String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(2).sval, ambito);
				if(ambitoVariable != null){
					String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
					Operando op = (Operando)val_peek(0).obj;
					if(op != null)
						if(tipoIde.equals(op.getTipo())){
							Terceto t = new Terceto("=", ambitoVariable, op.getValor());
							adminTerceto.agregarTerceto(t);
							yyval = new ParserVal(new Operando(tipoIde, "[" + t.getNumero()+ "]"));
						} else
							System.out.println("Los tipos son incompatibles");
				} else {
					System.out.println("La variable " + val_peek(2).sval +" no fue declarada");
					/* ver si devolver null}*/
				}}
break;
case 116:
//#line 443 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
break;
case 117:
//#line 444 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
break;
case 118:
//#line 445 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
break;
case 119:
//#line 449 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + $1.sval );*/
				   lista_param_invocacion = (ArrayList<Pair<String, String>>)val_peek(1).obj;
			  	   if(!lista_param_invocacion.isEmpty()){ /* Hubo un error mas abajo*/
			  	    	String ambitoProc = Main.tSimbolos.verificarAmbito(val_peek(3).sval, ambito);
			  	    	if(ambitoProc != null && this.verificarParametros(lista_param_invocacion, val_peek(3).sval) && main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosActuales() < main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosMax()){
			  	    		for(Pair p : lista_param_invocacion){
			  	    			Terceto t = new Terceto("=" ,p.getKey()+"@"+val_peek(3).sval, p.getValue());
			  	    			adminTerceto.agregarTerceto(t);
			  	    		}
			  	    		Terceto t = new Terceto("INV", ambitoProc, null); /*ver como guardar linea inicial de procedimiento.*/
			  	    		Main.tSimbolos.getDatosTabla(ambitoProc).incrementarLlamados();
			  	    	} else
			  	    		System.out.println("El procedimiento "+val_peek(3).sval+" esta fuera de alcance");
			  	   }}
break;
case 121:
//#line 466 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
break;
case 122:
//#line 467 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
break;
case 123:
//#line 468 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
break;
case 124:
//#line 469 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
break;
case 125:
//#line 472 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + $1.sval +" y " +$3.sval);*/
			  lista_param_invocacion.clear();
			  String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
			  if(ambitoVariable != null){
			  	lista_param_invocacion.add(new Pair<String,String>(val_peek(2).sval, ambitoVariable));
			  	yyval = new ParserVal(lista_param_invocacion);} /* esto no se si como seria pq hay 2 listas :'(*/
			  else
			  	System.out.println("La variable "+val_peek(0).sval+ "no se encuentra en el ambito");
			  }
break;
case 126:
//#line 481 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + $3.sval +" y " +$5.sval);*/
                               	lista_param_invocacion = (ArrayList<Pair<String, String>>)val_peek(4).obj;
                               	if(!lista_param_invocacion.isEmpty()){
                               		String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
                                        if(ambitoVariable != null){
                                        	lista_param_invocacion.add(new Pair<String,String>(val_peek(2).sval, ambitoVariable));
                                    		yyval = new ParserVal(lista_param_invocacion);
                                     	} else
                                        	System.out.println("La variable "+val_peek(0).sval+ " no se encuentra en el ambito");
                                }}
break;
case 128:
//#line 494 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
break;
case 129:
//#line 495 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
break;
case 130:
//#line 496 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
break;
case 131:
//#line 497 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
break;
//#line 1478 "Parser.java"
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
