.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib

.data
_c@main DW ?
_z@main DW ?
_a@main DW ?
_x@main DW ?
_y@main DW ?

.code
start:
MOV BX, _y@main
ADD BX, _z@main
CMP _a@main, BX
JNE Label4
MOV BX, _x@main
MOV _c@main, BX
Label4:
invoke ExitProcess, 0
end start