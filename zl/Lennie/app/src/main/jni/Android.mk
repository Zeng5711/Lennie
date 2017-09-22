LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := ndk_test
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	E:\QQPCmgr\Desktop\work\Lennie\app\src\main\jni\ndk_test.c \

LOCAL_C_INCLUDES += E:\QQPCmgr\Desktop\work\Lennie\app\src\main\jni
LOCAL_C_INCLUDES += E:\QQPCmgr\Desktop\work\Lennie\app\src\debug\jni


include $(BUILD_SHARED_LIBRARY)
