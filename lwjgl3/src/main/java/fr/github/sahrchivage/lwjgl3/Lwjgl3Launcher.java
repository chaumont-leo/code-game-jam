package fr.github.sahrchivage.lwjgl3;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import fr.github.sahrchivage.ConstantsKt;
import fr.github.sahrchivage.Main;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return; // This handles macOS support and helps on Windows.
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(Main.Companion.getMain(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        Graphics.DisplayMode dm = Lwjgl3ApplicationConfiguration.getDisplayMode();
        configuration.setTitle(ConstantsKt.WINDOW_TITLE);
        configuration.useVsync(true);
        configuration.setWindowedMode(dm.width, dm.height);
        configuration.setForegroundFPS(dm.refreshRate + 1);
//        configuration.

        return configuration;
    }
}
