package org.monkey.db.core.executor;

import org.monkey.db.core.Event;

public interface Executor {
    public void execute(Event poll);
}
