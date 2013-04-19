package com.jcdecaux.jcdroid.tictactoe;

import static org.androidannotations.annotations.EBean.Scope.Singleton;

import org.androidannotations.annotations.EBean;

import com.squareup.otto.Bus;

@EBean(scope = Singleton)
public class EBus extends Bus {
    // Allow AndroidAnnotations Injection
}
