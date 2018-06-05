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
@Fork(value = 2, jvmArgsAppend = {"-Xms2G", "-Xmx2G"})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DroolsBenchmark {

    protected KieBase kieBase;
    protected KieSession kieSession;
    
    private String drl;
    
    @Param({"4000"})
    private int totalConstraint;
    
    @Param({"1", "4"})
    private int constraintPerPettern;

    @Setup
    public void setupKieBase() {
        
        kieBase = new KieHelper().addContent(RuleGen.generateRule(4, 1000), ResourceType.DRL).build();
    }

    @Setup(Level.Iteration)
    public void setup() {
        drl = RuleGen.generateRule(constraintPerPettern, totalConstraint);
        kieBase = new KieHelper().addContent(drl, ResourceType.DRL).build();
        kieSession = kieBase.newKieSession();
    }

    @Benchmark
    public void test(final Blackhole eater) {
        
        for (int i = 0; i < totalConstraint; i = i + 5) {
            eater.consume(kieSession.insert(new Message("hi " + i, i)));
        }
        int fired = kieSession.fireAllRules();
        eater.consume(fired);
        
        System.out.println("fired = " + fired );
    }

}