import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SingletonSmartRoutingTopicSelector {
    INSTANCE;

    public static void main(String[] args) {
        SingletonSmartRoutingTopicSelector selector = SingletonSmartRoutingTopicSelector.INSTANCE;
        selector.setCustomerConfig(List.of(CustomerConfig.builder().service("s").subservice("ss").ou("o").build()));

        CustomerConfig customerConfig = selector.getCustomerConfig("s", "ss", "o");
        System.out.println(customerConfig);
    }

    private final Map<String, CustomerConfig> topicMap = new HashMap<>();

    List<CustomerConfig> configList;

    public List<CustomerConfig> getCustomerConfig() {
        return configList;
    }

    public void setCustomerConfig(@NonNull List<CustomerConfig> configList) {
        this.configList = configList;
        for (CustomerConfig config : configList) {
            final String key = buildKey(config);
            topicMap.putIfAbsent(key, config);
        }
    }

    @NonNull
    public CustomerConfig getCustomerConfig(final String service, final String subService, final String ou) {
        final String key = buildKey(CustomerConfig.builder().service(service).subservice(subService).ou(ou).build());
        return topicMap.getOrDefault(key, CustomerConfig.builder().build());
    }

    @NonNull
    private String buildKey(final CustomerConfig config) {
        return  Stream.of(config.getService(), config.getSubservice(), config.getOu())
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.joining(":"));
    }

    @Data
    @Builder
    private static class CustomerConfig {
        String service;
        String subservice;
        String ou;
    }
}