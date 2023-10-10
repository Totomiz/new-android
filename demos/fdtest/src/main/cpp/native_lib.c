

#include <jni.h>
#include "JniTools.h"

#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>


JNIEXPORT void JNICALL
Java_com_tdk_jni_JniTools_copyByFd(JNIEnv *env, jclass cls, jint inFd, jint outFd) {
    const int BUFFER_SIZE = 4096;
    char buffer[BUFFER_SIZE];
    int bytesRead;

    while ((bytesRead = read(inFd, buffer, BUFFER_SIZE)) > 0) {
        write(outFd, buffer, bytesRead);
    }
}

void copyByFdWithCSTD(JNIEnv *env, jclass cls, jint inFd, jint outFd) {
    FILE *srcFile = fdopen(inFd, "r");
    FILE *destFile = fdopen(outFd, "w");

    if (srcFile == NULL || destFile == NULL) {
        // 转换失败
        perror("fdopen");
        return;
    }

    // 从源文件读取内容，并将结果写入目标文件
    char buffer[1024];
    size_t bytesRead;

    while ((bytesRead = fread(buffer, sizeof(char), sizeof(buffer), srcFile)) > 0) {
        fwrite(buffer, sizeof(char), bytesRead, destFile);
    }

    fclose(srcFile);   // 关闭源文件指针
    fclose(destFile);  // 关闭目标文件指针

}

JNIEXPORT void JNICALL
Java_com_tdk_jni_JniTools_copyByFileDescriptor(JNIEnv *env, jclass cls, jobject srcFd,
                                               jobject dstFd) {
//    int inFd = (*env)->GetIntField(env, srcFd,
//                                   (*env)->GetFieldID(env, (*env)->GetObjectClass(env, srcFd), "getInt$",
//                                                      "I"));
//    int outFd = (*env)->GetIntField(env, dstFd,
//                                    (*env)->GetFieldID(env, (*env)->GetObjectClass(env, dstFd),
//                                                       "getInt$", "I"));

    int inFd = (*env)->GetIntField(env, srcFd,
                                   (*env)->GetFieldID(env, (*env)->GetObjectClass(env, srcFd),
                                                      "descriptor",
                                                      "I"));
    int outFd = (*env)->GetIntField(env, dstFd,
                                    (*env)->GetFieldID(env, (*env)->GetObjectClass(env, dstFd),
                                                       "descriptor", "I"));
    //使用内核 IO
    Java_com_tdk_jni_JniTools_copyByFd(env, cls, inFd, outFd);

    //使用c库标准IO
    //copyByFdWithCSTD(env, cls, inFd, outFd);
}

JNIEXPORT void JNICALL
Java_com_tdk_jni_JniTools_copyByPath(JNIEnv *env, jclass cls, jstring srcPath, jstring dstPath) {
    //JNIEnv 在c++中是一个结构体的别名
    // *env 是一个一级指针
    // c++字符转为一个java字符写法 env->NewStringUTF("C++ string")

    //JNIEnv 在c中是一个结构体指针
    // *env 是一个二级指针
    // c 中字符串转换为一个java字符串写法 *env->NewStringUTF(env,"C string")


    int inFd = open((*env)->GetStringUTFChars(env, srcPath, NULL), O_RDONLY);
    int outFd = open((*env)->GetStringUTFChars(env, dstPath, NULL), O_WRONLY | O_CREAT | O_TRUNC,
                     0644);

    Java_com_tdk_jni_JniTools_copyByFd(env, cls, inFd, outFd);

    close(inFd);
    close(outFd);
}


