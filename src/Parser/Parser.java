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
    9,   13,   13,   13,   12,   12,   15,   15,   15,   15,
   15,   15,   15,   14,   14,   14,   14,   17,   17,   17,
   17,   17,   16,   16,    7,    7,    6,    6,    6,    6,
    6,    6,   23,   23,   23,   23,   23,   18,   18,   25,
   24,   24,   29,   29,   29,   27,   27,   27,   27,   27,
   27,   27,   27,   27,   27,   28,   30,   30,   30,   30,
   32,   32,   32,   33,   33,   33,   33,   31,   31,   31,
   31,   31,   31,   26,   26,   19,   19,   19,   35,   34,
   36,   36,   36,   36,   36,   36,   36,   20,   20,   37,
   37,   37,   37,   37,   21,   21,   38,   38,   38,   22,
   22,   40,   40,   40,   40,   39,   39,   39,   41,   41,
   41,   41,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    2,    2,    1,    2,    1,    1,
    3,    2,    1,    3,    2,    3,    1,    1,    2,    4,
    1,    3,    3,    3,    8,    1,    7,    7,    7,    7,
    7,    7,    8,    1,    3,    5,    1,    7,    2,    3,
    4,    4,    2,    3,    1,    1,    2,    2,    2,    2,
    2,    1,    2,    2,    2,    2,    2,   12,    1,    1,
    3,    1,    2,    2,    3,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,    3,    1,    3,    3,    4,
    3,    3,    1,    1,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    6,    8,    1,    1,    1,
    5,    5,    5,    5,    5,    7,    7,    4,    1,    3,
    3,    2,    4,    3,    3,    1,    2,    2,    2,    4,
    1,    3,    3,    3,    3,    3,    5,    1,    2,    2,
    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   45,   46,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
    0,   21,   26,    0,    0,    0,    0,    0,   52,   59,
   98,  109,  116,  121,    0,   85,   84,    0,    0,    0,
    0,    0,    0,    0,   83,    0,  128,   87,    0,  100,
    0,    0,    0,    0,    0,    0,    0,    0,   62,    0,
    0,    7,    0,    0,    0,    0,    5,    8,   17,    0,
   18,   15,   12,    0,    0,   53,   47,   54,   48,   55,
   49,   56,   50,   57,   51,  130,    0,    0,  124,    0,
    0,   86,  129,    0,    0,    0,    0,    0,    0,  123,
    0,    0,   91,   92,   90,   93,   88,   89,    0,    0,
  110,    0,    0,  114,   64,    0,    0,   63,    0,    0,
    0,    0,    0,    0,   37,    0,    3,  122,   14,   19,
   11,    0,   23,    0,   22,  132,  126,    0,  120,    0,
    0,   81,   82,    0,    0,    0,    0,    0,    0,    0,
  113,  108,   65,   61,    0,    0,   60,    0,    0,    0,
    0,   43,    0,    0,    0,    0,   16,   20,   80,  131,
    0,  102,  104,    0,    0,  103,  101,    0,    0,    0,
    0,   44,    0,    0,    0,    0,    0,    0,   40,    0,
  127,    0,   96,   94,   95,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   41,   42,    0,  106,
    0,    0,    0,    0,    0,    0,    0,   29,   30,   32,
    0,   31,   28,    0,   27,   97,    0,    0,    0,    0,
    0,    0,    0,   33,   25,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   38,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   68,   70,   72,   74,    0,   73,
   71,   69,   67,   66,   58,
};
final static short yydgoto[] = {                         11,
   12,   68,   14,   15,   16,   17,   18,   70,   19,   20,
   71,   21,   22,  123,   23,  124,  125,   24,   25,   26,
   27,   28,   29,   58,  156,  196,   30,  157,   59,   51,
  109,   44,   45,   52,  175,   31,   32,   33,   46,   34,
   47,
};
final static short yysindex[] = {                       186,
   60,  116,  -37,    0,    0,   -3,   -1,  543,  -47,  281,
    0,    0,    0,  215,    0,    0,    0, -225,  -44,    0,
  249,    0,    0,  -43,  -35,   36,   76,  103,    0,    0,
    0,    0,    0,    0,  -46,    0,    0,   -4,  -33,  281,
  -41, -217,   28,    4,    0,   20,    0,    0,   78,    0,
  567,    7,   13,  -28,  126,   97, -191,   15,    0,  -40,
 -187,    0,  260,  -46,   31,   28,    0,    0,    0,  113,
    0,    0,    0,  294,  317,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -119,  281,    0,   44,
   28,    0,    0,  -41,  -41,  -41,  -41,   23, -170,    0,
  186,  129,    0,    0,    0,    0,    0,    0,  281,  186,
    0,   73,   81,    0,    0, -209,  173,    0,  281, -129,
  -36, -137,   84,   -6,    0,   92,    0,    0,    0,    0,
    0, -118,    0,  351,    0,    0,    0,   14,    0,    4,
    4,    0,    0, -112,   90, -109,  140, -104,   28, -102,
    0,    0,    0,    0,  343,  109,    0,  112,  -82,  -96,
  -34,    0,  -95, -187,   49,  -94,    0,    0,    0,    0,
  -78,    0,    0,    0, -124,    0,    0, -121,  -16, -121,
 -121,    0,  120,  121,  -52,  122,  119, -187,    0,  123,
    0,  164,    0,    0,    0,  -73, -223,  -72,  -69,  -66,
  -65,  -63,  135,  -61,  -59, -187,    0,    0,  -58,    0,
  -60,  161,  162,    3,  174,  182,  254,    0,    0,    0,
 -141,    0,    0,  352,    0,    0,   82,  290,  -21,  292,
  300,  301,  302,    0,    0, -187,  543,  543,  543,  367,
  543,  543,  543,  543,    0,  378,  397,  407,  422,  433,
  448,  467,  493,  508,    0,    0,    0,    0,  532,    0,
    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   47,    0,    0,    0,    0,  139,
    0,    0,  148,   37,    0,    0,    0,    0,    0,    0,
    0,    0,  160,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    6,    0,    0,  231,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  246,
  287,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  299,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -24,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  305,    0,    0,    0,    0,    0,   68,
   91,    0,    0,    0,    0,    0,    0,    0,  106,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -23,    0,    0,    0,    0,    0,
    0,    0,    0,   48,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  342,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -19,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  361,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -68,    2,   42,    0,    0,    0,  628,    0,    0,    0,
    0,    0,    0,  -30,    0,  495,    0,    0,    0,    0,
    0,    0,    0,  366,  -89, -152,    0,   19,    0,  100,
    0,   79,  -27,  377,    0,    0,    0,    0,   17,    0,
    0,
};
final static int YYTABLESIZE=864;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        121,
    2,   13,   54,   41,  160,    6,  185,   89,  204,   62,
   42,   87,  114,   92,   73,   77,   34,   39,   35,  240,
   50,   36,   62,   79,   42,   65,  198,  199,  200,  158,
  126,   69,  146,  148,  213,   88,   56,  164,   61,   93,
    7,  150,  197,  230,  214,   96,  153,  110,  154,   63,
   97,  194,  195,  111,  169,   90,   94,   57,   95,    2,
  100,    7,   75,   99,    6,  179,  118,   50,  142,  143,
   94,  128,   95,  119,   99,   62,   77,   77,  174,   77,
  144,   77,    4,    5,  139,  120,  145,   99,   87,   87,
  161,   87,  188,   87,   81,   77,   77,   77,   77,   39,
   43,  239,   13,   13,   41,   87,  105,   78,   78,   66,
   78,   13,   78,  151,  234,  134,  235,   42,  101,  162,
   40,  152,   41,  211,  163,    7,   78,   78,   78,   78,
   79,   79,  166,   79,   83,   79,  136,  137,  167,   91,
    4,    5,  192,  193,  170,   76,   76,  171,   13,   79,
   79,   79,   79,  194,  195,   49,  132,   57,  172,   77,
   41,   85,  206,  176,   76,  177,   76,  180,    9,  147,
  181,  131,  140,  141,  182,  183,  186,  190,  191,    9,
  201,  202,  205,  209,  212,  215,  116,  138,  216,   10,
   78,  217,  218,   13,  219,  221,  222,  119,  223,  225,
   10,  227,  228,    9,  237,  245,  117,  226,  149,   64,
   86,   72,   76,   79,  231,   48,   36,   41,  112,  203,
   78,   37,  232,   64,   10,    9,   53,  112,   76,    4,
    5,  155,  120,    4,    5,  113,  120,  184,   62,   62,
   62,   62,   62,   62,   62,   62,   10,   34,   39,   35,
   62,    8,   36,   55,    9,   60,    2,    7,  194,  195,
  229,    6,    8,    4,    5,    7,  120,    2,    2,    7,
    7,    7,    6,    6,    7,   10,   98,    7,  246,  247,
  248,  250,  251,  252,  253,  254,    8,   98,    9,  118,
  259,   80,   77,   77,  233,   77,   77,   77,   77,    9,
   98,   77,   87,  105,  125,   77,   77,   77,    8,   10,
   77,   77,   77,   77,   99,   99,   35,   36,    4,    5,
   10,  120,   37,   78,   78,   41,   78,   78,   78,   78,
   38,   82,   78,    9,   48,   36,   78,   78,   78,   67,
   37,   78,   78,   78,   78,  115,   79,   79,   38,   79,
   79,   79,   79,   55,   10,   79,    9,  111,   84,   79,
   79,   79,   76,   24,   79,   79,   79,   79,  129,  130,
   76,   74,   48,   36,   76,   76,   76,   10,   37,   76,
   76,   76,   76,  115,  127,    1,   38,   41,    4,    5,
    9,  120,  220,    2,  119,  236,    1,    3,    4,    5,
  107,  178,    6,  117,    2,    7,    9,  173,    3,    4,
    5,   10,  238,    6,  241,  112,    7,    9,  133,   75,
    1,  117,  242,  243,  244,  102,    0,   10,    2,   48,
   36,  210,    3,    4,    5,   37,    9,    6,   10,    0,
    7,  135,    1,   38,    0,    0,    9,    0,    0,    0,
    2,    0,    0,    0,    3,    4,    5,   10,    0,    6,
    0,    9,    7,    0,    0,    0,    0,   10,    0,    0,
    0,    1,    9,    0,    0,  168,    0,    0,    0,    2,
    0,    0,   10,    3,    4,    5,  118,    9,    6,  249,
    0,    7,    0,   10,    0,    0,    0,    0,    0,    0,
    0,  125,  255,    0,    0,    1,    9,    0,   10,    0,
    0,    0,    0,    2,    0,    0,    1,    3,    4,    5,
    0,  256,    6,    0,    2,    7,    0,   10,    3,    4,
    5,  257,    9,    6,    0,    0,    7,   48,   36,    0,
    0,    0,  115,   37,    0,    0,  258,    9,    0,    0,
    1,   38,    0,   10,  111,    0,    0,  260,    2,    0,
   24,    0,    3,    4,    5,    0,    0,    6,   10,    0,
    7,    9,  261,    1,    0,    0,    0,    0,    0,    0,
    0,    2,    9,    0,    0,    3,    4,    5,    0,    0,
    6,  262,   10,    7,    0,    0,    0,  107,    0,   48,
   36,    0,    0,   10,    0,   37,    0,    1,    0,   94,
    0,   95,    0,   38,    0,    2,   75,  263,  165,    3,
    4,    5,    0,    1,    6,    0,  107,    7,  108,    0,
    0,    2,  264,    0,    1,    3,    4,    5,    0,    0,
    6,    0,    2,    7,    0,    0,    3,    4,    5,    0,
    0,    6,    0,    1,    7,    0,  265,    0,  187,  189,
    0,    2,    0,    1,    0,    3,    4,    5,    0,    0,
    6,    2,    0,    7,    0,    3,    4,    5,    1,    0,
    6,  207,  208,    7,    0,    0,    2,  122,  122,    1,
    3,    4,    5,    0,    0,    6,    0,    2,    7,    0,
  224,    3,    4,    5,    1,    0,    6,    0,    0,    7,
    0,    0,    2,    0,    0,    0,    3,    4,    5,    0,
    0,    6,    0,    1,    7,    0,    0,    0,    0,    0,
    0,    2,    0,    0,    0,    3,    4,    5,    0,    0,
    6,    0,    0,    7,    0,    0,    0,  159,  122,    1,
    0,  122,    0,    0,    0,    0,    0,    2,    0,    0,
    0,    3,    4,    5,    1,    0,    6,    0,    0,    7,
    0,    0,    2,    0,    0,    0,    3,    4,    5,    0,
    0,    6,    0,    0,    7,    0,    0,    0,    1,    0,
    0,  122,  122,    0,    0,    0,    2,    0,    0,    1,
    3,    4,    5,    0,    0,    6,    0,    2,    7,    0,
    0,    3,    4,    5,  122,  122,    6,    0,    0,    7,
    0,    0,    0,    0,    0,  103,  104,  105,  106,    0,
    0,    0,    0,  122,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  122,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   40,   45,   41,    0,   41,   41,   61,    8,
   58,   58,   41,   41,   59,   59,   41,   41,   41,   41,
    2,   41,   21,   59,   58,    9,  179,  180,  181,  119,
   61,  257,  101,  102,  258,   40,   40,   44,   40,  257,
   40,  110,   59,   41,  197,   42,  256,   41,  258,    8,
   47,  275,  276,   41,   41,   39,   43,   61,   45,   59,
   41,   61,   21,   44,   59,  155,  258,   49,   96,   97,
   43,   41,   45,   59,   44,   74,   40,   41,  147,   43,
   58,   45,  270,  271,   41,  273,  257,   44,   42,   43,
  121,   45,   44,   47,   59,   59,   60,   61,   62,   40,
    1,  123,  101,  102,   45,   59,   59,   40,   41,   10,
   43,  110,   45,   41,  256,   74,  258,   58,   41,  257,
   61,   41,   45,  192,   41,  125,   59,   60,   61,   62,
   40,   41,   41,   43,   59,   45,  256,  257,  257,   40,
  270,  271,  267,  268,  257,   40,   41,   58,  147,   59,
   60,   61,   62,  275,  276,   40,   44,   61,  268,  123,
   45,   59,   44,  268,   59,  268,   61,   59,   40,   41,
   59,   59,   94,   95,  257,  272,  272,  272,  257,   40,
   61,   61,   61,   61,  258,  258,   61,   88,  258,   61,
  123,  258,  258,  192,  258,   61,  258,   59,  258,  258,
   61,   41,   41,   40,  123,  236,   59,  268,  109,  257,
  257,  256,  256,  123,   41,  257,  258,   45,   59,  272,
  256,  263,   41,  257,   61,   40,  264,  256,  123,  270,
  271,   59,  273,  270,  271,  264,  273,  272,  237,  238,
  239,  240,  241,  242,  243,  244,   61,  272,  272,  272,
  249,  123,  272,  257,   40,  257,  256,  257,  275,  276,
  258,  256,  123,  270,  271,  265,  273,  267,  268,  269,
  270,  271,  267,  268,  274,   61,  257,  277,  237,  238,
  239,  240,  241,  242,  243,  244,  123,  257,   40,   59,
  249,  256,  256,  257,   41,  259,  260,  261,  262,   40,
  257,  265,  256,  256,   59,  269,  270,  271,  123,   61,
  274,  275,  276,  277,  267,  268,  257,  258,  270,  271,
   61,  273,  263,  256,  257,   45,  259,  260,  261,  262,
  271,  256,  265,   40,  257,  258,  269,  270,  271,  125,
  263,  274,  275,  276,  277,   59,  256,  257,  271,  259,
  260,  261,  262,  257,   61,  265,   40,   59,  256,  269,
  270,  271,  257,   59,  274,  275,  276,  277,  256,  257,
  265,  123,  257,  258,  269,  270,  271,   61,  263,  274,
  275,  276,  277,  258,  125,  257,  271,   45,  270,  271,
   40,  273,  258,  265,  256,   44,  257,  269,  270,  271,
   59,   59,  274,  256,  265,  277,   40,  268,  269,  270,
  271,   61,  123,  274,  123,  256,  277,   40,  125,   59,
  257,   56,  123,  123,  123,   49,   -1,   61,  265,  257,
  258,  268,  269,  270,  271,  263,   40,  274,   61,   -1,
  277,  125,  257,  271,   -1,   -1,   40,   -1,   -1,   -1,
  265,   -1,   -1,   -1,  269,  270,  271,   61,   -1,  274,
   -1,   40,  277,   -1,   -1,   -1,   -1,   61,   -1,   -1,
   -1,  257,   40,   -1,   -1,  125,   -1,   -1,   -1,  265,
   -1,   -1,   61,  269,  270,  271,  256,   40,  274,  123,
   -1,  277,   -1,   61,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  256,  125,   -1,   -1,  257,   40,   -1,   61,   -1,
   -1,   -1,   -1,  265,   -1,   -1,  257,  269,  270,  271,
   -1,  125,  274,   -1,  265,  277,   -1,   61,  269,  270,
  271,  125,   40,  274,   -1,   -1,  277,  257,  258,   -1,
   -1,   -1,  256,  263,   -1,   -1,  125,   40,   -1,   -1,
  257,  271,   -1,   61,  256,   -1,   -1,  125,  265,   -1,
  256,   -1,  269,  270,  271,   -1,   -1,  274,   61,   -1,
  277,   40,  125,  257,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  265,   40,   -1,   -1,  269,  270,  271,   -1,   -1,
  274,  125,   61,  277,   -1,   -1,   -1,  256,   -1,  257,
  258,   -1,   -1,   61,   -1,  263,   -1,  257,   -1,   43,
   -1,   45,   -1,  271,   -1,  265,  256,  125,  124,  269,
  270,  271,   -1,  257,  274,   -1,   60,  277,   62,   -1,
   -1,  265,  125,   -1,  257,  269,  270,  271,   -1,   -1,
  274,   -1,  265,  277,   -1,   -1,  269,  270,  271,   -1,
   -1,  274,   -1,  257,  277,   -1,  125,   -1,  164,  165,
   -1,  265,   -1,  257,   -1,  269,  270,  271,   -1,   -1,
  274,  265,   -1,  277,   -1,  269,  270,  271,  257,   -1,
  274,  187,  188,  277,   -1,   -1,  265,   60,   61,  257,
  269,  270,  271,   -1,   -1,  274,   -1,  265,  277,   -1,
  206,  269,  270,  271,  257,   -1,  274,   -1,   -1,  277,
   -1,   -1,  265,   -1,   -1,   -1,  269,  270,  271,   -1,
   -1,  274,   -1,  257,  277,   -1,   -1,   -1,   -1,   -1,
   -1,  265,   -1,   -1,   -1,  269,  270,  271,   -1,   -1,
  274,   -1,   -1,  277,   -1,   -1,   -1,  120,  121,  257,
   -1,  124,   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,  257,   -1,  274,   -1,   -1,  277,
   -1,   -1,  265,   -1,   -1,   -1,  269,  270,  271,   -1,
   -1,  274,   -1,   -1,  277,   -1,   -1,   -1,  257,   -1,
   -1,  164,  165,   -1,   -1,   -1,  265,   -1,   -1,  257,
  269,  270,  271,   -1,   -1,  274,   -1,  265,  277,   -1,
   -1,  269,  270,  271,  187,  188,  274,   -1,   -1,  277,
   -1,   -1,   -1,   -1,   -1,  259,  260,  261,  262,   -1,
   -1,   -1,   -1,  206,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  236,
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
"procedimiento : error_proc",
"error_proc : declaracion_proc bloque_sentencias '}'",
"error_proc : declaracion_proc '{' '}'",
"error_proc : declaracion_proc '{' bloque_sentencias",
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

