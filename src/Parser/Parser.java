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
   21,   21,   21,   21,   21,   16,   16,   23,   22,   22,
   27,   27,   27,   25,   25,   25,   25,   25,   25,   25,
   25,   25,   25,   26,   28,   28,   28,   28,   30,   30,
   30,   31,   31,   31,   31,   29,   29,   29,   29,   29,
   29,   24,   24,   17,   17,   17,   33,   32,   34,   34,
   34,   34,   34,   34,   34,   18,   18,   35,   35,   35,
   35,   35,   19,   19,   36,   36,   36,   20,   20,   38,
   38,   38,   38,   37,   37,   37,   39,   39,   39,   39,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    2,    2,    1,    2,    1,    1,
    3,    2,    1,    3,    2,    3,    1,    1,    2,   11,
    1,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,    1,    3,    5,    1,    7,    2,    3,    4,    4,
    2,    3,    1,    1,    2,    2,    2,    2,    2,    1,
    2,    2,    2,    2,    2,   12,    1,    1,    3,    1,
    2,    2,    3,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,    3,    1,    3,    3,    4,    3,    3,
    1,    1,    1,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    6,    8,    1,    1,    1,    5,    5,
    5,    5,    5,    7,    7,    4,    1,    3,    3,    2,
    4,    3,    3,    1,    2,    2,    2,    4,    1,    3,
    3,    3,    3,    3,    5,    1,    2,    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   43,   44,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
   21,    0,    0,    0,    0,    0,   50,   57,   96,  107,
  114,  119,    0,   83,   82,    0,    0,    0,    0,    0,
    0,    0,   81,    0,  126,   85,    0,   98,    0,    0,
    0,    0,    0,    0,    0,    0,   60,    0,    0,    7,
    0,    0,    0,    0,    5,    8,   17,    0,   18,   15,
   12,   51,   45,   52,   46,   53,   47,   54,   48,   55,
   49,  128,    0,    0,  122,    0,    0,   84,  127,    0,
    0,    0,    0,    0,    0,  121,    0,    0,   89,   90,
   88,   91,   86,   87,    0,    0,  108,    0,    0,  112,
   62,    0,    0,   61,    0,    0,    0,    0,    0,    0,
   35,    0,    3,  120,   14,   19,   11,    0,  130,  124,
    0,  118,    0,    0,   79,   80,    0,    0,    0,    0,
    0,    0,    0,  111,  106,   63,   59,    0,    0,   58,
    0,    0,    0,    0,   41,    0,    0,    0,    0,   16,
   78,  129,    0,  100,  102,    0,    0,  101,   99,    0,
    0,    0,    0,   42,    0,    0,    0,    0,    0,    0,
   38,    0,  125,    0,   94,   92,   93,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   39,   40,
    0,  104,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   95,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   24,   25,   27,   30,    0,   29,   28,   26,   23,
   22,   66,   68,   70,   72,    0,   71,   69,   67,   65,
   64,   20,   56,
};
final static short yydgoto[] = {                         11,
   12,   66,   14,   15,   16,   17,   18,   68,   19,   20,
   69,  119,   21,  120,  121,   22,   23,   24,   25,   26,
   27,   56,  149,  188,   28,  150,   57,   49,  105,   42,
   43,   50,  167,   29,   30,   31,   44,   32,   45,
};
final static short yysindex[] = {                       225,
  -35,  102,  -40,    0,    0,   -3,   10,  453,  -50,  207,
    0,    0,    0,  186,    0,    0,    0, -214,  -47,    0,
    0,  -44,  -37,   81,   86,   87,    0,    0,    0,    0,
    0,    0,  -49,    0,    0,   12,   56,  207,  280, -209,
    8,    7,    0,   55,    0,    0,  283,    0,  515,   26,
   40,  -39,   98,  119, -188,   24,    0,  -20, -231,    0,
  256,  -49,   76,    8,    0,    0,    0,  122,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -182,  207,    0,   77,    8,    0,    0,  280,
  280,  280,  280,   31, -170,    0,  225,  138,    0,    0,
    0,    0,    0,    0,  207,  225,    0,   57,   62,    0,
    0, -199,  381,    0,  207, -194,   27, -149,   71,  104,
    0,   78,    0,    0,    0,    0,    0, -122,    0,    0,
   39,    0,    7,    7,    0,    0, -120,   83, -129,  154,
 -124,    8, -119,    0,    0,    0,    0,  414,   91,    0,
   92, -101, -115,  -24,    0, -112, -231,  114, -111,    0,
    0,    0,  -92,    0,    0,    0, -175,    0,    0, -181,
  -55, -181, -181,    0,  106,  109,  -54,  110,  147, -231,
    0,  111,    0,  215,    0,    0,    0,  -85, -203,  -83,
  -82,  -81,  -75,  -73,  108,  -72,  -70, -231,    0,    0,
  -68,    0,  -71,  155,  157,   50,  159,  160,  161,   80,
   88,   90,  -96,  268,  273,  162,  306,    0,  307,  310,
   -7,  312,  313,  315,  316,  453,  453,  453,  311,  453,
  453,  453, -231,  453,  453,  453,  453,  321,  453,  453,
  453,  453,  296,  343,  366,  376,  392,  410,  426,  437,
    0,  468,  478,  494,  523,  549,  564,  590,  615,  625,
  648,    0,    0,    0,    0,  658,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  673,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -29,    0,    0,    0,    0,  115,    0,    0,
  130,   45,    0,    0,    0,    0,    0,    0,    0,    0,
  133,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    0,    0,  145,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  244,  259,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  264,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -16,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   70,   93,    0,    0,    0,    0,    0,    0,
    0,  123,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -12,    0,    0,
    0,    0,    0,    0,    0,  146,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -9,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  331,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -8,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  346,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  351,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -62,    3,   53,    0,    0,    0,  670,    0,    0,    0,
    0,  -38,    0,  624,    0,    0,    0,    0,    0,    0,
    0,  156,  -84, -126,    0,   22,    0,   18,    0,   37,
   32,  167,    0,    0,    0,    0,   29,    0,    0,
};
final static int YYTABLESIZE=950;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         52,
    2,  110,   13,  189,   37,    6,  196,   40,   83,   39,
   60,   71,   85,   85,   73,   85,  177,   85,   41,  117,
  122,   75,   40,   48,   32,   38,  230,   64,   37,   85,
  151,   33,   34,  238,  139,  141,   54,   63,    4,    5,
    7,  116,   67,  143,  190,  191,  192,   89,   92,   59,
   90,   84,   91,   93,  205,   87,  146,   55,  147,    2,
   61,    7,  206,  171,    6,   86,  106,  153,   48,  114,
   88,  186,  187,  129,  130,    4,    5,  166,  154,  161,
  107,   90,  115,   91,   75,   75,  138,   75,  137,   75,
  222,  184,  185,  186,  187,   96,   85,  144,   95,   13,
   13,  131,  145,   75,   75,   75,   75,  155,   13,   76,
   76,  156,   76,   40,   76,  237,  124,  132,  159,   95,
   95,  203,  142,  135,  136,    7,  133,  134,   76,   76,
   76,   76,   77,   77,  160,   77,  162,   77,  164,   77,
  163,   47,   13,  168,   79,   81,   39,  157,  169,  172,
  173,   77,   77,   77,   77,  174,  175,  180,  112,  178,
  182,  229,   74,   74,  183,  128,  193,   75,  213,  194,
  197,  201,  204,  117,  207,  208,  209,    9,  140,   55,
  127,   74,  210,   74,  211,  214,   13,  215,  115,  217,
  198,  110,   76,    9,  251,  219,  218,  220,   10,  223,
  224,  225,  226,  116,  103,  233,   62,   82,   70,  113,
  227,   72,  228,   98,   10,   77,  108,  195,   74,  186,
  187,   33,   34,   51,  109,    9,   85,   35,   60,   60,
   60,   60,   60,   60,   60,   36,   60,   60,   60,   60,
   60,   60,   60,   60,   60,   74,   10,  176,   60,    4,
    5,   39,  116,   53,    9,   32,    2,    7,   60,   37,
    8,    6,   33,   34,    9,    7,   58,    2,    2,    7,
    7,    7,    6,    6,    7,   10,    8,    7,  243,  244,
  245,  247,  248,  249,  250,   10,  252,  253,  254,  255,
  257,  258,  259,  260,  261,    9,    4,    5,  266,  116,
   75,   75,  123,   75,   75,   75,   75,  221,  276,   75,
   65,   94,   62,   75,   75,   75,   10,  113,   75,   75,
   75,   75,  109,   97,   39,   76,   76,   39,   76,   76,
   76,   76,   94,   94,   76,    9,   76,    8,   76,   76,
   76,   78,   80,   76,   76,   76,   76,    8,   77,   77,
    9,   77,   77,   77,   77,  111,   10,   77,   46,   34,
    9,   77,   77,   77,   35,  212,   77,   77,   77,   77,
  117,   10,   36,    4,    5,   53,  116,  125,  126,   74,
  123,   10,    9,    4,    5,  115,  116,   74,  110,  105,
  231,   74,   74,   74,    1,  232,   74,   74,   74,   74,
  116,  103,    2,   10,   31,    9,    3,    4,    5,   73,
    1,    6,   97,   97,    7,    9,    4,    5,    2,  116,
  262,  165,    3,    4,    5,   39,   10,    6,  234,  235,
    7,    9,  236,  246,  239,  240,   10,  241,  242,  148,
    0,    0,    1,  256,    0,    0,    0,    0,    0,    9,
    2,    0,   10,    0,    3,    4,    5,    0,   39,    6,
    0,    0,    7,   46,   34,    9,    0,  263,    0,   35,
   10,    1,  170,    0,    0,    0,    9,   36,    0,    2,
    0,    1,  202,    3,    4,    5,   10,    0,    6,    2,
  264,    7,    9,    3,    4,    5,    0,   10,    6,  123,
  265,    7,    0,    0,    0,    0,    0,    9,    0,    0,
    0,    0,    1,   10,  113,    0,  267,    9,    0,  109,
    2,    0,    0,    0,    3,    4,    5,    0,   10,    6,
    0,    0,    7,    9,  268,    0,   46,   34,   10,   46,
   34,    0,   35,    0,    0,   35,    0,    0,    0,    0,
  269,    0,    1,   36,   10,    0,    0,   90,    0,   91,
    2,  270,    9,    0,    3,    4,    5,    1,    0,    6,
    0,    0,    7,    0,  103,    2,  104,    1,    0,    3,
    4,    5,    0,   10,    6,    2,  105,    7,    9,    3,
    4,    5,  271,    0,    6,    0,    0,    7,    0,    1,
    0,   31,  272,    9,    0,    0,   73,    2,    0,   10,
    0,    3,    4,    5,    0,    0,    6,    0,  273,    7,
    0,    0,    1,    0,   10,    0,    0,    0,    0,    9,
    2,    0,    1,    0,    3,    4,    5,   46,   34,    6,
    2,    0,    7,   35,    3,    4,    5,  274,    1,    6,
   10,   36,    7,    0,    9,    0,    2,    0,    0,    0,
    3,    4,    5,    0,    9,    6,    1,    0,    7,    0,
   46,   34,    0,  275,    2,   10,   35,    0,    3,    4,
    5,    0,    1,    6,   36,   10,    7,    9,  277,    0,
    2,    0,    0,    1,    3,    4,    5,    9,    0,    6,
    0,    2,    7,    0,    0,    3,    4,    5,   10,    1,
    6,    0,    9,    7,  278,    0,    0,    2,   10,    0,
    0,    3,    4,    5,    1,    0,    6,  118,  118,    7,
    0,    0,    2,   10,    1,    0,    3,    4,    5,  279,
    0,    6,    2,  158,    7,    0,    3,    4,    5,  280,
    1,    6,    0,    0,    7,    0,    0,    0,    2,    0,
    0,    0,    3,    4,    5,    0,    0,    6,    0,    0,
    7,    0,  281,   99,  100,  101,  102,    0,    0,    1,
  179,  181,  282,    0,    0,  152,  118,    2,    0,  118,
    0,    3,    4,    5,    0,    0,    6,  283,    0,    7,
    0,    0,  199,  200,    0,    1,    0,    0,    0,    0,
    0,    0,    0,    2,    0,    0,    0,    3,    4,    5,
    1,  216,    6,    0,    0,    7,  118,  118,    2,    0,
    0,    0,    3,    4,    5,    0,    0,    6,    0,    0,
    7,    0,    0,    0,    0,    0,    1,    0,  118,  118,
    0,    0,    0,    0,    2,    0,    0,    0,    3,    4,
    5,    0,    0,    6,    0,    0,    7,  118,    0,    0,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    2,
    0,    1,    0,    3,    4,    5,    0,    0,    6,    2,
    0,    7,    0,    3,    4,    5,    0,    0,    6,    0,
    0,    7,  118,    0,    1,    0,    0,    0,    0,    0,
    0,    0,    2,    0,    1,    0,    3,    4,    5,    0,
    0,    6,    2,    0,    7,    0,    3,    4,    5,    1,
    0,    6,    0,    0,    7,    0,    0,    2,    0,    0,
    0,    3,    4,    5,    0,    0,    6,    0,    0,    7,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   41,    0,   59,   40,    0,   61,   58,   58,   45,
    8,   59,   42,   43,   59,   45,   41,   47,    1,   40,
   59,   59,   58,    2,   41,   61,  123,   10,   41,   59,
  115,   41,   41,   41,   97,   98,   40,    9,  270,  271,
   40,  273,  257,  106,  171,  172,  173,  257,   42,   40,
   43,   40,   45,   47,  258,   38,  256,   61,  258,   59,
    8,   61,  189,  148,   59,   37,   41,   41,   47,  258,
   39,  275,  276,  256,  257,  270,  271,  140,  117,   41,
   41,   43,   59,   45,   40,   41,  257,   43,   58,   45,
   41,  267,  268,  275,  276,   41,   41,   41,   44,   97,
   98,   84,   41,   59,   60,   61,   62,  257,  106,   40,
   41,   41,   43,   58,   45,  123,   41,   41,   41,   44,
   44,  184,  105,   92,   93,  125,   90,   91,   59,   60,
   61,   62,   40,   41,  257,   43,  257,   45,  268,   59,
   58,   40,  140,  268,   59,   59,   45,   44,  268,   59,
   59,   59,   60,   61,   62,  257,  272,   44,   61,  272,
  272,  258,   40,   41,  257,   44,   61,  123,   61,   61,
   61,   61,  258,   59,  258,  258,  258,   40,   41,   61,
   59,   59,  258,   61,  258,  258,  184,  258,   59,  258,
   44,   59,  123,   40,  233,   41,  268,   41,   61,   41,
   41,   41,  123,   59,   59,   44,  257,  257,  256,   54,
  123,  256,  123,   47,   61,  123,  256,  272,  256,  275,
  276,  257,  258,  264,  264,   40,  256,  263,  226,  227,
  228,  229,  230,  231,  232,  271,  234,  235,  236,  237,
  238,  239,  240,  241,  242,  123,   61,  272,  246,  270,
  271,   45,  273,  257,   40,  272,  256,  257,  256,  272,
  123,  256,  272,  272,   40,  265,  257,  267,  268,  269,
  270,  271,  267,  268,  274,   61,  123,  277,  226,  227,
  228,  229,  230,  231,  232,   61,  234,  235,  236,  237,
  238,  239,  240,  241,  242,   40,  270,  271,  246,  273,
  256,  257,   59,  259,  260,  261,  262,  258,  256,  265,
  125,  257,  257,  269,  270,  271,   61,   59,  274,  275,
  276,  277,   59,   41,   45,  256,  257,   45,  259,  260,
  261,  262,  257,  257,  265,   40,  256,  123,  269,  270,
  271,  256,  256,  274,  275,  276,  277,  123,  256,  257,
   40,  259,  260,  261,  262,  258,   61,  265,  257,  258,
   40,  269,  270,  271,  263,  258,  274,  275,  276,  277,
  256,   61,  271,  270,  271,  257,  273,  256,  257,  257,
  125,   61,   40,  270,  271,  256,  273,  265,  256,   59,
  123,  269,  270,  271,  257,  123,  274,  275,  276,  277,
  256,  256,  265,   61,   59,   40,  269,  270,  271,   59,
  257,  274,  267,  268,  277,   40,  270,  271,  265,  273,
  125,  268,  269,  270,  271,   45,   61,  274,  123,  123,
  277,   40,  123,  123,  123,  123,   61,  123,  123,   59,
   -1,   -1,  257,  123,   -1,   -1,   -1,   -1,   -1,   40,
  265,   -1,   61,   -1,  269,  270,  271,   -1,   45,  274,
   -1,   -1,  277,  257,  258,   40,   -1,  125,   -1,  263,
   61,  257,   59,   -1,   -1,   -1,   40,  271,   -1,  265,
   -1,  257,  268,  269,  270,  271,   61,   -1,  274,  265,
  125,  277,   40,  269,  270,  271,   -1,   61,  274,  256,
  125,  277,   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,
   -1,   -1,  257,   61,  256,   -1,  125,   40,   -1,  256,
  265,   -1,   -1,   -1,  269,  270,  271,   -1,   61,  274,
   -1,   -1,  277,   40,  125,   -1,  257,  258,   61,  257,
  258,   -1,  263,   -1,   -1,  263,   -1,   -1,   -1,   -1,
  125,   -1,  257,  271,   61,   -1,   -1,   43,   -1,   45,
  265,  125,   40,   -1,  269,  270,  271,  257,   -1,  274,
   -1,   -1,  277,   -1,   60,  265,   62,  257,   -1,  269,
  270,  271,   -1,   61,  274,  265,  256,  277,   40,  269,
  270,  271,  125,   -1,  274,   -1,   -1,  277,   -1,  257,
   -1,  256,  125,   40,   -1,   -1,  256,  265,   -1,   61,
   -1,  269,  270,  271,   -1,   -1,  274,   -1,  125,  277,
   -1,   -1,  257,   -1,   61,   -1,   -1,   -1,   -1,   40,
  265,   -1,  257,   -1,  269,  270,  271,  257,  258,  274,
  265,   -1,  277,  263,  269,  270,  271,  125,  257,  274,
   61,  271,  277,   -1,   40,   -1,  265,   -1,   -1,   -1,
  269,  270,  271,   -1,   40,  274,  257,   -1,  277,   -1,
  257,  258,   -1,  125,  265,   61,  263,   -1,  269,  270,
  271,   -1,  257,  274,  271,   61,  277,   40,  125,   -1,
  265,   -1,   -1,  257,  269,  270,  271,   40,   -1,  274,
   -1,  265,  277,   -1,   -1,  269,  270,  271,   61,  257,
  274,   -1,   40,  277,  125,   -1,   -1,  265,   61,   -1,
   -1,  269,  270,  271,  257,   -1,  274,   58,   59,  277,
   -1,   -1,  265,   61,  257,   -1,  269,  270,  271,  125,
   -1,  274,  265,  120,  277,   -1,  269,  270,  271,  125,
  257,  274,   -1,   -1,  277,   -1,   -1,   -1,  265,   -1,
   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,
  277,   -1,  125,  259,  260,  261,  262,   -1,   -1,  257,
  157,  158,  125,   -1,   -1,  116,  117,  265,   -1,  120,
   -1,  269,  270,  271,   -1,   -1,  274,  125,   -1,  277,
   -1,   -1,  179,  180,   -1,  257,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  265,   -1,   -1,   -1,  269,  270,  271,
  257,  198,  274,   -1,   -1,  277,  157,  158,  265,   -1,
   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,  257,   -1,  179,  180,
   -1,   -1,   -1,   -1,  265,   -1,   -1,   -1,  269,  270,
  271,   -1,   -1,  274,   -1,   -1,  277,  198,   -1,   -1,
   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,
   -1,  257,   -1,  269,  270,  271,   -1,   -1,  274,  265,
   -1,  277,   -1,  269,  270,  271,   -1,   -1,  274,   -1,
   -1,  277,  233,   -1,  257,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  265,   -1,  257,   -1,  269,  270,  271,   -1,
   -1,  274,  265,   -1,  277,   -1,  269,  270,  271,  257,
   -1,  274,   -1,   -1,  277,   -1,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,  277,
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

