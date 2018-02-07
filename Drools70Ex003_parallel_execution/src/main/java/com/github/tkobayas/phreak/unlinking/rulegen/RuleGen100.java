package com.github.tkobayas.phreak.unlinking.rulegen;

public class RuleGen100 {

    static final int RULE_NUM = 100;

    public static void main(String[] args) throws Exception {

        StringBuilder builder = new StringBuilder();

        builder.append("package com.github.tkobayas.phreak.unlinking\n\n");
        builder.append("import com.github.tkobayas.phreak.unlinking.model.*;\n\n");

        for (int i = 0; i < RULE_NUM; i++) {
            
            int suffixX = (i / 10) + 1;
            
            int suffix1 = (i % 10) + 1;
            int suffix2 = ((i + 1) % 10) + 1;
            int suffix3 = ((i + 2) % 10) + 1;
            int suffix4 = ((i + 3) % 10) + 1;
            int suffix5 = ((i + 4) % 10) + 1;

            builder.append("rule \"Rule" + i + "\"\n");
            builder.append("when\n");
            builder.append("    $f1 : MyFact1()\n");
            builder.append("    $f2 : MyFact2(id == $f1.id, value1 == \"hoge1\", value2 == $f1.value2, value3 == $f1.value3, value4 == $f1.value4, value5 == $f1.value5)\n");
            builder.append("    $f3 : MyFact3(id == $f1.id, value1 == \"hoge" + suffixX + "\", value2 == $f1.value2, value3 == $f1.value3, value4 == $f1.value4, value5 == $f1.value5)\n");
            builder.append("    $f4 : MyFact4(id == $f1.id, value1 == \"hoge" + suffix1 + "\", value2 == $f1.value2, value3 == $f1.value3, value4 == $f1.value4, value5 == $f1.value5)\n");
            builder.append("\n");
            builder.append("    $var0 : Long(this != 0) from accumulate($a0 : MyFact5(id == $f1.id, value1 == $f4.value" + suffix1 + "), count( $a0 ))\n");
            builder.append("then\n");
//            builder.append("System.out.println(kcontext.getRule());\n");
            builder.append("end\n");
            builder.append("\n");
        }

        System.out.println(builder.toString());
    }

}
