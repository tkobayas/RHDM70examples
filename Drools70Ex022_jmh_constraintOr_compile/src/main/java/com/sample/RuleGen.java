package com.sample;


public class RuleGen {

    public static String generateRule(int numOfConstraintPerPattern, int numOfTotalConstraint) {
        
        int numOfRules = numOfTotalConstraint / numOfConstraintPerPattern;
        
        StringBuilder sb = new StringBuilder();
        sb.append("package com.sample;\n" + 
                "\n" + 
                "import com.sample.Message;\n" + 
                "\n");
        
        for (int i = 0; i < numOfRules; i++) {
            sb.append("rule R" + i + "\n" + 
                    "    when\n" + 
                    "        $m : Message(");
            
            for (int j = 0; j < numOfConstraintPerPattern; j++) {
                sb.append(" status == " + (i * numOfConstraintPerPattern + j) + " ");
                if (j < numOfConstraintPerPattern - 1) {
                    sb.append("||");
                }
            }
            
            sb.append(")\n    then\n        System.out.println($m);\nend\n\n");
        }
        
        return sb.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(RuleGen.generateRule(4, 4000));
    }
}
