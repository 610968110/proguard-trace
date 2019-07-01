Java写的Android反混淆框架
===

#### 为何使用此框架
 - 项目混淆上线后，记录的crashLog是混淆后的，如 at com.a.c.ff.a(UNKNOWN SOURCE :32),这时可以使用此框架解析
 - 代码中混淆后不宜读，可以复制代码片段到此框架中解析

使用方式
````
/**
 * @params mappingFilePth mapping文件的路径
 * @params proguardStackLines 混淆后的堆栈信息，数组中的每一个Item是一行堆栈信息
 * @return 返回的json结果
 *         格式：{"code":200,"message":"xxxxx","data":[{"line":"xxxxxx"},{"line":"xxxxxx"},.......]}
 *         code为200解析成功，其他为失败，data中的line是每行解析后的堆栈信息
 */
String result = progurard.api.ProguardUtil.decode(String mappingFilePth, String[] proguardStackLines);
````

