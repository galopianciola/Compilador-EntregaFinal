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
   31,   31,   31,   26,   26,   19,   35,   19,   19,   34,
   36,   36,   36,   36,   36,   36,   36,   36,   20,   20,
   37,   37,   37,   37,   37,   21,   21,   38,   38,   38,
   22,   22,   40,   40,   40,   40,   39,   39,   39,   41,
   41,   41,   41,
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
    1,    1,    1,    1,    1,    6,    0,    9,    1,    1,
    5,    5,    5,    5,    5,    7,    7,    7,    4,    1,
    3,    3,    2,    4,    3,    3,    1,    2,    2,    2,
    4,    1,    3,    3,    3,    3,    3,    5,    1,    2,
    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   45,   46,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
    0,   21,   26,    0,    0,    0,    0,    0,   52,   59,
   99,  110,  117,  122,    0,   85,   84,    0,    0,    0,
    0,    0,    0,    0,   83,    0,  129,   87,    0,  100,
    0,    0,    0,    0,    0,    0,    0,    0,   62,    0,
    0,    7,    0,    0,    0,    0,    5,    8,   17,    0,
   18,   15,   12,    0,    0,   53,   47,   54,   48,   55,
   49,   56,   50,   57,   51,  131,    0,    0,  125,    0,
    0,   86,  130,    0,    0,    0,    0,    0,    0,  124,
    0,    0,   91,   92,   90,   93,   88,   89,    0,    0,
  111,    0,    0,  115,   64,    0,    0,   63,    0,    0,
    0,    0,    0,    0,   37,    0,    3,  123,   14,   19,
   11,    0,   23,    0,   22,  133,  127,    0,  121,    0,
    0,   81,   82,    0,    0,    0,    0,    0,    0,    0,
  114,  109,   65,   61,    0,    0,   60,    0,    0,    0,
    0,   43,    0,    0,    0,    0,   16,   20,   80,  132,
    0,    0,  102,  104,    0,    0,  103,    0,  101,    0,
    0,    0,    0,   44,    0,    0,    0,    0,    0,    0,
   40,    0,  128,    0,   96,    0,    0,    0,   94,   95,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,   42,    0,  107,    0,  108,  106,    0,    0,
    0,    0,    0,    0,   29,   30,   32,    0,   31,   28,
    0,   27,    0,    0,    0,    0,    0,    0,    0,    0,
   33,   25,    0,   98,    0,    0,    0,    0,    0,    0,
    0,    0,   38,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   68,   70,   72,   74,    0,   73,   71,   69,
   67,   66,   58,
};
final static short yydgoto[] = {                         11,
   12,   68,   14,   15,   16,   17,   18,   70,   19,   20,
   71,   21,   22,  123,   23,  124,  125,   24,   25,   26,
   27,   28,   29,   58,  156,  201,   30,  157,   59,   51,
  109,   44,   45,   52,  196,   31,   32,   33,   46,   34,
   47,
};
final static short yysindex[] = {                       133,
   62,  155,   13,    0,    0,  -40,   33,  470,  -51,  195,
    0,    0,    0,  151,    0,    0,    0, -232,  -47,    0,
  194,    0,    0,  -41,  -35,   86,  105,  116,    0,    0,
    0,    0,    0,    0,   66,    0,    0,   -5,   59,  195,
  250, -217,    3,   -3,    0,  -25,    0,    0,   80,    0,
  493,   11,   38,  173,  123,  -44, -215,   16,    0,   30,
 -199,    0,  162,   66,  103,    3,    0,    0,    0,   92,
    0,    0,    0,  227,  249,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -173,  195,    0,  109,
    3,    0,    0,  250,  250,  250,  250,   29, -165,    0,
  133,  118,    0,    0,    0,    0,    0,    0,  195,  133,
    0,   60,   68,    0,    0, -197,  259,    0,  195, -185,
  -10, -163,   75,   51,    0,   81,    0,    0,    0,    0,
    0, -129,    0,  275,    0,    0,    0,    4,    0,   -3,
   -3,    0,    0, -122,   88, -178,  108, -149,    3, -130,
    0,    0,    0,    0,  312,   96,    0,   97, -100, -111,
  -37,    0, -109, -199,   83, -107,    0,    0,    0,    0,
  -91,  133,    0,    0, -101,  133,    0,  133,    0, -136,
  -56, -136, -136,    0,  107,  110,   20,  111,  126, -199,
    0,  115,    0,  -87,    0,  -90,  -86,  -85,    0,    0,
  -73, -207,  -72,  -61,  -60,  -59,  -57,  128,  -55,  -53,
 -199,    0,    0,  -50,    0,  133,    0,    0,  166,  169,
   47,  268,  287,  350,    0,    0,    0, -192,    0,    0,
  370,    0,  174,  311,  318,   -9,  320,  326,  331,  334,
    0,    0, -199,    0,  470,  470,  470,  294,  470,  470,
  470,  470,    0,  319,  344,  354,  369,  384,  400,  416,
  432,  442,    0,    0,    0,    0,  460,    0,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  145,    0,    0,    0,    0,  137,
    0,    0,  167,  -32,    0,    0,    0,    0,    0,    0,
    0,    0,  347,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    6,    0,    0,  352,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  376,
  379,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  388,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  389,    0,    0,    0,    0,    0,   37,
   70,    0,    0,    0,    0,    0,    0,    0,   93,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -26,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -8,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -7,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  391,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  647,    2,   34,    0,    0,    0,  602,    0,    0,    0,
    0,    0,    0,  394,    0,  322,    0,    0,    0,    0,
    0,    0,    0,  404,  -97, -145,    0,   18,    0,   53,
    0,   48,    9,  413,    0,    0,    0,    0,   17,    0,
    0,
};
final static int YYTABLESIZE=863;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         56,
    2,   13,  202,  187,   34,    6,   42,   77,   77,   62,
   77,   73,   77,  105,   39,  100,   57,   77,   99,   50,
   57,  158,   62,   79,   69,   65,   77,   77,   77,   77,
  160,  248,   35,   36,   88,  203,  204,  205,   96,   93,
    7,   63,  118,   97,  169,   94,   94,   95,   95,   92,
  220,  110,   54,   43,   75,   90,  221,  181,  153,    2,
  154,    7,   66,  241,    6,  242,   50,  199,  200,  121,
    4,    5,   61,  120,  119,   62,   78,   78,  111,   78,
  209,   78,  136,  137,    4,    5,  144,  237,  172,  173,
   77,  145,   91,  162,  164,   78,   78,   78,   78,   89,
  151,   39,   13,   13,  142,  143,   41,  134,  152,   79,
   79,   13,   79,  247,   79,  163,   42,  176,  177,   42,
  101,  166,   40,   87,   41,    7,  190,  167,   79,   79,
   79,   79,   76,   76,  170,  132,  178,  179,  199,  200,
  138,  140,  141,  128,   81,  171,   99,    9,   13,  139,
  131,   76,   99,   76,  182,  183,  184,    9,  147,   78,
  185,  149,  188,   83,  192,  193,  195,  206,   10,  211,
  207,  210,    9,   13,   85,  214,  216,   13,   10,   13,
  215,  217,  218,  116,  219,  222,   87,   87,  228,   87,
    9,   87,   79,   10,   49,  120,  223,  224,  225,   41,
  226,    9,  229,   87,  230,   64,  234,  232,   72,  235,
  105,   10,   55,  114,   76,   76,   55,   13,  199,  200,
   78,   97,   10,   77,   77,  118,   77,   77,   77,   77,
    8,   98,   77,    9,  186,   34,   77,   77,   77,   41,
    8,   77,   77,   77,   77,   39,   62,   62,   62,   62,
   62,   62,   62,   62,   10,    8,    2,    7,   62,    4,
    5,    6,  120,   35,   36,    7,    9,    2,    2,    7,
    7,    7,    6,    6,    7,   67,   53,    7,  254,  255,
  256,  258,  259,  260,  261,  262,  127,   10,    9,   60,
  267,  208,   78,   78,   41,   78,   78,   78,   78,    4,
    5,   78,  120,   41,  236,   78,   78,   78,  238,   10,
   78,   78,   78,   78,    9,   64,   74,  155,   35,   36,
    4,    5,   86,  120,   37,   79,   79,  239,   79,   79,
   79,   79,   38,    9,   79,   10,   48,   36,   79,   79,
   79,   80,   37,   79,   79,   79,   79,  129,  130,   76,
   38,  133,    4,    5,   10,  120,   41,   76,    9,   98,
   82,   76,   76,   76,    1,   98,   76,   76,   76,   76,
  180,   84,    2,  135,    1,  174,    3,    4,    5,   10,
  115,    6,    2,    9,    7,  227,    3,    4,    5,    1,
  240,    6,  120,    9,    7,    4,    5,    2,  120,  168,
   87,    3,    4,    5,   10,  113,    6,    1,    9,    7,
  119,   48,   36,  243,   10,    2,  257,   37,    1,    3,
    4,    5,  118,    9,    6,   38,    2,    7,  112,   10,
    3,    4,    5,  245,  126,    6,  113,  116,    7,    9,
  246,  244,  249,  263,   10,  165,  112,   24,  250,   75,
    1,   48,   36,  251,  126,    9,  252,   37,    2,  117,
   10,  102,    3,    4,    5,   38,    0,    6,  264,    0,
    7,    9,    0,    0,    0,    0,   10,    0,  265,    0,
    0,    9,    0,    1,    0,  189,  191,    0,    0,    0,
    0,    2,   10,  266,    0,    3,    4,    5,    0,    9,
    6,    0,   10,    7,    0,    1,   48,   36,  268,    9,
  212,  213,   37,    2,  161,   48,   36,    3,    4,    5,
   10,   37,    6,    0,  269,    7,    0,    0,    0,   38,
   10,    1,  231,    0,    0,   94,    0,   95,    0,    2,
  270,    0,    0,    3,    4,    5,    0,    0,    6,    0,
    1,    7,  107,    0,  108,    0,  271,    0,    2,    0,
    0,    0,    3,    4,    5,    0,  272,    6,   48,   36,
    7,    0,    0,    0,   37,    1,    0,    0,    0,    0,
    0,    0,   38,    2,  273,    0,    0,    3,    4,    5,
    0,    0,    6,    0,    0,    7,    0,    0,    0,    0,
    1,    0,  113,    0,    0,    0,    0,  119,    2,    0,
    1,    0,    3,    4,    5,    0,    0,    6,    2,    0,
    7,    0,    3,    4,    5,    1,    0,    6,    0,    0,
    7,  126,    0,    2,  116,    0,  253,    3,    4,    5,
    1,    0,    6,  112,   24,    7,   75,    0,    2,    0,
    0,    0,    3,    4,    5,    0,    1,    6,    0,    0,
    7,  122,  122,    0,    2,    0,    0,    0,    3,    4,
    5,    0,    1,    6,    0,    0,    7,    0,    0,    0,
    2,    0,    0,    0,    3,    4,    5,    0,    1,    6,
    0,    0,    7,    0,    0,    0,    2,    0,    1,    0,
    3,    4,    5,    0,    0,    6,    2,    0,    7,    0,
    3,    4,    5,    0,    0,    6,    1,    0,    7,    0,
    0,  159,  122,    0,    2,  122,    1,    0,    3,    4,
    5,    0,    0,    6,    2,    0,    7,    0,    3,    4,
    5,    0,    0,    6,    0,    0,    7,  146,  148,    0,
    0,  103,  104,  105,  106,    0,  150,    0,    0,    0,
    0,    0,    0,    0,    0,  122,  122,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  122,  122,    0,  175,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  122,    0,    0,    0,    0,    0,  194,    0,
    0,    0,  197,    0,  198,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  122,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  233,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   59,   41,   41,    0,   58,   40,   41,    8,
   43,   59,   45,   59,   41,   41,   61,   59,   44,    2,
   61,  119,   21,   59,  257,    9,   59,   60,   61,   62,
   41,   41,   41,   41,   40,  181,  182,  183,   42,  257,
   40,    8,  258,   47,   41,   43,   43,   45,   45,   41,
  258,   41,   40,    1,   21,   39,  202,  155,  256,   59,
  258,   61,   10,  256,   59,  258,   49,  275,  276,   40,
  270,  271,   40,  273,   59,   74,   40,   41,   41,   43,
   61,   45,  256,  257,  270,  271,   58,   41,  267,  268,
  123,  257,   40,  257,   44,   59,   60,   61,   62,   41,
   41,   40,  101,  102,   96,   97,   45,   74,   41,   40,
   41,  110,   43,  123,   45,   41,   58,  267,  268,   58,
   41,   41,   61,   58,   45,  125,   44,  257,   59,   60,
   61,   62,   40,   41,  257,   44,  267,  268,  275,  276,
   88,   94,   95,   41,   59,   58,   44,   40,  147,   41,
   59,   59,   44,   61,   59,   59,  257,   40,   41,  123,
  272,  109,  272,   59,  272,  257,  268,   61,   61,   44,
   61,   61,   40,  172,   59,   61,  267,  176,   61,  178,
  268,  268,  268,   61,  258,  258,   42,   43,   61,   45,
   40,   47,  123,   61,   40,   59,  258,  258,  258,   45,
  258,   40,  258,   59,  258,  257,   41,  258,  256,   41,
  256,   61,  257,   41,  256,  123,  257,  216,  275,  276,
  256,  267,   61,  256,  257,   59,  259,  260,  261,  262,
  123,  257,  265,   40,  272,  272,  269,  270,  271,   45,
  123,  274,  275,  276,  277,  272,  245,  246,  247,  248,
  249,  250,  251,  252,   61,  123,  256,  257,  257,  270,
  271,  256,  273,  272,  272,  265,   40,  267,  268,  269,
  270,  271,  267,  268,  274,  125,  264,  277,  245,  246,
  247,  248,  249,  250,  251,  252,  125,   61,   40,  257,
  257,  272,  256,  257,   45,  259,  260,  261,  262,  270,
  271,  265,  273,   45,  258,  269,  270,  271,   41,   61,
  274,  275,  276,  277,   40,  257,  123,   59,  257,  258,
  270,  271,  257,  273,  263,  256,  257,   41,  259,  260,
  261,  262,  271,   40,  265,   61,  257,  258,  269,  270,
  271,  256,  263,  274,  275,  276,  277,  256,  257,  257,
  271,  125,  270,  271,   61,  273,   45,  265,   40,  257,
  256,  269,  270,  271,  257,  257,  274,  275,  276,  277,
   59,  256,  265,  125,  257,  268,  269,  270,  271,   61,
  258,  274,  265,   40,  277,  258,  269,  270,  271,  257,
   41,  274,  256,   40,  277,  270,  271,  265,  273,  125,
  256,  269,  270,  271,   61,   59,  274,  257,   40,  277,
   59,  257,  258,   44,   61,  265,  123,  263,  257,  269,
  270,  271,  256,   40,  274,  271,  265,  277,  256,   61,
  269,  270,  271,  123,   59,  274,  264,   59,  277,   40,
  123,  268,  123,  125,   61,  124,   59,   59,  123,   59,
  257,  257,  258,  123,   61,   40,  123,  263,  265,   56,
   61,   49,  269,  270,  271,  271,   -1,  274,  125,   -1,
  277,   40,   -1,   -1,   -1,   -1,   61,   -1,  125,   -1,
   -1,   40,   -1,  257,   -1,  164,  165,   -1,   -1,   -1,
   -1,  265,   61,  125,   -1,  269,  270,  271,   -1,   40,
  274,   -1,   61,  277,   -1,  257,  257,  258,  125,   40,
  189,  190,  263,  265,  121,  257,  258,  269,  270,  271,
   61,  263,  274,   -1,  125,  277,   -1,   -1,   -1,  271,
   61,  257,  211,   -1,   -1,   43,   -1,   45,   -1,  265,
  125,   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,
  257,  277,   60,   -1,   62,   -1,  125,   -1,  265,   -1,
   -1,   -1,  269,  270,  271,   -1,  125,  274,  257,  258,
  277,   -1,   -1,   -1,  263,  257,   -1,   -1,   -1,   -1,
   -1,   -1,  271,  265,  125,   -1,   -1,  269,  270,  271,
   -1,   -1,  274,   -1,   -1,  277,   -1,   -1,   -1,   -1,
  257,   -1,  256,   -1,   -1,   -1,   -1,  256,  265,   -1,
  257,   -1,  269,  270,  271,   -1,   -1,  274,  265,   -1,
  277,   -1,  269,  270,  271,  257,   -1,  274,   -1,   -1,
  277,  256,   -1,  265,  256,   -1,  243,  269,  270,  271,
  257,   -1,  274,  256,  256,  277,  256,   -1,  265,   -1,
   -1,   -1,  269,  270,  271,   -1,  257,  274,   -1,   -1,
  277,   60,   61,   -1,  265,   -1,   -1,   -1,  269,  270,
  271,   -1,  257,  274,   -1,   -1,  277,   -1,   -1,   -1,
  265,   -1,   -1,   -1,  269,  270,  271,   -1,  257,  274,
   -1,   -1,  277,   -1,   -1,   -1,  265,   -1,  257,   -1,
  269,  270,  271,   -1,   -1,  274,  265,   -1,  277,   -1,
  269,  270,  271,   -1,   -1,  274,  257,   -1,  277,   -1,
   -1,  120,  121,   -1,  265,  124,  257,   -1,  269,  270,
  271,   -1,   -1,  274,  265,   -1,  277,   -1,  269,  270,
  271,   -1,   -1,  274,   -1,   -1,  277,  101,  102,   -1,
   -1,  259,  260,  261,  262,   -1,  110,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  164,  165,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  189,  190,   -1,  147,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  211,   -1,   -1,   -1,   -1,   -1,  172,   -1,
   -1,   -1,  176,   -1,  178,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  243,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  216,
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
"seleccion : IF '(' if_condicion ')' bloque END_IF",
"$$1 :",
"seleccion : IF '(' if_condicion ')' bloque $$1 ELSE bloque END_IF",
"seleccion : error_if",
"if_condicion : condicion",
"error_if : IF if_condicion ')' bloque END_IF",
"error_if : IF '(' ')' bloque END_IF",
"error_if : IF '(' if_condicion bloque END_IF",
"error_if : IF '(' if_condicion ')' END_IF",
"error_if : IF '(' if_condicion ')' bloque",
"error_if : IF if_condicion ')' bloque ELSE bloque END_IF",
"error_if : IF '(' ')' bloque ELSE bloque END_IF",
"error_if : IF '(' if_condicion bloque ELSE bloque END_IF",
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