//#line 506 "gramatica.y"

private Lexico lexico;
private ArrayList<String> lista_variables;
private ArrayList<String> lista_parametros;
private ArrayList<Pair<String,String>> lista_param_invocacion;
private AdmTercetos adminTerceto;
private String ambito;
//private String procedimiento;

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
                	{Main.tSimbolos.modificarSimbolo(lexema, String.valueOf(valor));
                	return true;
                	}
                else {
                	System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una constante DOUBLE fuera de rango");
	               	Main.tSimbolos.eliminarSimbolo(lexema);
	 	}
	}
	return false;
}

public boolean verificarParametros(String proc){
	int orden = 1;
	for(Pair p : lista_param_invocacion){
		String parametroFormal = p.getKey() + "@" + proc;
		String parametroReal = (String)p.getValue();
		if(!Main.tSimbolos.existeLexema(parametroFormal)){ //el usuario lo escribio mal en la invocacion
			System.out.println("No se reconoce el parametro "+ parametroFormal);
			return false;}
		if(Main.tSimbolos.getDatosTabla(parametroFormal).getOrden() != orden){
			System.out.println("Los parametros no estan en el orden correcto");
			return false;}
		if(Main.tSimbolos.getDatosTabla(parametroFormal).getTipo() != Main.tSimbolos.getDatosTabla(parametroReal).getTipo()){
			System.out.println("Los tipos de los parametros reales y formales no son iguales");
			return false;}
		orden++;
	}
	return true;
}
//#line 668 "Parser.java"
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
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyo el identificador -> " + val_peek(0).sval);
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
								ambito = ambito.substring(0,ambito.lastIndexOf("@"));
								Terceto t = new Terceto("FinProc", val_peek(3).sval, null);
								adminTerceto.agregarTerceto(t);
								}
							}
