#include <jni.h>
#include <string.h>
#include "socketutil.h"

 JNIEXPORT void JNICALL Java_com_zqc_socketdemo_SocketJNI_connect  
   (JNIEnv *env, jclass jcz, jstring addr, jint port) {  
       jclass jclz = (*env)->FindClass(env,"com/zqc/socketdemo/DataHolder");  
       if (jclz) {  
            jmethodID ins      = (*env)->GetMethodID(env,jclz,"<init>","()V");  
            jobject   holdObj  = (*env)->NewObject(env,jclz,ins);  
            jmethodID method1    = (*env)->GetMethodID(env,jclz,"putData","(Ljava/lang/String;)V");  
            jstring msg        = (*env)->NewStringUTF(env,"has entering."); //只是个提示而已  
            (*env)->CallVoidMethod(env, holdObj,method1,msg);  
  
            LOGI("connect socket.");  
            const char* response =  connectRemote("10.18.73.62", 6868); 
            release();  
            LOGI("connect socket end.");  
            msg  = (*env)->NewStringUTF(env,response);  
            (*env)->CallVoidMethod(env, holdObj,method1,msg);//数据回调给DataHolder方法中  
       }  
  }  