package com.aeliseev.androwatch;

import android.content.Context;

/**
 * Created by AEliseev on 05.03.2014
 */
public abstract class ChainLink {

    // The next element in the chain of responsibility
    protected ChainLink next;

    public void setNext(ChainLink nextChainLink) {
        next = nextChainLink;
    }

    public abstract void doTaskWork(final Context context);
}