break;
case 21:
//#line 85 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se declar� un procedimiento");
                            if(val_peek(0).sval != null){ /* se declaro todo bien*/
                 		ambito = ambito.substring(0,ambito.lastIndexOf("@"));
				Terceto t = new Terceto("FinProc", val_peek(0).sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	}
                          }
break;
case 22:
//#line 95 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
break;
case 23:
//#line 96 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el bloque de sentencias");}
break;
case 24:
//#line 97 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
break;
case 25:
//#line 100 "gramatica.y"
{
			lista_parametros = (ArrayList<String>)val_peek(4).obj;
			if(!lista_parametros.isEmpty()){
				String nuevoLexema = val_peek(6).sval + "@" + ambito;
				if(!Main.tSimbolos.existeLexema(nuevoLexema)){
					Main.tSimbolos.reemplazarLexema(val_peek(6).sval, nuevoLexema);
					DatosTabla dt = Main.tSimbolos.getDatosTabla(nuevoLexema);
					dt.setUso("nombreProcedimiento");
					dt.setLlamadosMax(Integer.parseInt(val_peek(0).sval));
					dt.setCantParametros(lista_parametros.size());
					Main.tSimbolos.setDatosTabla(nuevoLexema, dt);

					int posicion = 1;
					for(String parametro : lista_parametros){
						Main.tSimbolos.reemplazarLexema(parametro, parametro +"@"+val_peek(6).sval);
						Main.tSimbolos.getDatosTabla(parametro +"@"+val_peek(6).sval).setOrden(posicion);
						posicion++;
					}
					ambito = ambito + "@"+ val_peek(6).sval;
					Terceto t = new Terceto("PROC", nuevoLexema, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.agregarProcedimiento(nuevoLexema);
					yyval = new ParserVal(nuevoLexema); /* para corroborar q el proc se declaro bien (no se si va)*/
				} else {
					System.out.print("El procedimiento "+ val_peek(6).sval + " ya fue declarado en este ambito");
					yyval = new ParserVal(null);
					}
			}}
break;
case 27:
//#line 131 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta el identificador");}
break;
case 28:
//#line 132 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '('");}
break;
case 29:
//#line 133 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta lista de parametros");}
break;
case 30:
//#line 134 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta ')'");}
break;
case 31:
//#line 135 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la palabra reservada NI ");}
break;
case 32:
//#line 136 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, '=' despues de NI ");}
break;
case 33:
//#line 137 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la constante UINT ");}
break;
case 34:
//#line 140 "gramatica.y"
{lista_parametros.clear();
			     lista_parametros.add(val_peek(0).sval);
			     yyval = new ParserVal(lista_parametros);}
break;
case 35:
//#line 143 "gramatica.y"
{lista_parametros.clear();
		    			if(!val_peek(2).sval.equals(val_peek(0).sval)){
						lista_parametros.add(val_peek(2).sval);
						lista_parametros.add(val_peek(0).sval);
					} else
						System.out.println("No puede haber dos parametros con el mismo IDE");
					yyval = new ParserVal(lista_parametros);}
break;
case 36:
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
//#line 158 "gramatica.y"
{lista_parametros.clear();
		    				yyval = new ParserVal(lista_parametros);}
break;
case 38:
//#line 162 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron más parametros de los permitidos (3)");}
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
//#line 166 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 43:
//#line 169 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + val_peek(0).sval);
		  DatosTabla dt = Main.tSimbolos.getDatosTabla(val_peek(0).sval);
		  dt.setUso("nombreParametro");
		  dt.setTipo(val_peek(1).sval);
		  Main.tSimbolos.setDatosTabla(val_peek(0).sval, dt);
		  yyval = new ParserVal(val_peek(0).sval);}
