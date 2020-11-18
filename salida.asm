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
_hola DB "hola", 0 
_distinto DB "distinto", 0 
_a@f1 DD ?
_b@f1 DD ?
_j@main DQ ?
_r@main DD ?
_c@f1 DQ ?
_var8 DD ?
_5 DD 5
_s@main DD ?
_2 DD 2

.code
start: 
FINIT 
MOV ECX, _5
MOV _r@main, ECX
MOV ECX, _5
MOV _s@main, ECX
MOV ECX, _r@main
MOV _a@f1, ECX
MOV EBX, offset _s@main
MOV _b@f1, EBX 
MOV EBX, offset _t@main
MOV _var8, EBX 
FILD _var8
FST _c@f1
CALL f1@main
MOV ECX, _s@main
CMP _r@main, ECX
JE Label14
invoke MessageBox, NULL, addr _hola, addr _hola, MB_OK 
JMP Label16
Label14: 
invoke MessageBox, NULL, addr _distinto, addr _distinto, MB_OK 
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