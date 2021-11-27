<h1 align="center"><a href="https://github.com/Abel-LiuJinQuan" target="_blank">searchEngine</a></h1>
<p align="center">
<!--   <a href="https://github.com/Abel-LiuJinQuan/searchEngine"><img alt="Travis-CI" src="https://travis-ci.com/xkcoding/spring-boot-demo.svg?branch=master"/></a> -->
<!--   <a href="https://www.codacy.com/app/xkcoding/spring-boot-demo?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=xkcoding/spring-boot-demo&amp;utm_campaign=Badge_Grade"><img alt="Codacy" src="https://api.codacy.com/project/badge/Grade/1f2e3d437b174bfc943dae1600332ec1"/></a> -->
  <a href="https://blog.csdn.net/Abel_Liujinquan"><img alt="author" src="https://img.shields.io/badge/author-JinQuan.Liu-blue.svg"/></a>
  <a href="https://www.oracle.com/technetwork/java/javase/downloads/index.html"><img alt="JDK" src="https://img.shields.io/badge/JDK-1.8.0_161-orange.svg"/></a>
  <a href="https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/html/"><img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.3.4.RELEASE-brightgreen.svg"/></a>
  <a href="https://github.com/Abel-LiuJinQuan/searchEngine/blob/master/LICENSE"><img alt="LICENSE" src="https://img.shields.io/github/license/Abel-LiuJinQuan/searchEngine.svg"/></a>
</p>

<p align="center">
  <a href="https://github.com/Abel-LiuJinQuan/searchEngine/stargazers"><img alt="star" src="https://img.shields.io/github/stars/Abel-LiuJinQuan/searchEngine.svg?label=Stars&style=social"/></a>
  <a href="https://github.com/Abel-LiuJinQuan/searchEngine/network/members"><img alt="star" src="https://img.shields.io/github/forks/Abel-LiuJinQuan/searchEngine.svg?label=Fork&style=social"/></a>
  <a href="https://github.com/Abel-LiuJinQuan/searchEngine/watchers"><img alt="star" src="https://img.shields.io/github/watchers/Abel-LiuJinQuan/searchEngine.svg?label=Watch&style=social"/></a>
</p>

<p align="center">
<!--   <span>中文 | <a href="./README.en.md">English</a></span> -->
  <span>中文 | English</span>
</p>

## 项目简介
✨ **searchEngine** 是基于 ElasticSearch 和 Java 实现的**文本高亮搜索**项目，模拟搜索引擎检索相似内容。<br/>
该项目集成了 Spring Boot、ElasticSearch、RestHighLevelClient、Vue.js、Element-ui、Log4j 和 Fastjson 等相关技术。<br/>
通过 ElasticSearch 存储文章文本数据，并通过 RestHighLevelClient 访问服务器进行数据读取和存储，并通过 Vue.js 进行可视化操作。<br/>
后续将不断对项目进行优化，添加更多功能，欢迎感兴趣的同学一起交流学习🔥。

**系统展示**

关键字搜索功能

![image](https://user-images.githubusercontent.com/41223520/143683342-7e7f9d43-14ed-4b53-939d-5be4d428e296.png)

文章添加

![image](https://user-images.githubusercontent.com/41223520/143683572-61a25376-b54a-4784-a16e-bb149b976420.png)

## 运行方式
1. git clone `https://github.com/Abel-LiuJinQuan/searchEngine.git`
2. 使用 IDEA 打开 clone 下来的项目
3. 在 IDEA 中 `Maven Projects` 的面板导入项目根目录下 的 `pom.xml` 文件
4. 编辑 `application.properties` 文件，配置自己的 ElasticSearch IP 和 Host(默认为9200)
5. 下载 ElasticSearch，并在本地开启运行，下载及运行方式：`https://www.cnblogs.com/jajian/p/9976900.html`
6. 运行 Maven 项目，后台项目默认端口为 8080
7. 进入 Vue 项目目录`frontend/searchEngineVue`下，依次执行 `npm install` -> `npm run build` -> `npm run dev`，运行 Vue 项目
8. 访问 `http://127.0.0.1:8081/`，完成。

## 版本列表
### v0.0.1

1.使用 ElasticSearch 搭建 Java 底层调用逻辑，封装 ES 的 API，
为后续搜索引擎平台开发提供简易API

2.编写接口，实现插入文本到ES，并能够实现搜索文本任意关键字查找文本信息

3.基于Vue.js编写简易界面，实现添加文本、搜索关键字功能

Copyright (c) 2021 JinQuan.Liu
