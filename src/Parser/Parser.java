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
   21,   21,   21,   21,   21,   16,   23,   22,   25,   26,
   26,   26,   26,   28,   28,   28,   29,   29,   29,   29,
   27,   27,   27,   27,   27,   27,   24,   24,   17,   17,
   17,   31,   30,   32,   32,   32,   32,   32,   32,   32,
   18,   18,   33,   33,   33,   33,   33,   19,   19,   34,
   34,   34,   20,   20,   36,   36,   36,   36,   35,   35,
   35,   37,   37,   37,   37,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    2,    2,    1,    2,    1,    1,
    3,    2,    1,    3,    2,    3,    1,    1,    2,   11,
    1,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,    1,    3,    5,    1,    7,    2,    3,    4,    4,
    2,    3,    1,    1,    2,    2,    2,    2,    2,    1,
    2,    2,    2,    2,    2,   12,    1,    3,    3,    1,
    3,    3,    4,    3,    3,    1,    1,    1,    2,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    6,    8,
    1,    1,    1,    5,    5,    5,    5,    5,    7,    7,
    4,    1,    3,    3,    2,    4,    3,    3,    1,    2,
    2,    2,    4,    1,    3,    3,    3,    3,    3,    5,
    1,    2,    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   43,   44,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
   21,    0,    0,    0,    0,    0,   50,   81,   92,   99,
  104,    0,   68,   67,    0,    0,    0,    0,    0,    0,
    0,   66,    0,  111,   70,    0,   83,    0,    0,    0,
    0,    0,    0,    0,    7,    0,    0,    0,    0,    5,
    8,   17,    0,   18,   15,   12,   51,   45,   52,   46,
   53,   47,   54,   48,   55,   49,  113,    0,    0,  107,
    0,    0,   69,  112,    0,    0,    0,    0,    0,    0,
  106,    0,    0,   74,   75,   73,   76,   71,   72,    0,
    0,   93,    0,    0,   97,    0,    0,    0,    0,    0,
    0,    0,   35,    0,    3,  105,   14,   19,   11,    0,
  115,  109,    0,  103,    0,    0,   64,   65,    0,    0,
    0,    0,    0,    0,    0,   96,   91,    0,    0,    0,
    0,    0,   41,    0,    0,    0,    0,   16,   63,  114,
    0,   85,   87,    0,    0,   86,   84,   58,    0,   57,
   42,    0,    0,    0,    0,    0,    0,   38,    0,  110,
    0,   79,    0,    0,    0,    0,    0,    0,    0,   39,
   40,    0,   89,    0,   77,   78,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   80,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   36,    0,    0,   24,   25,
   27,   30,    0,   29,   28,   26,   23,   22,    0,   20,
   56,
};
final static short yydgoto[] = {                         11,
   12,   61,   14,   15,   16,   17,   18,   63,   19,   20,
   64,  111,   21,  112,  113,   22,   23,   24,   25,   26,
   27,  107,  159,  187,   47,   48,  100,   41,   42,   49,
  155,   28,   29,   30,   43,   31,   44,
};
final static short yysindex[] = {                        88,
   93,  118,  -11,    0,    0,  -14,  -40,  275,  -50,  158,
    0,    0,    0,  103,    0,    0,    0, -215,  -44,    0,
    0,   21,   25,   70,   98,  110,    0,    0,    0,    0,
    0,  -48,    0,    0,   19,   23,  158,  136, -204,   13,
   -8,    0,   38,    0,    0,  146,    0,  403,   16,   20,
   35, -194,  -36, -227,    0,  113,  -48,   56,   13,    0,
    0,    0,  388,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -209,  158,    0,
  104,   13,    0,    0,  136,  136,  136,  136,   11, -186,
    0,   88,   53,    0,    0,    0,    0,    0,    0,  158,
   88,    0,   42,   50,    0,   34,   40, -197,  -28, -156,
   79,   46,    0,   80,    0,    0,    0,    0,    0, -138,
    0,    0,   -5,    0,   -8,   -8,    0,    0, -135,   67,
 -145,   63, -141,   13, -137,    0,    0, -128,  158, -125,
 -136,  -32,    0, -132, -227,   68, -131,    0,    0,    0,
 -123,    0,    0,    0, -159,    0,    0,    0,   76,    0,
    0,   81,   86,  -43,   91,  170, -227,    0,   95,    0,
   78,    0, -165, -121,  -99,  -42,  -97,  -96, -227,    0,
    0,  -92,    0, -101,    0,    0,  -90,   47,   49,   52,
 -103,   54,   55,  129,   57,    0,  138,  275,  275,  275,
  131,  275,  275,  275, -227,  275,   60,  154,  165,  180,
  204,  242,  260,  302,  331,    0,  346,  275,    0,    0,
    0,    0,  357,    0,    0,    0,    0,    0,  372,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -31,    0,    0,    0,    0,  123,    0,    0,  139,
  -38,    0,    0,    0,    0,    0,    0,    0,    0,  253,
    0,    0,    0,    0,    0,    6,    0,    0,  266,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  285,  310,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  339,    0,    0,    0,    0,    0,    0,
    0,  -24,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -10,   27,    0,    0,    0,    0,
    0,    0,    0,   37,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -16,    0,    0,    0,    0,
    0,    0,    0,  234,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   -9,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  351,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    4,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  354,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
   14,  477,  498,    0,    0,    0,  527,    0,    0,    0,
    0,  -17,    0,  348,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,   65,    0,   31,   17,  143,
    0,    0,    0,    0,   18,    0,    0,
};
final static int YYTABLESIZE=732;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         54,
    2,   60,   60,  109,   60,    6,   60,   39,  164,   78,
   70,   70,  141,   70,   66,   70,   32,  177,  191,  202,
   60,   60,   60,   60,   37,   52,   58,   70,   51,   61,
   61,   33,   61,   87,   61,  149,  114,   85,   88,   86,
    7,   62,    4,    5,   34,  108,  121,  122,   61,   61,
   61,   61,   84,   81,   83,   85,  101,   86,   79,    2,
  102,    7,  106,   80,    6,   40,   62,   62,  129,   62,
  130,   62,    4,    5,   59,  105,   59,   59,   91,   68,
   39,   90,  136,   70,   60,   62,   62,   62,   62,  145,
  137,  142,    9,  132,  138,   59,  116,   59,  139,   90,
  143,   82,    9,  127,  128,  131,  133,  171,  172,  185,
  186,  167,   61,   10,  135,  125,  126,    9,  148,  144,
  147,  150,  152,   10,  151,    7,  156,    9,   72,  158,
  157,  161,   36,  170,  173,  162,  188,   38,   10,  165,
  169,  174,    9,  123,  124,  154,  175,   90,   10,   62,
   39,  178,    9,   37,  201,  182,   74,   46,  189,   59,
  192,  193,   38,   10,  134,  195,  196,  197,   76,  198,
    9,  199,  205,   10,  200,    8,  203,  204,  207,  206,
   38,  102,  218,  160,  184,    8,   92,  216,   93,    0,
   38,   10,    0,    9,    0,    0,    0,  100,    0,    0,
    8,    0,   38,    0,    9,    0,   57,    0,   77,    0,
    8,   65,    0,  179,   10,  190,   53,   60,   60,    9,
   60,   60,   60,   60,   70,   10,   60,   60,  176,    0,
   60,   60,   60,    4,    5,   60,  108,  115,   60,  163,
   10,    4,    5,    9,  108,   61,   61,   32,   61,   61,
   61,   61,   50,  211,   61,   37,    2,    7,   61,   61,
   61,    6,   33,   61,   10,    7,   61,    2,    2,    7,
    7,    7,    6,    6,    7,   34,   67,    7,  219,   57,
   69,    9,   62,   62,    0,   62,   62,   62,   62,  220,
  103,   62,   88,   59,   89,   62,   62,   62,  104,    9,
   62,   59,   10,   62,  221,   59,   59,   59,    0,    1,
   59,   95,   89,   59,    9,    4,    5,    2,  108,    1,
   10,    3,    4,    5,  101,   71,    6,    2,  222,    7,
  153,    3,    4,    5,    1,   10,    6,    4,    5,    7,
  108,    9,    2,  108,    1,  183,    3,    4,    5,   32,
   33,    6,    2,   73,    7,   34,    3,    4,    5,    1,
   89,    6,   10,   35,    7,   75,  224,    2,   98,    1,
    9,    3,    4,    5,   45,   33,    6,    2,  102,    7,
   34,    3,    4,    5,  225,    9,    6,    1,   35,    7,
    0,   10,   45,   33,  100,    2,    9,   94,   34,    3,
    4,    5,   45,   33,    6,    0,   10,    7,   34,   90,
    1,    9,   31,    0,   45,   33,   35,   10,    2,    0,
   34,    1,    3,    4,    5,    0,  226,    6,   35,    2,
    7,  120,   10,    3,    4,    5,    1,    0,    6,    4,
    5,    7,  108,    0,    2,   85,  119,   86,    3,    4,
    5,    0,    0,    6,    0,  227,    7,    0,    0,  146,
    1,    0,   98,    0,   99,    0,    0,    0,    2,    0,
  228,    0,    3,    4,    5,    0,   13,    6,    0,    0,
    7,  230,    0,    0,   55,    0,    0,    0,    0,   88,
    0,    0,  166,  168,    0,    0,  231,    0,    1,    0,
   82,   82,    0,    0,    0,   56,    2,    0,   95,    0,
    3,    4,    5,  180,  181,    6,    1,    0,    7,    0,
    0,  101,    0,    0,    2,    0,  194,    0,    3,    4,
    5,    1,    0,    6,    0,    0,    7,    0,    0,    2,
  108,    0,    0,    3,    4,    5,    0,    0,    6,    0,
    0,    7,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,    0,   98,    2,    0,   13,   13,
    3,    4,    5,    0,    0,    6,    0,   13,    7,  110,
  110,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,   94,    2,    0,    0,    0,    3,
    4,    5,    1,    0,    6,    0,   90,    7,   13,   31,
    2,    0,    0,    1,    3,    4,    5,    0,    0,    6,
    0,    2,    7,    0,    0,    3,    4,    5,    1,    0,
    6,    0,    0,    7,  140,  110,    2,    0,  110,    0,
    3,    4,    5,  117,  118,    6,    0,   13,    7,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   94,   95,   96,   97,    0,    0,    0,    0,    0,
    0,  110,  110,    0,   55,   55,   55,   55,   55,   55,
   55,    0,   55,    0,    0,    0,    0,   55,    0,    0,
    0,    0,  110,  110,   55,  208,  209,  210,  212,  213,
  214,  215,    0,  217,    0,  110,    0,    0,  223,    0,
    0,    0,    0,    0,    0,  229,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  110,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   41,   40,   43,    0,   45,   58,   41,   58,
   42,   43,   41,   45,   59,   47,   41,   61,   61,  123,
   59,   60,   61,   62,   41,   40,    9,   59,   40,   40,
   41,   41,   43,   42,   45,   41,   54,   43,   47,   45,
   40,  257,  270,  271,   41,  273,  256,  257,   59,   60,
   61,   62,  257,   36,   38,   43,   41,   45,   40,   59,
   41,   61,  257,   41,   59,    1,   40,   41,   58,   43,
  257,   45,  270,  271,   10,   41,   40,   41,   41,   59,
   58,   44,   41,   59,  123,   59,   60,   61,   62,   44,
   41,  109,   40,   41,   61,   59,   41,   61,   59,   44,
  257,   37,   40,   87,   88,   92,   93,  267,  268,  275,
  276,   44,  123,   61,  101,   85,   86,   40,  257,   41,
   41,  257,  268,   61,   58,  125,  268,   40,   59,  258,
  268,  257,   40,  257,   59,  272,  258,   45,   61,  272,
  272,   61,   40,   79,   41,  132,   61,   44,   61,  123,
   58,   61,   40,   61,  258,   61,   59,   40,  258,  123,
  258,  258,   45,   61,  100,  258,  268,  258,   59,  123,
   40,  123,   44,   61,  123,  123,  123,  123,   41,  123,
   45,   59,  123,  139,  171,  123,   41,  205,   46,   -1,
   45,   61,   -1,   40,   -1,   -1,   -1,   59,   -1,   -1,
  123,   -1,   45,   -1,   40,   -1,  257,   -1,  257,   -1,
  123,  256,   -1,   44,   61,  258,  257,  256,  257,   40,
  259,  260,  261,  262,  256,   61,  265,  125,  272,   -1,
  269,  270,  271,  270,  271,  274,  273,  125,  277,  272,
   61,  270,  271,   40,  273,  256,  257,  272,  259,  260,
  261,  262,  264,  123,  265,  272,  256,  257,  269,  270,
  271,  256,  272,  274,   61,  265,  277,  267,  268,  269,
  270,  271,  267,  268,  274,  272,  256,  277,  125,  257,
  256,   40,  256,  257,   -1,  259,  260,  261,  262,  125,
  256,  265,   59,  257,  257,  269,  270,  271,  264,   40,
  274,  265,   61,  277,  125,  269,  270,  271,   -1,  257,
  274,   59,  257,  277,   40,  270,  271,  265,  273,  257,
   61,  269,  270,  271,   59,  256,  274,  265,  125,  277,
  268,  269,  270,  271,  257,   61,  274,  270,  271,  277,
  273,   40,  265,   59,  257,  268,  269,  270,  271,  257,
  258,  274,  265,  256,  277,  263,  269,  270,  271,  257,
  257,  274,   61,  271,  277,  256,  125,  265,   59,  257,
   40,  269,  270,  271,  257,  258,  274,  265,  256,  277,
  263,  269,  270,  271,  125,   40,  274,  257,  271,  277,
   -1,   61,  257,  258,  256,  265,   40,   59,  263,  269,
  270,  271,  257,  258,  274,   -1,   61,  277,  263,   59,
  257,   40,   59,   -1,  257,  258,  271,   61,  265,   -1,
  263,  257,  269,  270,  271,   -1,  125,  274,  271,  265,
  277,   44,   61,  269,  270,  271,  257,   -1,  274,  270,
  271,  277,  273,   -1,  265,   43,   59,   45,  269,  270,
  271,   -1,   -1,  274,   -1,  125,  277,   -1,   -1,  112,
  257,   -1,   60,   -1,   62,   -1,   -1,   -1,  265,   -1,
  125,   -1,  269,  270,  271,   -1,    0,  274,   -1,   -1,
  277,  125,   -1,   -1,    8,   -1,   -1,   -1,   -1,  256,
   -1,   -1,  145,  146,   -1,   -1,  125,   -1,  257,   -1,
  267,  268,   -1,   -1,   -1,    8,  265,   -1,  256,   -1,
  269,  270,  271,  166,  167,  274,  257,   -1,  277,   -1,
   -1,  256,   -1,   -1,  265,   -1,  179,   -1,  269,  270,
  271,  257,   -1,  274,   -1,   -1,  277,   -1,   -1,  265,
  256,   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,
   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  265,   -1,   92,   93,
  269,  270,  271,   -1,   -1,  274,   -1,  101,  277,   53,
   54,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  265,   -1,   -1,   -1,  269,
  270,  271,  257,   -1,  274,   -1,  256,  277,  132,  256,
  265,   -1,   -1,  257,  269,  270,  271,   -1,   -1,  274,
   -1,  265,  277,   -1,   -1,  269,  270,  271,  257,   -1,
  274,   -1,   -1,  277,  108,  109,  265,   -1,  112,   -1,
  269,  270,  271,  256,  257,  274,   -1,  171,  277,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  259,  260,  261,  262,   -1,   -1,   -1,   -1,   -1,
   -1,  145,  146,   -1,  198,  199,  200,  201,  202,  203,
  204,   -1,  206,   -1,   -1,   -1,   -1,  211,   -1,   -1,
   -1,   -1,  166,  167,  218,  198,  199,  200,  201,  202,
  203,  204,   -1,  206,   -1,  179,   -1,   -1,  211,   -1,
   -1,   -1,   -1,   -1,   -1,  218,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  205,
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
"condicion_for : condicion",
"asignacion_for : IDE '=' CTE_UINT",
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
//#line 582 "Parser.java"
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
case 57:
//#line 132 "gramatica.y"
{if(val_peek(0).sval != null){
				Terceto t = new Terceto("BF", val_peek(0).sval, null);
                          	adminTerceto.agregarTerceto(t);
                          	adminTerceto.apilar(t.getNumero());}
                          }
