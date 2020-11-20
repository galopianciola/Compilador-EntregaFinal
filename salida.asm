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
_s@main DQ ?
_var5_2_2bytes DW ?
_var3_2_2bytes DW ?
_j@main DQ ?
_t@main DQ ?
_var1_2_2bytes DW ?
_var2 DQ ?
_var4_2_2bytes DW ?
_2_0 DQ 2.0
_var2_2_2bytes DW ?
_3_0 DQ 3.0

.code
start:
FINIT
FLD _2_0
FST _s@main
FLD _3_0
FST _t@main
FLD _s@main
FADD _t@main
FST _var2
FLD _var2
FCOM _limiteInferiorDoublePositivo
FSTSW _var1_2_2bytes
MOV AX , _var1_2_2bytes
SAHF
JA LabelLimiteSupPositivo
JBE LabelLimiteInfNegativo
LabelLimiteSupPositivo:
FLD _var2
FCOM _limiteSuperiorDoublePositivo
FSTSW _var2_2_2bytes
MOV AX , _var2_2_2bytes
SAHF
JB LabelNoOverflow
JAE LabelOverflowSuma
LabelLimiteInfNegativo:
FLD _var2
FCOM _limiteInferiorDoubleNegativo
FSTSW _var3_2_2bytes
MOV AX , _var3_2_2bytes
SAHF
JA LabelLimiteSupNegativo
JBE LabelOverflowSuma
LabelLimiteSupNegativo:
FLD _var2
FCOM _limiteSuperiorDoubleNegativo
FSTSW _var4_2_2bytes
MOV AX , _var4_2_2bytes
SAHF
JB LabelNoOverflow
JAE LabelCero
LabelCero:
FLD _var2
FCOM _limiteDoubleCero
FSTSW _var5_2_2bytes
MOV AX , _var5_2_2bytes
SAHF
JNE LabelOverflowSuma
LabelNoOverflow:
FLD _var2
FST _j@main
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