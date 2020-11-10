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
   31,   31,   31,   26,   26,   19,   19,   19,   35,   35,
   35,   35,   35,   34,   36,   36,   36,   36,   36,   36,
   36,   36,   20,   20,   37,   37,   37,   37,   37,   21,
   21,   38,   38,   38,   22,   22,   40,   40,   40,   40,
   39,   39,   39,   41,   41,   41,   41,
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
    1,    1,    1,    1,    1,    6,    4,    1,    5,    4,
    4,    4,    4,    1,    5,    5,    5,    5,    5,    4,
    4,    4,    4,    1,    3,    3,    2,    4,    3,    3,
    1,    2,    2,    2,    4,    1,    3,    3,    3,    3,
    3,    5,    1,    2,    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   45,   46,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
    0,   21,   26,    0,    0,    0,    0,    0,   52,   59,
    0,   98,  114,  121,  126,    0,   85,   84,    0,    0,
    0,    0,    0,    0,    0,   83,    0,  133,   87,    0,
  104,    0,    0,    0,    0,    0,    0,    0,    0,   62,
    0,    0,    7,    0,    0,    0,    0,    5,    8,   17,
    0,   18,   15,   12,    0,    0,   53,   47,   54,   48,
   55,   49,   56,   50,   57,   51,    0,    0,  135,    0,
    0,  129,    0,    0,   86,  134,    0,    0,    0,    0,
    0,    0,  128,    0,    0,   91,   92,   90,   93,   88,
   89,    0,    0,  115,    0,    0,  119,   64,    0,    0,
   63,    0,    0,    0,    0,    0,    0,   37,    0,    3,
  127,   14,   19,   11,    0,   23,    0,   22,    0,    0,
    0,  137,  131,    0,  125,    0,    0,   81,   82,    0,
    0,    0,    0,    0,    0,    0,  118,  113,   65,   61,
    0,    0,   60,    0,    0,    0,    0,   43,    0,    0,
    0,    0,   16,   20,   97,  111,  112,  110,   80,  136,
    0,  106,  108,    0,  107,  105,    0,    0,    0,    0,
   44,    0,    0,    0,    0,    0,    0,   40,    0,  132,
   96,   94,   95,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,   42,    0,    0,    0,    0,
    0,    0,    0,   29,   30,   32,    0,   31,   28,    0,
   27,    0,    0,    0,    0,    0,    0,    0,   33,   25,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   38,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   68,
   70,   72,   74,    0,   73,   71,   69,   67,   66,   58,
};
final static short yydgoto[] = {                         11,
   12,   69,   14,   15,   16,   17,   18,   71,   19,   20,
   72,   21,   22,  126,   23,  127,  128,   24,   25,   26,
   27,   28,   29,   59,  162,  204,   30,  163,   60,   52,
  112,   45,   46,   53,   31,   32,   33,   34,   47,   35,
   48,
};
final static short yysindex[] = {                       310,
  -40,  256,  -28,    0,    0,  -32,  -33,  531,  -49,  365,
    0,    0,    0,  329,    0,    0,    0, -240,   44,    0,
  344,    0,    0,   50,   74,   79,   98,  111,    0,    0,
  118,    0,    0,    0,    0,  -44,    0,    0,   -9,   -3,
  365,  296, -230,   -6,   25,    0,    7,    0,    0,  419,
    0,  699,   23,   27,  156,  -42,  -36, -215,   12,    0,
  -10, -191,    0,  368,  -44,   40,   -6,    0,    0,    0,
  160,    0,    0,    0,  387,  397,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  310,  129,    0, -169,
  365,    0,   55,   -6,    0,    0,  296,  296,  296,  296,
  -12, -182,    0,  310,  144,    0,    0,    0,    0,    0,
    0,  365,  310,    0,   84,   90,    0,    0, -173,  306,
    0,  365, -150,  165, -166,   93,   66,    0,   96,    0,
    0,    0,    0,    0, -118,    0,  415,    0, -128,  154,
 -127,    0,    0,   -1,    0,   25,   25,    0,    0, -113,
   95, -123,  182, -114,   -6, -109,    0,    0,    0,    0,
  452,  101,    0,  102,  -94, -107,  -30,    0, -106, -191,
  108, -105,    0,    0,    0,    0,    0,    0,    0,    0,
  -89,    0,    0,  -97,    0,    0, -153,  -46, -153, -153,
    0,  112,  113,  -45,  114,  170, -191,    0,  115,    0,
    0,    0,    0,  -81, -226,  -80,  -77,  -76,  -75,  -72,
   53,  -71,  -70, -191,    0,    0,  -69,  150,  151,   -5,
  152,  157,  158,    0,    0,    0, -145,    0,    0,  159,
    0,   72,   78,  -15,   86,  192,  200,  202,    0,    0,
 -191,  531,  531,  531,  426,  531,  531,  531,  531,    0,
  437,  448,  464,  480,  495,  505,  521,  550,  568,    0,
    0,    0,    0,  579,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  606,    0,    0,    0,    0,
  137,    0,    0,  141,   33,    0,    0,    0,    0,    0,
    0,    0,    0,  176,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    6,    0,    0,  265,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  286,  332,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  348,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -13,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  371,    0,    0,    0,
    0,    0,    0,    0,    0,   57,   87,    0,    0,    0,
    0,  215,  -65,  259,  -37,  281,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -7,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  103,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   15,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   16,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  375,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  742,    2,   37,    0,    0,    0,  670,    0,    0,    0,
    0,    0,    0,   88,    0,  689,    0,    0,    0,    0,
    0,    0,    0,  283, -102, -136,    0,   13,    0,   60,
    0,   38,    5,  161,    0,    0,    0,    0,   26,    0,
    0,
};
final static int YYTABLESIZE=961;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         40,
    2,   13,   76,   76,   42,    6,   62,   57,   43,   63,
  194,   55,  205,   90,   51,  212,   70,   43,  119,  164,
   41,   76,   63,   76,   58,  245,   96,   34,   58,  124,
   91,  219,   13,   39,   66,  235,   97,   92,   98,  179,
    2,   97,  121,   98,   64,  150,   95,  103,  202,  203,
  102,  206,  207,  208,   43,   35,   36,   76,  188,    2,
   44,    2,   51,  113,    6,   93,   99,  114,  220,   67,
  122,  100,   77,   77,  151,   77,   63,   77,    4,    5,
  131,  123,  159,  102,  160,   76,  142,  143,   13,   13,
  168,   77,   77,   77,   77,  145,   78,   78,  102,   78,
   94,   78,   74,  148,  149,   13,   13,  244,   78,  170,
  239,  137,  240,  227,   13,   78,   78,   78,   78,    4,
    5,  202,  203,    2,  157,    7,   79,   79,    6,   79,
  158,   79,   80,  169,  146,  147,  172,   82,  173,  175,
  178,   13,   99,  180,  182,   79,   79,   79,   79,  129,
  144,  197,  181,  185,   13,   77,   84,    9,  186,  189,
  190,  109,  191,   99,  192,  195,  199,  200,    9,   86,
  201,  155,  209,  210,  213,  217,  218,  221,   10,   78,
  222,  223,  224,    9,  153,  225,  228,  229,  231,   10,
  232,  233,  236,    9,  242,  124,  117,  237,  238,  122,
  243,  103,  241,  135,   10,  166,    0,   65,  246,   79,
  105,  167,   89,  214,   10,  118,   36,   37,  134,   76,
   56,    9,   38,   61,   56,   99,  211,   76,  202,  203,
   39,   76,   76,   76,  117,   54,   76,   76,   76,   76,
    8,  193,   10,   63,   63,   63,   63,   63,   63,   63,
   63,    8,  234,   65,  101,   63,    2,    2,   34,    4,
    5,    6,  123,  101,   39,    2,    8,    2,    2,    2,
    2,    2,    6,    6,    2,  101,    8,    2,  251,  252,
  253,  255,  256,  257,  258,  259,   35,   36,   77,   77,
  264,   77,   77,   77,   77,   50,  101,   77,  102,   73,
   42,   77,   77,   77,    8,   77,   77,   77,   77,   77,
  226,  101,   78,   78,  247,   78,   78,   78,   78,  102,
  100,   78,  248,  123,  249,   78,   78,   78,  250,   79,
   78,   78,   78,   78,   81,    4,    5,  101,  123,  120,
   42,  100,   79,   79,  130,   79,   79,   79,   79,    9,
   42,   79,    0,   83,    0,   79,   79,   79,  109,   99,
   79,   79,   79,   79,  161,    0,   85,   99,    9,   99,
   10,   99,   99,   99,    1,    0,   99,    4,    5,   99,
  123,  102,    2,    9,   87,    1,    3,    4,    5,   10,
  120,    6,  124,    2,    7,  140,  122,    3,    4,    5,
    1,    0,    6,  100,   10,    7,  116,    9,    2,   42,
    1,  115,    3,    4,    5,  132,  133,    6,    2,  116,
    7,  176,    3,    4,    5,    0,    9,    6,   10,   24,
    7,  117,    8,   75,    4,    5,    9,  123,    1,    4,
    5,    0,  123,    0,    0,    0,    2,   10,    0,  183,
    3,    4,    5,   68,    9,    6,    0,   10,    7,  104,
    0,    0,    0,   42,    0,    9,   75,    0,    0,    0,
    0,  101,    0,    0,    0,   10,    9,    0,    0,  101,
    0,  101,    0,  101,  101,  101,   10,    9,  101,    0,
    0,  101,  130,    0,    0,    0,   42,   10,    0,    0,
    0,    0,    0,    9,    0,    0,    0,    0,   10,    0,
  187,  136,   49,   37,    0,  102,    0,    0,   38,    9,
  123,  138,    0,  102,   10,  102,   39,  102,  102,  102,
    0,    0,  102,    0,    9,  102,    0,  100,    0,  174,
   10,  130,    0,    0,    9,  100,    0,  100,  254,  100,
  100,  100,   49,   37,  100,   10,    0,  100,   38,    0,
    9,  260,   49,   37,    0,   10,    1,    0,   38,    0,
    9,    0,  261,    0,    2,    0,   39,    0,    3,    4,
    5,   10,    0,    6,    0,    1,    7,  120,  262,    9,
    0,   10,    0,    2,    0,    0,    0,    3,    4,    5,
    1,    0,    6,  116,  263,    7,    0,    9,    2,    0,
   10,    0,    3,    4,    5,    0,    0,    6,    9,  265,
    7,   49,   37,    0,    1,    0,   24,   38,   10,  266,
   75,    0,    2,    0,    0,   39,    3,    4,    5,   10,
    0,    6,    0,    1,    7,  267,    0,   87,   87,    0,
   87,    2,   87,    1,    0,    3,    4,    5,    0,    0,
    6,    2,    0,    7,   87,    3,    4,    5,    0,    0,
    6,    1,    0,    7,  268,   49,   37,    0,    0,    2,
    0,   38,    1,    3,    4,    5,    0,    0,    6,   39,
    2,    7,  269,    1,    3,    4,    5,    0,    0,    6,
    0,    2,    7,  270,    1,    3,    4,    5,   49,   37,
    6,    0,    2,    7,   38,    0,    3,    4,    5,    0,
    1,    6,   39,    0,    7,    0,    0,    0,    2,    0,
  125,  125,    3,    4,    5,    0,    1,    6,    0,    0,
    7,   97,    0,   98,    2,    0,    0,    0,    3,    4,
    5,    1,    0,    6,    0,    0,    7,    0,  110,    2,
  111,    1,    0,    3,    4,    5,    0,    0,    6,    2,
    0,    7,   88,    3,    4,    5,    0,    1,    6,    0,
    0,    7,    0,    0,    0,    2,    0,    1,    0,    3,
    4,    5,  165,  125,    6,    2,  125,    7,    0,    3,
    4,    5,    0,    0,    6,    0,    1,    7,    0,    0,
    0,    0,    0,    0,    2,  171,    0,    0,    3,    4,
    5,    0,    0,    6,    1,    0,    7,    0,  139,  141,
    0,    0,    2,    0,    0,    1,    3,    4,    5,  125,
  125,    6,    0,    2,    7,  152,  154,    3,    4,    5,
    0,    0,    6,    0,  156,    7,    0,    0,  196,  198,
    0,   87,    0,    0,    0,  125,  125,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  177,    0,  125,  215,  216,    0,    0,    0,    0,
    0,    0,    0,    0,  184,    0,    0,    0,    0,    0,
    0,    0,  230,    0,    0,    0,    0,    0,    0,    0,
  125,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  106,  107,  108,
  109,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,    0,   40,   41,   45,    0,   40,   40,   58,    8,
   41,   40,   59,   58,    2,   61,  257,   58,   61,  122,
   61,   59,   21,   61,   61,   41,  257,   41,   61,   40,
   40,  258,   31,   41,    9,   41,   43,   41,   45,   41,
   40,   43,  258,   45,    8,   58,   42,   41,  275,  276,
   44,  188,  189,  190,   58,   41,   41,   21,  161,   59,
    1,   61,   50,   41,   59,   40,   42,   41,  205,   10,
   59,   47,   40,   41,  257,   43,   75,   45,  270,  271,
   41,  273,  256,   44,  258,  123,  256,  257,   87,   88,
  257,   59,   60,   61,   62,   41,   40,   41,   44,   43,
   41,   45,   59,   99,  100,  104,  105,  123,   59,   44,
  256,   75,  258,   61,  113,   59,   60,   61,   62,  270,
  271,  275,  276,  123,   41,  125,   40,   41,  123,   43,
   41,   45,   59,   41,   97,   98,   41,   59,  257,  268,
  268,  140,   40,  257,  268,   59,   60,   61,   62,   62,
   91,   44,   58,  268,  153,  123,   59,   40,  268,   59,
   59,   59,  257,   61,  272,  272,  272,  257,   40,   59,
  268,  112,   61,   61,   61,   61,  258,  258,   61,  123,
  258,  258,  258,   40,   41,  258,  258,  258,  258,   61,
   41,   41,   41,   40,  123,   59,   41,   41,   41,   59,
  123,  267,   44,   44,   61,   41,   -1,  257,  123,  123,
   50,  124,  257,   44,   61,  258,  257,  258,   59,  257,
  257,   40,  263,  257,  257,  123,  272,  265,  275,  276,
  271,  269,  270,  271,   59,  264,  274,  275,  276,  277,
  123,  272,   61,  242,  243,  244,  245,  246,  247,  248,
  249,  123,  258,  257,   40,  254,  256,  257,  272,  270,
  271,  256,  273,  257,  272,  265,  123,  267,  268,  269,
  270,  271,  267,  268,  274,   61,  123,  277,  242,  243,
  244,  245,  246,  247,  248,  249,  272,  272,  256,  257,
  254,  259,  260,  261,  262,   40,  257,  265,   40,  256,
   45,  269,  270,  271,  123,  256,  274,  275,  276,  277,
  258,  257,  256,  257,  123,  259,  260,  261,  262,   61,
   40,  265,  123,   59,  123,  269,  270,  271,  241,  256,
  274,  275,  276,  277,  256,  270,  271,  123,  273,   57,
   45,   61,  256,  257,   59,  259,  260,  261,  262,   40,
   45,  265,   -1,  256,   -1,  269,  270,  271,  256,  257,
  274,  275,  276,  277,   59,   -1,  256,  265,   40,  267,
   61,  269,  270,  271,  257,   -1,  274,  270,  271,  277,
  273,  123,  265,   40,  267,  257,  269,  270,  271,   61,
   59,  274,  256,  265,  277,  267,  256,  269,  270,  271,
  257,   -1,  274,  123,   61,  277,   59,   40,  265,   45,
  257,  256,  269,  270,  271,  256,  257,  274,  265,  264,
  277,  268,  269,  270,  271,   -1,   40,  274,   61,   59,
  277,  256,  123,   59,  270,  271,   40,  273,  257,  270,
  271,   -1,  273,   -1,   -1,   -1,  265,   61,   -1,  268,
  269,  270,  271,  125,   40,  274,   -1,   61,  277,   41,
   -1,   -1,   -1,   45,   -1,   40,  123,   -1,   -1,   -1,
   -1,  257,   -1,   -1,   -1,   61,   40,   -1,   -1,  265,
   -1,  267,   -1,  269,  270,  271,   61,   40,  274,   -1,
   -1,  277,  125,   -1,   -1,   -1,   45,   61,   -1,   -1,
   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   61,   -1,
   59,  125,  257,  258,   -1,  257,   -1,   -1,  263,   40,
  256,  125,   -1,  265,   61,  267,  271,  269,  270,  271,
   -1,   -1,  274,   -1,   40,  277,   -1,  257,   -1,  125,
   61,  256,   -1,   -1,   40,  265,   -1,  267,  123,  269,
  270,  271,  257,  258,  274,   61,   -1,  277,  263,   -1,
   40,  125,  257,  258,   -1,   61,  257,   -1,  263,   -1,
   40,   -1,  125,   -1,  265,   -1,  271,   -1,  269,  270,
  271,   61,   -1,  274,   -1,  257,  277,  256,  125,   40,
   -1,   61,   -1,  265,   -1,   -1,   -1,  269,  270,  271,
  257,   -1,  274,  256,  125,  277,   -1,   40,  265,   -1,
   61,   -1,  269,  270,  271,   -1,   -1,  274,   40,  125,
  277,  257,  258,   -1,  257,   -1,  256,  263,   61,  125,
  256,   -1,  265,   -1,   -1,  271,  269,  270,  271,   61,
   -1,  274,   -1,  257,  277,  125,   -1,   42,   43,   -1,
   45,  265,   47,  257,   -1,  269,  270,  271,   -1,   -1,
  274,  265,   -1,  277,   59,  269,  270,  271,   -1,   -1,
  274,  257,   -1,  277,  125,  257,  258,   -1,   -1,  265,
   -1,  263,  257,  269,  270,  271,   -1,   -1,  274,  271,
  265,  277,  125,  257,  269,  270,  271,   -1,   -1,  274,
   -1,  265,  277,  125,  257,  269,  270,  271,  257,  258,
  274,   -1,  265,  277,  263,   -1,  269,  270,  271,   -1,
  257,  274,  271,   -1,  277,   -1,   -1,   -1,  265,   -1,
   61,   62,  269,  270,  271,   -1,  257,  274,   -1,   -1,
  277,   43,   -1,   45,  265,   -1,   -1,   -1,  269,  270,
  271,  257,   -1,  274,   -1,   -1,  277,   -1,   60,  265,
   62,  257,   -1,  269,  270,  271,   -1,   -1,  274,  265,
   -1,  277,   31,  269,  270,  271,   -1,  257,  274,   -1,
   -1,  277,   -1,   -1,   -1,  265,   -1,  257,   -1,  269,
  270,  271,  123,  124,  274,  265,  127,  277,   -1,  269,
  270,  271,   -1,   -1,  274,   -1,  257,  277,   -1,   -1,
   -1,   -1,   -1,   -1,  265,  127,   -1,   -1,  269,  270,
  271,   -1,   -1,  274,  257,   -1,  277,   -1,   87,   88,
   -1,   -1,  265,   -1,   -1,  257,  269,  270,  271,  170,
  171,  274,   -1,  265,  277,  104,  105,  269,  270,  271,
   -1,   -1,  274,   -1,  113,  277,   -1,   -1,  170,  171,
   -1,  256,   -1,   -1,   -1,  196,  197,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  140,   -1,  214,  196,  197,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  153,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  214,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  241,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  259,  260,  261,
  262,
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
"seleccion : seleccion_con_else ELSE bloque END_IF",
"seleccion : error_if",
"seleccion_con_else : IF '(' if_condicion ')' bloque",
"seleccion_con_else : IF if_condicion ')' bloque",
"seleccion_con_else : IF '(' ')' bloque",
"seleccion_con_else : IF '(' if_condicion bloque",
"seleccion_con_else : IF '(' if_condicion ')'",
"if_condicion : condicion",
"error_if : IF if_condicion ')' bloque END_IF",
"error_if : IF '(' ')' bloque END_IF",
"error_if : IF '(' if_condicion bloque END_IF",
"error_if : IF '(' if_condicion ')' END_IF",
"error_if : IF '(' if_condicion ')' bloque",
"error_if : seleccion_con_else bloque bloque END_IF",
"error_if : seleccion_con_else bloque ELSE END_IF",
"error_if : seleccion_con_else bloque ELSE bloque",
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