break;
case 58:
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
case 59:
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
case 60:
//#line 186 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 61:
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
case 62:
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
case 63:
//#line 213 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una conversión");
	  			Operando op = (Operando)val_peek(1).obj;
	  			 if(op != null)
	  				yyval = new ParserVal(new Operando("DOUBLE",op.getValor()));
	  			else
	  				yyval = new ParserVal(null);
	  			}
break;
case 64:
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
case 65:
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
case 66:
//#line 249 "gramatica.y"
{ yyval = new ParserVal((Operando)val_peek(0).obj);}
break;
case 67:
//#line 252 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante double -> " + val_peek(0).sval);
			yyval = new ParserVal(new Operando("DOUBLE", val_peek(0).sval));
			}
break;
case 68:
//#line 255 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó la constante uint -> " + val_peek(0).sval);
                     	yyval = new ParserVal(new Operando("UINT", val_peek(0).sval));
                        }
break;
case 69:
//#line 258 "gramatica.y"
{	if(chequearFactorNegado()){
        			Operando op = (Operando)val_peek(0).obj;
        			yyval = new ParserVal(new Operando(op.getTipo(), "-" + op.getValor()));
        			}}
break;
case 70:
//#line 262 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó el identificador -> " + val_peek(0).sval);
		if(Main.tSimbolos.getDatosTabla(val_peek(0).sval).isDeclarada())
                	yyval = new ParserVal(new Operando(Main.tSimbolos.getDatosTabla(val_peek(0).sval).getTipo(), val_peek(0).sval));
                else {
                       	System.out.println("La variable " + val_peek(0).sval +" no fue declarada");
                       	yyval = new ParserVal(null);
                }}
