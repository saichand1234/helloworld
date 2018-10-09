#!/bin/bash
cd `dirname $0`

img_mvn="maven:3.3.3-jdk-8"                 # docker image of maven
m2_cache=~/.m2                              # the local maven cache dir
proj_home=$PWD                              # the project root dir
img_output="deepexi/helloworld"         # output image tag

git pull  # should use git clone https://name:pwd@xxx.git

if which mvn ; then
    echo "use local maven"
    mvn clean package
else
    echo "use docker maven"
    docker run --rm \
        -v $m2_cache:/root/.m2 \
        -v $proj_home:/usr/src/mymaven \
        -w /usr/src/mymaven $img_mvn mvn clean package
fi

sudo mv $proj_home/helloworld-provider/target/helloworld-provider-*.jar $proj_home/helloworld-provider/target/demo.jar # 兼容所有sh脚本
sudo cp $m2_cache/repository/com/taobao/pandora/taobao-hsf.sar/dev-SNAPSHOT/taobao-hsf.sar-dev-SNAPSHOT.jar $proj_home/helloworld-provider/target/taobao-hsf.sar-dev-SNAPSHOT.jar
docker build -t $img_output .

mkdir -p $PWD/logs
chmod 777 $PWD/logs

# 删除容器
docker rm -f helloworld &> /dev/null

version=`date "+%Y%m%d%H"`
# 启动镜像
docker run -d --restart=on-failure:5 --privileged=true \
    --add-host=jmenv.tbsite.net:119.29.54.199 \
    -p 8088:8088 \
    -w /home \
    -v $PWD/logs:/home/logs \
    --name helloworld deepexi/helloworld \
    java \
        -Djava.security.egd=file:/dev/./urandom \
        -Duser.timezone=Asia/Shanghai \
        -Dpandora.location=/home/taobao-hsf.sar-dev-SNAPSHOT.jar \
        -XX:+PrintGCDateStamps \
        -XX:+PrintGCTimeStamps \
        -XX:+PrintGCDetails \
        -XX:+HeapDumpOnOutOfMemoryError \
        -Xloggc:logs/gc_$version.log \
        -jar /home/demo.jar

docker logs -f helloworld