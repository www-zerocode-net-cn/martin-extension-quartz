FROM erdonline/jdk8-yum-go:latest

MAINTAINER martin114@foxmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /martin-extension-quartz

WORKDIR /martin-extension-quartz

EXPOSE 9605

ADD ./martin-extension/martin-extension-quartz/target/martin-extension-quartz.xjar ./

ADD ./martin-extension/martin-extension-quartz/target/xjar.go ./

RUN go version

RUN go build xjar.go

ENTRYPOINT ./xjar java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar martin-extension-quartz.xjar
