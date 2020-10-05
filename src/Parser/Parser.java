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
//#line 20 "Parser.java"




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
   28,   28,   29,   26,   26,   26,   26,   26,   26,   23,
   23,   17,   17,   17,   30,   30,   30,   30,   30,   30,
   30,   30,   30,   30,   30,   30,   18,   18,   31,   31,
   31,   31,   31,   19,   19,   32,   32,   32,   20,   20,
   34,   34,   34,   34,   33,   33,   33,   35,   35,   35,
   35,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    2,    2,    1,    2,    1,    1,
    3,    2,    1,    3,    2,    1,    3,    1,    2,   11,
    1,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,    1,    3,    5,    1,    7,    2,    3,    4,    4,
    2,    3,    1,    1,    2,    2,    2,    2,    2,    1,
    2,    2,    2,    2,    2,   14,    1,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   13,
    3,    1,    3,    3,    4,    3,    3,    1,    1,    1,
    1,    1,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    8,   12,    1,    7,    7,    7,    7,    7,    7,
    7,   11,   11,   11,   11,   11,    4,    1,    3,    3,
    2,    4,    3,    3,    1,    2,    2,    2,    4,    1,
    3,    3,    3,    3,    3,    5,    1,    2,    2,    4,
    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   43,   44,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
   21,    0,    0,    0,    0,    0,   50,   57,   94,  108,
  115,  120,    0,   80,   79,    0,    0,    0,    0,    0,
    0,    0,   78,   81,    0,  127,   82,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    0,    0,    0,
    0,    5,    8,   16,    0,   18,   15,   12,   51,   45,
   52,   46,   53,   47,   54,   48,   55,   49,  129,    0,
    0,  123,    0,    0,   83,  128,    0,    0,    0,    0,
    0,    0,  122,    0,    0,    0,   87,   88,   86,   89,
   84,   85,    0,  109,    0,    0,  113,    0,    0,    0,
    0,    0,    0,    0,    0,   35,    0,    3,  121,   14,
   19,   11,    0,  131,  125,    0,  119,    0,    0,   76,
   77,    0,    0,    0,    0,    0,    0,    0,  112,  107,
    0,    0,    0,    0,    0,    0,    0,   41,    0,    0,
    0,    0,   17,   75,  130,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   42,    0,    0,    0,
    0,    0,    0,   38,    0,  126,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,   40,    0,   96,   97,   99,
  100,    0,   98,   95,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,    0,   90,   91,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   36,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   24,   25,   27,
   30,    0,   29,   28,   26,   23,   22,  104,  105,    0,
  103,  102,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,   93,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   58,   60,   63,
   65,   67,   69,    0,   68,   66,   64,   62,   61,   59,
   56,
};
final static short yydgoto[] = {                         11,
   12,   63,   14,   15,   16,   17,   18,   65,   19,   20,
   66,  114,   21,  115,  116,   22,   23,   24,   25,   26,
   27,   49,  225,   28,   50,  103,   42,   43,   44,   29,
   30,   31,   45,   32,   46,
};
final static short yysindex[] = {                       104,
  362,  327,  -36,    0,    0,  -33,  -28,  806,  -52,  582,
    0,    0,    0,  119,    0,    0,    0, -234,  -45,    0,
    0,   36,   53,   55,   94,   95,    0,    0,    0,    0,
    0,    0,  -50,    0,    0,   -7,  -38,  582, -233, -219,
   28,  -10,    0,    0,   17,    0,    0,  181,   11,   72,
   25,  -31,   13,  -40,  -18, -214,    0,  129,  -50,   21,
   28,    0,    0,    0,  123,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -180,
  582,    0,   31,   28,    0,    0,   56,   56,   56,   56,
   22, -171,    0,  -29,  -17,  -27,    0,    0,    0,    0,
    0,    0,  582,    0,   46,   49,    0, -160,  -43, -159,
 -192,   10, -155,   62,   16,    0,   63,    0,    0,    0,
    0,    0, -152,    0,    0,   -2,    0,  -10,  -10,    0,
    0, -149,   39,  806,  806,  147,  806,   28,    0,    0,
   50,   51,  -46,   57, -138, -151,  -41,    0, -150, -214,
   20, -148,    0,    0,    0, -137,  157,  180,  190,  216,
  245,  582,  582,  352,  582,  582,    0,   64,   65,  -56,
   68,   37, -214,    0,   69,    0, -145, -126, -122,   78,
 -120, -118,   93,   96,  600,   97,   99,  107, -127,  -90,
   86,  -88,  -84, -214,    0,    0,  -83,    0,    0,    0,
    0,  -95,    0,    0, -193, -193, -193,   41, -193, -193,
 -193,   54,   58,   60, -107,   61,   66,  132,   70,  278,
    0,  806,    0,    0,  -80,  -79,  -70, -222,  -67,  -66,
  -63,  -62,  806,  806,  806,  302,  806,  806,  806, -214,
  806,  331,  347,  369,  158,  159,  160,  161,  -26,  163,
  165,  168,  169,  379,  401,  428,  438,  456,  466,  481,
  497,    0,  516,  -54,   88,  117,  137,  214,  217,  243,
  290,  -16,  292,  306,  312,  313,  319,    0,    0,    0,
    0,  526,    0,    0,    0,    0,    0,    0,    0,  175,
    0,    0,  806,  806,  806,  806,  806,  542,  806,  806,
  806,  806,  806,    0,    0,  552,  567,  591,  607,  622,
  640,  650,  680,  702,  717,  749,  764,    0,    0,    0,
    0,    0,    0,  788,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    2,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   98,    0,    0,    0,    0,  112,    0,    0,
  126,  893,    0,    0,    0,    0,    0,    0,    0,    0,
  135,    0,    0,    0,    0,    0,    0,  446,    0,    0,
  139,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  277,  301,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  305,    0,    0,    0,    0,
    0,    0,    0,    0,  -24,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  901,  968,    0,
    0,    0,    0,    0,    0,    0,    0,  -30,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -22,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -15,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  318,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  324,    0,    0,    0,    0,    0,    0,    0,  373,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  374,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    1,   27,    0,    0,    0,  949,    0,    0,    0,
    0,  354,    0,  -81,    0,    0,    0,    0,    0,    0,
    0,  837, -161,    0,   30,    0,   -3,   -1,    0,    0,
    0,    0,   18,    0,    0,
};
final static int YYTABLESIZE=1244;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        170,
   13,    2,   82,   52,  192,   40,   54,   80,   57,  107,
   71,   56,  165,   68,  273,  237,   32,  143,   37,   40,
  110,  112,   64,  136,  298,   33,   60,  222,   71,   85,
   41,   89,   81,  151,   58,  248,   90,   86,  154,   61,
   87,    7,   88,   34,  226,  227,  229,  230,  231,  232,
  146,   96,  223,  224,   83,    4,    5,   93,  111,  150,
   92,  119,    7,  173,   92,  104,  249,   84,  172,  174,
   87,  127,   88,  108,   92,  124,  125,    4,    5,  132,
  194,  223,  224,  128,  129,  133,  139,  130,  131,  140,
  195,  196,   71,  134,   70,  137,  156,  141,  144,  228,
   39,  148,  149,  152,  153,  135,  297,  155,  162,  163,
  126,   72,  218,   74,   87,  166,   88,    9,  167,  176,
  168,  171,  198,  175,  189,  190,    7,    9,  193,  197,
  212,  101,  138,  102,   57,   57,   57,   57,   10,   82,
   82,  199,   82,    9,   82,  200,  215,  203,   10,  204,
  236,  205,   76,   78,  206,  209,   82,  210,    9,   57,
  157,  158,  160,  161,   10,  211,  123,  213,    9,  216,
  118,  220,  221,  217,  219,  240,  233,  245,  246,   10,
  234,  122,  235,  238,  116,  180,    9,  247,  239,   10,
  250,  251,  241,  111,  252,  253,    9,  117,  268,  269,
  270,  271,  202,  274,   59,  275,   79,   10,  276,  277,
   67,  164,  290,  288,  142,  191,  109,   10,   59,    9,
   57,   94,   57,   53,  105,   39,    8,   51,   55,    9,
  169,  272,  106,   57,   57,   57,   57,   57,   57,   57,
   10,   57,   57,   62,   71,   71,  243,   32,  244,   37,
   10,    4,    5,  118,  111,    9,   33,   57,    7,  254,
  255,  256,  258,  259,  260,  261,    7,  263,  265,  159,
    7,    7,    7,   91,   34,    7,   10,   91,    7,    4,
    5,  177,  111,  282,    9,    4,    5,   91,  111,    4,
    5,   69,  111,   57,   57,   57,   57,   57,   57,   57,
   57,   57,   57,   57,  178,   10,    4,    5,   71,  111,
   73,   57,   47,   34,  179,  223,  224,    9,   35,  306,
  307,  308,  309,  310,  312,  313,  314,  315,  316,  317,
   97,   98,   99,  100,    1,  124,  293,  324,   10,  294,
  181,    9,    2,  214,    1,  201,    3,    4,    5,   75,
   77,    6,    2,   82,    7,  289,    3,    4,    5,  114,
    1,    6,   10,  110,    7,  295,   48,  118,    2,  182,
    9,   39,    3,    4,    5,    1,  101,    6,  120,  121,
    7,  116,   31,    2,  291,    1,    9,    3,    4,    5,
  111,   10,    6,    2,  117,    7,   39,    3,    4,    5,
  242,   37,    6,    1,  292,    7,   39,   10,    9,  117,
  185,    2,  296,    1,  299,    3,    4,    5,    9,   40,
    6,    2,   38,    7,  257,    3,    4,    5,  300,   10,
    6,  106,   70,    7,  301,  302,    1,   47,   34,   10,
    9,  303,  305,   35,    2,    6,    1,    0,    3,    4,
    5,   36,    0,    6,    2,  264,    7,    0,    3,    4,
    5,   10,    0,    6,    0,  147,    7,    9,    0,    0,
    0,  266,    1,    0,    0,    0,    0,    9,    0,    0,
    2,    0,    0,    0,    3,    4,    5,    0,   10,    6,
    0,    0,    7,  267,    0,    9,    0,    0,   10,    0,
    0,    1,    0,  278,    0,    9,    0,    0,    0,    2,
    0,    0,    0,    3,    4,    5,   10,    0,    6,    0,
    9,    7,    0,    0,    0,  279,   10,    0,    0,    0,
    0,    0,  124,    0,    1,    0,    9,    0,    0,    0,
    0,   10,    2,    0,    0,    0,    3,    4,    5,    0,
    0,    6,  280,    0,    7,    9,  114,   10,    1,    0,
  110,    0,  281,    0,    0,    9,    2,    0,    0,    0,
    3,    4,    5,  101,    0,    6,   10,    0,    7,   31,
  283,    9,    0,   47,   34,    0,   10,    1,    0,   35,
  284,    9,    0,  262,    0,    2,    0,   36,    0,    3,
    4,    5,   10,    1,    6,  285,    9,    7,   47,   34,
    0,    2,   10,    0,   35,    3,    4,    5,   33,   34,
    6,  286,   36,    7,   35,    1,   39,   10,  106,   70,
    9,    0,   36,    2,    0,    1,    0,    3,    4,    5,
  287,    0,    6,    2,   39,    7,    9,    3,    4,    5,
  304,   10,    6,    0,    0,    7,    0,    1,  207,    0,
    0,    9,    0,    0,  311,    2,    0,   10,    0,    3,
    4,    5,    0,    0,    6,    0,  318,    7,    0,    9,
    0,    0,   10,    0,    1,    0,    0,    0,    0,    9,
    0,  319,    2,    0,    1,    0,    3,    4,    5,    0,
   10,    6,    2,    0,    7,    0,    3,    4,    5,    0,
   10,    6,    1,    0,    7,  320,    0,    0,    0,    9,
    2,    0,    1,    0,    3,    4,    5,    0,    0,    6,
    2,  321,    7,    0,    3,    4,    5,    1,    0,    6,
   10,    9,    7,    0,    0,    2,  322,    0,    0,    3,
    4,    5,    0,    1,    6,    0,    9,    7,    0,    0,
    0,    2,   10,    0,  323,    3,    4,    5,    0,    0,
    6,    0,    1,    7,  325,    0,    0,   10,    0,    0,
    2,    0,    1,    0,    3,    4,    5,    0,    9,    6,
    2,    0,    7,    0,    3,    4,    5,    0,    1,    6,
    0,    0,    7,    9,  326,    0,    2,    0,    1,   10,
    3,    4,    5,    0,    0,    6,    2,    0,    7,    0,
    3,    4,    5,    1,   10,    6,  327,    9,    7,    0,
    0,    2,    0,    0,    0,    3,    4,    5,   47,   34,
    6,  328,    0,    7,   35,    9,    0,    1,   10,    0,
    0,    0,   36,    0,    0,    2,   47,   34,    0,    3,
    4,    5,   35,    1,    6,    0,   10,    7,    0,    0,
   36,    2,    0,  329,    0,    3,    4,    5,    1,    0,
    6,    0,    0,    7,   95,    0,    2,    0,  330,    0,
    3,    4,    5,    0,    0,    6,    1,    0,    7,    0,
    0,    0,    0,    0,    2,    0,    1,    0,    3,    4,
    5,    0,  331,    6,    2,    0,    7,    0,    3,    4,
    5,    0,    0,    6,    0,    0,    7,    0,    0,    0,
    0,    0,    0,   72,    0,   72,    1,   72,    0,    0,
    0,   73,    0,   73,    2,   73,    0,    0,    3,    4,
    5,   72,   72,    6,   72,    0,    7,    0,    1,   73,
   73,    0,   73,    0,    0,    0,    2,    0,    0,    0,
    3,    4,    5,    1,    0,    6,    0,    0,    7,    0,
    0,    2,    0,    0,    0,    3,    4,    5,    0,    0,
    6,    0,    0,    7,    0,    0,    0,    0,  183,  184,
  186,  187,  188,  113,  113,    1,    0,    0,   74,    0,
   74,    0,   74,    2,    0,   72,    0,    3,    4,    5,
    1,  208,    6,   73,    0,    7,   74,   74,    2,   74,
    0,    0,    3,    4,    5,    0,    0,    6,    0,    0,
    7,    0,    0,    0,    1,    0,    0,    0,    0,    0,
    0,    0,    2,    0,    0,    0,    3,    4,    5,  145,
  113,    6,    1,  113,    7,    0,    0,    0,    0,    0,
    2,    0,    0,    0,    3,    4,    5,    0,    0,    6,
    0,    0,    7,    0,    0,    0,    0,    0,    0,    0,
   74,    0,    0,    0,    0,    0,    0,    0,  113,  113,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  113,  113,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  113,    0,    0,    0,    0,    0,   72,    0,
    0,   72,   72,   72,   72,    0,   73,    0,    0,   73,
   73,   73,   73,    0,    0,    0,    0,   72,   72,    0,
    0,    0,    0,    0,    0,   73,   73,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  113,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   74,    0,    0,   74,   74,   74,   74,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   74,   74,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,    0,   41,   40,   61,   58,   40,   58,    8,   41,
   41,   40,   59,   59,   41,  123,   41,   61,   41,   58,
   61,   40,  257,   41,   41,   41,    9,  123,   59,  263,
    1,   42,   40,  115,    8,  258,   47,  257,   41,   10,
   43,   40,   45,   41,  206,  207,  208,  209,  210,  211,
   41,   41,  275,  276,   37,  270,  271,   41,  273,   44,
   44,   41,   61,   44,   44,   41,  228,   38,  150,  151,
   43,   41,   45,   61,   44,  256,  257,  270,  271,   58,
   44,  275,  276,   87,   88,  257,   41,   89,   90,   41,
  172,  173,  123,  123,   59,  123,   58,  258,  258,   59,
   45,  257,   41,   41,  257,  123,  123,  257,   59,   59,
   81,   59,  194,   59,   43,   59,   45,   40,  257,  257,
  272,  272,  268,  272,   61,   61,  125,   40,   61,   61,
  258,   60,  103,   62,  134,  135,  136,  137,   61,   42,
   43,  268,   45,   40,   47,  268,   61,  268,   61,  268,
  258,   59,   59,   59,   59,   59,   59,   59,   40,  159,
  134,  135,  136,  137,   61,   59,   44,  258,   40,  258,
   59,  267,  268,  258,  258,   44,  123,  258,  258,   61,
  123,   59,  123,  123,   59,  159,   40,  258,  123,   61,
  258,  258,  123,   59,  258,  258,   40,   59,   41,   41,
   41,   41,  125,   41,  257,   41,  257,   61,   41,   41,
  256,  258,  125,  268,  258,  272,  257,   61,  257,   40,
  220,   41,  222,  257,  256,   45,  123,  264,  257,   40,
  272,  258,  264,  233,  234,  235,  236,  237,  238,  239,
   61,  241,  242,  125,  275,  276,  220,  272,  222,  272,
   61,  270,  271,  125,  273,   40,  272,  257,  257,  233,
  234,  235,  236,  237,  238,  239,  265,  241,  242,  123,
  269,  270,  271,  257,  272,  274,   61,  257,  277,  270,
  271,  125,  273,  257,   40,  270,  271,  257,  273,  270,
  271,  256,  273,  293,  294,  295,  296,  297,  298,  299,
  300,  301,  302,  303,  125,   61,  270,  271,  256,  273,
  256,  311,  257,  258,  125,  275,  276,   40,  263,  293,
  294,  295,  296,  297,  298,  299,  300,  301,  302,  303,
  259,  260,  261,  262,  257,   59,  123,  311,   61,  123,
  125,   40,  265,  258,  257,  268,  269,  270,  271,  256,
  256,  274,  265,  256,  277,  268,  269,  270,  271,   59,
  257,  274,   61,   59,  277,  123,   40,  256,  265,  125,
   40,   45,  269,  270,  271,  257,   59,  274,  256,  257,
  277,  256,   59,  265,  268,  257,   40,  269,  270,  271,
  256,   61,  274,  265,  256,  277,   45,  269,  270,  271,
  123,   40,  274,  257,  268,  277,   45,   61,   40,   56,
   59,  265,  123,  257,  123,  269,  270,  271,   40,   58,
  274,  265,   61,  277,  123,  269,  270,  271,  123,   61,
  274,   59,   59,  277,  123,  123,  257,  257,  258,   61,
   40,  123,  268,  263,  265,    0,  257,   -1,  269,  270,
  271,  271,   -1,  274,  265,  125,  277,   -1,  269,  270,
  271,   61,   -1,  274,   -1,  112,  277,   40,   -1,   -1,
   -1,  125,  257,   -1,   -1,   -1,   -1,   40,   -1,   -1,
  265,   -1,   -1,   -1,  269,  270,  271,   -1,   61,  274,
   -1,   -1,  277,  125,   -1,   40,   -1,   -1,   61,   -1,
   -1,  257,   -1,  125,   -1,   40,   -1,   -1,   -1,  265,
   -1,   -1,   -1,  269,  270,  271,   61,   -1,  274,   -1,
   40,  277,   -1,   -1,   -1,  125,   61,   -1,   -1,   -1,
   -1,   -1,  256,   -1,  257,   -1,   40,   -1,   -1,   -1,
   -1,   61,  265,   -1,   -1,   -1,  269,  270,  271,   -1,
   -1,  274,  125,   -1,  277,   40,  256,   61,  257,   -1,
  256,   -1,  125,   -1,   -1,   40,  265,   -1,   -1,   -1,
  269,  270,  271,  256,   -1,  274,   61,   -1,  277,  256,
  125,   40,   -1,  257,  258,   -1,   61,  257,   -1,  263,
  125,   40,   -1,  240,   -1,  265,   -1,  271,   -1,  269,
  270,  271,   61,  257,  274,  125,   40,  277,  257,  258,
   -1,  265,   61,   -1,  263,  269,  270,  271,  257,  258,
  274,  125,  271,  277,  263,  257,   45,   61,  256,  256,
   40,   -1,  271,  265,   -1,  257,   -1,  269,  270,  271,
  125,   -1,  274,  265,   45,  277,   40,  269,  270,  271,
  125,   61,  274,   -1,   -1,  277,   -1,  257,   59,   -1,
   -1,   40,   -1,   -1,  123,  265,   -1,   61,   -1,  269,
  270,  271,   -1,   -1,  274,   -1,  125,  277,   -1,   40,
   -1,   -1,   61,   -1,  257,   -1,   -1,   -1,   -1,   40,
   -1,  125,  265,   -1,  257,   -1,  269,  270,  271,   -1,
   61,  274,  265,   -1,  277,   -1,  269,  270,  271,   -1,
   61,  274,  257,   -1,  277,  125,   -1,   -1,   -1,   40,
  265,   -1,  257,   -1,  269,  270,  271,   -1,   -1,  274,
  265,  125,  277,   -1,  269,  270,  271,  257,   -1,  274,
   61,   40,  277,   -1,   -1,  265,  125,   -1,   -1,  269,
  270,  271,   -1,  257,  274,   -1,   40,  277,   -1,   -1,
   -1,  265,   61,   -1,  125,  269,  270,  271,   -1,   -1,
  274,   -1,  257,  277,  125,   -1,   -1,   61,   -1,   -1,
  265,   -1,  257,   -1,  269,  270,  271,   -1,   40,  274,
  265,   -1,  277,   -1,  269,  270,  271,   -1,  257,  274,
   -1,   -1,  277,   40,  125,   -1,  265,   -1,  257,   61,
  269,  270,  271,   -1,   -1,  274,  265,   -1,  277,   -1,
  269,  270,  271,  257,   61,  274,  125,   40,  277,   -1,
   -1,  265,   -1,   -1,   -1,  269,  270,  271,  257,  258,
  274,  125,   -1,  277,  263,   40,   -1,  257,   61,   -1,
   -1,   -1,  271,   -1,   -1,  265,  257,  258,   -1,  269,
  270,  271,  263,  257,  274,   -1,   61,  277,   -1,   -1,
  271,  265,   -1,  125,   -1,  269,  270,  271,  257,   -1,
  274,   -1,   -1,  277,   48,   -1,  265,   -1,  125,   -1,
  269,  270,  271,   -1,   -1,  274,  257,   -1,  277,   -1,
   -1,   -1,   -1,   -1,  265,   -1,  257,   -1,  269,  270,
  271,   -1,  125,  274,  265,   -1,  277,   -1,  269,  270,
  271,   -1,   -1,  274,   -1,   -1,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   41,   -1,   43,  257,   45,   -1,   -1,
   -1,   41,   -1,   43,  265,   45,   -1,   -1,  269,  270,
  271,   59,   60,  274,   62,   -1,  277,   -1,  257,   59,
   60,   -1,   62,   -1,   -1,   -1,  265,   -1,   -1,   -1,
  269,  270,  271,  257,   -1,  274,   -1,   -1,  277,   -1,
   -1,  265,   -1,   -1,   -1,  269,  270,  271,   -1,   -1,
  274,   -1,   -1,  277,   -1,   -1,   -1,   -1,  162,  163,
  164,  165,  166,   55,   56,  257,   -1,   -1,   41,   -1,
   43,   -1,   45,  265,   -1,  123,   -1,  269,  270,  271,
  257,  185,  274,  123,   -1,  277,   59,   60,  265,   62,
   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,
  277,   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  265,   -1,   -1,   -1,  269,  270,  271,  111,
  112,  274,  257,  115,  277,   -1,   -1,   -1,   -1,   -1,
  265,   -1,   -1,   -1,  269,  270,  271,   -1,   -1,  274,
   -1,   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  150,  151,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  172,  173,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  194,   -1,   -1,   -1,   -1,   -1,  256,   -1,
   -1,  259,  260,  261,  262,   -1,  256,   -1,   -1,  259,
  260,  261,  262,   -1,   -1,   -1,   -1,  275,  276,   -1,
   -1,   -1,   -1,   -1,   -1,  275,  276,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  240,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,   -1,   -1,  259,  260,  261,  262,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  275,  276,
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
"lista_de_variables : IDE",
"lista_de_variables : lista_de_variables ',' IDE",
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
"factor : factor_negado",
"factor : IDE",
"factor_negado : '-' CTE_DOUBLE",
"comparador : '<'",
"comparador : '>'",
"comparador : IGUAL_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : MENOR_IGUAL",
"comparador : DISTINTO",
"inc_decr : UP",
"inc_decr : DOWN",
"seleccion : IF '(' condicion ')' '{' bloque_sentencias '}' END_IF",
"seleccion : IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{' bloque_sentencias '}' END_IF",
"seleccion : error_if",
"error_if : IF condicion ')' '{' bloque_sentencias '}' END_IF",
"error_if : IF '(' ')' '{' bloque_sentencias '}' END_IF",
"error_if : IF '(' condicion '{' bloque_sentencias '}' END_IF",
"error_if : IF '(' condicion ')' bloque_sentencias '}' END_IF",
"error_if : IF '(' condicion ')' '{' '}' END_IF",
"error_if : IF '(' condicion ')' '{' bloque_sentencias END_IF",
"error_if : IF '(' condicion ')' '{' bloque_sentencias '}'",
"error_if : IF '(' condicion ')' '{' bloque_sentencias '}' '{' bloque_sentencias '}' END_IF",
"error_if : IF '(' condicion ')' '{' bloque_sentencias '}' ELSE bloque_sentencias '}' END_IF",
"error_if : IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{' '}' END_IF",
"error_if : IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{' bloque_sentencias END_IF",
"error_if : IF '(' condicion ')' '{' bloque_sentencias '}' ELSE '{' bloque_sentencias '}'",
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

