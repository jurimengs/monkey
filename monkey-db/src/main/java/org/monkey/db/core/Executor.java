package org.monkey.db.core;

public interface Executor<T> {
    public void execute(Operate<T> poll);
}
