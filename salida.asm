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
_d@main@f1 DQ ?
_c@f1 DQ ?
_x@main DD ?
_b@f1 DD ?
_2 DD 2
_a@f1 DD ?

.code
start: 
FINIT 
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