//#line 551 "gramatica.y"

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
		System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una constante UINT fuera de rango");
		Main.tSimbolos.eliminarSimbolo(lexema);
	}
	else if (id == Lexico.CTE_DOUBLE) {
		double valor = -1*Double.parseDouble(lexema.replace('d','e'));
		if(( valor > 2.2250738585272014e-308 && valor < 1.7976931348623157e+308) || (valor > -1.7976931348623157e+308 && valor < -2.2250738585072014e-308) || (valor == 0.0))
                	{Main.tSimbolos.modificarSimbolo(lexema, String.valueOf(valor));
                	return true;
                	}
                else {
                	System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una constante DOUBLE fuera de rango");
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
			System.out.println("No se reconoce el parametro formal "+ p.getKey());
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
//#line 672 "Parser.java"
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
{System.out.println("Error sin�ctico: Linea " + Lexico.linea + " se detect� un bloque de sentencias mal declarado, falta '{'");}
break;
case 6:
//#line 25 "gramatica.y"
{System.out.println("Error sint�ctico: Linea "+ Lexico.linea+ " se detect� un bloque de sentencias mal declarado, falta '}'");}
break;
case 11:
//#line 36 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se detect� una declaracion de variables");*/
					String tipoVar = val_peek(2).sval;
					lista_variables = (ArrayList<String>)val_peek(1).obj; /*controlar si ya est� en la tabla*/
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
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
break;
case 15:
//#line 61 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
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
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ',' entre los identificadores");}
break;
case 20:
//#line 78 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se declar� un procedimiento");
							if(val_peek(3).sval != null){ /* se declaro todo bien*/
								ambito = ambito.substring(0,ambito.lastIndexOf("@"));
								Terceto t = new Terceto("FinProc", val_peek(3).sval, null);
								adminTerceto.agregarTerceto(t);
								}
							}
break;
case 21:
//#line 85 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se declar? un procedimiento");
                            if(val_peek(0).sval != null){ /* se declaro todo bien*/
                 		ambito = ambito.substring(0,ambito.lastIndexOf("@"));
				Terceto t = new Terceto("FinProc", val_peek(0).sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	}
                          }
break;
case 22:
//#line 95 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
break;
case 23:
//#line 96 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta el bloque de sentencias");}
break;
case 24:
//#line 97 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
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
					Terceto t = new Terceto("ComienzaProc", nuevoLexema, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.agregarProcedimiento(nuevoLexema);
					yyval = new ParserVal(nuevoLexema); /* para corroborar q el proc se declaro bien (no se si va)*/
				} else {
					System.out.println("El procedimiento "+ val_peek(6).sval + " ya fue declarado en este ambito");
					yyval = new ParserVal(null);
					}
			}}
