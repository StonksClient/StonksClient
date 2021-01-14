package com.stonks.stonksclient;

import com.stonks.stonksclient.events.Event;
import com.stonks.stonksclient.modules.Module;
import com.stonks.stonksclient.modules.Setting;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.StickyKeyBinding;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;

public class StonksClient implements ModInitializer {

    static KeyBinding k1;
    private static final HashMap<Class<? extends Event>, List<Consumer<Event>>> listeners = new HashMap<>();
    public static final HashMap<Module, ArrayList<Field>> options = new HashMap<>();
    public static final ArrayList<Module> modules = new ArrayList<>();

    public static <T extends Event> void dispatchEvent(T e) {
        for (Consumer<Event> con : listeners.get(e.getClass())) {
            con.accept(e);
        }
    }

    public static <T extends Event> void registerListener(Class<T> event, Consumer<Event> lis) {
        List<Consumer<Event>> ar = listeners.get(event);
        if (ar == null) {
            listeners.put(event, Arrays.asList(lis));
        } else {
            try {
                ar.add(lis);
            } catch (UnsupportedOperationException ignored) {
                ArrayList<Consumer<Event>> ne = new ArrayList<>(ar);
                ne.add(lis);
                listeners.put(event, ne);
            }
        }
    }

    //ultra big stuff for reflexion
    private static Class<?>[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[0]);
    }

    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    @Override
    public void onInitialize() {
        //register a keys
        k1 = KeyBindingHelper.registerKeyBinding(new StickyKeyBinding("key.stonks.gui", GLFW.GLFW_KEY_RIGHT_SHIFT, "key.category.stonks", () -> true));
        //check each tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            //if we are in game (we should be)
            if (client.world != null) {
                boolean guiOpen = k1.isPressed();
                if (guiOpen) {
                    if (client.currentScreen == null) {
                        client.openScreen(new GuiScreen());
                    }
                }
            }
        });
        try {
            ArrayList<String> names = new ArrayList<>(); //for what ever reason they get listed twice
            for (Class<?> cl : getClasses("com.stonks.stonksclient.modules")) {
                if (cl.getSuperclass() != null && cl.getSuperclass().equals(Module.class)) { //if this extends Module
                    if (names.contains(cl.getName())) {
                        continue; //nope, we already have it
                    }
                    names.add(cl.getName());
                    Module m = (Module) cl.newInstance();
                    m.registerEvents();
                    modules.add(m);

                    ArrayList<Field> ar = new ArrayList<>();
                    for (Field f : cl.getDeclaredFields()) {
                        if (f.isAnnotationPresent(Setting.class)) {
                            ar.add(f);
                        }
                    }
                    options.put(m, ar);
                }
            }
        } catch (ClassNotFoundException | IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
