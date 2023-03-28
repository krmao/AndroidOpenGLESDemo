#include <jni.h>
#include <string>
#include <GLES2/gl2.h>
#include <EGL/egl.h>
#include <EGL/eglext.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_codesdancing_android_opengles_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}