//#line 541 "gramatica.y"

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
//#line 693 "Parser.java"
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
                          else
                          	yyval = new ParserVal(null);
                          }
break;
case 61:
//#line 226 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz? una asignaci?n al identificador -> " + val_peek(2).sval);
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
//#line 241 "gramatica.y"
{yyval = new ParserVal(null);}
break;
case 63:
//#line 244 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta el identificador");}
break;
case 64:
//#line 245 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '='");}
break;
case 65:
//#line 246 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta una constante UINT");}
break;
case 66:
//#line 249 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '('");}
break;
case 67:
//#line 250 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ';'");}
break;
case 68:
//#line 251 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta la condici�n");}
break;
case 69:
//#line 252 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ';'");}
break;
case 70:
//#line 253 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta la palabra UP o DOWN");}
break;
case 71:
//#line 254 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta una constante CTE_UINT");}
break;
case 72:
//#line 255 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta ')'");}
break;
case 73:
//#line 256 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '{'");}
break;
case 74:
//#line 257 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta el bloque de sentencias");}
break;
case 75:
//#line 258 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un FOR mal declarado, falta '}'");}
break;
case 76:
//#line 261 "gramatica.y"
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
//#line 275 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 78:
//#line 276 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una suma");
				Operando op1 = (Operando)val_peek(2).obj;
				Operando op2 = (Operando)val_peek(0).obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("+", op1.getValor(), op2.getValor());
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
//#line 291 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una resta");
	  			Operando op1 = (Operando)val_peek(2).obj;
                                Operando op2 = (Operando)val_peek(0).obj;
                                if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("-", op1.getValor(), op2.getValor());
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
//#line 305 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una conversi�n");
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
//#line 318 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una multiplicacion");
				Operando op1 = (Operando)val_peek(2).obj;
				Operando op2 = (Operando)val_peek(0).obj;
				if(op1 != null && op2 !=null){
					if(op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("*", op1.getValor(), op2.getValor());
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
//#line 333 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una division");
				Operando op1 = (Operando)val_peek(2).obj;
                                Operando op2 = (Operando)val_peek(0).obj;
                                if(op1 != null && op2 !=null){
					if (op1.getTipo().equals(op2.getTipo())){
						Terceto t = new Terceto("/", op1.getValor(), op2.getValor());
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
//#line 347 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 84:
//#line 350 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� la constante double -> " + val_peek(0).sval);
			yyval = new ParserVal(new Operando("DOUBLE", val_peek(0).sval));
			}
break;
case 85:
//#line 353 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� la constante uint -> " + val_peek(0).sval);
                     	yyval = new ParserVal(new Operando("UINT", val_peek(0).sval));
                        }
break;
case 86:
//#line 356 "gramatica.y"
{	if(chequearFactorNegado()){
        			Operando op = (Operando)val_peek(0).obj;
        			yyval = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
break;
case 87:
//#line 360 "gramatica.y"
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
//#line 370 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 89:
//#line 371 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 90:
//#line 372 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 91:
//#line 373 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 92:
//#line 374 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 93:
//#line 375 "gramatica.y"
{yyval = new ParserVal("!=");}
break;
case 94:
//#line 378 "gramatica.y"
{yyval = new ParserVal("+");}
break;
case 95:
//#line 379 "gramatica.y"
{yyval = new ParserVal("-");}
break;
case 96:
//#line 382 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia IF");
						   if(val_peek(3).sval != null)adminTerceto.desapilar();}
break;
case 97:
//#line 391 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se ley� una sentencia IF con ELSE");
                                                  if(val_peek(3).sval != null) adminTerceto.desapilar();}
break;
case 99:
//#line 396 "gramatica.y"
{if(val_peek(2).sval != null){
                    	  				Terceto t = new Terceto("BI", null, null);
                                                        adminTerceto.agregarTerceto(t);
                                                        adminTerceto.desapilar();
                                               	        adminTerceto.apilar(t.getNumero());
                                               	        yyval = new ParserVal("bien");
                                               	       }
                                               	    else
                                               	    	yyval = new ParserVal(null);}
break;
case 100:
//#line 405 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta '('");}
break;
case 101:
//#line 406 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta la condicion");}
break;
case 102:
//#line 407 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta ')'");}
break;
case 103:
//#line 408 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el bloque de sentencias");}
break;
case 104:
//#line 418 "gramatica.y"
{if(val_peek(0).sval != null){
				Terceto t = new Terceto("BF", val_peek(0).sval, null);
				adminTerceto.agregarTerceto(t);
				adminTerceto.apilar(t.getNumero());
			}
			else
				yyval = new ParserVal(null);}
break;
case 105:
//#line 427 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta '('");}
break;
case 106:
//#line 428 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta la condicion");}
break;
case 107:
//#line 429 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta ')'");}
break;
case 108:
//#line 430 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el bloque de sentencias");}
break;
case 109:
//#line 431 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el END_IF o ELSE");}
break;
case 110:
//#line 439 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el ELSE");}
break;
case 111:
//#line 440 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 112:
//#line 441 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta el END_IF");}
break;
case 113:
//#line 444 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una sentencia OUT");
			Terceto t = new Terceto("OUT", val_peek(1).sval, null);
			adminTerceto.agregarTerceto(t);}
break;
case 115:
//#line 450 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, falta '('");}
break;
case 116:
//#line 451 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + "  se detect� un OUT mal declarado, falta ')'");}
break;
case 117:
//#line 452 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + "  se detect� un OUT mal declarado, faltan '(' y ')'");}
break;
case 118:
//#line 453 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, entre los par�ntesis debe ir una cadena");}
break;
case 119:
//#line 454 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un OUT mal declarado, falta la cadena entre los parent�sis en el OUT");}
break;
case 120:
//#line 457 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una asignaci�n al identificador -> " + val_peek(2).sval);
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
case 122:
//#line 476 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " falta '=' en la asignaci�n");}
break;
case 123:
//#line 477 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignaci�n");}
break;
case 124:
//#line 478 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " falta una expresi�n aritm�tica del lado derecho de la asignaci�n");}
break;
case 125:
//#line 482 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realiz� una invocacion al procedimiento -> " + val_peek(3).sval );
				   lista_param_invocacion = (ArrayList<Pair<String, String>>)val_peek(1).obj;
			  	   if(lista_param_invocacion!= null && !lista_param_invocacion.isEmpty()){ /* Hubo un error mas abajo*/
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
case 127:
//#line 507 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el identificador");}
break;
case 128:
//#line 508 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el '('");}
break;
case 129:
//#line 509 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, faltan los par�metros");}
break;
case 130:
//#line 510 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocaci�n mal declarada, falta el ')'");}
break;
case 131:
//#line 513 "gramatica.y"
{/*System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los par�metros -> " + $1.sval +" y " +$3.sval);*/
			  lista_param_invocacion.clear();
			  String ambitoVariable = Main.tSimbolos.verificarAmbito(val_peek(0).sval, ambito);
			  if(ambitoVariable != null){
			  	lista_param_invocacion.add(new Pair<String,String>(val_peek(2).sval, ambitoVariable));
			  	yyval = new ParserVal(lista_param_invocacion);} /* esto no se si como seria pq hay 2 listas :'(*/
			  else
			  	System.out.println("La variable "+val_peek(0).sval+ "no se encuentra en el ambito");
			  }
break;
case 132:
//#line 522 "gramatica.y"
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
case 134:
//#line 535 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta el identificador de la izquierda");}
break;
case 135:
//#line 536 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta ':' entre los identificadores");}
break;
case 136:
//#line 537 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta la ',' que separa los identificadores");}
break;
case 137:
//#line 538 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron par�metros mal declarados, falta el identificador de la derecha");}
break;
//#line 1576 "Parser.java"
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
