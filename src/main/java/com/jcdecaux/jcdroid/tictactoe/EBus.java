package com.jcdecaux.jcdroid.tictactoe;

import static com.googlecode.androidannotations.api.Scope.Singleton;

import com.googlecode.androidannotations.annotations.EBean;
import com.squareup.otto.Bus;

@EBean(scope = Singleton)
public class EBus extends Bus {
    // Allow AndroidAnnotations Injection
}
