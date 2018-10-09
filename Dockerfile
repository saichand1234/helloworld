# 建议生产使用，ref: http://blog.tenxcloud.com/?p=1894
FROM fabric8/java-jboss-openjdk8-jdk

# Prepare by downloading dependencies
COPY helloworld-provider/target/demo.jar helloworld-provider/target/taobao-hsf.sar-dev-SNAPSHOT.jar /home/