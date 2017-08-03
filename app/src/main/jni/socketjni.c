#include <jni.h>
#include <string.h>
#include "socketutil.h"


  void showToast(JNIEnv *env, jobject context, jstring str) {
    jclass tclss = (*env)->FindClass(env,"android/widget/Toast");  
     jmethodID mid = (*env)->GetStaticMethodID(env,tclss,"makeText","(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;");  
     jobject job = (*env)->CallStaticObjectMethod(env,tclss,mid,context,str);  
     jmethodID showId = (*env)->GetMethodID(env,tclss,"show","()V");  
     (*env)->CallVoidMethod(env,job,showId,context,str);  
  }

 JNIEXPORT void JNICALL Java_com_zqc_socketdemo_SocketJNI_connect  
   (JNIEnv *env, jclass jcz, jobject context, jstring addr, jint port) {  
       // jclass jclz = (*env)->FindClass(env,"com/zqc/socketdemo/DataHolder");  
       // if (jclz) {  
       //      jmethodID ins      = (*env)->GetMethodID(env,jclz,"<init>","()V");  
       //      jobject   holdObj  = (*env)->NewObject(env,jclz,ins);  
       //      jmethodID method1    = (*env)->GetMethodID(env,jclz,"putData","(Ljava/lang/String;)V");  
       //      jstring msg        = (*env)->NewStringUTF(env,"has entering."); //只是个提示而已  
       //      (*env)->CallVoidMethod(env, holdObj,method1,msg);  
  
       //      LOGI("connect socket.");  
       //      const char* response =  connectRemote("10.18.73.62", 6868); 
       //      release();  
       //      LOGI("connect socket end.");  
       //      msg  = (*env)->NewStringUTF(env,response);  
       //      (*env)->CallVoidMethod(env, holdObj,method1,msg);//数据回调给DataHolder方法中  
       // }  
    jstring msg = (*env)->NewStringUTF(env,"start");
    //showToast(env,context,msg);//show的时候会出现暂时卡顿，原因不明
    LOGI("connect socket.");  
    const char* response =  connectRemote("10.18.73.62", 6868); //response就是服务端传给客户端的内容
    release();  
    LOGI("connect socket end.");  
    //msg  = (*env)->NewStringUTF(env,response);
    //showToast(env,context,msg);/
  }  