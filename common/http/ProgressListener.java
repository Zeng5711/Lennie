package com.hxyd.dyt.common.http;

/**
 * Created by win7 on 2017/3/31.
 */

public interface ProgressListener {
    /**
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
