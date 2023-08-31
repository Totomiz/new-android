

#include <jni.h>
#include "JniTools.h"

#include <string.h>
#include <unistd.h>
#include <fcntl.h>


JNIEXPORT void JNICALL
Java_com_tdk_jni_JniTools_copyByFd(JNIEnv *env, jclass cls, jint inFd, jint outFd) {
    const int BUFFER_SIZE = 4096;
    char buffer[BUFFER_SIZE];
    int bytesRead;

    while ((bytesRead = read(inFd, buffer, BUFFER_SIZE)) > 0) {
        write(outFd, buffer, bytesRead);
    }
}

JNIEXPORT void JNICALL
Java_com_tdk_jni_JniTools_copyByFileDescriptor(JNIEnv *env, jclass cls, jobject srcFd,
                                               jobject dstFd) {
    int inFd = (*env)->GetIntField(env, srcFd,
                                   (*env)->GetFieldID(env, (*env)->GetObjectClass(env, srcFd), "fd",
                                                      "I"));
    int outFd = (*env)->GetIntField(env, dstFd,
                                    (*env)->GetFieldID(env, (*env)->GetObjectClass(env, dstFd),
                                                       "fd", "I"));

    Java_com_tdk_jni_JniTools_copyByFd(env, cls, inFd, outFd);
}

JNIEXPORT void JNICALL
Java_com_tdk_jni_JniTools_copyByPath(JNIEnv *env, jclass cls, jstring srcPath, jstring dstPath) {
    int inFd = open((*env)->GetStringUTFChars(env, srcPath, NULL), O_RDONLY);
    int outFd = open((*env)->GetStringUTFChars(env, dstPath, NULL), O_WRONLY | O_CREAT | O_TRUNC,
                     0644);

    Java_com_tdk_jni_JniTools_copyByFd(env, cls, inFd, outFd);

    close(inFd);
    close(outFd);
}