//#line 218 "gramatica.y"

private Lexico lexico;
public Parser(Lexico lexico)
{
  this.lexico= lexico;
}

public int yylex(){
   Token token = this.lexico.getToken();
   if(token != null ){
   	int val =token.getId();
   	yyval = new ParserVal(token.getLexema());
   	return val;
   }
   return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}
//#line 707 "Parser.java"
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
//#line 21 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó un bloque de sentencias mal declarado, falta '{'");}
break;
case 6:
//#line 22 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó un bloque de sentencias mal declarado, falta '}'");}
break;
case 9:
//#line 29 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una sentencia declarativa");}
break;
case 10:
//#line 30 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una sentencia de ejecución");}
break;
case 11:
//#line 33 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se detectó una declaracion");}
break;
case 14:
//#line 38 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 15:
//#line 39 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 16:
//#line 42 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] lei un ID");}
break;
case 19:
//#line 47 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ',' entre los Id");}
break;
case 20:
//#line 49 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "]se declaro una PROC");}
break;
case 22:
//#line 54 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarada, falta IDE");}
break;
case 23:
//#line 55 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarada, falta '('");}
break;
case 24:
//#line 56 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta lista de parametros");}
break;
case 25:
//#line 57 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta ')'");}
break;
case 26:
//#line 58 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta NI ");}
break;
case 27:
//#line 59 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, '=' despues de NI ");}
break;
case 28:
//#line 60 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta la CTE_UINT ");}
break;
case 29:
//#line 61 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '{' que abre el bloque de sentecias ");}
break;
case 30:
//#line 62 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta el bloque de sentencias");}
break;
case 31:
//#line 63 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un procedimiento mal declarado, falta '}' que cierra el bloque de sentencias");}
break;
case 36:
//#line 72 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron más parametros de los permitidos (3)");}
break;
case 37:
//#line 73 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 38:
//#line 74 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 39:
//#line 75 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 40:
//#line 76 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ','");}
break;
case 43:
//#line 83 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] lei un tipo UINT");}
break;
case 44:
//#line 84 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] lei un tipo DOUBLE");}
break;
case 51:
//#line 95 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 52:
//#line 96 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 53:
//#line 97 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 54:
//#line 98 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 55:
//#line 99 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una sentencia mal declarada, falta ';'");}
break;
case 56:
//#line 102 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] lei un FOR");}
break;
case 58:
//#line 106 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '('");}
break;
case 59:
//#line 107 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta id ");}
break;
case 60:
//#line 108 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '='");}
break;
case 61:
//#line 109 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta 'CTE_UINT'");}
break;
case 62:
//#line 110 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 63:
//#line 111 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la condición");}
break;
case 64:
//#line 112 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ';'");}
break;
case 65:
//#line 113 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta la palabra UP o DOWN");}
break;
case 66:
//#line 114 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta 'CTE_UINT'");}
break;
case 67:
//#line 115 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta ')'");}
break;
case 68:
//#line 116 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '{'");}
break;
case 69:
//#line 117 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta el bloque de sentencias");}
break;
case 70:
//#line 118 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un FOR mal declarado, falta '}'");}
break;
case 73:
//#line 124 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una suma");}
break;
case 74:
//#line 125 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una resta");}
break;
case 75:
//#line 126 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una conversion");}
break;
case 76:
//#line 129 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una multiplicacion");}
break;
case 77:
//#line 130 "gramatica.y"
{ System.out.println("[Parser | Linea " + Lexico.linea + "] se realizo una division");}
break;
case 79:
//#line 134 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una cte double");}
break;
case 80:
//#line 135 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una cte uint");}
break;
case 82:
//#line 137 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un identificador");}
break;
case 92:
//#line 155 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un IF");}
break;
case 93:
//#line 156 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó un IF con ELSE");}
break;
case 95:
//#line 160 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '('");}
break;
case 96:
//#line 161 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta la condicion");}
break;
case 97:
//#line 162 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta ')'");}
break;
case 98:
//#line 163 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '{'");}
break;
case 99:
//#line 164 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
break;
case 100:
//#line 165 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '}'");}
break;
case 101:
//#line 166 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
break;
case 102:
//#line 167 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el ELSE");}
break;
case 103:
//#line 168 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '{'");}
break;
case 104:
//#line 169 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 105:
//#line 170 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta '}'");}
break;
case 106:
//#line 171 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
break;
case 107:
//#line 176 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó un OUT");}
break;
case 109:
//#line 180 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '(' en el OUT de cadena");}
break;
case 110:
//#line 181 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta ')' en el OUT de cadena");}
break;
case 111:
//#line 182 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " faltan '(' y ')' en el OUT de cadena");}
break;
case 112:
//#line 183 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " entre '(' y ')' no hay una cadena");}
break;
case 113:
//#line 184 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta cadena entre los parentesis en el OUT");}
break;
case 114:
//#line 187 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una asignacion");}
break;
case 116:
//#line 191 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignacion");}
break;
case 117:
//#line 192 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el ID en la asignacion");}
break;
case 118:
//#line 193 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión para ser asignada al ID");}
break;
case 119:
//#line 197 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion a una funcion");}
break;
case 121:
//#line 201 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocacion mal declarada, falta el identificador");}
break;
case 122:
//#line 202 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocacion mal declarada, falta el '('");}
break;
case 123:
//#line 203 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocacion mal declarada, faltan los parametros");}
break;
case 124:
//#line 204 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� una invocacion mal declarada, falta el ')'");}
break;
case 128:
//#line 212 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta el id de la izquierda");}
break;
case 129:
//#line 213 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ':' entre los id");}
break;
case 130:
//#line 214 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta ',' para separar los id");}
break;
case 131:
//#line 215 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detectaron parametros mal declarados, falta el id de la derecha");}
break;
//#line 1204 "Parser.java"
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