break;
case 27:
//#line 131 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta el identificador");}
break;
case 28:
//#line 132 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta '('");}
break;
case 29:
//#line 133 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta lista de parametros");}
break;
case 30:
//#line 134 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta ')'");}
break;
case 31:
//#line 135 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta la palabra reservada NI ");}
break;
case 32:
//#line 136 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, '=' despues de NI ");}
break;
case 33:
//#line 137 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un procedimiento mal declarado, falta la constante UINT ");}
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
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron m�s parametros de los permitidos (3)");}
break;
case 39:
//#line 163 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 40:
//#line 164 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 41:
//#line 165 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 42:
//#line 166 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 43:
//#line 169 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� el parametro -> " + val_peek(0).sval);
		  DatosTabla dt = Main.tSimbolos.getDatosTabla(val_peek(0).sval);
		  dt.setUso("nombreParametro");
		  dt.setTipo(val_peek(1).sval);
		  Main.tSimbolos.setDatosTabla(val_peek(0).sval, dt);
		  yyval = new ParserVal(val_peek(0).sval);}
break;
case 44:
//#line 176 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� el parametro -> " + val_peek(1).sval);
      		  DatosTabla dt = Main.tSimbolos.getDatosTabla(val_peek(0).sval);
                  dt.setUso("nombreParametro");
                  dt.setTipo(val_peek(1).sval);
                  dt.setParametroRef(true);
                  Main.tSimbolos.setDatosTabla(val_peek(0).sval, dt);
                  yyval = new ParserVal(val_peek(0).sval);}
