import os

def rename_resources_with_prefix(directory, prefix):
    for root, dirs, files in os.walk(directory):

        for file in files:
            # 检查文件名是否以指定前缀开头
            if file.startswith(prefix):
                # 构建新的文件名
                new_file_name = prefix + file[len(prefix):]

                # 构建新的文件路径
                new_file_path = os.path.join(root, new_file_name).replace('/', '\\')

                # 重命名文件
                os.rename(os.path.join(root, file), new_file_path)

                # 更新文件中的资源引用
                update_resource_references(new_file_path, file, new_file_name)

def update_resource_references(file_path, original_file_name, new_file_name):
    with open(file_path.replace('/', '\\'), 'r', encoding='utf-8') as f:
        content = f.read()

    # 在文件内容中替换旧文件名为新文件名
    updated_content = content.replace(original_file_name, new_file_name)

    with open(file_path.replace('/', '\\'), 'w', encoding='utf-8') as f:
        f.write(updated_content)

# 要重命名资源的目录及前缀
res_directory = r'todo'
prefix = 'lib_uires_'

# 执行重命名操作
rename_resources_with_prefix(res_directory, prefix)

# 待完善，更新文件中的资源引用