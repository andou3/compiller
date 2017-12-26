extern printf
main:
    mov rax, [a]
    mov rbx, [b]
    cmp rax, rbx
    jg S1
    mov rsi, [b]
    mov rdi, fmt
    mov rdx, [a]
    xor rax, rax
    call printf
S1:
    mov rsi, [a]
    mov rdi, fmt
    mov rdx, [b]
    xor rax, rax
    call printf