break;
case 45:
//#line 185 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� un tipo UINT");
		yyval = new ParserVal ("UINT");}
break;
case 46:
//#line 187 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� un tipo DOUBLE");
     		yyval = new ParserVal ("DOUBLE");}
break;
case 53:
//#line 199 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
break;
case 54:
//#line 200 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
break;
case 55:
//#line 201 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
break;
case 56:
//#line 202 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
break;
case 57:
//#line 203 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una sentencia mal declarada, falta ';'");}
break;
case 58:
//#line 206 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia FOR");
							if((val_peek(9).sval != null) && (val_peek(7).sval != null)){
								Terceto t = new Terceto(val_peek(5).sval,val_peek(9).sval,val_peek(4).sval);
								t.setTipo("UINT");
								adminTerceto.agregarTerceto(t);
								t = new Terceto("BI", null, null);
								adminTerceto.agregarTerceto(t);
								adminTerceto.desapilar(); /*para completar BF*/
								adminTerceto.desapilarFor();
								t = new Terceto("Label"+adminTerceto.cantTercetos(),null,null);
								adminTerceto.agregarTerceto(t);
							}}
break;
case 60:
//#line 221 "gramatica.y"
{if(val_peek(0).sval != null){
				Terceto t = new Terceto("BF", val_peek(0).sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	adminTerceto.apilar(t.getNumero());}
                          else
                          	yyval = new ParserVal(null);
                          }
break;
case 61:
//#line 229 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz? una asignaci?n al identificador -> " + val_peek(2).sval);
                                  String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(2).sval, ambito);
                                  if(ambitoVariable != null) {
                            		String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
                                        if(tipoIde.equals("UINT")){
                                		Terceto t = new Terceto("=", ambitoVariable, val_peek(0).sval);
                                		t.setTipo("UINT");
                                		adminTerceto.agregarTerceto(t);
                                		t = new Terceto("Label"+adminTerceto.cantTercetos(), null, null);
						t.setTipo("UINT");
                                		adminTerceto.agregarTerceto(t);
                                		adminTerceto.apilar(t.getNumero());
                                		yyval = new ParserVal(ambitoVariable);
                                	} else
                                		System.out.println("Los tipos son incompatibles");
                                  } else {
                                	System.out.println("La variable " + val_peek(2).sval +" no fue declarada");
                                	yyval = new ParserVal(null);}
                              	  }
