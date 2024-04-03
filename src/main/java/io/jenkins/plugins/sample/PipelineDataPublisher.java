package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import javax.annotation.Nonnull;
import jenkins.model.Jenkins;
import org.jenkinsci.plugins.workflow.job.WorkflowJob;
import hudson.model.Item;
import hudson.model.ItemGroup;
import java.util.UUID;
import java.util.logging.Logger;
import hudson.model.Fingerprint;
import java.util.Collection;

@Extension
public class PipelineDataPublisher extends RunListener<Run<?, ?>> {
    private static final Logger LOGGER = Logger.getLogger(PipelineListener.class.getName());

    public void writeOut(TaskListener listener, String msg) {
        LOGGER.info(msg);
        listener.getLogger().println(msg);
    }

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private void printAllPipelineNamesRecursive(ItemGroup<?> group, TaskListener listener) {
        for (Item item : group.getItems()) {
            if (item instanceof WorkflowJob) {
                // This is a pipeline
                writeOut(listener, "item name: " + item.getFullName());
            } else if (item instanceof ItemGroup) {
                // This is a folder or another group, dive deeper recursively
                printAllPipelineNamesRecursive((ItemGroup<?>) item, listener);
            }
        }
    }

    @Override
    public void onCompleted(Run<?, ?> run, @Nonnull TaskListener listener) {
        super.onCompleted(run, listener);

        Jenkins jenkins = Jenkins.getInstanceOrNull();
        if (jenkins == null) {
            listener.getLogger().println("Jenkins instance is not available.");
            return;
        }

        // Fetch Api Key
        CredentialUtil credentialManager = new CredentialUtil();
        String authToken = credentialManager.getSecretToken("dx_token");
        if (authToken == null) {
            listener.getLogger().println("Authentication token not found for key: dx_token");
            return;
        }
        String path = credentialManager.getSecretToken("dx_path");
        if (path == null) {
            listener.getLogger().println("Authentication token not found for key: dx_path");
            return;
        }

        // Create a unique ID and store it in the description field
        // run.setDescription(generateUUID());

        // prepare data
        String instanceId = jenkins.getLegacyInstanceId();
        String jobName = run.getParent().getFullName();
        String id = run.getId();
        String referenceId = "Jenkins/" + instanceId + "/" + jobName + "/" + id;
        Integer startTime = (int) (run.getStartTimeInMillis() / 1000);
        Integer duration = (int) (run.getDuration() / 1000);
        Integer finishTime = startTime + duration;
        Result result = run.getResult();
        String description = run.getDescription();
        String status;
        if (result != null) {
            status = result.toString().toLowerCase();
        } else {
            status = "unknown";
        }

        // Print extracted data
        writeOut(listener, "Sending run data to DX:");
        writeOut(listener, "pipeline_name: " + jobName);
        writeOut(listener, "pipeline_source: Jenkins");
        writeOut(listener, "reference_id: " + referenceId);
        writeOut(listener, "started_at: " + startTime);
        writeOut(listener, "finished_at: " + finishTime + "ms");
        writeOut(listener, "status: " + status);

        Collection<Fingerprint> fingerprints = run.getBuildFingerprints();
        writeOut(listener, "fp size: " + fingerprints.size());

        for (Fingerprint fingerprint : fingerprints) {
            String displayName = fingerprint.getDisplayName();
            String md5Checksum = fingerprint.getHashString();

            writeOut(listener, "fp display name: " + displayName);
            writeOut(listener, "fp hash: " + md5Checksum);
        }

        writeOut(listener, "trying to print all pipeline names:");
        printAllPipelineNamesRecursive(jenkins, listener);

        // DxDataSender.sendData(
        //         path + "/api/pipelineRuns.notify",
        //         "{" + "\"pipeline_name\": \""
        //                 + jobName + "\"," + "\"pipeline_source\": \"Jenkins\","
        //                 + "\"reference_id\": \""
        //                 + referenceId + "\"," + "\"id\": \""
        //                 + id + "\"," + "\"started_at\": \""
        //                 + startTime + "\"," + "\"finished_at\": \""
        //                 + finishTime + "\"," + "\"status\": \""
        //                 + status + "\"" + "}",
        //         authToken);
    }
}
