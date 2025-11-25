import java.util.*;

public class IRBuilder {

    StringBuilder ir = new StringBuilder();
    int tempId = 1;

    // ★ FIXED HERE — use ptr names instead of %a, %b
    Map<String, String> varPtr = new HashMap<>();

    // generate unique temp ir register names: %t1, %t2, ...
    String temp() {
        tempId++;
        return "%" + tempId + 1;
    }

    // emit text
    void emit(String line) {
        ir.append(line).append("\n");
    }

    // ---------- MAIN ENTRY ----------
    public String gen(List<Stmt> statements) {
        emit("; MinLang LLVM IR");
        emit("declare i32 @printf(i8*, ...)");
        emit("@fmt = constant [4 x i8] c\"%d\\0A\\00\"");
        emit("");
        emit("define i32 @main() {");

        for (Stmt s : statements) {
            genStmt(s);
        }

        emit("  ret i32 0");
        emit("}");

        return ir.toString();
    }

    // ---------- STMT GEN ----------
    void genStmt(Stmt stmt) {

        if (stmt instanceof PrintStmt ps) {
            String v = genExpr(ps.expr);

            emit(
                "  call i32 (i8*, ...) @printf(" +
                "i8* getelementptr([4 x i8], [4 x i8]* @fmt, i32 0, i32 0), " +
                "i32 " + v +
                ")"
            );
        }

        else if (stmt instanceof LetStmt ls) {

            String exprVal = genExpr(ls.expr);

            // ★ FIXED HERE — variable pointer name becomes %ptr_name
            if (!varPtr.containsKey(ls.name)) {
                String ptr = "%ptr_" + ls.name;       // safe name
                varPtr.put(ls.name, ptr);
                emit("  " + ptr + " = alloca i32");
            }

            // store i32 value into pointer
            emit("  store i32 " + exprVal + ", i32* " + varPtr.get(ls.name));
        }

        else if (stmt instanceof InputStmt is) {
            emit("  ; input not implemented yet");
        }
    }

    // ---------- EXPR GEN ----------
    String genExpr(Expr e) {

        if (e instanceof NumberExpr ne) {
            return ne.value;
        }

        if (e instanceof VarExpr ve) {

            if (!varPtr.containsKey(ve.name)) {
                return "0 ; undeclared variable " + ve.name;
            }

            // ★ FIXED HERE — load from %ptr_name instead of %name
            String t = temp();
            emit("  " + t + " = load i32, i32* " + varPtr.get(ve.name));
            return t;
        }

        if (e instanceof BinaryExpr be) {
            String left = genExpr(be.left);
            String right = genExpr(be.right);

            String t = temp();

            switch (be.op.lex) {
                case "+":
                    emit("  " + t + " = add i32 " + left + ", " + right);
                    return t;
                case "-":
                    emit("  " + t + " = sub i32 " + left + ", " + right);
                    return t;
                case "*":
                    emit("  " + t + " = mul i32 " + left + ", " + right);
                    return t;
                case "/":
                    emit("  " + t + " = sdiv i32 " + left + ", " + right);
                    return t;
            }
        }

        emit("  ; WARNING: unknown expr → using 0");
        return "0";
    }
}