break;
case 62:
//#line 248 "gramatica.y"
{yyval = new ParserVal(null);}
break;
case 63:
//#line 251 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta el identificador");}
break;
case 64:
//#line 252 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '='");}
break;
case 65:
//#line 253 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta una constante UINT");}
break;
case 66:
//#line 256 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '('");}
break;
case 67:
//#line 257 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ';'");}
break;
case 68:
//#line 258 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta la condici�n");}
break;
case 69:
//#line 259 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ';'");}
break;
case 70:
//#line 260 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta la palabra UP o DOWN");}
break;
case 71:
//#line 261 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta una constante CTE_UINT");}
break;
case 72:
//#line 262 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ')'");}
break;
case 73:
//#line 263 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '{'");}
break;
case 74:
//#line 264 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta el bloque de sentencias");}
break;
case 75:
//#line 265 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '}'");}
break;
case 76:
//#line 268 "gramatica.y"
{Operando op1 = (Operando)val_peek(2).obj;
                                             Operando op2 = (Operando)val_peek(0).obj;
                                             if(op1 != null && op2 !=null){
						if(op1.getTipo().equals(op2.getTipo())){
							Terceto t = new Terceto(val_peek(1).sval, op1.getValor(), op2.getValor());
							t.setTipo(op1.getTipo());
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
//#line 283 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 78:
//#line 284 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una suma");
				Operando op1 = (Operando)val_peek(2).obj;
				Operando op2 = (Operando)val_peek(0).obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("+", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else {
						System.out.println("Tipos incompatibles");
						yyval = new ParserVal(null);
						}
                                } else
                                	yyval = new ParserVal(null);}
break;
case 79:
//#line 300 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una resta");
	  			Operando op1 = (Operando)val_peek(2).obj;
                                Operando op2 = (Operando)val_peek(0).obj;
                                if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("-", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(),"["+t.getNumero()+"]"));
						}
					else{
						System.out.println("Tipos incompatibles");
						yyval = new ParserVal(null);}
                                } else
                                        yyval = new ParserVal(null);}
