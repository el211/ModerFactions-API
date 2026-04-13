package com.oreofactions.api;

/**
 * Static holder for the {@link ModernFactionsAPI} singleton.
 *
 * <p>The ModernFactions plugin calls {@link #register(ModernFactionsAPI)} during
 * {@code onEnable}. External plugins retrieve the instance via
 * {@link ModernFactionsAPI#get()} or {@link #get()}.
 */
public final class ModernFactionsProvider {

    private static ModernFactionsAPI instance;

    private ModernFactionsProvider() {}

    /**
     * Called by the ModernFactions plugin to publish the API implementation.
     * Do not call this from external plugins.
     */
    public static void register(ModernFactionsAPI api) {
        instance = api;
    }

    /**
     * Returns the active API instance, or {@code null} if the plugin has not
     * finished loading yet.
     */
    public static ModernFactionsAPI get() {
        return instance;
    }
}
