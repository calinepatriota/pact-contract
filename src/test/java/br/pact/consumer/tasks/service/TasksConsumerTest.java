package br.pact.consumer.tasks.service;

import br.pact.consumer.tasks.model.Task;
import br.pact.consumer.utils.RequestHelper;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TasksConsumerTest {
    private static final String INVALID_URL = "http://invalidURL.com";

    @InjectMocks
    private TasksConsumer consumer= new TasksConsumer(INVALID_URL);

    @Mock
    private RequestHelper helper;


    @Test
    public void shouldGetAnExistingTask() throws IOException {
        //Arranje
        Map<String, String> expectedTask = new HashMap<String, String>() ;
        expectedTask.put("id","1");
        expectedTask.put("task", "Task mocked!");
        expectedTask.put("dueDate", "2000-01-01");

        Mockito.when(helper.get(INVALID_URL +"/todo/1"))
                .thenReturn(expectedTask);
        //TasksConsumer consumer= new TasksConsumer("teste");
        //Act
       Task task= consumer.getTask(1L);
        //Assert
        Assert.assertNotNull(task);
        Assert.assertThat(task.getId(), CoreMatchers.is(1L));

    }
}
