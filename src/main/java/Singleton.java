import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// JVM will ensure a single object INSTANCE, but method are not thread safe.
public enum Singleton {
    INSTANCE;

    public static void main(String[] args) {
        Singleton selector = Singleton.INSTANCE;
        selector.setConfig(List.of(Config.builder().service("s").subservice("ss").ou("o").build()));

        Config customerConfig = selector.getConfig("s", "ss", "o");
        System.out.println(customerConfig);
    }

    private final Map<String, Config> topicMap = new HashMap<>();

    List<Config> configList;

    public void setConfig(@NonNull List<Config> configList) {
        this.configList = configList;
        for (Config config : configList) {
            final String key = buildKey(config);
            topicMap.putIfAbsent(key, config);
        }
    }

    @NonNull
    public Singleton.Config getConfig(final String service, final String subService, final String ou) {
        final String key = buildKey(Config.builder().service(service).subservice(subService).ou(ou).build());
        return topicMap.getOrDefault(key, Config.builder().build());
    }

    @NonNull
    private String buildKey(final Config config) {
        return  Stream.of(config.getService(), config.getSubservice(), config.getOu())
                .filter(s -> !StringUtils.isBlank(s))
                .collect(Collectors.joining(":"));
    }

    @Data
    @Builder
    private static class Config {
        String service;
        String subservice;
        String ou;
    }
}