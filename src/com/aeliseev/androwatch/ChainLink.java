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

    public void setAfterLast(ChainLink nextChainLink) {
        ChainLink nx = this;
        while (nx.getNext() != null) {
            nx = nx.getNext();
        }
        nx.setNext(nextChainLink);
    }

    public ChainLink getNext() {
        return next;
    }

    public void setupChain(ChainLink... nexts) {

        if (nexts.length > 0) {
            setNext(nexts[0]);
        }

        for (int i=0; i<nexts.length-1; i++) {
            nexts[i].setNext(nexts[i+1]);
        }
    }

    public abstract void doTaskWork(final Context context);
}
