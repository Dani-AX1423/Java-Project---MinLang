	.file	"s2.ll"
	.text
	.globl	main                            # -- Begin function main
	.p2align	4
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:
	pushq	%rbx
	.cfi_def_cfa_offset 16
	subq	$16, %rsp
	.cfi_def_cfa_offset 32
	.cfi_offset %rbx, -16
	movl	$5, 12(%rsp)
	movq	fmt@GOTPCREL(%rip), %rbx
	movq	%rbx, %rdi
	xorl	%esi, %esi
	xorl	%eax, %eax
	callq	printf@PLT
	movl	12(%rsp), %esi
	movq	%rbx, %rdi
	xorl	%eax, %eax
	callq	printf@PLT
	movl	$10, 8(%rsp)
	movq	%rbx, %rdi
	xorl	%esi, %esi
	xorl	%eax, %eax
	callq	printf@PLT
	movl	8(%rsp), %esi
	movq	%rbx, %rdi
	xorl	%eax, %eax
	callq	printf@PLT
	movq	%rbx, %rdi
	xorl	%esi, %esi
	xorl	%eax, %eax
	callq	printf@PLT
	movl	12(%rsp), %esi
	addl	8(%rsp), %esi
	movq	%rbx, %rdi
	xorl	%eax, %eax
	callq	printf@PLT
	xorl	%eax, %eax
	addq	$16, %rsp
	.cfi_def_cfa_offset 16
	popq	%rbx
	.cfi_def_cfa_offset 8
	retq
.Lfunc_end0:
	.size	main, .Lfunc_end0-main
	.cfi_endproc
                                        # -- End function
	.type	fmt,@object                     # @fmt
	.section	.rodata,"a",@progbits
	.globl	fmt
fmt:
	.asciz	"%d\n"
	.size	fmt, 4

	.section	".note.GNU-stack","",@progbits
