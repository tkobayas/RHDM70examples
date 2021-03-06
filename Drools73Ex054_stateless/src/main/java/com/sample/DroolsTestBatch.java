package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTestBatch {

    public static final void main(String[] args) {
        try {

            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            StatelessKieSession kSession = kContainer.newStatelessKieSession();

            Person john = new Person("john", 20);

            // Create BatchExecutionCommand
            // No need to add FireAllRulesCommand. It will be called at last by default
            final List<Command> cmds = new ArrayList<Command>();
            cmds.add(CommandFactory.newInsert(john, "john"));
            BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(cmds);

            final ExecutionResults batchResult = (ExecutionResults) kSession.execute(batchExecutionCommand);

            batchResult.getIdentifiers().stream().forEach(s -> System.out.println(batchResult.getValue(s)));

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
