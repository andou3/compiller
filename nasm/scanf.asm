global main
extern scanf
extern printf

section .data
    format: db "%d"
    integer: dd 0
section .text
main:
    push ebp
    mov ebp, esp
    sub esp, 10
    
    push integer
    push format
    call scanf
    
    push DWORD [integer]
    push format
    call printf

    mov esp, ebp
    pop ebp
    ret
