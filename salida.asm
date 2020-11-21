.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib

.data
_limiteSuperiorUint DD 65535
_limiteInferiorUint DD 0
_limiteInferiorDoublePositivo DQ -305.7749261414728
_limiteSuperiorDoublePositivo DQ 309.7976931348623
_limiteInferiorDoubleNegativo DQ 306.2023068651377
_limiteSuperiorDoubleNegativo DQ -310.2250738585072
_limiteDoubleCero DQ 0.0
_OverflowSuma DB "Overflow en suma", 0 
_ResNegativoRestaUint DB "Resultado negativo en resta entero sin signo", 0 
_t@main DQ ?
_distinto_2_0 DB "distinto_2_0", 0 
_distinto DB "distinto", 0 
_y@main@f1 DQ ?
_2_0 DQ 2.0
_igual DB "igual", 0 
_igual_2_0 DB "igual_2_0", 0 
_var5_2bytes DW ?
_var16_2bytes DW ?
_b@f1 DD ?
_x@main@f1 DQ ?
_1_0 DQ 1.0
_v@main@f1 DQ ?
_c@f1 DQ ?
_3_0 DQ 3.0
_var14 DD ?
_s@main DD ?
_2 DD 2
_var3 DQ ?

.code
start: 
FINIT 
MOV ECX, _2
MOV _s@main, ECX
FLD _2_0
FST _t@main
MOV EBX, offset _s@main
MOV _b@f1, EBX 
MOV EBX, offset _t@main
MOV _var14, EBX 
CALL f1@main
FLD _t@main
FCOM _3_0
FSTSW _var16_2bytes
MOV AX , _var16_2bytes
SAHF
JNE Label20
invoke MessageBox, NULL, addr _igual_2_0, addr _igual_2_0, MB_OK 
JMP Label22
Label20: 
invoke MessageBox, NULL, addr _distinto_2_0, addr _distinto_2_0, MB_OK 
Label22: 
FINIT
invoke ExitProcess, 0 
f1@main: 
MOV EBX, _var14
FLD _1_0
FMUL qword ptr [EBX] 
FST _var3
FLD _var3
FST _x@main@f1
FLD _x@main@f1
FCOM _2_0
FSTSW _var5_2bytes
MOV AX , _var5_2bytes
SAHF
JNE Label9
invoke MessageBox, NULL, addr _igual, addr _igual, MB_OK 
JMP Label11
Label9: 
invoke MessageBox, NULL, addr _distinto, addr _distinto, MB_OK 
Label11: 
RET 
FINIT
invoke ExitProcess, 0 
LabelRestaNegativa: 
invoke MessageBox, NULL, addr _ResNegativoRestaUint, addr _ResNegativoRestaUint, MB_OK 
FINIT
invoke ExitProcess, 0 
LabelOverflowSuma: 
invoke MessageBox, NULL, addr _OverflowSuma, addr _OverflowSuma, MB_OK 
FINIT
invoke ExitProcess, 0
end start