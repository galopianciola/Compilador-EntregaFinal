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
   17,   17,   17,   30,   29,   31,   31,   31,   31,   31,
   31,   31,   18,   18,   32,   32,   32,   32,   32,   19,
   19,   33,   33,   33,   20,   20,   35,   35,   35,   35,
   34,   34,   34,   36,   36,   36,   36,
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
    6,    8,    1,    1,    1,    5,    5,    5,    5,    5,
    7,    7,    4,    1,    3,    3,    2,    4,    3,    3,
    1,    2,    2,    2,    4,    1,    3,    3,    3,    3,
    3,    5,    1,    2,    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   43,   44,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
   21,    0,    0,    0,    0,    0,   50,   57,   93,  104,
  111,  116,    0,   80,   79,    0,    0,    0,    0,    0,
    0,    0,   78,    0,  123,   82,    0,   95,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    0,    0,    0,
    0,    5,    8,   17,    0,   18,   15,   12,   51,   45,
   52,   46,   53,   47,   54,   48,   55,   49,  125,    0,
    0,  119,    0,    0,   81,  124,    0,    0,    0,    0,
    0,    0,  118,    0,    0,   86,   87,   85,   88,   83,
   84,    0,    0,  105,    0,    0,  109,    0,    0,    0,
    0,    0,    0,    0,    0,   35,    0,    3,  117,   14,
   19,   11,    0,  127,  121,    0,  115,    0,    0,   76,
   77,    0,    0,    0,    0,    0,    0,    0,  108,  103,
    0,    0,    0,    0,    0,    0,    0,   41,    0,    0,
    0,    0,   16,   75,  126,    0,   97,   99,    0,    0,
   98,   96,    0,    0,    0,    0,    0,   42,    0,    0,
    0,    0,    0,    0,   38,    0,  122,    0,   91,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   39,   40,    0,  101,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   92,   89,   90,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   36,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   24,   25,   27,   30,    0,   29,   28,   26,   23,   22,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   20,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   58,   60,   63,   65,   67,   69,
    0,   68,   66,   64,   62,   61,   59,   56,
};
final static short yydgoto[] = {                         11,
   12,   63,   14,   15,   16,   17,   18,   65,   19,   20,
   66,  114,   21,  115,  116,   22,   23,   24,   25,   26,
   27,   48,  215,   28,   49,  102,   42,   43,   50,  160,
   29,   30,   31,   44,   32,   45,
};
final static short yysindex[] = {                       226,
  145,   81,   -8,    0,    0,  -35,  -22,  790,  -45,  241,
    0,    0,    0,  248,    0,    0,    0, -253,  -52,    0,
    0,  -36,   68,   69,  116,  128,    0,    0,    0,    0,
    0,    0,  -44,    0,    0,   -2,    6,  241,   27, -224,
   40,   -6,    0,   11,    0,    0,  104,    0,  577,    5,
   18,  -41,  -18,  -46,  -37, -220,    0,  297,  -44,  103,
   40,    0,    0,    0,  -38,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -227,
  241,    0,  119,   40,    0,    0,   27,   27,   27,   27,
   13, -201,    0,  226,  160,    0,    0,    0,    0,    0,
    0,  241,  226,    0,   22,   32,    0, -176,  127, -171,
 -203,  -33, -166,   58,   -9,    0,   62,    0,    0,    0,
    0,    0, -152,    0,    0,   47,    0,   -6,   -6,    0,
    0, -151,   55, -161,  170, -153,   40, -148,    0,    0,
   63,   64,  -42,   65, -140, -147,  -31,    0, -143, -220,
   37, -134,    0,    0,    0, -116,    0,    0,    0, -188,
    0,    0,  241,  241,  276,  241,  241,    0,   82,   85,
   -7,   87,   42, -220,    0,   89,    0,  199,    0,   83,
   93,  298,   98,   99,  100,  -93,  -86,  131,  -85,  -80,
 -220,    0,    0,  -74,    0,  -79, -182, -182, -182,   16,
 -182, -182, -182,   70,   73,   75, -107,   76,   86,  161,
   91,    0,    0,    0,  -50,  156,  165, -231,  168,  185,
  187,  188,  790,  790,  790,  326,  790,  790,  790, -220,
  790,  333,  377,  387,  407,   -1,  409,  412,  413,  414,
  354,  381,  396,  411,  421,  440,  450,  468,    0,  483,
  329,  335,  336,  337,  -21,  339,  340,  342,  343,  348,
    0,    0,    0,    0,  502,    0,    0,    0,    0,    0,
  790,  790,  790,  790,  790,  512,  790,  790,  790,  790,
  790,    0,  537,  552,  575,  593,  603,  643,  662,  673,
  695,  723,  734,  757,    0,    0,    0,    0,    0,    0,
  772,    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   49,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   15,    0,    0,    0,    0,  148,    0,    0,
  270,   71,    0,    0,    0,    0,    0,    0,    0,    0,
  275,    0,    0,    0,    0,    0,    0,    2,    0,    0,
  320,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  350,  360,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  361,    0,    0,    0,    0,
    0,    0,    0,    0,  -30,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   94,  121,    0,
    0,    0,    0,    0,    0,    0,  136,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -29,    0,    0,    0,    0,    0,    0,    0,  132,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -19,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  365,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  373,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  374,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -69,    1,   23,    0,    0,    0,  866,    0,    0,    0,
    0,  -28,    0,  -73,    0,    0,    0,    0,    0,    0,
    0,    4,  386,    0,   38,    0,   10,  -20,  427,    0,
    0,    0,    0,   28,    0,    0,
};
final static int YYTABLESIZE=1096;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        107,
   13,    6,  112,   64,   54,  123,   68,  146,   57,  171,
   32,   37,   40,   80,  110,  227,  166,   56,   85,  276,
  122,   33,   70,   34,  134,  136,  235,  117,  124,  125,
   58,   52,   86,  138,  150,   89,   60,   81,   41,  256,
   90,  151,  108,  213,  214,  103,   82,   61,    2,    4,
    5,   93,  111,  189,   92,  133,   82,   82,  104,   82,
    6,   82,  139,   40,   83,  159,    4,    5,  130,  131,
  132,   39,  140,   82,  218,   84,  173,  175,  178,  179,
  174,  141,   87,  147,   88,  191,  144,  154,    7,   87,
  148,   88,  213,  214,   13,   13,  128,  129,  149,  192,
  193,  275,  152,   13,  153,  155,  157,    2,  196,    7,
   72,   72,  156,   72,  161,   72,  168,  210,  126,  162,
   47,  163,  164,  167,  169,   39,   72,   74,  172,   72,
   72,   72,   72,   73,   73,   13,   73,  176,   73,  137,
  177,  197,  186,  119,   94,  187,   92,  190,   39,  194,
  226,  198,   73,   73,   73,   73,  201,  202,  203,  127,
   74,   74,   92,   74,  204,   74,  180,  181,  183,  184,
  185,  205,  208,    7,   76,   71,   71,  209,   13,   74,
   74,   74,   74,  211,   37,  200,   78,  143,  212,   39,
  100,  207,  223,   72,   71,  224,   71,  225,  228,    9,
  135,  249,   40,   67,  230,   38,  114,  232,  229,    9,
  109,   59,   79,  231,  105,  165,   73,  120,  121,   69,
   10,   53,  106,   57,   57,   57,   57,   57,   57,   57,
   10,   57,    4,    5,   55,  111,    4,    5,    9,  111,
  170,   32,   37,   74,   57,  241,  242,  243,  245,  246,
  247,  248,   33,  250,   34,   51,  255,    6,   71,   10,
    4,    5,   59,  111,  188,    9,  265,   91,    6,    6,
   82,   57,   57,   57,   57,   57,   57,   57,   57,   57,
   57,   57,    8,   46,   34,   39,   10,    9,   57,   35,
  213,  214,    8,  283,  284,  285,  286,  287,  289,  290,
  291,  292,  293,  294,    2,    7,    4,    5,   10,  111,
  301,    4,    5,    7,  111,    2,    2,    7,    7,    7,
   39,    8,    7,   71,   73,    7,   72,   72,  112,   72,
   72,   72,   72,  107,  182,   72,    9,   46,   34,   72,
   72,   72,   39,   35,   72,   72,   72,   72,    8,   73,
   73,   36,   73,   73,   73,   73,  199,   10,   73,   91,
   46,   34,   73,   73,   73,    9,   35,   73,   73,   73,
   73,   75,   62,  251,   36,   91,   74,   74,  113,   74,
   74,   74,   74,   77,  142,   74,   10,  100,  206,   74,
   74,   74,   71,    9,   74,   74,   74,   74,   94,   94,
   71,   33,   34,  114,   71,   71,   71,   35,  120,   71,
   71,   71,   71,  233,   10,   36,    1,  252,  110,  106,
    9,  118,  234,  102,    2,  237,    1,  253,    3,    4,
    5,   31,   70,    6,    2,    9,    7,  158,    3,    4,
    5,   10,  238,    6,  239,  240,    7,  254,  244,  257,
    9,  271,  258,  259,  260,    1,   10,  272,  273,  274,
    9,  277,  278,    2,  279,  280,  195,    3,    4,    5,
  281,   10,    6,   95,    0,    7,    0,    0,  261,    9,
    0,   10,    1,    0,    0,    0,    0,    0,    0,    9,
    2,    0,    0,    0,    3,    4,    5,   46,   34,    6,
   10,    0,    7,   35,    1,  262,    0,    9,    0,    0,
   10,   36,    2,    0,    0,    0,    3,    4,    5,    0,
  263,    6,    9,    0,    7,  112,    0,    0,   10,    0,
  107,    0,   46,   34,    0,  264,    0,    0,   35,    0,
    0,    9,    0,   10,    0,  266,   36,    0,    0,    0,
    0,    9,    0,    1,   46,   34,    0,    0,    0,    0,
   35,    2,   10,    0,  267,    3,    4,    5,   36,    0,
    6,    0,   10,    7,  268,  113,    9,    0,    0,    0,
    0,    0,    1,  216,  217,  219,  220,  221,  222,    0,
    2,    9,  269,    0,    3,    4,    5,   10,    0,    6,
    0,    0,    7,  236,    0,  120,    0,  270,    0,    0,
    1,    0,   10,    0,    9,  110,  106,    0,    2,   87,
  102,   88,    3,    4,    5,    0,  282,    6,   31,   70,
    7,    0,    9,    0,  288,   10,  100,    1,  101,    0,
    0,    0,    9,    0,    0,    2,    0,    0,    0,    3,
    4,    5,    1,   10,    6,    0,    0,    7,    0,    0,
    2,  295,    0,   10,    3,    4,    5,    1,    0,    6,
    0,    0,    7,    0,    0,    2,  296,    1,    0,    3,
    4,    5,    9,    0,    6,    2,    0,    7,    0,    3,
    4,    5,    0,    0,    6,    0,    1,    7,    0,  297,
    0,    9,    0,   10,    2,    0,    1,    0,    3,    4,
    5,    0,    9,    6,    2,    0,    7,  298,    3,    4,
    5,    0,   10,    6,    1,    0,    7,  299,    0,    0,
    0,    0,    2,   10,    9,    0,    3,    4,    5,    1,
    0,    6,    0,    0,    7,    0,    0,    2,    0,    0,
    0,    3,    4,    5,    0,   10,    6,    0,    1,    7,
    0,    0,    9,    0,    0,    0,    2,  300,    1,    0,
    3,    4,    5,    9,    0,    6,    2,    0,    7,    0,
    3,    4,    5,   10,    0,    6,  302,    0,    7,    0,
    0,    0,    0,    1,   10,    0,    9,  303,    0,    0,
    0,    2,    0,    0,    0,    3,    4,    5,    1,    0,
    6,    9,    0,    7,    0,    0,    2,   10,    0,  304,
    3,    4,    5,    0,    0,    6,    0,    0,    7,    9,
    0,    1,   10,    0,    0,   96,   97,   98,   99,    2,
    0,    0,    0,    3,    4,    5,    0,  305,    6,    1,
   10,    7,    0,    0,    0,    0,    0,    2,  306,    1,
    0,    3,    4,    5,    0,    0,    6,    2,    0,    7,
    0,    3,    4,    5,    0,    0,    6,    0,    0,    7,
    0,  307,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  308,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    2,    0,    0,
    0,    3,    4,    5,    0,    0,    6,    0,    1,    7,
  113,  113,    0,    0,    0,    0,    2,    0,    0,    1,
    3,    4,    5,    0,    0,    6,    0,    2,    7,    0,
    0,    3,    4,    5,    0,    0,    6,    0,    0,    7,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    2,
    0,    0,    0,    3,    4,    5,    0,    0,    6,    0,
    0,    7,    0,    0,    0,    0,  145,  113,    0,    1,
  113,    0,    0,    0,    0,    0,    0,    2,    0,    0,
    1,    3,    4,    5,    0,    0,    6,    0,    2,    7,
    0,    0,    3,    4,    5,    0,    0,    6,    0,    0,
    7,    0,    0,    1,    0,  113,  113,    0,    0,    0,
    0,    2,    0,    0,    0,    3,    4,    5,    1,    0,
    6,    0,    0,    7,    0,    0,    2,    0,  113,  113,
    3,    4,    5,    0,    0,    6,    1,    0,    7,    0,
    0,    0,    0,    0,    2,    0,  113,    0,    3,    4,
    5,    0,    0,    6,    0,    0,    7,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  113,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,    0,   40,  257,   40,   44,   59,   41,    8,   41,
   41,   41,   58,   58,   61,  123,   59,   40,   39,   41,
   59,   41,   59,   41,   94,   95,  258,   56,  256,  257,
    8,   40,  257,  103,   44,   42,    9,   40,    1,   41,
   47,  115,   61,  275,  276,   41,   41,   10,    0,  270,
  271,   41,  273,   61,   44,  257,   42,   43,   41,   45,
   59,   47,   41,   58,   37,  135,  270,  271,   89,   90,
   58,   45,   41,   59,   59,   38,  150,  151,  267,  268,
   44,  258,   43,  112,   45,   44,  258,   41,   40,   43,
  257,   45,  275,  276,   94,   95,   87,   88,   41,  173,
  174,  123,   41,  103,  257,  257,  268,   59,  178,   61,
   40,   41,   58,   43,  268,   45,  257,  191,   81,  268,
   40,   59,   59,   59,  272,   45,   59,   59,  272,   59,
   60,   61,   62,   40,   41,  135,   43,  272,   45,  102,
  257,   59,   61,   41,   41,   61,   44,   61,   45,   61,
  258,   59,   59,   60,   61,   62,   59,   59,   59,   41,
   40,   41,   44,   43,  258,   45,  163,  164,  165,  166,
  167,  258,  258,  125,   59,   40,   41,  258,  178,   59,
   60,   61,   62,  258,   40,  182,   59,   61,  268,   45,
   59,   61,  123,  123,   59,  123,   61,  123,  123,   40,
   41,  230,   58,  256,   44,   61,   59,  258,  123,   40,
  257,  257,  257,  123,  256,  258,  123,  256,  257,  256,
   61,  257,  264,  223,  224,  225,  226,  227,  228,  229,
   61,  231,  270,  271,  257,  273,  270,  271,   40,  273,
  272,  272,  272,  123,  244,  223,  224,  225,  226,  227,
  228,  229,  272,  231,  272,  264,  258,  256,  123,   61,
  270,  271,  257,  273,  272,   40,  244,  257,  267,  268,
  256,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  123,  257,  258,   45,   61,   40,  288,  263,
  275,  276,  123,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  280,  281,  256,  257,  270,  271,   61,  273,
  288,  270,  271,  265,  273,  267,  268,  269,  270,  271,
   45,  123,  274,  256,  256,  277,  256,  257,   59,  259,
  260,  261,  262,   59,   59,  265,   40,  257,  258,  269,
  270,  271,   45,  263,  274,  275,  276,  277,  123,  256,
  257,  271,  259,  260,  261,  262,   59,   61,  265,  257,
  257,  258,  269,  270,  271,   40,  263,  274,  275,  276,
  277,  256,  125,   41,  271,  257,  256,  257,   59,  259,
  260,  261,  262,  256,  258,  265,   61,  256,  258,  269,
  270,  271,  257,   40,  274,  275,  276,  277,  267,  268,
  265,  257,  258,  256,  269,  270,  271,  263,   59,  274,
  275,  276,  277,  258,   61,  271,  257,   41,   59,   59,
   40,  125,  258,   59,  265,  258,  257,   41,  269,  270,
  271,   59,   59,  274,  265,   40,  277,  268,  269,  270,
  271,   61,  258,  274,  258,  258,  277,   41,  123,   41,
   40,  123,   41,   41,   41,  257,   61,  123,  123,  123,
   40,  123,  123,  265,  123,  123,  268,  269,  270,  271,
  123,   61,  274,   47,   -1,  277,   -1,   -1,  125,   40,
   -1,   61,  257,   -1,   -1,   -1,   -1,   -1,   -1,   40,
  265,   -1,   -1,   -1,  269,  270,  271,  257,  258,  274,
   61,   -1,  277,  263,  257,  125,   -1,   40,   -1,   -1,
   61,  271,  265,   -1,   -1,   -1,  269,  270,  271,   -1,
  125,  274,   40,   -1,  277,  256,   -1,   -1,   61,   -1,
  256,   -1,  257,  258,   -1,  125,   -1,   -1,  263,   -1,
   -1,   40,   -1,   61,   -1,  125,  271,   -1,   -1,   -1,
   -1,   40,   -1,  257,  257,  258,   -1,   -1,   -1,   -1,
  263,  265,   61,   -1,  125,  269,  270,  271,  271,   -1,
  274,   -1,   61,  277,  125,  256,   40,   -1,   -1,   -1,
   -1,   -1,  257,  198,  199,  200,  201,  202,  203,   -1,
  265,   40,  125,   -1,  269,  270,  271,   61,   -1,  274,
   -1,   -1,  277,  218,   -1,  256,   -1,  125,   -1,   -1,
  257,   -1,   61,   -1,   40,  256,  256,   -1,  265,   43,
  256,   45,  269,  270,  271,   -1,  125,  274,  256,  256,
  277,   -1,   40,   -1,  123,   61,   60,  257,   62,   -1,
   -1,   -1,   40,   -1,   -1,  265,   -1,   -1,   -1,  269,
  270,  271,  257,   61,  274,   -1,   -1,  277,   -1,   -1,
  265,  125,   -1,   61,  269,  270,  271,  257,   -1,  274,
   -1,   -1,  277,   -1,   -1,  265,  125,  257,   -1,  269,
  270,  271,   40,   -1,  274,  265,   -1,  277,   -1,  269,
  270,  271,   -1,   -1,  274,   -1,  257,  277,   -1,  125,
   -1,   40,   -1,   61,  265,   -1,  257,   -1,  269,  270,
  271,   -1,   40,  274,  265,   -1,  277,  125,  269,  270,
  271,   -1,   61,  274,  257,   -1,  277,  125,   -1,   -1,
   -1,   -1,  265,   61,   40,   -1,  269,  270,  271,  257,
   -1,  274,   -1,   -1,  277,   -1,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,   -1,   61,  274,   -1,  257,  277,
   -1,   -1,   40,   -1,   -1,   -1,  265,  125,  257,   -1,
  269,  270,  271,   40,   -1,  274,  265,   -1,  277,   -1,
  269,  270,  271,   61,   -1,  274,  125,   -1,  277,   -1,
   -1,   -1,   -1,  257,   61,   -1,   40,  125,   -1,   -1,
   -1,  265,   -1,   -1,   -1,  269,  270,  271,  257,   -1,
  274,   40,   -1,  277,   -1,   -1,  265,   61,   -1,  125,
  269,  270,  271,   -1,   -1,  274,   -1,   -1,  277,   40,
   -1,  257,   61,   -1,   -1,  259,  260,  261,  262,  265,
   -1,   -1,   -1,  269,  270,  271,   -1,  125,  274,  257,
   61,  277,   -1,   -1,   -1,   -1,   -1,  265,  125,  257,
   -1,  269,  270,  271,   -1,   -1,  274,  265,   -1,  277,
   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,  277,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,   -1,   -1,  274,   -1,  257,  277,
   55,   56,   -1,   -1,   -1,   -1,  265,   -1,   -1,  257,
  269,  270,  271,   -1,   -1,  274,   -1,  265,  277,   -1,
   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,  277,
   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,
   -1,   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,
   -1,  277,   -1,   -1,   -1,   -1,  111,  112,   -1,  257,
  115,   -1,   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,
  257,  269,  270,  271,   -1,   -1,  274,   -1,  265,  277,
   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,
  277,   -1,   -1,  257,   -1,  150,  151,   -1,   -1,   -1,
   -1,  265,   -1,   -1,   -1,  269,  270,  271,  257,   -1,
  274,   -1,   -1,  277,   -1,   -1,  265,   -1,  173,  174,
  269,  270,  271,   -1,   -1,  274,  257,   -1,  277,   -1,
   -1,   -1,   -1,   -1,  265,   -1,  191,   -1,  269,  270,
  271,   -1,   -1,  274,   -1,   -1,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  230,
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

