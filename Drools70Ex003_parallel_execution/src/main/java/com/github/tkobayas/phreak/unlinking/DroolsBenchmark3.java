package com.github.tkobayas.phreak.unlinking;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.github.tkobayas.phreak.unlinking.model.MyFact1;
import com.github.tkobayas.phreak.unlinking.model.MyFact2;
import com.github.tkobayas.phreak.unlinking.model.MyFact3;
import com.github.tkobayas.phreak.unlinking.model.MyFact4;
import com.github.tkobayas.phreak.unlinking.model.MyFact5;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;
import org.kie.internal.conf.MultithreadEvaluationOption;
import org.kie.internal.utils.KieHelper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
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
@Warmup(iterations = 100000)
@Measurement(iterations = 10000)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DroolsBenchmark3 {

    @Param({"true", "false"})
    private boolean multithread;

    private static final String DRL = "accumulate-unlink-100.drl";

    KieBase kieBase;
    KieSession kieSession;

    @Setup
    public void setup() {
        final KieBaseConfiguration kieBaseConfiguration = KieServices.Factory.get().newKieBaseConfiguration();
        kieBaseConfiguration.setOption(multithread ? MultithreadEvaluationOption.YES : MultithreadEvaluationOption.NO);
        final KieHelper kieHelper = new KieHelper();
        kieHelper.addResource(KieServices.Factory.get().getResources().newClassPathResource(DRL));
        kieBase = kieHelper.build(kieBaseConfiguration);
    }

    @Setup(Level.Iteration)
    public void setupKieSession() {
        kieSession = kieBase.newKieSession();
    }

    @TearDown(Level.Iteration)
    public void tearDown() {
        kieSession.dispose();
        kieSession = null;
    }

    //@Benchmark
    public void test(final Blackhole eater) {
        
        for (int i = 0; i < 1000; i++) {
            MyFact1 fact1 = new MyFact1();
            fact1.setId("hoge" + i);
            fact1.setValue1("hoge1");
            fact1.setValue2("hoge2");
            fact1.setValue3("hoge3");
            fact1.setValue4("hoge4");
            fact1.setValue5("hoge5");

            MyFact2 fact2 = new MyFact2();
            fact2.setId("hoge" + i);
            fact2.setValue1("hoge1");
            fact2.setValue2("hoge2");
            fact2.setValue3("hoge3");
            fact2.setValue4("hoge4");
            fact2.setValue5("hoge5");

            MyFact3 fact3 = new MyFact3();
            fact3.setId("hoge" + i);
            fact3.setValue1("hoge1");
            fact3.setValue2("hoge2");
            fact3.setValue3("hoge3");
            fact3.setValue4("hoge4");
            fact3.setValue5("hoge5");

            MyFact4 fact4 = new MyFact4();
            fact4.setId("hoge" + i);
            fact4.setValue1("hoge1");
            fact4.setValue2("hoge2");
            fact4.setValue3("hoge3");
            fact4.setValue4("hoge4");
            fact4.setValue5("hoge5");

            MyFact5 fact5 = new MyFact5();
            fact5.setId("hoge" + i);
            fact5.setValue1("hoge1");
            fact5.setValue2("hoge2");
            fact5.setValue3("hoge3");
            fact5.setValue4("hoge4");
            fact5.setValue5("hoge5");

            eater.consume(kieSession.insert(fact1));
            eater.consume(kieSession.insert(fact2));
            eater.consume(kieSession.insert(fact3));
            eater.consume(kieSession.insert(fact4));
            eater.consume(kieSession.insert(fact5));

            eater.consume(kieSession.fireAllRules());
//            int result = kieSession.fireAllRules();
//            System.out.println(result);
        }
    }

    public static void main(String[] args) throws RunnerException, IOException {
        Options opt = new OptionsBuilder().include(DroolsBenchmark3.class.getSimpleName()).warmupIterations(0).measurementIterations(1).forks(1).build();

        new Runner(opt).run();
    }
}
