extern printf
global main
global C2

section .data
    a: dd 0
    b: dd 129
    if: db "if", 10, 0
    else: db "else", 10, 0
section .text
main:
	push ebp
	mov ebp, esp
	sub esp, 10

	mov eax, [a]
	cmp eax, [b]
	jnge S1

	;else
	push else
	call printf
	jmp done

	;if
	S1:
	push if
	call printf
	jmp done	
	
	done:

	mov esp, ebp
	pop ebp
	ret