break;
case 44:
//#line 176 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se leyó el parametro -> " + val_peek(1).sval);
      		  DatosTabla dt = Main.tSimbolos.getDatosTabla(val_peek(0).sval);
                  dt.setUso("nombreParametro");
                  dt.setTipo(val_peek(1).sval);
                  dt.setParametroRef(true);
                  Main.tSimbolos.setDatosTabla(val_peek(0).sval, dt);
                  yyval = new ParserVal(val_peek(0).sval);}
break;
case 45:
//#line 185 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo UINT");
		yyval = new ParserVal ("UINT");}
break;
case 46:
//#line 187 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un tipo DOUBLE");
     		yyval = new ParserVal ("DOUBLE");}
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
//#line 203 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 58:
//#line 206 "gramatica.y"
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
case 60:
//#line 218 "gramatica.y"
{if(val_peek(0).sval != null){
				Terceto t = new Terceto("BF", val_peek(0).sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	adminTerceto.apilar(t.getNumero());}
                          }
break;
case 61:
//#line 224 "gramatica.y"
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
case 62:
//#line 239 "gramatica.y"
{yyval = new ParserVal(null);}
break;
case 63:
//#line 242 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el identificador");}
break;
case 64:
//#line 243 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '='");}
break;
case 65:
//#line 244 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante UINT");}
break;
case 66:
//#line 247 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '('");}
break;
case 67:
//#line 248 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 68:
//#line 249 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la condición");}
break;
case 69:
//#line 250 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 70:
//#line 251 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la palabra UP o DOWN");}
break;
case 71:
//#line 252 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante CTE_UINT");}
break;
case 72:
//#line 253 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ')'");}
break;
case 73:
//#line 254 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '{'");}
break;
case 74:
//#line 255 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el bloque de sentencias");}
break;
case 75:
//#line 256 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '}'");}
break;
case 76:
//#line 259 "gramatica.y"
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
case 77:
//#line 273 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 78:
//#line 274 "gramatica.y"
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
case 79:
//#line 287 "gramatica.y"
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
case 80:
//#line 300 "gramatica.y"
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
case 81:
//#line 313 "gramatica.y"
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
case 82:
//#line 327 "gramatica.y"
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
case 83:
//#line 340 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 84:
//#line 343 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante double -> " + val_peek(0).sval);
			yyval = new ParserVal(new Operando("DOUBLE", val_peek(0).sval));
			}
