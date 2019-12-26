package com.sample;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;

public class MvelCoercion {

    public static void main(String[] args) {
        testExpression("15 * Math.round( ((bgValue) / 85) * 100) / 100");
        testExpression("(15 * 49 / 100)");
        testExpression("Math.round( ((bgValue) / 85) * 100)");
        testExpression("((bgValue) / 85) * 100");
        testExpression("15 * Math.round( ((bgValue) / 85) * 100)");

    }

    public static void testExpression(String ex) {
        // Compile the expression.
        Serializable compiled = MVEL.compileExpression(ex);

        Map vars = new HashMap();
        vars.put("bgValue", new BigDecimal("42.0"));

        // Now we execute it.
        Object result = MVEL.executeExpression(compiled, vars);

        System.out.println();
        System.out.println(ex + " : ");
        System.out.println("  result = " + result);
        System.out.println("  result.getClass() = " + result.getClass());

    }

}
