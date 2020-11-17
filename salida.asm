.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib

.data
_t@main DQ ?
_y@main@f1 DQ ?
_a@f1 DD ?
_b@f1 DD ?
_x@main@f1 DQ ?
_r@main DD ?
_c@f1 DQ ?
_s@main DD ?
_2 DD 2

.code
start:
FINIT
MOV EBX, _r@main
MOV _a@f1, EBX
MOV EBX, offset _s@main
MOV _b@f1, EBX
FLD _t@main
FST _c@f1
CALL f1@main
f1@main:
FLD _y@main@f1
FST _x@main@f1
RET
FINIT
invoke ExitProcess, 0
end start