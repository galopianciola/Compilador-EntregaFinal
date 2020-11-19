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
_igual_a_2 DB "igual_a_2", 0 
_t@main DQ ?
_igual_a_5 DB "igual_a_5", 0 
_g@main@f1 DD ?
_y@main@f1 DD ?
_a@f1 DD ?
_distinto_de_5 DB "distinto_de_5", 0 
_b@f1 DD ?
_x@main@f1 DD ?
_r@main DD ?
_c@f1 DQ ?
_5 DD 5
_3 DD 3
_s@main DD ?
_2 DD 2

.code
start: 
FINIT 
MOV ECX, _r@main
MOV _a@f1, ECX
MOV EBX, offset _s@main
MOV _b@f1, EBX 
FLD _t@main
FST _c@f1
CALL f1@main
MOV ECX, _2
CMP _s@main, ECX
JNE Label20
invoke MessageBox, NULL, addr _igual_a_2, addr _igual_a_2, MB_OK 
Label20: 
FINIT
invoke ExitProcess, 0 
f1@main: 
MOV ECX, _3
MOV _g@main@f1, ECX
MOV EBX, _b@f1
MOV ECX, _2
MOV dword ptr [EBX], ECX
MOV EBX, _b@f1
MOV ECX, dword ptr [EBX] 
ADD ECX, _g@main@f1
CMP ECX, _limiteSuperiorUint
JA LabelOverflowSuma
MOV _x@main@f1, ECX
MOV ECX, _5
CMP _x@main@f1, ECX
JNE Label9
invoke MessageBox, NULL, addr _igual_a_5, addr _igual_a_5, MB_OK 
JMP Label11
Label9: 
invoke MessageBox, NULL, addr _distinto_de_5, addr _distinto_de_5, MB_OK 
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