package com.app.test;

import org.springframework.context.ApplicationContextAware;

/**
 * Created by bert on 17-9-7.
 */
public class AppContext {

    private static AppContext appContext = new AppContext();

    private AppContext(){

    }

    public static AppContext getInstance() {
        return appContext;
    }

}
