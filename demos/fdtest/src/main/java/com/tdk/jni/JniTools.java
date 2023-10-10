/*
 * Copyright 2023-Now T Open Source Project .
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.tdk.jni;//


import java.io.FileDescriptor;

// 2023/8/31.
//
public class JniTools {
    static {
        System.loadLibrary("myutil");
    }

    public native static void copyByFd(int inFd, int outFd);

    public native static void copyByFileDescriptor(FileDescriptor srcFd, FileDescriptor dstFd);

    public native static void copyByPath(String srcPath, String dstPath);

}
