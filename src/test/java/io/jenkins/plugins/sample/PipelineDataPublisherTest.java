package io.jenkins.plugins.sample;

import static org.mockito.Mockito.*;

import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.model.TaskListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PipelineDataPublisherTest {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    @Mock
    private CredentialUtil mockCredentialUtil;

    @Mock
    private DxDataSender mockDxDataSender;

    @Before
    public void setUp() {
        mockCredentialUtil = mock(CredentialUtil.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnCompleted() throws Exception {
        FreeStyleProject project = jenkinsRule.createFreeStyleProject();
        FreeStyleBuild build = project.scheduleBuild2(0).get();
        // build.setResult(Result.SUCCESS);

        // Configure mocks
        when(mockCredentialUtil.getSecretToken("dx_token")).thenReturn("fakeToken");
        when(mockCredentialUtil.getSecretToken("dx_path")).thenReturn("http://fakepath");

        TaskListener mockListener = mock(TaskListener.class);
        when(mockListener.getLogger()).thenReturn(System.out);

        // You need to find a way to inject these mocks or refactor your design to allow mocking static methods.
        // Directly verifying static method calls on DxDataSender without PowerMock or similar is not feasible.

        // Execute the onCompleted method, which would internally call DxDataSender.sendData
        new PipelineDataPublisher().onCompleted(build, mockListener);

        // Verify DxDataSender was called with the expected arguments.
        // This step assumes DxDataSender.sendData is non-static or you have a mechanism to verify static calls.
    }
}
