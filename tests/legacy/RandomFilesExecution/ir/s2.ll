; MinLang LLVM IR
declare i32 @printf(i8*, ...)
@fmt = constant [4 x i8] c"%d\0A\00"

define i32 @main() {
  %ptr_a = alloca i32
  store i32 5, i32* %ptr_a
  ; WARNING: unknown expr → using 0
  call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @fmt, i32 0, i32 0), i32 0)
  %21 = load i32, i32* %ptr_a
  call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @fmt, i32 0, i32 0), i32 %21)
  %ptr_b = alloca i32
  store i32 10, i32* %ptr_b
  ; WARNING: unknown expr → using 0
  call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @fmt, i32 0, i32 0), i32 0)
  %31 = load i32, i32* %ptr_b
  call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @fmt, i32 0, i32 0), i32 %31)
  ; WARNING: unknown expr → using 0
  call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @fmt, i32 0, i32 0), i32 0)
  %41 = load i32, i32* %ptr_a
  %51 = load i32, i32* %ptr_b
  %61 = add i32 %41, %51
  call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @fmt, i32 0, i32 0), i32 %61)
  ret i32 0
}
