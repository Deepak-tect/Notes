# ls : 
## 1. ls -l: 
This command lists files and directories in long format. It displays detailed information about each file or directory, including permissions, number of links, owner, group, size, last modified date, and filename. For example:
```csharp
-rw-r--r-- 1 user group 1234 Apr 26 10:00 example.txt
```

This format shows the file type and permissions, the number of links, owner, group, size in bytes, last modified date, and filename.

### 2. ls -lc: 
This command lists files and directories in long format, but it sorts them based on the time of last change (ctime) of the file or directory, instead of the time of last modification (mtime). The ctime is updated when the metadata of the file or directory changes (such as permissions or ownership). For example:
```csharp
-rw-r--r-- 1 user group 1234 Apr 26 10:00 example.txt
```
This format shows the same information as ls -l, but the files are sorted based on their ctime.

### 3. ls -lu: 
This command lists files and directories in long format, and it sorts them based on the time of last access (atime) of the file or directory. The atime is updated whenever the file or directory is accessed (read, written to, or executed). For example:
```csharp
-rw-r--r-- 1 user group 1234 Apr 26 10:00 example.txt
```
This format shows the same information as ls -l, but the files are sorted based on their atime.
These commands provide different ways of listing and sorting files and directories based on their metadata, providing flexibility in how you view and manage your file system.


# touch 
1. Create a new empty file:



