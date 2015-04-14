FROM centos:centos6

ENV JDK_VERSION 8u40
ENV JDK_BUILD_VERSION b26

RUN curl -LO "http://download.oracle.com/otn-pub/java/jdk/${JDK_VERSION}-${JDK_BUILD_VERSION}/jdk-${JDK_VERSION}-linux-x64.rpm" \
	-H 'Cookie: oraclelicense=accept-securebackup-cookie' \
	&& rpm -i jdk-${JDK_VERSION}-linux-x64.rpm \
	&& rm -f jdk-${JDK_VERSION}-linux-x64.rpm

ENV JAVA_HOME /usr/java/default