break;
case 80:
//#line 315 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una conversi�n");
	  			Operando op = (Operando)val_peek(1).obj;
	  			if(op != null)
	  				if(op.getTipo() == "UINT"){
	  					yyval = new ParserVal(new Operando("DOUBLE",op.getValor()));
	  					Terceto t = new Terceto("CONV", op.getValor(), null);
	  					adminTerceto.agregarTerceto(t);
	  					yyval = new ParserVal(new Operando("DOUBLE","["+t.getNumero()+"]"));
	  					}
	  				else{
	  					System.out.println("Error: no se permite convertir un double");
	  					yyval = new ParserVal(null);}
	  			else
	  				yyval = new ParserVal(null);
	  			}
break;
case 81:
//#line 332 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una multiplicacion");
				Operando op1 = (Operando)val_peek(2).obj;
				Operando op2 = (Operando)val_peek(0).obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("*", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
						}
					else{
						System.out.println("Tipos incompatibles");
						yyval = new ParserVal(null);}
				} else
                                 	yyval = new ParserVal(null);
                                }
break;
case 82:
//#line 348 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una division");
				Operando op1 = (Operando)val_peek(2).obj;
                                Operando op2 = (Operando)val_peek(0).obj;
                                if(op1 != null && op2 !=null){
					if (op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("/", op1.getValor(), op2.getValor());
						t.setTipo(op1.getTipo());
						adminTerceto.agregarTerceto(t);
						yyval = new ParserVal(new Operando(op1.getTipo(), "["+t.getNumero()+"]"));
					} else{
						System.out.println("Tipos incompatibles");
						yyval = new ParserVal(null);}
                                } else
                                	yyval = new ParserVal(null);
                               }
