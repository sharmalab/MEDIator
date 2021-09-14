FROM alpine:3.8
LABEL maintainer="pradeeban.kathiravelu@emory.edu"

### update and include Shadow to manage users and groups
RUN apk update
RUN apk --no-cache add wget
RUN apk --no-cache add shadow

# Add group Bindaas and user Bindaas
RUN groupadd -g 9999 bindaas && \
    useradd -r -u 9999 -g bindaas bindaas

WORKDIR /root/

RUN apk --no-cache add openjdk8-jre

# Add java to path
ENV PATH /root/jre1.8.0_171/bin:$PATH

RUN chown -R bindaas:bindaas /root/

COPY . /root

USER bindaas

EXPOSE 8040

CMD ["sh", "run.sh"]
