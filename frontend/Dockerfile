# ---------- STAGE ÚNICO : dev / pruebas ----------
FROM node:20.11.1-alpine

# CLI globales
RUN npm i -g @angular/cli@19.2.8 @ionic/cli@7.2.1

# Código de la app
WORKDIR /workspace
COPY package*.json ./
RUN npm ci
COPY . .

# Puerto que expondrá ng serve
EXPOSE 4200

# Comando por defecto: servidor de desarrollo
CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "4200"]
