package br.pact.consumer.tasks.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.consumer.junit.PactVerifications;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.pact.consumer.tasks.model.Task;
import br.pact.consumer.tasks.service.TasksConsumer;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TasksConsumerContractTest {

    @Rule
   public PactProviderRule mockProvider = new PactProviderRule("Tasks",this);  // Service side
    // O PactProvider cria uma api dinamica, essa api fica no ar somente durante o teste
    //public PactProviderRule mockProvider = new PactProviderRule("Tasks","localhost",8080,this);  // Service side

    // where Tasks means the API that I will access
    // Target is how gonna recieve this provider, in this case, is the won class PactProviderRule
    //At the end of test will be create a contract between BasicConsumer and Tasks

    @Pact(consumer = "BasicConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        return builder
                .given("There is a task with id = 1") //where inform the preconditions to the test
                .uponReceiving("Retrieve Task #1")
                    .path("/todo/1")
                    .method("GET")
                .willRespondWith()
                    .status(200)
                    .body("{\"id\": 1,\"task\": \"Task from pact\",\"dueDate\": \"2020-01-01\"}")
                .toPact();
    }

    @Test
    @PactVerification
    public void test() throws IOException {
        //Arrange
        TasksConsumer consumer = new TasksConsumer(mockProvider.getUrl());
        System.out.println(mockProvider.getUrl());

        //Act
        Task task = consumer.getTask(1L);
        System.out.println(task);

        //Assert
        assertThat(task.getId(), is(1L));
        assertThat(task.getTask(),is("Task from pact"));
    }


}