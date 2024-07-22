package com.discipline.drms.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneCache {
    private static final Map<String, Parent> cache = new HashMap<>();

    public static Parent getScene(String fxmlPath) throws IOException {
        if (cache.containsKey(fxmlPath)) {
            return cache.get(fxmlPath);
        }
        FXMLLoader loader = new FXMLLoader(SceneCache.class.getResource(fxmlPath));
        Parent scene = loader.load();
        cache.put(fxmlPath, scene);
        return scene;
    }

    public static void clearCache() {
        cache.clear();
    }
}
