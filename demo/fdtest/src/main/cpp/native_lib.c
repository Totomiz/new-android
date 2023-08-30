

#include <jni.h>
#include "com_example_NativeLib.h"

#include <string.h>
#include <unistd.h>


JNIEXPORT void JNICALL
Java_com_example_NativeLib_copyFile(JNIEnv
*env,
jobject obj, jobject
srcFd,
jobject destFd
) {
// 获取源文件和目标文件的文件描述符
jint srcDescriptor = (*env)->GetIntField(env, srcFd,
                                         (*env)->GetFieldID(env, (*env)->GetObjectClass(env, srcFd),
                                                            "fd", "I"));
jint destDescriptor = (*env)->GetIntField(env, destFd, (*env)->GetFieldID(env,
                                                                          (*env)->GetObjectClass(
                                                                                  env, destFd),
                                                                          "fd", "I"));

// 设置缓冲区大小
const int BUFFER_SIZE = 1024;
char buffer[BUFFER_SIZE];
ssize_t bytesRead;

// 从源文件描述符中读取数据，并将其写入到目标文件描述符中
while ((
bytesRead = read(srcDescriptor, buffer, BUFFER_SIZE)
) > 0) {
write(destDescriptor, buffer, bytesRead
);
}

// 关闭文件描述符
close(srcDescriptor);
close(destDescriptor);
}

//JNIEXPORT void JNICALL Java_com_example_NativeLib_copyFile(JNIEnv* env, jobject obj, jobject src, jobject dest){
//    //获取源文件文件描述符
//    jclass fileDescriptorClass=(*env)->GetObjectClass(env,src);
//    jfieldID fdFieldID = (*env)->GetFieldID(env, fileDescriptorClass, "descriptor", "I");
//    jint srcFd = (*env)->GetIntField(env, src, fdFieldID);
//
//    // 获取目标文件的文件描述符
//    jclass destFileDescriptorClass = (*env)->GetObjectClass(env, dest);
//    jfieldID destFdFieldID = (*env)->GetFieldID(env, destFileDescriptorClass, "descriptor", "I");
//    jint destFd = (*env)->GetIntField(env, dest, destFdFieldID);
//
//    // 打开源文件
//    FILE* srcFile = fdopen(srcFd, "rb");
//    if (srcFile == NULL) {
//        return;
//    }
//
//    // 打开目标文件
//    FILE* destFile = fdopen(destFd, "wb");
//    if (destFile == NULL) {
//        fclose(srcFile);
//        return;
//    }
//
//    // 复制文件内容
//    char buffer[4096];
//    size_t bytesRead;
//    while ((bytesRead = fread(buffer, 1, sizeof(buffer), srcFile)) > 0) {
//        fwrite(buffer, 1, bytesRead, destFile);
//    }
//
//    // 关闭文件
//    fclose(srcFile);
//    fclose(destFile);
//}