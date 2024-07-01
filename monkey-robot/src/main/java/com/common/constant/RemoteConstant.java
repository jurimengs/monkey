package com.common.constant;

public interface RemoteConstant {
    public interface VMModel {
        // 本地模式
        public final static int MODEL_LOCAL = 0;
        // 远程模式
        public final static int MODEL_REMOTE = 1;
        //
    }

    public interface TransferModel {
        /**
         * 纯操作传输模式，仅传输操作， 这种模式下会屏蔽操作产生的内容变化，相当于远程控制
         */
        public final static int MODEL_TRANSFER_OPERATION = 0;
        /**
         * 内容传输模式，传输复制的内容，相当于远程传输内容
         */
        public final static int MODEL_TRANSFER_CONTENT = 1;
        //
    }
}