break;
case 71:
//#line 271 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 72:
//#line 272 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 73:
//#line 273 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 74:
//#line 274 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 75:
//#line 275 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 76:
//#line 276 "gramatica.y"
{yyval = new ParserVal("!=");}
break;
case 77:
//#line 279 "gramatica.y"
{yyval = new ParserVal("+");}
break;
case 78:
//#line 280 "gramatica.y"
{yyval = new ParserVal("-");}
break;
case 79:
//#line 283 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF");
								adminTerceto.desapilar();}
break;
case 80:
//#line 288 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  			                                   adminTerceto.desapilar();}
break;
case 82:
//#line 293 "gramatica.y"
{Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.desapilar();
                     adminTerceto.apilar(t.getNumero());
                     }
break;
case 83:
//#line 300 "gramatica.y"
{System.out.println(" se leyó una sentencia IF" + val_peek(0).sval);
				if(val_peek(0).sval != null){
					Terceto t = new Terceto("BF", val_peek(0).sval, null);
					adminTerceto.agregarTerceto(t);
					adminTerceto.apilar(t.getNumero());
				}}
break;
case 84:
//#line 316 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
break;
case 85:
//#line 317 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
break;
case 86:
//#line 318 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
break;
case 87:
//#line 320 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
break;
case 88:
//#line 322 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
break;
case 89:
//#line 326 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 90:
//#line 328 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
break;
case 91:
//#line 332 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");}
break;
case 93:
//#line 336 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
break;
case 94:
//#line 337 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
break;
case 95:
//#line 338 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
break;
case 96:
//#line 339 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
break;
case 97:
//#line 340 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
break;
case 98:
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
case 100:
//#line 362 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
break;
case 101:
//#line 363 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
break;
case 102:
//#line 364 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
break;
case 103:
//#line 368 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + val_peek(3).sval );}
break;
case 105:
//#line 372 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
break;
case 106:
//#line 373 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
break;
case 107:
//#line 374 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
break;
case 108:
//#line 375 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
break;
case 109:
//#line 378 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 110:
//#line 379 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 112:
//#line 383 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
break;
case 113:
//#line 384 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
break;
case 114:
//#line 385 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
break;
case 115:
//#line 386 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
break;
//#line 1229 "Parser.java"
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
