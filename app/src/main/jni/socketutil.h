
#ifndef SOCKET_UTIL
#define SOCKET_UTIL
#include <jni.h>
#include <android/log.h>  

#define  LOG_TAG    "mysocket"  
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)  

void release();
const char* connectRemote(const char*,const int);
#endif
