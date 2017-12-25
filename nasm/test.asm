extern printf
global main

section .data
    a: dd 0
    b: dd 129
    msg1: db " > that ", 10, 0
section .text
main:
    mov rax, [a]
    mov rbx, [b]
    cmp rax, rbx
    jg S1
    ;b > a - else statement
    mov rsi, [b]
    mov rdi, [msg1]
    call printf
S1:
    lea rdi, [msg1]
    lea rsi, [a]
    call printf