#
# The contents of this file are subject to the terms of the Common Development and
# Distribution License (the License). You may not use this file except in compliance with the
# License.
#
# You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
# specific language governing permission and limitations under the License.
#
# When distributing Covered Software, include this CDDL Header Notice in each file and include
# the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
# Header, with the fields enclosed by brackets [] replaced by your own identifying
# information: "Portions Copyright [year] [name of copyright owner]".
#
# Copyright 2020-2025 Ping Identity Corporation.
#
FROM gcr.io/forgerock-io/java-21:latest

# NOTE: Fabric8 Docker Maven plugin doesn't support heredocs or run mounts.

ARG uid=11111
ARG gid=0
ARG source=.
ARG target=/opt/opendj
ARG runSetup=false

COPY --chown=${uid}:${gid} ${source} ${target}

RUN apt-get update && \
    apt-get install -y bash-completion unzip && \
    rm -rf /var/lib/apt/lists/* && \
    if [ "${uid}" != "11111" ]; then \
      adduser --home "/home/opendj" -uid ${uid} --gid ${gid} opendj \ --disabled-password --gecos "opendj user"; \
    fi && \
    if [ -f "${target}" ]; then \
      echo "Unzipping ${target}..." && \
      mv "${target}" "${target}.zip" && \
      unzip "${target}.zip" -d $(dirname "${target}") && \
      chown -R "${uid}:${gid}" "${target}" && \
      rm "${target}.zip"; \
    fi

USER ${uid}
WORKDIR ${target}
ENV PATH $PATH:/opt/jdk/bin:${target}/bin

RUN if [ "${runSetup}" = "true" ]; then ./samples/docker/setup.sh; fi

EXPOSE 4444
EXPOSE 1389
EXPOSE 1636
EXPOSE 8080
EXPOSE 8443
EXPOSE 8989

ENTRYPOINT ["./docker-entrypoint.sh"]
