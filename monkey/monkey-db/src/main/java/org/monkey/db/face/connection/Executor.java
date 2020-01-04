package org.monkey.db.face.connection;

import org.monkey.db.connection.Operate;

public interface Executor<T> {
    public void execute(Operate<T> poll);
}