break;
case 85:
//#line 346 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante uint -> " + val_peek(0).sval);
                     	yyval = new ParserVal(new Operando("UINT", val_peek(0).sval));
                        }
break;
case 86:
//#line 349 "gramatica.y"
{	if(chequearFactorNegado()){
        			Operando op = (Operando)val_peek(0).obj;
        			yyval = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
break;
case 87:
//#line 353 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó el identificador -> " + val_peek(0).sval);
		String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
		if(ambitoVariable != null)
                	yyval = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo(), ambitoVariable));
                else {
                       	System.out.println("La variable " + val_peek(0).sval +" no fue declarada");
                       	yyval = new ParserVal(null);
                }}
break;
case 88:
//#line 363 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 89:
//#line 364 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 90:
//#line 365 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 91:
//#line 366 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 92:
//#line 367 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 93:
//#line 368 "gramatica.y"
{yyval = new ParserVal("!=");}
break;
case 94:
//#line 371 "gramatica.y"
{yyval = new ParserVal("+");}
break;
case 95:
//#line 372 "gramatica.y"
{yyval = new ParserVal("-");}
break;
case 96:
//#line 375 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF");
								adminTerceto.desapilar();}
break;
case 97:
//#line 377 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  			                                   adminTerceto.desapilar();}
break;
case 99:
//#line 382 "gramatica.y"
{Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.desapilar();
                     adminTerceto.apilar(t.getNumero());
                     }
