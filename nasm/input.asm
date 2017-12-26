input: 
        mov     rax, 0              ; 0 - read
        mov     rdi, 0              ; 0 - stdin
        mov     rsi, buffer
        mov     rdx, buf_size
        syscall
        ret