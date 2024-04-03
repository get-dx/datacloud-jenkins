package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.listeners.ItemListener;
import java.util.logging.Logger;

@Extension
public class PipelineListener extends ItemListener {
    private static final Logger LOGGER = Logger.getLogger(PipelineListener.class.getName());

    @Override
    public void onCreated(Item item) {
        LOGGER.info("Item Created: " + item.getName());
        System.out.println("Item Created: " + item.getName());
    }

    @Override
    public void onUpdated(Item item) {
        LOGGER.info("MMMMMMM  AAAAA  Item Updated: " + item.getName());
        System.out.println("MMMMMMM  AAAAA  Item Updated: " + item.getName());
    }

    @Override
    public void onRenamed(Item item, String oldName, String newName) {
        LOGGER.info("MMMMMMM  AAAAA  Item Renamed: " + item.getName());
        System.out.println("MMMMMMM  AAAAA  Item Renamed: " + item.getName());
    }
}
