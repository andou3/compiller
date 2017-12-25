extern printf
global main

SECTION .data

msg: db "the variable has value of %d",10,0 ;we use this to display the variable

SECTION .text

     ;extern printf                  ;tell nasm that we want to call printf in this asm
     ;global main                    ;make main available to operating system(os)
main:
     ;create the stack frame
     push ebp
     mov ebp, esp

     ;create local variables by reserving space on the stack
     sub esp, 0xFF  ;reserve space of 16 bytes-- maybe 4 integers(4bytes*8bit=32bits)



     mov eax, 8
     mov ecx, 2
     mul ecx
     mov DWORD [ebp - 4], eax ;store 15 into first variable 
     mov eax, 8
     mov ecx, 3
     mul ecx
     mov DWORD [ebp - 8], eax  ;255
     mov eax, [ebp - 4]
     mov ecx, [ebp - 8]
     add eax, ecx
     mov [ebp - 12], eax

     push DWORD [ebp-4]   ;push the value stored at ebp -4 onto stack
     push DWORD msg       ;push the address of msg onto the stack
     call printf          ;call the extern, c standard library

     push DWORD [ebp-8]   ;push the value 
     push DWORD msg       ;push the address of msg onto the stack
     call printf          ;call the extern, c standard library

     push DWORD [ebp-12]   ;push the value 
     push DWORD msg       ;push the address of msg onto the stack
     call printf          ;call the extern, c standard library

     ;destroy the stack frame
     mov esp, ebp
     pop ebp
     ret 