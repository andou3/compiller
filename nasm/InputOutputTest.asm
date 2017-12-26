section .data

section .text
global  output, input, _start

_start:
    mov     rsi, buffer     ; Хитрый трюк(64 битный костыль) - исправь на стек у себя
    mov     rdx, 20         ; Заполнил нужные регистры перед вызовом - типа передал аргументы
    call    input           ; !!!read читает ascii(подставь нужную кодировку) коды!!!

    mov     rsi, buffer     ; То же самое,   
    mov     rdx, 20         ; что и в инпуте   
    call    output
    
    mov rax, 60             ; exit call number
    mov rdi, 0              ; return code
    syscall

output:
        mov     rax, 1               ; 1 - write   
        mov     rdi, 1               ; 1 - stdout   
        ;mov     rsi, msg            ; лучше раскоментить и передавать аргументы через стек в легаси моде(32 битный режим)
        ;mov     rdx, ln                
        syscall                         
        ret                            

input: 
        mov     rax, 0              ; 0 - read
        mov     rdi, 0              ; 0 - stdin
        ;mov     rsi, buffer
        ;mov     rdx, 20
        syscall
        ret

section .bss                        ; секция, где можно хранить переменные, но одно но, они вроде как становяться глобальными от этого
                                    ; собственно си код их тут и хранит
buffer: resb 20                     ; buffer куда read читает с stdin