break;
case 100:
//#line 389 "gramatica.y"
{System.out.println(" se leyó una sentencia IF" + val_peek(0).sval);
				if(val_peek(0).sval != null){
					Terceto t = new Terceto("BF", val_peek(0).sval, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.apilar(t.getNumero());
				}}
break;
case 101:
//#line 397 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
break;
case 102:
//#line 398 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
break;
case 103:
//#line 399 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
break;
case 104:
//#line 400 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
break;
case 105:
//#line 401 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
break;
case 106:
//#line 404 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 107:
//#line 405 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
break;
case 108:
//#line 409 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");
			Terceto t = new Terceto("OUT", val_peek(1).sval, null);
			adminTerceto.agregarTerceto(t);}
break;
case 110:
//#line 415 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
break;
case 111:
//#line 416 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
break;
case 112:
//#line 417 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
break;
case 113:
//#line 418 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
break;
case 114:
//#line 419 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
break;
case 115:
//#line 422 "gramatica.y"
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
case 117:
//#line 441 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
break;
case 118:
//#line 442 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
break;
case 119:
//#line 443 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
break;
case 120:
//#line 447 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + val_peek(3).sval );
				   lista_param_invocacion = (ArrayList<Pair<String, String>>)val_peek(1).obj;
			  	   if(!lista_param_invocacion.isEmpty()){ /* Hubo un error mas abajo*/
			  	    	String ambitoProc = Main.tSimbolos.verificarAmbito(val_peek(3).sval, ambito);
			  	    	if(ambitoProc != null){
			  	    	   	if (verificarParametros(val_peek(3).sval)){
			  	    	   		if(lista_param_invocacion.size() == Main.tSimbolos.getDatosTabla(ambitoProc).getCantParametros()){
								if(Main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosActuales() < Main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosMax()){
									for(Pair p : lista_param_invocacion){
										Terceto t = new Terceto("=" ,p.getKey()+"@"+val_peek(3).sval, (String)p.getValue());
										adminTerceto.agregarTerceto(t);
									}
									Terceto t = new Terceto("INV", ambitoProc, null); /*ver como guardar linea inicial de procedimiento.*/
									Main.tSimbolos.getDatosTabla(ambitoProc).incrementarLlamados();
									adminTerceto.agregarTerceto(t);
								} else
									System.out.println("Supero la cantidad maxima de llamados a "+val_peek(3).sval);
							}else
								System.out.println("Faltan parametros para invocar al procedimiento "+val_peek(3).sval);						}
			  	    	}else{
			  	    		System.out.println("El procedimiento "+val_peek(3).sval+" esta fuera de alcance");}
			  	   }}
break;
case 122:
//#line 472 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
break;
case 123:
//#line 473 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
break;
case 124:
//#line 474 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
break;
case 125:
//#line 475 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
break;
case 126:
//#line 478 "gramatica.y"
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
case 127:
//#line 487 "gramatica.y"
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
case 129:
//#line 500 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
break;
case 130:
//#line 501 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
break;
case 131:
//#line 502 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
break;
case 132:
//#line 503 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
break;
//#line 1519 "Parser.java"
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
