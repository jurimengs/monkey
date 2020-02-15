package org.monkey.db.face.connection;

import org.monkey.db.core.Operate;

public interface Executor<T> {
    public void execute(Operate<T> poll);
}