//#line 389 "gramatica.y"

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
//#line 656 "Parser.java"
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
case 58:
//#line 132 "gramatica.y"
{if(val_peek(0).sval != null){
				Terceto t = new Terceto("BF", val_peek(0).sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	adminTerceto.apilar(t.getNumero());}
                          }
break;
case 59:
//#line 138 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una asignaci�n al identificador -> " + val_peek(2).sval);
                                  if(Main.tSimbolos.getDatosTabla(val_peek(2).sval).isDeclarada()){
                            		String tipoIde = Main.tSimbolos.getDatosTabla(val_peek(2).sval).getTipo();
                                        if(tipoIde.equals("UINT")){
                                		Terceto t = new Terceto("=", val_peek(2).sval, val_peek(0).sval);
                                		adminTerceto.agregarTerceto(t);
                                		adminTerceto.apilar(t.getNumero()+1);
                                		yyval = new ParserVal(val_peek(2).sval);
                                	} else
                                		System.out.println("Los tipos son incompatibles");
                                  } else {
                                	System.out.println("La variable " + val_peek(2).sval +" no fue declarada");
                                	yyval = new ParserVal(null);}
                              	  }
break;
case 60:
//#line 152 "gramatica.y"
{yyval = new ParserVal(null);}
break;
case 61:
//#line 155 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el identificador");}
break;
case 62:
//#line 156 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '='");}
break;
case 63:
//#line 157 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante UINT");}
break;
case 64:
//#line 160 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '('");}
break;
case 65:
//#line 161 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 66:
//#line 162 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la condición");}
break;
case 67:
//#line 163 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 68:
//#line 164 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la palabra UP o DOWN");}
break;
case 69:
//#line 165 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta una constante CTE_UINT");}
break;
case 70:
//#line 166 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ')'");}
break;
case 71:
//#line 167 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '{'");}
break;
case 72:
//#line 168 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el bloque de sentencias");}
break;
case 73:
//#line 169 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '}'");}
break;
case 74:
//#line 172 "gramatica.y"
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
case 75:
//#line 186 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 76:
//#line 187 "gramatica.y"
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
case 77:
//#line 200 "gramatica.y"
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
case 78:
//#line 213 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una conversión");
	  			Operando op = (Operando)val_peek(1).obj;
	  			 if(op != null)
	  				yyval = new ParserVal(new Operando("DOUBLE",op.getValor()));
	  			else
	  				yyval = new ParserVal(null);
	  			}
