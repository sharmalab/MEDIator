FROM alpine:3.8
LABEL maintainer="pradeeban.kathiravelu@emory.edu"

### update and include Shadow to manage users and groups
RUN apk update
RUN apk --no-cache add wget
RUN apk --no-cache add shadow

# Add group Bindaas and user Bindaas
RUN groupadd -g 9999 bindaas && \
    useradd -r -u 9999 -g bindaas bindaas

WORKDIR /root/src

RUN apk --no-cache add openjdk8-jre

# Add java to path
ENV PATH /root/src/jre1.8.0_171/bin:$PATH

RUN chown -R bindaas:bindaas /root/

WORKDIR /root/bindaas/bin

USER bindaas

EXPOSE 9099
EXPOSE 8080

CMD ["sh", "run.sh"]
