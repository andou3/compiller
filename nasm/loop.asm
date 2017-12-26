section .text
    global main
extern printf
extern scanf

main:
    mov ebx,0 ;number 10 to ebx
    push ebp
    mov ebp, esp
    sub esp, 0xFF

    loop:	
    push ebx ;first parameter
    push message ;second parameter
    call printf ;call inbuilt printf statement for outputting
    inc ebx ;decrement the value of ebx by 1
    cmp ebx, 10
    jne loop ;if not equal to zero go to loop
    
    mov esp, ebp
    pop ebp
    ret


section .data
message:	db	"Value = %d",10,0
scan: db "%d", 10, 0
i: dd 5