//#line 354 "gramatica.y"

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
//#line 689 "Parser.java"
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
case 92:
//#line 254 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  			                                   adminTerceto.desapilar();}
break;
case 94:
//#line 259 "gramatica.y"
{Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.desapilar();
                     adminTerceto.apilar(t.getNumero());
                     }
break;
case 95:
//#line 266 "gramatica.y"
{System.out.println(" se leyó una sentencia IF" + val_peek(0).sval);
				if(val_peek(0).sval != null){
					Terceto t = new Terceto("BF", val_peek(0).sval, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.apilar(t.getNumero());
				}}
break;
case 96:
//#line 282 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
break;
case 97:
//#line 283 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
break;
case 98:
//#line 284 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
break;
case 99:
//#line 286 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
break;
case 100:
//#line 288 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
break;
case 101:
//#line 291 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 102:
//#line 293 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
break;
case 103:
//#line 297 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");}
break;
case 105:
//#line 301 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
break;
case 106:
//#line 302 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
break;
case 107:
//#line 303 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
break;
case 108:
//#line 304 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
break;
case 109:
//#line 305 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
break;
case 110:
//#line 308 "gramatica.y"
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
case 112:
//#line 327 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
break;
case 113:
//#line 328 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
break;
case 114:
//#line 329 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
break;
case 115:
//#line 333 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + val_peek(3).sval );}
break;
case 117:
//#line 337 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
break;
case 118:
//#line 338 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
break;
case 119:
//#line 339 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
break;
case 120:
//#line 340 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
break;
case 121:
//#line 343 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 122:
//#line 344 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 124:
//#line 348 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
break;
case 125:
//#line 349 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
break;
case 126:
//#line 350 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
break;
case 127:
//#line 351 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
break;
//#line 1347 "Parser.java"
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
