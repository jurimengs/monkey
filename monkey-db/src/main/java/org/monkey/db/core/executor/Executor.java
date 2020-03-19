package org.monkey.db.core.executor;

import org.monkey.db.core.event.Event;

public interface Executor {
    public void execute(Event poll);
}
