DeltaRelease
============

Delta Release Project
Java Web工程增量发布工具

DeltaRelease Project一款Java Web项目增量发布工具，能够快速的帮您取到最新的补丁文件，并发布到对应的工程目录下。

DeltaRelease Project适用于在Eclipse、MyEclipse、NetBeans等Java IDE下开发的工程。

使用说明：
	1.修改/conf/publish_config.xml，将patch_source_dir标签对应的值更改为您的工程目录编译目录
	2.将您想要发布的补丁文件，写到deltaReleaseFile.txt文件中。
	3.运行Publisher。java
	4.现在您可以在/conf/publish_config.xml中配置的patch_dest_dir目录寻找您的补丁文件。
