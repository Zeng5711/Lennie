#include <jni.h>

JNIEXPORT jstring JNICALL
Java_lennie_org_lennie_ndk_MyNDK_hello(JNIEnv
*env, jclass type)
{

// TODO


return (*env)->NewStringUTF(env, "hello C++!");
}