break;
case 79:
//#line 222 "gramatica.y"
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
case 80:
//#line 236 "gramatica.y"
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
case 81:
//#line 249 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 82:
//#line 252 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante double -> " + val_peek(0).sval);
			yyval = new ParserVal(new Operando("DOUBLE", val_peek(0).sval));
			}
break;
case 83:
//#line 255 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante uint -> " + val_peek(0).sval);
                     	yyval = new ParserVal(new Operando("UINT", val_peek(0).sval));
                        }
break;
case 84:
//#line 258 "gramatica.y"
{	if(chequearFactorNegado()){
        			Operando op = (Operando)val_peek(0).obj;
        			yyval = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
break;
case 85:
//#line 262 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó el identificador -> " + val_peek(0).sval);
		if(Main.tSimbolos.getDatosTabla(val_peek(0).sval).isDeclarada())
                	yyval = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(val_peek(0).sval).getTipo(), val_peek(0).sval));
                else {
                       	System.out.println("La variable " + val_peek(0).sval +" no fue declarada");
                       	yyval = new ParserVal(null);
                }}
break;
case 86:
//#line 271 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 87:
//#line 272 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 88:
//#line 273 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 89:
//#line 274 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 90:
//#line 275 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 91:
//#line 276 "gramatica.y"
{yyval = new ParserVal("!=");}
break;
case 92:
//#line 279 "gramatica.y"
{yyval = new ParserVal("+");}
break;
case 93:
//#line 280 "gramatica.y"
{yyval = new ParserVal("-");}
break;
case 94:
//#line 283 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF");
								adminTerceto.desapilar();}
