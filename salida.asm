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
_distinto DB "distinto", 0 
_y@main@f1 DQ ?
_2_0 DQ 2.0
_var8_2bytes DW ?
_b@f1 DD ?
_x@main@f1 DQ ?
_1_0 DQ 1.0
_v@main@f1 DQ ?
_var17 DD ?
_c@f1 DQ ?
_var7 DQ ?
_igual_a_c_ DB "igual a c ", 0 
_var5 DQ ?
_s@main DD ?
_2 DD 2

.code
start: 
FINIT 
MOV ECX, _2
MOV _s@main, ECX
FLD _2_0
FSTP _t@main
MOV EBX, offset _s@main
MOV _b@f1, EBX 
MOV EBX, offset _t@main
MOV _var17, EBX 
CALL f1@main
FINIT
invoke ExitProcess, 0 
f1@main: 
FLD _1_0
FSTP _v@main@f1
FLD _2_0
FSTP _y@main@f1
MOV EBX, _var17
FLD _y@main@f1
FDIV qword ptr [EBX] 
FSTP _var5
FLD _var5
FSTP _x@main@f1
FLD _y@main@f1
FMUL _v@main@f1
FSTP _var7
MOV EBX, _var17
FLD qword ptr [EBX] 
FCOMP _var7
FSTSW _var8_2bytes
MOV AX , _var8_2bytes
SAHF
JNE Label12
invoke MessageBox, NULL, addr _igual a c , addr _igual a c , MB_OK 
JMP Label14
Label12: 
invoke MessageBox, NULL, addr _distinto, addr _distinto, MB_OK 
Label14: 
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