break;
case 83:
//#line 363 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 84:
//#line 366 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� la constante double -> " + val_peek(0).sval);
			yyval = new ParserVal(new Operando("DOUBLE", val_peek(0).sval));
			}
break;
case 85:
//#line 369 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� la constante uint -> " + val_peek(0).sval);
                     	yyval = new ParserVal(new Operando("UINT", val_peek(0).sval));
                        }
break;
case 86:
//#line 372 "gramatica.y"
{	if(chequearFactorNegado()){
        			Operando op = (Operando)val_peek(0).obj;
        			yyval = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
break;
case 87:
//#line 376 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� el identificador -> " + val_peek(0).sval);
		String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
		if(ambitoVariable != null)
                	yyval = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo(), ambitoVariable));
                else {
                       	System.out.println("La variable " + val_peek(0).sval +" no fue declarada");
                       	yyval = new ParserVal(null);
                }}
break;
case 88:
//#line 386 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 89:
//#line 387 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 90:
//#line 388 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 91:
//#line 389 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 92:
//#line 390 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 93:
//#line 391 "gramatica.y"
{yyval = new ParserVal("!=");}
break;
case 94:
//#line 394 "gramatica.y"
{yyval = new ParserVal("+");}
break;
case 95:
//#line 395 "gramatica.y"
{yyval = new ParserVal("-");}
break;
case 96:
//#line 398 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia IF");
						   if(val_peek(3).sval != null)
						   	{adminTerceto.desapilar();
						   	Terceto t = new Terceto("Label"+Integer.toString(adminTerceto.cantTercetos()), null, null);
                                                        adminTerceto.agregarTerceto(t);}
                                                        }
break;
case 97:
//#line 404 "gramatica.y"
{if(val_peek(2).sval != null){
	  					Terceto t = new Terceto("BI", null, null);
                                            	adminTerceto.agregarTerceto(t);
                                                adminTerceto.desapilar();
                                                Terceto t1 = new Terceto("Label"+Integer.toString(adminTerceto.cantTercetos()), null, null);
                                                adminTerceto.agregarTerceto(t1);
                           	            	adminTerceto.apilar(t.getNumero());
                           	           	}
                                            }
break;
case 98:
//#line 412 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia IF con ELSE");
	  			                                   if(val_peek(6).sval != null) {
	  			                                   adminTerceto.desapilar();
	  			                                   Terceto t = new Terceto("Label"+Integer.toString(adminTerceto.cantTercetos()), null, null);
	  			                                   adminTerceto.agregarTerceto(t);}}
break;
case 100:
//#line 427 "gramatica.y"
{if(val_peek(0).sval != null){
				Terceto t = new Terceto("BF", val_peek(0).sval, null);
				adminTerceto.agregarTerceto(t);
				adminTerceto.apilar(t.getNumero());
			}
			else
				yyval = new ParserVal(null);}
break;
case 101:
//#line 436 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta '('");}
break;
case 102:
//#line 437 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta la condicion");}
break;
case 103:
//#line 438 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta ')'");}
break;
case 104:
//#line 439 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el bloque de sentencias");}
break;
case 105:
//#line 440 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el END_IF o ELSE");}
break;
case 106:
//#line 443 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta '('");}
break;
case 107:
//#line 444 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta la condicion");}
break;
case 108:
//#line 445 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta ')'");}
break;
case 109:
//#line 450 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una sentencia OUT");
			Terceto t = new Terceto("OUT", val_peek(1).sval, null);
			adminTerceto.agregarTerceto(t);}
