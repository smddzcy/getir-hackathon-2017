FROM node:6.6.0

COPY . /wow
COPY package.json /wow/package.json
COPY .env.example /wow/.env.example

WORKDIR /wow

RUN npm install

CMD ["npm","start"]

EXPOSE 8888
