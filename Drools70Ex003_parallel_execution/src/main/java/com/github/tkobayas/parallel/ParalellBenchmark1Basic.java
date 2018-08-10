package com.github.tkobayas.parallel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.conf.MultithreadEvaluationOption;
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
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.SingleShotTime)
@State(Scope.Thread)
@Warmup(iterations = 50)
@Measurement(iterations = 50)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ParalellBenchmark1Basic {
    
//    @Param({"10", "100", "1000", "2000"})
    @Param({"100", "1000"})
    private int a_numOfRules;
    
//    @Param({"10", "100", "1000", "2000"})
    @Param({"100", "1000"})
    private int b_numOfFacts;

    @Param({"false", "true"})
    private boolean c_multithread;
    
    KieBase kieBase;
    KieSession kieSession;

    @Setup
    public void setup() {
        final KieBaseConfiguration kieBaseConfiguration = KieServices.Factory.get().newKieBaseConfiguration();
        kieBaseConfiguration.setOption(c_multithread ? MultithreadEvaluationOption.YES : MultithreadEvaluationOption.NO);
        final KieHelper kieHelper = new KieHelper();
        String drl = generateDrl();
        kieHelper.addContent(drl, ResourceType.DRL);
        kieBase = kieHelper.build(kieBaseConfiguration);
    }

    private String generateDrl() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("package com.github.tkobayas.parallel\n\n");
        sb.append("global java.util.List list;\n\n");
        
        for (int i = 0; i < a_numOfRules; i++) {
            sb.append("rule R" + i + "\n"
                    + "when\n"
                    + "    $i : Integer( intValue == " + i + " )\n"
                    + "    String( toString == $i.toString )\n"
                    + "then\n"
                    + "    list.add($i);\n"
                    + "end\n");
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    @Setup(Level.Iteration)
    public void setupKieSession() {
        kieSession = kieBase.newKieSession();
        kieSession.setGlobal("list", Collections.synchronizedList(new ArrayList<Integer>()));
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        kieSession.dispose();
        kieSession = null;
    }

    @Benchmark
    public void test(final Blackhole eater) {

        for (int i = 0; i < b_numOfFacts; i++) {

            Integer integer = new Integer(i % a_numOfRules);
            String string = String.valueOf(integer);

            eater.consume(kieSession.insert(integer));
            eater.consume(kieSession.insert(string));
        }
        eater.consume(kieSession.fireAllRules());
    }

    public static void main(String[] args) throws RunnerException, IOException {
        Options opt = new OptionsBuilder().include(ParalellBenchmark1Basic.class.getSimpleName()).warmupIterations(0).measurementIterations(1).forks(1).build();

        new Runner(opt).run();
    }
}
