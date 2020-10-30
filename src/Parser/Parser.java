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
   17,   17,   17,   30,   29,   29,   32,   32,   32,   32,
   31,   31,   31,   31,   18,   18,   33,   33,   33,   33,
   33,   19,   19,   34,   34,   34,   20,   20,   36,   36,
   36,   36,   35,   35,   35,   37,   37,   37,   37,
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
    3,    5,    1,    1,    4,    1,    3,    3,    3,    2,
    2,    3,    4,    4,    4,    1,    3,    3,    2,    4,
    3,    3,    1,    2,    2,    2,    4,    1,    3,    3,
    3,    3,    3,    5,    1,    2,    2,    4,    3,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   43,   44,    0,    0,    0,    0,    0,
    0,    1,    0,    0,    4,    9,   10,    0,    0,   13,
   21,    0,    0,    0,    0,    0,   50,   57,    0,   93,
   96,  106,  113,  118,    0,   80,   79,    0,    0,    0,
    0,    0,    0,    0,   78,    0,  125,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    0,    0,
    0,    0,    5,    8,   17,    0,   18,   15,   12,   51,
   45,   52,   46,   53,   47,   54,   48,   55,   49,  101,
    0,    0,  127,    0,    0,  121,    0,    0,   81,  126,
    0,    0,    0,    0,    0,    0,  120,   98,    0,   97,
   86,   87,   85,   88,   83,   84,    0,  107,    0,    0,
  111,    0,    0,    0,    0,    0,    0,    0,    0,   35,
    0,    3,  119,   14,   19,   11,    0,  102,   91,    0,
  129,  123,    0,  117,    0,    0,   76,   77,    0,    0,
   95,    0,  110,  105,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,   16,  103,    0,   75,
  128,    0,    0,    0,    0,    0,    0,   42,    0,    0,
    0,    0,    0,    0,   38,    0,   92,  124,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   39,   40,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   89,   90,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   36,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   24,   25,   27,   30,
    0,   29,   28,   26,   23,   22,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   58,   60,   63,   65,   67,   69,    0,   68,   66,   64,
   62,   61,   59,   56,
};
final static short yydgoto[] = {                         11,
   12,   64,   14,   15,   16,   17,   18,   66,   19,   20,
   67,  118,   21,  119,  120,   22,   23,   24,   25,   26,
   27,   50,  211,   28,   51,  107,   44,   45,   29,   82,
   30,   31,   32,   33,   46,   34,   47,
};
final static short yysindex[] = {                       221,
  106,  167,  -37,    0,    0,  -28,   -1,  691,  -52,  288,
    0,    0,    0,  247,    0,    0,    0, -226,  -48,    0,
    0,  -46,   30,   54,  109,  112,    0,    0,  129,    0,
    0,    0,    0,    0,  -51,    0,    0,    6,   61,  288,
  104, -191,   35,   -2,    0,  -39,    0,    0,  537,   31,
  726,   40,   63,   27,  -11,  -16, -202,    0,  320,  -51,
   26,   35,    0,    0,    0,   42,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -231, -176,    0, -215,  288,    0,   32,   35,    0,    0,
  104,  104,  104,  104,   41, -165,    0,    0,   62,    0,
    0,    0,    0,    0,    0,    0,  288,    0,   68,   69,
    0, -135,  -18, -134, -219,   64, -139,   84,   -6,    0,
   85,    0,    0,    0,    0,    0, -130,    0,    0,  139,
    0,    0,   34,    0,   -2,   -2,    0,    0, -128,   75,
    0,   35,    0,    0,   83,   86,  -49,   88, -104, -114,
  -25,    0, -112, -202,  147, -111,    0,    0, -106,    0,
    0,  -94,  288,  288,  449,  288,  288,    0,  105,  111,
  -57,  116,  169, -202,    0,  119,    0,    0,  123,  125,
  480,  126,  127,  128,  -93,  -88,   80,  -70,  -69, -202,
    0,    0,  -66, -214, -214, -214,  -59, -214, -214, -214,
   70,   73,   74, -115,   76,   78,  188,   79,    0,    0,
  -47,  163,  173, -211,  178,  183,  186,  189,  691,  691,
  691,  353,  691,  691,  691, -202,  691,  302,  405,  407,
  408,    8,  409,  410,  413,  415,  372,  382,  397,  412,
  422,  440,  459,  470,    0,  488,  264,  330,  334,  337,
  -15,  338,  341,  342,  347,  348,    0,    0,    0,    0,
  503,    0,    0,    0,    0,    0,  691,  691,  691,  691,
  691,  513,  691,  691,  691,  691,  691,    0,  532,  547,
  565,  594,  623,  654,  673,  702,  724,  735,  753,  771,
    0,    0,    0,    0,    0,    0,  789,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  493,    0,    0,    0,    0,  122,
    0,    0,  124,  -26,    0,    0,    0,    0,    0,  158,
    0,  136,    0,    0,    0,    0,    0,    0,   28,    0,
    0,  145,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  192,    0,    0,    0,    0,    0,  155,  336,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  198,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  343,
    0,    0,    0,    0,    0,    0,    0,    0,  -19,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   55,  114,    0,    0,    0,    0,
    0,   71,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -14,    0,    0,    0,  346,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -12,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -9,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  360,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  375,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -8,    1,   82,    0,    0,    0,  859,    0,    0,    0,
    0,   87,    0,  -34,    0,    0,    0,    0,    0,    0,
    0,  -29, -140,    0,   43,    0,    2,   13,    0,    0,
    0,    0,    0,    0,    9,    0,    0,
};
final static int YYTABLESIZE=1085;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        214,
   13,   97,   53,  188,   96,   42,   84,  223,   58,  166,
   69,   55,   71,   72,   72,  171,   72,   61,   72,   99,
   81,   32,    2,  116,  128,  272,   37,    6,   33,   13,
   65,   34,   72,   72,   72,   72,  129,  154,   57,   93,
  131,  132,  147,   43,   94,   85,  231,   87,  252,  114,
    4,    5,   62,   89,  212,  213,  215,  216,  217,  218,
  209,  210,    7,  209,  210,   90,  123,    4,    5,   96,
  115,  100,  134,  232,  160,   96,   91,   91,   92,   92,
  108,    2,   88,    7,  155,  127,    6,  112,   73,   59,
  130,  140,  135,  136,   73,   73,   72,   73,  139,   73,
  126,   86,  141,  111,  150,  137,  138,  271,  143,  144,
   71,   71,   75,   73,   73,   73,   73,  152,   42,  173,
  175,  159,  145,  148,  153,  156,  157,  133,  161,   71,
   13,   71,  162,  179,  180,  182,  183,  184,  191,  192,
  204,  163,  222,  121,  164,   39,  167,    7,   41,  142,
   41,  197,  168,   74,   74,  207,   74,  169,   74,  172,
  176,  177,  178,   42,  201,  185,   40,   77,    9,  202,
   79,  186,   74,   74,   74,   74,  189,   73,    9,  193,
  116,  194,  114,  195,  198,  199,  200,  205,  206,   10,
  174,  208,  219,   71,  109,  220,  221,  100,  224,   10,
  225,  227,  151,  115,   60,   83,   49,   68,  165,   70,
  228,   41,  190,  122,  187,  209,  210,   95,  100,   58,
   58,   58,   58,   58,   58,   58,   52,   58,   54,   72,
   72,  226,   72,   72,   72,   72,   74,   99,   72,  146,
   58,   72,   72,   72,   72,  113,  170,   72,   72,   72,
   72,    8,   32,    4,    5,   56,  115,   37,   99,   33,
    9,    8,   34,    4,    5,  251,  115,   58,   58,   58,
   58,   58,   58,   58,   58,   58,   58,   58,    2,    7,
  100,   10,   95,    6,   58,   72,    9,    7,   95,    2,
    2,    7,    7,    7,    6,    6,    7,  124,  125,    7,
  237,  238,  239,  241,  242,  243,  244,   10,  246,   74,
   73,   73,  245,   73,   73,   73,   73,   60,  109,   73,
   99,  261,   73,   73,   73,   73,  110,   71,   73,   73,
   73,   73,   41,    4,    5,   71,  115,  203,   71,   71,
   71,   71,  247,    8,   71,   71,   71,   71,  279,  280,
  281,  282,  283,  285,  286,  287,  288,  289,  290,    9,
   48,   36,   35,   36,   76,  297,   37,   78,   37,   74,
   74,   63,   74,   74,   74,   74,   38,  116,   74,  114,
   10,   74,   74,   74,   74,    1,  267,   74,   74,   74,
   74,  109,    9,    2,  112,    1,   80,    3,    4,    5,
  115,  108,    6,    2,  104,    7,  158,    3,    4,    5,
  122,    9,    6,   10,  100,    7,    4,    5,   31,  115,
  229,    9,  100,   48,   36,  100,  100,  100,  100,   37,
  230,  100,   10,   70,  100,  233,    9,   38,    4,    5,
  234,  115,   10,  235,  122,  248,  236,  249,  250,  253,
  254,    9,  268,  255,   99,  256,  269,   10,   94,  270,
  273,    9,   99,  274,  275,   99,   99,   99,   99,  276,
  277,   99,   10,    0,   99,  240,    0,    1,    0,    9,
    0,    0,   10,    0,    0,    2,    0,    0,    0,    3,
    4,    5,    0,   41,    6,    0,  257,    7,    9,    0,
   10,    0,    0,    1,    0,    0,  258,  181,    0,    9,
    0,    2,    0,    0,    0,    3,    4,    5,    0,   10,
    6,  259,    0,    7,   41,    0,    0,    9,    0,    0,
   10,    0,    0,    0,   82,   82,  260,   82,  196,   82,
    0,    0,    9,    0,   48,   36,  262,    0,   10,    0,
   37,   82,    9,    0,    0,    0,    0,    0,   38,    0,
    0,    0,    0,   10,  263,    0,    0,    0,    0,    0,
    0,    9,    0,   10,    0,    0,    1,   98,    0,    0,
    0,   41,    0,  264,    2,    0,    9,    0,    3,    4,
    5,  112,   10,    6,  265,    0,    7,    0,  108,    0,
    0,  104,    0,    0,    9,    0,    0,   10,    0,    1,
    0,    0,  266,    0,    0,   31,    0,    2,    0,    0,
    0,    3,    4,    5,    0,   10,    6,  278,    1,    7,
   70,    0,    0,    9,    0,  284,    2,    0,    1,    0,
    3,    4,    5,    0,    0,    6,    2,    0,    7,    0,
    3,    4,    5,    1,   10,    6,  291,    0,    7,    0,
    0,    2,    9,    0,    0,    3,    4,    5,    1,    0,
    6,  292,    0,    7,    0,    0,    2,    0,    1,    0,
    3,    4,    5,   10,    0,    6,    2,    0,    7,  293,
    3,    4,    5,    9,    0,    6,    1,    0,    7,    0,
    0,    0,    0,    0,    2,   48,   36,    0,    3,    4,
    5,   37,    9,    6,   10,    1,    7,    0,  294,   38,
    0,    0,    0,    2,    0,    0,    1,    3,    4,    5,
    9,    0,    6,   10,    2,    7,   48,   36,    3,    4,
    5,    9,   37,    6,    1,    0,    7,  295,   82,    0,
   38,   10,    2,    0,    0,    0,    3,    4,    5,    1,
    0,    6,   10,    9,    7,    0,    0,    2,   91,    1,
   92,    3,    4,    5,    9,    0,    6,    2,  296,    7,
    0,    3,    4,    5,   10,  105,    6,  106,    1,    7,
    0,    0,    9,   48,   36,   10,    2,  298,    0,   37,
    3,    4,    5,    1,    0,    6,    0,   38,    7,    0,
    9,    2,    0,   10,    0,    3,    4,    5,    0,    0,
    6,    1,    0,    7,    0,    0,  299,    0,    9,    2,
    0,   10,    0,    3,    4,    5,    0,    0,    6,    0,
    0,    7,    0,    0,    0,    0,    0,    0,  300,   10,
    1,    0,    0,    0,    0,    0,    0,    0,    2,  301,
    0,    0,    3,    4,    5,    0,    0,    6,    0,    0,
    7,    0,    0,    0,    0,    0,    0,  302,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    2,    0,    0,
    0,    3,    4,    5,    0,  303,    6,    0,    0,    7,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,    0,  304,  117,  117,    0,    0,    2,    0,
    0,    0,    3,    4,    5,    0,    0,    6,    0,    1,
    7,    0,    0,    0,    0,    0,    0,    2,    0,    0,
    0,    3,    4,    5,    0,    0,    6,    1,    0,    7,
    0,    0,    0,    0,    0,    2,    0,    0,    1,    3,
    4,    5,    0,    0,    6,    0,    2,    7,    0,    0,
    3,    4,    5,  149,  117,    6,    0,  117,    7,    0,
    1,    0,    0,    0,  101,  102,  103,  104,    2,    0,
    0,    1,    3,    4,    5,    0,    0,    6,    0,    2,
    7,    0,    0,    3,    4,    5,    0,    0,    6,    1,
    0,    7,  117,  117,    0,    0,    0,    2,    0,    0,
    0,    3,    4,    5,    0,    0,    6,    1,    0,    7,
    0,  117,  117,    0,    0,    2,    0,    0,    0,    3,
    4,    5,    0,    0,    6,    1,    0,    7,  117,    0,
    0,    0,    0,    2,    0,    0,    0,    3,    4,    5,
    0,    0,    6,    0,    0,    7,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  117,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,   41,   40,   61,   44,   58,   58,  123,    8,   59,
   59,   40,   59,   40,   41,   41,   43,    9,   45,   49,
   29,   41,    0,   40,  256,   41,   41,    0,   41,   29,
  257,   41,   59,   60,   61,   62,  268,   44,   40,   42,
  256,  257,   61,    1,   47,   40,  258,   39,   41,   61,
  270,  271,   10,   41,  195,  196,  197,  198,  199,  200,
  275,  276,   40,  275,  276,  257,   41,  270,  271,   44,
  273,   41,   41,  214,   41,   44,   43,   43,   45,   45,
   41,   59,   40,   61,  119,   44,   59,   61,   59,    8,
  267,  257,   91,   92,   40,   41,  123,   43,   58,   45,
   59,   41,   41,   41,   41,   93,   94,  123,   41,   41,
   40,   41,   59,   59,   60,   61,   62,  257,   58,  154,
  155,  130,  258,  258,   41,   41,  257,   85,  257,   59,
  130,   61,   58,  163,  164,  165,  166,  167,  173,  174,
   61,   59,  258,   57,   59,   40,   59,  125,   45,  107,
   45,  181,  257,   40,   41,  190,   43,  272,   45,  272,
  272,  268,  257,   58,  258,   61,   61,   59,   40,  258,
   59,   61,   59,   60,   61,   62,   61,  123,   40,   61,
   59,   59,   59,   59,   59,   59,   59,  258,  258,   61,
   44,  258,  123,  123,   59,  123,  123,   40,  123,   61,
  123,  123,  116,   59,  257,  257,   40,  256,  258,  256,
  258,   45,   44,   59,  272,  275,  276,  257,   61,  219,
  220,  221,  222,  223,  224,  225,  264,  227,  257,  256,
  257,   44,  259,  260,  261,  262,  123,   40,  265,  258,
  240,  268,  269,  270,  271,  257,  272,  274,  275,  276,
  277,  123,  272,  270,  271,  257,  273,  272,   61,  272,
   40,  123,  272,  270,  271,  258,  273,  267,  268,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  256,  257,
  123,   61,  257,  256,  284,  256,   40,  265,  257,  267,
  268,  269,  270,  271,  267,  268,  274,  256,  257,  277,
  219,  220,  221,  222,  223,  224,  225,   61,  227,  256,
  256,  257,  226,  259,  260,  261,  262,  257,  256,  265,
  123,  240,  268,  269,  270,  271,  264,  257,  274,  275,
  276,  277,   45,  270,  271,  265,  273,  258,  268,  269,
  270,  271,   41,  123,  274,  275,  276,  277,  267,  268,
  269,  270,  271,  272,  273,  274,  275,  276,  277,   40,
  257,  258,  257,  258,  256,  284,  263,  256,  263,  256,
  257,  125,  259,  260,  261,  262,  271,  256,  265,  256,
   61,  268,  269,  270,  271,  257,  123,  274,  275,  276,
  277,  256,   40,  265,   59,  257,  268,  269,  270,  271,
  256,   59,  274,  265,   59,  277,  268,  269,  270,  271,
  256,   40,  274,   61,  257,  277,  270,  271,   59,  273,
  258,   40,  265,  257,  258,  268,  269,  270,  271,  263,
  258,  274,   61,   59,  277,  258,   40,  271,  270,  271,
  258,  273,   61,  258,  125,   41,  258,   41,   41,   41,
   41,   40,  123,   41,  257,   41,  123,   61,  267,  123,
  123,   40,  265,  123,  123,  268,  269,  270,  271,  123,
  123,  274,   61,   -1,  277,  123,   -1,  257,   -1,   40,
   -1,   -1,   61,   -1,   -1,  265,   -1,   -1,   -1,  269,
  270,  271,   -1,   45,  274,   -1,  125,  277,   40,   -1,
   61,   -1,   -1,  257,   -1,   -1,  125,   59,   -1,   40,
   -1,  265,   -1,   -1,   -1,  269,  270,  271,   -1,   61,
  274,  125,   -1,  277,   45,   -1,   -1,   40,   -1,   -1,
   61,   -1,   -1,   -1,   42,   43,  125,   45,   59,   47,
   -1,   -1,   40,   -1,  257,  258,  125,   -1,   61,   -1,
  263,   59,   40,   -1,   -1,   -1,   -1,   -1,  271,   -1,
   -1,   -1,   -1,   61,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   40,   -1,   61,   -1,   -1,  257,   41,   -1,   -1,
   -1,   45,   -1,  125,  265,   -1,   40,   -1,  269,  270,
  271,  256,   61,  274,  125,   -1,  277,   -1,  256,   -1,
   -1,  256,   -1,   -1,   40,   -1,   -1,   61,   -1,  257,
   -1,   -1,  125,   -1,   -1,  256,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,   -1,   61,  274,  125,  257,  277,
  256,   -1,   -1,   40,   -1,  123,  265,   -1,  257,   -1,
  269,  270,  271,   -1,   -1,  274,  265,   -1,  277,   -1,
  269,  270,  271,  257,   61,  274,  125,   -1,  277,   -1,
   -1,  265,   40,   -1,   -1,  269,  270,  271,  257,   -1,
  274,  125,   -1,  277,   -1,   -1,  265,   -1,  257,   -1,
  269,  270,  271,   61,   -1,  274,  265,   -1,  277,  125,
  269,  270,  271,   40,   -1,  274,  257,   -1,  277,   -1,
   -1,   -1,   -1,   -1,  265,  257,  258,   -1,  269,  270,
  271,  263,   40,  274,   61,  257,  277,   -1,  125,  271,
   -1,   -1,   -1,  265,   -1,   -1,  257,  269,  270,  271,
   40,   -1,  274,   61,  265,  277,  257,  258,  269,  270,
  271,   40,  263,  274,  257,   -1,  277,  125,  256,   -1,
  271,   61,  265,   -1,   -1,   -1,  269,  270,  271,  257,
   -1,  274,   61,   40,  277,   -1,   -1,  265,   43,  257,
   45,  269,  270,  271,   40,   -1,  274,  265,  125,  277,
   -1,  269,  270,  271,   61,   60,  274,   62,  257,  277,
   -1,   -1,   40,  257,  258,   61,  265,  125,   -1,  263,
  269,  270,  271,  257,   -1,  274,   -1,  271,  277,   -1,
   40,  265,   -1,   61,   -1,  269,  270,  271,   -1,   -1,
  274,  257,   -1,  277,   -1,   -1,  125,   -1,   40,  265,
   -1,   61,   -1,  269,  270,  271,   -1,   -1,  274,   -1,
   -1,  277,   -1,   -1,   -1,   -1,   -1,   -1,  125,   61,
  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,  125,
   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,   -1,
  277,   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,   -1,  125,  274,   -1,   -1,  277,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,   -1,   -1,  125,   56,   57,   -1,   -1,  265,   -1,
   -1,   -1,  269,  270,  271,   -1,   -1,  274,   -1,  257,
  277,   -1,   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,   -1,   -1,  274,  257,   -1,  277,
   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,  257,  269,
  270,  271,   -1,   -1,  274,   -1,  265,  277,   -1,   -1,
  269,  270,  271,  115,  116,  274,   -1,  119,  277,   -1,
  257,   -1,   -1,   -1,  259,  260,  261,  262,  265,   -1,
   -1,  257,  269,  270,  271,   -1,   -1,  274,   -1,  265,
  277,   -1,   -1,  269,  270,  271,   -1,   -1,  274,  257,
   -1,  277,  154,  155,   -1,   -1,   -1,  265,   -1,   -1,
   -1,  269,  270,  271,   -1,   -1,  274,  257,   -1,  277,
   -1,  173,  174,   -1,   -1,  265,   -1,   -1,   -1,  269,
  270,  271,   -1,   -1,  274,  257,   -1,  277,  190,   -1,
   -1,   -1,   -1,  265,   -1,   -1,   -1,  269,  270,  271,
   -1,   -1,  274,   -1,   -1,  277,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  226,
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
"seleccion : if_condicion bloque END_IF",
"seleccion : if_condicion bloque_else ELSE bloque END_IF",
"seleccion : error_if",
"bloque_else : bloque",
"if_condicion : IF '(' condicion ')'",
"if_condicion : error_if_condicion",
"error_if_condicion : IF condicion ')'",
"error_if_condicion : IF '(' ')'",
"error_if_condicion : IF '(' condicion",
"error_if_condicion : IF condicion",
"error_if : if_condicion END_IF",
"error_if : if_condicion bloque error",
"error_if : if_condicion bloque_else ELSE END_IF",
"error_if : if_condicion bloque_else ELSE bloque",
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

