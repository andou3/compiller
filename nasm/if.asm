extern printf
global main
global C2

section .data
    a: dd 0
    b: dd 129
    fmt: db "%d > than %d", 10, 0
    msg: db "S2 called!", 10, 0
section .text
main:
    mov rax, [a]
    mov rbx, [b]
    cmp rax, rbx
    jg S1
    ;b > a - else statement
    mov rsi, [b]
    mov rdi, fmt
    mov rdx, [a]
    xor rax, rax
    call printf
    call S2
S1:
    mov rsi, [a]
    mov rdi, fmt
    mov rdx, [b]
    xor rax, rax
    call printf
    call S2

S2:
    mov     rax, 1                  ; system call 1 is write
    mov     rdi, 1                  ; file handle 1 is stdout
    mov     rsi, msg           ; address of string to output
    mov     rdx, 12                 ; number of bytes
    syscall                         ; invoke operating system to do the write

    mov     eax, 60                 ; system call 60 is exit
    xor     rdi, rdi                ; exit code 0
    syscall                         ; invoke operating system to exit