break;
case 95:
//#line 288 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  			                                   adminTerceto.desapilar();}
break;
case 97:
//#line 293 "gramatica.y"
{Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.desapilar();
                     adminTerceto.apilar(t.getNumero());
                     }
break;
case 98:
//#line 300 "gramatica.y"
{System.out.println(" se leyó una sentencia IF" + val_peek(0).sval);
				if(val_peek(0).sval != null){
					Terceto t = new Terceto("BF", val_peek(0).sval, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.apilar(t.getNumero());
				}}
break;
case 99:
//#line 316 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
break;
case 100:
//#line 317 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
break;
case 101:
//#line 318 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
break;
case 102:
//#line 320 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
break;
case 103:
//#line 322 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
break;
case 104:
//#line 326 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 105:
//#line 328 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
break;
case 106:
//#line 332 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");}
break;
case 108:
//#line 336 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
break;
case 109:
//#line 337 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
break;
case 110:
//#line 338 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
break;
case 111:
//#line 339 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
break;
case 112:
//#line 340 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
break;
case 113:
//#line 343 "gramatica.y"
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
case 115:
//#line 362 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
break;
case 116:
//#line 363 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
break;
case 117:
//#line 364 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
break;
case 118:
//#line 368 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + val_peek(3).sval );}
break;
case 120:
//#line 372 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
break;
case 121:
//#line 373 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
break;
case 122:
//#line 374 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
break;
case 123:
//#line 375 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
break;
case 124:
//#line 378 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 125:
//#line 379 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 127:
//#line 383 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
break;
case 128:
//#line 384 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
break;
case 129:
//#line 385 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
break;
case 130:
//#line 386 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
break;
//#line 1359 "Parser.java"
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