//#line 342 "gramatica.y"

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
//#line 251 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyó una sentencia IF con ELSE");
	  					adminTerceto.desapilar();}
break;
case 94:
//#line 256 "gramatica.y"
{Terceto t = new Terceto("BI", null, null);
                     adminTerceto.agregarTerceto(t);
                     adminTerceto.desapilar();
                     adminTerceto.apilar(t.getNumero());
                     }
break;
case 95:
//#line 263 "gramatica.y"
{System.out.println(" se leyó una sentencia IF" + val_peek(1).sval);
				if(val_peek(1).sval != null){
				Terceto t = new Terceto("BF", val_peek(1).sval, null);
				adminTerceto.agregarTerceto(t);
				adminTerceto.apilar(t.getNumero());
				}}
break;
case 97:
//#line 272 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta '('");}
break;
case 98:
//#line 273 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta la condicion");}
break;
case 99:
//#line 274 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta ')'");}
break;
case 100:
//#line 275 "gramatica.y"
{System.out.println("Error sint�ctico: Linea " + Lexico.linea + " se detect� un IF mal declarado, falta los parentesis");}
break;
case 101:
//#line 278 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias");}
break;
case 102:
//#line 279 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF o ELSE");}
break;
case 103:
//#line 281 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el bloque de sentencias del ELSE");}
break;
case 104:
//#line 282 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un IF mal declarado, falta el END_IF");}
break;
case 105:
//#line 285 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una sentencia OUT");}
break;
case 107:
//#line 289 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta '('");}
break;
case 108:
//#line 290 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, falta ')'");}
break;
case 109:
//#line 291 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + "  se detectó un OUT mal declarado, faltan '(' y ')'");}
break;
case 110:
//#line 292 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, entre los paréntesis debe ir una cadena");}
break;
case 111:
//#line 293 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó un OUT mal declarado, falta la cadena entre los parentésis en el OUT");}
break;
case 112:
//#line 296 "gramatica.y"
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
case 114:
//#line 315 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta '=' en la asignación");}
break;
case 115:
//#line 316 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta el identificador del lado izquierdo de la asignación");}
break;
case 116:
//#line 317 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " falta una expresión aritmética del lado derecho de la asignación");}
break;
case 117:
//#line 321 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se realizó una invocacion al procedimiento -> " + val_peek(3).sval );}
break;
case 119:
//#line 325 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el identificador");}
break;
case 120:
//#line 326 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el '('");}
break;
case 121:
//#line 327 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, faltan los parámetros");}
break;
case 122:
//#line 328 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectó una invocación mal declarada, falta el ')'");}
break;
case 123:
//#line 331 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 124:
//#line 332 "gramatica.y"
{System.out.println("[Parser | Linea " + Lexico.linea + "] se leyeron los parámetros -> " + val_peek(2).sval +" y " +val_peek(0).sval);}
break;
case 126:
//#line 336 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la izquierda");}
break;
case 127:
//#line 337 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta ':' entre los identificadores");}
break;
case 128:
//#line 338 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta la ',' que separa los identificadores");}
break;
case 129:
//#line 339 "gramatica.y"
{System.out.println("Error sintáctico: Linea " + Lexico.linea + " se detectaron parámetros mal declarados, falta el identificador de la derecha");}
break;
//#line 1351 "Parser.java"
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
