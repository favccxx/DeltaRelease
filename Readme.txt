***********************
**author : favccxx
**type : favsoft
**version : V1.0
***********************

1.该项目仅适用于Java Web项目增量发布。
2.该项目不能取到内部类编译过程中产生的多个内部类，如您的项目中有此类情况，请手工复制到对应的发布目录。

使用说明：
	1.修改/conf/publish_config.xml，将patch_source_dir标签对应的值更改为您的工程目录编译目录
	2.运行Publisher。java
	3.去/conf/publish_config.xml中配置的patch_dest_dir目录寻找您的补丁文件。



