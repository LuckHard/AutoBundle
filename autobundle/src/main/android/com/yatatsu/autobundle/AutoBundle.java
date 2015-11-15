package com.yatatsu.autobundle;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * Injection helper.
 * resolve each binding class for target.
 */
public final class AutoBundle {

    /**
     * {@link AutoBundleBinder} implementation class will be generated by apt.
     * So if {@link Arg} does not used, impl class may not exist.
     */
    static final String BINDER_CLASS_NAME = "com.yatatsu.autobundle.AutoBundleBindingDispatcher";

    /**
     * static instance will load at the first time static method called.
     */
    private static AutoBundleBinder autoBundleBinder;

    private AutoBundle() {
        throw new AssertionError("no instances");
    }

    /**
     * assign to target fields from {@link Activity#getIntent()}
     * @param activity target activity which has {@link Arg} annotated fields.
     */
    public static void bind(Activity activity) {
        bind(activity, activity.getIntent());
    }

    /**
     * assign to target fields from {@link Fragment#getArguments()}.
     *
     * target may be {@link Fragment} or compat.
     *
     * @param target target Fragment which has {@link Arg} annotated fields.
     */
    public static void bind(Object target) {
        try {
            findBinder().bind(target);
        } catch (Exception e) {
            throw new RuntimeException("AutoBundle cannot bind with " + target.getClass());
        }
    }

    /**
     * assign arguments to target fields.
     *
     * target may be {@link android.app.Activity},
     * {@link android.content.BroadcastReceiver},
     * {@link android.app.Service},
     * {@link android.app.Fragment} or these compatibility class.
     *
     * @param target target which has {@link Arg} annotated fields.
     * @param args source bundle.
     */
    public static void bind(Object target, Bundle args) {
        try {
            findBinder().bind(target, args);
        } catch (Exception e) {
            throw new RuntimeException("AutoBundle cannot bind with " + target.getClass());
        }
    }

    /**
     * assign to target fields from {@link Intent#getExtras()}.
     *
     * target may be {@link android.app.Activity},
     * {@link android.content.BroadcastReceiver},
     * {@link android.app.Service} or these compatibility class.
     *
     * @param target target which has {@link Arg} annotated fields.
     * @param intent source bundle.
     */
    public static void bind(Object target, Intent intent) {
        try {
            findBinder().bind(target, intent);
        } catch (Exception e) {
            throw new RuntimeException("AutoBundle cannot bind with " + target.getClass());
        }
    }

    /**
     * set arguments from source fields.
     *
     * target may be {@link android.app.Activity},
     * {@link android.content.BroadcastReceiver},
     * {@link android.app.Service},
     * {@link android.app.Fragment} or these compatibility class.
     *
     * @param source source instance which has {@link Arg} annotated fields.
     * @param args target bundle.
     */
    public static void pack(Object source, Bundle args) {
        try {
            findBinder().bind(source, args);
        } catch (Exception e) {
            throw new RuntimeException("AutoBundle cannot bind with " + source.getClass());
        }
    }

    static AutoBundleBinder findBinder()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (autoBundleBinder == null) {
            Class<?> binderClass = Class.forName(BINDER_CLASS_NAME);
            autoBundleBinder = (AutoBundleBinder) binderClass.newInstance();
        }
        return autoBundleBinder;
    }
}