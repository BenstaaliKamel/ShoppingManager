FROM node:lts-alpine as build-stage
WORKDIR /cash_manager
COPY . .
RUN npm install
EXPOSE 80
CMD ["npm", "run", "serve"]