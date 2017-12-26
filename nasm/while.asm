global main
extern printf

section .data
    i: dd 10
    msg: db "test %d", 10, 0

section .text
main: 
	push ebp
	mov ebp, esp
	sub esp, 0xFFF
        ; while(a < b)  ++a;      
        ; способ 1
        mov     eax, 0
        mov     ebx, 10
loop_begin:        
        cmp     eax, ebx
        je     loop_end
	call output
        inc    eax
        jmp     loop_begin
loop_end:
	call output
output:
	push DWORD eax
        push msg               ; 1 - stdout   
        ;mov DWORD esi, [i]   
	call printf
	;ret

	mov esp, ebp
	pop ebp
	ret