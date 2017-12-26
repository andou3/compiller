extern printf
global main

SECTION .data
    msg: db "var: %d", 10, 0
    i: db 10
SECTION .text
    
main:
    push ebp
    mov ebp, esp
    sub esp, 0xFFFFF

    mov eax, 8
    mov ecx, 2
    mul ecx
    mov DWORD [ebp - 4], eax
    mov eax, 8
    mov ecx, 3
    mul ecx
    mov DWORD [ebp - 8], eax
    mov eax, [ebp - 4]
    mov ecx, [ebp - 8]
    add eax, ecx
    mov DWORD [ebp - 12], eax
    mov eax, [ebp - 12]
    mov ecx, [i]
    add eax, ecx
    mov DWORD [ebp - 12], eax
    mov eax, [ebp - 12]
    mov [i], eax

    push DWORD [i]
    push msg
    call printf

    mov esp, ebp
    pop ebp
    ret
