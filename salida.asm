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
_OverflowSuma DB "Overflow en suma", 0 
_ResNegativoRestaUint DB "Resultado negativo en resta entero sin signo", 0 
_t@main DQ ?
_a@f1 DD ?
_b@f1 DD ?
_iguales DB "iguales", 0 
_j@main DQ ?
_r@main DD ?
_c@f1 DQ ?
_var8 DD ?
_5 DD 5
_distintos DB "distintos", 0 
_s@main DD ?
_var10_2bytes DW ?
_2 DD 2
_5_0 DQ 5.0

.code
start: 
FINIT 
FLD _5_0
FST _t@main
FLD _5_0
FST _j@main
MOV ECX, _r@main
MOV _a@f1, ECX
MOV EBX, offset _s@main
MOV _b@f1, EBX 
MOV EBX, offset _t@main
MOV _var8, EBX 
FILD _var8
FST _c@f1
CALL f1@main
FLD _t@main
FCOM _j@main
FSTSW _var10_2bytes
MOV AX , _var10_2bytes
SAHF
JE Label14
invoke MessageBox, NULL, addr _distintos, addr _distintos, MB_OK 
JMP Label16
Label14: 
invoke MessageBox, NULL, addr _iguales, addr _iguales, MB_OK 
Label16: 
f1@main: 
MOV EAX, _5
MUL _2
MOV ECX, EAX
MOV EBX, _b@f1
MOV dword ptr [EBX], ECX
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