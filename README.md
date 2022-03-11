# Git常用命令

![git流程图](https://github.com/ZhouFengXun/GitTest/blob/main/git%E6%B5%81%E7%A8%8B%E5%9B%BE.png)

#### 一、本地操作：

git init：初始化本地库

git status：查看工作区、暂存区的状态

git add <file name>：将工作区的“新建/修改”添加到暂存区

git rm --cached <file name>：移除暂存区的修改

git commit <file name>：将暂存区的内容提交到本地库

　　tip：需要再编辑提交日志，比较麻烦，建议用下面带参数的提交方法

git commit -m "提交日志" <file name>：文件从暂存区到本地库

git pull    拉取远程代码

git push origin main  将本地代码提交到github仓库

git clone <远程库地址>：克隆远程库

详细步骤 ：[点击参考](https://blog.csdn.net/weixin_45627031/article/details/107290267)  

 
# 知识扩展：

##### 1.日志

git log：查看历史提交

　　tip：空格向下翻页，b向上翻页，q退出

git log --pretty=oneline：以漂亮的一行显示，包含全部哈希索引值

git log --oneline：以简洁的一行显示，包含简洁哈希索引值

git reflog：以简洁的一行显示，包含简洁哈希索引值，同时显示移动到某个历史版本所需的步数


##### 2.版本控制

git reset --hard 简洁/完整哈希索引值：回到指定哈希值所对应的版本

git reset --hard HEAD：强制工作区、暂存区、本地库为当前HEAD指针所在的版本

git reset --hard HEAD^：后退一个版本　　

　　tip：一个^表示回退一个版本

git reset --hard HEAD~1：后退一个版本

　　tip：波浪线~后面的数字表示后退几个版本

##### 3.比较差异

git diff：比较工作区和暂存区的所有文件差异

git diff <file name>：比较工作区和暂存区的指定文件的差异

git diff HEAD|HEAD^|HEAD~|哈希索引值 <file name>：比较工作区跟本地库的某个版本的指定文件的差异

#### 4.分支操作

git branch -v：查看所有分支

git branch -d <分支名>：删除分支

git branch -m <旧分支名> <新分支名>：重命名分支

git branch <分支名>：新建分支

git checkout <分支名>：切换分支

git merge <被合并分支名>：合并分支

　　tip：如master分支合并 hot_fix分支，那么当前必须处于master分支上，然后执行 git merge hot_fix 命令

　　tip2：合并出现冲突

　　　　①删除git自动标记符号，如<<<<<<< HEAD、>>>>>>>等

　　　　②修改到满意后，保存退出

　　　　③git add <file name>

　　　　④git commit -m "日志信息"，此时后面不要带文件名

##### 5.本地库跟远程库交互：

git clone <远程库地址>：克隆远程库

　　功能：①完整的克隆远程库为本地库，②为本地库新建origin别名，③初始化本地库

git remote -v：查看远程库地址别名

git remote add <别名> <远程库地址>：新建远程库地址别名

git remote rm <别名>：删除本地中远程库别名

git push <别名> <分支名>：本地库某个分支推送到远程库，分支必须指定

git push <别名> :<远程库分支名>：删除远程库的某个分支

　　tip：注意远程库分支名前面有个英文的冒号，代表删除的意思。比如删除远程库的dev分支：git push origin :dev

git pull <别名> <分支名>：把远程库的修改拉取到本地

　　tip：该命令包括git fetch，git merge

git fetch <远程库别名> <远程库分支名>：抓取远程库的指定分支到本地，但没有合并

git merge <远程库别名/远程库分支名>：将抓取下来的远程的分支，跟当前所在分支进行合并

git fork：复制远程库

　　tip：一般是外面团队的开发人员fork本团队项目，然后进行开发，之后外面团队发起pull request，然后本团队进行审核，如无问题本团队进行merge（合并）到团队自己的远程库，整个流程就是本团队跟外面团队的协同开发流程，Linux的团队开发成员即为这种工作方式。

##### 6.git代理设置（加速clone等，非必须）

1.配置ss代理：

git config --global http.proxy socks5://127.0.0.1:6789
git config --global https.proxy socks5://127.0.0.1:6789
git config --global http.sslVerify false

2.取消代理：

git config --global --unset http.proxy 
git config --global --unset https.proxy

3.查看代理

git config --global --get http.proxy
git config --global --get https.proxy
 
# 三、其他设置

1.设置全局用户名

git config --global user.name myname
2.设置全局邮箱

git config --global user.email myemail@gmail.com
3.修复 git status 把中文名显示为 \xxx\xxx\xxx 的问题

git config --global core.quotepath false

