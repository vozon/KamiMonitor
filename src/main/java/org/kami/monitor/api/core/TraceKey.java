package org.kami.monitor.api.core;


public interface TraceKey
{

    public abstract Object getInvokeKey();

    public abstract Object getThreadKey();
}