break;
case 111:
//#line 456 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, falta '('");}
break;
case 112:
//#line 457 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + "  se detect� un OUT mal declarado, falta ')'");}
break;
case 113:
//#line 458 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + "  se detect� un OUT mal declarado, faltan '(' y ')'");}
break;
case 114:
//#line 459 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, entre los par�ntesis debe ir una cadena");}
break;
case 115:
//#line 460 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, falta la cadena entre los parent�sis en el OUT");}
break;
case 116:
//#line 463 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una asignaci�n al identificador -> " + val_peek(2).sval);
				String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(2).sval, ambito);
				if(ambitoVariable != null){
					String tipoIde = Main.tSimbolos.getDatosTabla(ambitoVariable).getTipo();
					Operando op = (Operando)val_peek(0).obj;
					if(op != null)
						if(tipoIde.equals(op.getTipo())){
							Terceto t = new Terceto("=", ambitoVariable, op.getValor());
							t.setTipo(op.getTipo());
							adminTerceto.agregarTerceto(t);
							yyval = new ParserVal(new Operando(tipoIde, "[" + t.getNumero()+ "]"));
						} else
							System.out.println("Los tipos son incompatibles");
				} else {
					System.out.println("La variable " + val_peek(2).sval +" no fue declarada");
					/* ver si devolver null}*/
				}}
break;
case 118:
//#line 483 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " falta '=' en la asignaci�n");}
break;
case 119:
//#line 484 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignaci�n");}
break;
case 120:
//#line 485 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " falta una expresi�n aritm�tica del lado derecho de la asignaci�n");}
break;
case 121:
//#line 489 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una invocacion al procedimiento -> " + val_peek(3).sval );
				   lista_param_invocacion = (ArrayList<Pair<String, String>>)val_peek(1).obj;
			  	   if(lista_param_invocacion!= null && !lista_param_invocacion.isEmpty()){ /* Hubo un error mas abajo*/
			  	    	String ambitoProc = Main.tSimbolos.verificarAmbito(val_peek(3).sval, ambito);
			  	    	if(ambitoProc != null){
			  	    	   	if(lista_param_invocacion.size() == Main.tSimbolos.getDatosTabla(ambitoProc).getCantParametros()){
							if (verificarParametros(val_peek(3).sval)){
								if(Main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosActuales() < Main.tSimbolos.getDatosTabla(ambitoProc).getLlamadosMax()){
									for(Pair p : lista_param_invocacion){
										Terceto t = new Terceto("=" ,p.getKey()+"@"+val_peek(3).sval, (String)p.getValue());
										t.setTipo(Main.tSimbolos.getDatosTabla(p.getKey()+"@"+val_peek(3).sval).getTipo());
										adminTerceto.agregarTerceto(t);
									}
									Terceto t = new Terceto("INV", ambitoProc, null); /*ver como guardar linea inicial de procedimiento.*/
									Main.tSimbolos.getDatosTabla(ambitoProc).incrementarLlamados();
									adminTerceto.agregarTerceto(t);
								} else
									System.out.println("Supero la cantidad maxima de llamados a "+val_peek(3).sval);
							}
						}else
							System.out.println("Faltan parametros para invocar al procedimiento "+val_peek(3).sval);						}
			  	    	}else{
			  	    		System.out.println("El procedimiento "+val_peek(3).sval+" esta fuera de alcance");}
			  	   }
break;
case 123:
//#line 516 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el identificador");}
break;
case 124:
//#line 517 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el '('");}
break;
case 125:
//#line 518 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, faltan los par�metros");}
break;
case 126:
//#line 519 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el ')'");}
break;
case 127:
//#line 522 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los par�metros -> " + $1.sval +" y " +$3.sval);*/
			  if(lista_param_invocacion!=null){
				  lista_param_invocacion.clear();
				  String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
				  if(ambitoVariable != null){
					lista_param_invocacion.add(new Pair<String,String>(val_peek(2).sval, ambitoVariable));
					yyval = new ParserVal(lista_param_invocacion);} /* esto no se si como seria pq hay 2 listas :'(*/
				  else
					System.out.println("La variable "+val_peek(0).sval+ "no se encuentra en el ambito");
				  }}
break;
case 128:
//#line 532 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los par�metros -> " + $3.sval +" y " +$5.sval);*/
                               	lista_param_invocacion = (ArrayList<Pair<String, String>>)val_peek(4).obj;
                               	if(lista_param_invocacion != null && !lista_param_invocacion.isEmpty()){
                               		String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
                                        if(ambitoVariable != null){
                                        	lista_param_invocacion.add(new Pair<String,String>(val_peek(2).sval, ambitoVariable));
                                    		yyval = new ParserVal(lista_param_invocacion);
                                     	} else
                                        	System.out.println("La variable "+val_peek(0).sval+ " no se encuentra en el ambito");
                                }}
break;
case 130:
//#line 545 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta el identificador de la izquierda");}
break;
case 131:
//#line 546 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta ':' entre los identificadores");}
break;
case 132:
//#line 547 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta la ',' que separa los identificadores");}
break;
case 133:
//#line 548 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta el identificador de la derecha");}
break;
//#line 1566 "Parser.java"
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
