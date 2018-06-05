package com.sample;

import java.util.concurrent.TimeUnit;

import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
@Warmup(iterations = 2)
@Measurement(iterations = 2)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DroolsBenchmark {

    protected KieBase kieBase;
    protected KieSession kieSession;
    
    private static String DRL ="package com.sample;\n" + 
            "\n" + 
            "import com.sample.Message;\n" + 
            "\n" + 
            "rule \"R1\"\n" + 
            "    when\n" + 
            "        m:Message( status == 0 )\n" + 
            "    then\n" + 
            "        System.out.println(m.getMessage());\n" + 
            "end\n";
    
    @Param({"10", "100", "1000"})
    private int factsNr;

    @Setup
    public void setupKieBase() {
        kieBase = new KieHelper().addContent(DRL, ResourceType.DRL).build();
    }

    @Setup(Level.Iteration)
    public void setup() {
        kieSession = kieBase.newKieSession();
    }

    @Benchmark
    public void test(final Blackhole eater) {

        for (int i = 0; i < factsNr; i++) {
            eater.consume(kieSession.insert(new Message("hi " + i, i)));
        }
        eater.consume(kieSession.fireAllRules());
    }

}