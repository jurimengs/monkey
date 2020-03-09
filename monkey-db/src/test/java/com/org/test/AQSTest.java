package com.org.test;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class AQSTest extends AbstractQueuedSynchronizer {
    /**
     * 该线程是否正在独占资源。只有用到condition才需要去实现它。
     * @Title: isHeldExclusively
     * @Description: TODO(描述)
     * @return 
     * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#isHeldExclusively() 
     * @author zhouman
     * @date 2020-03-09 03:31:35
     */
    @Override
    protected boolean isHeldExclusively() {
        // TODO Auto-generated method stub
        return super.isHeldExclusively();
    }

    /**
     * 独占方式。尝试获取资源，成功则返回true，失败则返回false。
     * @Title: tryAcquire
     * @Description: TODO(描述)
     * @param arg0
     * @return 
     * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#tryAcquire(int) 
     * @author zhouman
     * @date 2020-03-09 03:31:44
     */
    @Override
    protected boolean tryAcquire(int arg0) {
        // TODO Auto-generated method stub
        return super.tryAcquire(arg0);
    }

    /**
     * 共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
     * @Title: tryAcquireShared
     * @Description: TODO(描述)
     * @param arg0
     * @return 
     * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#tryAcquireShared(int) 
     * @author zhouman
     * @date 2020-03-09 03:32:48
     */
    @Override
    protected int tryAcquireShared(int arg0) {
        // TODO Auto-generated method stub
        return super.tryAcquireShared(arg0);
    }

    
    /**
     * 独占方式。尝试释放资源，成功则返回true，失败则返回false。
     * @Title: tryRelease
     * @Description: TODO(描述)
     * @param arg0
     * @return 
     * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#tryRelease(int) 
     * @author zhouman
     * @date 2020-03-09 03:32:57
     */
    @Override
    protected boolean tryRelease(int arg0) {
        // TODO Auto-generated method stub
        return super.tryRelease(arg0);
    }

    /**
     * 共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。
     * @Title: tryReleaseShared
     * @Description: TODO(描述)
     * @param arg0
     * @return 
     * @see java.util.concurrent.locks.AbstractQueuedSynchronizer#tryReleaseShared(int) 
     * @author zhouman
     * @date 2020-03-09 03:33:06
     */
    @Override
    protected boolean tryReleaseShared(int arg0) {
        // TODO Auto-generated method stub
        return super.tryReleaseShared(arg0);
    }

}
