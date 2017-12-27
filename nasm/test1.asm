global main
extern scanf
extern printf
section .text
main:
push ebp
mov ebp, esp
sub esp, 0xFFFFF ; start stack

mov DWORD [ebp - 4], 2
mov DWORD [ebp - 8], 8
mov eax, [ebp - 4]
mov ecx, [ebp - 8]
mul ecx
mov DWORD [ebp - 12], eax

mov DWORD [ebp - 16], 3
mov DWORD [ebp - 20], 9
mov eax, [ebp - 16]
mov ecx, [ebp - 20]
mul ecx
mov DWORD [ebp - 24], eax

mov eax, [ebp - 12]
mov ecx, [ebp - 24]
add eax, ecx
mov DWORD [ebp - 28], eax

mov DWORD [ebp - 32], 2
mov eax, [ebp - 28]
mov ecx, [ebp - 32]
sub eax, ecx
mov DWORD [ebp - 36], eax

mov DWORD [ebp - 40], 3
mov DWORD [ebp - 44], 3
mov eax, [ebp - 40]
mov ecx, [ebp - 44]
mul ecx
mov DWORD [ebp - 48], eax

mov DWORD [ebp - 52], 3
mov eax, [ebp - 48]
mov ecx, [ebp - 52]
div ecx
mov DWORD [ebp - 56], eax

mov eax, [ebp - 36]
mov ecx, [ebp - 56]
add eax, ecx
mov DWORD [ebp - 60], eax
	
push DWORD [ebp - 60]
push msg
call printf

mov esp, ebp
pop ebp
ret

section .data
msg: db "%d",10,0


